package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.sql.SqlTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Vladimir on 23.09.2016.
 */
public class SqlStorage implements Storage {


    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(new SqlTransaction<Void>() {
            @Override
            public Void execute(Connection conn) throws SQLException {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value=? WHERE type=? AND resume_uuid=?")) {
                    for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                        ps.setString(1, e.getValue());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, r.getUuid());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                return null;
            }
        });
    }

    @Override
    public void save(Resume r) {

        sqlHelper.transactionalExecute(new SqlTransaction<Void>() {
            @Override
            public Void execute(Connection conn) throws SQLException {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                }
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                    for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, e.getValue());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.execute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c" +
                "        ON r.uuid = c.resume_uuid " +
                "     WHERE r.uuid=?", new SqlExecutor<Resume>() {
            @Override
            public Resume execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume r = new Resume(uuid, rs.getString("full_name"));
                do {
                    String value = rs.getString("value");
                    if (value != null) {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
                    }
                } while (rs.next());
                return r;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid=?", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            }
        });
    }


    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.execute("" +
                "    SELECT r.uuid, r.full_name, c.type, c.value" +
                "      FROM resume r" +
                " LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, UUID", new SqlExecutor<List<Resume>>() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();

                List<Resume> resumes = new ArrayList<>();

                while (rs.next()) {

                    Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    while (Objects.equals(rs.getString("uuid"), r.getUuid()) && rs.next()) {

                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.addContact(type, value);
                        }
                    }
                    resumes.add(r);
                    rs.previous();
                }
                return resumes;
            }
        });
    }

    @Override
    public int size() {

        return sqlHelper.execute("SELECT count(*) FROM resume", new SqlExecutor<Integer>() {
            @Override
            public Integer execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                return rs.next() ? rs.getInt(1) : 0;
            }
        });
    }
}

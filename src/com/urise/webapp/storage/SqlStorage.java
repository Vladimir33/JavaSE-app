package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladimir on 23.09.2016.
 */
public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            if (r.getContacts().isEmpty()) {
                try (PreparedStatement psNullContacts = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                    psNullContacts.setString(1, r.getUuid());
                    psNullContacts.execute();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value=? WHERE type=? AND resume_uuid=?")) {

                    sqlHelper.addBatchFromMapInUpdate(r, ps, r.getContacts());
                    ps.executeBatch();
                }
            }
            if (r.getSections().isEmpty()) {
                try (PreparedStatement psNullSections = conn.prepareStatement("DELETE FROM text_section WHERE resume_uuid=?")) {
                    psNullSections.setString(1, r.getUuid());
                    psNullSections.execute();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE text_section SET ts_value=? WHERE ts_type=? AND resume_uuid=?")) {

                    sqlHelper.addBatchFromMapInUpdate(r, ps, r.getSections());
                    ps.executeBatch();
                }
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {

        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {

                sqlHelper.addBatchFromMapInSave(r, ps, r.getContacts());
                ps.executeBatch();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO text_section (resume_uuid, ts_type, ts_value) VALUES (?,?,?)")) {

                sqlHelper.addBatchFromMapInSave(r, ps, r.getSections());
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.execute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c" +
                "        ON r.uuid = c.resume_uuid " +
                " LEFT JOIN text_section ts" +
                "        ON r.uuid = ts.resume_uuid   WHERE r.uuid=?", ps -> {
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
                        String ts_value = rs.getString("ts_value");
                        if (ts_value != null) {
                            SectionType type = SectionType.valueOf(rs.getString("ts_type"));
                            r.addSection(type, new TextSection(ts_value));
                        }
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.execute("" +
                "    SELECT r.uuid, r.full_name, c.type, c.value, ts_type, ts_value" +
                "      FROM resume r" +
                " LEFT JOIN contact c ON r.uuid = c.resume_uuid" +
                " LEFT JOIN text_section ts ON r.uuid = ts.resume_uuid" +
                "  ORDER BY full_name, UUID", ps -> {
                    ResultSet rs = ps.executeQuery();

                    List<Resume> resumes = new ArrayList<>();

                    while (rs.next()) {

                        Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        do {
                            String value = rs.getString("value");
                            if (value != null) {
                                ContactType type = ContactType.valueOf(rs.getString("type"));
                                r.addContact(type, value);
                            }
                            String ts_value = rs.getString("ts_value");
                            if (ts_value != null) {
                                SectionType type = SectionType.valueOf(rs.getString("ts_type"));
                                r.addSection(type, new TextSection(ts_value));
                            }
                        } while (rs.next() && Objects.equals(rs.getString("uuid"), r.getUuid()));

                        resumes.add(r);
                        rs.previous();
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {

        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}

package com.urise.webapp.sql;


import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;


import java.sql.*;
import java.util.Map;

/**
 * Created by Vladimir on 24.09.2016.
 */
public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public void execute(String sql) {

        //    execute(sql, PreparedStatement::execute);
        execute(sql, new SqlExecutor<Boolean>() {
            @Override
            public Boolean execute(PreparedStatement ps) throws SQLException {
                return ps.execute();
            }
        });
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

//    public void setContact(Resume r, PreparedStatement ps) throws SQLException {
//        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, e.getKey().name());
//            ps.setString(3, e.getValue());
//            ps.addBatch();
//        }
//    }
}


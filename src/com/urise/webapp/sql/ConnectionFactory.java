package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Vladimir on 23.09.2016.
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;

    //PreparedStatement getPreparedStatement(String sql) throws SQLException;
}

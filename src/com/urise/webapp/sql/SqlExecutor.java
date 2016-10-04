package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Vladimir on 01.10.2016.
 */
public interface SqlExecutor<T> {
    T execute(PreparedStatement ps) throws SQLException;
}

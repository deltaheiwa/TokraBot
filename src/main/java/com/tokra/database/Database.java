package com.tokra.database;

import com.tokra.bot.Tokra;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection connect() throws SQLException;
    void createTables();

    DatabaseType getType();
}

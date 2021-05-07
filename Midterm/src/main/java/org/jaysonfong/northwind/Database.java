/*
 * The MIT License
 *
 * Copyright 2021 Jayson Fong <contact@jaysonfong.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jaysonfong.northwind;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jaysonfong.northwind.template.Toolbox.util.DisplayScene;
import org.jaysonfong.northwind.template.Toolbox.util.SceneManager;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public final class Database {
    
    private static String DATABASE_URL = null;
    private static Connection connection;
    
    /**
     *
     * @return Database URL
     */
    public static final String getDatabaseUrl() {
        if (Database.DATABASE_URL != null) {
            return Database.DATABASE_URL;
        }
        File databaseFile = new File("Northwind.mdb");
        Database.DATABASE_URL = "jdbc:ucanaccess:///" + databaseFile.getAbsolutePath();
        return Database.DATABASE_URL;
    }
    
    /**
     * Initialize the connection
     * @throws SQLException 
     */
    public static final void initializeConnection() throws SQLException {
        Database.connection = DriverManager.getConnection(Database.getDatabaseUrl());
    }
    
    public static final PreparedStatement getStatement(final String sql) throws SQLException {
        try {
            return Database.connection.prepareStatement(sql);
        } catch (SQLException | NullPointerException sqlNullException) {
            SceneManager.switchScene(DisplayScene.ERROR);
            throw new SQLException("Prepared Statement Creation Failure");
        }
    }
    
    public static final void updateUrl(String newUrl) {
        Database.DATABASE_URL = newUrl;
        try {
            Database.initializeConnection();
        } catch (SQLException sqlException) {
            SceneManager.switchScene(DisplayScene.ERROR);
        }
    }
    
}

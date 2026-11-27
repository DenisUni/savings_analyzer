package org.database.databaseManagementTest;

import org.infrastructure.database.DatabaseManagement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTableTest {

    private static final String DB_TEST_URL = "src/main/java/org/infrastructure/database/test-database.db";
    private static final String SQL_TEST_URL = "jdbc:sqlite:"+ DB_TEST_URL;


    @BeforeEach
    void setUp() throws SQLException {

        //Create the test database
        DatabaseManagement.setUrl(SQL_TEST_URL);

        DatabaseManagement.createTables();

        //Start with an empty database
        try (Connection conn = DriverManager.getConnection(SQL_TEST_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM SavingsCategory");
            stmt.executeUpdate("DELETE FROM IncomeSource");
            stmt.executeUpdate("DELETE FROM SaveProfile");
            stmt.executeUpdate("DELETE FROM User");
        }
    }

    @AfterAll
    static void tearDown() throws SQLException, IOException
    {
        Files.delete(Paths.get(DB_TEST_URL));
    }

    @Test
    void testCreateTables() throws SQLException{

        try (Connection conn = DriverManager.getConnection(SQL_TEST_URL);
             Statement stmt = conn.createStatement()) {

            //Import all table names from the database
            ResultSet rs = stmt.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table'");

            ArrayList<String> tables = new ArrayList<>();

            //Store all table names in the list
            while(rs.next()){
                tables.add(rs.getString("name"));
            }

            //Check against the required table names
            assertTrue(tables.contains("User"));
            assertTrue(tables.contains("SaveProfile"));
            assertTrue(tables.contains("SavingsCategory"));
            assertTrue(tables.contains("IncomeSource"));

        }
    }




}

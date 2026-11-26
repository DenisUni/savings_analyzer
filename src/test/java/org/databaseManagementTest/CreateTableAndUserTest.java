package org.databaseManagementTest;

import org.domain.User;
import org.infrastructure.database.DatabaseManagement;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class CreateTableAndUserTest {

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

    @DisplayName("Normal case: Insert user")
    @Test
    void testInsertUser() throws SQLException{
        DatabaseManagement.userInsert("001-001", "test_name","test@email");

        ArrayList<User> users = DatabaseManagement.userSearch("test_name","test@email");

        assertEquals(1,users.size());

        assertEquals("001-001", users.get(0).getId());
        assertEquals("test_name",  users.get(0).getName());
        assertEquals("test@email", users.get(0).getEmail());
    }



    @Nested
    @DisplayName("User – Edge cases: Empty parameters")
    class UserEmptyParameters{

        @DisplayName("Edge case: Empty user ID")
        @Test
        void testInsertUserEmptyId() {

            SQLException exception = assertThrows(SQLException.class,
                    ()-> {DatabaseManagement.userInsert("", "test_name", "test_email");});
            assertEquals("[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM (id) <> '')", exception.getMessage());

        }
        @DisplayName("Edge case: Empty user name")
        @Test
        void testInsertUserEmptyName() {

            SQLException exception = assertThrows(SQLException.class,
                    ()-> {DatabaseManagement.userInsert("001-001", "", "test_email");});
            assertEquals("[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM(name) <> '')", exception.getMessage());

        }
        @DisplayName("Edge case: Empty name email")
        @Test
        void testInsertUserEmptyEmail() throws SQLException{

            SQLException exception = assertThrows(SQLException.class,
                    ()-> {DatabaseManagement.userInsert("001", "test_name", "");});
            assertEquals( "[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM(email) <> '')", exception.getMessage());
        }
    }















}

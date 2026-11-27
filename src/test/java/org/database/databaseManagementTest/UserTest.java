package org.database.databaseManagementTest;

import org.domain.User;
import org.infrastructure.database.DatabaseManagement;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

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


    @Nested
    @DisplayName("User input")
    class userInsert{


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


        @DisplayName("Edge case: duplicate ID insert")
        @Test
        void UserDuplicateInsert() throws SQLException {
            DatabaseManagement.userInsert("001-001", "test_name1", "test_email1");

            SQLException exception = assertThrows(SQLException.class,
                    ()-> {DatabaseManagement.userInsert("001-001", "test_name2" , "test_email2");});

            assertEquals("[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: User.id)",
                    exception.getMessage());

        }

        @Nested
        @DisplayName("User – Edge cases: Input empty parameters")
        class UserEmptyParameters{

            @DisplayName("Edge case: Empty user ID")
            @Test
            void testInsertUserEmptyId() {

                SQLException exception = assertThrows(SQLException.class,
                        ()-> {DatabaseManagement.userInsert("    ", "test_name", "test_email");});
                assertEquals("[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM (id) <> '')", exception.getMessage());

            }
            @DisplayName("Edge case: Empty user name")
            @Test
            void testInsertUserEmptyName() {

                SQLException exception = assertThrows(SQLException.class,
                        ()-> {DatabaseManagement.userInsert("001-001", "     ", "test_email");});
                assertEquals("[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM(name) <> '')", exception.getMessage());

            }
            @DisplayName("Edge case: Empty name email")
            @Test
            void testInsertUserEmptyEmail() throws SQLException{

                SQLException exception = assertThrows(SQLException.class,
                        ()-> {DatabaseManagement.userInsert("001", "test_name", " ");});
                assertEquals( "[SQLITE_CONSTRAINT_CHECK] A CHECK constraint failed (CHECK constraint failed: TRIM(email) <> '')", exception.getMessage());
            }
        }

    }
    @Nested
    @DisplayName("User search")
    class UserSearch {
        @Test
        @DisplayName("Normal case - not exist user ")
        void testSearchNotExistUser() throws SQLException {
            ArrayList<User> users = DatabaseManagement.userSearch("unknown_name", "unknown_email");
            assertEquals(0, users.size());
        }

        @Test
        @DisplayName("User search - same name")
        void testSameName() throws SQLException {
            DatabaseManagement.userInsert("001","test_name","test_email1");
            DatabaseManagement.userInsert("002","test_name","test_email2");

            ArrayList <User> users1 = DatabaseManagement.userSearch("test_name","test_email1");
            ArrayList <User> users2 = DatabaseManagement.userSearch("test_name","test_email2");


            assertEquals(1,users1.size());
            assertEquals("001",users1.get(0).getId());

            assertEquals(1,users2.size());
            assertEquals("002",users2.get(0).getId());
        }
    }













}

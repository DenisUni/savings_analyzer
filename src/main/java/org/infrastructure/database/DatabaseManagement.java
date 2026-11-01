package org.infrastructure.database;

import java.sql.*;
//https://www.sqlitetutorial.net/sqlite-java/

public class DatabaseManagement {

    //connection string
    private static final String URL =
            "jdbc:sqlite:src/main/java/org/infrastructure/database/database.db";


    public static void  createTables(){


        String sqlUser = "CREATE TABLE IF NOT EXISTS User(" +
                " id varchar primary key," +
                " name varchar not null," +
                " email varchar not null);";


        String sqlSaveProfile = "CREATE TABLE IF NOT EXISTS SaveProfile(" +
                "    save_id varchar not null ," +
                "    id varchar not null ," +
                "    name varchar not null," +
                "    savings_rate DECIMAL(4,3) check ( savings_rate between 0 and 1 ) not null," +
                "    primary key (save_id,id)," +
                "    FOREIGN KEY (id) references User(id)" +
                ");";

        String SavingsCategory = "CREATE TABLE IF NOT EXISTS SavingsCategory(" +
                "    category_id varchar primary key," +
                "    save_id varchar not null," +
                "    id varchar not null," +
                "    name varchar not null," +
                "    percent DECIMAL(4,3) check ( percent between 0 and 1) not null," +
                "    description TEXT," +
                "    foreign key (save_id,id) references  SaveProfile(save_id,id)" +
                ");";

        String IncomeSource = "CREATE TABLE IF NOT EXISTS IncomeSource(" +
                "    income_id varchar primary key," +
                "    save_id varchar not null ," +
                "    id varchar not null ," +
                "    name varchar not null," +
                "    amount DECIMAL (10,2) check ( Amount >= 0 )," +
                "    description TEXT," +
                "    active BOOLEAN not null," +
                "    foreign key (save_id,id) references  SaveProfile(save_id,id)" +
                ");";
             try (Connection conn =  DriverManager.getConnection(URL);
                  Statement stmt = conn.createStatement()){
                //Create new tables
                stmt.execute(sqlUser);
                stmt.execute(sqlSaveProfile);
                stmt.execute(SavingsCategory);
                stmt.execute(IncomeSource);

                System.out.println("Tables successfully created.");

            } catch (SQLException e) {
                System.out.println("(createTables) ERROR :" + e.getMessage());
            }
        }

    public static void userInsert(String id, String name, String email){

        String sql = "INSERT INTO User(id, name, email) VALUES (?,?,?)";

        try(Connection conn =  DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,email);
            pstmt.executeUpdate();  //run the INSERT statement

            System.out.println("User was in DB insert");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }




}

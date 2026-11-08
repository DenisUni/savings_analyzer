package org.infrastructure.database;

import org.domain.IncomeSource;
import org.domain.SaveProfile;
import org.domain.SavingsCategory;
import org.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;

//https://www.sqlitetutorial.net/sqlite-java/

public class DatabaseManagement {

    //connection string
    private static final String URL =
            "jdbc:sqlite:src/main/java/org/infrastructure/database/database.db";

    public static void createTables(){

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

    public static ArrayList<User>  userSearch(String name, String email){
        String sql = "SELECT id,name,email FROM User WHERE name = ? AND email = ?";

        try(Connection conn = DriverManager.getConnection(URL);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();

            ArrayList<User> result  = new ArrayList<User>();


            while (rs.next()){

                result.add(new User(rs.getString("id"),rs.getString("name"),rs.getString("email")));

                //Debug
                System.out.printf("%-25s%-25s%-25s%n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            //Insert lists to output list
            return result;

        }
        catch(Exception e){
                System.out.println(e.getMessage());
        }
        return null;
    }

    public static Boolean  userExist(String name,String email){

        String sql = "SELECT id,name,email FROM User WHERE name = ? AND email = ? ";

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> names  = new ArrayList<String>();
            ArrayList<String> emails  = new ArrayList<String>();
            ArrayList<ArrayList<String>> result  = new ArrayList<ArrayList<String>>();


            while (rs.next()){
                names.add(rs.getString("name"));
                emails.add(rs.getString("email"));

            }
            if(names.contains(name) && emails.contains(email)) return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void saveProfileInsert(String user_id,String save_id, String name, Double saving_rate ){

        String sql = "INSERT INTO SaveProfile(save_id, id, name, savings_rate) VALUES (?,?,?,?)";

        try(Connection conn =  DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,save_id);
            pstmt.setString(2,user_id);
            pstmt.setString(3,name);
            pstmt.setDouble(4,saving_rate);
            pstmt.executeUpdate();  //run the INSERT statement

            System.out.println("SaveProfile was in DB insert");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<SaveProfile> saveProfileSearch(String user_id){
        String sql = "SELECT save_id, id, name, savings_rate FROM SaveProfile WHERE id = ?";

        try(Connection conn = DriverManager.getConnection(URL);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<SaveProfile> result  = new ArrayList<SaveProfile>();

            while (rs.next()){

                result.add(new SaveProfile(rs.getString("save_id"),
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("savings_rate")));
                //Debug
                /*System.out.printf("%-25s%-25s%-25s%-25s%n",
                        rs.getString("save_id"),
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("savings_rate")
                );*/
            }

            return result;

        }
        catch(Exception e){
            System.out.println("(saveProfileSearch) ERROR: "+ e.getMessage());
        }
        return null;
    }

    public static void incomeSourceInsert(
            String income_id , String user_id, String save_id,
            String name, Double amount , String description, Boolean isActive  ){

        String sql =
                "INSERT INTO IncomeSource(income_id, save_id, id, name, amount, description, active) VALUES (?,?,?,?,?,?,?)";

        try(Connection conn =  DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,income_id);
            pstmt.setString(2,save_id);
            pstmt.setString(3,user_id);
            pstmt.setString(4,name);
            pstmt.setDouble(5,amount);
            pstmt.setString(6, description);
            pstmt.setBoolean(7, isActive);

            pstmt.executeUpdate();  //run the INSERT statement

            System.out.println("IncomeSource was in DB insert");
        }
        catch (Exception e){
            System.err.println("(incomeSourceInsert) ERROR: " + e.getMessage());
        }
    }

    public static ArrayList<IncomeSource> incomeSourceSearch(
            String user_id, String saveProfile_id){

        String sql = "SELECT income_id, save_id, id, name, amount, description, active FROM IncomeSource WHERE id = ? AND save_id = ?";

        try(Connection conn = DriverManager.getConnection(URL);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            pstmt.setString(2, saveProfile_id);

            try( ResultSet rs = pstmt.executeQuery()) {

                ArrayList<IncomeSource> result = new ArrayList<IncomeSource>();

                while (rs.next()) {
                    result.add(new IncomeSource(
                            rs.getString("save_id"),
                            rs.getString("id"),
                            rs.getString("income_id"),
                            rs.getString("name"),
                            rs.getDouble("amount"),
                            rs.getString("description"),
                            rs.getBoolean("active")
                    ));
                    //Debug
                    System.out.printf("%-25s%-25s%-25s%-25s%-25s%-25s%-25s%n",
                            rs.getString("save_id"),
                            rs.getString("id"),
                            rs.getString("income_id"),
                            rs.getString("name"),
                            rs.getDouble("amount"),
                            rs.getString("description"),
                            rs.getBoolean("active")
                    );
                }
                return result;
            }
            catch (Exception e){
                System.out.println("(incomeSourceSearch ResultSet) Error: " + e.getMessage());
            }

        }
        catch(Exception e){
            System.out.println("(incomeSourceSearch Connection or PreparedStatement ) ERROR: "+ e.getMessage());
        }
        return null;
    }

    //Work ...
    public static void savingsCategoryInsert(
            String category_id,String save_id, String user_id,  String name, double percent, String description)
    {
        String sql =
                "INSERT INTO SavingsCategory(category_id, save_id, id, name, percent, description) VALUES (?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1,category_id);
            pstmt.setString(2,save_id);
            pstmt.setString(3,user_id);
            pstmt.setString(4,name);
            pstmt.setDouble(5,percent);
            pstmt.setString(6,description);

            pstmt.executeUpdate();  //run the INSERT statement

            System.out.println("SavingsCategory was in DB insert");

        }

        catch (Exception e){
            System.out.println("(SavingsCategoryInsert) ERROR: "  + e.getMessage());
        }
    }

    public static ArrayList<SavingsCategory> savingsCategorySearch(
            String user_id , String saveProfile_id)
    {
        String sql = "SELECT category_id, save_id, id, name, percent, description FROM SavingsCategory WHERE id = ? AND save_id = ?";

        try(Connection conn = DriverManager.getConnection(URL);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            pstmt.setString(2, saveProfile_id);

            try( ResultSet rs = pstmt.executeQuery()) {

                ArrayList<SavingsCategory> result = new ArrayList<SavingsCategory>();

                while (rs.next()) {
                    result.add(new SavingsCategory(
                            rs.getString("category_id"),
                            rs.getString("save_id"),
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("percent"),
                            rs.getString("description")
                    ));
                    //Debug
                    System.out.printf("%-25s%-25s%-25s%-25s%-25s%-25s%n",
                            rs.getString("category_id"),
                            rs.getString("save_id"),
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("percent"),
                            rs.getString("description")
                    );
                }
                return result;
            }
            catch (Exception e){
                System.out.println("(SavingsCategorySearch ResultSet) Error: " + e.getMessage());
            }

        }
        catch(Exception e){
            System.out.println("(SavingsCategorySearch Connection or PreparedStatement ) ERROR: "+ e.getMessage());
        }
        return null;
    }





}

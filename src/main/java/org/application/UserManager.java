package org.application;

import org.domain.IncomeSource;
import org.domain.SaveProfile;
import org.domain.SavingsCategory;
import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class UserManager {
    //___________________________User___________________________________________________

    //Create class User
    public static User userClassCreate (String name, String email){
        return new User(name, email);  //User class is success created and return true
    }

    //Create object User in Database
    public static void userSaveDatabase(User user1) throws SQLException{
        DatabaseManagement.createTables();
        DatabaseManagement.userInsert(user1.getId(), user1.getName(), user1.getEmail()); //Object created in DB
    }

    //Save User in DB and create class
    public static void userCreate(String name, String email) throws SQLException{

        if(name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Invalid name or email");

        name = name.trim().toLowerCase();
        email = email.trim().toLowerCase();


        if (DatabaseManagement.userExist(name, email))
            throw new IllegalArgumentException("User already exist");

        User user = userClassCreate(name, email);

        userSaveDatabase(user);

    }

    public static User userDataSearch(String name,String email) throws SQLException {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name or email");
        }


        name = name.trim().toLowerCase();
        email = email.trim().toLowerCase();


        ArrayList<User> usersList =  DatabaseManagement.userSearch(name,email);

        if(usersList.isEmpty() ) {
            throw new NoSuchElementException("User not found!");
        }
        if(usersList.size() > 1){
            throw new IllegalStateException("Duplicate users found!");
        }
        String user_id = usersList.get(0).getId();
        String user_name = usersList.get(0).getName();
        String user_email = usersList.get(0).getEmail();

        return new User(user_id,user_name,user_email);

    }

    //_____________________________SaveProfile_______________________________________________________
    public static SaveProfile saveProfileClassCreate(String user_id, String name, double saving_rate){
        return new SaveProfile(user_id,name,saving_rate);
    }

    public static void saveProfileInDatabaseCreate(SaveProfile saveProfile) throws SQLException{
        DatabaseManagement.saveProfileInsert(
                saveProfile.getUser_id(), saveProfile.getSave_id(), saveProfile.getName(),saveProfile.getSaving_rate());
    }

    public static void saveProfileCreate(String user_id, String name, double saving_rate)throws SQLException{

        if(user_id == null || user_id.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || saving_rate <= 0 || saving_rate > 100
                || !Double.isFinite(saving_rate))
            throw new IllegalArgumentException("Invalid SaveProfile parameters!");

        name = name.trim().toLowerCase();

        SaveProfile saveProfileUser = saveProfileClassCreate( user_id,  name,  saving_rate/100);
        saveProfileInDatabaseCreate(saveProfileUser);
    }

    public static ArrayList<SaveProfile> saveProfilesSearch(String user_id) throws SQLException{
        return DatabaseManagement.saveProfileSearch(user_id);
    }

    public static SaveProfile saveProfileSearch(String user_id, String save_id) throws SQLException{

        ArrayList<SaveProfile> saveProfilesList = saveProfilesSearch(user_id);

        for(SaveProfile saveProfile1 : saveProfilesList  ){
            if(saveProfile1.getSave_id().equals(save_id))
                return saveProfile1;
        }
        throw new NoSuchElementException("SaveProfile not found!");
    }

    //________________________________IncomeSource___________________________________________________
    public static IncomeSource incomeSourceClassCreate(String user_id, String save_id,
                                            String name, double amount , String description, Boolean isActive){

        return new IncomeSource(save_id, user_id, name, amount,description, isActive );
    }

    public static void incomeSourceInDataBase(IncomeSource incomeSource1) throws SQLException{
        DatabaseManagement.incomeSourceInsert(
                incomeSource1.getIncome_id(),
                incomeSource1.getId(),
                incomeSource1.getSave_id(),
                incomeSource1.getName(),
                incomeSource1.getAmount(),
                incomeSource1.getDescription(),
                incomeSource1.isActive());
    }

    public static void incomeSourceCreate(String user_id, String save_id, String name,
                                          double amount , String description, Boolean isActive) throws SQLException{

        if(user_id == null || user_id.trim().isEmpty() ||
                save_id == null || save_id.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                amount <= 0 ||
                !Double.isFinite(amount))
            throw new IllegalArgumentException("Invalid incomeSource parameters");

        name = name.trim().toLowerCase();

        IncomeSource incomeSource1 = incomeSourceClassCreate(user_id,save_id,name,amount,description,isActive);
        incomeSourceInDataBase(incomeSource1);
    }

    public static ArrayList<IncomeSource> incomeSourcesSearch(String user_id, String save_id) throws SQLException{
        return DatabaseManagement.incomeSourceSearch(user_id,save_id);
    }

    public static void incomeSourceUpdate(String name, double amount,String description, Boolean active,
                                             String id, String save_id,String income_id) throws SQLException{
        if(save_id == null || save_id.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                amount  <= 0 ||
                !Double.isFinite(amount))
            throw new IllegalArgumentException("Invalid incomeSource parameters");

        name = name.trim().toLowerCase();

        DatabaseManagement.incomeSourceUpdate( name,  amount, description,  active, id,  save_id, income_id );
    }

    public static double total_income_calculation(String user_id, String save_id) throws SQLException{
        ArrayList<IncomeSource> incomeSourceList = incomeSourcesSearch(user_id,save_id );
        double total_income = 0;
        for (IncomeSource incomeSource : incomeSourceList) {
            if(incomeSource.isActive())
                total_income += incomeSource.getAmount();
        }
        return total_income;
    }

    //__________________________________SavingsCategory__________________________________________
    public static SavingsCategory savingsCategoryClassCreate(String id, String save_id, String name, double percent, String description) {
        return  new SavingsCategory( save_id,  id,  name,  percent,  description);
    }

    public static void savingsCategoryInDataBase(SavingsCategory savingsCategory1) throws SQLException{
        DatabaseManagement.savingsCategoryInsert(
                savingsCategory1.getCategory_id(),
                savingsCategory1.getSave_id(),
                savingsCategory1.getId(),
                savingsCategory1.getName(),
                savingsCategory1.getPercent(),
                savingsCategory1.getDescription());
    }

    public static void savingsCategoryCreate(String id, String save_id,  String name, double percent, String description) throws SQLException{

        if (name == null || name.trim().isEmpty()
                || id == null || id.trim().isEmpty()
                || save_id == null || save_id.trim().isEmpty()
                || percent < 0 || percent >= 100
                || !Double.isFinite(percent)) {
            throw new IllegalArgumentException("Invalid savingsCategory parameters");
        }

        name = name.trim().toLowerCase();

        SavingsCategory savingsCategory1 = savingsCategoryClassCreate(id, save_id,  name, percent/100 , description);
        savingsCategoryInDataBase(savingsCategory1);
    }

    public static ArrayList<SavingsCategory> savingsCategorySearch (String user_id, String save_id) throws SQLException{
        return DatabaseManagement.savingsCategorySearch(user_id,save_id);
    }



    public static double abc (double a,double b){
        return a - b;
    }


}

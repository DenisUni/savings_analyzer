package org.application;

import org.domain.IncomeSource;
import org.domain.SaveProfile;
import org.domain.SavingsCategory;
import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.logging.log4j.*;
import java.util.NoSuchElementException;

public class UserManager {
    //___________________________User___________________________________________________

    private static final Logger log = LogManager.getLogger(UserManager.class.getName());


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

        if(name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()){
            log.debug("userCreate() Invalid name: {} or email: {}",name,email);
            throw new IllegalArgumentException("Invalid name or email");}

        name = name.trim().toLowerCase();
        email = email.trim().toLowerCase();


        if (DatabaseManagement.userExist(name, email)){
            log.debug("userCreate() User {} already exist", name);
            throw new IllegalArgumentException("User already exist");}

        User user = userClassCreate(name, email);

        userSaveDatabase(user);

    }

    public static User userDataSearch(String name,String email) throws SQLException {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            log.debug("userDataSearch() Invalid name: {} or email: {}",name,email);
            throw new IllegalArgumentException("Invalid name or email");
        }


        name = name.trim().toLowerCase();
        email = email.trim().toLowerCase();


        ArrayList<User> usersList =  DatabaseManagement.userSearch(name,email);

        if(usersList.isEmpty() ) {
            log.debug("userDataSearch() User {} isn´t found", name);
            throw new NoSuchElementException("User not found!");
        }
        if(usersList.size() > 1){
            log.error("userDataSearch() User {} and User {} are duplicate", usersList.get(0).getId(), usersList.get(1).getId());
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
                || !Double.isFinite(saving_rate)){
            log.debug("saveProfile() Invalid name: {} or email: {}",name, saving_rate);
            throw new IllegalArgumentException("Invalid SaveProfile parameters!");}

        name = name.trim().toLowerCase();

        SaveProfile saveProfileUser = saveProfileClassCreate( user_id,  name,  saving_rate/100);
        saveProfileInDatabaseCreate(saveProfileUser);
    }

    public static ArrayList<SaveProfile> saveProfilesSearch(String user_id) throws SQLException{
        return DatabaseManagement.saveProfileSearch(user_id);
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
                !Double.isFinite(amount)){
            log.debug("incomeSourceCreate() Invalid name: {} or amount: {} or userId: {} or saveID {}",name, amount,user_id, save_id);
            throw new IllegalArgumentException("Invalid incomeSource parameters");}

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
                !Double.isFinite(amount)){
            log.debug("incomeSourceUpdate() Invalid name: {} or amount: {} or  saveID {}",name, amount, save_id);
            throw new IllegalArgumentException("Invalid incomeSource parameters");}

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
            log.debug("savingsCategory() Invalid name: {} or ID: {} or percent: {} or  saveID {}",name, id, percent, save_id);
            throw new IllegalArgumentException("Invalid savingsCategory parameters");
        }

        name = name.trim().toLowerCase();

        SavingsCategory savingsCategory1 = savingsCategoryClassCreate(id, save_id,  name, percent/100 , description);
        savingsCategoryInDataBase(savingsCategory1);
    }

    public static ArrayList<SavingsCategory> savingsCategorySearch (String user_id, String save_id) throws SQLException{
        return DatabaseManagement.savingsCategorySearch(user_id,save_id);
    }

}

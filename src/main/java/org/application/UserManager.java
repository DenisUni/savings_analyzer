package org.application;

import org.domain.IncomeSource;
import org.domain.SaveProfile;
import org.domain.SavingsCategory;
import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.util.ArrayList;

public class UserManager {
    //User
        //Create class User
    public static User userClassCreate (String name, String email){
        User user1 = new User(name, email);  //User class is success created and return true
        return user1;
    }
        //Create object User in Database
    public static void userSaveDatabase(User user1){
        DatabaseManagement.createTables();
        DatabaseManagement.userInsert(user1.getId(), user1.getName(), user1.getEmail()); //Object created in DB
    }
        //Save User in DB and create class
    public static void userSave(String name, String email){
        if(!DatabaseManagement.userExist(name,email)) {
            User user1 = userClassCreate(name, email);
            userSaveDatabase(user1);
            return;
        }
        System.out.println("User already exist !!!");

    }

    public static User userDataSearch(String name,String email) {
        try{

            ArrayList<User> usersList =  DatabaseManagement.userSearch(name,email);

            if(usersList.size() != 0){
                if(usersList.size() > 1){
                    System.out.println("ERROR: Duplicate exist");
                    return null;
                }
                String user_id = usersList.get(0).getId();
                String user_name = usersList.get(0).getName();
                String user_email = usersList.get(0).getEmail();

                User user1 = new User(user_id,user_name,user_email);
                return user1;
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage() );
        }
        return null;
    }

    //SaveProfile
    public static SaveProfile saveProfileClassCreate(String user_id, String name, Double saving_rate){
        SaveProfile saveProfileUser1 = new SaveProfile(user_id,name,saving_rate);
        return saveProfileUser1;
    }

    public static void saveProfileInDatabaseCreate(SaveProfile saveProfile){
        DatabaseManagement.saveProfileInsert(
                saveProfile.getUser_id(), saveProfile.getSave_id(), saveProfile.getName(),saveProfile.getSaving_rate());
    }

    public static void saveProfileCreate(String user_id, String name, Double saving_rate){
        SaveProfile saveProfileUser1 = saveProfileClassCreate( user_id,  name,  saving_rate/100);

        saveProfileInDatabaseCreate(saveProfileUser1);
    }

    public static ArrayList<SaveProfile> saveProfilesSearch(String user_id){
        return DatabaseManagement.saveProfileSearch(user_id);
    }


    //IncomeSource
    public static IncomeSource incomeSourceClassCreate(String user_id, String save_id,
                                            String name, Double amount , String description, Boolean isActive){
        IncomeSource incomeSource1 = new IncomeSource(save_id, user_id, name, amount,description, isActive );
        return incomeSource1;



    }
    public static void incomeSourceInDataBase(IncomeSource incomeSource1){
        DatabaseManagement.incomeSourceInsert(
                incomeSource1.getIncome_id(),
                incomeSource1.getId(),
                incomeSource1.getSave_id(),
                incomeSource1.getName(),
                incomeSource1.getAmount(),
                incomeSource1.getDescription(),
                incomeSource1.isActive());
    }
    public static void incomeSourceCreate(String user_id, String save_id,String name, Double amount , String description, Boolean isActive){
        IncomeSource incomeSource1 = incomeSourceClassCreate(user_id,save_id,name,amount,description,isActive);
        incomeSourceInDataBase(incomeSource1);
    }
    public static ArrayList<IncomeSource> incomeSourcesSearch(String user_id, String save_id){
        return DatabaseManagement.incomeSourceSearch(user_id,save_id);
    }

    //SavingsCategory
    public static SavingsCategory savingsCategoryClassCreate(String save_id, String id, String name, double percent, String description) {
        SavingsCategory savingsCategory1 = new SavingsCategory( save_id,  id,  name,  percent,  description);
        return savingsCategory1;
    }
    public static void savingsCategoryInDataBase(SavingsCategory savingsCategory1){
        DatabaseManagement.savingsCategoryInsert(
                savingsCategory1.getCategory_id(),
                savingsCategory1.getSave_id(),
                savingsCategory1.getId(),
                savingsCategory1.getName(),
                savingsCategory1.getPercent(),
                savingsCategory1.getDescription());
    }
    public static void savingsCategoryCreate(String save_id, String id,  String name, double percent, String description) {
        SavingsCategory savingsCategory1 = savingsCategoryClassCreate(save_id, id,  name, percent/100 , description);
        savingsCategoryInDataBase(savingsCategory1);
    }
    public static ArrayList<SavingsCategory> savingsCategorySearch (String user_id, String save_id) {
        return DatabaseManagement.savingsCategorySearch(user_id,save_id);
    }













}

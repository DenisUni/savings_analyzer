package org.application;

import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.util.ArrayList;

public class UserManager {

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

            ArrayList<ArrayList<String>> usersList =  DatabaseManagement.userSearch(name,email);

            if(usersList.get(0).size() != 0){
                if(usersList.get(0).size() > 1){
                    System.out.println("ERROR: Duplicate exist");
                    return null;
                }
                String user_id = usersList.get(0).get(0);
                String user_name = usersList.get(1).get(0);
                String user_email = usersList.get(2).get(0);

                User user1 = new User(user_id,user_name,user_email);
                return user1;
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage() );
        }
        return null;
    }











}

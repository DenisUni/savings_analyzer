package org.application;

import org.domain.User;
import org.jetbrains.annotations.NotNull;

public class UserRegistration {

    public static User userClassCreate (String name, String email){

        try {
            return new User(name, email);  //User Class is success created and return true

        }
        catch(Exception e){
            System.out.println(e.getMessage()); //User Class is not created and return false
            return null ;
        }

    }









}

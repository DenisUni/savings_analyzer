package org.application;

import org.infrastructure.database.DatabaseManagement;
import org.presentation.CLI;

//https://martinfowler.com/bliki/PresentationDomainDataLayering.html

public class Main {
    public static void main(String[] args) {
        String user_id;
        String profile_id = "Login again or Problem";

        user_id = CLI.startPage();

        profile_id = CLI.profilePage(user_id);
        if(profile_id == null){
            profile_id = "Login again or Problem";
            System.out.println("END! " + profile_id  + " And "+ user_id);
            return;

        }

        System.out.println("END! " + profile_id  + " And "+ user_id);
    }
}
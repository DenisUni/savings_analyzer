package org.application;

import org.infrastructure.database.DatabaseManagement;

import org.presentation.CLI;

import static org.presentation.CLI.incomeSourcePage;
//https://martinfowler.com/bliki/PresentationDomainDataLayering.html

public class Main {
    public static void main(String[] args) {
        String user_id;
        String save_id = "Login again or Problem";

        user_id = CLI.startPage();

        save_id = CLI.profilePage(user_id);
        if(save_id == null){
            save_id = "Login again or Problem";
            System.out.println("END! " + save_id  + " And "+ user_id);
            return;
        }

        incomeSourcePage(user_id,save_id);



        System.out.println("END! " + save_id  + " And "+ user_id);
    }
}
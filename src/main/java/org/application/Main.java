package org.application;

import org.presentation.CLI;
//https://martinfowler.com/bliki/PresentationDomainDataLayering.html

public class Main {
    public static void main(String[] args) {
        String user_id;
        String save_id;

        user_id = CLI.startPage();

        save_id = CLI.profilePage(user_id);
        if(save_id == null){
            save_id = "Login again or Problem";
            System.out.println("END! " + save_id  + " And "+ user_id);
            return;
        }
/*
        CLI.incomeSourcePage(user_id,save_id);
        CLI.savingsCategoryPage(user_id,save_id);
        System.out.println("Total income: " + total_income_calculation(user_id,save_id);

        */

        CLI.basicAnalysis(user_id, save_id);

        System.out.println("END! Save_id: "+ save_id  + " And user_id "+ user_id);
    }


}
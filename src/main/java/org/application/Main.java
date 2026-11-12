package org.application;

import org.infrastructure.database.DatabaseManagement;

import org.presentation.CLI;

import static org.application.UserManager.target_savings_calculation;
import static org.application.UserManager.total_income_calculation;
import static org.presentation.CLI.incomeSourcePage;
import static org.presentation.CLI.savingsCategoryPage;
//https://martinfowler.com/bliki/PresentationDomainDataLayering.html

public class Main {
    public static void main(String[] args) {
        /*String user_id;
        String save_id;

        user_id = CLI.startPage();

        save_id = CLI.profilePage(user_id);
        if(save_id == null){
            save_id = "Login again or Problem";
            System.out.println("END! " + save_id  + " And "+ user_id);
            return;
        }

        incomeSourcePage(user_id,save_id);
        savingsCategoryPage(user_id,save_id);
        System.out.println("Total income: " + total_income_calculation(user_id,save_id));

        double target_savings = target_savings_calculation(user_id,save_id);
        System.out.println("Target savings: " + target_savings);






        System.out.println("END! Save_id: "+ save_id  + " And user_id "+ user_id);*/

        CSVManager csvManager = new CSVManager();
        csvManager.csvReaderFunc();
    }


}
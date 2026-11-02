package org.presentation;

import org.application.UserManager;
import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Scanner;

public class CLI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void userDataInput(){

        System.out.println("--- User Create ----- ");

        String name;
        String email;
        try{
            System.out.println("Your name:");
            name = scanner.nextLine();
            System.out.println("Your email:");
            email = scanner.nextLine();
            UserManager.userSave(name,email);

        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }

    }

    public static void userLogin() {
        System.out.println("--- Login Page ----- ");
        try{
            System.out.println("Your name:");
            String name = scanner.nextLine();
            System.out.println("Your email:");
            String email = scanner.nextLine();

            if(UserManager.userDataSearch(name,email) != null){
                System.out.println("Find!");
                User user1 = UserManager.userDataSearch(name,email); //change output on a String (toSting) for CLI not an Object
                System.out.println(user1.toString());
                }
                else System.out.println("Not Find");

        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage() );
        }
    }

        public static void startPage(){
        System.out.println("--- Start Page ----- ");
        String answer;
        try {
            System.out.println("Do you want registration (r) or login (l) ? ");
            answer = scanner.nextLine();

            if(Objects.equals(answer, "r")) userDataInput();
            else if (Objects.equals(answer, "l")) userLogin();

        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }



    }
}

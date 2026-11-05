package org.presentation;

import org.application.UserManager;
import org.domain.IncomeSource;
import org.domain.SaveProfile;
import org.domain.User;
import org.infrastructure.database.DatabaseManagement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Scanner;

import static org.application.UserManager.incomeSourcesSearch;
import static org.application.UserManager.saveProfilesSearch;

public class CLI {

    private static final Scanner scanner = new Scanner(System.in);
    //User
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

    public static String userLogin() {
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
                return user1.getId();
                }
                else System.out.println("Not Find");

        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage() );
        }
        return null;
    }

    public static String startPage(){
        System.out.println("--- Start Page ----- ");
        String answer;
        try {
            System.out.println("Do you want registration (r) or login (l) ? ");
            answer = scanner.nextLine();

            if(Objects.equals(answer, "r")) userDataInput();
            else if (Objects.equals(answer, "l")) {
                String user_id = userLogin();
                return user_id;
            }

        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        return null;
    }

    //SaveProfile
    public static void profileDataInput(String user_id){

        System.out.println("--- Profile Create ----- ");

        String save_profile_name;
        String saves_rate_tmp;
        double saves_rate;

        try{
            System.out.println("Profile name:");
            save_profile_name = scanner.nextLine();
            System.out.println("Saves rate:");
            saves_rate_tmp = scanner.nextLine();
            saves_rate = Double.parseDouble(saves_rate_tmp);
            UserManager.saveProfileCreate(user_id,save_profile_name,saves_rate );

        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }

    }

    public static String profilePage(String user_id){
        System.out.println("--- Profile Page ----- ");
        String answer;
        try {
            System.out.println("Do you want registration (r) or select (l) your 'Save Profile'? ");
            answer = scanner.nextLine();

            //User can many Profiles have

            if(answer.equals("r")) profileDataInput(user_id);

            else if (answer.equals("l")) {
                ArrayList<SaveProfile> profilesList = saveProfilesSearch(user_id);

                if(profilesList.size() > 1){
                    for(int i = 0; i < profilesList.size(); i++)
                    {
                        System.out.println( i +" : " + profilesList.get(i).toString());
                    }
                    System.out.println("Select profile: (1 or 2 or ...)");
                    int index = Integer.parseInt(scanner.nextLine());

                    return profilesList.get(index).getSave_id();
                } else if (profilesList.size() == 1) {
                    return profilesList.get(0).getSave_id();
                }
                System.out.println("(profilePage) ERROR ");

            }

        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        return null;
    }

    //IncomeSource
    public static void incomeSourceDataInput(String user_id, String save_id){
        System.out.println("--- IncomeSource Create ----- ");

        String name;
        double amount;
        String description;
        boolean isActive;

        try{
            System.out.println("IncomeSource name:");
            name = scanner.nextLine();
            System.out.println("Amount:");
            String amount_temp = scanner.nextLine();
            amount = Double.parseDouble(amount_temp);
            System.out.println("Description:");
            description = scanner.nextLine();
            System.out.println("Is Active (y) or Not Active (n) now :");

            String isActive_tmp = scanner.nextLine();
            if(isActive_tmp.equals("y")) isActive = true;
            else if (isActive_tmp.equals("n")) isActive = false;
            else{
                System.out.println("Not correct input");
                return;
            }

            UserManager.incomeSourceCreate(user_id ,save_id ,name ,amount ,description ,isActive);

        }
        catch (Exception e){
            System.out.println("(incomeSourceDataInput) Error: "+ e.getMessage());
        }

    }

    public static void incomeSourcePage(String user_id, String save_id){
        System.out.println("--- IncomeSource Page----- ");
        String answer;
        try {
            System.out.println("Do you want create (c), show (s), change (c) ( come soon! ) or delete ( come soon! ) (d) your 'IncomeSource'? ");
            answer = scanner.nextLine();

            //User can many Profiles have

            if(answer.equals("c")) incomeSourceDataInput(user_id, save_id);

            else if (answer.equals("s")) {
                ArrayList<IncomeSource> incomeSourcesList = incomeSourcesSearch(user_id,save_id);

                if(incomeSourcesList.size() > 1){

                    for(int i = 0; i < incomeSourcesList.size(); i++)
                    {
                        System.out.println( i +" : " + incomeSourcesList.get(i).toString());
                    }

                } else if (incomeSourcesList.size() == 1) {
                    System.out.println( incomeSourcesList.get(0).toString() );
                }
                else System.out.println("ERROR (incomeSourcePage) if-else or list is empty");
            }

        }
        catch (Exception e){
            System.out.println("(incomeSourcePage) Error: "+ e.getMessage());
        }

    }



}

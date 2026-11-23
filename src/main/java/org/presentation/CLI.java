package org.presentation;

import org.application.AnalysisManager;
import org.application.UserManager;
import org.domain.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static org.application.UserManager.incomeSourcesSearch;
import static org.application.UserManager.saveProfilesSearch;
import static org.presentation.Style.*;



public class CLI {


    private static final Scanner scanner = new Scanner(System.in);


    //___________________User______________________________________
    public static void userDataInput() {

        Style.header("User Create ");


        String name;
        String email;

        try {
            System.out.println("Your name:");
            name = scanner.nextLine();
            separator();

            System.out.println("Your email:");
            email = scanner.nextLine();
            UserManager.userCreate(name, email);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String userLogin() {
        Style.header("Login Page");

        try {
            System.out.println("Your name:");
            String name = scanner.nextLine();
            separator();
            System.out.println("Your email:");
            String email = scanner.nextLine();

            if (UserManager.userDataSearch(name, email) != null) {
                success("Find!");
                User user1 = UserManager.userDataSearch(name, email); //change output on a String (toSting) for CLI not an Object
                System.out.println(user1.toString());   //Logger
                return user1.getId();
            } else error("Not Find");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    public static String startPage() {
        logo();
        Style.header("Start Page");
        String answer;

        try {
            //System.out.println("Do you want registration (r) or login (l) ? ");

            menuItem("0", "Create a new account");
            menuItem("1", "Login");
            menuItem("2", "Exit (coming soon)");
            separator();

            answer = scanner.nextLine();


            if (Objects.equals(answer, "0")) userDataInput();
            else if (Objects.equals(answer, "1")) {
                String user_id = userLogin();
                return user_id;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    //___________________SaveProfile______________________________________________
    public static void profileDataInput(String user_id) {
        Style.header("Profile Create");

        String save_profile_name;
        String saves_rate_tmp;
        double saves_rate;

        try {
            System.out.println("Profile name:");
            save_profile_name = scanner.nextLine();
            separator();
            System.out.println("Saves rate:");
            saves_rate_tmp = scanner.nextLine();
            separator();
            saves_rate = Double.parseDouble(saves_rate_tmp);
            UserManager.saveProfileCreate(user_id, save_profile_name, saves_rate);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static String profilePage(String user_id) {
        Style.header("Profile Page");
        String answer;

        try {
            //System.out.println("Do you want registration (r) or select (l) your 'Save Profile'? ");
            menuItem("0", "Create a new profile");
            menuItem("1", "Select a exist profile");
            menuItem("2", "Exit (coming soon)");
            separator();

            answer = scanner.nextLine();

            //User can many Profiles have
            if (answer.equals("0")) profileDataInput(user_id);

            else if (answer.equals("1   ")) {
                ArrayList<SaveProfile> profilesList = saveProfilesSearch(user_id);

                if (profilesList.size() > 1) {
                    for (int i = 0; i < profilesList.size(); i++) {
                        menuItem( ""+i+"" , "Name: " +  profilesList.get(i).getName() + " | Rate: " + profilesList.get(i).getSaving_rate()*100+"%") ;
                    }
                    System.out.println("Select profile: (0 or 1 or ...)");
                    int index = Integer.parseInt(scanner.nextLine());

                    return profilesList.get(index).getSave_id();
                } else if (profilesList.size() == 1) {
                    return profilesList.get(0).getSave_id();
                }
                System.out.println("(profilePage) ERROR "); // Logger

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    //___________________IncomeSource______________________________________________
    public static IncomeSourceInputElement incomeSourceInput() {
        String name;
        double amount;
        String description;
        boolean isActive;
        try {
            System.out.println("IncomeSource name:");
            name = scanner.nextLine();
            System.out.println("Amount:");
            String amount_temp = scanner.nextLine();
            amount = Double.parseDouble(amount_temp);
            System.out.println("Description:");
            description = scanner.nextLine();

            menuItem("0", "Is Active");
            menuItem("1", "Not Active");

            //System.out.println("Is Active (y) or Not Active (n) now :");

            String isActive_tmp = scanner.nextLine();
            if (isActive_tmp.equals("0")) isActive = true;
            else if (isActive_tmp.equals("1")) isActive = false;
            else {
                error("Not correct input");

                return null;
            }
            return new IncomeSourceInputElement(name, amount, description, isActive);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;

    }

    public static void incomeSourceCreate(String user_id, String save_id) {
        Style.header("IncomeSource Create");

        IncomeSourceInputElement incomeSourceInputElement = incomeSourceInput();

        String name = incomeSourceInputElement.getName();
        Double amount = incomeSourceInputElement.getAmount();
        String description = incomeSourceInputElement.getDescription();
        Boolean isActive = incomeSourceInputElement.isActive();

        UserManager.incomeSourceCreate(user_id, save_id, name, amount, description, isActive);

        success("(incomeSourceCreate) created! ");
    }

    public static void incomeSourcesShow(String user_id, String save_id) {
        ArrayList<IncomeSource> incomeSourcesList = incomeSourcesSearch(user_id, save_id);

        if (incomeSourcesList.size() > 1) {

            for (int i = 0; i < incomeSourcesList.size(); i++) {
                menuItem(""+i + "" ,   incomeSourcesList.get(i).getName()+ " "
                        +incomeSourcesList.get(i).getAmount() + " Euro "
                        + incomeSourcesList.get(i).getDescription()+" "
                + incomeSourcesList.get(i).isActive() + " ");
            }

        } else if (incomeSourcesList.size() == 1) {
            System.out.println(incomeSourcesList.get(0).toString());
            menuItem("0" ,   " Name: " + incomeSourcesList.get(0).getName()+
                    "Amount: " +incomeSourcesList.get(0).getAmount() + "Euro " +
                    "Description: " + incomeSourcesList.get(0).getDescription()+" " +
                    "isActive: " + incomeSourcesList.get(0).isActive() + " ");

        } else error("ERROR (incomeSourcePage) if-else or list is empty");
    }

    public static void incomeSourcePage(String user_id, String save_id) {
        Style.header(" IncomeSource Page ");
        String answer;
        try {

            menuItem("0", "create");
            menuItem("1", "show");
            menuItem("2", "update");
            menuItem("3", "delete ( come soon! )");

            //System.out.println("Do you want create (c), show (s), Update (u) ( come soon! ) or delete ( come soon! ) (d) your 'IncomeSource'? ");
            answer = scanner.nextLine();

            //User can many incomeSources have
            if (answer.equals("0")) incomeSourceCreate(user_id, save_id);

            else if (answer.equals("1")) {
                incomeSourcesShow(user_id, save_id);
            } else if (answer.equals("2")) {
                ArrayList<IncomeSource> incomeSourcesList = incomeSourcesSearch(user_id, save_id);

                incomeSourcesShow(user_id, save_id);

                System.out.println("Which source should be updated (0 or 1 or ...)?");
                answer = scanner.nextLine();
                //Input Validation!
                int answer_int = Integer.parseInt(answer);

                IncomeSource incomeSourceToUpdate = incomeSourcesList.get(answer_int);
                IncomeSourceInputElement incomeSourceInputElement = incomeSourceInput();

                String name = incomeSourceInputElement.getName();
                Double amount = incomeSourceInputElement.getAmount();
                String description = incomeSourceInputElement.getDescription();
                Boolean isActive = incomeSourceInputElement.isActive();

                UserManager.incomeSourceUpdate(
                        name, amount, description, isActive, user_id, save_id, incomeSourceToUpdate.getIncome_id());
                success("Updated !");
            }
        } catch (Exception e) {
            System.out.println("(incomeSourcePage) Error: " + e.getMessage());  //Logger
        }
    }

    //___________________SavingsCategory______________________________________________________________
    public static void savingsCategoryDataInput(String user_id, String save_id) {
        Style.header(" SavingsCategory Create");

        String name;
        double percent;
        String description;

        try {
            System.out.println("SavingsCategory name:");
            name = scanner.nextLine();
            separator();
            System.out.println("Percent:");
            String percent_temp = scanner.nextLine();
            separator();
            percent = Double.parseDouble(percent_temp);
            System.out.println("Description:");
            description = scanner.nextLine();

            UserManager.savingsCategoryCreate(user_id, save_id, name, percent, description);
        } catch (Exception e) {
            System.out.println("(SavingsCategoryDataInput) Error: " + e.getMessage());
        }
    }

    public static void savingsCategoryPage(String user_id, String save_id) {
        Style.header("SavingsCategory Page ");
        String answer;

        try {
            //System.out.println("Do you want create (c), show (s), change (c) ( come soon! ) or delete ( come soon! ) (d) your 'SavingsCategory'? ");
            menuItem("0", "create");
            menuItem("1", "show");
            menuItem("2", "update ( come soon! )");
            menuItem("3", "delete ( come soon! )");

            answer = scanner.nextLine();

            //User can many SavingsCategories have
            if (answer.equals("0")) savingsCategoryDataInput(user_id, save_id);

            else if (answer.equals("1")) {
                ArrayList<SavingsCategory> savingsCategoriesList = UserManager.savingsCategorySearch(user_id, save_id);

                if (savingsCategoriesList.size() > 1) {

                    for (int i = 0; i < savingsCategoriesList.size(); i++) {
                        //System.out.println(i + " : " + savingsCategoriesList.get(i).toString());
                        menuItem("" + i + "" ,
                                " Name: " + savingsCategoriesList.get(i).getName() +
                                " Present: " + savingsCategoriesList.get(i).getPercent() + " %" +
                                " Description: " + savingsCategoriesList.get(i).getDescription() + "." );
                    }

                } else if (savingsCategoriesList.size() == 1) {
                    System.out.println(savingsCategoriesList.get(0).toString());
                } else error("ERROR (savingsCategoryPage) if-else or list is empty");
            }

        } catch (Exception e) {
            System.out.println("(savingsCategoryPage) Error: " + e.getMessage());
        }
    }

    //___________________Analysis_______________________________________
    public static void basicAnalysis(String user_id, String save_id) {
        ArrayList<Double> list = AnalysisManager.basicCalculation();
        double total_income = list.get(2);
        double predefinedTotalIncome = UserManager.total_income_calculation(user_id, save_id);

        int resultIncomeCheck = AnalysisManager.incomeCheck(user_id, save_id);

        if (resultIncomeCheck == 1 || resultIncomeCheck == 2) {
            System.out.println("Please correct your predefined Incomes.");
            System.out.println("predefined: " + predefinedTotalIncome + " and your income: " + total_income);
            //a function (page) to correct

        } else if (resultIncomeCheck == -1) {
            System.out.println("ERROR: Your income is less than zero, zero or null");
            System.out.println("predefined: " + predefinedTotalIncome + " and your income:" + total_income);

        } else if (resultIncomeCheck == 0) {
            System.out.println("Your income remains the same as the amount you predefined!");
            System.out.println("predefined: " + predefinedTotalIncome + " and your income:" + total_income);

            ArrayList<DistributionListElement> distributionListElementList = AnalysisManager.distributionOfSavings(user_id, save_id);

            for (int i = 0; i < distributionListElementList.size(); i++) {
                System.out.println(distributionListElementList.get(i).toString());
            }

        } else {
            System.out.println("(basicAnalysis) ERROR");
            //a function (page) to fix
            return;
        }
    }
}
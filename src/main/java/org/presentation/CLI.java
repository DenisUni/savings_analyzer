package org.presentation;

import org.application.AnalysisManager;
import org.application.UserManager;
import org.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import static org.application.UserManager.incomeSourcesSearch;
import static org.application.UserManager.saveProfilesSearch;
import static org.presentation.Style.*;



public class CLI {

    private static final Scanner scanner = new Scanner(System.in);

    //___________________User______________________________________
    public static void userDataInput() throws SQLException {

        Style.header("User Create ");

        System.out.println("Your name:");
        String name = scanner.nextLine();
        separator();

        System.out.println("Your email:");
        String email = scanner.nextLine();
        UserManager.userCreate(name, email);

    }

    public static String userLogin() throws SQLException {

        Style.header("Login Page");

        System.out.println("Your name:");
        String name = scanner.nextLine();
        separator();

        System.out.println("Your email:");
        String email = scanner.nextLine();

        User user1 = UserManager.userDataSearch(name, email); //change output on a String (toSting) for CLI not an Object
        success("Find!");

        //System.out.println(user1.toString());   //Logger
        return user1.getId();

    }

    public static String startPage() throws SQLException {

        while(true){
            try{
                logo();
                Style.header("Start Page");
                String answer;

                menuItem("0", "Create a new account");
                menuItem("1", "Login");
                menuItem("2", "Exit (coming soon)");
                separator();

                answer = scanner.nextLine().trim();


                if (Objects.equals(answer, "0")) {
                    userDataInput();
                    return userLogin();
                } else if (Objects.equals(answer, "1")) {
                    return userLogin();
                }
                throw new IllegalArgumentException("Invalid input");

            }
            catch (IllegalArgumentException e){
                System.out.println("Input Error: " + e.getMessage());
            }
            catch (NoSuchElementException e){
                System.out.println("Database Error: " + e.getMessage());
            }
        }

    }

    //___________________SaveProfile______________________________________________
    public static void profileDataInput(String user_id) throws SQLException {
        Style.header("Profile Create");


        System.out.println("Profile name:");
        String save_profile_name = scanner.nextLine();
        separator();
        System.out.println("Saves rate:");
        String saves_rate_tmp = scanner.nextLine();
        separator();
        double saves_rate;
        try{
            saves_rate = Double.parseDouble(saves_rate_tmp);

        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Please enter a valid number");
        }
        if (saves_rate <= 0 || saves_rate > 100) {
            throw new IllegalArgumentException("Saving rate must be between 1 and 100");
        }

        UserManager.saveProfileCreate(user_id, save_profile_name, saves_rate);

    }

    public static String profilePage(String user_id) throws SQLException {

        while (true) {

            try {
                Style.header("Profile Page");
                String answer;

                //System.out.println("Do you want registration (r) or select (l) your 'Save Profile'? ");
                menuItem("0", "Create a new profile");
                menuItem("1", "Select an exist profile");
                menuItem("2", "Exit (coming soon)");
                separator();

                answer = scanner.nextLine().trim();

                //User can many Profiles have
                if (answer.equals("0")) {
                    profileDataInput(user_id);
                    ArrayList<SaveProfile> profilesList = saveProfilesSearch(user_id);

                    if (profilesList.isEmpty()) {
                        throw new IllegalStateException("Profile could not be created.");
                    }
                    return profilesList.get(0).getSave_id();

                } else if (answer.equals("1")) {
                    ArrayList<SaveProfile> profilesList = saveProfilesSearch(user_id);

                    if (profilesList.isEmpty()) {
                        throw new IllegalStateException("No profiles available. Create a profile first.");
                    }


                    for (int i = 0; i < profilesList.size(); i++)
                        menuItem(String.valueOf(i),
                                "Name: " + profilesList.get(i).getName()
                                        + " | Rate: "
                                        + profilesList.get(i).getSaving_rate()*100
                                        + "%"
                        );

                    System.out.println("Select profile: (0 or 1 or ...)");

                    int index;

                    try {
                        index = Integer.parseInt(scanner.nextLine().trim());

                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Please enter a valid number.");
                    }

                    if (index < 0 || index >= profilesList.size()) {
                        throw new IllegalArgumentException("Profile index does not exist.");
                    }
                    return profilesList.get(index).getSave_id();
                }



                throw new IllegalArgumentException("Invalid input");
            }
            catch (IllegalArgumentException e){
                System.out.println("Input Error: " + e.getMessage());
            }
            catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //___________________IncomeSource______________________________________________
    public static IncomeSourceInputElement incomeSourceInput() {

        boolean isActive;

        System.out.println("IncomeSource name:");
        String name = scanner.nextLine();

        System.out.println("Amount:");
        String amount_temp = scanner.nextLine();
        double amount;

        try {
            amount = Double.parseDouble(amount_temp);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Please enter a valid number",e);
        }
        if(amount <= 0 || !Double.isFinite(amount))
            throw new IllegalArgumentException("The amount must be positive.");

        System.out.println("Description:");
        String description = scanner.nextLine();

        menuItem("0", "Is Active");
        menuItem("1", "Not Active");

        //System.out.println("Is Active (y) or Not Active (n) now :");

        String isActive_tmp = scanner.nextLine();
        if (isActive_tmp.equals("0"))
            isActive = true;
        else if (isActive_tmp.equals("1"))
            isActive = false;
        else
            throw new IllegalArgumentException("Incorrect input");

        return new IncomeSourceInputElement(name, amount, description, isActive);

    }

    public static void incomeSourceCreate(String user_id, String save_id) throws SQLException {
        Style.header("IncomeSource Create");

        IncomeSourceInputElement incomeSourceInputElement = incomeSourceInput();

        String name = incomeSourceInputElement.getName();
        double amount = incomeSourceInputElement.getAmount();
        String description = incomeSourceInputElement.getDescription();
        Boolean isActive = incomeSourceInputElement.isActive();

        UserManager.incomeSourceCreate(user_id, save_id, name, amount, description, isActive);

        success("(incomeSourceCreate) created! ");
    }

    public static void incomeSourcesShow(String user_id, String save_id) throws SQLException {

        ArrayList<IncomeSource> incomeSourcesList = incomeSourcesSearch(user_id, save_id);

        if(incomeSourcesList.isEmpty())
            throw new NoSuchElementException("No profiles available. Create a profile first.");


        for (int i = 0; i < incomeSourcesList.size(); i++) {
            menuItem(i + " ", incomeSourcesList.get(i).getName() + " "
                    + incomeSourcesList.get(i).getAmount() + " Euro "
                    + incomeSourcesList.get(i).getDescription() + " "
                    + incomeSourcesList.get(i).isActive() + " ");
        }
    }

    public static void incomeSourcePage(String user_id, String save_id) throws SQLException {

        while(true) {

            try {
                Style.header("IncomeSource Page ");


                menuItem("0", "create");
                menuItem("1", "show");
                menuItem("2", "update");
                menuItem("3", "delete ( come soon! )");

                String answer;
                answer = scanner.nextLine();

                //User can many incomeSources have
                if (answer.equals("0"))
                    incomeSourceCreate(user_id, save_id);

                else if (answer.equals("1")){
                    incomeSourcesShow(user_id, save_id);
                    break;
                }

                else if (answer.equals("2")) {

                    ArrayList<IncomeSource> incomeSourcesList = incomeSourcesSearch(user_id, save_id);

                    incomeSourcesShow(user_id, save_id);

                    System.out.println("Which source should be updated (0 or 1 or ...)?");
                    answer = scanner.nextLine();

                    int index;

                    try {
                        index = Integer.parseInt(answer);
                    }
                    catch (NumberFormatException e){
                        throw new IllegalArgumentException("Please enter a valid number");
                    }

                    if(index < 0 || index >= incomeSourcesList.size()){
                        throw new IllegalArgumentException("Incorrect index.");
                    }

                    IncomeSource incomeSourceToUpdate = incomeSourcesList.get(index);

                    IncomeSourceInputElement incomeSourceInputElement = incomeSourceInput();

                    String name = incomeSourceInputElement.getName();
                    double amount = incomeSourceInputElement.getAmount();
                    String description = incomeSourceInputElement.getDescription();
                    Boolean isActive = incomeSourceInputElement.isActive();

                    UserManager.incomeSourceUpdate(
                            name, amount, description, isActive, user_id, save_id, incomeSourceToUpdate.getIncome_id());
                    success("Updated !");

                } else
                    throw new IllegalArgumentException("Incorrect input");
            }
            catch (NoSuchElementException e ){
                System.out.println("Error:" + e.getMessage());
            }
            catch(IllegalArgumentException e){
                System.out.println("Input Error: " + e.getMessage());
            }

        }
    }

    //___________________SavingsCategory______________________________________________________________
    public static void savingsCategoryDataInput(String user_id, String save_id) throws SQLException {
        Style.header(" SavingsCategory Create");

        double percent;

        System.out.println("SavingsCategory name:");
        String name = scanner.nextLine();
        separator();
        System.out.println("Percent:");
        String percent_temp = scanner.nextLine();
        separator();

        try{
            percent = Double.parseDouble(percent_temp);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Please enter a valid number ",e );
        }
        if (percent <= 0 || percent > 100 || !Double.isFinite(percent)) {
            throw new IllegalArgumentException("Percent must be greater than 0 and at most 100");
        }            System.out.println("Description:");
        String description = scanner.nextLine();

        UserManager.savingsCategoryCreate(user_id, save_id, name, percent, description);

    }

    public static void savingsCategoryPage(String user_id, String save_id) throws SQLException {

        while(true) {

            try {
                Style.header("SavingsCategory Page ");
                String answer;

                menuItem("0", "create");
                menuItem("1", "show");
                menuItem("2", "update ( come soon! )");
                menuItem("3", "delete ( come soon! )");

                answer = scanner.nextLine();

                //User can have many SavingsCategories
                if (answer.equals("0"))
                    savingsCategoryDataInput(user_id, save_id);

                else if (answer.equals("1")) {
                    ArrayList<SavingsCategory> savingsCategoriesList = UserManager.savingsCategorySearch(user_id, save_id);


                    if(savingsCategoriesList.isEmpty())
                        throw new NoSuchElementException("No savingsCategory available. Create a profile first.");

                    for (int i = 0; i < savingsCategoriesList.size(); i++) {
                        //System.out.println(i + " : " + savingsCategoriesList.get(i).toString());
                        menuItem(i + "",
                                " Name: " + savingsCategoriesList.get(i).getName() +
                                        " Present: " + savingsCategoriesList.get(i).getPercent() + " %" +
                                        " Description: " + savingsCategoriesList.get(i).getDescription() + ".");
                    }
                    break;

                } else
                    throw new IllegalArgumentException("Incorrect input");

            }
            catch (NoSuchElementException e) {
                System.out.println("Not Found: " + e.getMessage());
            }
            catch (IllegalArgumentException e) {
                System.out.println("Input Error: " + e.getMessage());
            }
        }
    }

    //___________________Analysis_______________________________________
    public static void basicAnalysis(String user_id, String save_id) throws SQLException, IOException {

        ArrayList<Double> list = AnalysisManager.basicCalculation();
        double total_income = list.get(2);
        double predefinedTotalIncome = UserManager.total_income_calculation(user_id, save_id);

        int resultIncomeCheck = AnalysisManager.incomeCheck(user_id, save_id);

        if (resultIncomeCheck == 1 || resultIncomeCheck == 2) {
            error("Please correct your predefined Incomes.");
            error("predefined: " + predefinedTotalIncome + " and your income: " + total_income);
            incomeSourcePage(user_id, save_id);

        } else if (resultIncomeCheck == -1) {
            error("ERROR: Your income is less than zero, zero or null");
            error("predefined: " + predefinedTotalIncome + " and your income:" + total_income);
            incomeSourcePage(user_id, save_id);

        } else if (resultIncomeCheck == 0) {
            success("Your income remains the same as the amount you predefined!");
            success("predefined: " + predefinedTotalIncome + " and your income:" + total_income);

            ArrayList<DistributionListElement> distributionListElementList = AnalysisManager.distributionOfSavings(user_id, save_id);

            for (int i = 0; i < distributionListElementList.size(); i++) {
                System.out.println(distributionListElementList.get(i).toString());
            }
        }
    }
}
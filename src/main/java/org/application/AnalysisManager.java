package org.application;

import org.domain.SavingsCategory;
import org.infrastructure.csv.CSVManagement;

import java.util.ArrayList;

public class AnalysisManager {


    // List <IncomsFormCsv>
    // List<ListOfMoneyForSavingscategory>

    // [0]total_saved , [1]total_expenditure , [2]total_income
    /** return : [0]total_saved , [1]total_expenditure , [2]total_income */
    public static ArrayList<Double> basicCalculation(){

        CSVManagement csvManagement = new CSVManagement();

        return  csvManagement.csvReaderFunc();
    }


    /** return : error -1 ; ok 0 ; less 1 , greater 2 */
    public static int incomeCheck(String user_id, String save_id ) {

        ArrayList<Double> list = basicCalculation();
        double predefinedTotalIncome = UserManager.total_income_calculation(user_id, save_id);

        double total_saved = list.get(0);
        double total_expenditure = list.get(1);
        double total_income = list.get(2);


        if (predefinedTotalIncome == total_income) {
            return 0;

        } else if (predefinedTotalIncome > total_income) {
            System.out.println("Your income is less than the amount you set!");
            return 1;

        } else if (predefinedTotalIncome < total_income) {
            return 2;
        }
        else return -1;

    }

    public static void distributionOfSavings(String user_id, String save_id){
        ArrayList<Double> list = basicCalculation();
        double total_income = list.get(2);

        ArrayList<SavingsCategory> savingsCategories =  UserManager.savingsCategorySearch(user_id, save_id);

        //Work


    }
}

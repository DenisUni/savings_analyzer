package org.infrastructure.csv;
import de.siegmar.fastcsv.reader.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
//Source
// https://stackbriefly.com/blog/java-csv-libraries-2026-commons-csv-fastcsv-univocity-jackson-opencsv
// https://fastcsv.org/guides/basic/#iterative-reading-of-some-csv-data-with-a-header

public class CSVManagement {
    static String PATH = "/data/2acd89fc-66f5.csv"; //File should be automatic detect without name

    public ArrayList<Double> csvReaderFunc(){
        double total_sum = 0.0;
        double negativ_sum = 0.0;
        double positiv_sum = 0.0;
        ArrayList <Double> result = new ArrayList<>();
        try(CsvReader<NamedCsvRecord> csv = CsvReader.builder()
                .ofNamedCsvRecord(readFromClasspath(PATH))){

            for(NamedCsvRecord rec: csv) {
                String date = rec.getField("Bookpuk Date" );
                String partner = rec.getField("Partner Name");
                String amount_tmp = rec.getField("Amount (EUR)");
                double amount = Double.parseDouble(amount_tmp);
                total_sum += amount;
                if(amount > 0 ) positiv_sum += amount;
                else negativ_sum += amount;

                //System.out.printf("Date: %s | Partner: %s | Amount: %s %n", date,partner,amount);
            }
            result.add(total_sum);
            result.add(negativ_sum);
            result.add(positiv_sum);

            //System.out.println("total_sum: "+ total_sum +" positive_sum: " + positiv_sum + " negative_sum: " + negativ_sum  );

        }
        catch (Exception e){
            System.out.println("(csvReaderFunc) ERROR" + e.getMessage());
            e.printStackTrace();

        }
        //"Bookpuk Date" , "Partner Name" , "Amount (EUR)"

        return result;
    }
    //belong to csvReaderFunc() . Don`t change.
    InputStream readFromClasspath(final String name) throws FileNotFoundException {
        final InputStream in = getClass().getResourceAsStream(name);
        if (in == null) {
            throw new FileNotFoundException("Resource not found on classpath: " + name);
        }
        return in;
        }
}

package org.application;
import de.siegmar.fastcsv.reader.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
//Source
// https://stackbriefly.com/blog/java-csv-libraries-2026-commons-csv-fastcsv-univocity-jackson-opencsv
// https://fastcsv.org/guides/basic/#iterative-reading-of-some-csv-data-with-a-header

public class CSVManager {
    static String PATH = "/data/2acd89fc-66f5.csv"; //File should be automatic detect without name

    public void csvReaderFunc(){
        try(CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(readFromClasspath(PATH))){
            csv.forEach(System.out::println);

        }
        catch (Exception e){
            System.out.println("(csvReaderFunc) ERROR" + e.getMessage());
            e.printStackTrace();

        }

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

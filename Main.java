import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        /*
         * Handle command line input
         */
        if (args.length != 2) {
            System.out.println("Err1: Invalid execution parameters\n% java Main <training-dataset> <test-dataset>");
            System.exit(0);
        }

        Database dbTrain = null;
        Database dbTest = null;
        try {
            dbTrain = new Database(args[0]);    //2016
            dbTest  = new Database(args[1]);    //2012
        } catch (Exception e) {
            System.out.println("Err2: Could not create new Database object");
            System.exit(0);
        }


        // C4.5


        /* 
         * Print preprocessed data (test and training) to new csv files
         */
        File fTest = new File("data/PP-sp500-financials-2012.csv");
        try
        {
            PrintWriter pw = new PrintWriter(fTest);

            // Print attribute titles
            pw.println("Price,Dividend Yield,Price/Earnings,Book Value,52 week low,52 week high,Market Cap,EBITDA,Price/Sales,Price/Book");

            for (int row = 0; row < dbTest.data.size(); row++) {
                for (int col = 0; col < dbTest.data.get(row).size(); col++) {
                    pw.print(dbTest.data.get(row).get(col));

                    if (col != dbTest.data.get(row).size() - 1)
                        pw.print(",");
                }

                pw.println();
            }

            pw.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

        File fTrain = new File("data/PP-sp500-financials-2016-rated.csv");
        try
        {
            PrintWriter pw = new PrintWriter(fTrain);

            // Print attribute titles
            pw.println("Price,Dividend Yield,Price/Earnings,Book Value,52 week low,52 week high,Market Cap,EBITDA,Price/Sales,Price/Book,MorningStar Rating");

            for (int row = 0; row < dbTrain.data.size(); row++) {
                for (int col = 0; col < dbTrain.data.get(row).size(); col++) {
                    pw.print(dbTrain.data.get(row).get(col));

                    if (col != dbTrain.data.get(row).size() - 1)
                        pw.print(",");
                }

                pw.println();
            }

            pw.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

}
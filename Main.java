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
            System.out.println("Err1: Invalid execution parameters\n% java Main <training-dataset> <k>");
            System.exit(0);
        }

        Database dbTrain = null;
        // Database dbTest = null;
        try {
            dbTrain = new Database(args[0]);    //2016
            // dbTest  = new Database(args[1]);    //2012
        } catch (Exception e) {
            System.out.println("Err2: Could not create new Database object");
            System.exit(0);
        }



        /* 
         * Print preprocessed data (test and training) to new csv files
         */
        // File fTest = new File("data/PP-sp500-financials-2012.csv");
        // try
        // {
        //     PrintWriter pw = new PrintWriter(fTest);

        //     // Print attribute titles
        //     pw.println("Price,Dividend Yield,Price/Earnings,Book Value,52 week low,52 week high,Market Cap,EBITDA,Price/Sales,Price/Book");

        //     for (int row = 0; row < dbTest.data.size(); row++) {
        //         for (int col = 0; col < dbTest.data.get(row).size(); col++) {
        //             pw.print(dbTest.data.get(row).get(col));

        //             if (col != dbTest.data.get(row).size() - 1)
        //                 pw.print(",");
        //         }

        //         pw.println();
        //     }

        //     pw.close();
        // }
        // catch (FileNotFoundException ex) {
        //     System.out.println(ex);
        // }

        File fTrain = new File("data/preprocessed-output.csv");
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



        /*
         * Run NaiveBayes
         */
        NaiveBayes bayes = new NaiveBayes(dbTrain);
        List<Double> ratings = new ArrayList<Double>();

        ratings = bayes.runBayes();

        // Compute accuracy of Bayes
        int match = 0;
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).intValue() == dbTrain.data.get(i).get(dbTrain.data.get(i).size()-1).intValue())
                match++;
        }

        System.out.println("\nAccuracy of NaiveBayes:  " + (double) ((double) match / (double) dbTrain.data.size()));



        /*
         * Run k-means
         */
        int k = Integer.parseInt(args[1]);
        KMeans km = new KMeans(dbTrain, k);
        List<Cluster> clusters = km.runKMeans();


        System.out.println("\n\n" + k + " clusters:\n");
        for (Cluster c : clusters) {
            System.out.println("Size: " + c.size());
            System.out.println("Centroid: " + c.centroid + "\n");
        }

    }

}
import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        /*
         * Handle command line input
         */
        if (args.length != 3) {
            System.out.println("Err1: Invalid execution parameters\n   % java Main <training-dataset> <test-dataset> <output-filename>");
            System.exit(0);
        }

        Database dbTrain = null;
        Database dbTest = null;
        try {
            dbTrain = new Database(args[0]);    //2016
            // dbTest  = new Database(args[1]);    //2012
        } catch (Exception e) {
            System.out.println("Err2: Could not create new Database object");
            System.exit(0);
        }


        for (int row = 0; row < dbTrain.data.size(); row++) {
            for (int col = 0; col < dbTrain.data.get(row).size(); col++) {
                System.out.print(dbTrain.data.get(row).get(col) + "  ");
            }

            System.out.println();
        }

        System.out.println("\nCols: " + dbTrain.data.get(0).size());
        System.out.println("Rows: " + dbTrain.data.size());



        /*
         * Run neural net
         */
        //TODO



        /* 
         * Print results to output file
         */
        //TODO
        // File fOut = new File(args[2]);
        // try
        // {
        //     PrintWriter pw = new PrintWriter(fOut);

        //     pw.println();

        //     pw.close();
        // }
        // catch (FileNotFoundException ex) {
        //     System.out.println(ex);
        // }

    }

}
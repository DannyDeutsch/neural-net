import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        // Handle command line input
        if (args.length != 3) {
            System.out.println("Must specify 3 command line arguments:\n$ java Main <dataset filename> <k> <output filename>");
            System.exit(0);
        }

        Database db = null;

        try {
            db = new Database(args[0]);
        } catch (Exception e) {
            System.out.println("Could not create new Database object");
        }

        int k = Integer.parseInt(args[1]);


        // Make sure DB has enough records given k
        if (db.data.size() < k) {
            System.out.println("'k' is larger than the number of records in the database!");
            System.exit(0);
        }


        // Run k-means
        KMeans km = new KMeans(db, k);
        List<Cluster> clusters = km.runKMeans();


        // Print results to output file
        File fOut = new File(args[2]);
        try
        {
            PrintWriter pw = new PrintWriter(fOut);

            //TODO: fix
            for (Cluster c : clusters) {
                pw.println("Size: " + c.size());
                pw.println("Centroid: " + c.centroid);
                pw.println("Records: " + c.records + "\n");
            }

            pw.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Database
{
    public List<List<Double>> data;
    public List<List<String>> dataStr;


	public Database(String fName) throws Exception
	{
		data = new ArrayList<List<Double>>();
        dataStr = new ArrayList<List<String>>();

		FileInputStream fin = new FileInputStream(fName);
        InputStreamReader istream = new InputStreamReader(fin);
        BufferedReader stdin = new BufferedReader(istream);


        /*
         * Populate 'dataStr' List with each record
         */
        String line;
        while ((line = stdin.readLine()) != null)
        {
            List<String> recordStr = new ArrayList<String>();

            String[] temp = line.split(",");

            for (String val : temp) recordStr.add(val);

            if (recordStr.isEmpty()) continue;

            dataStr.add(recordStr);
        }



        /*
         * Populates 'data' List with processed values
         */
        for (int row = 1; row < dataStr.size(); row++)    //skip header row
        {
            List<Double> record = new ArrayList<Double>();

            for (int col = 2; col < dataStr.get(row).size(); col++)    //skip Symbol, Name attributes
            {
                // Dividend values: Replace "" or "N/A" with 0
                // if (col == 3)
                // {
                //     try {
                //         record.add(Double.parseDouble(dataStr.get(row).get(col)));
                //     } catch (NumberFormatException e) {
                //         record.add(0.0);
                //     }
                // }
                // else {
                try {
                    record.add(Double.parseDouble(dataStr.get(row).get(col)));
                } catch (NumberFormatException e) {
                    record.add(0.0);
                }
            }

            if (record.isEmpty()) continue;

            data.add(record);
        }



        // Close input stream readers
        fin.close();
        istream.close();
        stdin.close();
	}

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}


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
                String val = dataStr.get(row).get(col);

                if (col == 3)    // Dividend: Replace "N/A" or "" with "0.0"
                {
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        record.add(0.0);
                    }
                }
                else if (col == 4) {    // P/E: Replace "N/A" with =Price/EBITDA
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        //TODO
                        record.add(-100.0);
                    }
                }
                else if (col == 8 || col == 9) {    //Market Cap, EBITDA:  
                    int len = val.length();
                    if (val.charAt(len-1) == 'B') {
                        record.add(Double.parseDouble(val.substring(0, len-1)));
                    }
                    else if (val.charAt(len-1) == 'M') {
                        record.add(Double.parseDouble(val.substring(0, len-1)) / 1000);
                    }
                    else {
                        try {
                            record.add(Double.parseDouble(val));
                        } catch (NumberFormatException e) {
                            record.add(-1.0);
                        }
                    }
                }
                else if (col == 11) {    // Price/Book: Replace "N/A" with =Price/Book Value
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        //TODO
                        record.add(-100.0);
                    }
                }
                else {
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        record.add(-1.0);
                    }
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

}


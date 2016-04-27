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

                else if (col == 4) {    // P/E: Replace "N/A" with = Price / (EBITDA / (MC/Price))
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        double ebitda, marketCap;

                        String editdaStr = dataStr.get(row).get(9);
                        String marketCapStr = dataStr.get(row).get(8);

                        try {
                            ebitda = Double.parseDouble(editdaStr);
                        } catch (NumberFormatException e1) {    //ebitda value is formatted wrong
                            if (editdaStr.charAt(editdaStr.length()-1) == 'B') {
                                ebitda = Double.parseDouble(editdaStr.substring(0, editdaStr.length()-1));
                            }
                            else if (editdaStr.charAt(editdaStr.length()-1) == 'M') {
                                ebitda = Double.parseDouble(editdaStr.substring(0, editdaStr.length()-1)) / 1000;
                            }
                            else {    //error!
                                ebitda = -1.0;
                            }
                        }

                        try {
                            marketCap = Double.parseDouble(marketCapStr);
                        } catch (NumberFormatException e2) {    //ebitda value is formatted wrong
                            if (marketCapStr.charAt(marketCapStr.length()-1) == 'B') {
                                marketCap = Double.parseDouble(marketCapStr.substring(0, marketCapStr.length()-1));
                            }
                            else if (marketCapStr.charAt(marketCapStr.length()-1) == 'M') {
                                marketCap = Double.parseDouble(marketCapStr.substring(0, marketCapStr.length()-1)) / 1000;
                            }
                            else {    //error!
                                marketCap = -1.0;
                            }
                        }

                        record.add(record.get(0) / (ebitda / (marketCap / record.get(0))));
                    }
                }
                
                else if (col == 8 || col == 9) {    // Market Cap, EBITDA: Remove last char and normalize
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
                        } catch (NumberFormatException e) {    //error!
                            record.add(-1.0);
                        }
                    }
                }
                
                else if (col == 11) {    // Price/Book: Replace "N/A" with =Price/Book Value
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {
                        record.add(record.get(0) / record.get(3));
                    }
                }
                
                else {
                    try {
                        record.add(Double.parseDouble(val));
                    } catch (NumberFormatException e) {    //error!
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


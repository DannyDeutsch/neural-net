A basic program that preprocesses a financial dataset, filling in missing values smartly, then running the NaiveBayes classification and k-means clustering algorithms.

Upon execution, the preprocessed dataset will be written to a new cvs file in the data/ directory, and the results of NaiveBayes and k-means will be printed to stdout.

/***************************************************************
 *  Compilation:  javac *.java
 *  Execution:    java Main <dataset> <k>
 *
 *  % java Main data/sp500-financials-2016-rated.csv 4
 *
 ***************************************************************/

See my report for a more thorough pattern and insight analysis of the data.
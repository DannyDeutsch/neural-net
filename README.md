A basic neural network to classify stocks from 1-5 stars (1 suggesting to sell and 5 to buy), based on common financial metrics such as dividend yield and P/E ratio.

/***************************************************************
 *  Compilation:  javac *.java
 *  Execution:    java Main <training-dataset> <test-dataset> <output-filename>
 *
 *  % java Main data/sp500-financials-2016.csv data/sp500-financials-2012.csv output
 *
 ***************************************************************/

The neural network is trained with a dataset representing the current financial information of the S&P 500 constituents, each stock classified based on the current Morningstar rating.

The neural network classification model is then applied to the test dataset, the S&P 500 constituents from 2012, to rate each stock.

The "accuracy" of this classification model will be judged based on the to-date performance of 4 and 5 star stocks from 2012. A positive return is necessary to deem this model valid at all; a significant outperformance of the broad S&P 500 index is necessary to deem it potentially valuable.

Accuracy, as defined above, will also be compared to those of C4.5 and NaiveBayes, which are more sensible classification algorithms given these data.
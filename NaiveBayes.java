import java.util.*;

public class NaiveBayes
{
	private Database db;

	
	public NaiveBayes(Database db)
	{
		this.db = db;
	}

	/* Returns true if 'val' is within 10% range of 'anchor' */
	private boolean isInRange(double val, double anchor)
	{
		if (val <= anchor + (.05*anchor) && val >= anchor - (.05*anchor))
			return true;
		return false;
	}


	public List<Double> runBayes()
	{
		List<Double> classified = new ArrayList<Double>(); //list of class assignments, one per record

		// Compute P(C), for C = 1,2,3,4,5
		int total = db.data.size();			//total number of tuples
		int[] cCounts = new int[5];			//counts of each classification, 1-5
		double[] cProbs = new double[5];	//prob's of each classification, 1-5

		for (List<Double> record : db.data)
		{
			switch ( (int) ((double) (record.get(record.size()-1)) ) ) {
				case 1: cCounts[0]++;
						break;
				case 2: cCounts[1]++;
						break;
				case 3: cCounts[2]++;
						break;
				case 4: cCounts[3]++;
						break;
				case 5: cCounts[4]++;
						break;
			   default: System.out.println("Err1: Invalid rating parsed from data");
				 		System.exit(0);
			}
		}

		for (int i = 0; i < cProbs.length; i++) {
			cProbs[i] = (double) cCounts[i] / total;
		}


		double attrVal;
		int[] countXc = new int[5];
		double[] probXc = new double[5];	//prob attribute given classification, 1-5

		for (List<Double> testRecord : db.data)	// Iterate thru each record
		{
			// Reset conditional probabilities
			for (int i = 0; i < probXc.length; i++) {
				probXc[i] = 1.0;
			}

			// Iterate thru each attribute in record
			for (int i = 0; i < db.data.get(0).size()-1; i++)
			{
				attrVal = testRecord.get(i);

				// attrECount = 0;
				// attrPCount = 0;
				for (int cnt : countXc) cnt = 0;

				for (List<Double> record : db.data)
				{
					// if (mushroom.get(0).equals("e") && mushroom.get(i).equals(attrVal)) {
					// 	attrECount++;
					// } else if (mushroom.get(0).equals("p") && mushroom.get(i).equals(attrVal)) {
					// 	attrPCount++;
					// }
					if (isInRange(record.get(i), attrVal))
					{
						switch ((int) ((double) (record.get(record.size()-1)) ))
						{
							case 1: countXc[0]++;
									break;
							case 2: countXc[1]++;
									break;
							case 3: countXc[2]++;
									break;
							case 4: countXc[3]++;
									break;
							case 5: countXc[4]++;
									break;
						   default: System.out.println("Err2: Invalid rating parsed from data");
							 		System.exit(0);
						}
					}
				}


				// probXp = (double) (probXp * ((double) attrPCount/pCount));
				// probXe = (double) (probXe * ((double) attrECount/eCount));
				for (int j = 0; j < probXc.length; j++) {
					probXc[j] = (double) (probXc[j] * ((double) (countXc[j]/(double)cCounts[j])));
				}
			}


			// Classify the record
			// if ( ((double) probXe * probE) > ((double) probXp * probP) ) {
			// 	c = "e";
			// } else {
			// 	c = "p";
			// }
			double c;	//classification: 1.0, 2.0, ... , 5.0
			double max = 0.0;
			c = max;
			for (int i = 0; i < probXc.length; i++)
			{
				if ( (double) (probXc[i] * cProbs[i]) > max) {
					max = (double) (probXc[i] * cProbs[i]);
					c = (double) i + 1;
				}
			}
				

			classified.add(c);
		}
		

		return classified;
	}
}
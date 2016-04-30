import java.util.*;

public class NaiveBayes
{
	private Database db;

	
	public NaiveBayes(Database db)
	{
		this.db = db;
	}


	public List<String> startBayes()
	{
		List<String> classified = new ArrayList<String>(); //list of class assignments, one per tuple
		String c;	//either "e" or "p"

		int total = db.data.size();	//total number of tuples
		int eCount = 0;				//total count edible
		int pCount = 0;				//total count poisonous
		double probE, probP;		//prob edible/poisonous

		double probAttr;			//prob of a given attribute value
		double probXp;
		double probXe;



		/**************************
		      Bayes algorithm
		 **************************/
		
		// Compute P(C), for C = edible & C = poisonous
		for (List<String> mushroom : db.data)
		{
			if (mushroom.get(0).equals("e"))
				eCount++;
			else
				pCount++;
		}

		probE = (double) eCount/total;
		probP = (double) pCount/total;




		String attrVal;
		int attrECount, attrPCount;

		for (List<String> testMushroom : db.data)	// Iterate thru each record
		{
			//Reset conditional probabilities
			probXp = 1.0;
			probXe = 1.0;

			for (int i = 1; i < db.data.get(0).size(); i++)		// Iterate thru each attribute in record
			{
				attrVal = testMushroom.get(i);

				attrECount = 0;
				attrPCount = 0;

				for (List<String> mushroom : db.data)
				{
					if (mushroom.get(0).equals("e") && mushroom.get(i).equals(attrVal)) {
						attrECount++;
					} else if (mushroom.get(0).equals("p") && mushroom.get(i).equals(attrVal)) {
						attrPCount++;
					}
				}


				probXp = (double) (probXp * ((double) attrPCount/pCount));
				probXe = (double) (probXe * ((double) attrECount/eCount));
			}


			// Classify the record
			if ( ((double) probXe * probE) > ((double) probXp * probP) ) {
				c = "e";
			} else {
				c = "p";
			}

			classified.add(c);
		}


		return classified;
	}
}
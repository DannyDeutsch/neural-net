import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;


public class Cluster
{
	public List<Double> centroid;
	public Set<List<Double>> records = new LinkedHashSet<List<Double>>();

	/* Constructor */
	public Cluster(List<Double> centroid) {
		this.centroid = centroid;
	}

	/* Add record to Cluster */
	public void addToCluster(List<Double> record) {
		records.add(record);
	}

	/* Return number of records in Cluster */
	public int size() { return records.size(); }
}
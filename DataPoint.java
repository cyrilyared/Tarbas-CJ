
public class DataPoint implements Comparable<DataPoint> {
	double[] x; // x, y coordinates in Cartesian plane
	int label; //should be 0 or 1
	// 0: element not in Queue;
	// 1: element in Queue
	
	
	public DataPoint(double[] coordinates, int label) {
		this.x = new double[2];
		x[0] = coordinates[0];
		x[1] = coordinates[1];
		this.label = label;
	}
	
	public String toString() {
		String result = "";
		result += "x = " + x[0] + ", y = " + x[1] + ", label: " + label;
		return result;
	}


	@Override
	public int compareTo(DataPoint dp) {
		if(this.x[0] < dp.x[0]) {
			return -1;
		} else if(this.x[0] > dp.x[1]) {
			return 1;
		} else { //in the case they are equal
			if(this.x[1] < dp.x[1]) {
				return -1;
			} else if(this.x[1] > dp.x[1]) {
				return 1;
			} else { //same x same y equals IS NOT VIOLATED
				return 0;
			}
		}
	}
	
	@Override
	public boolean equals(Object dp) {
		if(dp instanceof DataPoint) {
			DataPoint datapoint = (DataPoint) dp; 
			return (this.x[0] == datapoint.x[0] && this.x[1] == datapoint.x[1]);
		} else {
			return false;
		}
		
	}
	
}

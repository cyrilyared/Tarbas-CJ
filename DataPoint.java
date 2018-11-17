
public class DataPoint {
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
	
}

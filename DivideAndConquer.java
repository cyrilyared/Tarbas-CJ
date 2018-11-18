import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class DivideAndConquer {

	public static final int minimalAcceptableDistance = 8;
	//create a method that given array list I sort it and then iterate through x and y and add elements
	//to a hash map (same key) if they are connected 
	//compare to all other elements in Hashmap (if connected to one)

	//	public HashMap<Integer, DataPoint> identifyQueues(ArrayList<DataPoint> array) {
	//		ArrayList<DataPoint>[] possibleChains = new ArrayList<DataPoint>[array.size()];
	//		//HashMap<Integer, DataPoint> map = new HashMap<Integer, DataPoint>(array.size());
	//		//Sort ArrayList
	//		Collections.sort(array); 
	//		int i = 0;
	//		
	//		for(DataPoint data: array) {
	//			possibleChains[i] = data;
	//			for(DataPoint data2: array) {
	//				if(!data.equals(data2)) {
	//					//if not the same point, calculate distance
	//					double distanceCalculated = calculatedistance(data, data2);
	//					//if less than minimal acceptable, add to same key in HashMap
	//					if(distanceCalculated < minimalAcceptableDistance) {
	//						//so it is related to point
	//						map.put(i, data2);
	//					}
	//				} 
	//			}
	//			i++;
	//		}
	//		return map;
	//	}

	public ArrayList<DataPoint> identifyLongestQueue(ArrayList<DataPoint> array) {
		ArrayList<Integer> categories = new ArrayList<Integer>();
		//Sort ArrayList
		Collections.sort(array); 

		//		int i = 0;
		//		while(i < array.size()) {
		//			
		//			i++;
		//		}

		//first element label 1
		int category_label = 1;
		categories.add(category_label);

		for(int i = 0; i < array.size(); i++) {

			double minimalDistance = Double.MAX_VALUE;			
			for(int j = i-1; j >= 0; j--) {
				boolean minimumFound = false;
				int index = -1;
				double distance_between_points = calculatedistance(array.get(i), array.get(j));

				if(distance_between_points < minimalAcceptableDistance && distance_between_points < minimalDistance ) {
					minimalDistance = distance_between_points;
					index = j;
					minimumFound = true;

				}
				if(minimumFound) {
					categories.add(i, categories.get(index));
				} else {
					categories.add(i, category_label);
					category_label++;
				}


			}
		}

		return null;
	}


	public static double calculatedistance(DataPoint a, DataPoint b) {
		return Math.sqrt(Math.pow((a.x[0] - b.x[0]), 2) + Math.pow((a.x[1] - b.x[1]), 2));
	}


	/**
	 * Returns training matrix to be used to classify 
	 * @param a
	 * @return matrix
	 */
	public int[][] trainingmatrix(ArrayList<DataPoint> a) {
		int[][] result = new int[100][100];
		for(DataPoint data: a) {
			if(data.label == 1) {
				result[(int) data.x[0]][(int) data.x[1]] = 2;
			} else {
				result[(int) data.x[0]][(int) data.x[1]] = 1;
			}
		}
		return result;
	}



}


import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

public class GroupQueue {

	//create a method that given array list I sort it and then iterate through x and y and add elements
	//to a hash map (same key) if they are connected 
	//compare to all other elements in Hashmap (if connected to one)


public static ArrayList<DataPoint> identifyLongestQueue(List<DataPoint> array, int minimalAcceptableDistance) {


		if(array == null || array.size() == 0) {
			return null;
		}

		ArrayList<Integer> categories = new ArrayList<Integer>();
		//Sort ArrayList
		Collections.sort(array); 

		//first element label 1
		int category_label = 1;
		categories.add(category_label);

		for(int i = 1; i < array.size(); i++) {

			double minimalDistance = Double.MAX_VALUE;
			boolean minimumFound = false;
			int index = -1;

			for(int j = i-1; j >= 0; j--) {
				double distance_between_points = calculatedistance(array.get(i), array.get(j));

				if(distance_between_points < minimalAcceptableDistance && distance_between_points < minimalDistance ) {
					minimalDistance = distance_between_points;
					index = j;
					minimumFound = true;
				}
			}

			if(minimumFound) {
				categories.add(i, categories.get(index));
			} else {
				categories.add(i, category_label);
				category_label++;
			}

		}

		Map<Integer, Long> counted_categories = categories.stream().collect(Collectors.groupingBy(e->e, Collectors.counting()));

		long max = 0;
		int label = 0;
		for(Map.Entry<Integer, Long> entry: counted_categories.entrySet()) {
			if(entry.getValue() > max) {
				max = entry.getValue();
				label = entry.getKey();
			}
		}

		ArrayList<DataPoint> returnArray = new ArrayList<DataPoint>();

		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i) == label) {
				returnArray.add(array.get(i));
			}
		}

		return returnArray;

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

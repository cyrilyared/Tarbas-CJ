import java.util.ArrayList;

public class home {

	/**
	 * This will be done the easiest way (Grade 6)
	 * @param listOfPoints
	 * @param average_rate (in seconds per element)
	 * @param index (location of point in queue : number of people that came after you)
	 * @return estimated time it takes you no matter where you are in the queue (in seconds)
	 */
	public int estimatedTimeNeeded1(ArrayList<DataPoint> listOfPoints, int average_rate, int index) { 	
		return (listOfPoints.size()- index) * average_rate;
	}

	//We need to generate a method that can estimate the average rate for non linear cases
	//based of previous data (& type of data: for example if you can divides data in sub-categories, each having an easier to calculate average time)
	//then you calculate weighted average
	//but need new data that might not have
	//this is why I will opt for the first choice 
	//based on previous data

	/**
	 * This is a more sophisticated version of the same method when rate is not constant
	 * @param listOfPoints
	 * @param index
	 * @return
	 */
	public int estimatedTimeNeeded2 (ArrayList<DataPoint> listOfPoints, int index) {

		return 0;
	}
	
	

	/**
	 * This methods converts the estimated time (in seconds) to String format used in everyday life
	 * @param l: estimated time in seconds
	 * @return the time in hours, minutes second format
	 */
	public String toString(int time) {	
		String result = "";
		int hours =  time/3600;
		int minutes = (time%3600)/60;
		int seconds = (time%3600) % 60;
		result += "t = " + hours + "h " + minutes + "min " + seconds + "s";
		return result;
	}
	

}

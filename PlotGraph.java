import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.*;
import acm.program.GraphicsProgram;

public class PlotGraph extends GraphicsProgram {
	
	public static final int radiusofPoint = 5;
	
	/**
	 * Given a DataPoint, it is added to the graph
	 * @param p
	 */
	private void addDataPointToGraph(DataPoint p) {
		GOval point = new GOval(radiusofPoint, radiusofPoint);
		point.setLocation(p.x[0], p.x[1]);
		point.setFilled(true);
		//if it is in queue color in red, else i blue
		if(p.label == 1) {
			point.setColor(Color.red);
		} else {
			point.setColor(Color.blue);
		}
		add(point);
	}
	
	public void buildGraph(ArrayList<DataPoint> arr) {
		for(DataPoint data: arr) {
			addDataPointToGraph(data);
		}
	}	

}

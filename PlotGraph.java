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
	private GOval datapointToGraphpoint(DataPoint p) {
		GOval point = new GOval(radiusofPoint, radiusofPoint);
		point.setLocation(p.x[0], p.x[1]);
		point.setFilled(true);
		//if it is in queue color in red, else i blue
		if(p.label == 1) {
			point.setColor(Color.red);
		} else {
			point.setColor(Color.blue);
		}
		return point;
	}
	
	public ArrayList<GOval> buildGraph(ArrayList<DataPoint> arr) {
		ArrayList<GOval> point_add = new ArrayList<GOval>();
		for(DataPoint data: arr) {
			GOval obj = datapointToGraphpoint(data);
			point_add.add(obj);
		}
		return point_add;
	}

	
	
//	public void init() {
//		DataPoint p = new DataPoint(new double[] {3.4, 10}, 1);
//		DataPoint p1 = new DataPoint(new double[] {4.7, 5}, 0);
//		DataPoint p2 = new DataPoint(new double[] {6.7, 8}, 0);
//		DataPoint p3 = new DataPoint(new double[] {2.4, 18}, 1);
//		ArrayList<DataPoint> d = new ArrayList<DataPoint>();
//		d.add(p);
//		d.add(p1);
//		d.add(p2);
//		d.add(p3);
//		
//		PlotGraph plot = new PlotGraph();
//		ArrayList<GOval> goval = plot.buildGraph(d);
//		for(GOval oval: goval) {
//			add(oval);
//		}
//	}
}

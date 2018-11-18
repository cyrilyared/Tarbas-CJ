import java.io.*;
import java.util.*;


public class LineGenerator {
	//private ArrayList<DataPoint> queue;
	private DataPoint lastElementin;

	public static ArrayList<DataPoint> create_ds(int status,int nb_points){
		Random r =new Random();
		ArrayList<DataPoint> list = new ArrayList<DataPoint>();

		if(status ==0) {			
			for(int i =0;i<nb_points;i++) {
				double[] coordinates = {r.nextDouble()*multiplier,r.nextDouble()*multiplier};
				DataPoint p = new DataPoint(coordinates,0);
				list.add(p);

			}
			return list;
		}
		else if(status ==1) {
			double[] coordinates = {r.nextDouble()*multiplier,r.nextDouble()*multiplier};
			DataPoint p1=new DataPoint(coordinates,1);
			double[] p2_cor= {p1.x[0]-maxdistance+r.nextDouble()*2*maxdistance,p1.x[1]-maxdistance+r.nextDouble()*2*maxdistance};
			DataPoint p2=new DataPoint(p2_cor,1);
			list.add(p1);
			list.add(p2);
			int qel=MinPointsInQueue+r.nextInt(94);
			double m1 = (p1.x[1]-p2.x[1])/(p1.x[0]-p2.x[0]);
			double meanangle = Math.toDegrees((Math.atan(m1)));
			for(int i =2;i<qel;i++) {
				double angle = r.nextGaussian()*angledeviation+meanangle;
				list.add(find_p3(list.get(i-1),angle));
			}
			for (int i=0; i<(nb_points-qel);i++) {
				double[] coor = {r.nextDouble()*multiplier+maxdistance,r.nextDouble()*multiplier+maxdistance};
				DataPoint q = new DataPoint(coor,0);
				boolean b = false;
				for (DataPoint d:list) {
					b=dist(q,d)>5*maxdistance;
					if (b==false) break;
				}
				if(b==true) list.add(q);
				
			}
			return list;
		}
		else throw new IllegalArgumentException("Status not defined");
	}

	public static DataPoint find_p3(DataPoint p2,double angle) {
		Random r =new Random();
		double angle_rad=Math.toRadians(angle);
		double dist_p2_p3 = r.nextInt((int) (maxdistance-0.3*maxdistance))+0.3*maxdistance;
		double p3_x= dist_p2_p3*Math.cos(angle_rad)+p2.x[0];
		double p3_y= dist_p2_p3*Math.sin(angle_rad)+p2.x[1]; 
		double[] p3_cor = {p3_x,p3_y};
		DataPoint p3 = new DataPoint(p3_cor,1);
		return p3;
	}

	private static double dist(DataPoint a, DataPoint b) {
		double dist = Math.sqrt(Math.pow((a.x[0]-b.x[0]), 2)+Math.pow((a.x[1]-b.x[1]), 2));
		return dist;
	}

	public DataPoint getLastElement() {
		return lastElementin;
	}
	
	 

	public static void main(String[] args) {
		ArrayList<DataPoint> ds = LineGenerator.create_ds(1,100);
		//Sort array
		Collections.sort(ds);
		//
		
		for(DataPoint d: ds) {
			System.out.println(d);
		}
		File file=new File("file.csv");
		try {
			PrintWriter printwriter=new PrintWriter(file);
			for(DataPoint d: ds) {
				printwriter.println(d.x[0]+","+d.x[1]+","+d.label);
			}
			printwriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static final int MinPointsInQueue = 6;
	private static final double angledeviation =5;
	private static final double maxdistance = 4;
	private static final int multiplier = 100;
}	

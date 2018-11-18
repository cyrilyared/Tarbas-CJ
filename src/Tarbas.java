import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tarbas {
    public static final String PERSON_DETECTION_FILE = "person_detection.py";
    public static final String LINEAR_REGRESSION_FILE = "linear_regression.py";
    public static final String IMAGES_DIRECTORY = "./images";
    public static final int MINIMUM_DISTANCE_BETWEEN_POINTS = 8;

    public static void main(String[] args) throws IOException {
        String personDetectionFile = runPython(new String[] {PERSON_DETECTION_FILE, IMAGES_DIRECTORY});

        List<String> newImageCSVFiles = new ArrayList<String>(Arrays.asList(personDetectionFile.split(";")));
        List<Double> TimeQueue = new ArrayList<Double>();
        System.out.println(String.valueOf(newImageCSVFiles.size()) + " images found.");


        for(String csvFile: newImageCSVFiles) {
            Double time = processImageCSVFile(csvFile);
            TimeQueue.add(time);
        }
        System.out.println("\n\n");
        for(int i = 0; i < newImageCSVFiles.size(); i++)  {
            if(TimeQueue.get(i) == -1) {
                System.out.println("No queue found for " + newImageCSVFiles.get(i));
            } else {
                double time = TimeQueue.get(i);
                int timeInt = (int)time;

                System.out.println("Waiting time for queue " + newImageCSVFiles.get(i) + " is " +EstimateTime.toString(timeInt)+".");
            }
        }
    }

    public static double processImageCSVFile(String csvFile) throws IOException {
            BufferedReader br = null;
            String line = "";
            String CSVSplit = ",";
            List<DataPoint> dataPointList = new ArrayList<DataPoint>();

            try {

                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    String[] datapointStr = line.split(CSVSplit);
                    double x = Double.parseDouble(datapointStr[0]);
                    double y = Double.parseDouble(datapointStr[1]);
                    double coordinate[] = {x, y};
                    DataPoint dataPoint = new DataPoint(coordinate);
                    dataPointList.add(dataPoint);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            List<DataPoint> processedList = GroupQueue.identifyLongestQueue(dataPointList, MINIMUM_DISTANCE_BETWEEN_POINTS);
            List<String> csvComponent = new ArrayList<String>(Arrays.asList(csvFile.split("\\.")));

            String processedFileName = "." + csvComponent.get(1) + "_processed.csv";
            boolean fileCreated = printToCSV(processedFileName, processedList);

            System.out.println("Processing " + processedFileName);

            String linRegScore = runPython(new String[] {LINEAR_REGRESSION_FILE, csvFile});
            System.out.println("R^2 of " + csvFile + " is: "+ linRegScore);

            if(fileCreated) {
                linRegScore = runPython(new String[]{LINEAR_REGRESSION_FILE, processedFileName});
                System.out.println("R^2 of " + processedFileName + " is: " + linRegScore);
            }

            if(linRegScore != null && linRegScore != "" & linRegScore != "null") {
                 double rscore = Double.parseDouble(linRegScore);
                 if(rscore > 0.7) {
                     double time = EstimateTime.estimatedTimeNeeded1(dataPointList, 60, 0);
                     return time;
                 } else {
                     return -1;
                 }
            } else {
                return -1;
            }
    }

    public static boolean printToCSV(String filename, List<DataPoint> dataPoints) {
        File file=new File(filename);
        if (dataPoints == null || dataPoints.size() == 0) {
            return false;
        }
        try {
            PrintWriter printwriter=new PrintWriter(file);
            for(DataPoint d: dataPoints) {
                printwriter.println(d.x[0]+","+d.x[1]);
            }
            printwriter.close();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Runs Python scripts
     * @param args [0] is Python script, [1] is arguments for Python script
     * @return String output of Python script to STDOUT
     * @throws IOException
     */
    public static String runPython(String[] args) throws IOException {
        Process p;
        if(args.length == 1) {
            p = Runtime.getRuntime().exec("python " + args[0]);
        } else {
            p = Runtime.getRuntime().exec("python " + args[0] + " " + args[1]);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String result = in.readLine();
        return result;
    }
}
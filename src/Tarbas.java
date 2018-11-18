import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tarbas {
    public static final String PERSON_DETECTION_FILE = "person_detection.py";
    public static final String LINEAR_REGRESSION_FILE = "linear_regression.py";
    public static final String IMAGES_DIRECTORY = "./images";

    public static void main(String[] args) throws IOException {
        String personDetectionFile = runPython(new String[] {PERSON_DETECTION_FILE, IMAGES_DIRECTORY});

        List<String> newImageFiles = new ArrayList<String>(Arrays.asList(personDetectionFile.split(";")));
        List<Double> RScore = new ArrayList<Double>();
        System.out.println(String.valueOf(newImageFiles.size()) + " ");





        for(String str: newImageFiles) {
            System.out.println(str);

            String linRegScore = runPython(new String[] {LINEAR_REGRESSION_FILE, str});
            System.out.println("R^2 of " + str + " is: "+ linRegScore);
            if(linRegScore != null && linRegScore != "" & linRegScore != "null") {
                RScore.add(Double.valueOf(linRegScore));
            }
        }
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
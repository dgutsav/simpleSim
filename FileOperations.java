import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class FileOperations {
    public static List<String[]> readCSV(String filename) {
        System.out.println("Reading " + filename);
        List<String[]> arr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            System.out.println("File Found");
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                arr.add(d);
            }
        } catch (Exception e) {
            System.err.println(e);
            return new ArrayList<>();
        }
        return arr;
    }

    public static void writeToFile(String filename, String text) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(text);
            fw.close();
        } catch (IOException e) {

        }
    }
}
package ku.cs.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVReader {
    public static String[] getLines(String filePath) throws IOException {
        String [] lines = new String[100];
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        int i = 0;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            lines[i++] = line;
        }
        reader.close();
        return Arrays.copyOf(lines, i);
    }
}

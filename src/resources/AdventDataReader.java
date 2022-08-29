package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdventDataReader {

    public static String readFromInputStream(String inputFile) throws IOException {
        FileReader in = new FileReader("src/resources/" + inputFile);
        BufferedReader br = new BufferedReader(in);

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }
}

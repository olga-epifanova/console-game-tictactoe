import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class File {

    private static final String fileName = "rating.txt";

    public static void writeFile(String text) {

        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(text + "\n");
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}
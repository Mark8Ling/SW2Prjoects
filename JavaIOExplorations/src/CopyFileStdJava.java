import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Program to copy a text file into another file.
 *
 * @author Mark Ling
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        String i3 = args[0];
        String o3 = args[1];

        try (BufferedReader reader = new BufferedReader(new FileReader(i3));
                BufferedWriter writer = new BufferedWriter(new FileWriter(o3))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while copying the file:");
            e.printStackTrace();
        }
    }

    }

}

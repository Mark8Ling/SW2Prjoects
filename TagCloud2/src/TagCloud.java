import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A Tag Cloud Generator Program Generates an HTML-based tag cloud from a given
 * input text file.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public final class TagCloud {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private TagCloud() {
    }

    /**
     * String containing list of separators.
     */
    private static final String SEPARATORS = ".,'\"!?;:()-[]{}<>/\\ \t\n\r";

    /**
     * Compare {@code Map.Pair<String, Integer>}s in alphabetical order
     * according to key value.
     */
    @SuppressWarnings("serial")
    private static final class AlphaSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

            String s1 = o1.getKey();
            String s2 = o2.getKey();
            int compare = s1.compareToIgnoreCase(s2);

            if (compare == 0) {
                compare = s1.compareTo(s2);
            }

            return compare;
        }
    }

    /**
     * Compare {@code Map.Pair<String, Integer>}s by decreasing order by value.
     */
    @SuppressWarnings("serial")
    private static final class WordSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

            Integer i1 = o1.getValue();
            Integer i2 = o2.getValue();
            int compare = i2.compareTo(i1);

            if (compare == 0) {
                compare = o1.getKey().compareTo(o2.getKey());
            }

            return compare;
        }
    }

    /**
     * Reads the input file and counts word frequencies.
     *
     * @param inputFile
     *            the file to read
     * @return a Map of words and their frequencies
     * @throws IOException
     *             if an error occurs during file reading
     */
    private static Map<String, Integer> countWords(String inputFile) throws IOException {
        //initializes map and set
        Map<String, Integer> wordCounts = new HashMap<>();
        Set<Character> separators = new HashSet<>();
        //adds words to set SEPARATed by SEPARATORS
        for (char c : SEPARATORS.toCharArray()) {
            separators.add(c);
        }
        //reads line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //extracts words and counts
                int index = 0;
                while (index < line.length()) {
                    String token = nextWordOrSeparator(line, index, separators);
                    if (!separators.contains(token.charAt(0))) {
                        wordCounts.put(token.toLowerCase(),
                                wordCounts.getOrDefault(token.toLowerCase(), 0) + 1);
                    }
                    index += token.length();
                }
            }
        }
        return wordCounts;
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        //Initializing variables.
        String word = "";
        int temp = position;

        //If it is a separator, then it will continue to store until it is not.
        if (separators.contains(text.charAt(temp))) {
            word += text.charAt(temp);
        }

        //If it is not a separator, then the word will build until it is a separator.
        while (temp < text.length() && !separators.contains(text.charAt(temp))) {
            word += text.charAt(temp);
            temp++;
            word = word.toLowerCase();
        }

        return word;

    }

    /**
     * This gives the header and the title of the tab of the HTML document.
     *
     * @param out
     *            The file that user printing too.
     * @param inputFile
     *            The file that user is using to run this program.
     * @param numWords
     *            Number of times each word shows up in file.
     *
     */
    private static void header(PrintWriter out, String inputFile, int numWords) {

        //Printing out the beginning of the HTML file.
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + numWords + " words in " + inputFile + "</title>");
        out.println("<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + numWords + " words in " + inputFile + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * This gives the body of the HTML document along with the table.
     *
     * @param out
     *            The file that user printing too.
     * @param sortedWords
     *            Map of words sorted by number of times used
     * @param minCount
     *            minimum count in tag cloud words
     * @param maxCount
     *            maximum count in tag cloud words
     */
    private static void body(PrintWriter out,
            List<Map.Entry<String, Integer>> sortedWords, int maxCount, int minCount) {
        //weird case were 'i gets put in first
        sortedWords.remove(0);
        //iterates over each word
        for (Map.Entry<String, Integer> entry : sortedWords) {
            String word = entry.getKey();
            int count = entry.getValue();
            //calculates the font size and prints each word at each font size
            int fontSize = calculateFontSize(maxCount, minCount, count);
            String element = "<span style=\"font-size:" + fontSize
                    + "px\" title=\"Count: " + count + "\">" + word + "</span>";
            out.println(element);
        }
    }

    /**
     * This function finds the font size for the word corresponding to the
     * number of occurrences.
     *
     * @param max
     *            maximum count in tag cloud words
     * @param min
     *            minimum count in tag cloud words
     * @param count
     *            count of the word
     * @requires max > 0 && min > 0
     *
     * @return the font size for a specific word in the appropriate range
     */
    private static int calculateFontSize(int max, int min, int count) {
        int fs = 0;
        final int mavalue = 48;
        final int mivalue = 11;
        //checks for case is max and min are equal
        if (max == min) {
            fs = (mavalue + mivalue) / 2;
        } else {
            //tag could formula
            fs = mivalue + (mavalue - mivalue) * (count - min) / (max - min);
        }
        return fs;
    }

    /**
     * Writes the HTML footer for the tag cloud.
     *
     * @param out
     *            The file that user printing too.
     */
    private static void writeFooter(PrintWriter out) {

        //Printing out the ending of the HTML file.
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //asks for user files and text
        System.out.print("Enter input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output file name: ");
        String outputFile = scanner.nextLine();

        System.out.print("Enter the number of words for the tag cloud: ");
        int numWords = scanner.nextInt();

        try {
            Map<String, Integer> wordCounts = countWords(inputFile);
            //converts the map to list while sorting it by frequency
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(
                    wordCounts.entrySet());
            Comparator<Map.Entry<String, Integer>> comp1 = new WordSort();

            sortedWords.sort(comp1);

            //take only top used word
            if (sortedWords.size() > numWords) {
                sortedWords = sortedWords.subList(0, numWords);
            }

            Comparator<Map.Entry<String, Integer>> comp2 = new AlphaSort();

            sortedWords.sort(comp2);

            //finds highest and lowest counts
            int maxCount = sortedWords.stream().mapToInt(Map.Entry::getValue).max()
                    .orElse(0);
            int minCount = sortedWords.stream().mapToInt(Map.Entry::getValue).min()
                    .orElse(0);
            //writes the html code using methods
            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)))) {
                header(writer, inputFile, numWords);
                body(writer, sortedWords, maxCount, minCount);
                writeFooter(writer);
            }
            //Success Message
            System.out.println("Tag cloud generated successfully!");
            //Error Message
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        //closes open scanner
        scanner.close();
    }
}

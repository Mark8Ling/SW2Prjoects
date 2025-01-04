import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/**
 * Put a short phrase describing the program here.
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
     * This method is going to read each line and get the word without space or
     * punctuation, and it will get the number of occurrence of the same word.
     *
     * @param reader
     *            Reading the file line by line
     *
     * @return a map that has the word and number of occurrence.
     */
    private static Map<String, Integer> lineReader(SimpleReader reader) {

        //Declaring variables.
        Set<Character> separator = new Set1L<Character>();
        Map<String, Integer> map = new Map1L<String, Integer>();
        String lineOfString;

        //This will make a separator that separate the words with the separator.
        generateElements(" \t\n\r,-.!?[]';:/()*`1234567890\"{}~<>", separator);

        //This while loop will read to the end of the inputFile.
        while (!reader.atEOS()) {
            String firstLine = reader.nextLine();
            int i = 0;

            //This will make a map with the word and the number of occurrences.
            while (i < firstLine.length() - 1) {

                //The file will separate the separators and the words from the inputFile.
                lineOfString = nextWordOrSeparator(firstLine, i, separator);

                //This will go though each word, and it has the same word + 1.
                i = i + lineOfString.length();
                if (!separator.contains(lineOfString.charAt(0))) {
                    if (map.hasKey(lineOfString)) {
                        int occurrence = map.value(lineOfString) + 1;
                        map.replaceValue(lineOfString, occurrence);
                    } else {
                        map.add(lineOfString, 1);
                    }
                }

            }

        }
        return map;
    }

    /**
     * This gives the header and the title of the tab of the HTML document.
     *
     * @param inputFile
     *            The file that user is using to run this program.
     * @param number
     *            The user's input value to generate the number of words
     * @param outputFile
     *            This is putting the code to the HTML page.
     *
     */
    private static void header(String inputFile, int number, SimpleWriter outputFile) {

        //Printing out the beginning of the HTML file.
        outputFile.println("<html>");
        outputFile.println("<head>");
        outputFile
                .println("<title>Top " + number + " words in " + inputFile + "</title>");
        outputFile.println("<link href=\"https://cse22x1.engineering.osu.edu/2231/"
                + "web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                + "rel = \"stylesheet\" type = \"text/css\">");
        outputFile.println(
                "<link href=\"tagcloud.css\" rel= \"stylesheet\" type= \"text/css\">");
        outputFile.println("</head>");

    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        //Clearing whatever it is in the charSet.
        charSet.clear();

        //This loop will add the separators to the charSet.
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }
    }

    /**
     * This function sorts the values of Map.Pair<String, Integer> in numeric
     * decreasing order.
     */
    private static final class NumberSort
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * This functions sorts the words of Map.Pair<String, Integer> in
     * alphabetical order.
     */
    private static final class WordSort implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
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
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

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
     * This function finds the font size for the word corresponding to the
     * number of occurrences.
     *
     * @param max
     *            maximum count in tag cloud words
     * @param min
     *            minimum count in tag cloud words
     * @param value
     *            count of the word
     * @requires max > 0 && min > 0
     *
     * @return the font size for a specific word in the appropriate range
     */
    private static String fontSize(int max, int min, int value) {

        //Initializing values
        final int maxFont = 48;
        final int minFont = 11;
        int size = 1;

        if (value > min) {

            //tag cloud formula
            size = (maxFont - minFont) * (value - min);
            size /= (max - min);

            // make sure minimum is above 11
            size += minFont;
        }

        return "f" + size;
    }

    /**
     * This gives the body of the HTML document along with the table.
     *
     * @param inputFile
     *            The file that user is using to run this program
     * @param outputFile
     *            This is putting the code to the HTML page
     * @param output
     *            This is the file to be accessed
     * @param number
     *            This is the number that the user inputs to generate the tag
     *            cloud
     * @param map
     *            This has the word and the their counts
     *
     */
    private static void body(String inputFile, SimpleWriter outputFile, String output,
            int number, Map<String, Integer> map) {

        //This is printing out the body.
        outputFile.println("<body>");
        outputFile.println("<h2>Top " + number + " words in " + inputFile + "</h2>");
        outputFile.println("<hr>");
        outputFile.println("<div class = \"cdiv\">");
        outputFile.println("<p class = \"cbox\">");

        //This is making a SortingMachine that will sort through the numbers
        Comparator<Map.Pair<String, Integer>> compNum = new NumberSort();
        SortingMachine<Map.Pair<String, Integer>> sNum = new SortingMachine2<Map.Pair<String, Integer>>(
                compNum);

        //This sorts through the map as it extracts the element to the SortingMachine
        while (map.size() > 0) {
            sNum.add(map.removeAny());
        }
        sNum.changeToExtractionMode();

        //This is using a SortingMachine to sort words into alphabetical order
        Comparator<Map.Pair<String, Integer>> compWord = new WordSort();
        SortingMachine<Map.Pair<String, Integer>> sWord = new SortingMachine2<Map.Pair<String, Integer>>(
                compWord);

        //Since the SortingMachine is descending order, it grabs the largest number
        int max = 0;
        Map.Pair<String, Integer> element = sNum.removeFirst();
        max = element.value();
        sWord.add(element);

        //This is adding the other words into the SortingMachine
        for (int i = 0; i < number; i++) {
            if (sNum.size() > 1) {
                Map.Pair<String, Integer> alphaWord = sNum.removeFirst();
                sWord.add(alphaWord);
            }
        }

        //This min depends how many values does the user wants
        int min = 0;
        if (0 < sNum.size()) {
            Map.Pair<String, Integer> minVal = sNum.removeFirst();
            min = minVal.value();
            sWord.add(minVal);
        }
        sWord.changeToExtractionMode();

        //This is getting the fond size with the corresponding words
        while (0 < sWord.size()) {
            Map.Pair<String, Integer> temp = sWord.removeFirst();
            String size = fontSize(max, min, temp.value());
            outputFile.println("<span style=\"cursor:default\" class=\"" + size
                    + "\" title=\"count: " + temp.value() + "\">" + temp.key()
                    + "</span>");
        }

        //The is printing the ending
        outputFile.println("</p>");
        outputFile.println("</div>");
        outputFile.println("</body>");
        outputFile.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Prompt the user for the name of the file to be analyzed.
        out.print("Name of the input file: ");
        String inputFile = in.nextLine();

        //Prompt the user for the name of the file to be accessed.
        out.print("Name of the output file: ");
        String output = in.nextLine();

        out.print("Enter the number of words to be generated tag cloud: ");
        int number = Integer.parseInt(in.nextLine());

        while (number <= 0) {
            out.print("Please enter a number that is positive and not zero: ");
            number = Integer.parseInt(in.nextLine());
        }

        //This allows the developer to write the HTML file.
        SimpleWriter outputFile = new SimpleWriter1L(output);

        //This allows the inputFile to be read.
        SimpleReader reader = new SimpleReader1L(inputFile);

        //This gets the map with the key and value
        Map<String, Integer> map = lineReader(reader);

        //If the number exceeds the number of words in the map
        while (number > map.size()) {
            out.println("The number words exceed the words in the tag cloud");
            out.println("Print a number that is lower than the words of the tag cloud");
            number = Integer.parseInt(in.nextLine());
        }

        //This is printing out the heading of the HTML file.
        header(inputFile, number, outputFile);

        //This is printing the table and the ending of the HTML file.
        body(inputFile, outputFile, output, number, map);

        in.close();
        out.close();
        reader.close();
        outputFile.close();
    }

}

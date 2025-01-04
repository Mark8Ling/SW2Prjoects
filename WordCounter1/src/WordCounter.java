import java.util.Arrays;
import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes a txt file and creates a table with words and instances of words for
 * the file.
 *
 * @author Mark Ling
 *
 */
public final class WordCounter {

    /**
     * Checkstyle.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s in lexicographic order, also ignoring capital
     * letters.
     */
    private static final class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            //use toLowerCase so case is ignored
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    //using javaDoc nextWordOrSeparator from SW1
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
        //creates variables
        int counter = position;
        boolean isSeparator = separators.contains(text.charAt(position));

        //iterates though text to find instances of specific word
        while (counter < text.length()
                && separators.contains(text.charAt(counter)) == isSeparator) {
            counter++;
        }

        return text.substring(position, counter);

    }

    //using generateElements from SW1
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

        charSet.clear();
        for (int i = 0; i < str.length(); i++) {
            charSet.add(str.charAt(i));
        }
    }

    /**
     * Creates an array of the keys from map and sorts them alphabetically.
     *
     * @param m
     *            map that will be used for arr
     *
     * @return a new, alphabetically sorted array
     * @ensures arr is in alphabetical order
     */
    public static String[] alphaArr(Map<String, Integer> m) {
        String[] arr = new String[m.size()];
        Map<String, Integer> temp = m.newInstance();
        temp.transferFrom(m);
        int count = 0;
        //loops through map to add words or add instance value
        while (temp.size() > 0) {
            Map.Pair<String, Integer> p = temp.removeAny();
            arr[count] = p.key();
            m.add(p.key(), p.value());
            count++;
        }
        //sorts array using java method
        Arrays.sort(arr, new StringLT());
        return arr;
    }

    /**
     * Creates a map from the words in the text file.
     *
     * @param file
     *            the input text file
     * @param map
     *            an empty map that will end with having all of the words
     *
     * @updates map
     *
     * @ensures files words = m keys
     *
     */
    public static void createMap(SimpleReader file, Map<String, Integer> map) {

        int pos = 0;
        Set<Character> specialChars = new Set1L<Character>();

        //Characters that are considering separators
        String chars = " \t~`!@#$%^&*()-_+={}[]|;:'<>,.?/";

        //creates a set of the special characters
        generateElements(chars, specialChars);

        while (!file.atEOS()) {
            String line = file.nextLine();
            //resets position on line for each line
            //iterates through words breaking for separators
            pos = 0;
            while (pos < line.length()) {
                //finds the next character or word
                String charOrWord = nextWordOrSeparator(line, pos, specialChars);
                //checks if word
                if (!specialChars.contains(charOrWord.charAt(0))) {
                    //adds to map if it's a new word or adds one to value
                    if (!map.hasKey(charOrWord)) {
                        map.add(charOrWord, 1);
                    } else {
                        int val = map.value(charOrWord);
                        val++;
                        //now replace the value that was associated before
                        map.replaceValue(charOrWord, val);
                    }
                }
                pos = pos + charOrWord.length();
            }
        }
    }

    /**
     * Updates given map to alphabetical order.
     *
     * @param arr
     *            given alphabetical arr
     *
     * @param map
     *            map that will be updated
     * @updates m
     * @ensures map is in alphabetical order
     */
    private static void alphaMap(String[] arr, Map<String, Integer> map) {

        Map<String, Integer> copy = new Map1L<String, Integer>();
        //loops through arr and map to alphabetically order map
        for (int i = 0; i < arr.length; i++) {
            copy.add(arr[i], map.value(arr[i]));
        }
        map.transferFrom(copy);
    }

    /**
     * Creates an HTML page with a table based on the map as a parameter.
     *
     * @param output
     *            the file this is being output to
     *
     * @param map
     *            the map that has all the words and counts
     *
     * @param arr
     *            the array that has all of the words in alphabetical order
     * @param inFile
     *            the text file input
     * @clears m
     * @ensures {HTML table elements = m}
     *
     *
     */
    public static void createTable(SimpleWriter output, SimpleReader inFile,
            Map<String, Integer> map, String[] arr) {
        output.println("<html><head><title>Words Counted in " + inFile
                + "</title></head><body>");
        output.println("<h2>Words Counted in " + inFile + "</h2><hr />");
        output.println("<table border=\"1\"><tr><th>Words</th><th>Counts</th></tr>");

        int i = 0;
        while (map.size() > 0) {
            Pair<String, Integer> p = map.remove(arr[i]);
            output.println("<tr><td>" + p.key() + "</td><td>" + p.value() + "</td></tr>");
            i++;
        }

        output.println("</table></body></html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            The command line arguments; unused here.
     */
    public static void main(String[] args) {

        //opens sw and sr
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        //asks for input and outfile (in data folder)
        out.println("Enter the name of the input file: ");
        String inName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L("data/" + inName);
        out.println("Enter the name of the output file: ");
        SimpleWriter outFile = new SimpleWriter1L("data/" + in.nextLine());
        //creates map
        Map<String, Integer> wordMap = new Map1L<String, Integer>();
        //calls methods to complete specifications
        createMap(inFile, wordMap);
        String[] arr = alphaArr(wordMap);
        alphaMap(arr, wordMap);
        createTable(outFile, inFile, wordMap, arr);
        out.print("Check the data folder");
        //closes all open sws and srs
        in.close();
        out.close();
    }
}

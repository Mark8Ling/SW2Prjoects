import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * This software is designed to generate a glossary using an input file, with
 * the resulting glossary saved in a specified output folder. The glossary file
 * is named index.html, and individual terms are stored in a file named
 * terms.html.
 *
 * @author Mark Ling
 */
public final class Glossary {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     *
     * Comparator used for ordering the words.
     *
     */

    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String arg0, String arg1) {
            return arg0.compareTo(arg1);
        }
    }

    /**
     *
     * Generates the header for the main index file.
     *
     * @param index
     *            The HTML page for the glossary
     *
     */
    public static void createGlossaryPage(SimpleWriter index) {
        // Opens Glossary
        index.println(
                "<!DOCTYPE html>\n<html>\n<head>\n<title>Glossary</title></head>\n<body>"
                        + "<h2>Glossary</h2>\n<hr/>\n<h3>Index</h3>");
    }

    /**
     *
     * Generates the closing page for the main index file.
     *
     * @param index
     *            The HTML page for the glossary
     *
     */
    private static void closeGlossaryPage(SimpleWriter index) {
        // Closes Glossary
        index.println("</ul>\n</body>\n</html>");
    }

    /**
     * Generates an HTML page dedicated to a specific word.
     *
     * @param definition
     *            The meaning of the associated word.
     * @param word
     *            The word for which the page is being generated.
     * @param out
     *            The output file stream used for the HTML page.
     */
    public static void createGlossaryWordPage(String word, String definition,
            SimpleWriter out) {
        // html documentation for opening tags and look of word and definition
        out.println("<html>\n" + "<head>\n" + "<title>" + word + "</title>\n"
                + "</head>\n" + "<body>\n" + "<h2><b><i><font color=\"red\">"
                + word + "</font></i></b></h2>\n" + "<blockquote>" + definition
                + "</blockquote>\n" + "<hr />\n"
                + "<p>Return to <a href=\"index.html\">index</a>.</p>\n"
                + "</body>\n" + "</html>");

    }

    /**
     * Retrieves words and adds them to a Queue.
     *
     * @param words
     *            The queue where the definitions will be stored.
     * @param in
     *            The input file stream.
     */
    public static void getGlossaryWords(Queue<String> words, SimpleReader in) {
        //in not empty
        while (!in.atEOS()) {
            //gets next value in the input
            String test = in.nextLine().trim();
            //makes sure the trimmed line does not contain white space
            if (!test.isEmpty() && !test.contains(" ")) {
                //adding into queue
                words.enqueue(test);
            }
        }
    }

    /**
     * Retrieves word definitions from the input and adds them to a Queue for
     * storage.
     *
     * @param definitions
     *            The queue where the definitions will be stored.
     * @param in
     *            The input file stream.
     */
    public static void getGlossaryDefinitions(Queue<String> definitions,
            SimpleReader in) {
        //in not empty
        while (!in.atEOS()) {
            //creates empty string to store definitions
            String definition = "";
            //reads next line and remove whitespaces
            String test = in.nextLine().trim();
            //processes lines until empty line is found
            while (!test.isEmpty()) {
                if (test.contains(" ")) {
                    definition += test + " ";
                }
                //checks for more in input stream
                if (!in.atEOS()) {
                    test = in.nextLine().trim();
                } else {
                    //sets value to empty string to exit loop
                    test = "";
                }
            }
            //puts definitions into the queue
            definitions.enqueue(definition);
        }
    }

    /**
     * Generates a list on the index page.
     *
     * @param index
     *            The output stream for the index page.
     * @param words
     *            The queue of words.
     */
    public static void createWordList(SimpleWriter index, Queue<String> words) {
        //creates the unordered list
        index.println("<ul>");
        //finds length of the queue
        int size = words.length();
        //goes through all of the words in the queue
        int itt = 0;
        while (itt < size) {
            //finds the first word in the queue
            String word = words.front();
            //creates a list with a link for the page in the word
            index.println(
                    "<li><a href=" + word + ".html>" + word + "</a></li>");
            //goes to the next word in the iteration
            words.rotate(1);
            itt++;
        }
    }

    /**
     * Substitutes words present in the glossary with hyperlink to their
     * respective glossary pages within the given definition.
     *
     * @param words
     *            The words listed in the glossary.
     * @param definition
     *            The text of the definition to be examined and updated if
     *            necessary.
     * @return The modified definition containing links where applicable.
     */
    public static String replaceWordWithHyperlink(Queue<String> words,
            String definition) {
        String linkedDefinition = "";
        //splits definition into an array
        String[] itt = definition.split(" ");
        //goes through each word in the parsed definition
        for (int i = 0; i < itt.length; i++) {
            for (String word : words) {
                //finds matching word and create page with link to word
                if (itt[i].equals(word) || itt[i].equals(word + ",")) {
                    String link = "<a href=" + word + ".html>" + word
                            + (itt[i].endsWith(",") + ":") + "</a>";
                    //replaces  word with the hyperlink
                    itt[i] = link;
                }
            }
        }
        linkedDefinition = String.join(" ", itt);
        linkedDefinition = linkedDefinition.replace("[", "").replace("]", "");
        return linkedDefinition;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        //creates input and output
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //creates queues for words and definitions
        Queue<String> words = new Queue1L<>();
        Queue<String> definitions = new Queue1L<>();
        //asks for file specifics
        out.println("Enter The Location & Name of Input File: ");
        String name = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(name);
        out.println("Enter The Name of the Output Folder: ");
        String folder = in.nextLine();
        //create the output for the index file in the folder
        SimpleWriter index = new SimpleWriter1L(folder + "/index.html");
        //create the glossary page
        createGlossaryPage(index);
        //gets words and puts them into  queue
        getGlossaryWords(words, fileIn);
        //creates a new file stream for the definitions
        SimpleReader fileIn2 = new SimpleReader1L(name);
        fileIn.close();
        getGlossaryDefinitions(definitions, fileIn2);
        //seperates empty words and words with white space into different queues
        int length1 = words.length();
        Queue<String> empties = new Queue1L<>();
        Queue<String> defs = new Queue1L<>();
        for (int i = 0; i < length1; i++) {
            String currentWord = words.dequeue();
            if (currentWord.equals("")) {
                empties.enqueue(currentWord);
            } else {
                int characterCount = 0;
                for (int f = 0; f < currentWord.length(); f++) {
                    if (currentWord.charAt(f) == ' ') {
                        characterCount++;
                    }
                }
                if (characterCount != 0) {
                    defs.enqueue(currentWord);
                } else {
                    words.enqueue(currentWord);
                }
            }
        }
        //create individual pages for words with definitions inside
        int definitionLength = definitions.length();
        int count = 0;
        while (count < definitionLength) {
            String temporaryWord = words.front();
            String temporaryDefinition = replaceWordWithHyperlink(words,
                    definitions.dequeue());
            SimpleWriter tempOut = new SimpleWriter1L(
                    folder + "/" + temporaryWord + ".html");
            createGlossaryWordPage(temporaryWord, temporaryDefinition, tempOut);
            words.rotate(1);
            count++;
            tempOut.close();
        }
        //sorts words and indexes lists on the glossary page
        Comparator<String> termComparator = new StringLT();
        words.sort(termComparator);
        createWordList(index, words);
        //closes everything
        closeGlossaryPage(index);
        in.close();
        fileIn2.close();
        out.close();
        index.close();
    }
}

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer.isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF")
                : "" + "Violation of: <\"IF\"> is proper prefix of tokens";

        //This is getting the If
        tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Condition not found.");
        Condition cond = parseCondition(tokens.dequeue());

        //This is getting the THEN
        Reporter.assertElseFatalError(tokens.dequeue().equals("THEN"), "THEN not found");

        //This makes the block and process it
        Statement blockIF = s.newInstance();
        blockIF.parseBlock(tokens);

        //block must end with an END if no ELSE or continue with ELSE
        Reporter.assertElseFatalError(
                tokens.front().equals("ELSE") || tokens.front().equals("END"),
                "ELSE or END IF not found.");

        //The if has else, process the else block
        if (tokens.front().equals("ELSE")) {
            tokens.dequeue();
            Statement blockELSE = s.newInstance();
            blockELSE.parseBlock(tokens);

            // check for END IF
            Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                    "END not found.");
            Reporter.assertElseFatalError(tokens.dequeue().equals("IF"), "IF not found.");

            // assemble the block
            s.assembleIfElse(cond, blockIF, blockELSE);

        } else {

            // there is no else, just check for end and assemble
            tokens.dequeue();
            Reporter.assertElseFatalError(tokens.dequeue().equals("IF"), "IF not found.");
            s.assembleIf(cond, blockIF);
        }

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE")
                : "" + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        //This is getting the while
        tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Condition not found.");
        Condition cond = parseCondition(tokens.dequeue());

        //This is checking if it has DO
        Reporter.assertElseFatalError(tokens.dequeue().equals("DO"), "DO not found.");

        //Make and process the block
        Statement block = s.newInstance();
        block.parseBlock(tokens);

        //This is checking for END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"), "END not found.");
        Reporter.assertElseFatalError(tokens.dequeue().equals("WHILE"),
                "WHILE not found.");

        //Assembling the block
        s.assembleWhile(cond, block);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front())
                : "" + "Violation of: identifier string is proper prefix of tokens";

        //assemble call since front must be an identifier checked by assert
        s.assembleCall(tokens.dequeue());

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //This will check what statement is the token
        if (tokens.front().equals("IF")) {
            parseIf(tokens, this);
        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, this);
        } else if (Tokenizer.isIdentifier(tokens.front())) {

            //This has to be valid to call this
            parseCall(tokens, this);
        } else {
            Reporter.fatalErrorToConsole("Statement not found.");
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        int i = 0;

        //statement can be either an IF, WHILE, or CALL
        while (tokens.front().equals("IF") || tokens.front().equals("WHILE")
                || Tokenizer.isIdentifier(tokens.front())) {
            Statement block = this.newInstance();

            //This is process the block and add
            block.parse(tokens);
            this.addToBlock(i, block);

            i++;
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}

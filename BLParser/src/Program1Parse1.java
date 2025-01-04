import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION")
                : "" + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";
        //Checks if token is Instruction and dequeue it
        Reporter.assertElseFatalError(tokens.front().equals("INSTRUCTION"),
                "Token should be instruction");
        tokens.dequeue();
        //Checks if tokens are an identifier and dequeues it
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.front()),
                "Token should be an identifier");
        String identifier = tokens.dequeue();
        //Checks if identifier is a primitive call using boolean
        String primCall = "move,turnleft,turnright,turnback,skip";
        boolean isPrim = primCall.indexOf(identifier) != -1;
        Reporter.assertElseFatalError(!isPrim, "Identifier should be primitive call");
        //Checks if token is Is and dequeues it
        Reporter.assertElseFatalError(tokens.front().equals("IS"), "Token should be is");
        tokens.dequeue();
        //Parses through blocks that follow if statements
        body.parseBlock(tokens);
        //Checks if token is End and dequeues it
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Token should be End");
        tokens.dequeue();
        //Checks if token is original and dequeues it
        Reporter.assertElseFatalError(tokens.front().equals(identifier),
                "Token is not Original");
        tokens.dequeue();
        //Returns the instruction
        return identifier;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        //Creates maps to hold statement bodies
        Map<String, Statement> context = this.newContext();
        //Checks if token is Program and dequeue it
        Reporter.assertElseFatalError(tokens.front().equals("PROGRAM"),
                "Token should be PROGRAM");
        tokens.dequeue();
        //Sets the name of the premitive token
        String premitive = tokens.dequeue();
        this.setName(premitive);
        //Checks if token is IS and dequeue it
        Reporter.assertElseFatalError(tokens.front().equals("IS"), "Token should be IS");
        tokens.dequeue();
        //Checks multiple INSTRUCTION tokens and parses through
        //them using parseInstruction method and adds them to the map
        while (tokens.front().equals("INSTRUCTION")) {
            Statement identifer = this.newBody();
            String instruction = parseInstruction(tokens, identifer);
            context.add(instruction, identifer);
        }
        //Updates the program with the parsed instructions
        this.swapContext(context);
        //Checks if token is BEGIN and dequeue it
        Reporter.assertElseFatalError(tokens.front().equals("BEGIN"),
                "Token should be BEGIN");
        tokens.dequeue();
        //Parses through blocks using parseBlock method and swaps it into the program
        Statement s = this.newBody();
        s.parseBlock(tokens);
        this.swapBody(s);
        //Checks if token is the END and dequeue it
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Token should be END");
        tokens.dequeue();
        //Checks if token match and dequeues the matching tokens
        Reporter.assertElseFatalError(tokens.front().equals(premitive),
                "Tokens should Match");
        tokens.dequeue();
        //Checks if input is over as it should
        Reporter.assertElseFatalError(tokens.front().equals("### END OF INPUT ###"),
                "Input should be Done");

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}

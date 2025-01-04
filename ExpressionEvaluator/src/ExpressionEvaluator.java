import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of an expression consisting of numbers,
 * arithmetic operators, and parentheses.
 *
 * @author Put your name here
 *
 */
public final class ExpressionEvaluator {

    /**
     * Base used in number representation.
     */
    private static final int RADIX = 10;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExpressionEvaluator() {
    }

    /**
     * Evaluates an expression and returns its value.
     *
     * @param source
     *            the StringBuilder that starts with an expr string
     * @return value of the expression
     * @updates source
     */
    public static int valueOfExpr(StringBuilder source) {
        int value = valueOfTerm(source);

        while (source.charAt(0) == '+' || source.charAt(0) == '-') {
            char op = source.charAt(0);
            source.deleteCharAt(0);

            if (op == '+') {
                value += valueOfTerm(source);
            } else {
                value -= valueOfTerm(source);
            }
        }
        return value;
    }

    /**
     *
     * Evaluates a term and returns its value.
     *
     * @param source
     *            the StringBuilder that starts with a term string
     * @return value of the term
     * @updates source
     */
    private static int valueOfTerm(StringBuilder source) {
        int value = valueOfFactor(source);

        while ((source.charAt(0) == '*' || source.charAt(0) == '/')) {
            char op = source.charAt(0);

            source.deleteCharAt(0);

            if (op == '*') {
                value *= valueOfFactor(source);
            } else {
                value /= valueOfFactor(source);
            }
        }
        return value;
    }

    /**
     *
     * Evaluates a factor and returns its value.
     *
     * @param source
     *            the StringBuilder that starts with a factor string
     * @return value of the factor
     * @updates source
     */
    private static int valueOfFactor(StringBuilder source) {
        int value = 0;

        if (source.charAt(0) == '(') {
            source.deleteCharAt(0);
            value = valueOfExpr(source);
            source.deleteCharAt(0);
        } else {
            value = valueOfDigitSeq(source);
        }
        return value;
    }

    /**
     *
     * Evaluates a digit sequence and returns its value.
     *
     * @param source
     *            the StringBuilder that starts with a digit-seq string
     * @return value of the digit sequence
     * @updates source
     */
    private static int valueOfDigitSeq(StringBuilder source) {
        String str = "";
        int value = 0;

        while (Character.isDigit(source.charAt(0))) {
            str += Integer.toString(valueOfDigit(source));
        }
        value = Integer.parseInt(str);
        return value;
    }

    /**
     *
     * Evaluates a digit and returns its value.
     *
     * @param source
     *            the StringBuilder that starts with a digit
     * @return value of the digit
     * @updates source
     */
    private static int valueOfDigit(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = Character.digit(source.charAt(0), RADIX);

        source.deleteCharAt(0);

        return value;
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
        out.print("Enter an expression followed by !: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            /*
             * Parse and evaluate the expression after removing all white space
             * (spaces and tabs) from the user input.
             */
            int value = valueOfExpr(new StringBuilder(source.replaceAll("[ \t]", "")));
            out.println(source.substring(0, source.length() - 1) + " = " + value);
            out.print("Enter an expression followed by !: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}

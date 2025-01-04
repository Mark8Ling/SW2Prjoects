import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Harry Lian and Mark Ling
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        //Uses default constructor to create rep
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        //Takes Int and put it into a String and puts it into a rep
        if (i == (0)) {
            this.rep = "";
        } else {
            this.rep = Integer.toString(i);
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*")
                : "" + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        //Takes String and keeps it a String inside the rep
        if (s.equals("0")) {
            this.rep = "";
        } else {
            this.rep = s;
        }

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        //Take NN and turns it into a String inside the rep
        if (n.isZero()) {
            this.rep = "";
        } else {
            this.rep = n.toString();
        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {

        //Refreshes the rep
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3
                : "" + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";

        //this is adding the digit of K to the string
        this.rep = this.rep + k;

        //if this.rep is 0, then the string should be nothing
        if (this.rep.equals("0")) {
            this.rep = "";
        }

    }

    @Override
    public final int divideBy10() {

        //initializing the return value and return the last digit of the string
        int answer = 0;

        //if there is something a number in the string
        if (!this.rep.isEmpty()) {

            //this is getting the last number of the string
            String onesPlace = this.rep.substring(this.rep.length() - 1);

            //this is changing the original string lose its one's
            this.rep = this.rep.substring(0, this.rep.length() - 1);

            //this is changing the String of a number to an int
            answer = Integer.parseInt(onesPlace);
        }

        return answer;
    }

    @Override
    public final boolean isZero() {

        //there is nothing in the String
        boolean answer = this.rep.equals("");

        return answer;
    }

}

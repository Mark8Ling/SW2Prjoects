import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Testing with multiplyBy10 other numbers with other number and nothing in
     * n.
     */
    @Test
    public final void multiplyBy10WithOtherNumbers() {
        final int number = 9;

        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(number);

        n.multiplyBy10(number);
        assertEquals(n, nExpected);
    }

    /**
     * Testing with multiplyBy10 with 0 and nothing in n.
     */
    @Test
    public final void multiplyBy10With0() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);

        assertEquals(n, nExpected);
    }

    /**
     * Testing with multiplyBy10 with numbers in n and 0.
     */
    @Test
    public final void multiplyBy10WithNumberAnd0() {
        final int number = 5;

        NaturalNumber n = this.constructorTest(number);
        NaturalNumber nExpected = this.constructorRef(number);

        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);

        assertEquals(n, nExpected);
    }

    /**
     * Testing with multiplyBy10 with numbers in n and 0.
     */
    @Test
    public final void multiplyBy10WithNumberAndAnotherNumber() {
        final int number = 5;
        NaturalNumber n = this.constructorTest(number);
        NaturalNumber nExpected = this.constructorRef(number);
        n.multiplyBy10(2);
        nExpected.multiplyBy10(2);
        assertEquals(n, nExpected);
    }

    /**
     * Testing with multiplyBy10 with numbers in n and 0.
     */
    @Test
    public final void multiplyBy10With0AndAnotherNumber() {
        final int number = 9;
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.multiplyBy10(number);
        nExpected.multiplyBy10(number);
        assertEquals(n, nExpected);
    }

    /**
     * Testing with multiplyBy10 with the max number of integer in n and a
     * number.
     */
    @Test
    public final void multiplyBy10WithMaxBoundary() {
        final int number = 9;

        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);

        n.multiplyBy10(number);
        nExpected.multiplyBy10(number);

        assertEquals(n, nExpected);
    }

    /**
     * Testing divideBy10 with nothing in n.
     */
    @Test
    public final void divideBy10With0() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        int nOnesPlace = n.divideBy10();
        int nExpectedOnesPlace = nExpected.divideBy10();

        assertEquals(n, nExpected);
        assertEquals(nOnesPlace, nExpectedOnesPlace);
    }

    /**
     * Testing divideBy10 with a number in n.
     */
    @Test
    public final void divideBy10WithANumber() {
        final int number = 12312;

        NaturalNumber n = this.constructorTest(number);
        NaturalNumber nExpected = this.constructorRef(number);

        int nOnesPlace = n.divideBy10();
        int nExpectedOnesPlace = nExpected.divideBy10();

        assertEquals(n, nExpected);
        assertEquals(nOnesPlace, nExpectedOnesPlace);
    }

    /**
     * Testing divideBy10 with the max number of integer in n.
     */
    @Test
    public final void divideBy10WithMaxBoundary() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);

        int nOnesPlace = n.divideBy10();
        int nExpectedOnesPlace = nExpected.divideBy10();

        assertEquals(n, nExpected);
        assertEquals(nOnesPlace, nExpectedOnesPlace);
    }

    /**
     * Testing isZero with the number 0.
     */
    @Test
    public final void isZeroWith0() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);

        assertEquals(n.isZero(), nExpected.isZero());
        assertEquals(n.isZero(), true);
    }

    /**
     * Testing isZero with a non-zero number.
     */
    @Test
    public final void isZeroWithNonZero() {
        final int number = 3;
        NaturalNumber n = this.constructorTest(number);
        NaturalNumber nExpected = this.constructorRef(number);
        assertEquals(n.isZero(), nExpected.isZero());
        assertEquals(n.isZero(), false);
    }

    /**
     * Testing isZero with integer max number.
     */
    @Test
    public final void isZeroWithMaxNumber() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(n.isZero(), nExpected.isZero());
        assertEquals(n.isZero(), false);
    }

    /**
     * Testing Default NN Constructor.
     */
    @Test
    public final void naturalNumberDefault() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(n, nExpected);
    }

    /**
     * Testing Int NN Constructor.
     */
    @Test
    public final void naturalNumberInt() {
        final int five = 5;
        NaturalNumber n = this.constructorTest(five);
        NaturalNumber nExpected = this.constructorRef(five);
        assertEquals(n, nExpected);
    }

    /**
     * Testing Int NN Constructor with zero.
     */
    @Test
    public final void naturalNumberIntZero() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(n, nExpected);
    }

    /**
     * Testing String NN Constructor.
     */
    @Test
    public final void naturalNumberString() {
        String number = "1";
        NaturalNumber n = this.constructorTest(number);
        NaturalNumber nExpected = this.constructorRef(number);
        assertEquals(n, nExpected);
    }

    /**
     * Testing String NN Constructor with zero.
     */
    @Test
    public final void naturalNumberStringZero() {
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(n, nExpected);
    }

    /**
     * Testing NN NN Constructor.
     */
    @Test
    public final void naturalNumberNN() {
        final int fiv = 5;
        NaturalNumber five = this.constructorTest(fiv);

        NaturalNumber n = this.constructorTest(five);
        NaturalNumber nExpected = this.constructorRef(five);
        assertEquals(n, nExpected);
    }

    /**
     * Testing NN NN Constructor with zero.
     */
    @Test
    public final void naturalNumberNNZero() {
        NaturalNumber zero = this.constructorTest(0);

        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        assertEquals(n, nExpected);
    }

}

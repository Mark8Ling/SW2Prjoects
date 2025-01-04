import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    @Test
    public final void testConstructor() {
        Sequence<String> s1 = this.constructorTest();
        Sequence<String> sExpected = this.constructorRef();
        assertEquals(s1, sExpected);
    }

    @Test
    public final void add1() {
        Sequence<String> s1 = this.createFromArgsTest("red", "orange", "yellow");
        Sequence<String> sExpected = this.createFromArgsRef("green", "red", "orange",
                "yellow");
        s1.add(0, "green");
        assertEquals(s1, sExpected);
    }

    @Test
    public final void add2() {
        Sequence<String> s1 = this.createFromArgsTest();
        Sequence<String> sExpected = this.createFromArgsRef("green");
        s1.add(0, "green");
        assertEquals(s1, sExpected);
    }

    @Test
    public final void remove1() {
        Sequence<String> s1 = this.createFromArgsTest("word", "letter", "alphabet");
        Sequence<String> sExpected = this.createFromArgsRef("word", "alphabet");
        s1.remove(1);
        assertEquals(s1, sExpected);
    }

    @Test
    public final void remove2() {
        Sequence<String> s1 = this.createFromArgsTest("1");
        Sequence<String> sExpected = this.createFromArgsRef();
        s1.remove(0);
        assertEquals(s1, sExpected);
    }

    @Test
    public final void length1() {
        Sequence<String> s1 = this.createFromArgsTest("word", "letter", "alphabet");
        assertEquals(3, s1.length());
    }

    @Test
    public final void length2() {
        Sequence<String> s1 = this.createFromArgsTest();
        assertEquals(0, s1.length());
    }
}

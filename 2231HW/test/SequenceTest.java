import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

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

    // TODO - add test cases for constructor, add, remove, and length

    @Test
    public void testLength() {
       Sequence<Integer> sequence = new Sequence1L<>();
        assertEquals(0, sequence.length());
    }

    @Test
    public void testAdd() {
        Sequence<Integer> sequence = new Sequence1L<>();
        sequence.add(10, 0);
        assertEquals(1, sequence.length());
        assertEquals(10, sequence.entry(0));
    }

    @Test
    public void testAddMulti() {
        Sequence<Integer> sequence = new Sequence1L<>();
        sequence.add(10);
        sequence.add(20);
        sequence.add(30);
        assertEquals(3, sequence.length());
        assertEquals(10, sequence.get(0));
        assertEquals(20, sequence.get(1));
        assertEquals(30, sequence.get(2));
    }

    @Test
    public void testAddSpec() {
        Sequence<Integer> sequence = new Sequence1L<>();
        sequence.add(10);
        sequence.add(30);
        sequence.add(20, 1);
        assertEquals(3, sequence.length());
        assertEquals(10, sequence.get(0));
        assertEquals(20, sequence.get(1));
        assertEquals(30, sequence.get(2));
    }

    @Test
    public void testRemove() {
        Sequence<Integer> sequence = new Sequence1L<>();
        sequence.add(10);
        sequence.add(20);
        sequence.add(30);
        sequence.remove(1);
        assertEquals(2, sequence.length());
        assertFalse(sequence.contains(20));
    }
    @Test
    public void testRemoveOff() {
        Sequence<Integer> sequence = new Sequence1L<>();
        sequence.add(10);
        sequence.add(20);
        sequence.remove(10);
        assertEquals(2, sequence.length());
    }

    @Test
    public void testLengthTwice() {
        Sequence sequence = new Sequence();
        sequence.add(10);
        sequence.add(20);
        sequence.add(30);
        assertEquals(3, sequence.length());
        sequence.remove(1);
        assertEquals(2, sequence.length());
    }
}

}

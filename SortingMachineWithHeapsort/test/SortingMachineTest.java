import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static final class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /**
     * Tests the Constructor.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * Tests add with empty.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "a");
        m.add("a");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /**
     * Tests add with size one.
     */
    @Test
    public final void testAddSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "a", "b");
        m.add("b");
        assertEquals(mExpected, m);
    }

    /**
     * Tests add with multiple elements.
     */
    @Test
    public final void testAddMulti() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "a", "b",
                "c");
        m.add("b");
        m.add("c");
        assertEquals(mExpected, m);
    }

    /**
     * Tests size with 1 elements.
     */
    @Test
    public final void testSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");

        assertEquals(1, m.size());
    }

    /**
     * Tests size with 2 elements.
     */
    @Test
    public final void testSize2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a", "b");
        assertEquals(2, m.size());
    }

    /**
     * Tests size with 3 elements.
     */
    @Test
    public final void testSize3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a", "b", "c");
        final int three = 3;
        assertEquals(three, m.size());
    }

    /**
     * Tests removeFirst with multiple elements.
     */
    @Test
    public final void testRemoveFirstMulti() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a", "b", "c");
        m.removeFirst();
        m.removeFirst();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "a");
        assertEquals(mExpected, m);
    }

    /**
     * Tests removeFirst with one elements left.
     */
    @Test
    public final void testRemoveFirst1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a", "b");
        m.removeFirst();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "a");
        assertEquals(mExpected, m);
    }

    /**
     * Tests removeFirst with two elements left.
     */
    @Test
    public final void testRemoveFirst2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a", "b", "c");
        m.removeFirst();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "a", "b");
        assertEquals(mExpected, m);
    }

    /**
     * Tests isinInsertionMode.
     */
    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        assertTrue(m.isInInsertionMode());
    }

    /**
     * Tests changeToExtractMode.
     */
    @Test
    public final void testChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a", "b", "c");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "a", "b",
                "c");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * Tests order when empty.
     */
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(ORDER, m.order());
    }

    /**
     * Tests order when empty.
     */
    @Test
    public final void testOrderSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple", "banana",
                "cherry");
        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testOrderExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        m.changeToExtractionMode();
        assertEquals(ORDER, m.order());
    }

}

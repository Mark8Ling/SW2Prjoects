import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Harry Lian and Mark Ling
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * This is testing the constructor.
     */
    @Test
    public final void testConstructor() {
        Set<String> s1 = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(s1, sExpected);
    }

    /**
     * This is testing to add method with one element.
     */
    @Test
    public final void testAddEmpty() {
        Set<String> testSet = this.createFromArgsTest();
        Set<String> refSet = this.createFromArgsRef("a");

        testSet.add("a");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to add method with an empty element.
     */
    @Test
    public final void testAdd1() {
        Set<String> testSet = this.createFromArgsTest("a");
        Set<String> refSet = this.createFromArgsRef("a", "b");

        testSet.add("b");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to add multiple variable.
     */
    @Test
    public final void testAddMulti() {
        Set<String> testSet = this.createFromArgsTest();
        Set<String> refSet = this.createFromArgsRef("a", "b");

        testSet.add("a");
        testSet.add("b");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to remove one variable.
     */
    @Test
    public final void testRemove() {
        Set<String> testSet = this.createFromArgsTest("a", "b");
        Set<String> refSet = this.createFromArgsRef("a");

        testSet.remove("b");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to remove one variable and left with nothing in the set.
     */
    @Test
    public final void testRemoveToEmpty() {
        Set<String> testSet = this.createFromArgsTest("a");
        Set<String> refSet = this.createFromArgsRef();

        testSet.remove("a");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to remove one variable and left with nothing in the set.
     */
    @Test
    public final void testRemoveMulti() {
        Set<String> testSet = this.createFromArgsTest("a", "b", "c");
        Set<String> refSet = this.createFromArgsRef("a");

        testSet.remove("c");
        testSet.remove("b");

        assertEquals(refSet, testSet);
    }

    /**
     * This is testing to removeAny from a selection.
     */
    @Test
    public final void testRemoveAny() {
        Set<String> testSet = this.createFromArgsTest("a", "b", "c");
        Set<String> refSet = this.createFromArgsRef("a", "b");

        testSet.removeAny();

        assertEquals(refSet.size(), testSet.size());
    }

    /**
     * This is testing to removeAny on one element.
     */
    @Test
    public final void testRemoveAnyToEmpty() {
        Set<String> testSet = this.createFromArgsTest("a");
        Set<String> refSet = this.createFromArgsRef();

        testSet.removeAny();

        assertEquals(refSet.size(), testSet.size());
    }

    /**
     * This is testing to removeAny on one element.
     */
    @Test
    public final void testRemoveAnyToMulti() {
        Set<String> testSet = this.createFromArgsTest("a", "b", "c");
        Set<String> refSet = this.createFromArgsRef("a");

        testSet.removeAny();
        testSet.removeAny();

        assertEquals(refSet.size(), testSet.size());
    }

    /**
     * This is testing if the set contains the word.
     */
    @Test
    public final void testContainsTrue() {
        Set<String> testSet = this.createFromArgsTest("a", "b");
        assertTrue(testSet.contains("a"));
    }

    /**
     * This is testing if the set does not have the word.
     */
    @Test
    public final void testContainsFalse() {
        Set<String> testSet = this.createFromArgsTest("a", "b");
        assertFalse(testSet.contains("c"));
    }

    /**
     * This is testing an empty set for contain method.
     */
    @Test
    public final void testContainsEmpty() {
        Set<String> testSet = this.createFromArgsTest();
        assertFalse(testSet.contains("a"));
    }

    /**
     * This is testing the size of more elements in a set.
     */
    @Test
    public final void testSize() {
        Set<String> testSet = this.createFromArgsTest("a", "b");
        assertEquals(2, testSet.size());
    }

    /**
     * This is testing with set size 0.
     */
    @Test
    public final void testSizeZero() {
        Set<String> testSet = this.createFromArgsTest();
        assertEquals(0, testSet.size());
    }

    @Test
    public final void testSizeBtwn() {
        Set<String> testSet = this.createFromArgsTest("a", "b");
        assertEquals(2, testSet.size());
        testSet.add("c");
        assertEquals(3, testSet.size());
    }
}

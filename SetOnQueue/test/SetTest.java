import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Mark Ling
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    @Test
    public void testAdd() {
        Set<String> testSet = this.createFromArgsTest();
        Set<String> refSet = this.createFromArgsRef("apple");

        testSet.add("apple");

        assertEquals(refSet, testSet);
    }

    @Test
    public void testAddMulti() {
        Set<String> testSet = this.createFromArgsTest();
        Set<String> refSet = this.createFromArgsRef("apple", "banana");

        testSet.add("apple");
        testSet.add("banana");

        assertEquals(refSet, testSet);
    }

    @Test
    public void testAddDup() {
        Set<String> testSet = this.createFromArgsTest("apple");
        Set<String> refSet = this.createFromArgsRef("apple");

        testSet.add("apple");
        assertEquals(refSet, testSet);
    }

    @Test
    public void testRemove() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        Set<String> refSet = this.createFromArgsRef("apple");

        testSet.remove("banana");

        assertEquals(refSet, testSet);
    }

    @Test
    public void testRemoveNonExistingElement() {
        Set<String> testSet = this.createFromArgsTest("apple");
        Set<String> refSet = this.createFromArgsRef("apple");

        testSet.remove("banana");
        assertEquals(refSet, testSet);
    }

    @Test
    public void testRemoveAny() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana", "cherry");
        Set<String> refSet = this.createFromArgsRef("apple", "banana");

        testSet.removeAny();

        assertEquals(refSet.size(), testSet.size());
    }

    @Test
    public void testContainsTrue() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        assertTrue(testSet.contains("apple"));
    }

    @Test
    public void testContainsFalse() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        assertFalse(testSet.contains("cherry"));
    }

    @Test
    public void testSizeEmpty() {
        Set<String> testSet = this.constructorTest();
        assertEquals(0, testSet.size());
    }

    @Test
    public void testSize() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        assertEquals(2, testSet.size());
    }

    @Test
    public void testSizeAfterAddAndRemove() {
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        testSet.add("cherry");
        assertEquals(3, testSet.size());

        testSet.remove("banana");
        assertEquals(2, testSet.size());
    }
}

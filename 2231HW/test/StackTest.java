import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     * Test case for push, ensure stack contains pushed element.
     */
    @Test
    public void testPush_oneElement() {
        Stack2<T> testStack = this.createFromArgsTest();
        Stack2<T> refStack = this.createFromArgsRef("a");
        testStack.push("a");
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for push, ensure length is updated after multiple pushes.
     */
    @Test
    public void testPush_multipleElements() {
        Stack2<T> testStack = this.createFromArgsTest();
        Stack2<T> refStack = this.createFromArgsRef("a", "b", "c");
        testStack.push("a");
        testStack.push("b");
        testStack.push("c");
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for push, ensure stack handles multiple pushes correctly.
     */
    @Test
    public void testPush_order() {
        Stack2<T> testStack = this.createFromArgsTest();
        testStack.push("a");
        testStack.push("b");
        assertEquals(2, testStack.length());
    }

    /**
     * Test case for pop, removing from a stack with one element.
     */
    @Test
    public void testPop_oneElement() {
        Stack2<T> testStack = this.createFromArgsTest("a", "b", "c");
        Stack2<T> refStack = this.createFromArgsRef("a", "b");
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for pop, ensure multiple pops reduce length correctly.
     */
    @Test
    public void testPop_multipleElements() {
        Stack2<T> testStack = this.createFromArgsTest("a", "b", "c");
        Stack2<T> refStack = this.createFromArgsRef("a");
        testStack.pop();
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for pop, ensure popping all elements leaves the stack empty.
     */
    @Test
    public void testPop_untilEmpty() {
        Stack2<T> testStack = this.createFromArgsTest("a", "b", "c");
        Stack2<T> refStack = this.createFromArgsRef();
        testStack.pop();
        testStack.pop();
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for length, ensure length is 0 for empty stack.
     */
    @Test
    public void testLength_empty() {
        Stack2<T> testStack = this.createFromArgsTest();
        assertEquals(0, testStack.length());
    }

    /**
     * Test case for length, ensure length is correct after push.
     */
    @Test
    public void testLength_afterPush() {
        Stack2<T> testStack = this.createFromArgsTest("a");

        assertEquals(1, testStack.length());
    }

    /**
     * Test case for length, ensure length is correct after push and pop.
     */
    @Test
    public void testLength_afterPushAndPop() {
        Stack2<T> testStack = this.constructorTest("a");
        testStack.push("b");
        testStack.pop();
        assertEquals(1, testStack.length());
    }
}

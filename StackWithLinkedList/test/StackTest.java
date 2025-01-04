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
    public void testConstuctor() {
        Stack<String> testStack = this.createFromArgsTest();
        Stack<String> refStack = this.createFromArgsRef();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for push, ensure stack contains pushed element.
     */
    @Test
    public void testPushoneElement() {
        Stack<String> testStack = this.createFromArgsTest();
        Stack<String> refStack = this.createFromArgsRef("a");
        testStack.push("a");
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for push, ensure length is updated after multiple pushes.
     */
    @Test
    public void testPushmultipleElements() {
        Stack<String> testStack = this.createFromArgsTest();
        Stack<String> refStack = this.createFromArgsRef("c", "b", "a");
        testStack.push("a");
        testStack.push("b");
        testStack.push("c");
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for push, ensure stack handles multiple pushes correctly.
     */
    @Test
    public void testPushorder() {
        Stack<String> testStack = this.createFromArgsTest();
        testStack.push("a");
        testStack.push("b");
        assertEquals(2, testStack.length());
    }

    /**
     * Test case for pop, removing from a stack with one element.
     */
    @Test
    public void testPoponeElement() {
        Stack<String> testStack = this.createFromArgsTest("a", "b", "c");
        Stack<String> refStack = this.createFromArgsRef("b", "c");
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for pop, ensure multiple pops reduce length correctly.
     */
    @Test
    public void testPopmultipleElements() {
        Stack<String> testStack = this.createFromArgsTest("a", "b", "c");
        Stack<String> refStack = this.createFromArgsRef("c");
        testStack.pop();
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for pop, ensure popping all elements leaves the stack empty.
     */
    @Test
    public void testPopuntilEmpty() {
        Stack<String> testStack = this.createFromArgsTest("a", "b", "c");
        Stack<String> refStack = this.createFromArgsRef();
        testStack.pop();
        testStack.pop();
        testStack.pop();
        assertEquals(refStack, testStack);
    }

    /**
     * Test case for length, ensure length is 0 for empty stack.
     */
    @Test
    public void testLengthempty() {
        Stack<String> testStack = this.createFromArgsTest();
        assertEquals(0, testStack.length());
    }

    /**
     * Test case for length, ensure length is correct after push.
     */
    @Test
    public void testLengthafterPush() {
        Stack<String> testStack = this.createFromArgsTest("a");

        assertEquals(1, testStack.length());
    }

    /**
     * Test case for length, ensure length is correct after push and pop.
     */
    @Test
    public void testLengthafterPushAndPop() {
        Stack<String> testStack = this.createFromArgsTest("a");
        testStack.push("b");
        testStack.pop();
        assertEquals(1, testStack.length());
    }

}

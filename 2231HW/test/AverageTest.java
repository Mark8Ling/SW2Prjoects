
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Sample JUnit test fixture for SequenceSmooth.
 *
 * @author Mark Ling
 *
 */
public final class AverageTest {

    /**
     * Test average with one and two.
     */
    @Test
    public void test1() {
        /*
         * Set up variables and call method under test
         */
        int one = Integer.MAX_VALUE;
        int two = Integer.MAX_VALUE - 1;

        int excepted = Integer.MAX_VALUE - 1;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    /**
     * Test smooth with s1 = <2, 4, 6> and s2 = <-5, 12>.
     */
    @Test
    public void test2() {
        /*
         * Set up variables and call method under test
         */
        int one = Integer.MIN_VALUE;
        int two = Integer.MIN_VALUE + 1;

        int excepted = Integer.MIN_VALUE + 1;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test3() {
        /*
         * Set up variables and call method under test
         */
        int one = Integer.MIN_VALUE;
        int two = Integer.MIN_VALUE;

        int excepted = Integer.MIN_VALUE;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test4() {
        /*
         * Set up variables and call method under test
         */
        int one = Integer.MAX_VALUE;
        int two = Integer.MAX_VALUE;

        int excepted = Integer.MAX_VALUE;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test5() {
        /*
         * Set up variables and call method under test
         */
        int one = 5;
        int two = 8;

        int excepted = 6;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test6() {
        /*
         * Set up variables and call method under test
         */
        int one = -5;
        int two = -8;

        int excepted = -6;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test7() {
        /*
         * Set up variables and call method under test
         */
        int one = 11;
        int two = -4;

        int excepted = 3;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test8() {
        /*
         * Set up variables and call method under test
         */
        int one = -3;
        int two = 2;

        int excepted = 0;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test9() {
        /*
         * Set up variables and call method under test
         */
        int one = 3;
        int two = 5;

        int excepted = 4;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

    @Test
    public void test10() {
        /*
         * Set up variables and call method under test
         */
        int one = -3;
        int two = -5;

        int excepted = -4;
        int guess = Average.average(one, two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(excepted, guess);
    }

}

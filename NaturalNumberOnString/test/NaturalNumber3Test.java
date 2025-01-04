import components.naturalnumber.NaturalNumber;

/**
 * Customized JUnit test fixture for {@code NaturalNumber3}.
 */
public class NaturalNumber3Test extends NaturalNumberTest {

    @Override
    protected final NaturalNumber constructorTest() {

        //Creates default NN and return to see if NN is true
        NaturalNumber test = new NaturalNumber3();

        return test;
    }

    @Override
    protected final NaturalNumber constructorTest(int i) {

        //Creates int NN and return to see if NN is true
        NaturalNumber test = new NaturalNumber3(i);

        return test;
    }

    @Override
    protected final NaturalNumber constructorTest(String s) {

        //Creates String NN and return to see if NN is true
        NaturalNumber test = new NaturalNumber3(s);

        return test;
    }

    @Override
    protected final NaturalNumber constructorTest(NaturalNumber n) {

        //Creates NN type NN and return to see if NN is true
        NaturalNumber test = new NaturalNumber3(n);

        return test;
    }

    @Override
    protected final NaturalNumber constructorRef() {

        //Creates default NN and return to see if NN is referenced
        NaturalNumber test = new NaturalNumber3();

        return test;
    }

    @Override
    protected final NaturalNumber constructorRef(int i) {

        //Creates int NN and return to see if NN is referenced
        NaturalNumber test = new NaturalNumber3(i);

        return test;
    }

    @Override
    protected final NaturalNumber constructorRef(String s) {

        //Creates String NN and return to see if NN is referenced
        NaturalNumber test = new NaturalNumber3(s);

        return test;
    }

    @Override
    protected final NaturalNumber constructorRef(NaturalNumber n) {

        //Creates NN type NN and return to see if NN is referenced
        NaturalNumber test = new NaturalNumber3(n);

        return test;
    }

}

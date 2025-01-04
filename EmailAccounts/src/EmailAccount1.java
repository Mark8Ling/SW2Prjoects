import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Mark Ling
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    private static final Map<String, Integer> test = new Map1L<>();
    private final String firstName;
    private final String lastName;
    private final String emailAddress;

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;

        String ln = lastName.toLowerCase();
        int number = 1;
        if (test.hasKey(ln)) {
            Map.Pair<String, Integer> pair = test.remove(ln);
            int num = pair.value();
            number = num + 1;
        }

        this.emailAddress = ln + "." + number + "@osu.edu";

    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return this.firstName + " " + this.lastName;

    }

    @Override
    public String emailAddress() {

        return this.emailAddress;

    }

    @Override
    public String toString() {

        return "Name: " + this.name() + ", Email: " + this.emailAddress();

    }

}

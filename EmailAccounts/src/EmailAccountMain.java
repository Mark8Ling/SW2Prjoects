import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple program to exercise EmailAccount functionality.
 */
public final class EmailAccountMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private EmailAccountMain() {
    }

    private static void email(String name, SimpleWriter out) {
        String[] parts = name.split(" ");

        if (parts.length < 2) {
            out.println("Invalid input. Please enter a first and last name.");
            return;
        }
        String fn = parts[0];
        String ln = parts[1];

        out.println(fn + "-" + ln);

        EmailAccount account = new EmailAccount1(fn, ln);

        out.println(account.name());

        out.println(account.emailAddress());

        out.println(account);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        String name = "first";

        while (!name.isEmpty()) {
            out.println("Enter your name (First Last) - Enter empty to cancel");
            name = in.nextLine();
            email(name, out);
        }
        out.println("cancelled");

        in.close();
        out.close();
    }

}

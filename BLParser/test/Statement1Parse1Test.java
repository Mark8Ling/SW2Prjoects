import components.statement.Statement;
import components.statement.Statement1;

/**
 * Customized JUnit test fixture for {@code Statement1Parse1}.
 */
public class Statement1Parse1Test extends StatementTest {

    @Override
    protected final Statement constructorTest() {
        return new Statement1Parse1();
    }

    @Override
    protected final Statement constructorRef() {
        return new Statement1();
    }

    }

    Queue1L rep = null;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Queue1L<>();
    }

/**
 * No-argument constructor.
 */

public WaitingLineKernal() {
    this.createNewRep();
}

    public void add(T x) {
        if (this.rep.length() > 0) {
            T y = this.rep.dequeue();
            if (y.equals(x)) {
                System.out.println("You are already in the Waiting Line");
            }
        } else {
            this.rep.enqueue(x);
        }
    }

    public T remove(int pos) {
        Queue<T> temp = new Queue1L<>();
        for (int i = 0; i > pos - 1; i++) {
            T y = this.rep.dequeue();
            temp.enqueue(y);
        }
        T x = this.rep.dequeue();

        if (temp.length() > 0) {
            T y = temp.dequeue();
            this.rep.enquque(y);
        }

        return x;
    }

    public T seat() {
        T x = this.rep.dequeue();
        return x;
    }

    public int locate(T x) {
        int place = -1;
        Queue<T> temp = new Queue1L<>();
        for (int i = 0; i > this.rep.length() - 1; i++) {
            T y = this.rep.dequeue();
            if (y.equals(x)) {
                place = i;
            }
            temp.enqueue(y);
        }
        if (temp.length() > 0) {
            T y = temp.dequeue();
            this.rep.enquque(y);
        }
        return place;
}

import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Harry Lian and Mark Ling
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //initializing variable
        boolean inTree = false;

        //if the tree has children
        if (t.size() != 0) {

            //this is disassembling the tree into left and right
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T temp = t.disassemble(left, right);

            //checking to see if the root has the value of x
            if (temp.equals(x)) {
                inTree = true;

                //checking the left tree has the value of x
            } else if (x.compareTo(temp) < 0) {
                inTree = isInTree(left, x);

                //checking the right tree has the value of x
            } else {
                inTree = isInTree(right, x);
            }

            //restoring t
            t.assemble(temp, left, right);
        }

        return inTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //This is the condition if there are no children in t and insert x
        if (t.size() == 0) {
            BinaryTree<T> temp1 = t.newInstance();
            BinaryTree<T> temp2 = t.newInstance();
            t.assemble(x, temp1, temp2);

            //This is the condition if there are children in t
        } else {

            //This is disassembling t
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T temp = t.disassemble(left, right);

            //This is going to the left tree and insert x
            if (x.compareTo(temp) < 0) {
                insertInTree(left, x);

                //This is going to the right tree and insert x
            } else {
                insertInTree(right, x);
            }

            //Restoring t
            t.assemble(temp, left, right);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        //Initializing variables and making the left and right tree
        T smallest = t.root();
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        //Checking if t has children
        if (t.size() > 1) {
            T root = t.disassemble(left, right);

            //This is the smallest value
            if (left.size() != 0) {
                smallest = removeSmallest(left);

                //restoring t
                t.assemble(root, left, right);

                //This is getting the smallest value from the right tree
            } else {
                t.transferFrom(right);
            }
            //This is getting the smallest value if there are empty trees
        } else {
            smallest = t.disassemble(left, right);
        }

        return smallest;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        //Initializing variable
        T answer = t.root();

        //This is making the left and right tree
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        //This is checking if t has children
        if (t.size() > 1) {

            //This is getting the root
            T root = t.disassemble(left, right);

            //Checking if the root is the same as x
            if (root.equals(x)) {

                //This is removing the smallest value from the right tree
                if (right.size() != 0) {
                    T smallest = removeSmallest(right);
                    t.assemble(smallest, left, right);

                    //This is moving the left leaf to the root
                } else {
                    t.transferFrom(left);
                }

                //This is the removing the elements from left or right tree.
            } else if (x.compareTo(root) < 0) {
                answer = removeFromTree(left, x);
                t.assemble(root, left, right);
            } else {
                answer = removeFromTree(right, x);
                t.assemble(root, left, right);
            }
        } else {

            //This is getting the root of t since there are no children of t
            answer = t.disassemble(left, right);
        }

        return answer;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.tree = new BinaryTree1<>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?>
                : "" + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        T val = removeFromTree(this.tree, x);
        return val;
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        T answer = removeSmallest(this.tree);

        return answer;
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}

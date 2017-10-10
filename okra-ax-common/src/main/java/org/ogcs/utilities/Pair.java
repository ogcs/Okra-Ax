package org.ogcs.utilities;

import java.io.Serializable;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
public class Pair<L, R> implements Serializable {

    private static final long serialVersionUID = -2503469319729266769L;

    private L left;
    private R right;

    public Pair() {
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * <p>Obtains an immutable pair of from two objects inferring the generic types.</p>
     * <p>
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     *
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @param left  the left element, may be null
     * @param right the right element, may be null
     * @return a pair formed from the two parameters, not null
     */
    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }

    /**
     * <p>Gets the left element from this pair.</p>
     * <p>
     * <p>When treated as a key-value pair, this is the key.</p>
     *
     * @return the left element, may be null
     */
    public L getLeft() {
        return left;
    }

    /**
     * <p>Gets the right element from this pair.</p>
     * <p>
     * <p>When treated as a key-value pair, this is the value.</p>
     *
     * @return the right element, may be null
     */
    public R getRight() {
        return right;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}

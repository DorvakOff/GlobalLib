package com.dorvak.globallib.tuple;

public class Pair<L, R> {

    private final R right;
    private final L left;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + "=" + right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair<?, ?> p) {
            return right.equals(p.right) && left.equals(p.left);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = right.hashCode();
        result = 31 * result + left.hashCode();
        return result;
    }
}

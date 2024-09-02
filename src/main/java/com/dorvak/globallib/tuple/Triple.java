package com.dorvak.globallib.tuple;

public class Triple<L, M, R> extends Pair<L, R> {

    private final M middle;

    public Triple(L left, M middle, R right) {
        super(left, right);
        this.middle = middle;
    }

    public M getMiddle() {
        return middle;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "left=" + getLeft() +
                ", middle=" + middle +
                ", right=" + getRight() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Triple<?,?,?> t) {
            return getLeft().equals(t.getLeft()) && middle.equals(t.middle) && getRight().equals(t.getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + middle.hashCode();
        return result;
    }
}

package net.chmielowski.numeric;

public final class Inverted {
    private final int mValue;
    private final boolean mInvert;

    public Inverted(final int value, final boolean invert) {
        mValue = value;
        mInvert = invert;
    }

    public int value() {
        if (mInvert) {
            return -mValue;
        }
        return mValue;
    }
}

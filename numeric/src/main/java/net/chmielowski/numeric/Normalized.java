package net.chmielowski.numeric;

import static java.lang.Math.abs;

// TODO: following classes should
// 1. be moved to an external library (and new repo, gradle artf etc)
// 2. implement Number
// 3. be translated to Kotlin
public final class Normalized {
    private final int mValue;

    public Normalized(final int value) {
        mValue = value;
    }

    public int value() {
        if (mValue == 0) {
            return 0;
        }
        return mValue / abs(mValue);
    }
}

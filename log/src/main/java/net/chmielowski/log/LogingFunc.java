package net.chmielowski.log;

import rx.functions.Func1;

public final class LogingFunc<T> implements Func1<T, T> {
    private final String mTag;
    private final String mMsg;

    public LogingFunc(final String tag, final String msg) {
        this.mTag = tag;
        this.mMsg = msg;
    }

    @Override
    public T call(final T o) {
        L.og(mTag, mMsg);
        return o;
    }
}

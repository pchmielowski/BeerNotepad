package net.chmielowski.log;

import org.slf4j.LoggerFactory;

public final class L {
    private L() {
    }

    public static void og(final String tag, final String msg) {
        LoggerFactory.getLogger(tag).info(msg);
    }
}

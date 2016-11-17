package net.chmielowski.log;

public final class T {
    private T() {
    }

    //CHECKSTYLE:OFF
    public static String AG(final Object o) {
        return o.getClass().getSimpleName();
    }
    //CHECKSTYLE:ON

}

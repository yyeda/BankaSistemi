package bank.util;


public class PredicateUtil {
    public static <T> boolean isNull(T t) {
        return t == null;
    }

    public static <T> boolean isNotNull(T t) {
        return t != null;
    }

    public static <T> boolean isNull(T... t) {
        for (T t1 : t) {
            if (t1 == null) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isNotNull(T... t) {
        for (T t1 : t) {
            if (t1 == null) {
                return false;
            }
        }
        return true;
    }

    public static <T> T getOrDefault(T t, T defaultValue) {
        return isNull(t) ? defaultValue : t;
    }

}

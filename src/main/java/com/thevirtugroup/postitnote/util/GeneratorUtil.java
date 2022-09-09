package com.thevirtugroup.postitnote.util;

import java.util.concurrent.ThreadLocalRandom;

public class GeneratorUtil {

    private GeneratorUtil() {

    }

    public static Long generateRandomNumber() {
        return ThreadLocalRandom.current().nextLong(100, 500000 + 1);
    }
}

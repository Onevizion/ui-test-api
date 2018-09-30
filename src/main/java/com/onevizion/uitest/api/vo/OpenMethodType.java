package com.onevizion.uitest.api.vo;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum OpenMethodType {

    GENERAL_APPLET_FIRST_TAB, GENERAL_APPLET_SECOND_TAB, CUSTOM_APPLET_FIRST_TAB, CUSTOM_APPLET_SECOND_TAB;

    private static final List<OpenMethodType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final SecureRandom RANDOM = new SecureRandom();

    public static OpenMethodType randomOpenMethod() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
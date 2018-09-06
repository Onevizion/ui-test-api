package com.onevizion.guitest.vo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum OpenMethodType {

    GENERAL_APPLET_FIRST_TAB, GENERAL_APPLET_SECOND_TAB, CUSTOM_APPLET_FIRST_TAB, CUSTOM_APPLET_SECOND_TAB;

    private static final List<OpenMethodType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static OpenMethodType randomOpenMethod() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
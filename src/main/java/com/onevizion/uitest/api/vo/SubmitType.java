package com.onevizion.uitest.api.vo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum SubmitType {

    OK, APPLY;

    private static final List<SubmitType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static SubmitType randomSubmitType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
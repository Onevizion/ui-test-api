package com.onevizion.uitest.api.vo;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SubmitType {

    OK, APPLY;

    private static final List<SubmitType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final SecureRandom RANDOM = new SecureRandom();

    public static SubmitType randomSubmitType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
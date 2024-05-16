package com.sdi.jig.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MissingJig {
    LITTLE(5), PROPER(9), MORE(18);

    private final int count;
}

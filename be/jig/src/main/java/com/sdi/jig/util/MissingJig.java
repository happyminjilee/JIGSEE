package com.sdi.jig.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MissingJig {
    LITTLE(2), PROPER(7), MORE(19);

    private final int count;
}

package com.sdi.jig.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DownTime {
    LITTLE(1), PROPER(4), MORE(24), MANY(-1);

    private final int time;

}

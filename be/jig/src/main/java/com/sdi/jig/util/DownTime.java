package com.sdi.jig.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DownTime {
    LITTLE(10), PROPER(30), MORE(90), MANY(-1);

    private final int time;

    public static int getDownTime(int count) {
        if (count <= MissingJig.LITTLE.getCount()) {
            return DownTime.LITTLE.getTime();
        } else if (count <= MissingJig.PROPER.getCount()) {
            return DownTime.PROPER.getTime();
        } else if (count <= MissingJig.MORE.getCount()) {
            return DownTime.MORE.getTime();
        }
        return DownTime.MANY.getTime();
    }
}

<<<<<<<< HEAD:be/member/src/main/java/com/sdi/member/util/CommonException.java
package com.sdi.member.util;
========
package com.sdi.work_order.util;
>>>>>>>> release-wo:be/work-order/src/main/java/com/sdi/work_order/util/CommonException.java

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public CommonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }
        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}

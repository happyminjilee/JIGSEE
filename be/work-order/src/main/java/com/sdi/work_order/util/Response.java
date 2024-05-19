<<<<<<<< HEAD:be/member/src/main/java/com/sdi/member/util/Response.java
package com.sdi.member.util;
========
package com.sdi.work_order.util;
>>>>>>>> release-wo:be/work-order/src/main/java/com/sdi/work_order/util/Response.java

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public static Response<String> error(String message) {
        return new Response<>("FAIL", message);
    }
}

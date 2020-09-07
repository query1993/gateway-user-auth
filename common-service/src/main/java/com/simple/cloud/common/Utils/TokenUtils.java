package com.simple.cloud.common.Utils;

import java.util.Calendar;
import java.util.Date;

public final class TokenUtils {

    private TokenUtils() {
    }

    public static Date getTokenExpiredTime(int expireTime) {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.add(Calendar.SECOND, expireTime);
        return currentCalendar.getTime();
    }

    public static boolean checkTokenIsExpired(Date expireTime) {
        return new Date().getTime() > expireTime.getTime();
    }
}

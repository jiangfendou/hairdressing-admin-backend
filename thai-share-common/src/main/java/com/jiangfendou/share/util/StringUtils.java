package com.jiangfendou.share.util;

import java.util.UUID;

public class StringUtils {

    public static  String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}

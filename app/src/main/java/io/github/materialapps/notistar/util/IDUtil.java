package io.github.materialapps.notistar.util;

import java.util.UUID;

public class IDUtil {
    public static String getRandomName()
    {
        return UUID.randomUUID().toString();
    }

}

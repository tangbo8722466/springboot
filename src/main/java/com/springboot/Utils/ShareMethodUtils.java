package com.springboot.Utils;

import java.lang.reflect.Field;

public class ShareMethodUtils {
    public static boolean checkAllObjFieldIsNull(Object obj) throws IllegalAccessException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) != null) {
                return false;
            }
        }
        return true;
    }
}

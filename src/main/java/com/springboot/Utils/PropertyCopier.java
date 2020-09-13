package com.springboot.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PropertyCopier {
    private static Logger logger = LoggerFactory.getLogger(PropertyCopier.class);

    public PropertyCopier() {
    }

    public static <T, F> T copy(F from, Class<T> toCls) {
        if (from == null) {
            return null;
        } else {
            try {
                T to = toCls.newInstance();
                BeanUtils.copyProperties(from, to);
                return to;
            } catch (Exception var3) {
                logger.error("copy property failed", var3);
                return null;
            }
        }
    }

    public static <T, F> List<T> copy(List<F> from, Class<T> toCls) {
        return from == null ? null : (List)from.stream().map((e) -> {
            return copy(e, toCls);
        }).collect(Collectors.toList());
    }
}
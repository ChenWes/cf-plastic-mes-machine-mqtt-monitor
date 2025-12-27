package com.cf.common.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author ccw
 * @Date 11/11/2021
 */
public class BeanMapperUtils {
    private static DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozerBeanMapper.map(source, destinationClass);
    }

    public static void map(Object source, Object destination) {
        dozerBeanMapper.map(source, destination);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(dozerBeanMapper.map(sourceObject, destinationClass));
        }
        return destinationList;
    }
}

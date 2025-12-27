package com.cf.common.utils;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wilfmao
 * @date 2023/10/11 11:30
 */
public class ListUtil {

    /**
     * 获取列表中的唯一元素列表
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> getDistinctList(List<T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().filter(r -> !ObjectUtils.isEmpty(r)).distinct().collect(Collectors.toList());
    }

}

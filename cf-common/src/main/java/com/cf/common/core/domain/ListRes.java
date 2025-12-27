package com.cf.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 返回List或者分页查询响应对象
 *
 * @Author l84165417
 * @Date 2022/1/27 9:09
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListRes<T> {
    /**
     * 返回查询结果
     */
    private List<T> records;

    /**
     * 总条数
     */
    private Integer total;
}
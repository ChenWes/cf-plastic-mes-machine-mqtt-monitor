package com.cf.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface I18nMapper {

    List<Map<String, String>> getPrimaryKeyNames(@Param("tableSchema") String tableSchema, @Param("tableNames") List<String> tableNames);

    List<Map<String, Object>> getDataList(@Param("sql") String sql);
}

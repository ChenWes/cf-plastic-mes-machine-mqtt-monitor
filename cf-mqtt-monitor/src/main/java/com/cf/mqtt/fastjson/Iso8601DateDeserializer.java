package com.cf.mqtt.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * ISO 8601日期反序列化器，支持7位微秒
 *
 * @author coder-ren
 * @date 2025/12/30
 */
public class Iso8601DateDeserializer implements ObjectDeserializer {

    private static final Pattern ISO8601_PATTERN = Pattern.compile(
        "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{1,7}[+-]\\d{2}:\\d{2}"
    );

    @Override
    @SuppressWarnings("unchecked")
    public Date deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object value = parser.parse();
        
        if (value == null) {
            return null;
        }
        
        if (value instanceof Date) {
            return (Date) value;
        }
        
        if (value instanceof String) {
            String dateStr = (String) value;
            if (dateStr.isEmpty()) {
                return null;
            }
            
            try {
                // 处理ISO 8601格式，支持7位微秒
                // 格式: 2025-12-29T16:17:10.1702967+07:00
                if (ISO8601_PATTERN.matcher(dateStr).matches()) {
                    // 将7位微秒转换为3位毫秒（保留前3位）
                    String normalizedDateStr = normalizeMicroseconds(dateStr);
                    // 使用SimpleDateFormat解析，支持ISO 8601格式
                    // XXX模式会自动处理时区偏移
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    return sdf.parse(normalizedDateStr);
                } else {
                    // 尝试标准ISO 8601格式
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    return sdf.parse(dateStr);
                }
            } catch (ParseException e) {
                // 如果解析失败，尝试其他常见格式
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    return sdf.parse(dateStr);
                } catch (ParseException e2) {
                    throw new RuntimeException("Failed to parse date: " + dateStr, e2);
                }
            }
        }
        
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        
        throw new RuntimeException("Cannot deserialize date from: " + value);
    }

    /**
     * 将7位微秒标准化为3位毫秒
     * 例如: 2025-12-29T16:17:10.1702967+07:00 -> 2025-12-29T16:17:10.170+07:00
     */
    private String normalizeMicroseconds(String dateStr) {
        // 查找小数点和时区符号之间的部分
        int dotIndex = dateStr.indexOf('.');
        int timezoneIndex = dateStr.indexOf('+');
        if (timezoneIndex == -1) {
            timezoneIndex = dateStr.indexOf('-', dotIndex + 1);
        }
        
        if (dotIndex > 0 && timezoneIndex > dotIndex) {
            String beforeDot = dateStr.substring(0, dotIndex + 1);
            String microseconds = dateStr.substring(dotIndex + 1, timezoneIndex);
            String timezone = dateStr.substring(timezoneIndex);
            
            // 如果微秒超过3位，只保留前3位
            if (microseconds.length() > 3) {
                microseconds = microseconds.substring(0, 3);
            } else {
                // 如果不足3位，用0补齐
                while (microseconds.length() < 3) {
                    microseconds += "0";
                }
            }
            
            return beforeDot + microseconds + timezone;
        }
        
        return dateStr;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}


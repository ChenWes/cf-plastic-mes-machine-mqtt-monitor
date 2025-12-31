package com.cf.mqtt.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ISO 8601日期序列化器，将Date序列化为ISO 8601格式字符串
 * 序列化时输出为字符串格式到Redis
 *
 * @author coder-ren
 * @date 2025/12/30
 */
public class Iso8601DateSerializer implements ObjectSerializer {

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> 
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    );

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }

        if (object instanceof Date) {
            Date date = (Date) object;
            // 使用ThreadLocal确保线程安全，并将Date格式化为ISO 8601字符串格式
            String dateStr = DATE_FORMAT.get().format(date);
            // 直接写入字符串，确保序列化到Redis时是字符串格式而不是数字
            serializer.write(dateStr);
        } else {
            serializer.write(object);
        }
    }
}


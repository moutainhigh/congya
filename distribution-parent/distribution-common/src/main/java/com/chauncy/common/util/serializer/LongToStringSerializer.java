package com.chauncy.common.util.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * long转String
 *
 * @author zhangrt
 */
public class LongToStringSerializer implements ObjectSerializer
{
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException
    {
        if (object instanceof Long && object != null)
            serializer.write(Long.toString((Long) object));
        else
            serializer.write(object);
    }
}

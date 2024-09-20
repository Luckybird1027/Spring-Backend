package com.luckybird.common.json.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.luckybird.common.json.other.CommonBeanSerializerModifier;
import com.luckybird.common.json.other.DateTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.List;

/**
 * Json工具类
 *
 * @author Mir
 * @modify 新云鸟
 */
public class JsonUtils {

    private static final ObjectMapper OM_COMMON;

    private static final ObjectMapper OM_WITH_TYPE;

    static {
        ObjectMapper om = new ObjectMapper();
        // 序列化所有可读属性，包括public字段
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 序列化的时候序列对象的所有非null属性
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 关闭序列化的时候没有为属性找到getter方法报错
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 关闭反序列化的时候，没有找到属性的setter报错
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 反序列化的时候如果多了其他属性,不抛出异常
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 如果是空对象的时候,不抛异常
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 增加Java8时间处理模块
        om.registerModule(new DateTimeModule());
        // 复制不同使用场景的OM
        OM_COMMON = om.copy();
        // 增加自定义序列化修改器
        OM_COMMON.setSerializerFactory(om.getSerializerFactory().withSerializerModifier(new CommonBeanSerializerModifier()));

        OM_WITH_TYPE = om.copy();
        // 设置非Final字段保留类型信息，此设置不应跟系统外部序列化/反序列化时使用，存在安全漏洞，仅内部使用，如Redis，Bean深度拷贝等场景
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    /**
     * 对象转JSON字符串
     *
     * @param bean 对象
     * @param <T>  对象类型
     * @return JSON字符串
     */
    @SneakyThrows(Throwable.class)
    public static <T> String toStr(T bean) {
        return bean == null ? null : OM_COMMON.writeValueAsString(bean);
    }

    /**
     * JSON转对象
     *
     * @param json  json字符串
     * @param clazz 对象类型
     * @param <T>   对象类型
     * @return 对象
     */
    @SneakyThrows(Throwable.class)
    public static <T> T parse(@NonNull String json, @NonNull Class<T> clazz) {
        return OM_COMMON.readValue(json, clazz);
    }

    /**
     * Json转List
     *
     * @param json          json字符串
     * @param typeReference 类型引用
     * @param <T>           对象类型
     * @return List对象
     */
    @SneakyThrows(Throwable.class)
    public static <T> List<T> parse(@NonNull String json, @NonNull TypeReference<List<T>> typeReference) {
        return OM_COMMON.readValue(json, typeReference);
    }

    /**
     * 对象深拷贝
     *
     * @param bean 对象
     * @param <T>  对象类型
     * @return 深拷贝对象
     */
    @SneakyThrows(Throwable.class)
    @SuppressWarnings("unchecked")
    public static <T> T deepClone(@NonNull T bean) {
        return (T) OM_WITH_TYPE.readValue(OM_WITH_TYPE.writeValueAsString(bean), bean.getClass());
    }
}

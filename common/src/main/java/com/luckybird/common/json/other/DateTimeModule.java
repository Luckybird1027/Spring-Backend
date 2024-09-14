package com.luckybird.common.json.other;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * 日期时间默认序列化
 *
 * @author Mir
 */
public class DateTimeModule extends SimpleModule {

    public static final DateTimeFormatter SIMPLE_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter();

    public DateTimeModule() {
        super(PackageVersion.VERSION);
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(SIMPLE_LOCAL_DATE_TIME));
        addSerializer(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE));
        addSerializer(LocalTime.class, new LocalTimeSerializer(ISO_LOCAL_TIME));
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(SIMPLE_LOCAL_DATE_TIME));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_LOCAL_DATE));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(ISO_LOCAL_TIME));
    }
}

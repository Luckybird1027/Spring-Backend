package com.luckybird.logutil.utils;

import com.luckybird.common.base.Difference;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.utils.ContextUtils;
import com.luckybird.repository.mapper.OperateLogMapper;
import com.luckybird.repository.po.OperateLogPO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 日志工具类
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class LogUtils {

    // TODO: 需要转为异步处理，减少对主业务的影响

    private static OperateLogMapper operateLogMapper;

    @Resource
    public void setOperateLogMapper(OperateLogMapper operateLogMapper) {
        LogUtils.operateLogMapper = operateLogMapper;
    }

    private static final List<String> IGNORE_FIELDS = List.of("createTime", "creatorId", "updateTime", "updaterId", "deleted");

    /**
     * 日志记录方法-修改
     *
     * @param module   操作模块
     * @param type     操作类型
     * @param feature  操作功能
     * @param oldValue 旧数据
     * @param newValue 新数据
     */
    public static void log(String module, String type, String feature, Object oldValue, Object newValue) {

        OperateLogPO log = new OperateLogPO();

        // 依次比较新旧数据的各个字段数据，生成差异数据
        if (oldValue == null || newValue == null) {
            throw new BizException("INVALID_PARAMETER");
        }
        List<Difference> differences = new ArrayList<>();
        Class<?> oldClass = oldValue.getClass();
        Class<?> newClass = newValue.getClass();
        if (oldClass != newClass) {
            throw new BizException("INVALID_PARAMETER");
        }
        Field[] fields = oldClass.getDeclaredFields();
        for (Field field : fields) {
            if (IGNORE_FIELDS.contains(field.getName())) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object oldFieldValue = field.get(oldValue);
                Object newFieldValue = field.get(newValue);
                if (!Objects.equals(oldFieldValue, newFieldValue)) {
                    differences.add(new Difference(field.getName(), oldFieldValue, newFieldValue));
                }
            } catch (IllegalAccessException ignored) {
                return;
            }
        }
        if (differences.isEmpty()) {
            return;
        }
        log.setOperateModule(module);
        log.setOperateType(type);
        log.setOperateFeature(feature);
        log.setDataDifference(differences);
        log.setOperatorId(ContextUtils.getUserInfo().getId());
        log.setOperateTime(LocalDateTime.now());
        log.setClientIp(ContextUtils.getUserInfo().getIp());
        log.setClientUa(ContextUtils.getUserInfo().getUa());
        operateLogMapper.insert(log);
    }

    /**
     * 日志记录方法-新增/删除
     *
     * @param module  操作模块
     * @param type    操作类型
     * @param feature 操作功能
     * @param value   新增/删除的数据
     */
    public static void log(String module, String type, String feature, Object value) {
        OperateLogPO log = new OperateLogPO();
        log.setOperateModule(module);
        log.setOperateType(type);
        log.setOperateFeature(feature);
        log.setOperatorId(ContextUtils.getUserInfo().getId());
        log.setOperateTime(LocalDateTime.now());
        log.setClientIp(ContextUtils.getUserInfo().getIp());
        log.setClientUa(ContextUtils.getUserInfo().getUa());
        if (value != null) {
            log.setDataBrief(value);
        }
        operateLogMapper.insert(log);
    }
}

package com.luckybird.log.utils;

import com.luckybird.common.base.Difference;
import com.luckybird.common.base.FieldValue;
import com.luckybird.common.context.utils.ContextUtils;
import com.luckybird.repository.operateLog.OperateLogMapper;
import com.luckybird.repository.operateLog.OperateLogPO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志工具类
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class LogUtils {

    private static OperateLogMapper operateLogMapper;

    @Resource
    public void setOperateLogMapper(OperateLogMapper operateLogMapper) {
        LogUtils.operateLogMapper = operateLogMapper;
    }

    /**
     * 日志记录方法-修改
     *
     * @param module      操作模块
     * @param type        操作类型
     * @param feature     操作功能
     * @param differences 差异字段列表
     */
    public static void differenceLog(String module, String type, String feature, List<Difference> differences) {

        OperateLogPO log = new OperateLogPO();
        log.setOperateModule(module);
        log.setOperateType(type);
        log.setOperateFeature(feature);
        log.setDataDifference(differences);
        log.setOperatorId(ContextUtils.getUserInfo().getId());
        log.setOperateTime(LocalDateTime.now());
        log.setClientIp(ContextUtils.getUserInfo().getIp());
        log.setClientUa(ContextUtils.getUserInfo().getUa());
        if (differences.isEmpty()) {
            return;
        }
        operateLogMapper.insert(log);
    }

    /**
     * 日志记录方法-新增/删除
     *
     * @param module    操作模块
     * @param type      操作类型
     * @param feature   操作功能
     * @param fieldValues 字段值列表
     */
    public static void briefLog(String module, String type, String feature, List<FieldValue> fieldValues) {
        OperateLogPO log = new OperateLogPO();
        log.setOperateModule(module);
        log.setOperateType(type);
        log.setOperateFeature(feature);
        log.setOperatorId(ContextUtils.getUserInfo().getId());
        log.setOperateTime(LocalDateTime.now());
        log.setClientIp(ContextUtils.getUserInfo().getIp());
        log.setClientUa(ContextUtils.getUserInfo().getUa());
        log.setDataBrief(fieldValues);
        operateLogMapper.insert(log);
    }
}

package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.CheckLog;

/**
 * 复核日志数据处理接口
 * @author jeekhan
 *
 */
public interface CheckLogMapper {

    //新插入复核信息
    int insert(CheckLog record);
    
    //根据主键获取复核历史记录信息
    CheckLog selectByID(Long id);

}


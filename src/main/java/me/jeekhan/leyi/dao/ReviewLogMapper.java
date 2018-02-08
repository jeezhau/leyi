package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.ReviewLog;

/**
 * 审批日志数据处理接口
 * @author jeekhan
 *
 */
public interface ReviewLogMapper {

    //新插入审批信息
    int insert(ReviewLog record);
    
    //根据主键获取审批历史记录信息
    ReviewLog selectByID(Long id);

}


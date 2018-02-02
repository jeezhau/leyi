package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.ReviewInfo;

/**
 * 审批信息数据处理接口
 * @author jeekhan
 *
 */
public interface ReviewInfoMapper {


	
    //新插入审批信息
    Long insert(ReviewInfo record);
    //根据主键获取审批历史记录信息
    ReviewInfo selectByPrimaryKey(Integer id);
    
    //
    //Long insertSelective(ReviewInfo record);
    
	//
    //Long deleteByPrimaryKey(Long id);
    
    //
    //Long updateByPrimaryKeySelective(ReviewInfo record);
    //
    //int updateByPrimaryKeyWithBLOBs(ReviewInfo record);
    
    //根据主键更新审批信息，返回主键
    //int updateByPrimaryKey(ReviewInfo record);
}


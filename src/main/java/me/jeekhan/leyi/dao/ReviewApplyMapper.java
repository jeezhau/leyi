package me.jeekhan.leyi.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.ReviewApply;

/**
 * 审批申请数据处理接口
 * @author jeekhan
 *
 */
public interface ReviewApplyMapper {
	
    //新插入审批申请
    int insert(ReviewApply record);
    
    //根据主键获取审批申请信息
    ReviewApply selectByID(Long id);
    
    //变更审批申请记录状态
    int updateStatus(@Param("id")Long id,@Param("result")String result);
    
    //取指定对象的待审核记录
    ReviewApply selectWait4Review(@Param("objName")String objName,@Param("keyId")Long keyId);
    
    //更新申请时间
    int updateApplyTime(@Param("id")Long id,@Param("applyTime")Date applyTime);
    
}


package me.jeekhan.leyi.dao;

import java.util.List;

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
    
    //变更审批申请记录
    int update(ReviewApply record);
    
    //取指定对象的待审核的申请记录
    ReviewApply selectWait4Review(@Param("objName")String objName,@Param("keyId")Long keyId);
    
    //取指定对象的所有申请记录
    List<ReviewApply> selectAll4Obj(@Param("objName")String objName,@Param("keyId")Long keyId);
    
}


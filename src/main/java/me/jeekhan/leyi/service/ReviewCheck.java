package me.jeekhan.leyi.service;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.CheckLog;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;

public interface ReviewCheck {
	/**
	 * 新插入审批申请
	 * @param record
	 * @return
	 */
    Long insertReviewApply(ReviewApply record);
    /**
     * 根据主键获取审批申请信息
     * @param id
     * @return
     */
    ReviewApply geReviewApplytByID(Long id);
    /**
     * 变更审批申请记录状态
     * @param id
     * @param status
     * @return
     */
    Long updateStatus4ReviewApply(@Param("id")Long id,@Param("status")String status);
    
    /**
     * 新插入审批信息
     * @param record
     * @return
     */
    Long insertReviewLog(ReviewLog record);
    
    /**
     * 根据主键获取审批历史记录信息
     * @param id
     * @return
     */
    ReviewLog getReviewLogByID(Long id);
    
    /**
     * 新插入复核信息
     * @param record
     * @return
     */
    Long insertCheckLog(CheckLog record);
    
    /**
     * 根据主键获取复核历史记录信息
     * @param id
     * @return
     */
    CheckLog getCheckLogByID(Long id);

}

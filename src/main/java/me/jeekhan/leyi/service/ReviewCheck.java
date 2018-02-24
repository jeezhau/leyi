package me.jeekhan.leyi.service;

import me.jeekhan.leyi.model.CheckLog;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;

/**
 * 审批、复核服务接口
 * @author jeekhan
 *
 */
public interface ReviewCheck {
	/**
	 * 新插入审批申请
	 * @param record
	 * @return
	 */
    Long addReviewApply(ReviewApply record);
    /**
     * 根据主键获取审批申请信息
     * @param id
     * @return
     */
    ReviewApply getReviewApply(Long id);
    /**
     * 更新审批申请记录
     * @param apply
     * @return
     */
    Long updateReviewApply(ReviewApply apply);
    
    /**
     * 新插入审批信息
     * @param record
     * @return
     */
    Long addReviewLog(ReviewLog record);
    
    /**
     * 根据主键获取审批历史记录信息
     * @param id
     * @return
     */
    ReviewLog getReviewLog(Long id);
    
    /**
     * 新插入复核信息
     * @param record
     * @return
     */
    Long addCheckLog(CheckLog record);
    
    /**
     * 根据主键获取复核历史记录信息
     * @param id
     * @return
     */
    CheckLog getCheckLog(Long id);
    
    /**
     * 取指定对象的待审核(包含复核中)的申请记录
     * @param objName
     * @param keyId
     * @return
     */
    ReviewApply getWait4Review(String objName,Long keyId);
    
    /**
     * 判断对象是否可再次提交申请
     * 1、每类申请在连续6次审批被拒绝后系统不再接受申请；
     * 2、复核不通过的记录不可再次提交申请；
     * @param objName
     * @param keyId
     * @return
     */
    boolean canApply(String objName,Long keyId);

}

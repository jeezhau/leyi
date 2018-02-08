package me.jeekhan.leyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.CheckLogMapper;
import me.jeekhan.leyi.dao.ReviewApplyMapper;
import me.jeekhan.leyi.dao.ReviewLogMapper;
import me.jeekhan.leyi.model.CheckLog;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;
import me.jeekhan.leyi.service.ReviewCheck;

/**
 * 审批复核服务类
 * @author jeekhan
 *
 */
@Service
public class ReviewCheckImpl implements ReviewCheck{
	@Autowired
	private ReviewApplyMapper reviewApplyMapper;
	@Autowired
	private ReviewLogMapper reviewLogMapper;
	@Autowired
	private CheckLogMapper checkLogMapper;
	
	/**
	 * 新插入审批申请
	 * @param record
	 * @return 新插入记录ID
	 */
    public Long insertReviewApply(ReviewApply record) {
    		reviewApplyMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取审批申请信息
     * @param id
     * @return
     */
    public ReviewApply geReviewApplytByID(Long id) {
    		return reviewApplyMapper.selectByID(id);
    }
    
    /**
     * 变更审批申请记录状态
     * @param id
     * @param status
     * @return  新更新记录ID
     */
    public Long updateStatus4ReviewApply(Long id,String status) {
    		int cnt = reviewApplyMapper.updateStatus(id, status);
    		if(cnt >0) {
    			return id;
    		}
    		return -1L;	//更新数据库失败
    }
    
    /**
     * 新插入审批信息
     * @param record
     * @return  新插入记录ID
     */
    public Long insertReviewLog(ReviewLog record) {
    		reviewLogMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取审批历史记录信息
     * @param id
     * @return
     */
    public ReviewLog getReviewLogByID(Long id) {
    		return reviewLogMapper.selectByID(id);
    }
    
    /**
     * 新插入复核信息
     * @param record
     * @return  新插入记录ID
     */
    public Long insertCheckLog(CheckLog record) {
    		checkLogMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取复核历史记录信息
     * @param id
     * @return
     */
    public CheckLog getCheckLogByID(Long id) {
    		return checkLogMapper.selectByID(id);
    }
}

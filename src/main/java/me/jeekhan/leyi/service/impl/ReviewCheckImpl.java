package me.jeekhan.leyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.SysPropUtil;
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
    public Long addReviewApply(ReviewApply record) {
    		reviewApplyMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取审批申请信息
     * @param id
     * @return
     */
    public ReviewApply getReviewApply(Long id) {
    		return reviewApplyMapper.selectByID(id);
    }
    
    /**
     * 更新审批申请记录
     * @param apply
     * @return  新更新记录ID
     */
    public Long updateReviewApply(ReviewApply apply) {
    		int cnt = reviewApplyMapper.update(apply);
    		if(cnt >0) {
    			return apply.getId();
    		}
    		return ErrorCodes.DB_CRUD_ERR;	//更新数据库失败
    }
    
    /**
     * 新插入审批信息
     * @param record
     * @return  新插入记录ID
     */
    public Long addReviewLog(ReviewLog record) {
    		reviewLogMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取审批历史记录信息
     * @param id
     * @return
     */
    public ReviewLog getReviewLog(Long id) {
    		return reviewLogMapper.selectByID(id);
    }
    
    /**
     * 新插入复核信息
     * @param record
     * @return  新插入记录ID
     */
    public Long addCheckLog(CheckLog record) {
    		checkLogMapper.insert(record);
    		return record.getId();
    }
    
    /**
     * 根据主键获取复核历史记录信息
     * @param id
     * @return
     */
    public CheckLog getCheckLog(Long id) {
    		return checkLogMapper.selectByID(id);
    }
    
    /**
     * 取指定对象的待审核(包含复核中)的申请记录
     * @param objName
     * @param keyId
     * @return
     */
    public ReviewApply getWait4Review(String objName,Long keyId) {
    		return reviewApplyMapper.selectWait4Review(objName, keyId);
    }
    
    /**
     * 判断对象是否可再次提交申请
     * 1、每类申请在连续N次审批被拒绝后系统不再接受申请；
     * 2、复核不通过的记录不可再次提交申请；
     * @param objName
     * @param keyId
     * @return
     */
    public boolean canApply(String objName,Long keyId) {
    		int limitSize = 6;
    		try {
    			limitSize = new Integer(SysPropUtil.getParam("APPLY_LIMIT_CNT"));
    		}catch(Exception e) {
    			limitSize = 6;
    		}
    		List<ReviewApply> list = reviewApplyMapper.selectAll4Obj(objName, keyId);
    		if(list == null || list.isEmpty()) {
    			return true;
    		}
    		if("CR".equals(list.get(0).getStatus())) { //复核不通过
			return false; 
		}
    		if(list.size() < limitSize) {
    			return true;
    		}else {
    			for(int i=0;i<limitSize;i++) {
    				if("A".equals(list.get(0).getStatus())) {//出现通过
    					return true;
    				}
    			}
    			return false;//连续N次被拒绝
    		}
    }
}

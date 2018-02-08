package me.jeekhan.leyi.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dao.InviteCodeMapper;
import me.jeekhan.leyi.dao.ReviewApplyMapper;
import me.jeekhan.leyi.dao.ReviewLogMapper;
import me.jeekhan.leyi.dao.UserBaseInfoMapper;
import me.jeekhan.leyi.dao.UserFullInfoMapper;
import me.jeekhan.leyi.model.InviteCode;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;
	@Autowired
	private UserFullInfoMapper userFullInfoMapper;
	@Autowired
	private ReviewLogMapper reviewInfoMapper;
	@Autowired
	private ReviewApplyMapper reviewApplyMapper;
	@Autowired
	private InviteCodeMapper inviteCodeMapper;
	
	/**
	 * 提取用户基本信息
	 * @param	id 用户ID
	 */
	@Override
	public UserBaseInfo getUserBaseInfo(Long id){
		return userBaseInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据用户名提取用户详细信息
	 * @param username	用户名或邮箱
	 * @return	用户详细信息
	 */
	public UserFullInfo getUserFullInfo(String username){
		return userFullInfoMapper.selectByName(username);
	}
	
	/**
	 * 用户身份验证
	 * @param	username	用户名/邮箱
	 * @param	passwd	用户密码（明文）
	 * @return	true-成功，false-失败
	 */
	@Override
	public boolean authentification(String username, String passwd) {
		UserFullInfo  userInfo = userFullInfoMapper.selectByName(username);
		try{
			if(userInfo == null){
				return false;
			}else{
				if(SunSHAUtils.encodeSHA512Hex(passwd).equals(userInfo.getPasswd())){
					return true;
				}
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 根据ID获取用户的详细信息
	 * @param	id 用户ID
	 * @return	用户详细信息
	 */
	@Override
	public UserFullInfo getUserFullInfo(Long id) {
		return userFullInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 保存用户
	 * 1、有则更新，无则修改；
	 * 2、新增审批申请记录；
	 * @param	用户详细信息
	 * @return	用户ID、错误码
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Override 
	public Long saveUser(UserFullInfo userFullInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(userFullInfo == null){
			return ErrorCodes.LESS_INFO;
		}		
		UserFullInfo oldInfo = userFullInfoMapper.selectByName(userFullInfo.getUsername());
		if(oldInfo != null && !oldInfo.getId().equals(userFullInfo.getId())){
			if(oldInfo.getUsername().equals(userFullInfo.getUsername())){	//用户名已被使用
				return ErrorCodes.USER_USERNAME_USED;
			}
			if(oldInfo.getEmail().equals(userFullInfo.getEmail())){	//邮箱已被使用
				return ErrorCodes.USER_EMAIL_USED;
			}
		}
		userFullInfo.setStatus("0");	//待审核
		if(userFullInfo.getId() == null){//新增
			userFullInfo.setPasswd(SunSHAUtils.encodeSHA512Hex(userFullInfo.getPasswd()));
			userFullInfo.setRegistTime(new Date());
			userFullInfo.setUpdateTime(new Date());
			userFullInfoMapper.insert(userFullInfo);
			Long userId = userFullInfo.getId();	//取新增用户的ID
			//修改邀请码的使用次数
			inviteCodeMapper.updateUsedCnt(userFullInfo.getInviteCode());
			//新插入或更新审批申请
			String objName = SysPropUtil.getParam("USER");
			ReviewApply apply = reviewApplyMapper.selectWait4Review(objName, userId);
			if(apply != null) {
				reviewApplyMapper.updateApplyTime(apply.getId(),new Date());
			}else {
				apply = new ReviewApply();
				apply.setObjName(objName);
				apply.setKeyId(userId);
				apply.setApplyTime(new Date());
				apply.setStatus("0"); //待审核
				reviewApplyMapper.insert(apply);
			}
			return userId;
		}else{	//修改
			oldInfo = userFullInfoMapper.selectByPrimaryKey(userFullInfo.getId());
			if(oldInfo == null) {
				return ErrorCodes.USER_NO_EXISTS;
			}
			userFullInfo.setRegistTime(oldInfo.getRegistTime());
			userFullInfo.setUpdateTime(new Date());
			userFullInfoMapper.updateByPrimaryKey(userFullInfo);
			return userFullInfo.getId();
		}
	}
	
	/**
	 * 获取可用于显示于系统首页的用户
	 * @return
	 */
	@Override
	public List<UserFullInfo> getSysIndexShowUser(){
		return userFullInfoMapper.selectSysIndexShowUser();
	}
	
	/**
	 * 取待审核用户数量
	 * @return
	 */
	@Override
	public int get4ReviewUsersCnt() {
		return userFullInfoMapper.countUsers4Review();
	}
	
	/**
	 * 取待审核的用户记录
	 * @param	pageCond		分页信息
	 * @return
	 */
	public List<UserFullInfo> getUsers4Review(PageCond pageCond){
		return userFullInfoMapper.selectUsers4Review(pageCond);
	}
	
	/**
	 * 用户审核
	 * @param userId		用户ID
	 * @param reviewLog	审核信息
	 * @return	用户ID
	 */
	@Override
	public Long reviewUser(Long userId,ReviewLog reviewLog){
		String usrInfo = userFullInfoMapper.selectByPrimaryKey(userId).toString();
		reviewLog.setOriginalInfo(usrInfo);
		reviewLog.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewLog);
		reviewApplyMapper.updateStatus(reviewLog.getApplyId(), reviewLog.getStatus());
		return userFullInfoMapper.updateStatus(userId, reviewLog.getStatus());
	}
	
	/**
	 * 取用户当前可用的邀请码
	 * @param userId
	 * @return
	 */
	public InviteCode getAvailableCodeByUser(Long userId) {
		return inviteCodeMapper.selectAvailableByUser(userId);
	}
	
	/**
	 * 判断邀请码是否可用
	 * @param code
	 * @return
	 */
	public boolean isAvailableCode(BigDecimal code) {
		int cnt = inviteCodeMapper.isAvailableCode(code);
		if(cnt == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建用户新的邀请码
	 * @return
	 */
	public BigDecimal createNewCode(Long userId) {
		InviteCode inviteCode = new InviteCode();
		inviteCode.setCreateOpr(userId);
		inviteCode.setCreateTime(new Date());
		inviteCode.setUsedCnt(0);
		inviteCodeMapper.insert(inviteCode);
		return inviteCode.getCode();
	}
}

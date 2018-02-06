package me.jeekhan.leyi.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.dao.UserBaseInfoMapper;
import me.jeekhan.leyi.dao.UserFullInfoMapper;
import me.jeekhan.leyi.model.ReviewInfo;
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
	private ReviewInfoMapper reviewInfoMapper;
	
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
	 * 保存用户：有则更新，无则修改
	 * @param	用户详细信息
	 * @return	用户ID,0-缺少信息，-1-用户名已被使用,-2-邮箱已被使用
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
//			//修改邀请码的使用状态
//			String inviteCode = userFullInfo.getInviteCode();
//			InviteInfo inviteInfo = inviteInfoMapper.selectByPrimaryKey(inviteCode);
//			inviteInfo.setStatus("1");
//			inviteInfo.setUseTime(new Date());
//			inviteInfoMapper.updateByPrimaryKey(inviteInfo);
//			//为用户补充一个邀请码
//			InviteInfo Invite = new InviteInfo();
//			Invite.setCrtTime(new Date());
//			Invite.setCrtUser(inviteInfo.getCrtUser());
//			inviteInfoMapper.insert(Invite);
//			//为新增用户添加一个邀请码
//			InviteInfo newInvite = new InviteInfo();
//			newInvite.setCrtTime(new Date());
//			newInvite.setCrtUser(userId);
//			inviteInfoMapper.insert(newInvite);
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
	 * @param result		审核结果:A-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 * @return	用户ID
	 */
	@Override
	public Long reviewUser(Long userId,String result,ReviewInfo reviewInfo){
		String usrInfo = userFullInfoMapper.selectByPrimaryKey(userId).toString();
		reviewInfo.setObjName("tb_user_full_info");
		reviewInfo.setKeyId(userId);
		reviewInfo.setOriginalInfo(usrInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		return userFullInfoMapper.updateStatus(userId, result);
	}

}

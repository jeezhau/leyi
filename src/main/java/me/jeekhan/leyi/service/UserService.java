package me.jeekhan.leyi.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.InviteCode;
import me.jeekhan.leyi.model.ReviewLog;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;

public interface UserService {
	public UserBaseInfo getUserBaseInfo(Long id);
	/**
	 * 用户权限信息验证
	 * @param username	用户名/邮箱
	 * @param passwd	密码
	 * @return
	 */
	public boolean authentification(String username,String passwd);
	/**
	 * 取用户详细信息
	 * @param username 用户名/邮箱
	 * @return 用户详细信息
	 */
	public UserFullInfo getUserFullInfo(String username);
	/**
	 * 取用户详细信息
	 * @param id 用户ID
	 * @return 用户详细信息
	 */
	public UserFullInfo getUserFullInfo(Long id);
	/**
	 * 保存用户信息
	 * @param userFullInfo	用户详细信息
	 * @return 用户ID
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public Long saveUser(UserFullInfo userFullInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public Long updPwd(UserFullInfo user);
	
	/**
	 * 获取用于显示于系统主页的用户信息
	 * @return
	 */
	public List<UserFullInfo> getSysIndexShowUser();

	/**
	 * 取待审核用户数量
	 * @return
	 */
	public int get4ReviewUsersCnt();
	
	/**
	 * 取待审核的用户记录
	 * @param pageCond	分页信息
	 * @return
	 */
	public List<UserFullInfo> getUsers4Review(PageCond pageCond);
	
	/**
	 * 用户审核
	 * @param userId   	用户ID
	 * @param result		审核结果:A-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 * @return	用户ID
	 */
	public Long reviewUser(Long userId,ReviewLog reviewLog);
	
	/**
	 * 取用户当前可用的邀请码
	 * @param userId
	 * @return
	 */
	public InviteCode getAvailableCodeByUser(Long userId);
	
	
	/**
	 * 判断邀请码是否可用
	 * @param code
	 * @return
	 */
	public boolean isAvailableCode(BigDecimal code);
	
	/**
	 * 创建用户新的邀请码
	 * @return
	 */
	public BigDecimal createNewCode(Long userId);

}

package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.dao.ThemeInfoMapper;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeInfo;
import me.jeekhan.leyi.service.ThemeService;

/**
 * 主题分类服务
 * @author Jee Khan
 *
 */
@Service
public class ThemeServiceImpl implements ThemeService {
	@Autowired
	private ThemeInfoMapper themeInfoMapper;
	@Autowired
	private ReviewInfoMapper reviewInfoMapper;
	/**
	 * 保存主题
	 *  0、同级主题最多为33个，主题层数最多3层；
	 *  1、存在ID则更信息；无ID则新增；
	 *  2、同名同层主题使用更新；
	 *  @param theme 主题信息
	 *  @return 主题ID，负数错误标志
	 */
	@Override
	public Long saveTheme(ThemeInfo theme) {
		if(theme == null ){	//数据为空
			return ErrorCodes.LESS_INFO;
		}
		theme.setStatus("0");	//待审核
		theme.setUpdateTime(new Date());
		//检查是否有同级同名的分类
		ThemeInfo old = themeInfoMapper.selectTheme(theme.getName(),theme.getParentId(),theme.getOwnerId());	
		if(old != null && !old.getId().equals(theme.getId())) {//存在同级同名主题
			return ErrorCodes.THEME_EXISTS_SAME;
		}
		//同级主题数量限制
		int count = themeInfoMapper.countThemes(theme.getOwnerId(),theme.getParentId());
		if("/".equals(theme.getParentId())){ //一级主题
			if(count >= 33){  //统计主题个数大于33个
				return ErrorCodes.THEME_THEMES_CNT_LIMIT;  
			}
		}
		//主题层数限制
		String[] themeIds = theme.getParentId().split("/");
		if(themeIds.length >=4 ) {	//主题层数已达到3个
			return ErrorCodes.THEME_THEMES_LVL_LIMIT;
		}		
		
		if(theme.getId() == null){//新增
			if(!"/".equals(theme.getParentId())) {//下级主题验证
				String[] idSeq = theme.getParentId().split("/");
				ThemeInfo tmp = themeInfoMapper.selectByPrimaryKey(new Long(idSeq[idSeq.length-1]));
				if(tmp == null) {
					return ErrorCodes.THEME_PARENTID_ERROR;	//主题数据非法：上级主题ID序列非法
				}
			}
			themeInfoMapper.insert(theme);
			ThemeInfo lastest = themeInfoMapper.selectTheme(theme.getName(),theme.getParentId(),theme.getOwnerId());
			return lastest.getId();
		}else{	//修改
			ThemeInfo tmp = themeInfoMapper.selectByPrimaryKey(theme.getId());
			if(!theme.getOwnerId().equals(tmp.getOwnerId())) {
				return ErrorCodes.THEME_THEMEID_ERROR;	//主题数据非法：主题ID非法
			}
			themeInfoMapper.update(theme);
			return theme.getId();
		}
	}
	
	/**
	 * 逻辑删除主题
	 *  设置主题状态为'D'
	 */
	@Override
	public Long deleteTheme(Long themeId) {
		ThemeInfo theme = themeInfoMapper.selectByPrimaryKey(themeId);
		themeInfoMapper.updateStatus(themeId,"D");
		return theme.getId();
	}
	
	/**
	 * 根据主题ID获取主题信息
	 */
	@Override
	public ThemeInfo getTheme(Long themeId) {
		return themeInfoMapper.selectByPrimaryKey(themeId);
	}
	
	/**
	 * 根据主题名称取指定用户下的指定上级的主题信息
	 * @param themeName 主题名称
	 * @param parentId	上级主题ID序列
	 * @param ownerId	主题拥有者
	 * @return
	 */
	@Override
	public ThemeInfo getTheme(String themeName,String parentId,Long ownerId) {
		return themeInfoMapper.selectTheme(themeName, parentId, ownerId);
	}
	
	/**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentId	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
	@Override
	public List<ThemeInfo> getThemes(Long ownerId,String parentId,boolean isSelf) {
		return themeInfoMapper.selectThemes(ownerId, parentId,isSelf);
	}
	
	/**
	 * 获取待审核的主题记录
	 * @return
	 */
	@Override
	public List<ThemeInfo> getThemes4Review(PageCond pageCond){
		return themeInfoMapper.selectThemes4Review(pageCond);
	}
	
	/**
	 * 主题审核
	 * @param themeId   主题ID
	 * @param result	审核结果:A-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 * @return	主题ID
	 */
	@Override
	public Long reviewTheme(Long themeId,String result,ReviewInfo reviewInfo){
		String themeInfo = themeInfoMapper.selectByPrimaryKey(themeId).toString();
		reviewInfo.setObjName("tb_theme_info");
		reviewInfo.setKeyId(themeId);
		reviewInfo.setOriginalInfo(themeInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		
		return themeInfoMapper.updateStatus(themeId, result);
	}
	
	/**
	 * 取待审核主题数量
	 * @return
	 */
	@Override
	public int get4ReviewThemesCnt() {
		return themeInfoMapper.countThemes4Review();
	}

	/**
	 * 统计同级指定层级的主题数量
	 * @param	ownerId	主题拥有者
	 * @param	parentId	上级主题ID序列
	 * @return	主题数量
	 */
	@Override
	public int countThemes(Long ownerId, String parentId) {
		return themeInfoMapper.countThemes(ownerId, parentId);
	}
	
}



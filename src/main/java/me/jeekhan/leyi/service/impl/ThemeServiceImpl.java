package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.dao.ArticleBriefMapper;
import me.jeekhan.leyi.dao.ThemeClassMapper;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ThemeService;

/**
 * 主题分类服务
 * @author Jee Khan
 *
 */
@Service
public class ThemeServiceImpl implements ThemeService {
	@Autowired
	private ThemeClassMapper themeClassMapper;
	@Autowired
	private ArticleBriefMapper articleBriefMapper;
	
	/**
	 * 保存主题
	 *  0、同级主题最多为33个，主题层数最多3层；
	 *  1、存在ID则更信息；无ID则新增；
	 *  2、同名同层主题使用更新；
	 *  @param theme 主题信息
	 *  @return 主题ID，负数错误标志
	 */
	@Override
	public Long saveTheme(ThemeClass theme) {
		if(theme == null ){	//数据为空
			return ErrorCodes.LESS_INFO;
		}
		theme.setStatus("0");	//待审核
		theme.setUpdateTime(new Date());
		//检查是否有同级同名的分类
		ThemeClass old = themeClassMapper.selectTheme(theme.getName(),theme.getParentSeq(),theme.getOwnerId());	
		if(old != null && !old.getId().equals(theme.getId())) {//存在同级同名主题
			return ErrorCodes.THEME_EXISTS_SAME;
		}
		//同级主题数量限制
		int count = themeClassMapper.countUserThemes(theme.getOwnerId(),theme.getParentSeq(),true);
		if("/".equals(theme.getParentSeq())){ //一级主题
			if(count >= 33){  //统计主题个数大于33个
				return ErrorCodes.THEME_THEMES_CNT_LIMIT;  
			}
		}
		//主题层数限制
		String[] themeIds = theme.getParentSeq().split("/");
		if(themeIds.length >=4 ) {	//主题层数已达到3个
			return ErrorCodes.THEME_THEMES_LVL_LIMIT;
		}		
		
		if(theme.getId() == null){//新增
			if(!"/".equals(theme.getParentSeq())) {//上级主题验证
				String[] idSeq = theme.getParentSeq().split("/");
				ThemeClass tmp = themeClassMapper.selectByPrimaryKey(new Long(idSeq[idSeq.length-1]));
				if(tmp == null) {
					return ErrorCodes.THEME_PARENTSEQ_ERROR;	//主题数据非法：上级主题ID序列非法
				}
			}
			themeClassMapper.insert(theme);
			return theme.getId();	//返回新增主题ID
		}else{	//修改
			ThemeClass tmp = themeClassMapper.selectByPrimaryKey(theme.getId());
			if(!theme.getOwnerId().equals(tmp.getOwnerId())) {
				return ErrorCodes.THEME_THEMEID_ERROR;	//主题数据非法：主题ID非法
			}
			themeClassMapper.update(theme);
			return theme.getId();
		}
	}
	
	/**
	 * 逻辑删除主题
	 *  设置主题状态为'D'
	 * 1、如果待删主题有下级主题，则不可删除；
	 * 2、如果待删主题有关联文章，则不可删除；
	 *
	 */
	@Override
	public Long deleteTheme(Long themeId) {
		ThemeClass theme = themeClassMapper.selectByPrimaryKey(themeId);
		String themeSeq = ("/".equals(theme.getParentSeq())?"":theme.getParentSeq()) + "/" + themeId;
		int cntChildren = themeClassMapper.countUserThemes(theme.getOwnerId(), themeSeq,true);
		if(cntChildren>0) {
			return ErrorCodes.THEME_HAS_CHILDREN;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("theme_id", themeId);
		int cntRelAraticles = articleBriefMapper.countArticlesByUser(theme.getOwnerId(), true, params);
		if(cntRelAraticles>0) {
			return ErrorCodes.THEME_HAS_REL_ARTICLES;
		}
		themeClassMapper.updateStatus(themeId,"D");
		return theme.getId();
	}
	
	/**
	 * 根据主题ID获取主题信息
	 */
	@Override
	public ThemeClass getTheme(Long themeId) {
		return themeClassMapper.selectByPrimaryKey(themeId);
	}
	
	/**
	 * 根据主题名称取指定用户下的指定上级的主题信息
	 * @param themeName 主题名称
	 * @param parentSeq	上级主题ID序列
	 * @param ownerId	主题拥有者
	 * @return
	 */
	@Override
	public ThemeClass getTheme(String themeName,String parentSeq,Long ownerId) {
		return themeClassMapper.selectTheme(themeName, parentSeq, ownerId);
	}
	
	/**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentSeq	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
	@Override
	public List<ThemeClass> getThemes(Long ownerId,String parentSeq,boolean isSelf) {
		return themeClassMapper.selectUserThemes(ownerId, parentSeq,isSelf);
	}
	

	/**
	 * 统计同级指定层级的主题数量
	 * @param	ownerId	主题拥有者
	 * @param	parentSeq	上级主题ID序列
	 * @return	主题数量
	 */
	@Override
	public int countThemes(Long ownerId, String parentSeq) {
		return themeClassMapper.countUserThemes(ownerId, parentSeq,true);
	}
	
}



package me.jeekhan.leyi.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeInfo;
/**
 *  主题分类服务类
 * @author Jee Khan
 *
 */
public interface ThemeService {
	/**
	 * 保存主题分类
	 * @param theme
	 * @return 主题ID
	 */
	public Long saveTheme(ThemeInfo theme);
	
	/**
	 * 逻辑删除主题分类
	 * 1、没有下级主题才可删除；
	 * 2、主题下没有关联的文章才可删除；
	 * @param themeId
	 * @return 父主题ID
	 */
	public Long deleteTheme(Long themeId);
	
	/**
	 * 获取指定的主题分类
	 * @param themeId	主题ID
	 * @return
	 */
	public ThemeInfo getTheme(Long themeId);
	
	/**
	 * 根据主题名称取指定用户下的指定上级的主题信息
	 * @param themeName 主题名称
	 * @param parentId	上级主题ID序列
	 * @param ownerId	主题拥有者
	 * @return
	 */
	public ThemeInfo getTheme(String themeName,String parentId,Long ownerId);

	
    /**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentId	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
	public List<ThemeInfo> getThemes(Long ownerId,String parentId,boolean isSelf);
	
	/**
	 * 统计用户指定主题的下级主题数量
	 * @param ownerId	主题拥有者
	 * @param parentId	上级主题ID序列
	 * @return
	 */
    int countThemes(@Param("ownerId")Long ownerId,@Param("parentId")String parentId);
		
	/**
	 * 获取待审核的主题记录
	 * @param	pageCond	分页信息
	 * @return
	 */
	public List<ThemeInfo> getThemes4Review(PageCond pageCond);
	
	/**
	 * 主题审核
	 * @param themeId   主题ID
	 * @param result		审核结果:A-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 */
	public Long reviewTheme(Long themeId,String result,ReviewInfo reviewInfo);
	
	/**
	 * 取待审核主题数量
	 * @return
	 */
	public int get4ReviewThemesCnt();
	
}

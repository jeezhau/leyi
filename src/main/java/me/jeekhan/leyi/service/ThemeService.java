package me.jeekhan.leyi.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.ThemeClass;
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
	public Long saveTheme(ThemeClass theme);
	
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
	public ThemeClass getTheme(Long themeId);
	
	/**
	 * 根据主题名称取指定用户下的指定上级的主题信息
	 * @param themeName 主题名称
	 * @param parentSeq	上级主题ID序列
	 * @param ownerId	主题拥有者
	 * @return
	 */
	public ThemeClass getTheme(String themeName,String parentSeq,Long ownerId);

	
    /**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentSeq	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
	public List<ThemeClass> getThemes(Long ownerId,String parentSeq,boolean isSelf);
	
	/**
	 * 统计用户指定主题的下级主题数量
	 * @param ownerId	主题拥有者
	 * @param parentSeq	上级主题ID序列
	 * @return
	 */
    int countThemes(@Param("ownerId")Long ownerId,@Param("parentSeq")String parentSeq);
		
	
}

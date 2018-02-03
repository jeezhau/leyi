package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ThemeClass;

/**
 * 文章主题信息数据处理接口
 * @author jeekhan
 *
 */
public interface ThemeClassMapper {
	//更新主题状态
    Long updateStatus(@Param("id")Long id,@Param("status") String status);
    
    //新插入主题记录
    Long insert(ThemeClass record);
    
    //根据主键获取主题信息
    ThemeClass selectByPrimaryKey(Long id);
    
    	//根据主题名称取指定用户下的指定上级的主题信息
    ThemeClass selectTheme(@Param("name")String name,@Param("parentSeq")String parentSeq ,@Param("ownerId")Long ownerId);
    
    //更新主题信息
    Long update(ThemeClass record);
    
    /**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentSeq	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
    List<ThemeClass> selectUserThemes(@Param("ownerId")Long ownerId,@Param("parentSeq")String parentSeq,@Param("isSelf")boolean isSelf);
    
    //统计用户指定主题的下级主题数量
    int countUserThemes(@Param("ownerId")Long ownerId,@Param("parentSeq")String parentSeq,@Param("isSelf")boolean isSelf);
    
    	//获取待审核的主题记录
    List<ThemeClass> selectThemes4Review(PageCond pageCond);
    
    //统计待审核的主题记录数量
    int countThemes4Review();
}


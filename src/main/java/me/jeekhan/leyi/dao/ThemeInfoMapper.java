package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ThemeInfo;

/**
 * 文章主题信息数据处理接口
 * @author jeekhan
 *
 */
public interface ThemeInfoMapper {
	//更新主题状态
    Long updateStatus(@Param("id")Long id,@Param("status") String status);
    
    //新插入主题记录
    Long insert(ThemeInfo record);
    
    //根据主键获取主题信息
    ThemeInfo selectByPrimaryKey(Long id);
    
    	//根据主题名称取指定用户下的指定上级的主题信息
    ThemeInfo selectTheme(@Param("name")String name,@Param("parentId")String parentId ,@Param("ownerId")Long ownerId);
    
    //更新主题信息
    Long update(ThemeInfo record);
    
    //List<ThemeInfo> selectUserThemes(@Param("userId")Long userId,@Param("isSelf")boolean isSelf);
    

    /**
     * 取指定用户的主题信息
     * 1、isSelf 为true 则为用户自己，可以取所有的下级信息，为false只可取审核通过的信息；
     * @param ownerId	主题拥有者 
     * @param parentId	上级主题ID序列
     * @param isSelf		是否为用户自己
     * @return
     */
    List<ThemeInfo> selectThemes(@Param("ownerId'")Long ownerId,@Param("parentId")String parentId,@Param("isSelf")boolean isSelf);
    
    //统计用户指定主题的下级主题数量
    int countThemes(@Param("ownerId")Long ownerId,@Param("parentId")String parentId);
    
    	//获取待审核的主题记录
    List<ThemeInfo> selectThemes4Review(PageCond pageCond);
    
    //统计待审核的主题记录数量
    int countThemes4Review();
}


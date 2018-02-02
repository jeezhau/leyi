package me.jeekhan.leyi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;

/**
 * 文章概要信息数据处理接口
 * @author jeekhan
 *
 */
public interface ArticleBriefMapper {
	//变更文章状态
    Long updateStatus(@Param("id")Long id,@Param("status")String status);
    
    //新插入一条文章记录，返回文章ID
    Long insert(ArticleBrief record);
    
    //根据主键获取记录
    ArticleBrief selectByPrimaryKey(Long id);

    //根据主键更新记录，返回文章ID
    Long updateByPrimaryKey(ArticleBrief record);
    
    //根据查询条件统计指定用户的文章数量
    int countArticlesByUser(@Param("userId") Long userId,@Param("isSelf")boolean isSelf,Map<String,Object>params,
    		@Param("pageCond") PageCond pageCond);
    
    //根据查询条件获取指定用户的文章信息，以热度排序
    List<ArticleBrief> selectArticlesByUser(@Param("userId") Long userId,@Param("isSelf")boolean isSelf,Map<String,Object>params,
    		@Param("pageCond") PageCond pageCond);
    
    //根据查询条件统计系统所有用户的文章数量 
    int countArticlesAll(@Param("isSelf")boolean isSelf,Map<String,Object>params,@Param("pageCond") PageCond pageCond);
    
    //根据查询条件获取系统所有用户的文章信息，以热度排序 
    List<ArticleBrief> selectArticlesAll(@Param("isSelf")boolean isSelf,Map<String,Object>params,@Param("pageCond") PageCond pageCond);
    
    //取指定主题下文章数量及文章信息
    //int countArticlesByTheme(@Param("logicId") String logicId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    //List<ArticleBrief> selectArticlesByTheme(@Param("logicId") String logicId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    
    //根据文章信息从数据库中提取该条记录（最新）
    ArticleBrief selectLatestRecrod(ArticleBrief record);
    
    //取系统中最新待审核的记录
    List<ArticleBrief> selectArticles4Review(PageCond pageCond);
    
    //统计待审核文章数量
    int countArticles4Review();
}


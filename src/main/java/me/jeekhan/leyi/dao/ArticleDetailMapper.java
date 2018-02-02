package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.ArticleDetail;

/**
 * 文章详细信息数据处理接口
 * @author jeekhan
 *
 */
public interface ArticleDetailMapper {

	//删除文章详细信息，返回文章ID
    Long delete(Long articleId);
    
    //新插入文章详细信息，返回文章ID
    Long insert(ArticleDetail record);
    
    //根据主键获取文章详细信息
    ArticleDetail selectByPrimaryKey(Long articleId);
    
    //更新文章详细信息，返回文章ID
    Long update(ArticleDetail record);

}
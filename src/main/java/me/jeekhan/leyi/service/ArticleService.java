package me.jeekhan.leyi.service;

import java.util.List;
import java.util.Map;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleDetail;
import me.jeekhan.leyi.model.ReviewInfo;

/**
 * 文章相关服务
 * @author Jee Khan
 *
 */
public interface ArticleService {
	
	/**
	 * 保存文章简介信息
	 * @param articleBrief	文章简介
	 * @return	文章ID
	 */
	public Long saveArticleBrief(ArticleBrief articleBrief);
	
	/**
	 * 保存文章内容
	 * @param articleDetail	文章内容
	 * @return	文章ID
	 */
	public Long saveArticleDetail(ArticleDetail articleDetail);
	
	/**
	 * 保存文章全部信息
	 * @param articleBrief	文章简介
	 * @param articleDetail	文章内容
	 * @return	文章ID
	 */
	public Long saveArticleInfo(ArticleBrief articleBrief,ArticleDetail articleDetail);
	
	/**
	 * 逻辑删除指定的文章
	 * @param articleId
	 * @return	文章ID
	 */
	public Long deleteArticle(Long articleId);
	
	/**
	 * 获取指定文章的简介信息
	 * @param id	文章ID
	 * @return
	 */
	public ArticleBrief getArticleBref(Long id);
	
	/**
	 * 获取指定文章的内容
	 * @param id	文章ID
	 * @return
	 */
	public ArticleDetail getArticleDetail(Long id);
	
	/**
	 * 根据查询条件分页查询指定用户的所有文章信息，按热度（更新时间，评论数量，关注度的加权值）降序排列;
	 * @param userId		用户ID
	 * @param isSelf 	是否包含审核中的记录
	 * @param params		查询条件
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticlesByUser(Long userId,boolean isSelf,Map<String,Object> params,PageCond pageCond);
	
	/**
	 * 根据查询条件统计指定用户的所有文章信息
	 * @param userId		用户ID
	 * @param isSelf 	是否包含审核中的记录
	 * @param params		查询条件
	 * @return
	 */
	public int countArticlesByUser(Long userId,boolean isSelf,Map<String,Object> params);
	
	/**
	 * 根据查询条件分页查询显示系统所有用户的所有文章信息，按热度（更新时间，评论数量，关注度的加权值）降序排列;
	 * @param isSelf 是否包含审核中的记录
	 * @param params		查询条件
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticlesAll(boolean isSelf,Map<String,Object> params,PageCond pageCond);
    
	/**
	 * 根据查询条件统计系统所有用户的所有文章信息
	 * @param isSelf 是否包含审核中的记录
	 * @param params		查询条件
	 * @return
	 */
	public int countArticlesAll(boolean isSelf,Map<String,Object> params);
    
	/**
	 * 取待审核的文章记录
	 * @return
	 */
    public List<ArticleBrief> getArticles4Review(PageCond pageCond);

	/**
	 * 文章审核
	 * @param articleId	文章ID
	 * @param result	  	审核结果：A-通过，R-拒绝
	 * @param reviewInfo	审核信息
	 */
	public Long reviewArticle(Long articleId,String result,ReviewInfo reviewInfo);
	
	/**
	 * 取待审核文章数量
	 * @return
	 */
	public int countArticles4Review();
	
}

package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dao.ArticleBriefMapper;
import me.jeekhan.leyi.dao.ArticleDetailMapper;
import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.dao.ThemeInfoMapper;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleDetail;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeInfo;
import me.jeekhan.leyi.service.ArticleService;

/**
 * 文章相关服务
 * 功能权限部分使用其他方式控制
 * @author Jee Khan
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleBriefMapper articleBriefMapper;
	@Autowired
	private ArticleDetailMapper  articleDetailMapper;
	@Autowired
	private ReviewInfoMapper reviewInfoMapper;
	@Autowired
	private ThemeInfoMapper themeInfoMapper;
	/**
	 * 获取指定文章概要信息
	 * @param articleId	文章ID
	 * @return	文章概要信息
	 */
	@Override
	public ArticleBrief getArticleBref(Long articleId) {
		return articleBriefMapper.selectByPrimaryKey(articleId);
	}
	
	/**
	 * 获取指定文章内容
	 * @param articleId	文章ID
	 * @return	文章详细信息
	 */
	@Override
	public ArticleDetail getArticleDetail(Long articleId) {
		return articleDetailMapper.selectByPrimaryKey(articleId);
	}
	
	/**
	 * 根据查询条件分页查询指定用户的所有文章信息，按热度（更新时间，评论数量，关注度的加权值）降序排列;
	 * @param userId		用户ID
	 * @param isSelf 	是否包含审核中的记录
	 * @param params		查询条件
	 * @param pageCond	分页条件
	 * @return
	 */
	@Override
	public List<ArticleBrief> getArticlesByUser(Long userId,boolean isSelf,Map<String,Object> params,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesByUser(userId, isSelf,params,pageCond);
		return list;
	}
	
	/**
	 * 根据查询条件分页查询显示系统所有用户的所有文章信息，按热度（更新时间，评论数量，关注度的加权值）降序排列;
	 * @param isSelf 是否包含审核中的记录
	 * @param params		查询条件
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticlesAll(boolean isSelf,Map<String,Object> params,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesAll(isSelf,params,pageCond);
		return list;
	}
	
	/**
	 * 保存文章概要信息
	 * 有则更新，无则插入
	 * @param articleBrief	文章简介
	 * @return 文章ID或小于0的错误码
	 */
	@Override
	public Long saveArticleBrief(ArticleBrief articleBrief) {
		if(articleBrief == null){
			return ErrorCodes.LESS_INFO;
		}
		//主题验证
		ThemeInfo theme = themeInfoMapper.selectByPrimaryKey(articleBrief.getThemeId());
		if(theme == null || !theme.getOwnerId().equals(articleBrief.getOwnerId())) {
			return ErrorCodes.ARTICLE_THEMEID_ERROR;	//主题ID非法
		}
		
		articleBrief.setStatus("0");		//待审核
		articleBrief.setUpdateTime(new Date());
		
		if(articleBrief.getId() == null){ //新增
			articleBriefMapper.insert(articleBrief);
			Long id = articleBriefMapper.selectLatestRecrod(articleBrief).getId();	//取刚插入的一条文章
			return id;
		}else{	//修改
			ArticleBrief tmp = articleBriefMapper.selectByPrimaryKey(articleBrief.getId());
			if(!tmp.getOwnerId().equals(articleBrief.getId())) {
				return ErrorCodes.ARTICLE_ID_ERROR;	//文章ID非法
			}
			articleBriefMapper.updateByPrimaryKey(articleBrief);
			return articleBrief.getId();
		}
	}
	
	/**
	 * 保存文章内容：有则更新，无则插入
	 * 仅在文章概要保存完成之后才执行该操作
	 * @param articleDetail	文章内容
	 * @return 文章ID或错误码
	 */
	@Override
	public Long saveArticleDetail(ArticleDetail articleDetail) {
		if(articleDetail == null || articleDetail.getArticleId() == null){
			return ErrorCodes.LESS_INFO;	//缺少数据
		}
		
		if(articleDetailMapper.selectByPrimaryKey(articleDetail.getArticleId()) != null){	//已有记录
			articleDetailMapper.update(articleDetail);
		}else{
			articleDetailMapper.insert(articleDetail);
		}
		return articleDetail.getArticleId();
	}
	
	/**
	 * 保存文章全部信息：有则更新，无则插入
	 * @param articleBrief	文章简介
	 * @param articleDetail	文章内容  
	 * @return 文章ID或错误码
	 */
	@Override
	public Long saveArticleInfo(ArticleBrief articleBrief, ArticleDetail articleDetail) {
		if(articleBrief == null){
			return ErrorCodes.LESS_INFO;
		}
		//保存文章概要
		Long id = saveArticleBrief(articleBrief);
		if(id <= 0){
			return id;
		}
		//保存文章详细内容
		articleDetail.setArticleId(id);
		id = saveArticleDetail(articleDetail);
		return id;
	}
	
	/**
	 * 逻辑删除指定文章
	 */
	@Override
	public Long deleteArticle(Long articleId) {
		return articleBriefMapper.updateStatus(articleId,"D");
	}	
	
	
	/**
	 * 取待审核的记录
	 */
	public List<ArticleBrief> getArticles4Review(PageCond pageCond){
		return articleBriefMapper.selectArticles4Review(pageCond);
	}
	
	/**
	 * 文章审核
	 * @param articleId	文章ID
	 * @param result	  	审核结果：A-通过，R-拒绝
	 * @param reviewInfo	审核信息
	 */
	public Long reviewArticle(Long articleId,String result,ReviewInfo reviewInfo) {
		String briefInfo = articleBriefMapper.selectByPrimaryKey(articleId).toString();
		reviewInfo.setObjName("tb_article_brief");
		reviewInfo.setKeyId(articleId);
		reviewInfo.setOriginalInfo(briefInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		
		String contentInfo = articleDetailMapper.selectByPrimaryKey(articleId).toString();
		reviewInfo.setObjName("tb_article_detail");
		reviewInfo.setOriginalInfo(contentInfo);
		reviewInfoMapper.insert(reviewInfo);
		
		return articleBriefMapper.updateStatus(articleId,result);
		
	}
	/**
	 * 取待审核文章数量
	 * @return
	 */
	@Override
	public int get4ReviewArticlesCnt() {
		return articleBriefMapper.countArticles4Review();
	}
	
}

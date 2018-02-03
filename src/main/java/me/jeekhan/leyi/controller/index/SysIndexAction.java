package me.jeekhan.leyi.controller.index;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.UserService;

/**
 * 系统主页访问控制
 * @author jeekhan
 *
 */
@Controller
public class SysIndexAction {
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 系统访问首页
	 * 【权限】
	 * 		所用人
	 * 【功能说明】
	 * 		1、获取系统推荐的当前明星用户（最多三个）展现于首页；
	 * 		2、获取系统最热门的文章显示；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
	 */
	@RequestMapping(value="/")
	public String accessIndex(Map<String,Object>map){
		List<UserFullInfo> hotUsers = userService.getSysIndexShowUser();
		List<ArticleBrief> hotArticles = articleService.getArticlesAll(false, null, null);
		map.put("hotUsers", hotUsers);
		map.put("hotArticles", hotArticles);
		return "index";
	}

}



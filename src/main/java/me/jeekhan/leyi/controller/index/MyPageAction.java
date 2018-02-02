package me.jeekhan.leyi.controller.index;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ThemeInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeService;
import me.jeekhan.leyi.service.UserService;
/**
 * 个人用户首页展示管理
 * @author jeekhan
 *
 */
@Controller
@SessionAttributes({"operator","topThemes"})
public class MyPageAction {
	@Autowired
	ThemeService themeService;
	@Autowired
	ArticleService  articleService;
	@Autowired
	UserService userService;

	/**
	 * 取作者的主页信息
	 * 【权限】
	 * 		所有人
	 * 【功能说明】
	 * 		1.取作者信息；
	 * 		2.取作者的顶层主题；
	 * 		3.取作者的所有最新最热门文章；
	 * @param username
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}")
	public String MyIndexPage(@PathVariable("username")String username ,Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo != null){
			Long id = userInfo.getId();
			boolean isSelf = false;
			if(operator.getUserId() == id ){ //作者自己
				isSelf = true;
			}else{	//访问非自己
				if(!"A".equals(userInfo.getStatus()) ){ //用户当前未审核通过
					return "redirect:/";		//跳转至系统首页
				}
			}
			map.put("userInfo", userInfo);
			List<ThemeInfo> topThemes = themeService.getThemes(id, "/", isSelf);
			map.put("topThemes",topThemes);	//访问目标用户一级主题

			List<ArticleBrief> articleBriefs = articleService.getArticlesAll(isSelf, null, null);
			map.put("articles",articleBriefs);	//目标用户的最热门文章
			return "myIndex";
		}else{	//访问目标用户不存在
			return "redirect:/";
		}
	}
}

package me.jeekhan.leyi.common;

/**
 * 系统错误常量 CODE
 * @author jeekhan
 * CODE 说明：
 * 错误码为6位的负整数：前三位为功能码，后三位指定功能下的错误码；
 */

public class ErrorCodes {
	public static Long LESS_INFO = 0L;	//缺少信息
	
	//用户信息相关错误码，功能码：100
	public static Long USER_USERNAME_USED = -100001L;	//用户名已被使用
	public static Long USER_EMAIL_USED = -100002L;	//邮箱已被使用

	//主题信息相关错误码，功能码：101
	public static Long THEME_EXISTS_SAME = -101001L;			//已存在同名同级主题
	public static Long THEME_THEMES_CNT_LIMIT = -101002L;		//同级主题数已达上限（33个）
	public static Long THEME_PARENTSEQ_ERROR = -101003L;		//主题数据非法：上级主题ID序列非法
	public static Long THEME_THEMEID_ERROR = -101004L;		//主题数据非法：更新主题ID非法
	public static Long THEME_THEMES_LVL_LIMIT = -101005L;		//主题级别层次已达上限（3）
	public static Long THEME_HAS_CHILDREN = -101006L;		//主题拥有下级主题
	public static Long THEME_HAS_REL_ARTICLES = -101007L;		//主题拥有关联文章
	
	//文章信息相关错误码：功能码：102
	public static Long ARTICLE_ID_ERROR = -102001L;		//文章数据非法：更新文章ID非法：
	public static Long ARTICLE_THEMEID_ERROR = -102002L;	//文章数据非法：新增主题ID非法
}

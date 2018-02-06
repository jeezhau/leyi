package me.jeekhan.leyi.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import me.jeekhan.leyi.common.FileFilter;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;
/**
 * 图片通用处理
 * @author Jee Khan
 *
 */
@Controller
@RequestMapping("/common")
@SessionAttributes({"operator"})
public class PictureDealAction {
	@Autowired
	private UserService userService;
	/**
	 * Ckeditor上传图片
	 * 【权限】
	 * 	1、登录用户；
	 * 【功能说明】
	 * 	1.取文章信息，如果文章不存在则返回应用主页；
	 * 	2.取文章作者信息；
	 * 	3.保存显示模式（详情或审核）；
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadImg")
	@ResponseBody
	public void uploadImg(@RequestParam(value="upload")MultipartFile upload,@ModelAttribute("operator")Operator operator,PrintWriter out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(!upload.isEmpty()){
			String imgType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf('.')+1);
			String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + operator.getUsername() + "/";  
			
			response.setCharacterEncoding("utf-8");  
		    // CKEditor回调参数
			String callback = request.getParameter("CKEditorFuncNum");  
//			if (imgType.equals("jpeg")|| imgType.equals("jpg")||imgType.equals("gif") || imgType.equals("image/png")) {  
//		           
//		    }  
	        if (upload.getSize() > 3*1024*1024) {  
	            //out.println("<script type=\"text/javascript\">");  
	            out.println("function(){window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于3M')}();");  
	            //out.println("</script>");  
	            return;
	        }  
	        File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//图片上传路径  
	        String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
	        fileName += "." + imgType;
			FileOutputStream outFile = new FileOutputStream(path + fileName);
			InputStream in = upload.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n=in.read(buf))>0){
				outFile.write(buf, 0, n);
			}
			outFile.close();

		  
	        // 返回显示图片路径   
			String showPath = request.getContextPath() + "/common/showPic/" + operator.getUsername() + "/" + fileName;
	        out.println("<script type=\"text/javascript\">");  
	        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + showPath + "','')");  
	        out.println("</script>");  
	        return ;  
		}
	}
	/**
	 * 显示图片
	 * @param username
	 * @param picName
	 * @param out
	 * @throws IOException 
	 */
	@RequestMapping(value="/showPic/{username}/{picName}")
	public void showPicture(@PathVariable("username")String username,@PathVariable("picName")String picName,OutputStream out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + "USER_"+ userInfo.getId().longValue() + "/"; 
		File dir = new File(path);
		File[] files = dir.listFiles(new FileFilter(picName));
		if(files != null && files.length>0){
			BufferedImage image = ImageIO.read(files[0]);
			response.setContentType("image/*");  
			OutputStream os = response.getOutputStream();  
			String type = files[0].getName().substring(picName.length()+1);
			ImageIO.write(image, type, os);  
		}
	}
}

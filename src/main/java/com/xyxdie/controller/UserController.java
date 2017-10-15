package com.xyxdie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.xyxdie.model.Message;
import com.xyxdie.model.User;
import com.xyxdie.service.MessageService;
import com.xyxdie.service.UserService;
import com.xyxdie.util.AbstractBaseResp;
import com.xyxdie.util.UploadImg;
import com.xyxdie.util.Validate;
import com.xyxdie.vo.MessageJsonBean;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {

	//图片路径
	private final String IMGURL = "/WEB-INF/upload";
	//每页数量
	private final int PAGENUM = 5;
	private final float FPAGENUM = 5.0f;

	@Autowired
	private UploadImg uploadImg;

	@Autowired
	private UserService userService;

	@Autowired
	private Validate validate;

	@Autowired
	private MessageService messageService;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/")
	public String index(){
		return "index";
	}

	/**
	 * 查看留言
	 * @param model
	 */
	@RequestMapping("messageList")
	public void list(String pageIndex, HttpServletRequest request, HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		Long totalCount = messageService.findMessageCount();
		//计算出页数并返回给前台
		Integer totalPage = (int)( Math.ceil(totalCount / FPAGENUM) );
		Integer page = 1;
		if(pageIndex != null && (pageIndex.length()) != 0){
			page= Integer.valueOf(pageIndex);
		}
		List<MessageJsonBean> list = messageService.findMessageByPage(page, PAGENUM);
		baseResp.setObject(list);
		baseResp.setTotalCount(totalCount);
		baseResp.setTotalPage(totalPage);
		Gson gson = new Gson();
		JSONObject json = new JSONObject();
		String baseRespToJson = gson.toJson(baseResp);
		/*发送到前台*/
		response.setCharacterEncoding("utf-8");
		PrintWriter writer;
		try {
			json.put("baseResp", baseRespToJson);
			writer = response.getWriter();
			writer.print(baseRespToJson);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交留言
	 * @param result
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveMessage")
	public String saveMessage(String id, String content, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response)throws IOException, Exception {
		User sessionUser = (User) session.getAttribute("user");
		if(sessionUser != null) {
			Message message = new Message();
			message.setUserid(sessionUser.getId());
			if(content != null && (content.length()) != 0){
				message.setMessage(content);
				message.setDate(messageService.getDate());
				message.setIp(request.getRemoteAddr());
				messageService.saveMessage(message);
			}
		}
		return "index";
	}

	/**
	 * 详细信息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(){
		return "include/detail";
	}

	/**
	 * 我的消息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/mynews"}, method = {RequestMethod.GET, RequestMethod.HEAD})
	public String mynews(Model model){
		return "include/mynews";
	}


	/**
	 * 用户首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/user"}, method = {RequestMethod.GET, RequestMethod.HEAD})
	public String userIndex(HttpSession session, Model model){
		User sessionUser = (User)session.getAttribute("user");
		if(sessionUser != null){
			model.addAttribute("user", sessionUser);
			return "user/index";
		}
		model.addAttribute("user", new User());
		return "user/index";
	}

	/**
	 * 查看用户自身评论
	 * @param session
	 * @param model
	 * @param page  第几页
	 * @return
	 */
	@RequestMapping(value = "/user/message-{page}", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String message(HttpSession session, Model model, @PathVariable int page){
		User user = (User)session.getAttribute("user");
		List<Message> list = messageService.findMessagesByUserId(user.getId());
		model.addAttribute("messages", list);
		model.addAttribute("id", user.getId());
		model.addAttribute("count", (int)( Math.ceil(messageService.findMessageCount() / PAGENUM) ));
		return "user/message";
	}

	/**
	 * 删除用户评论
	 * @param session
	 * @param model
	 * @param mid   留言id
	 * @return
	 */
	@RequestMapping(value = "/user/deleteMessage-{mid}", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String deleteMessage(HttpSession session, Model model,
			@PathVariable int mid){
		User user = (User)session.getAttribute("user");
		messageService.deleteMessageById(mid);
		List<Message> list = messageService.findMessagesByUserId(user.getId());
		model.addAttribute("messages", list);
		return "user/message-1";
	}

	/**
	 * 用户注册 get
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/user/register"}, method = {RequestMethod.GET, RequestMethod.HEAD})
	public String register(Model model){
		model.addAttribute(new User());
		return "user/register";
	}

	/**
	 * 用户注册 POSt
	 * @param user  modelAttribute传递的user
	 * @param result
	 * @param session
	 * @return
	 */
	@RequestMapping(value = {"/user/register"}, method = {RequestMethod.POST, RequestMethod.HEAD})
	public String register(@Valid  User user,BindingResult result,
			HttpSession session){
		validate.registValidate(user, result);
		if(result.hasErrors()){
			return "user/register";
		}
		user.setType(1);
		userService.saveUser(user);
		session.setAttribute("user", user);
		return "user/index";
	}

	/**
	 * 用户登录 get
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String login(Model model, HttpSession session){
		//判断session
		User sessionUser = (User)session.getAttribute("user");
		if(sessionUser != null){
			model.addAttribute("user", sessionUser);
			return "user/index";
		}
		model.addAttribute("user", new User());
		return "user/login";
	}

	/**
	 * 用户登录POST
	 * @param user  获取前台表单传递的User对象
	 * @param result
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = {RequestMethod.POST, RequestMethod.HEAD})
	public String login(@Valid User user, BindingResult result,
			HttpSession session, Model model){
		//验证
		validate.loginValidate(user, result);
		if(result.hasErrors()){
			return "user/login";
		}
		session.setAttribute("user", userService.findByEmail(user.getEmail()));
		model.addAttribute("user", userService.findByEmail(user.getEmail()));
		return "user/index";
	}

	/**
	 * 退出登录
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/loginout", method = {RequestMethod.GET, RequestMethod.HEAD})
	public void loginOut(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		//清空session
		session.invalidate();
		response.sendRedirect(request.getContextPath() +"/user/login");
	}

	/**
	 * 编辑用户信息 get
	 * @param id    用户id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/edit-{id}", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String edit(@PathVariable int id, Model model){
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	/**
	 * 编辑用户信息 提交post
	 * @param user  用户对象
	 * @param id    用户id
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/user/edit-{id}", method = {RequestMethod.POST, RequestMethod.HEAD})
	public String edit(@Valid User user,@Valid int id, BindingResult result){
		validate.updateValidate(user, id, result);
		if(result.hasErrors()){
			return "user/edit";
		}
		User sqlUser = userService.findById(id);
		sqlUser.setEmail(user.getEmail());
		sqlUser.setName(user.getName());
		sqlUser.setPasswd(user.getPasswd());
		userService.updateUser(sqlUser);
		return "user/index";
	}

	/**
	 * 修改图片 get
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/edit-img", method = RequestMethod.GET)
	public String uploadOneFileHandler(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "user/editimg";
	}

	/**
	 * 修改图片 post
	 * @param request
	 * @param session
	 * @param file    图片文件
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/edit-img", method = RequestMethod.POST)
	public String uploadFileHandler(HttpServletRequest request, HttpSession session,
			@RequestParam("file") MultipartFile file, Model model) {
		User user = (User) session.getAttribute("user");
		// 上传目录
		String rootPath =   request.getSession().getServletContext().getRealPath(IMGURL);   
		System.out.println(rootPath);
		uploadImg.uploadimg(file, user, rootPath);
		model.addAttribute("user", user);
		return "user/index";
	}

	/**
	 * 根据页面返回对应的json数组
	 * @param page 第几页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/messageJson-{page}", method = {RequestMethod.GET, RequestMethod.HEAD})
	public List<MessageJsonBean> json(@PathVariable int page){
		List<MessageJsonBean> list = messageService.findMessageByPage(page, PAGENUM);
		return list;
	}

}

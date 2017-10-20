package com.xyxdie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.xyxdie.model.Child;
import com.xyxdie.model.Message;
import com.xyxdie.model.User;
import com.xyxdie.service.ChildService;
import com.xyxdie.service.MessageService;
import com.xyxdie.service.UserService;
import com.xyxdie.util.AbstractBaseResp;
import com.xyxdie.util.HttpClientUtils;
import com.xyxdie.vo.ChildJsonBean;
import com.xyxdie.vo.MessageJsonBean;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

	//每页数量
	private final int PAGENUM = 3;
	private final float FPAGENUM = 3.0f;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ChildService childService;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/")
	public String index(){
		return "index";
	}

	/**
	 * 微信端用户信息
	 * @return
	 */
	@RequestMapping("/userinfo")
	public void index(HttpServletRequest request, HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		String url = "http://xyx.hnzmh.com/wx_share.php?act=info";
		String str = "";
		try {
			str = HttpClientUtils.get(url, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		baseResp.setMessage(str);
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
	 * 父贴留言
	 * @param model
	 */
	@RequestMapping("messageList")
	public String messageList(String pageIndex, String openid, String nickname, String headimgurl, HttpSession session, HttpServletRequest request, HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		User user = new User();  
		if (openid!=null && openid.length()!=0) {
			nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
			user.setPasswd(openid);
			user.setName(nickname);  
			user.setImgUrl(headimgurl); 
			if (nickname.equals("项越兄弟")) {
				user.setType(2);
			} else {
				user.setType(1);
			}
			List<User> users = userService.findUserByOpenId(openid);
			if (users.size()==0) {
				userService.saveUser(user);
			} else {
				for (User u : users) {
					user.setId(u.getId());
					session.setAttribute("user", user);
				}
			}
		}
		Long totalCount = 1L;
		Integer page = 1;
		if(pageIndex != null && (pageIndex.length()) != 0){
			page= Integer.valueOf(pageIndex);
		}
		totalCount = messageService.findMessageCount();
		List<MessageJsonBean> list = new ArrayList<MessageJsonBean>();
		if (user.getType()==1) {
			totalCount = messageService.findMsingleCount(user.getId());
			list = messageService.findMessageBySingle(page, PAGENUM, user.getId());
		} else {
			totalCount = messageService.findMessageCount();
			list = messageService.findMessageByPage(page, PAGENUM);
		}
		for(MessageJsonBean me : list) {
			int pid = me.getId();
			Long status = childService.isGbook(pid, 1);
			if (status>0){
				me.setStatus(1);
			} else {
				me.setStatus(0);
			}
		}
		//计算出页数并返回给前台
		Integer totalPage = (int)( Math.ceil(totalCount / FPAGENUM) );
		baseResp.setObject(list);
		baseResp.setTotalCount(totalCount);
		baseResp.setTotalPage(totalPage);
		Gson gson = new Gson();
		JSONObject jsontwo = new JSONObject();
		String baseRespToJson = gson.toJson(baseResp);
		/*发送到前台*/
		response.setCharacterEncoding("utf-8");
		PrintWriter writer;
		try {
			jsontwo.put("baseResp", baseRespToJson);
			writer = response.getWriter();
			writer.print(baseRespToJson);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
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
	public void saveMessage(Integer pid, String content, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		User sessionUser = (User) session.getAttribute("user");
		int code = 0;
		if(sessionUser != null) {
			if(pid == null) {
				Message message = new Message();
				message.setUserid(sessionUser.getId());
				if(content != null && (content.length()) != 0){
					message.setMessage(content);
					message.setDate(messageService.getDate());
					message.setIp(request.getRemoteAddr());
					code = messageService.saveMessage(message);
				}
			} else {
				Child child = new Child();
				child.setPid(pid);
				child.setUserid(sessionUser.getId());
				child.setMessage(content);
				child.setDate(childService.getDate());
				child.setIp(request.getRemoteAddr());
				code = childService.saveChild(child);
			}
		}
		baseResp.setCode(code);
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
	 * 子贴留言
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("childList")
	public void childList(String pid, HttpServletRequest request, 
			HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		if (pid!=null && pid.length()!=0) {
			int id = Integer.parseInt(pid);
			List<ChildJsonBean> list = childService.findAllChild(id);
			List<MessageJsonBean> messageJsonBean = messageService.findMessageJsonBeanById(id);
			if (null != messageJsonBean && messageJsonBean.size() > 0) {
				for (MessageJsonBean me : messageJsonBean) {
					ChildJsonBean childJsonBean = new ChildJsonBean(1, id, me.getName(), me.getIp(), me.getDate(),  me.getMessage(),  me.getImgUrl(), 1);
					list.add(childJsonBean);
				}
			}
			baseResp.setObject(list);
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
	}


}

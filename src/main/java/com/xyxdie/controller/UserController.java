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
	public void index(String code, String adopenid, HttpServletRequest request, HttpSession session, HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		User user = new User();
		try {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx6aa1205fde028896&secret=17286f5eb8f40927c7936d7c63324f18&code="+code+"&grant_type=authorization_code";
			String str = HttpClientUtils.get(url, "UTF-8");
			if(str != null && str.length()!=0){
				JSONObject jsStr = JSONObject.fromObject(str);
				String accesstoken = jsStr.getString("access_token");
				String openid = jsStr.getString("openid");
				String urlq = "https://api.weixin.qq.com/sns/userinfo?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
				String strq = HttpClientUtils.get(urlq, "UTF-8");
				if(strq != null && strq.length()!=0){
					JSONObject jsStrq = JSONObject.fromObject(strq);
					String nickname = jsStrq.getString("nickname");
					String headimgurl = jsStrq.getString("headimgurl");
					user.setPasswd(openid);
					user.setImgUrl(headimgurl);
					user.setName(nickname);
					if (openid.equals(adopenid)) {
						user.setType(2);
					} else {
						user.setType(1);
					}
					List<User> list = userService.findUserByOpenId(openid);
					if(list!=null && list.size() !=0 ){
						for (User u : list) {
							user.setId(u.getId());
							userService.updateUser(user);
						}
					}else {
						userService.saveUser(user);
					}
					request.getSession().setAttribute("user", user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		baseResp.setObject(user);
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
	public String messageList(String pageIndex, String userid, HttpServletRequest request, HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		try {
			Long totalCount = 1L;
			Integer page = 1;
			if(pageIndex != null && (pageIndex.length()) != 0){
				page= Integer.valueOf(pageIndex);
			}
			totalCount = messageService.findMessageCount();
			List<MessageJsonBean> list = new ArrayList<MessageJsonBean>();
			if (userid!=null && userid.length()!=0) {
				int id = Integer.parseInt(userid);
				User u = userService.findById(id);
				if (u.getType()==1) {
					totalCount = messageService.findMsingleCount(id);
					list = messageService.findMessageBySingle(page, PAGENUM, id);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void saveMessage(String pid, String userid, String content, HttpServletRequest request, 
			HttpServletResponse response)throws IOException, Exception {
		AbstractBaseResp baseResp = new AbstractBaseResp();
		int code = 0;
		if (userid!=null && userid.length()!=0 && content != null && (content.length()) != 0) {
			int id = Integer.parseInt(userid);
			if (pid!=null && pid.length()!=0) {
				Child child = new Child();
				child.setPid(Integer.parseInt(pid));
				child.setUserid(id);
				child.setMessage(content);
				child.setDate(childService.getDate());
				child.setIp(request.getRemoteAddr());
				code = childService.saveChild(child);
			} else {
				Message message = new Message();
				message.setUserid(id);
				message.setMessage(content);
				message.setDate(messageService.getDate());
				message.setIp(request.getRemoteAddr());
				code = messageService.saveMessage(message);
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

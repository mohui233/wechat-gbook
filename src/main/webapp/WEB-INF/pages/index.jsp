<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>留言大厅</title>
    <meta charset="UTF-8"/>
   	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta content="telephone=no" name="format-detection" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
    <%-- <c:import url="/WEB-INF/pages/include/inc.jsp"/> --%>
</head>
<body class="main-page">
	<div class="head">
		<h2>留言大厅</h2>
		<span class="des">管理员会尽快处理您的留言</span>
		<a href=""><button class="btn news-btn"><img src="${pageContext.request.contextPath}/img/comments.png"/></button></a>
		<button class="btn publish-btn">发布留言</button>
		<span class="dot"></span>
		<span class="line"></span>
	</div>

	<div class="list" id="list">
		<script id="list-content" type="text/x-dot-template">
				{{~it:item:index}}
				<div class="item" data-id="{{= item.id}}">
					<div class="info">
						<img class="headpic" src="{{= item.img}}" />
						<span class="name">{{= item.name}}</span>
						<span class="date">{{= item.date}}</span>
					</div>
					<div class="content">
						<span class="question">{{= item.question}}</span>
						<span class="answer"><span>{{= item.answer}}</span></span>
					</div>
				</div>
				{{~}}
			</script>

		<div id="pages" class="pages">
			<button class="prev">上一页</button>
			<span class="page-number"><span class="curr">1</span> <span class="total-page">1</span></span>
			<button class="next">下一页</button>
		</div>
	</div>

	<div class="foot">
		<span class="line"></span>
		<div class="links">
			<a href="">最新活动</a>
			<a href="">客户案例</a>
			<a href="">在线娱乐</a>
			<a href="">联系我们</a>
			<a href="">官方活动</a>
			<% 
				User u = (User) request.getSession().getAttribute("user");
				if(null != u) {
			%>
			<input type="hidden" name="id" value="<%=u.getId()%>"/>
			<%
				}
				else 
					out.print("请发布消息！");
			%>
		</div>
		<span class="company">北京项越兄弟智能工程技术有限公司</span>
	</div>

	<div class="edit-view" id="edit-view">
		<div class="panel">
		<form method="post">
			<textarea id="textarea" autocomplete="off" name="content"></textarea>
			<span class="placeholder">发布新内容</span>
			<button type="button" class="btn cancel-btn">取消</button>
			<button type="button" class="btn submit-btn">发布</button>
		 </form>		
		</div>
	</div>

	<div class="loadjs">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/dot.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/main.js" type="text/javascript" charset="utf-8"></script>
		<script>
			$(window).ready(function() {
				window.localStorage.removeItem("pid");
				var href = "http://www.xyxdie.com/gbook";
				var redirectUri = encodeURIComponent(href);
				var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6aa1205fde028896&redirect_uri=' + redirectUri + '&response_type=code&scope=snsapi_userinfo&state=xyxdie#wechat_redirect';
				//获取url中的参数
				function getUrlParam(name) {
					var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
					var r = window.location.search.substr(1).match(reg);  //匹配目标参数
					if (r != null) return unescape(r[2]); return null; //返回参数值
				}
				var code = getUrlParam('code');
				var id = $('input[name=id]').val();
				if(!code && !id) {
					 window.open(url, '_self');
				}else {
					var adopenid = "xxxxx";
					$.ajax({
						type: "post",
						dataType: "json",
						async: true,
						url: "userinfo",
						data: {
							code : code,
							adopenid: adopenid
						},	
						success: function(data) {
							var userid = data.object.id;
							window.localStorage.setItem("userid", userid)
							var pageIndex = location.hash.replace('#page=', '');
							pageIndex = pageIndex ? pageIndex : 1;
							/*加载分页数据*/
							if($('#list').length > 0) {
								var data = {
										pageIndex :	pageIndex,
										userid : userid
								};
								load_data(data);
							}
						},
						error: function(data) {
							console.log(data)
						}
					});
			    }
		    });
		</script>
	</div>
</body>
</html>
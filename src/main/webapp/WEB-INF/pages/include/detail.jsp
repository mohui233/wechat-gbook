<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<meta charset="utf-8" />
		<title>详细信息</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
	</head>

	<body class="detail-page">
		<div class="head">
			<span class="des">每一条留言我们都会认真反馈</span>
			<a href="javascript:window.history.back();location.reload();">
				<button class="btn news-btn"><img src="${pageContext.request.contextPath}/img/back.png"/></button>
			</a>
			<button class="btn publish-btn">快捷回复</button>
			<span class="line"></span>
		</div>

		<div class="list">
			<div class="btns">
				<button class="btn reply-btn">回复</button>
				<a class="goback">返回首页</a>
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
			</div>
			<span class="company">北京项越兄弟智能工程技术有限公司</span>
		</div>
		
		<div class="edit-view" id="edit-view">
			<div class="panel">
			<form method="post">
				<textarea id="textarea" autocomplete="off" name="content"></textarea>
				<span class="placeholder">回复内容</span>
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
				$(".goback").click(function(){
					history.go(-1);
				});				
				var pname = window.localStorage.getItem("pname");
				if (!pname){
					pname = "用户";
				}
				$(".detail-page .head").prepend(
					"<h2>"+pname+" 的留言</h2>"
				);
				var pid = window.localStorage.getItem("pid");
				$.ajax({
					type: "post",
					dataType: "json",
					async: true,
					url: "childList",
					data: {
						pid : pid
					},
					success: function(data) {
						for (var i = 0; i < data.object.length; i++) {
							var a = data.object[i];
							if (a.type==1) {
								$(".list").prepend(
									"<div class='item self'>" +
									"<div class='info'>" +
									"<img class='headpic' src="+a.imgUrl+" />" + 
									"<span class='name'> "+a.name+" </span>" + 
									"<span class='date'>"+a.date+"</span>" +
									"</div>" + 
									"<div class='content'>" +
									"<span class='caption'>"+a.message+"</span>" +
									"</div>" +
									"</div>"
								);
							} else {
								$(".list").prepend(
										"<div class='item other'>" +
										"<div class='info'>" +
										"<img class='headpic' src="+a.imgUrl+" />" + 
										"<span class='name'> "+a.name+" </span>" + 
										"<span class='date'>"+a.date+"</span>" +
										"</div>" + 
										"<div class='content'>" +
										"<span class='caption'>"+a.message+"</span>" +
										"</div>" +
										"</div>"
								);
							}
						}
					},
					error: function(data) {
						console.log(data)
					}
				});
			</script>
		</div>
	</body>
</html>
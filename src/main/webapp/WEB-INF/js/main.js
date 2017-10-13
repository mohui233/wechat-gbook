$(window).ready(function() {

	// 所有页面
	// 留言编辑面板
	var height = Math.max(180, $(window).height() * 0.35) + 56;
	$('.edit-view .panel').css('margin-top', -height + 'px');

	/*隐藏面板*/
	$('.edit-view .cancel-btn').click(function() {
		hideEditView();
	});

	$('.edit-view').click(function(e) {
		if(e.target.id == 'edit-view') {
			hideEditView();
		}
	});

	var hideEditView = function(callback) {
		$('.edit-view .panel').css('margin-top', -height + 'px');
		setTimeout(function() {
			$('.edit-view').removeClass('show');

			if(callback) {
				callback();
			}
		}, 400);
	};
	/*隐藏面板*/

	/*文本编辑*/
	$('.edit-view textarea').on('input propertychange', function() {
		var val = $(this).val();
		if(val) {
			$('.edit-view .placeholder').hide();
		} else {
			$('.edit-view .placeholder').show();
		}
	});
	/*文本编辑*/

	/*发布留言*/
	$('.publish-btn').click(function() {
		$('.edit-view').addClass('show');
		setTimeout(function() {
			$('.edit-view .panel').css('margin-top', '0px');
		}, 20);
	});
	/*发布留言*/

	/*回复留言*/
	$('.reply-btn').click(function() {
		$('.edit-view').addClass('show');
		setTimeout(function() {
			$('.edit-view .panel').css('margin-top', '0px');
		}, 20);
	});
	/*回复留言*/

	/*提交*/
	$('.submit-btn').click(function() {
		hideEditView(function() {
			location.reload();
		});
	});
	/*提交*/

	/*加载分页数据*/
	if($('#list').length > 0) {
		var list = [];
		$.ajax({
			type: "post",
			dataType: "json",
			async: true,
			url: "messageList",
			success: function(data) {
				list = [];
				var answer = '管理员：请等待我的回复';
				for (var i = 0; i < data.object.length; i++) {
					var a = data.object[i];
					var item = {
							id: 1,
							name: a.name,
							date: a.date,
							img: 'upload/' + a.imgUrl,
							question: a.message,
							answer: answer
					};
					list.push(item)
				}
				var data = {
					totalCount: totalCount,
					list: list
				}
				//加载数据
				var temp = doT.template($("#list-content").text());
				$("#list").prepend(temp(data.list));
				//进入详情
				$('.list .item .content').click(function() {
					var id = $(this).parents('.item').attr('data-id');
					window.open('detail.html?id=' + id, '_self');
				});
			},
			error: function(data) {
				console.log(data)
			}
		})

		console.log(totalCount);
		console.log(pageCount);
		/*分页信息*/
		var totalPage = pageCount;

		var pageIndex = location.hash.replace('#page=', '');
		pageIndex = pageIndex ? pageIndex : 1;

		if(pageIndex == 1) {
			$('.prev').addClass('disable');
		}

		if(pageIndex == totalPage) {
			$('.next').addClass('disable');
		}

		$('.pages .curr').text(pageIndex);
		$('.pages .total-page').text(totalPage);
		/*分页信息*/
	}
	/*加载分页数据*/

	/*上一页*/
	$('.prev').click(function() {
		if(!$(this).hasClass('disable')) {
			var pageIndex = location.hash.replace('#page=', '');

			if(pageIndex > 1) {
				location.hash = "#page=" + (--pageIndex);
			}

			if(pageIndex == 1) {
				$(this).addClass('disable');
			}

			$('.pages .curr').text(pageIndex);
			$('.next').removeClass('disable');
		}
	});
	/*上一页*/

	/*下一页*/
	$('.next').click(function() {
		if(!$(this).hasClass('disable')) {
			var pageIndex = location.hash.replace('#page=', '');
			pageIndex = pageIndex ? pageIndex : 1;

			if(pageIndex < totalPage) {
				location.hash = "#page=" + (++pageIndex);
			}

			if(pageIndex == totalPage) {
				$(this).addClass('disable');
			}

			$('.pages .curr').text(pageIndex);
			$('.prev').removeClass('disable');
		}
	});
	/*下一页*/

	/*判断当前页*/

	/*判断当前页*/

	//留言板首页
	if($('body').hasClass('main-page')) {

	}

	//我的信息列表页
	if($('body').hasClass('news-page')) {
		window.open('https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx905a34f609765252&redirect_uri=http://www.baidu.com&response_type=code&scope=snsapi_userinfo&state=19920813#wechat_redirect', '_self');
	}
	
	//我的信息详情页
	if($('body').hasClass('detail-page')) {

	}
});

//格式化日期
Date.prototype.Format = function(fmt) { //author: meizz 
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
	$(function() {
		//导航切换
		$(".menuson .header").click(
				function() {
					var $parent = $(this).parent();
					$(".menuson>li.active").not($parent).removeClass(
							"active open").find('.sub-menus').hide();

					$parent.addClass("active");
					if (!!$(this).next('.sub-menus').size()) {
						if ($parent.hasClass("open")) {
							$parent.removeClass("open").find('.sub-menus')
									.hide();
						} else {
							$parent.addClass("open").find('.sub-menus').show();
						}

					}
				});

		// 三级菜单点击
		$('.sub-menus li').click(function(e) {
			$(".sub-menus li.active").removeClass("active")
			$(this).addClass("active");
		});

		$('.title').click(function() {
			var $ul = $(this).next('ul');
			$('dd').find('.menuson').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('.menuson').slideUp();
			} else {
				$(this).next('.menuson').slideDown();
			}
		});
	})
</script>


</head>

<body style="background:#fff3e1;">
	<div class="lefttop">
		<span></span>配置管理
	</div>

	<dl class="leftmenu">
		<dd>
			<div class="title">
				<span><img src="images/leftico01.png" />
				</span>172.20.0.11(service)
			</div>
			<ul class="menuson">
				<li>
					<div class="header">
						<cite></cite>mi-scheduler
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-scheduler&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-scheduler-notice
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-scheduler-notice&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-app
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-app&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-base
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-base&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-finance
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-finance&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-msg
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-msg&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-nhl
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-nhl&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-p1
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-p1&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-smart
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-smart&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-service-uum
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=service&server=172.20.0.11&path=mi-service-uum&name=service.properties&type=properties" target="rightFrame">service.properties</a>
						</li>
					</ul>
			     </li>
			</ul>
		</dd>

        <dd>
			<div class="title">
				<span><img src="images/leftico02.png" />
				</span>172.20.0.11(jetty)
			</div>
			<ul class="menuson">
				<li>
					<div class="header">
						<cite></cite>osp
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=ehcache-shiro.xml&type=xml" target="rightFrame">ehcache-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=fdfs_client.conf&type=conf" target="rightFrame">fdfs_client.conf</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=resources.properties&type=properties" target="rightFrame">resources.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-config.xml&type=xml" target="rightFrame">spring-config.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-config-cxf.xml&type=xml" target="rightFrame">spring-config-cxf.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-config-service.xml&type=xml" target="rightFrame">spring-config-service.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-config-shiro.xml&type=xml" target="rightFrame">spring-config-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-mvc.xml&type=xml" target="rightFrame">spring-mvc.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=osp&name=spring-mvc-shiro.xml&type=xml" target="rightFrame">spring-mvc-shiro.xml</a></li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>uum
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=uum&name=resources.properties&type=properties" target="rightFrame">resources.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=uum&name=spring-config.xml&type=xml" target="rightFrame">spring-config.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=uum&name=spring-config-service.xml&type=xml" target="rightFrame">spring-config-service.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=uum&name=spring-config-shiro.xml&type=xml" target="rightFrame">spring-config-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.20.0.11&path=uum&name=spring-mvc.xml&type=xml" target="rightFrame">spring-mvc.xml</a></li>
					</ul>
			     </li>
			</ul>
		</dd>

		<dd>
			<div class="title">
				<span><img src="images/leftico02.png" />
				</span>172.30.0.10
			</div>
			<ul class="menuson">
				<li>
					<div class="header">
						<cite></cite>mi-api-nhl-member
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=ehcache.xml&type=xml" target="rightFrame">ehcache.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=fdfs_client.conf&type=conf" target="rightFrame">fdfs_client.conf</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=log4j.properties&type=properties" target="rightFrame">log4j.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=resources.properties&type=properties" target="rightFrame">resources.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config.xml&type=xml" target="rightFrame">spring-config.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-cache.xml&type=xml" target="rightFrame">spring-config-cache.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-cxf.xml&type=xml" target="rightFrame">spring-config-cxf.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-mongo.xml&type=xml" target="rightFrame">spring-config-mongo.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-redis.xml&type=xml" target="rightFrame">spring-config-redis.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-service.xml&type=xml" target="rightFrame">spring-config-service.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-config-shiro.xml&type=xml" target="rightFrame">spring-config-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-member&name=spring-mvc.xml&type=xml" target="rightFrame">spring-mvc.xml</a></li>
					</ul>
			     </li>
				<li>
					<div class="header">
						<cite></cite>mi-api-nhl-smart
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=ehcache.xml&type=xml" target="rightFrame">ehcache.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=fdfs_client.conf&type=conf" target="rightFrame">fdfs_client.conf</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=log4j.properties&type=properties" target="rightFrame">log4j.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=resources.properties&type=properties" target="rightFrame">resources.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config.xml&type=xml" target="rightFrame">spring-config.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-cache.xml&type=xml" target="rightFrame">spring-config-cache.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-cxf.xml&type=xml" target="rightFrame">spring-config-cxf.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-mongo.xml&type=xml" target="rightFrame">spring-config-mongo.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-redis.xml&type=xml" target="rightFrame">spring-config-redis.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-service.xml&type=xml" target="rightFrame">spring-config-service.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-config-shiro.xml&type=xml" target="rightFrame">spring-config-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-api-nhl-smart&name=spring-mvc.xml&type=xml" target="rightFrame">spring-mvc.xml</a></li>
					</ul>
			     </li>
			     <li>
					<div class="header">
						<cite></cite>mi-web-smart
						<i></i>
					</div>
					<ul class="sub-menus">
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=fdfs_client.conf&type=conf" target="rightFrame">fdfs_client.conf</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=log4j.properties&type=properties" target="rightFrame">log4j.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=resources.properties&type=properties" target="rightFrame">resources.properties</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=spring-config.xml&type=xml" target="rightFrame">spring-config.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=spring-config-service.xml&type=xml" target="rightFrame">spring-config-service.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=spring-config-shiro.xml&type=xml" target="rightFrame">spring-config-shiro.xml</a></li>
						<li><a href="<%=basePath%>PropertyServlet?method=readContent&deployType=jetty&server=172.30.0.10&path=mi-web-smart&name=spring-mvc.xml&type=xml" target="rightFrame">spring-mvc.xml</a></li>
					</ul>
			     </li>
			</ul>
		</dd>
	</dl>
</body>
</html>

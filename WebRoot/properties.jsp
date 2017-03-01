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
<link href="css/tip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
      $(document).ready(function(){
		  $(".sure").click(function(){
			  $(".tip").fadeOut(100);
			});
		
		  $(".cancel").click(function(){
			  $(".tip").fadeOut(100);
		  });
		  
		  $("#save").click(function(){
			  $.ajax({
				  type: 'POST',
				  url: "<%=basePath%>PropertyServlet",
				  data: {
				      method : 'writeContent',
				      content : $("#content").val()
				  },
				  success: function(){
				       $(".tip").fadeIn(200);
				  }
		     });
		   });
	});
</script>
</head>

<body>
    <table>
         <tr>
           <td>
               <textarea id="content" rows="30" cols="130">${content}</textarea>
           <td>
         </tr>
         <tr>
           <td>
               <button id="save">保存</button>
           <td>
         </tr>
    </table>
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
            <p>保存成功</p>
        </div>
      </div>
      <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
      </div>
    </div>
</body>
</html>

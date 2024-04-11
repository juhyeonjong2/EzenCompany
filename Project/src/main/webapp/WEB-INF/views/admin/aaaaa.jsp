<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
 <script>
//notifySend
  $('#notifySendBtn').click(function(e){
	  socket.send("관리자,");	
  });
  </script>
</head>
<body>
<%@ include file="../include/socketHeader.jsp"%>
<div class="container">
	<h1 class="page-header">Chat</h1>		
	
	<table class="table table-bordered">
		<tr>
			<td colspan="2"><div id="list"></div></td>
		</tr>
		<tr>
			<td><button id="notifySendBtn"></button></td>
			<td colspan="2"><input type="text" name="msg" id="msg" placeholder="대화 내용을 입력하세요." class="form-control" disabled></td>
		</tr>
	</table>
	
</div>
</body>
</html>
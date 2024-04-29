<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
session = request.getSession();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function logout() {
	window.location.href = '/11005/logout.do';
}

window.onload = function() {
	const msg = '${msg}';
	
	if (msg !== '') {
		alert(msg);
		// location.href = '/11005/join-page.do';
	}
}
</script>
</head>
<body>
	memberId = ${sessionScope.memberId}
	<input type="button" value="Logout" onClick="javascript:logout()"> 
</body>
</html>
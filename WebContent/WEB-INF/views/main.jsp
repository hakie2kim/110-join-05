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
function doLogout() {
	window.location.href = '/05/logout.do';
}
</script>
</head>
<body>
	memberId = ${sessionScope.memberId}
	<input type="button" value="Logout" onClick="javascript:doLogout()"> 
</body>
</html>
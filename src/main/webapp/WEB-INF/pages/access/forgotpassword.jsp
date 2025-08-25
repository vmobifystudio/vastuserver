<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vastu - Recover Password</title>
</head>
<c:url value="/access/forgotPassword" var="forgotPassword" />
<body>

	<form action="${forgotPassword}" method="post">
		<fieldset>
			<legend>Recover Password</legend>
			<label>Email *</label>
			<input name="email" />
		</fieldset>
		<span class="label label-inverse">Fields marked with * are
			mandatory</span>
		<br />
		<br />
		<button class="btn btn-info" type="submit">Recover Password</button>
           	&nbsp;&nbsp;&nbsp;&nbsp;
           	<a class="btn btn-danger" href="javascript:history.back(1)">Cancel
		</a>
	</form>

</body>
</html>
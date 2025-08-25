<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>vastu - Register User</title>
<script type="text/javascript">
$(document).ready(function(){
	$('#dob').datepicker();
});
</script>
</head>
<body>
	<c:url value="/register/user" var="registerUser"/>
	<form:form id="registerUser" action="${registerUser}" method="post" modelAttribute="user">
		<div>
			<c:if test="${not empty message}">
				<div id="message" class="success">${message}</div>
			</c:if>
			<s:bind path="*">
				<c:if test="${status.error}">
					<div id="message" class="alert alert-danger">Form has errors</div>
				</c:if>
			</s:bind>
		</div>
		<fieldset>
			<legend>User</legend>
			<form:errors path="firstName" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">First Name*</span><form:input path="firstName" cssClass="form-control"/>
			</div>
			<br>
			<form:errors path="lastName" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Last Name*</span><form:input path="lastName" cssClass="form-control"/>
			</div>
			<br>	
			<form:errors path="email" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Email*</span><form:input path="email" cssClass="form-control"/>
			</div>
			<br>
			<form:errors path="mobileNumber" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Mobile Number*</span><form:input path="mobileNumber" cssClass="form-control"/>
			</div>
			<br>
			<form:errors path="gender" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Gender</span>
				<select id="gender" name="gender" class="form-control">
					<option value="M">Male</option>
					<option value="F">Female</option>
				</select>
			</div>
			<br>
			<form:errors path="dob" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Date of Birth</span><input name="dob" id="dob" class="form-control" value="${today}" data-date-format="dd-mm-yyyy"/>
			</div>
			<br>
			<form:errors path="userRoles" cssClass="alert-danger" />
			<div class="input-group width-xlarge">
				<span class="input-group-addon">Register As </span>
				<select id="isBusinessUser" name="isBusinessUser" class="form-control">
					<option value="false">Customer</option>
					<option value="true">Business User + Customer</option>
				</select>
			</div>
		</fieldset>
		<span class="label label-warning">Fields marked with * are mandatory</span>
		<br>
		<br>
		<button class="btn btn-success" type="submit">Submit</button>
		<a class="btn btn-danger" href="javascript:history.back(1)">Cancel</a>
	</form:form>
</body>
</html>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - Add User</title>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>

</head>
<c:url value="/user/add" var="userAdd" />
<c:url value="/user/list" var="userList" />


<body>

	<%--@ include file="nav.jsp"--%>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>

	<form:form id="addUser" method="post" modelAttribute="user"
		action="${userAdd}">
		<div>
			<c:if test="${not empty message}">
				<div id="message" class="success">${message}</div>
			</c:if>
			<s:bind path="*">
				<c:if test="${status.error}">
					<div id="message" class="alert alert-error">Form has errors</div>
				</c:if>
			</s:bind>
		</div>
		<fieldset>
			<legend>User</legend>
			<form:label path="name">
                    Name <form:errors path="name"
					cssClass="alert-error" />
			</form:label>
			<form:input path="name" cssClass="form-control width-xlarge"/>
			<%-- <form:label path="lastName">
                    Last Name <form:errors path="lastName"
					cssClass="alert-error" />
			</form:label>
			<form:input path="lastName" cssClass="form-control width-xlarge"/> --%>
			<form:label path="password">
                    Password <form:errors path="password"
					cssClass="alert-error" />
			</form:label>
			<form:password path="password" cssClass="form-control width-xlarge"/>

			<form:label path="email">
                    Email* <form:errors path="email"
					cssClass="alert-error" />
			</form:label>
			<form:input path="email" cssClass="form-control width-xlarge"/>
			<form:label path="mobileNumber">
                    Mobile Number* <form:errors path="mobileNumber"
					cssClass="alert-error" />
			</form:label>
			<form:input path="mobileNumber" cssClass="form-control width-xlarge"/>
			<form:label path="userRoles">
                    Choose Role* <form:errors path="userRoles"
					cssClass="alert-error" />
			</form:label>
			<form:select path="userRoles" multiple="false" cssClass="form-control width-medium">
				<form:options items="${roleList}" itemValue="id" itemLabel="role" />
			</form:select>
		</fieldset>
		<br />
		<span class="label label-inverse">Fields marked with * are
			mandatory</span>
		<br />
		<br />
		<button class="btn btn-info" type="submit">Add</button>
           	&nbsp;&nbsp;&nbsp;&nbsp;
           	<a class="btn btn-danger" href="javascript:history.back(1)">Cancel
		</a>
	</form:form>
</body>
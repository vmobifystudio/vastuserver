<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>vastu - Update tip</title>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<c:url value="/tip/update" var="updatetip"/>
	<form:form id="updatetip" action="${updatetip}" method="post" modelAttribute="tip">
		<div>
			<c:if test="${not empty message}">
				<div id="message" class="success">${message}</div>
			</c:if>
			<s:bind path="*">
				<c:if test="${status.error}">
					<div id="message" class="text text-danger">Form has errors</div>
				</c:if>
			</s:bind>
		</div>
		<fieldset>
			<legend>Tip</legend>
					<form:errors path="tipTitle" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Tip Title*</span><form:input path="tipTitle" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="tipDescription" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Tip Description*</span><form:textarea path="tipDescription" cssClass="form-control" rows="5"></form:textarea>
					</div>
					<br>
		</fieldset>
		<span class="label label-warning">Fields marked with * are mandatory</span>
		<br>
		<br>
		<c:if test="${tip.isEditAllowed}">
			<button class="btn btn-success" type="submit">Submit</button>
		</c:if>
		<a class="btn btn-danger" href="javascript:history.back(1)">Cancel</a>
	</form:form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MySkool - Add System Property</title>

</head>

<c:url value="/systemproperty/add" var="propertyAdd" />
<c:url value="/systemproperty/list" var="propertyList" />
<c:choose>
	<c:when test="${not empty retUrl}">
		<c:url value="${retUrl}" var="cancelUrl" />
	</c:when>
	<c:otherwise>
		<c:url value="/systemproperty/list" var="cancelUrl" />
	</c:otherwise>
</c:choose>

<body>

	<form:form id="addProperty" method="post" modelAttribute="property"
		action="${propertyAdd}">
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
			<legend>Add New Property</legend>
			<form:label path="propName">
                    Property Name * <form:errors path="propName"
					cssClass="alert alert-error" />
			</form:label>
			<form:input path="propName" />
			<form:label path="propValue">
                    Property Value * <form:errors path="propValue"
					cssClass="alert-error" />
			</form:label>
			<form:input path="propValue" />
		</fieldset>
		<br />
		<span class="label label-inverse">Fields marked with * are
			mandatory</span>
		<br />
		<br />
		<button class="btn btn-info" type="submit">Add</button>
           	&nbsp;&nbsp;&nbsp;&nbsp;
           	<a class="btn btn-danger" href="${cancelUrl}">Cancel </a>
	</form:form>

</body>
</html>
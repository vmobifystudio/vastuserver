<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - PickListItem</title>
</head>
<c:url value="/pickListItem/add" var="pickListItemAdd" />
<c:url value="/pickListItem/list" var="pickListItemList" />

<body>

	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<form:form id="addPickListItem" method="post"
		modelAttribute="pickListItem" action="${pickListItemAdd}">
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
			<legend>PickListItem Details - ${pickListItem.displayValue}</legend>

			<form:label path="displayValue">
		                   Name* <form:errors path="displayValue"
					cssClass="alert-error" />
			</form:label>
			<form:input path="displayValue" readonly="true" />
			<form:label path="listType">
		                   List Type* <form:errors path="listType"
					cssClass="alert-error" />
			</form:label>
			<select name="listType" disabled="disabled">
				<c:forEach items="${listTypes}" var="status">
					<option value="${status}"
						<c:if test="${(status==pickListItem.listType)}"> selected="selected"</c:if>>${status}</option>
				</c:forEach>
			</select>
		</fieldset>
		<br />
		<br />
	</form:form>
	<a class="btn btn-danger" href="javascript:history.back(1)">Ok</a>
</body>
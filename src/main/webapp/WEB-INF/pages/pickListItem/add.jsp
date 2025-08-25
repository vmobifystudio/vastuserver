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
	<%--@ include file="nav.jsp"--%>
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
			<legend>PickListItem</legend>
			<fieldset>
				<form:label path="displayValue">
		                   Name* <form:errors path="displayValue"
						cssClass="alert-error" />
				</form:label>
				<form:input path="displayValue" />
				<form:label path="listType">
		                   List Type* <form:errors path="listType"
						cssClass="alert-error" />
				</form:label>
				<select name="listType">
					<c:forEach items="${listTypes}" var="status">
						<option value="${status}">${status}</option>
					</c:forEach>
				</select>
			</fieldset>
		</fieldset>
		<span class="label label-inverse">Fields marked with * are
			mandatory</span>
		<br>
		<br>
		<button class="btn btn-info" type="submit">Add</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a class="btn btn-danger" href="javascript:history.back(1)">Cancel</a>
	</form:form>
</body>
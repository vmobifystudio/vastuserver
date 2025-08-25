<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - Update PickListItem</title>

</head>

<c:url value="/pickListItem/update" var="pickListItemUpdate" />
<c:url value="/pickListItem/list" var="pickListItemList" />

<body>

	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<form:form id="updatePickListItem" method="post"
		modelAttribute="pickListItem" action="${pickListItemUpdate}">
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
					<option value="${status}"
						<c:if test="${(status==pickListItem.listType)}"> selected="selected"</c:if>>${status}</option>
				</c:forEach>
			</select>
		</fieldset>
		<c:choose>
			<c:when test="${readOnly}">
				<a class="btn btn-info"
					href="<c:url value="javascript:history.back(1)"/>">Ok </a>
			</c:when>
			<c:otherwise>
				<button class="btn btn-info" type="submit">Update</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-danger" href="javascript:history.back(1)">Cancel
				</a>
			</c:otherwise>
		</c:choose>
	</form:form>

</body>
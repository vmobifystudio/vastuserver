<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>MySkool - Update System Property</title>

</head>

<c:url value="/systemproperty/update" var="propertyUpdate" />
<c:url value="/systemproperty/list" var="propertyList" />

<body>

	<%--@ include file="nav.jsp"--%>


	<form:form id="updateProperty" method="post" modelAttribute="property"
		action="${propertyUpdate}">
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
			<legend>System Property</legend>
			<form:label path="propName">
                    Property Name <form:errors path="propName"
					cssClass="alert-error" />
			</form:label>
			<form:input readOnly="true" path="propName" />
			<form:label path="propValue">
                    Property Message <form:errors path="propValue"
					cssClass="alert-error" />
			</form:label>
			<form:input readOnly="${readOnly}" path="propValue" />
		</fieldset>
		<c:choose>
			<c:when test="${readOnly}">
				<a class="btn btn-info" href="<c:url value="/systemproperty/list"/>">Ok
				</a>
			</c:when>
			<c:otherwise>
				<button class="btn btn-info" type="submit">Update</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-danger" href="<c:url value="/systemproperty/list"/>">Cancel
				</a>
			</c:otherwise>
		</c:choose>

		<input type="hidden" name="validateForUpdate" value="true" />
	</form:form>

</body>
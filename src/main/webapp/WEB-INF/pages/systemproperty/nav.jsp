<%@ include file="/taglibs.jsp"%>
<c:url value="/systemproperty/add" var="propertyAdd" />
<c:url value="/systemproperty/list" var="propertyList" />
<%-- <c:url value="/status/import" var="cityUpload" /> --%>
<body>

<div>
<a class="btn btn-primary" href="${propertyAdd}">Add New Property</a>&nbsp;
<%-- <a class="btn btn-primary" href="${cityUpload}">Import Cities</a> --%>
</div>
<br />

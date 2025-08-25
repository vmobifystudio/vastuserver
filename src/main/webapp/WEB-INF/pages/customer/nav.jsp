<%@ include file="/taglibs.jsp"%>
<c:url value="/customer/add" var="customerAdd" />
<c:url value="/customer/exportToExcel" var="customerExport" />
<c:url value="/customer/list" var="customerList" />
<c:url value="/customer/upload" var="customerUpload" />
<c:url value="/customer/generateReport" var="reportUrl"/>
<body>

<div class="row">
	<div class="span4">
	<form class="form-inline pull-right" action="${urlBase}" action="get" role="form">
	  <input type="text" class="form-control width-xlarge" name="searchTerm" id="searchTerm" placeholder="search">
	  <button type="submit" class="btn btn-success">Search</button>
	  <a class="btn btn-info" href="${urlBase}">View All</a>
	</form>
	</div>
	
</div>
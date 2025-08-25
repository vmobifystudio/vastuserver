<%@ include file="/taglibs.jsp"%>
<c:url value="/tip/add" var="tipAdd" />
<c:url value="/tip/list" var="tipList" />
<c:url value="/tip/import" var="tipUpload" />
<body>

<div class="row">
	<div class="span4">
	<a class="btn btn-primary" href="${tipAdd}">Add New Tip</a>&nbsp;
	</div>
	
	<form class="form-inline pull-right" action="${urlBase}" action="get" role="form">
	  <input type="text" class="form-control width-xlarge" name="searchTerm" id="searchTerm" placeholder="search">
	  <button type="submit" class="btn btn-success">Search</button>
	  <a class="btn btn-info" href="${urlBase}">View All</a>
	</form>
</div>

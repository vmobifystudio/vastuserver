<%@ include file="/taglibs.jsp"%>
<c:url value="/product/add" var="productAdd" />
<c:url value="/product/list" var="productList" />
<c:url value="/product/import" var="productUpload" />
<body>

<div class="row">
	<div class="span4">
	<a class="btn btn-primary" href="${productAdd}">Add New Product</a>&nbsp;
	</div>
	
	<form class="form-inline pull-right" action="${urlBase}" action="get" role="form">
	  <input type="text" class="form-control width-xlarge" name="searchTerm" id="searchTerm" placeholder="search">
	  <button type="submit" class="btn btn-success">Search</button>
	  <a class="btn btn-info" href="${urlBase}">View All</a>
	</form>
</div>

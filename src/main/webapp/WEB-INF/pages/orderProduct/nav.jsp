<%@ include file="/taglibs.jsp"%>
<c:url value="/orderProduct/add" var="orderProductAdd" />
<c:url value="/orderProduct/list" var="orderProductList" />
<c:url value="/orderProduct/import" var="orderProductUpload" />
<body>

<div class="row">
	<div class="span4">
	<a class="btn btn-primary" href="${orderProductAdd}">Add New Order</a>&nbsp;
	</div>
	
	<form class="form-inline pull-right" action="${urlBase}" action="get" role="form">
	  <input type="text" class="form-control width-xlarge" name="searchTerm" id="searchTerm" placeholder="search">
	  <button type="submit" class="btn btn-success">Search</button>
	  <a class="btn btn-info" href="${urlBase}">View All</a>
	</form>
</div>

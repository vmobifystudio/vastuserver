<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vastu - product Details</title>
</head>
<body>
	<h2>Product - Details</h2>
	<br>
	<div class="panel panel-primary" id="primary-info">
		<div class="panel-heading">
			<h3 class="panel-title">Primary Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Code</label>
				<p class="col-md-4">${product.code}</p>
				<label class="col-md-2">Product Name</label>
				<p class="col-md-2">${product.productName}</p>
			</div>
			<div class="row">	
				<label class="col-md-2">Price</label>
				<p class="col-md-4">${product.price}</p>
				<label class="col-md-2">Related To</label>
				<p class="col-md-4">
					<c:forEach items="${relatedTo}" var="room" varStatus="status">
						${room}
						<c:if test="${not status.last}">,</c:if>
					</c:forEach>
				</p>
				<label class="col-md-2">Description</label>
				<p class="col-md-10">${product.description}</p>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="base-info">
		<div class="panel-heading">
			<h3 class="panel-title">Base Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Created By</label>
				<p class="col-md-4">${product.createdBy}</p>
				<label class="col-md-2">Created Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${product.createdDate}" pattern="dd-MM-yyyy"/>
				</p>
				
			</div>
			<div class="row">
				<label class="col-md-2">Last Modified By</label>
				<p class="col-md-4">${product.lastModifiedBy}</p>
				<label class="col-md-2">Last Modified Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${product.modifiedDate}" pattern="dd-MM-yyyy"/>
				</p>
			</div>
		</div>
	</div>
	<a class="btn btn-success" href="javascript:history.back(1)">Ok</a>
</body>
</html>
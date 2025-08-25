<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>vastu - Customer</title>
</head>
<body>
	<h2>${customer.name} - Details</h2>
	<br>
	<div class="panel panel-primary" id="primary-info">
		<div class="panel-heading">
			<h3 class="panel-title">Primary Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Code</label>
				<p class="col-md-2">${customer.code}</p>
				<label class="col-md-2">Name</label>
				<p class="col-md-2">${customer.name}</p>
				<label class="col-md-2">Is SystemCustomer</label>
				<p class="col-md-2">
				<c:choose>
					<c:when test="${customer.isSystemCustomer}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
				</c:choose></p>
			</div>
			<div class="row">
				<label class="col-md-2">Loyalty Points Count</label>
				<p class="col-md-2">${customer.loyaltyPointsCount}</p>
				<label class="col-md-2">Latitude</label>
				<p class="col-md-2">${customer.lat}</p>
				<label class="col-md-2">Longitude</label>
				<p class="col-md-2">${customer.lng}</p>
			</div>
			<div class="row">
				<label class="col-md-2">Subscribed Store Owners</label>
				<p class="col-md-10">
					<c:set value="0" var="i"/>
					<c:forEach items="${customer.subscribedStoreOwners}" var="storeOwner">
						<c:choose>
							<c:when test="${i == 0 }">
								${storeOwner.name}
								<c:set value="1" var="i"/>
							</c:when>
							<c:otherwise>
								, ${storeOwner.name}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</p>
			</div>
		</div>
	</div>
	<div class="panel panel-success" id="contact-info">
		<div class="panel-heading">
			<h3 class="panel-title">Contact Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Email</label>
				<p class="col-md-2">${customer.email}</p>
				<label class="col-md-2">Mobile Number</label>
				<p class="col-md-2">${customer.mobileNumber}</p>
			</div>
			<div class="row">
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
				<p class="col-md-4">${customer.createdBy}</p>
				<label class="col-md-2">Created Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${customer.createdDate}" pattern="dd-MM-yyyy"/>
				</p>
				<label class="col-md-2">Is Enabled</label>
				<p class="col-md-2">
					<c:choose>
						<c:when test="${customer.isEnabled}">Yes</c:when>
						<c:otherwise>No</c:otherwise>
					</c:choose>
				</p>
			</div>
			<div class="row">
				<label class="col-md-2">Last Modified By</label>
				<p class="col-md-4">${customer.lastModifiedBy}</p>
				<label class="col-md-2">Last Modified Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${customer.modifiedDate}" pattern="dd-MM-yyyy"/>
				</p>
			</div>
			<br>
		</div>
	</div>
	
	<a class="btn btn-success" href="javascript:history.back(1)">Ok</a>
	
</body>
</html>
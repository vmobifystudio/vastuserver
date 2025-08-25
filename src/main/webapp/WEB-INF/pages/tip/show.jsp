<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ConChact - tip Details</title>
</head>
<body>
	<h2>tip - Details</h2>
	<br>
	<div class="panel panel-primary" id="primary-info">
		<div class="panel-heading">
			<h3 class="panel-title">Primary Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Code</label>
				<p class="col-md-4">${tip.code}</p>
				<label class="col-md-2">Tip Title</label>
				<p class="col-md-2">${tip.tipTitle}</p>
			</div>
			<div class="row">	
				<label class="col-md-2">Tip Description</label>
				<p class="col-md-4">${tip.tipDescription}</p>
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
				<p class="col-md-4">${tip.createdBy}</p>
				<label class="col-md-2">Created Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${tip.createdDate}" pattern="dd-MM-yyyy"/>
				</p>
				
			</div>
			<div class="row">
				<label class="col-md-2">Last Modified By</label>
				<p class="col-md-4">${tip.lastModifiedBy}</p>
				<label class="col-md-2">Last Modified Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${tip.modifiedDate}" pattern="dd-MM-yyyy"/>
				</p>
			</div>
		</div>
	</div>
	<a class="btn btn-success" href="javascript:history.back(1)">Ok</a>
</body>
</html>
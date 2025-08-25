<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ConChact - orderProduct Details</title>
</head>
<body>
	<h2>Order - Details</h2>
	<br>
	<div class="panel panel-primary" id="primary-info">
		<div class="panel-heading">
			<h3 class="panel-title">Primary Info</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<div class="row">
				<label class="col-md-2">Code</label>
				<p class="col-md-4">${orderProduct.code}</p>
				<label class="col-md-2">Person Name</label>
				<p class="col-md-2">${orderProduct.name}</p>
			</div>
			<div class="row">	
				<label class="col-md-2">Address</label>
				<p class="col-md-4">${orderProduct.address}</p>
				<label class="col-md-2">Pin</label>
				<p class="col-md-2">${orderProduct.pin}</p>
			</div>
			<div class="row">	
				<label class="col-md-2">Mobile Number</label>
				<p class="col-md-4">${orderProduct.mobileNumber}</p>
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
				<p class="col-md-4">${orderProduct.createdBy}</p>
				<label class="col-md-2">Created Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${orderProduct.createdDate}" pattern="dd-MM-yyyy"/>
				</p>
				
			</div>
			<div class="row">
				<label class="col-md-2">Last Modified By</label>
				<p class="col-md-4">${orderProduct.lastModifiedBy}</p>
				<label class="col-md-2">Last Modified Date</label>
				<p class="col-md-2">
					<fmt:formatDate value="${orderProduct.modifiedDate}" pattern="dd-MM-yyyy"/>
				</p>
			</div>
		</div>
	</div>

	<div class="panel panel-primary" id="base-info">
		<div class="panel-heading clearfix">
			<h3 class="panel-title">Product Line Item</h3>
		</div>
		<div class="panel-body" style="margin-left: 10px;">
			<form id="bulkSelect">
				<display:table name="lineItems" class="table" requestURI="" id="lineItem" export="false">
					<display:column title="Product Name">
						${lineItem.product.productName}

					</display:column>
					<display:column title="Product Count">
						${lineItem.count}

					</display:column>
				</display:table>
			</form>
		</div>
	</div>

	<a class="btn btn-success" href="javascript:history.back(1)">Ok</a>
</body>
</html>
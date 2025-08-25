<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vastu- orderProducts</title>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<h1>Order</h1>
	<c:url var="urlBase" value="/orderProduct/list" />
	<%@ include file="nav.jsp"%>
	<c:set var="urlPrefix" value="/orderProduct/list?searchTerm=${searchTerm}&pageNumber=" scope="page" />
	<%@ include file="../include/pagination.jsp" %>
	<c:url value="/orderProduct/show" var="orderProductDetailLinkPrefix" />
	<c:url value="/orderProduct/update" var="orderProductUpdateLinkPrefix" />
	<c:set value="1" var="count"/>
	<form id="searchForm">
		<div class="table-responsive">
			<display:table class="table table-hover" name="orderProducts" id="orderProduct">
				<display:column title="#" >
					${count} <c:set value="${count + 1}"  var="count"/>
				</display:column>
				<display:column title="Code" >
					<a href="${orderProductDetailLinkPrefix}/${orderProduct.cipher}">${orderProduct.code}</a>
				</display:column>
				<display:column title="Person Name" property="name" />
				<display:column title="Address" property="address" />
				<display:column title="Pin" property="pin" />
				<display:column title="Mobile Number" property="mobileNumber" />
    			<display:column title="Actions" sortable="false">
    				<div class="btn-group">
    					<button type="button" class="btn btn-info">Action</button>
    					<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
						    <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:if test="${orderProduct.isEditAllowed}">
								<li><a href="${orderProductUpdateLinkPrefix}/${orderProduct.cipher}">Edit</a></li>
							</c:if>
							<c:if test="${orderProduct.isDeleteAllowed}">
								<li><a href="#ConfirmModal_${orderProduct.id}" data-toggle="modal"/>Delete</a></li>
							</c:if>
						</ul>
    				</div>
    				<!-- modal -->
					<div class="modal" id="ConfirmModal_${orderProduct.id}" >
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" >×</button>
									<h3 id="myModalLabel">Delete orderProduct?</h3>
								</div>
								<div class="modal-body">
									<p>The orderProduct will be permanently deleted. This action cannot be undone.</p>
									<p>Are you sure?</p>
								</div>
								<div class="modal-footer">
									<a class="btn btn-primary" type="button" href="<c:url value="/orderProduct/delete/${orderProduct.cipher}"/>">Delete</a>
									<button class="btn" data-dismiss="modal" >Cancel</button>
								</div>
							</div>
						</div>
					</div>
    			</display:column>
			</display:table>
		</div>
	</form>
</body>
</html>
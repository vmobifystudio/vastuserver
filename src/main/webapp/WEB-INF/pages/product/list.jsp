<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vastu- Products</title>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<h1>Products</h1>
	<c:url var="urlBase" value="/product/list" />
	<%@ include file="nav.jsp"%>
	<c:set var="urlPrefix" value="/product/list?searchTerm=${searchTerm}&pageNumber=" scope="page" />
	<%@ include file="../include/pagination.jsp" %>
	<c:url value="/product/show" var="productDetailLinkPrefix" />
	<c:url value="/product/update" var="productUpdateLinkPrefix" />
	<c:set value="1" var="count"/>
	<form id="searchForm">
		<div class="table-responsive">
			<display:table class="table table-hover" name="products" id="product">
				<display:column title="#" >
					${count} <c:set value="${count + 1}"  var="count"/>
				</display:column>
				<display:column title="Code" >
					<a href="${productDetailLinkPrefix}/${product.cipher}">${product.code}</a>
				</display:column>
				<display:column title="Product Name" property="productName" />
				<display:column title="Price" property="price" />
				
				<display:column title="Kitchen">
					<c:choose>
					<c:when test="${product.isKitchenroomScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Bathroom">
					<c:choose>
					<c:when test="${product.isBathRoomScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Bedroom">
					<c:choose>
					<c:when test="${product.isBedroomScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Hall">
					<c:choose>
					<c:when test="${product.isHallScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Gallery">
					<c:choose>
					<c:when test="${product.isGalleryScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Directioncut">
					<c:choose>
					<c:when test="${product.isDirectioncutScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Entrance">
					<c:choose>
					<c:when test="${product.isEnterenceScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				
				<display:column title="Window">
					<c:choose>
					<c:when test="${product.isWindowScore}">Yes</c:when>
					<c:otherwise>No</c:otherwise>
					</c:choose>				
				</display:column>
				    			
    			<display:column title="Actions" sortable="false">
    				<div class="btn-group">
    					<button type="button" class="btn btn-info">Action</button>
    					<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
						    <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:if test="${product.isEditAllowed}">
								<li><a href="${productUpdateLinkPrefix}/${product.cipher}">Edit</a></li>
							</c:if>
							<c:if test="${product.isDeleteAllowed}">
								<li><a href="#ConfirmModal_${product.id}" data-toggle="modal"/>Delete</a></li>
							</c:if>
						</ul>
    				</div>
    				<!-- modal -->
					<div class="modal" id="ConfirmModal_${product.id}" >
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" >×</button>
									<h3 id="myModalLabel">Delete product?</h3>
								</div>
								<div class="modal-body">
									<p>The product will be permanently deleted. This action cannot be undone.</p>
									<p>Are you sure?</p>
								</div>
								<div class="modal-footer">
									<a class="btn btn-primary" type="button" href="<c:url value="/product/delete/${product.cipher}"/>">Delete</a>
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
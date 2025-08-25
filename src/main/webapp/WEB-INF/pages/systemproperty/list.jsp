<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MySkool - System Property</title>
</head>
<body>

	<h1>System Property</h1>
	<br />
	<%@ include file="nav.jsp"%>

	<c:set var="urlPrefix" value="/systemproperty/list?pageNumber=" scope="page" />
	<%@ include file="../include/pagination.jsp" %>

	<c:url value="/show/systemproperty" var="detailLinkPrefix" />

	<display:table name="properties"
		class="table table-striped table-condensed" id="property" export="false">
				
		<display:column title="Property Name" sortable="false">
			<a href="${detailLinkPrefix}/${property.id}?readOnly=true">${property.propName}</a>
		</display:column>			
			
		<display:column title="Value" property="propValue" sortable="false" />
		<display:column title="" sortable="false">
			<div class="btn-group">
				<a class="btn btn-info dropdown-toggle" data-toggle="dropdown"
					href="#"> Action <span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<!-- dropdown menu links -->
					<li><a href="<c:url value="/systemproperty/${property.id}"/>"> Edit</a></li>
					<li><a href="#ConfirmModal_${property.id}" data-toggle="modal" />
						Delete</a></li>
				</ul>
			</div>

			<!-- modal -->
			<div class="modal hide" id="ConfirmModal_${property.id}" tabindex="-1">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 id="myModalLabel">Delete Property?</h3>
				</div>
				<div class="modal-body">
					<p>The property will be permanently deleted. This action cannot
						be undone.</p>
					<p>Are you sure?</p>
				</div>
				<div class="modal-footer">
					<a class="btn btn-primary" type="button"
						href="<c:url value="/systemproperty/delete/${property.id}"/>">Delete</a>
					<button class="btn" data-dismiss="modal">Cancel</button>
				</div>
			</div>

			<%--         <a class="btn btn-info" href="<c:url value="/city/${city.id}"/>">Update</a> --%>
			<%--         <a class="btn btn-danger" href="<c:url value="/city/delete/${city.id}"/>">Delete</a> --%>
		</display:column>
	</display:table>

</body>
</html>
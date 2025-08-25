<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vastu- Tips</title>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<h1>Tips</h1>
	<c:url var="urlBase" value="/tip/list" />
	<%@ include file="nav.jsp"%>
	<c:set var="urlPrefix" value="/tip/list?searchTerm=${searchTerm}&pageNumber=" scope="page" />
	<%@ include file="../include/pagination.jsp" %>
	<c:url value="/tip/show" var="tipDetailLinkPrefix" />
	<c:url value="/tip/update" var="tipUpdateLinkPrefix" />
	<c:set value="1" var="count"/>
	<form id="searchForm">
		<div class="table-responsive">
			<display:table class="table table-hover" name="tips" id="tip">
				<display:column title="#" >
					${count} <c:set value="${count + 1}"  var="count"/>
				</display:column>
				<display:column title="Code" >
					<a href="${tipDetailLinkPrefix}/${tip.cipher}">${tip.code}</a>
				</display:column>
				<display:column title="Tip Title" property="tipTitle" />
				<display:column title="Tip Description" property="tipDescription" />
    			<display:column title="Actions" sortable="false">
    				<div class="btn-group">
    					<button type="button" class="btn btn-info">Action</button>
    					<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
						    <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:if test="${tip.isEditAllowed}">
								<li><a href="${tipUpdateLinkPrefix}/${tip.cipher}">Edit</a></li>
							</c:if>
							<c:if test="${tip.isDeleteAllowed}">
								<li><a href="#ConfirmModal_${tip.id}" data-toggle="modal"/>Delete</a></li>
							</c:if>
						</ul>
    				</div>
    				<!-- modal -->
					<div class="modal" id="ConfirmModal_${tip.id}" >
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" >×</button>
									<h3 id="myModalLabel">Delete tip?</h3>
								</div>
								<div class="modal-body">
									<p>The tip will be permanently deleted. This action cannot be undone.</p>
									<p>Are you sure?</p>
								</div>
								<div class="modal-footer">
									<a class="btn btn-primary" type="button" href="<c:url value="/tip/delete/${tip.cipher}"/>">Delete</a>
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
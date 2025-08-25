<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - Customer</title>
<script type="text/javascript">

function selectAll() {
	$('.checkbox').attr("checked", true);
}

function unselectAll() {
	$('.checkbox').removeAttr("checked");
}

function bulkDelete () {
	$('#bulkSelect').attr("action", "<c:url value="/customer/multiDelete"/>");
	$('#bulkSelect').submit();
}

$(document).ready(function() {
	$('#bulkActionMenu').hide();
	$('#selectall').click(function() {
		if (this.checked) {
			selectAll();
			$('#bulkActionMenu').fadeIn();			
		}
		else {
			unselectAll();
			$('#bulkActionMenu').fadeOut();			
		}
	});
	
	$('.checkbox').click(function() {
		if ($('input.checkbox[type="checkbox"]:checked').val()) {		
			$('#bulkActionMenu').fadeIn();
		}
		else {
			$('#bulkActionMenu').fadeOut();			
		}
	});
});

</script>
</head>


<body>
<sec:authorize access="isAnonymous()">
    <c:redirect url="/login"/>
</sec:authorize>
<h1>Customers</h1>
<br/>

<c:url var="urlBase" value="/customer/list" />
<c:url var="approveCustomer" value="/customer/approve" />
<%@ include file="nav.jsp"%>
<c:set var="urlPrefix" value="/customer/list?searchTerm=${searchTerm}&pageNumber=" scope="page" />

<%@ include file="../include/pagination.jsp" %>
<div class="btn-group" id="bulkActionMenu">
	<a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown"
		href="#"> Bulk Action <span class="caret"></span>
	</a>
	<ul class="dropdown-menu">
		<li><a href="#ConfirmMultiDelete" data-toggle="modal" >
			Delete</a></li>
	</ul>
</div>

<c:url value="/customer/show" var="customerDetailLinkPrefix" />

<form id="bulkSelect">
	<display:table name="customers" class="table" requestURI=""
	    id="customer" export="false">
	    <display:column  title="<input type='checkbox' id='selectall' />">
	        <input type="checkbox" class="checkbox" name="selectedIds" value="${customer.id}" />
	    </display:column>
	    <display:column title="Code" sortable="true">
	        <a href="${customerDetailLinkPrefix}/${customer.cipher}">${customer.code}</a>
	    </display:column>
	        
	    <display:column title="Name" property="name" sortable="true"/>
	    <display:column title="Email" property="email" sortable="true" />
		<display:column title="Is System Customer" sortable="true">
			<c:choose>
				<c:when test="${customer.isSystemCustomer}">Yes</c:when>
				<c:otherwise>No</c:otherwise>				
			</c:choose>
		</display:column>
		<display:column title="Loyalty Points" property="loyaltyPointsCount" sortable="true"/>	    
	    <display:column title="Actions" sortable="false">
			
			<div class="btn-group">
		 			<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
			    Action
			    <span class="caret"></span>
			  	</a>
				<ul class="dropdown-menu">
				</ul>
			</div>	
				
			<!-- modal -->
			<div class="modal" id="ConfirmModal_${customer.id}" >
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >×</button>
							<h3 id="myModalLabel">Delete Customer?</h3>
						</div>
						<div class="modal-body">
							<p>The customer will be permanently deleted. This action cannot be undone.</p>
							<p>Are you sure?</p>
						</div>
						<div class="modal-footer">
							<a class="btn btn-primary" type="button" href="<c:url value="/customer/delete/${customer.cipher}"/>">Delete</a>
			    			<button class="btn" data-dismiss="modal" >Cancel</button>
						</div>
					</div>
				</div>
			</div>
	    </display:column>
	</display:table>
</form>

<!-- modal -->
<div class="modal hide" id="ConfirmMultiDelete" tabindex="-1" >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" >×</button>
    <h3 id="myModalLabel">Delete Customers?</h3>
  </div>
  <div class="modal-body">
    <p>The selected customers will be permanently deleted. This action cannot be undone.</p>
	<p>Are you sure?</p>
  </div>
  <div class="modal-footer">
    <a class="btn btn-primary" type="button" href="javascript:bulkDelete()">Yes, Delete</a>
    <button class="btn" data-dismiss="modal" >No, Cancel</button>
  </div>
</div>

</body>
</html>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>Travel System - User</title>
<script type="text/javascript">
function openLink(url) {
    var args = new Object;
    args.window = window;
    var thePopup = window.open( url, "User Report", "menubar=0,location=0,height=700,width=700",window);
    //thePopup.print();
};

function selectAll() {
	$('.checkbox').attr("checked", true);
}

function unselectAll() {
	$('.checkbox').removeAttr("checked");
}

function bulkDelete () {
	$('#bulkSelect').attr("action", "<c:url value="/user/multiDelete"/>");
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
//		if ($('input[name="selectedIds"]').val() != [] ) {
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
<h1>Users</h1>
<br/>

<c:url var="urlBase" value="/user/list" />
<%@ include file="nav.jsp"%>
<c:set var="urlPrefix" value="/user/list?searchTerm=${searchTerm}&pageNumber=" scope="page" />

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

	<c:url value="/show/user" var="userDetailLinkPrefix" />

	<form id="bulkSelect">
<display:table name="users" class="table" requestURI=""
    id="user" export="false">
    <display:column  title="<input type='checkbox' id='selectall' />">
        <input type="checkbox" class="checkbox" name="selectedIds" value="${user.id}" />
    </display:column>
    <display:column title="Email"
        sortable="true" >
        <a href="${userDetailLinkPrefix}/${user.id}?readOnly=true">${user.email}</a>
        </display:column>
        
    <display:column title="First Name" property="firstName"
        sortable="true"  />
        
    <display:column title="Last Name" property="lastName"
        sortable="true" />
    <display:column title="Email" property="email"
        sortable="true" />
    
    <display:column title="Roles">
    	<c:forEach items="${user.userRoles}" var="role" varStatus="status">
        	${role.description}<c:if test="${not status.last}">, </c:if>
     	</c:forEach>    
    </display:column>
    
    <display:column title="Actions" sortable="false">
		
	<div class="btn-group">
  			<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
		    Action
		    <span class="caret"></span>
		  	</a>
			<ul class="dropdown-menu">
			    <!-- dropdown menu links -->
			    <li><a href="<c:url value="/user/${user.id}"/>"> Edit</a></li>
	        		<sec:authorize url="/user/delete/${user.id}">
				    	<li><a href="#ConfirmModal_${user.id}" data-toggle="modal"/> Delete</a></li>
				    </sec:authorize>
			</ul>
		</div>	
		
		<!-- modal -->
		<div class="modal hide" id="ConfirmModal_${user.id}" tabindex="-1" >
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" >×</button>
		    <h3 id="myModalLabel">Delete User?</h3>
		  </div>
		  <div class="modal-body">
		    <p>The user will be permanently deleted. This action cannot be undone.</p>
			<p>Are you sure?</p>
		  </div>
		  <div class="modal-footer">
		    <a class="btn btn-primary" type="button" href="<c:url value="/user/delete/${user.id}"/>">Delete</a>
		    <button class="btn" data-dismiss="modal" >Cancel</button>
		  </div>
		</div>
		
		
<!--		<a class="btn btn-info" href="<c:url value="/user/${user.id}"/>">Update</a>
		<sec:authorize url="/user/delete/${user.id}">
	        <a class="btn btn-danger" href="#ConfirmModal" data-toggle="modal"> Delete </a>
        </sec:authorize>
-->    
    </display:column>
</display:table>
</form>

<!-- modal -->
<div class="modal hide" id="ConfirmMultiDelete" tabindex="-1" >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" >×</button>
    <h3 id="myModalLabel">Delete Users?</h3>
  </div>
  <div class="modal-body">
    <p>The selected users will be permanently deleted. This action cannot be undone.</p>
	<p>Are you sure?</p>
  </div>
  <div class="modal-footer">
    <a class="btn btn-primary" type="button" href="javascript:bulkDelete()">Yes, Delete</a>
    <button class="btn" data-dismiss="modal" >No, Cancel</button>
  </div>
</div>

<!-- <div id="dialog-form" class="modal hide">
</div> -->

</body>
</html>
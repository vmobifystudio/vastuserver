<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - PickListItem</title>
<script type="text/javascript">
	function selectAll() {
		$('.checkbox').attr("checked", true);
	}

	function unselectAll() {
		$('.checkbox').removeAttr("checked");
	}

	function bulkDelete() {
		$('#bulkSelect').attr("action",
				"<c:url value="/pickListItem/multiDelete"/>");
		$('#bulkSelect').submit();
	}

	$(document).ready(function() {
		$('#bulkActionMenu').hide();
		$('#selectall').click(function() {
			if (this.checked) {
				selectAll();
				$('#bulkActionMenu').fadeIn();
			} else {
				unselectAll();
				$('#bulkActionMenu').fadeOut();
			}
		});

		$('.checkbox').click(function() {
			//		if ($('input[name="selectedIds"]').val() != [] ) {
			if ($('input.checkbox[type="checkbox"]:checked').val()) {
				$('#bulkActionMenu').fadeIn();
			} else {
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
	<h1>PickListItems</h1>
	<br />

	<c:url var="urlBase" value="/pickListItem/list" />
	<%@ include file="nav.jsp"%>
	<c:set var="urlPrefix"
		value="/pickListItem/list?searchTerm=${searchTerm}&pageNumber="
		scope="page" />

	See below the list of PickListItems currently listed in your vastu
	account -
	<%@ include file="../include/pagination.jsp"%>

	<div class="btn-group" id="bulkActionMenu">
		<a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown"
			href="#"> Bulk Action <span class="caret"></span>
		</a>
		<ul class="dropdown-menu">
			<li><a href="#ConfirmMultiDelete" data-toggle="modal">
					Delete</a></li>
		</ul>
	</div>

	<c:url value="/show/pickListItem" var="pickListItemDetailLinkPrefix" />

	<form id="bulkSelect">
		<display:table name="pickListItems"
			class="table table-striped table-condensed" requestURI=""
			id="pickListItem" export="false">
			<display:column title="<input type='checkbox' id='selectall' />">
				<input type="checkbox" class="checkbox" name="selectedIds"
					value="${pickListItem.id}" />
			</display:column>
			<display:column title="PickListItem ID" sortable="true">
				<a
					href="${pickListItemDetailLinkPrefix}/${pickListItem.id}?readOnly=true">${pickListItem.itemValue}</a>
			</display:column>
			<display:column title="PickListItem Name" property="displayValue"
				sortable="true" />
			<display:column title="PickListItem Type" property="listType"
				sortable="true" />
			<display:column title="" sortable="false">
				<!-- dropdown menu links -->
				<a class="btn btn-primary" type="button"
					href="<c:url value="/pickListItem/${pickListItem.id}"/>"> Edit</a>&nbsp;
	    <a class="btn btn-danger" type="button"
					href="#ConfirmModal_${pickListItem.id}" data-toggle="modal" /> Delete</a>

				<!-- modal -->
				<div class="modal hide" id="ConfirmModal_${pickListItem.id}"
					tabindex="-1">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h3 id="myModalLabel">Delete PickListItem?</h3>
					</div>
					<div class="modal-body">
						<p>The pickListItem will be permanently deleted. This action
							cannot be undone.</p>
						<p>Are you sure?</p>
					</div>
					<div class="modal-footer">
						<a class="btn btn-primary" type="button"
							href="<c:url value="/pickListItem/delete/${pickListItem.id}"/>">Delete</a>
						<button class="btn" data-dismiss="modal">Cancel</button>
					</div>
				</div>
			</display:column>
		</display:table>
	</form>

	<!-- modal -->
	<div class="modal hide" id="ConfirmMultiDelete" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Delete PickListItems?</h3>
		</div>
		<div class="modal-body">
			<p>The selected pickListItems will be permanently deleted. This
				action cannot be undone.</p>
			<p>Are you sure?</p>
		</div>
		<div class="modal-footer">
			<a class="btn btn-primary" type="button"
				href="javascript:bulkDelete()">Yes, Delete</a>
			<button class="btn" data-dismiss="modal">No, Cancel</button>
		</div>
	</div>
</body>
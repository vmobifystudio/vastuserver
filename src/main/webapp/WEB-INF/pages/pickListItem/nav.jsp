<%@ include file="/taglibs.jsp"%>
<c:url value="/pickListItem/add" var="pickListItemAdd" />
<c:url value="/pickListItem/list" var="pickListItemList" />
<c:url value="/pickListItem/import" var="pickListItemUpload" />
<body>

<div class="row">
<div class="span8">
<a class="btn btn-primary" href="${pickListItemAdd}">Add New PickListItem</a>&nbsp;
<a class="btn btn-primary" href="${pickListItemUpload}">Import PickListItems</a>
</div>

<div class="span4">
<form class="form-search" action="${urlBase}" action="get">
  <input type="text" class="input-medium search-query" name="searchTerm" id="searchTerm">
  <button type="submit" class="btn">Search</button>
  <a class="btn" href="${urlBase}">View All</a>
</form>
</div>
</div>
<p></p>
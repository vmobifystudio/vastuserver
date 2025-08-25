<%@ include file="/taglibs.jsp"%>
<c:url value="/user/add" var="userAdd" />
<c:url value="/user/list" var="userList" />
<c:url value="/user/upload" var="userUpload" />
<c:url value="/user/generateReport" var="reportUrl"/>
<body>

<div class="row">
<div class="span8">
<a class="btn btn-primary" href="${userAdd}">Add New User</a>&nbsp;
<%-- <a class="btn btn-primary" href="${userUpload}">Import Users</a>&nbsp; --%>
<%-- <a class="btn btn-primary" href="#" onclick="openLink('${reportUrl}');">Generate Report</a> --%>
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
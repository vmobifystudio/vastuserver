<%@page import="org.apache.velocity.runtime.directive.Foreach"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>Home</title>
<!-- <script src="http://malsup.github.com/jquery.form.js"></script> -->
<script type="text/javascript">

</script>
</head>

<body>
	<%-- <%@ include file="/messages.jsp"%> --%>
	<sec:authorize access="isAuthenticated()">
		<c:url var="urlBase" value="/home" />
		<c:set var="urlPrefix"
			value="/home?searchTerm=${searchTerm}&pageNumber=" scope="page" />
		<div style="padding: 10px;
  					margin-bottom: 10px;
					font-size: 18px;
					font-weight: 100;
					line-height: 20px;
					vertical-align: middle;
					color: inherit;
					background-color: #eeeeee;
					-webkit-border-radius: 6px;
					-moz-border-radius: 6px;
					border-radius: 6px;">
			<h1 style="margin-bottom: 0;
  					   font-size: 30px;
  					   line-height: 1;
  					   letter-spacing: -1px;
  					   color: inherit;">Dashboard</h1>
			<br>
			<h2>
				Welcome, <span id="username"><%=((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName()%></span>
			</h2>
			<br/>
		</div>
		
	</sec:authorize>
</body>
</html>
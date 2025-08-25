<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title><decorator:title default="Welcome" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="<c:url value="/assets/css/bootswatch/bootswatch.min.css"/>" rel="stylesheet">
<link href="<c:url value="/assets/css/bootswatch/bootstrap.css"/>" rel="stylesheet">
<link href="<c:url value="/assets/css/customwidths.css"/>" rel="stylesheet">
<link href="<c:url value="/assets/css/datepicker.css"/>" rel="stylesheet">
<link href="<c:url value="/assets/css/font-awesome.css"/>" rel="stylesheet">
	
	

<script src="<c:url value="/assets/js/jquery.js"/>"></script>
<script src="<c:url value="/assets/js/bootswatch/bootstrap.min.js"/>"></script>
<script src="<c:url value="/assets/js/bootswatch/bootswatch.js"/>"></script>
<script src="<c:url value="/assets/js/bootswatch/jquery-1.10.2.min.js"/>"></script>
<script src="<c:url value="/assets/js/underscore-min.js"/>"></script>
<script src="<c:url value="/assets/js/bootstrap-datepicker.js"/>"></script>
<script type="text/javascript">
_.templateSettings = {
	    interpolate: /\<\@\=(.+?)\@\>/gim,
	    evaluate: /\<\@(.+?)\@\>/gim,
	    escape: /\<\@\-(.+?)\@\>/gim,
	    variable: "rc"
	};
</script>

<decorator:head />
</head>

<body>
	<c:url value="/home" var="homeUrl" />
	<c:url value="/login" var="loginUrl" />
	<c:url value="/logout" var="logoutUrl" />

	<c:url value="/tip/list" var="tipListUrl" />	
	<c:url value="/product/list" var="productListUrl" />
	<c:url value="/orderProduct/list" var="orderListUrl" />
	
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="${homeUrl}" class="navbar-brand">Vastu</a>
				<button class="navbar-toggle" type="button" data-toggle="collapse"
					data-target="#navbar-main">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse" id="navbar-main">
				<ul class="nav navbar-nav">
					<sec:authorize access="isAuthenticated()">
						<li class="dropdown">
							<a href="${tipListUrl}">Tips</a>
						</li>
						<li class="dropdown">
							<a href="${productListUrl}">Products</a>
						</li>
						<li class="dropdown">
							<a href="${orderListUrl}">Orders</a>
						</li>
					</sec:authorize>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						
						<sec:authorize access="isAnonymous()">
							<a class="dropdown-toggle" href="${loginUrl}"> <i class=".glyphicon-user"></i> Login</a>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<button class="btn btn-default navbar-btn dropdown-toggle" data-toggle="dropdown" href="#"> <i class=".glyphicon-user"></i> 
								<%=((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName()%>
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="${logoutUrl}">Sign Out</a></li>
								<li><a href="${changePasswordUrl}">Change Password</a></li>
							</ul>
						</sec:authorize>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="page-header" id="banner">
			<div class="row">
				<div id="page-wrapper">
					<%@ include file="/messages.jsp"%>
					<decorator:body />
					<br>
				</div>
			</div>
		</div>
	</div>
	<!-- </div> -->
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->


</body>
</html>

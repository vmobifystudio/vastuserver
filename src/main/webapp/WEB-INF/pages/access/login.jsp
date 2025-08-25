<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ include file="/taglibs.jsp"%>

<html>
<head>
	<title>Login</title>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
</head>

<body>
	<sec:authorize access="isAuthenticated()">
		<c:redirect url="/home"/>
	</sec:authorize>
	<!-- <div class="col-lg-8 col-md-7 col-sm-6">
      <p class="lead">Login Here</p>
    </div> -->
	<div class="col-lg-6">
            <div class="well bs-component">
				<form action="j_spring_security_check" class="form-horizontal" method="post" >
					<fieldset>
						<legend>Login</legend>
						<div class="form-group">
		                    <label for="username" class="col-lg-2 control-label">Username</label>
		                    <div class="col-lg-10">
		                      <input class="form-control" id="username" name="j_username" type="text" placeholder="Enter Username">
		                    </div>
		                 </div>
		                 <div class="form-group">
		                    <label for="password" class="col-lg-2 control-label">Password</label>
		                    <div class="col-lg-10">
		                      <input class="form-control" id="password" name="j_password" type="password" placeholder="***********">
		                    </div>
		                 </div>
						<div class="form-group">
			               <div class="col-lg-10 col-lg-offset-2">
			                 <input class="btn btn-primary" type="submit" value="Login"/>
			               </div>
			             </div>
					</fieldset>
					<a href="<c:url value="/access/forgotPassword" />">Recover my password</a>
					<a href="<c:url value="/register/storeOwner" />">Register Here!</a>
				</form>
			</div>
		</div>
</body>
</html>
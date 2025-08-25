<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>vastu - Add orderProduct</title>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<c:url value="/orderProduct/add" var="addorderProduct"/>
	<div class="col-lg-6">
           <div class="bs-component">
           		<form:form id="addorderProduct" action="${addorderProduct}" method="post" modelAttribute="orderProduct">
				<div>
					<c:if test="${not empty message}">
						<div id="message" class="success">${message}</div>
					</c:if>
					<s:bind path="*">
						<c:if test="${status.error}">
							<div id="message" class="text text-danger">Form has errors</div>
						</c:if>
					</s:bind>
				</div>
				<fieldset>
					<legend>Order</legend>
					<form:errors path="name" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Name*</span><form:input path="name" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="address" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Address*</span><form:input path="address" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="pin" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Pin*</span><form:input path="pin" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="mobileNumber" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Mobile Number*</span><form:input path="mobileNumber" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="lineItems" cssClass="text-danger" /> 
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Products*</span>
						<form:select path="lineItems" multiple="true" cssClass="form-control">
							<form:options items="${products}" itemValue="id" itemLabel="productName"/>
						</form:select>
					</div>
					<br>
				</fieldset>
				<span class="label label-warning">Fields marked with * are mandatory</span>
				<br>
				<br>
				<button class="btn btn-success" type="submit">Submit</button>
				<a class="btn btn-danger" href="javascript:history.back(1)">Cancel</a>
			</form:form>
		</div>
	</div>
</body>
</html>
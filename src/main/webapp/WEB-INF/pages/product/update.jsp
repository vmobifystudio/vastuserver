<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vastu - Update product</title>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</head>
<body>
	<sec:authorize access="isAnonymous()">
    	<c:redirect url="/login"/>
	</sec:authorize>
	<c:url value="/product/update" var="updateproduct"/>
	<form:form id="updateproduct" action="${updateproduct}" method="post" modelAttribute="product" enctype="multipart/form-data">
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
			<legend>Product</legend>
					<form:errors path="productName" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Product Name*</span><form:input path="productName" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="price" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Price*</span><form:input path="price" cssClass="form-control"/>
					</div>
					<br>
					<form:errors path="description" cssClass="text-danger" />
					<div class="input-group width-xlarge">
						<span class="input-group-addon">Description*</span><form:textarea path="description" cssClass="form-control" rows="5"></form:textarea>
					</div>
					<br>
		
					<label>Related To</label>
					<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isKitchenroomScore" <c:if test="${product.isKitchenroomScore}">checked="checked"</c:if>>
	              		Kitchen
	           			</label>
	        		</div>
	        		
					<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isBathRoomScore" <c:if test="${product.isBathRoomScore}">checked="checked"</c:if>>
	              		Bath Room
	           			</label>
	        		</div>
	        		
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isBedroomScore" <c:if test="${product.isBedroomScore}">checked="checked"</c:if>>
	              		Bed Room
	           			</label>
	        		</div>
	        		
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isHallScore" <c:if test="${product.isHallScore}">checked="checked"</c:if>>
	              		Hall
	           			</label>
	        		</div>
	        		
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isGalleryScore" <c:if test="${product.isGalleryScore}">checked="checked"</c:if>>
	              		Gallery
	           			</label>
	        		</div>
	        		
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isDirectioncutScore" <c:if test="${product.isDirectioncutScore}">checked="checked"</c:if>>
	              		Directioncut
	           			</label>
	        		</div>
	        		
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isEnterenceScore" <c:if test="${product.isEnterenceScore}">checked="checked"</c:if>>
	              		Enterence
	           			</label>
	        		</div>
	        		<div class="radio" >
	           			<label>
	              			<input type="checkbox" name="isWindowScore" <c:if test="${product.isWindowScore}">checked="checked"</c:if>>
	              		Window
	           			</label>
	        		</div>
					
					
		</fieldset>
		<span class="label label-warning">Fields marked with * are mandatory</span>
		<br>
		<br>
		<c:if test="${product.isEditAllowed}">
			<button class="btn btn-success" type="submit">Submit</button>
		</c:if>
		<a class="btn btn-danger" href="javascript:history.back(1)">Cancel</a>
	</form:form>
</body>
</html>
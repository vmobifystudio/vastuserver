<%@ include file="/taglibs.jsp"%>
<c:if test="${not empty message}">
	<div class="bs-component">
	    <button type="button" class="close" data-dismiss="alert">×</button>
	    <div class="alert alert-dismissable alert-info"><c:out value="${message}"/></div>
	    <c:if test="${not empty sessionScope.message}">
	    		<c:remove var="message"/>
	    </c:if>
	</div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-dismissable alert-danger"><c:out value="${errorMessage}"/></div>
    <button type="button" class="close" data-dismiss="alert">×</button>
    <c:if test="${not empty sessionScope.errorMessage}">
    		<c:remove var="errorMessage"/>
    </c:if>
</c:if>
<c:if test="${not empty extraMessages}">
    <div class="alert alert-dismissable alert-danger">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <c:forEach var="extraMessage" items="${extraMessages}">   		
   		<c:out value="${extraMessage}"/>
   		<br/>   		
    </c:forEach>
    </div>
    <c:if test="${not empty sessionScope.extraMessages}">
    		<c:remove var="extraMessages"/>
    </c:if>
</c:if>

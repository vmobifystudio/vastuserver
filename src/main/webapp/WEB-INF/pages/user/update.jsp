<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - Update User</title>
<script type="text/javascript">
$(document).ready(function(){

   
  });

</script>
</head>

<c:url value="/user/update" var="userUpdate" />
<c:url value="/user/list" var="userList" />
<body>
<sec:authorize access="isAnonymous()">
    <c:redirect url="/login"/>
</sec:authorize>
<%--@ include file="nav.jsp"--%>


<form:form id="updateUser" method="post" modelAttribute="user" action="${userUpdate}">      
  <div>
                <c:if test="${not empty message}">
                    <div id="message" class="success">${message}</div>  
                </c:if>
                <s:bind path="*">
                    <c:if test="${status.error}">
                        <div id="message" class="alert alert-error">Form has errors</div>
                    </c:if>
                </s:bind>
            </div>
            <fieldset>
                <legend>User</legend>
                <form:label path="firstName">
                    First Name <form:errors path="firstName" cssClass="alert-error" />
                </form:label>
                <form:input readOnly="${readOnly}" path="firstName" />
                <form:label path="lastName">
                    Last Name <form:errors path="lastName" cssClass="alert-error" />
                </form:label>
                <form:input readOnly="${readOnly}" path="lastName" />
                <c:if test="${readOnly!=true}">
	                <form:label path="password">
	                    Password <form:errors path="password" cssClass="alert-error" />
	                </form:label>
	                <form:password path="password" readOnly="${readOnly}"/>
                </c:if>
                <form:label path="email">
                    Email* <form:errors path="email" cssClass="alert-error" />
                </form:label>
                <form:input readOnly="${readOnly}" path="email" />
                <form:label path="mobileNumber">
                    Mobile Number* <form:errors path="mobileNumber" cssClass="alert-error" />
                </form:label>
                <form:input readOnly="${readOnly}" path="mobileNumber" />
                <form:label path="userRoles">
                    Choose Role <form:errors path="userRoles" cssClass="alert-error" />
                </form:label>
                <form:select path="userRoles" multiple="false" disabled="${readOnly}">
                    <form:options items="${roleList}" itemValue="id" itemLabel="role"/>
                </form:select>            
                <form:label path="isEnabled">
		                   Is Active* <form:errors path="isEnabled"
						cssClass="alert-error" />
				</form:label>
				<select name="isEnabled" id="isEnabled" <c:if test="${readOnly}">disabled="disabled"</c:if>>
					<option value="true" <c:if test="${bpoUser.isEnabled}">selected="selected"</c:if>>Yes</option>
					<option value="false" <c:if test="${not bpoUser.isEnabled}">selected="selected"</c:if>>No</option>
				</select>
            </fieldset>
            <c:choose>
             <c:when test="${readOnly}">
                    <a class="btn btn-info" href="<c:url value="javascript:history.back(1)"/>">Ok </a>                           
             </c:when>
             <c:otherwise>
                    <button class="btn btn-info" type="submit">Update</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn btn-danger" href="javascript:history.back(1)">Cancel </a>
             </c:otherwise>
            </c:choose>
        </form:form>

</body>
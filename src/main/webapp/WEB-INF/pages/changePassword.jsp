<%@ include file="/taglibs.jsp"%>
<html>
<head>
<title>vastu - Change Password</title>

<script type="text/javascript">

$(document).ready(function() {
	$('#submitButton').click(function() {
		if (validate()) {
			$('#changePassword').submit();
		}
	});	
});

function validate() {
	if (!$('#oldPassword').val()) {
		alert('Please enter your old password');
		return false;
	}
	if (!$('#newPassword').val()) {
		alert('Please enter your new password');
		return false;
	}
	if (!$('#newPasswordConfirm').val()) {
		alert('Please confirm your new password');
		return false;
	}
	if ($('#newPassword').val() != $('#newPasswordConfirm').val()) {
		alert('New password / Confirm password do not match');
		return false;		
	}
	
	return true;	
}

</script>

</head>

<c:url value="/access/changePassword" var="/access/changePassword" />

<body>

<sec:authorize access="isAnonymous()">
    <c:redirect url="/login"/>
</sec:authorize>
<form id="changePassword" method="post" action="${changePassword}">
            <div>
                <c:if test="${not empty message}">
                    <div id="message" class="success">${message}</div>  
                </c:if>
               
            </div>
            <fieldset>
            		<label for="oldPassword">
                Old Password
                </label>
                <input name="oldPassword" id="oldPassword" value="" type="password" />
            		<label for="newPassword">
                New Password
                </label>
                <input name="newPassword" id="newPassword" value="" type="password" />
            		<label for="newPasswordConfirm">
                Confirm New Password
                </label>
                <input name="newPasswordConfirm" id="newPasswordConfirm" value="" type="password" />

            </fieldset>
            <br>
            <a class="btn" id="submitButton">Change Password</a>
           	&nbsp;&nbsp;&nbsp;&nbsp;
           	<a class="btn btn-danger" href="javascript:history.back(1)">Cancel </a>

</body>
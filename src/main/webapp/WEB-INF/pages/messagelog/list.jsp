<%@ include file="/taglibs.jsp"%>

<html>
<head>
<title>MySkool - Message Log</title>
</head>
<body>
<h1>Sent Messages Log</h1>
<br/>
<%@ include file="nav.jsp"%>

<c:set var="urlPrefix" value="/messagelog/list?pageNumber=" scope="page" />
<%@ include file="../include/pagination.jsp" %>

<display:table name="messageLogs" class="table table-striped table-condensed" requestURI=""
    id="messageLog" export="false">
    <c:url value="/show/messageLog/${messageLog.id}" var="messageLogDisplay">
        <c:param name="readOnly" value="true"/> 
    </c:url>
    <display:column title="Timestamp" property="sendTimestamp"
        sortable="true" />
    <display:column title="User" property="email"
        sortable="true" />
    <display:column title="Mode" property="mode"
        sortable="true" />
    <display:column title="Destination" property="destination"
        sortable="true" />
    <display:column title="Tag" property="tag"
        sortable="true" />
</display:table>
</body>
</html>

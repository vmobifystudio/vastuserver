<%-- <%@ page language="java" errorPage="/WEB-INF/pages/error.jsp" isELIgnored="false" %> --%>
<%@ page language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="ss" uri="/WEB-INF/tld/custom.tld" %>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.vishwakarma.vastu.model.UserInfo" %>

<c:set var="datePattern"><fmt:message key="date.format"/></c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="req" value="${pageContext.request}"/>


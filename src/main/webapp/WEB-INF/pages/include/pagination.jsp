<%@ include file="/taglibs.jsp"%>
<c:url var="firstUrl" value="${urlPrefix}1" />
<c:url var="lastUrl" value="${urlPrefix}${page.totalPages}" />
<c:url var="prevUrl" value="${urlPrefix}${currentIndex - 1}" />
<c:url var="nextUrl" value="${urlPrefix}${currentIndex + 1}" />
<c:url var="pageSizeChangeUrl" value="${urlPrefixForPageSize}" />

<!-- <div class="input-append"> -->
<!--   <input class="span2" id="searchTerm" size="16" type="text"> -->
<!--   <button class="btn" type="button">Search</button> -->
<!--   <button class="btn" type="button">View All</button> -->
<!-- </div> -->
<div class="pagination">
<div class="row">
<div class="span10">
    <ul class="pagination pagination-sm">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}">&lt;&lt;</a></li>
                <li><a href="${prevUrl}">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${urlPrefix}${i}" />
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${(currentIndex == page.totalPages) or (page.totalPages == 0)}">
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}">&gt;</a></li>
                <li><a href="${lastUrl}">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

<div>
<c:if test="${showPageSize}">
	<form class="form-inline">
	<label for="pageSizeSelect">
	Page Size:
	</label> 
	<select id="pageSizeSelect" style="width:50px">
	<option value="${pageSizeChangeUrl}25">25</option>
	<option value="${pageSizeChangeUrl}50">50</option>
	<option value="${pageSizeChangeUrl}100">100</option>
	</select>
	</form>
</c:if>
</div>
</div>
</div>

<script type="text/javascript">
$('#pageSizeSelect option').filter(function() {
	return $.trim( $(this).text() ) == $('#pageSize').val(); 
	}).attr('selected','selected');

$('#pageSizeSelect').change(function() {
	var url = $(this).val();
	window.location = url;
	return false;
});

</script>
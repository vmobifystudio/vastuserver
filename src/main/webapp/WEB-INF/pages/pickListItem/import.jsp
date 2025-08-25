<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EduKit - Import Sales Inquiry</title>
</head>
<body>

<a class="btn btn-info" href="<c:url value='/assets/import-templates/salesInquiry.xls' />"> Download XLS Template </a> 
<br />
<br />
<form class="well form-inline" method="post" action="import" enctype="multipart/form-data">
	<fieldset>
		<legend>Import from Spreadsheet (XLS)</legend>
    	<input class="input-file" type="file" id="file" name="file"/>
    	<br />
    	<br />
    	<button type="submit" class="btn btn-primary" id="import">Import</button>
    	<a class="btn btn-danger" href="javascript:history.back(1)">Cancel </a>
    </fieldset>
</form>

</body>
</html>
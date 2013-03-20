<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="-1"> 
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="No-Cache"> 
<title>파일 올리기</title>
<style type="text/css">
	div.container { width: 100% }
	input[type=file] { width: 90%}
	textarea { width: 80%}
	span { vertical-align:top; }
</style>
</head>
<body>
	<form:form modelAttribute="uploadItem" Method= "post" enctype= "multipart/form-data">
		<div id="file_container" class="container">
			<span>파일 : <form:input path="fileData" type="file"/></span>
		</div>
		<div id="desc_container" class="container">
			<span>설명 : <textarea name="description" cols="50" rows="10"></textarea></span>
		</div>
		<br/><br/>
		<input type="submit" value= "전송">
	</form:form>
</body>
</html>
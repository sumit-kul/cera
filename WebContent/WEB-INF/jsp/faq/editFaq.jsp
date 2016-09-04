<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak - FAQ </title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<script type="text/javascript" src="faqs.js" ></script>
		<script src="ckeditor/ckeditor.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript">
			function updateFAQ(){
				document.submitFAQEntry.action = "${communityEraContext.contextUrl}/faq/faqEdit.do";
				document.submitFAQEntry.submit();
			}
		</script>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="breadcrum">
					<ul>
						<li>You are here:</li>
						<li><a href="${communityEraContext.contextUrl}">Welcome</a></li>
						<li><a href="${communityEraContext.contextUrl}/faq/showAllFaqs.do" >FAQ List</a></li>
						<li class="uppercase">${command.subject}</li>
					</ul>
				</div>
				
				<div class="commSection">
					<div class="communities">
						<h2 style="margin-top:-10px;">Edit FAQ</h2>
					</div>
					
						<div class="inputForm">
				 			<div class="innerBlock">
				 			<form:form showFieldErrors="true" commandName="command" multipart="true" name="submitFAQEntry">
								<form:errors message="Please correct the errors below" cssClass="errorText" />
								<form:hidden path="id"/>
								<label for="title">Question:</label>
									<br />
								<form:input path="subject" cssClass="editor" cssStyle="width: 670px; margin: 10px 0px 20px 0px;"/>
												
								<div style="margin-right: 10px;">
									<form:textarea cssClass="ckeditor" path="body" id="body"></form:textarea>
								</div>
										
								<c:if test="${communityEraContext.userSysAdmin}">	
								<br/>		
									<c:if test="${command.filePresent}">
										<p>Download
										<a href="${communityEraContext.contextUrl}/faq/get-file.do?id=${command.id}" class="pdf" target="_blank" 
											title="Opens a new window containing attached file" class="search_btn" >  [${command.fileName}]</a> | 
										<a href="${communityEraContext.contextUrl}faq/file-remove.do?id=${command.id}" class="search_btn" >Remove this file</a>
										</p>
									</c:if>		
									<p><label  for="file1">Upload the document:</label><br />
									<input class="browse" type="file" id="file" name="upload" size="60" title="System Administrator only"/>
									<br/><form:errors path="upload" cssClass="errorText" />
									</p>
								</c:if>
								<div class="left" style="margin-bottom: 20px;">
									<a href="javascript:void(0);" onclick="updateFAQ();" class="btnmain" >Update FAQ</a>
								</div>
							</form:form>
						</div>
					</div>
					
				</div> 
			</div> 
			
			<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
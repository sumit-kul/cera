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
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="breadcrum">
					<ul>
						<li>You are here:</li>
						<li><a href="${communityEraContext.contextUrl}">Welcome</a></li>
						<li class="uppercase">Frequently Asked Questions</li>
					</ul>
				</div>
				
				<div class="commSection">
					<div class="communities" style="padding: 0px 10px;">
						<h2 style="margin-top:-10px;">Frequently Asked Questions</h2>
						<p></p>
						<div class="menus">
							<ul>
								<c:if test="${communityEraContext.userSysAdmin}">
									<li><a href="${communityEraContext.contextUrl}/faq/faqCreate.do" >Add a new question</a></li>
								</c:if>
							</ul>
						</div>
					</div>
	
					<c:forEach var="row" items="${command.faqList}">
						<div class="inputForm">
			 				<div class="innerBlock">
							<dl>
								<dt style="font-size: 11px; text-decoration: none; font-weight: bold; color: rgb(0, 114, 167);">
								<a name="A${row.id}"></a>${row.subject}
								</dt>
								<dd><div class="richtext ${backgroundclass}">${row.body}</div></dd>
								<dd>
									<c:if test="${row.filePresent}">
													Download
										<a href="${communityEraContext.contextUrl}/faq/get-file.do?id=${row.id}" class="pdf" target="_blank" 
										title="Opens a new window containing attached file">  [${row.fileName}]</a>
									</c:if>
								</dd>
								<dd>
									<c:if test="${communityEraContext.userSysAdmin}">
										<div class="right" >
										<br />
											<a href="${communityEraContext.contextUrl}/faq/faqRemove.do?id=${row.id}" class="newButtton" style="float:right;margin-left:10px;">Remove</a>
											
											<a href="${communityEraContext.contextUrl}/faq/faqEdit.do?id=${row.id}" class="newButtton" style="float:right;">Edit</a>
											
										</div>
									</c:if>		
								</dd>
							</dl>
							</div>
			   			</div>
			   		</c:forEach>
					</div> 
				</div> 
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
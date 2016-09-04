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
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>${communityEraContext.currentCommunityName}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="forum">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">

				<p id="breadcrumb">
					
					You are here: <a href="${communityEraContext.contextUrl}/comm/a-z-index.do">All Communities</a> &gt; 
					<c:forEach items="${communityEraContext.currentCommunity.ancestors}" var="ancestor">
						<a href="${communityEraContext.contextUrl}/cid/${ancestor.id}/home.do" >${ancestor.name}</a> &gt;
					</c:forEach>
					<a href="${communityEraContext.currentCommunityUrl}/home.do">${communityEraContext.currentCommunityName}</a> &gt; 
					<a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do" class="backarrow">Library</a>
						
				</p>


			<div id="columnmain" class="forum">
				<form:form showFieldErrors="true" commandName="command" multipart="true">
				<ul id="forumnav">
					<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do" class="backarrow">Back to documents</a></li>
				</ul>
				
				<dl>
					<dt>Document deleted</dt>
					<dd><p>The document has been deleted. <a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do">Click here to return to the document library</a>.</p></dd>

				</dl>
				</form:form>


			</div>

			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>

		</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
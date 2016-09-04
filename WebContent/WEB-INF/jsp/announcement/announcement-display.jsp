<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>Admin Announcements</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="canonical" href="${communityEraContext.requestUrl}" />
	<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
	<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	<style type="text/css"> 	td.unactivated { 	background-color: #red; color: red; 	} 	</style>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="my-profile">

	<%@ include file="/WEB-INF/jspf/open-container2.jspf" %>
	<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
	<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">

			<p id="breadcrumb">						
						You are here: 	<a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a> &gt;
						<a href="${communityEraContext.contextUrl}/announcement/announcement-index.do">Announcements</a> &gt; ${command.title}
					</p>
			
			<div id="columnmain" >	
					<dd class="forumdetails">
					<h2 class="header blue">${command.title}
					<c:if test='${command.status == 1}'>(Draft)</c:if>
						<c:if test='${command.status == 2}'>(Live)</c:if>
						<c:if test='${command.status == 3}'>(Archived)</c:if>
					</h2>
					Posted by <a href="${communityEraContext.contextUrl}/admin/user-details.do?id=${command.authorId}">${command.authorName}</a> |   <fmt:formatDate value="${command.datePosted}" pattern="dd MMM yyyy kk:mm" />
					</dd>
					
					${command.subject}
					
					<dd><div class="richtext">${command.body}</div></dd>
					
					<%--
					<c:if test='${command.file1Present}'>
						<dd>
						Download <a href="/announcement/get-file.do?id=${command.id}&amp;file=1" 
						class="${command.file1IconClass}" target="_blank" title="Opens a new window containing attached file">[${command.fileName1}]</a> | ${command.sizeInKb1}Kb</dd>
					</c:if>

					<c:if test='${command.file2Present}'>
						<dd >
						Download<a href="/announcement/get-file.do?id=${command.id}&amp;file=2" 
						class="${command.file2IconClass}" target="_blank" title="Opens a new window containing attached file"> [${command.fileName2}]</a> | ${command.sizeInKb2}Kb</dd>
					</c:if>
					
					<c:if test='${command.file3Present}'>
						<dd >
						Download <a href="/announcement/get-file.do?id=${command.id}&amp;file=3" 
						target="_blank" title="Opens a new window containing attached file"
						class="${command.file3IconClass}">[${command.fileName3}]</a> | ${command.sizeInKb3}Kb</dd>
					</c:if>
					--%>
					<div class="hr"><hr /></div>

				</div>
			

		<div id="columnright">
						
			<div class="padding">
			<%@ include file="/WEB-INF/jspf/font-size-switcher.jspf" %>
		<h3>Tools</h3>
		
		<ul class="tools">
			<c:if test='${command.status == 1}'>
			<li class="important"><a href="${communityEraContext.contextUrl}/announcement/announcement-change-status.do?id=${command.id}&amp;actionType=live">Publish</a></li>
			</c:if>
			
			<c:if test='${command.status == 2}'>
			<li class="important"><a href="${communityEraContext.contextUrl}/announcement/announcement-change-status.do?id=${command.id}&amp;actionType=archive">Archive</a></li>	
			</c:if>
			
			<li class="important"><a href="${communityEraContext.contextUrl}/announcement/announcement-add-edit.do?id=${command.id}">Edit this announcement</a></li>			
			
			<li class="cross"><a href="${communityEraContext.contextUrl}/announcement/announcement-delete.do?id=${command.id}">Delete this announcement</a></li>		
			
		</ul>

		</div>
		
		</div>


	</div> 

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
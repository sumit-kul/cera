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
						You are here: 	<a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a> &gt; Announcements
					</p>
						
				<div id="columnmain">


	<form:form method="get">
		<div class="formelements">
			<form:dropdown path="pageSize">
				<form:option value="5" label="View 5 per page" />
				<form:option value="8" label="View 8 per page" />
				<form:option value="10" label="View 10 per page" />
				<form:option value="15" label="View 15 per page" />
			</form:dropdown> 
			<input type="hidden" name="page" value="${command.page}" /> 
			<input class="btnmain" type="image" src="img/btn-orange-go.gif" alt="Go" />
		</div>
	</form:form>
	
				<table class="type1" cellpadding="0" cellspacing="0" border="0" summary="Forum topics">
					<thead>
					<tr>
						<th>Title</th>
						<th>Type</th>
						<th >Author</th>
						<th>Status</th>
						<th>Date Posted</th>
					</tr>
					</thead>
					
					<tbody>
						
					<c:forEach items="${command.scrollerPage}" var="row" varStatus="status">		
					
						<tr class="${status.index % 2 eq 0 ? '' : 'alt'}" >
												
						<td width="40%">					
							<a href="${communityEraContext.contextUrl}/announcement/announcement-display.do?id=${row.id}">${row.title}</a>
						</td>
						<td>${row.messageType==1?"Service":"Information"} 
						<td >${row.authorName}</td>
						
						<td>
						<c:if test='${row.status == 1}'>Draft</c:if>
						<c:if test='${row.status == 2}'>Live</c:if>
						<c:if test='${row.status == 3}'>Archived</c:if>
						</td>
						
						<td>
						<fmt:formatDate value="${row.datePosted}" pattern="dd MMM yyyy kk:mm" />
						</td>
						
						</tr>
					</c:forEach>
				
					</tbody>
 			   </table>

	<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" />

			</div>

			
		<div id="columnright">
						
			<div class="padding">
		<h3 class="blue">Tools</h3>
		<ul class="tools">
		    <li class="plus"><a href="${communityEraContext.contextUrl}/announcement/announcement-add-edit.do" title="Start a new announcement">Start new announcement</a></li>
		</ul>
		
			</div> 
			
		</div> 

		</div> 

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
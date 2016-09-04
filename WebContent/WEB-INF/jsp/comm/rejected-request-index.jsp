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
	<title>${communityEraContext.currentCommunity.name}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="wiki-index">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

			<div class="padding">
			
			<div id="main">
			
						<p id="breadcrumb">
							<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
							<a href="${communityEraContext.currentCommunityUrl}/admin.do">Admin</a> &gt;
							Rejected membership requests
						
						</p>
		
			<form:form>


			<div id="columnmain" class="forum">

			<c:if test="${command.numberOfRejectedRequests==0}">There are no current rejected membership requests</c:if>
			
			<c:if test="${command.numberOfRejectedRequests>0}">
			
				<p>The following users have been rejected to join this community</p>
				

					<table  class="type1" summary="Membership Requests">
					
					<thead>
					
						<tr>
							<th>Select</th>
							<th >Name</th>
							<th nowrap="nowrap">&nbsp;</th>
							<th>Organisation</th>
							<th>Request date</th>
						</tr>
					
					</thead>
	
					<c:forEach items="${command.scrollerPage}" var="row" varStatus="status">

							<tr class="${status.index % 2 eq 0 ? 'alt' : ''}" >
    						<td><input name="selectedIds" value="${row.user.id}" type="checkbox" class="check" /></td>
    		
							<td width="1">
			
								<c:if test="${row.user.photoPresent}">
								<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img" var="photoUrl"><c:param name="id" value="${row.user.id}"/></c:url>
								<img src="${photoUrl}" class="mugshot" width="36" height="36" /> 
								</c:if>
			
								<c:if test="${not row.user.photoPresent}">	<img src="img/user_icon.png" class="mugshot"/> </c:if>
		
							</td>

							<td class="nowrap">
								<c:url value="${communityEraContext.contextUrl}/pers/connectionResult.do" var="profileUrl">
									<c:param name="id" value="${row.user.id}"/><c:param name="backlink" value="ref"/>
								</c:url>
								<a href="${profileUrl}">${row.user.fullName}</a>
								<br>${row.user.jobTitle}	
							</td>
					

							<td>${row.user.authorityOrOrganisationName}</td>
							
							<td><fmt:formatDate value="${row.requestDate}" pattern="dd MMM yyyy" /></td>
			
					</tr>

				</c:forEach>						

 				</table>

		<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" /> 
		
		</c:if>

		</div>
		
		<div id="columnright">
						
			<div class="padding">
			<%@ include file="/WEB-INF/jspf/font-size-switcher.jspf" %>
			
					<c:if test="${command.numberOfRejectedRequests>0}">
			
					<h3 class="green">Admin Tools</h3>
			
					<ul class="tools">	
			
					<li class="cross">
						<input type="submit" name="btnmain" value="Delete selected " title="Delete the selected requests with no response to the user"
						  	style="font-weight: bold; font-size: 1.1em; border:none;background-color: transparent;text-align: left;text-decoration: underline;"/>
					</li>
			
				</ul>
				
				<div class="guide">
								
				<h3><strong>Select requests</strong></h3>			
				<p>Use the check boxes to select which rejected membership requests to delete</p>
								
			</div>
			
				</c:if>
			

			</div> 
			
		</div> 
		
		
       </form:form>

	       	</div> 

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
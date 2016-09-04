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
		<title>Jhapak - ${command.title}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
			    width: 160px;
			}
		</style>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
				<div class="innerPanl">
					<div class="carousel_caption">
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Private'}">
							<h4><i class="fa fa-lock" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Protected'}">
							<h4><i class="fa fa-shield" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Public'}">
							<h4><i class="fa fa-globe" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<p>${communityEraContext.currentCommunity.welcomeText}</p>							
					</div>
					<div class="menuContainer">
						<ul id="gn-menu" class="gn-menu-main">
							<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
							<li>
								<nav id="menu">
						    		<input type="checkbox" id="toggle-nav"/>
						    		<label id="toggle-nav-label" for="toggle-nav"><i class="fa fa-bars"></i></label>
						    		<div class="box">
							    		<ul>
							    			<li><a href="${communityEraContext.currentCommunityUrl}/home.do"><i class="fa fa-home" style="margin-right: 8px;"></i>Home</a></li>
							    			<c:forEach items="${communityEraContext.enabledFeatureNamesForCurrentCommunity}" var="feature">
												<c:if test="${feature == 'Assignments'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/assignment/showAssignments.do"><i class="fa fa-briefcase" style="margin-right: 8px;"></i>Assignments</a></li>
												</c:if>
												<c:if test="${feature == 'Blog'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>Blogs</a></li>
												</c:if>
												<c:if test="${feature == 'Forum'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do"><i class="fa fa-comments-o" style="margin-right: 8px;"></i>Forums</a></li>
												</c:if>
												<c:if test="${feature == 'Members'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
												</c:if>
												<c:if test="${feature == 'Library'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
												</c:if>
												<c:if test="${feature == 'Events'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
												</c:if>
											</c:forEach>
							    		</ul>
						    		</div>
						    	</nav>
					    	</li>
						</ul>
					</div><!-- /container -->
					<div class="groups">
						<div class="picture">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="290" height="290" /> 
								</c:when>
								<c:otherwise>
									<img src="img/community_Image.png" id="photoImg" width="290" height="290" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div class="left-panel">
			<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
			<div class="commSection">
				<div class="communities">
					<div class="creater">
						This wiki page was published by 
						<%-- <a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.posterId}&backlink=ref" 
						title="Click here to visit ${command.displayName}'s profile" >${command.displayName}</a> on ${command.postedOn} --%>
					</div>

					<div class="menus">
						<ul>
							<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>
						</ul>
					</div>
				</div> 
				
				<div class="paginatedList" style="margin-bottom:20px;">				
					<div id="sort-by">
						<div id="tabs">
					 		<a href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}">Current version</a>
							<h4 class="selectedTab" >Previous versions</h4>	
						</div>	
					</div>
					
					<c:forEach items="${command.scrollerPage}" var="row">	
						<div class='internalPagination'>
							<div class='leftImg'>	
								<c:choose>
									<c:when test="${row.editedByPhotoPresent}">	
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img" var="photoUrl">
										<c:param name="id" value="${row.posterId}"/></c:url>
										<img src="${photoUrl}" alt="${row.editedBy}"/>	
									</c:when>
									<c:otherwise>
										<img src="img/user_icon.png" />
									</c:otherwise>
								</c:choose>	
							</div>
							<div class='details' style="width: 610px;">
								<div class='heading'>
									<a href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?backlink=ref&amp;id=${row.id}">Version: ${row.sequence} [${row.title}]</a>
								</div>
								<div class='person'>
								<c:if test="${!row.first}">
									Edited by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}'>${row.editedBy}</a> on ${row.datePosted}
								</c:if>
								<c:if test="${row.first}">
									Published by ${row.editedBy} on ${row.datePosted}
								</c:if>
									
								</div>
								<c:if test="${!row.first}">
									<div class='person'>
										Reason for update: ${row.reasonForUpdate}
									</div>
								</c:if>
							</div>
						</div>
						<c:if test="${!row.first}">
						<br />
							<li class='lineseparater' style="list-style: none outside none;margin: 60px 3px 5px 8px;"></li>
						</c:if>
					</c:forEach>
					</div>
				</div> 
				</div> 		
				
				<div class="right-panel">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
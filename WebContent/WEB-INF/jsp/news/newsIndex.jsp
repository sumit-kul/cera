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
		<meta name="author" content="Jhapak">
		<meta name="robots" content="noindex,nofollow">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak | ${communityEraContext.currentCommunity.name}</title>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>      
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	</head>

	<body id="communityEra">
		<div class="mainBody">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
	
			<div class="containerMain">	
				<div class="leftPannel">
					<p id="breadcrumb"><%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %> News feeds </p>
					
					<div class="myUploadExtra">
							<div class="logowithname">
								<div class="commlogo">
									<c:choose>
										<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
											<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="160" height="160"/> 
										</c:when>
										<c:otherwise>
											<img src="img/community_Image.png" id="photoImg" width="160" height="160" />
										</c:otherwise>
									</c:choose>
								</div>
								<div class="commdetails">
									<p class="one_row_comm">
									<a href="${communityEraContext.currentCommunityUrl}/home.do" class="commNameTop">${communityEraContext.currentCommunity.name}</a>
								</p>
								</div>
							</div>
							<div class="commdetails">
								<p class="one_row_comm2">RSS feeds</p>
								<p>Use news feeds to see when websites selected by this community have added new content.<p>
							</div> <!-- /commdetails -->
							<div class="commOptions">
								<ul class="commMenu">
									<li><a href="${communityEraContext.currentCommunityUrl}/home.do">Home</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do">Blogs</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Events</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do">Forums</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do">Library</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do">Connections</a></li>
									<li class=""><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do">Wikis</a></li>
								</ul>
							</div>
					 	</div> <!-- /myUploadExtra -->
					 	
					 	<div class="actionBlock2">
					 		<div class="commbtns">
								<div class="filterBox">
									<%--<form:form showFieldErrors="true">
											<form:dropdown path="sortByOption" fieldLabel="Sort By:" cssClass="almostshortwidth">
												<form:optionlist options="${command.sortByOptionOptions}" />
											</form:dropdown>
										<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
										<input type="submit" value="Go" class="search_btn" />
									</form:form> --%>
								</div>
							</div>
						</div> <!-- /actionBlock -->
				
		
		<div class="subcommdetailsMain">
			<form:form action="${communityEraContext.requestUrl}" method="get">
				<div class="formelements">
						<form:dropdown path="pageSize">
						<form:option value="5" label="View 5 per page"/>
						<form:option value="8" label="View 8 per page"/>
						<form:option value="10" label="View 10 per page"/>
						<form:option value="15" label="View 15 per page"/>
						</form:dropdown>
					<input type="hidden" name="page" value="${command.page}" /> 
					<input type="hidden" name="feedId" value="${command.feedId}" /> 
					<input class="btnmain" type="image" src="img/btn-green-go.gif" alt="Go" /> 
				</div>
			</form:form>
			
			<div class="subcommdetails">
				<div class="rate" style="width: 97% !important">
				<p>
				<c:choose>
					<c:when test="${empty command.feedList}">
							No news feeds have been added for this community
					</c:when>
					<c:when test="${not empty command.feed}">			
						<c:if test="${communityEraContext.userCommunityAdmin}">
								<a style="float:right" href="${communityEraContext.currentCommunityUrl}/news/feed-delete.do?feedId=${command.feed.id}&pageSize=${command.pageSize}">Remove this feed</a>
						</c:if>
						<strong style="font-size:1.2em">${command.feed.name}</strong><br/>
						${command.feed.title}
					</c:when>
					<c:otherwise>
						<br/><strong style="font-size:1.2em">
						All news feeds (<fmt:formatNumber pattern="###,###,###" value="${command.newsItemCount}" /> items)
						</strong><br/>
					</c:otherwise>
				</c:choose>				
   				</p>				
				</div><!-- /.rate -->
			
				<dl>
					<c:forEach items="${command.scrollerPage}" var="row">										
						<dt><a class="green" href="${row.link}" title="View the full article">${row.title}</a></dt>
						<dd class="detailsNoBottomLine"> ${row.feed.name} | <fmt:formatDate value="${row.datePublished}" pattern="dd MMM yyyy" /></dd>
						<dd>${row.description}</dd>
						<dd class="details"> </dd>
					</c:forEach>					
				</dl>

				<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" />
			</div> <!-- /subcommdetails -->  
		</div> <!-- /subcommdetailsMain -->
	</div><!-- /.myColumnmain -->

		<div id="columnright">
			<div class="padding">
				<h3 class="green">View RSS feeds from</h3>
				<ul class="tools">
				<li class="feed"><a href="${communityEraContext.currentCommunityUrl}/feed/newsFeeds.do?pageSize=${command.pageSize}">All RSS feeds</a></li>
				<c:forEach items="${command.feedList}" var="feed">										
					<li class="feed"><a href="${communityEraContext.currentCommunityUrl}/feed/newsFeeds.do?feedId=${feed.id}&pageSize=${command.pageSize}">${feed.name}</a></li>
				</c:forEach>
				</ul>
	
				<c:if test="${communityEraContext.userCommunityAdmin}">
					<form style="margin-top:10px;" action="${communityEraContext.currentCommunityUrl}/news/feed-add.do" method="post">
							<h3 style="margin-bottom:0">Add a new RSS feed</h3>
							<div style="color:black;margin:0">Enter name and URL</div>
							<input type="text" name="feedName" value="" style="margin:0;color: #606060" class="text" />
							<input type="text" name="feedUrl" value="http://" style="margin:0;color: #606060" class="text"/>
							<input type="hidden" name="pageSize" value="${command.pageSize}" />
							<input class="btnmain" type="image" src="img/btn-green-subscribe.gif" alt="Subscribe" />
					</form>
				</c:if>
			</div> 
		</div> 

	</div> <!-- /#myMain -->	
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
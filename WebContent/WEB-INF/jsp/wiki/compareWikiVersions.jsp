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
		<title>Jhapak - ${command.title} : Compare wiki versions</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf"%>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		
        <link href="css/diff.css" type="text/css" rel="stylesheet"/>
      	<script src="js/tooltip/wz_tooltip.js" type="text/javascript"></script>
      	<script src="js/tooltip/tip_balloon.js" type="text/javascript"></script>
      	<script src="js/dojo/dojo.js" type="text/javascript"></script>
      	<script src="js/diff.js" type="text/javascript"></script>
      	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
      	<script>
        	htmlDiffInit();
      	</script>
		
		<style type="text/css">
			.topHeader .heading {
			    width: 600px;
			    color: #184A72;
			    margin-bottom: 2px;
			    background: transparent none repeat scroll 0% 0%;
			    font-family: "Linux Libertine",Georgia,Times,serif;
			}
			
			#container .left-panel .commSection .paginatedList .creater {
				display: inline-block;
				width: 97%;
				margin-left: 4px;
			}
			
			#container .left-panel .heading p {
				font-size: 12px;
				font-weight: bold;
				color: #66799f;
			}
			
			strong {
				font-weight: normal;
			}
			
			#container .left-panel .commSection .paginatedList .topSection {
				border-width: 0px;
			}
			
			#container .left-panel .commSection .paginatedList {
				background: #F5F5F5 none repeat scroll 0% 0%;
			}
			
			#container .left-panel .commSection .paginatedList .topSection {
				margin-bottom: 0px;
			}
			
			#container .left-panel .commSection .paginatedList .sections {
				margin: 0px 6px 8px;
				background-color: #FFF;
				padding: 8px;
				border-radius: 5px;
				display: inline-block;
			}
			
			#container .left-panel .commSection .paginatedList .sections h2.secHeader {
				margin-top: 20px;
				margin-bottom: 8px;
				width: 100%;
				display: inline-block;
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
					<div class="paginatedList" style="margin: 0px 0px 10px auto; padding: 0px;">	
						<div class="topSection" style="padding: 0px;">
							<div class="menus" style="float: left; width: 100%; border-bottom: 1px solid #D8D8D8; padding: 0px 10px 10px;">
								<ul style="margin-top: 6px;">
									<li><a class="heaedrMenu"href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Page</a></li>
									<li><a class="heaedrMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Discussion</a></li>
									<li><a class="heaedrMenu selectMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiHistory.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">History</a></li>
								</ul>
							</div>
							<div style="padding: 10px 10px 5px; display: inline-block;">
								<div class="topHeader" >
									<div class="heading" style="font-family: Arial,Helvetica,sans-serif;">
										Compare wiki versions: <span style="font-size: 20px; font-weight: bold;" id="topTitle">${command.title}</span>
									</div>
								</div> 
							</div>
							<div class="creater">
								<form:form showFieldErrors="true">
									<div style="width: 50%; float: left;">
										<label for="first" style="width: 90px;">Later version:</label>
										<form:dropdown path="first" id="firstVersion" >
											<form:optionlist options="${command.versionOptionList}"/>
										</form:dropdown>
										<div style='margin-top: 8px;'>
											<div class='leftImg'>
												<c:choose>
													<c:when test="${command.previousUser.photoPresent}">		
														<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${command.previousUser.id}" width="120" height="120" /> 
													</c:when>
													<c:otherwise>
														<img src="img/community_Image.png" id="photoImg" width="120" height="120" />
													</c:otherwise>
												</c:choose>
											</div>
											<div class='details' style="max-width: 300px;">
												<div class='heading' >
													<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.laterUser.id}&backlink=ref'>${command.laterUser.fullName}</a>
													<p><a href='${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}&amp;entrySequence=${command.first}&backlink=ref' 
														title='${command.firstSequence}: ${command.title}' style="font-size: 12px;"><fmt:formatDate pattern="kk:mm, dd MMM yyyy" value="${command.laterDate}" />
													</a></p>
													<span style="max-width: 300px;">${command.laterReason}</span>
												</div>
											</div>
										</div>
									</div>
									<div style="width: 50%; float: left;">
										<label for="second" style="width: 90px;">Earlier version:</label>
										<form:dropdown path="second" id="secondVersion" >
											<form:optionlist options="${command.versionOptionList}"/>
										</form:dropdown>
										<input type="hidden" id="entryId" name="entryId" value="${command.entryId}"/>
										<input type="submit" value="Continue" style="float: right;" class="btnmain" />
										<div style='margin-top: 8px;'>
											<div class='leftImg'>
												<c:choose>
													<c:when test="${command.previousUser.photoPresent}">		
														<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${command.previousUser.id}" width="120" height="120" /> 
													</c:when>
													<c:otherwise>
														<img src="img/community_Image.png" id="photoImg" width="120" height="120" />
													</c:otherwise>
												</c:choose>
											</div>
											<div class='details' style="max-width: 300px;">
												<div class='heading' >
													<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.previousUser.id}&backlink=ref'>${command.previousUser.fullName}</a>
													<p><a href='${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}&amp;entrySequence=${command.second}&backlink=ref'  title='${command.second}: ${command.title}' style="font-size: 12px; width: 300px;">
														<fmt:formatDate pattern="kk:mm, dd MMM yyyy" value="${command.previousDate}" />
													</a></p>
													<span style="max-width: 300px;">${command.previousReason}</span>
												</div>
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
						<c:if test="${command.compared}">
							<div class="sections" >
								${command.comparisonText}
							</div>
							<c:forEach items="${command.compareSections}" var="row">
								<c:if test="${row.compared}">
								<div class="sections" style="margin: 12px 10px; 0px; display: inline-block;">
									<h2 class="secHeader"><span>${row.secHeader}</span></h2>
									${row.comparisonText}
								</div>
								</c:if>
							</c:forEach>
						</c:if>
						</div>
					</div> 
				</div>		
				
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
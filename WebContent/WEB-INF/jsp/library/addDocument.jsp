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
		<meta name="description" content="Jhapak, file, folder, add-file" />
		<meta name="keywords" content="Jhapak, file, folder, add-file, community" />
		<title>Jhapak - Add File</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<cera:taggingJS context="${communityEraContext}"/>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript">
			function submitLibraryEntry(){
				document.addFileAction.action = "${communityEraContext.requestUrl}";
				document.addFileAction.submit();
			}
		</script>
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
							<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
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
												<c:if test="${feature == 'Wiki'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
												</c:if>
												<c:if test="${feature == 'Members'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
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
				<div class="commSection">
					<div class="inputForm">
				 		<div class="innerBlock">
							<form:form showFieldErrors="true" commandName="command" multipart="true" name="addFileAction">
								<form:errors message="Please correct the errors below" cssClass="errorText" /> 
								<label for="title">Title:</label>
								<br />
								<form:input path="title" cssClass="editor"/>
								<br /><br />
								<label for="description">Description:</label>
								<br />
								<form:textarea tabindex="1" path="description" id="description" rows="12" cssClass="fixedWidth"/>
							
								<div class="tagOutLine" style="margin-top: 12px; padding: 0px 0px 15px 10px;width:680px;">
									<h4>Upload the document or file:</h4><h6>Click on browse to select the file to upload</h6>
									<input class="browse" type="file" id="upload" name="upload" size="50" />
									<br/><form:errors path="upload" cssClass="errorText" />
								</div>
								<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="20" parentType="document" />
								<div class="left" style="margin-bottom: 20px;">
									<a href="javascript:void(0);" onclick="submitLibraryEntry();" class="btnmain" >Submit library entry</a>
								</div>
							</form:form>
						</div>
						</div>
					</div> 
				</div>
				
				<div class="right-panel">
					<div class="inbox">
						<div class="eyebrow">
							<span><i class="fa fa-tags" style="margin-right: 2px;"></i>Popular Tags</span>
						</div>
						<div id="cloud" class="communities"></div>
						<div id="waitCloudMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div id="cloudList" style="display: none; " class="communities"></div>
						<ul id="cloudTabs">
							<c:choose>
								<c:when test="${command.toggleList == 'true'}">
									<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
								</c:when>
								<c:otherwise>
									<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
							  	</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
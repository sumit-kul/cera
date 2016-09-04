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
		<meta name="robots" content="index, follow">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak | ${communityEraContext.currentCommunity.name}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	</head>
	<body id="myHomepage">
		<div class="mainBody">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
			<%@ include file="/WEB-INF/jspf/middleHeader.jspf" %>
			<%@ include file="/WEB-INF/jspf/bottomHeader.jspf" %>
	
			<div class="containerMain">
				<div class="leftPannel">
					<p id="breadcrumb"><%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %> Add relevent Website</p>
					<div class="myUpload">
						<div class="commlogo">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?communityId=${communityEraContext.currentCommunity.id}" width="160" height="160" /> 
								</c:when>
								<c:otherwise>
									<img src='img/community_Image.png' width="160" height="160" />
								</c:otherwise>
							</c:choose>
						</div>
						<div class="commdetails">
							<p class="one_row_comm">
								<a href="${communityEraContext.currentCommunityUrl}/home.do" class="commNameTop">${communityEraContext.currentCommunity.name}</a>
							</p>
							<p class="one_row_comm">Add relevent Website</p>
							<p>This is where community admins can add relevent Website</p>
						</div>
						<div class="commOptions">
							<ul class="commMenu">
								<li class="selected"><a href="${communityEraContext.currentCommunityUrl}/home.do">Home</a>
									<span class='dynaDropDown' title='community/communityOptions.ajx?currId=1001&commId=${communityEraContext.currentCommunity.id}'><span class='ddimgWht'/></span>
								</li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do">Blogs</a></li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Events</a></li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do">Forums</a></li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do">Library</a></li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do">Connections</a></li>
								<li class=""><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do">Wikis</a></li>  
							</ul>
						</div>
				 	</div> <!-- /myUpload -->
				 	<div class="commActionBlock">
						<div class="commbtns">
							<%@ include file="/WEB-INF/jspf/light-right-col.jspf" %>
						</div>
					</div> <!-- /commActionBlock -->
					
					<div class="commHomeRow" style="margin: 4px 0px 4px;" >

						<form:form showFieldErrors="true" commandName="command">
							<form:hidden path="id"/>
							<form:hidden path="sequence"/>
							<div style="padding: 8px;">
								<h3 ><strong>Search Site</strong></h3>
								<cera:formSection cssClass="header" label="Name"> 
								<p>Descriptive name - 20 chars recommended max.</p>  
								<form:input path="name"  cssClass="text"/>
								</cera:formSection>
				
								<cera:formSection cssClass="header" label="Site"> 
								<p>Enter the full site name as it appears after the http://<br/>e.g. www.xyz.com</p>  
								<form:input path="hostname" cssClass="text" />
								</cera:formSection>
				
							    <cera:formSection label="Description" cssClass="header">
									<p>Description - 300 chars max.</p>
									<form:textarea path="description" cols="60" rows="4" cssClass="text"/>
								</cera:formSection>
								<p>
									<input class="search_btn" type="image" 	src=""  alt="Continue" 	/>
								</p>
							</div>
						</form:form>
					</div>
					</div><!-- /#leftPannel -->
				
				<div class="rightOuterPannel" style="margin-top: 0px;">
				</div> 
				<%-- tag-cloud will be adjusted in last 	
				<div class="padding">	
				<%@ include file="/WEB-INF/jspf/tag-cloud.jspf" %>
				</div> --%>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		</div> 
	</body>
</html>

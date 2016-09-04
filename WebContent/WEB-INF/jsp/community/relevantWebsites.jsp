<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<title>Jhapak | Relevant Web Sites</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">	
			.checkbox-cell label {	width: auto !important;		}
			input.checkbox {	width: auto !important;		}						
		</style>	
	</head>
		<body id="myHomepage">
		<div class="mainBody">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
			<%@ include file="/WEB-INF/jspf/middleHeader.jspf" %>
			<%@ include file="/WEB-INF/jspf/bottomHeader.jspf" %>
	
			<div class="containerMain">
				<div class="leftPannel">
				<form:form showFieldErrors="true" commandName="command" >
					<p id="breadcrumb"><%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %> Search Sites</p>
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
									<p class="one_row_comm">Edit relevent Websites for this community</p>
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
									<c:if test="${command.listSize<20}">
										<a href="${communityEraContext.currentCommunityUrl}/relevantWebsiteCreate.do?backlink=ref" title="Add a new search site" class="search_btn">Add a new site</a>
									</c:if>
								</div>
							</div> <!-- /commActionBlock -->
							
							<div class="commHomeRow" style="margin: 4px 0px 4px;" >
								<div style="padding: 8px;">
									<p><strong>Edit search sites for this community</strong></p><br/>
									Community admins can create a list of web sites of special interest to the community to provide members with a personalized search tool.
									You can add up to a maximum of 20 sites. <c:if test="${command.listSize>1}">
									The order of the websites has no effect on the returned results. There are ${command.listSize} in the list for this community </p>
									</c:if>
		
									<form:errors message="Please correct the errors below" cssClass="errorText" /> 
		
									<table id="Table1" cellspacing="0" summary="Search sites listed" class="type1">
		
						  			<tr>
							  			<th scope="col" abbr="Select"></th>
							  			<th nowrap="nowrap" scope="col" abbr="Move" >Order</th>
							  			<th scope="col" abbr="Name">Site</th>
							  			<th scope="col" abbr="Description">Description</th>
						  			</tr>
						
									<c:forEach var="site" items="${command.sites}">
					
								  <tr>
								    <td class="alt"><input type="checkbox" class="inlinebutton" name="selectedIds" value="${site.id}"/></td>
					    
								    <td class="alt"  >
								    <a href="${communityEraContext.currentCommunityUrl}/admin/search-site-move.do?backlink=ref&amp;id=${site.id}&amp;dir=down" title="Move down">
								    <img border="0" src="img/down-arrow-tiny.gif" alt="Move down" class="left"/></a>
								    <a href="${communityEraContext.currentCommunityUrl}/admin/search-site-move.do?backlink=ref&amp;id=${site.id}&amp;dir=up" title="Move up">
								    <img border="0" src="img/up-arrow-tiny.gif" alt="Move up"  class="left"/></a>
								   </td>
		   
								   <td class="alt">
								    <a href="${communityEraContext.currentCommunityUrl}/admin/relevantWebsiteUpdate.do?backlink=ref&amp;id=${site.id}">${site.name}</a>
								    <br/>${site.hostname}
								    </td>
					
								    <td class="alt">${site.description}</td>
								  </tr>
					  
								</c:forEach>
		  					</table>
		  					<c:if test="${command.listSize>1}">
								<input  type="submit" name="buttonRemoveSelected" value="Remove selected" title="Remove the sites that are ticked in the list" 
								style="font-weight: bold; font-size: 1.1em; border:none; background-color: transparent;text-align: left;text-decoration: underline; cursor : pointer;  display:block;"/>
							</c:if>
						</div>
					</div>
				</form:form>
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
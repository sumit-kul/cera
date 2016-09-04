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

<body id="homepage">
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
<div id="container">


		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>

		<%@ include file="/WEB-INF/jspf/community-breadcrumb.jspf" %>

	<div class="padding">

		<div id="main">
			
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>
			<div id="columnmain" class="forum">
			<form:form>
				<ul id="forumnav">
					<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do" class="backarrow">Back to entries</a></li>
				</ul>
				
				<h2><span class="green">${command.wikiAction} an entry:</span> Preview your content</h2>
				
				<div id="compose">
					<div class="padding">

						<dl>
							<dt>${command.title}</dt>
							<dd class="detailsprofile"><fmt:formatDate pattern="dd MMM yyyy (kk:mm)" value="${command.today}"  /> | 0 edits</dd>

							<dd>
								<p>${command.body}</p>
							</dd>
							<dt class="subsub">Related entries</dt>
							<dd>
								<p>
<c:if test="${command.relatedId1 != ''}">			
								<a href="${communityEraContext.currentCommunityUrl}/${command.relatedEntryLink1}">${command.relatedEntryTitle1}</a><br />								
</c:if>	
<c:if test="${command.relatedId2 != ''}">							
								<a href="${communityEraContext.currentCommunityUrl}/${command.relatedEntryLink2}">${command.relatedEntryTitle2}</a><br />	
</c:if>
<c:if test="${command.relatedId3 != ''}">								
								<a href="${communityEraContext.currentCommunityUrl}/${command.relatedEntryLink3}">${command.relatedEntryTitle3}</a><br />	
</c:if>	
<c:if test="${command.relatedId4 != ''}">							
								<a href="${communityEraContext.currentCommunityUrl}/${command.relatedEntryLink4}">${command.relatedEntryTitle4}</a><br />	
</c:if>	
<c:if test="${command.relatedId5 != ''}">							
								<a href="${communityEraContext.currentCommunityUrl}/${command.relatedEntryLink5}">${command.relatedEntryTitle5}</a><br />	
</c:if>								
								</p>
							</dd>
							<dt class="subsub">Related external links</dt>
							<dd>
								<p>
<c:if test="${command.externalTitle1 != ''}">						
								<a href="${command.externalURL1}">${command.externalTitle1}</a><br />
</c:if>
<c:if test="${command.externalTitle2 != ''}">												
								<a href="${command.externalURL2}">${command.externalTitle2}</a><br />
</c:if>
<c:if test="${command.externalTitle3 != ''}">												
								<a href="${command.externalURL3}">${command.externalTitle3}</a><br />
</c:if>
<c:if test="${command.externalTitle4 != ''}">												
								<a href="${command.externalURL4}">${command.externalTitle4}</a><br />
</c:if>
<c:if test="${command.externalTitle5 != ''}">												
								<a href="${command.externalURL5}">${command.externalTitle5}</a><br />
</c:if>
								</p>
							</dd>
						</dl>

						<p><input class="inlinebutton" name="_target0" type="image" src="img/btn-green-edit.gif" alt="Edit" /><input class="fright button"  type="image" name="_finish" src="img/btn-green-confirmandpublish.gif" alt="Confirm and publish" /></p>
					</div>
				</div>
				</form:form>
			</div>

			</div>

			<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>


	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
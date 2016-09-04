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
		<title>Jhapak | New Wiki entry : Confirm</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="breadcrum">
					<ul>
						<li>You are here:</li>
						<li><a href="${communityEraContext.contextUrl}/community/showCommunities.do">All Communities</a></li>
						<li>
							<a href="${communityEraContext.currentCommunityUrl}/home.do">${communityEraContext.currentCommunity.name}</a>
						</li>
						<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do">Wiki</a></li>
						<li class="uppercase">${command.title}</li>
					</ul>
				</div>  
				
				
				<h2><span class="green">${command.wikiAction} a page:</span> Confirmed</h2>
				
				<div id="compose">
					<div class="padding">
						<h2>Your wiki page has been published successfully</h2>
						
						<p><a href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}" class="greenlink">Click here to view your page</a></p>
						
						<p>If you need to edit your wiki page, you can click on the edit link that appears in your tools for any page.</p>
						
						<p><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><img src="img/btn-green-viewallentries.gif" alt="View all pages" /></a></p>
					</div>
				</div><!-- /#compose  -->
				

			</div>

			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>

		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
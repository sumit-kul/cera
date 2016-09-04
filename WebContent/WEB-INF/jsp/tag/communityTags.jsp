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
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<base href="${communityEraContext.contextUrl}/"/>
<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link type="text/css" media="all" rel="stylesheet" href="cssTest/new2.css" />
	<link type="text/css" media="all" rel="stylesheet" href="cssTest/section.css" />
	<link type="text/css" media="print" rel="stylesheet" href="css/print.css" />
	
	<link type="text/css" media="print" rel="stylesheet" href="cssNew/bootstrap.css" />
	<link type="text/css" media="print" rel="stylesheet" href="cssNew/style.css" />
	
	<script type="text/javascript" src="js/mootools-release-1.11.js"></script>
	<script type="text/javascript" src="js/tips.js"></script>
	<title>${communityEraContext.currentCommunityName}</title>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	
	<style type="text/css">
		li.tag-list {	white-space: nowrap;	margin-bottom: 4px;		}
		li.tag-list a {	color: #699F01; 	display: inline; margin-left: 10px; 		}
	</style>
	
</head>

<body id="forum">
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		
				<p id="breadcrumb">
						<h4 class="tipped">
								<a href="${communityEraContext}/cid/12404/tag-list.do?backlink=ref" class="ttip" onclick="return false" title="Tagged Items :: Tags are one word labels which members can assign to any items in the community. They make it easier to find related items">
								<img src="img/i/help-ongreen.gif" alt="Help" width="18" height="18" />
								</a>	
								</h4>
				<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
					Community tags
						
				</p>
		
			<div id="columnmain" >
			
				<h2 class="green">
					<c:if test="${command.parentId == 0}">All tags used in this community</c:if>
					<c:if test="${command.parentId != 0}">	All tags applied to the selected item</c:if>											
				</h2>

				<br />

				<ul>
						
					<c:forEach var="tag" items="${command.tagList}">			
			
					<li class="tag-list">${tag.count}
					<a href="${communityEraContext.currentCommunityUrl}/show-tagged.do?selectedTag=${tag.tagText}">${tag.tagText}</a>
					</li>
					
					</c:forEach>
		
				</ul>
			</div>
			
			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>

		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
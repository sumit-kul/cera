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
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>${communityEraContext.currentCommunity.name}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="all-communities">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

				<div class="padding">
			
					<div id="main">
			
						<p id="breadcrumb">
							<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
							<a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Event Calendar</a> &gt;
						</p>
						
				<div id="columnmain">
				<ul id="forumnav">
					<li><form:backlink cssClass="backarrow"/></li>
				</ul>
				
				<dl>
					<dt>
					<c:if test="${command.new}">
						<img src="img/icon-newstar-large.gif" alt="" class="fright" />
					</c:if>
					${command.name}</dt>
					<dd><p>(from '${command.communityName}')</p></dd>
					<dd><p>Posted by ${command.userName} | <fmt:formatDate pattern="dd MMM yyyy" value="${command.created}"  /></p></dd>

					<dd><p>${command.displayBody}</p></dd>
					<dt class="subsub">Location</dt>
					<dd class="details"><span class="black">${command.location}</span></dd>

					<dt class="subsub">Date and time</dt>
					<dd class="details"><span class="black"><fmt:formatDate pattern="dd MMM yyyy (kk:mm)" value="${command.startDate}"  /> - <fmt:formatDate pattern="dd MMM yyyy (kk:mm)" value="${command.endDate}"  /></span></dd>

					<dt class="subsub">Further information</dt>
					<dd class="details">
					<c:if test='${command.weblink != ""}'>
							<p><a href="${command.weblink}">${command.weblink}</a></p>
					</c:if>
						Contact: ${command.contactName}<br />
						Tel: ${command.contactTel}<br />
					<c:if test='${command.contactEmail != ""}'>	
						Email: <a href="mailto:${command.contactEmail}">${command.contactEmail}</a>
					</c:if>	
					</dd>
				</dl>

			</div>

<%@ include file="/WEB-INF/jspf/tools.jspf" %>
		</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
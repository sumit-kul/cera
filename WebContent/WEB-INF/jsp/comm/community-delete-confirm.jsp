<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>Delete community</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="all-communities">
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
			<div id="sub">
				<div class="right_shadow">
					<div class="left_shadow">
				<h2><c:if test="${empty communityEraContext.h2Header}">${communityEraContext.currentCommunity.name} 
	<c:if test="${not empty communityEraContext.currentCommunity.name && fn:endsWith(communityEraContext.requestUri, 'Community')}">Community</c:if>
						</c:if>
						    <c:if test="${not empty communityEraContext.h2Header}">${communityEraContext.h2Header}</c:if>
							</h2>
							<br class="clearer"/>
						</div>
					</div>
				</div>
			
			
		<div class="padding">

		<div id="main">
				<p id="breadcrumb">
					You are here: <a href="${communityEraContext.contextUrl}/comm/a-z-index.do">All Communities</a> &gt; 
							Deleted community
				</p>	


			<div id="columnmain">

				
				<dl>
					<dt>Community deleted </dt>
					<dd><p>The community has been deleted. <a href="${communityEraContext.contextUrl}/comm/a-z-index.do">Click here to return to All Communities</a>.</p></dd>

				</dl>



			</div>

			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>
		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>${communityEraContext.currentCommunityName}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body>  

<form:form showFieldErrors="true" commandName="command" >
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		
		<p id="breadcrumb">
						
					<p id="breadcrumb">
						
						You are here: <a href="${communityEraContext.contextUrl}/welcome.do">Welcome</a> &gt; Feedback
						
					</p>
						
		</p>
		
				</div>
			
			<div class="bubblerow">
			   <div class="low">
			   
				<div class="bubble fullwidth">
					<h2 class="bubbleicon-searchforcommunity"><strong>Your Feedback</strong></h2>
					
					<div>
					<p>jhapak.com team welcomes any comments, suggestions and error-reports related to the site. Your feedback is important to us and helps us to develop and improve the site for the future. 
					Please note, When submitting your feedback please include the URL of any page you are referring to. This will help us to deal with your comments promptly.
					</p>

				<h2>jhapak.com</h2>

				<p>
				jhapak.com team welcomes any comments, suggestions and error-reports related to the site. Your feedback is important to us and helps us to develop and improve the site for the future. 
				Please email us at <a	href="mailto:support@jhapak.com">jhapak.com</a> 
				</p>


			</div>
		
			
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
		</form:form>
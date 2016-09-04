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
	<title>Membership request</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body>


	<div class="padding">

		<div id="main">
			
			<p id="breadcrumb">
				<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
				<a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do" >Membership requests</a> &gt; 
				Membership request
				
			</p>
					
			<div id="columnmain">


	<form:form showFieldErrors="true" action="${communityEraContext.currentCommunityUrl}/membershipDecision.do">
	
	<form:hidden path="selectedIdString" />
	<form:hidden path="actionType" />
	
	
	<p>If required, edit the message that will be sent to the selected user(s):</p>	
	<div id="columnmain" class="form">
				<h2 class="header">${command.actionType} user(s)</h2>
				
							
				
				<p>
				<form:textarea path="messageText"  fieldLabel="Message text" rows="16" cols="66"/>
				</p>
				
				<p>
					<input class="btnmain" 	type="image"  src="img/btn-green-go.gif"  alt="Continue"  />
				</p>
	</div>
		

	</form:form>

		</div>

		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
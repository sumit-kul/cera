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
	<title>Jhapak</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	<style type="text/css">
	
	#noMessage {
		border: none; 
		margin-left: 120px !important;
		width: auto;
		clear: both;
		float: left;	
		padding: 0px;	
	}

	#noMessageLabel {
		padding: 0px;
		margin-left: 15px !important;	
		float: left;
	}
	
	</style>
</head>

<body id="blog-entries">


		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
			
			<p id="breadcrumb">
				<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
				<a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do">Member List</a> &gt;
				${command.userName}
				
			</p>


	<form:form showFieldErrors="true">
	
	<form:hidden path="id" />
	
	<div id="columnmain" class="form">
				<h2 class="header">Expel user ${command.userName}</h2>
				<p>If required, edit the message that will be sent to the expelled user:</p>				
				<p>
				<form:textarea path="messageText" cssClass="almostfullwidth" fieldLabel="Message text" rows="14" cols="60"/><br/><br/>
				<form:checkbox path="noMessage" /><span id="noMessageLabel">Do not send a message</span>
				</p>
				<p>
					<input class="btnmain"  type="image" src="img/btn-green-go.gif" alt="Continue" 	/>
				</p>
	</div>
		

	</form:form>



			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>
		</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
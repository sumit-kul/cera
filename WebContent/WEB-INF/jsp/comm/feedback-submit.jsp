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
	<title>Feedback </title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body>

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		
		<p id="breadcrumb">
						
					<p id="breadcrumb">
						<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>  Feedback
						
					</p>
						
		</p>


			<div id="columnmain" class="form">

			<form:form showFieldErrors="true">
			<form:hidden path="id"/>
		
				
				<h2><span class="green">Your feedback</span></h2>
				
				<p>
Your feedback will be sent to the Community Admins 
</p>
				
				<div id="compose">
					<div class="padding">
	
						<p><form:textarea tabindex="1" fieldLabel="Feedback"  path="body" cols="60" rows="10" cssClass="tall"/></p>
												
						<p>
							<input class="btnmain" type="image" src="img/btn-green-submitfeedback.gif" alt="Submit feedback" />
						</p>
					</div>
				</div>

			</form:form>
			
			</div><!-- columnmain -->

			<div id="columnright">
				
			</div>

			</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
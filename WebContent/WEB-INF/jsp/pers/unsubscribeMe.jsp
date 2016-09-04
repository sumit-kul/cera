<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${communityEraContext.currentUser.fullName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak - Register</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
		 .h2style {
		 	font-family: TimesNewRoman,Times New Roman,Times,Baskerville,Georgia,serif;
			font-size: 38px;
			font-style: normal;
			font-variant: bold;
			font-weight: 2000;
			line-height: 26.4px;
		 }
		</style>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="welcomeSignupright">
				<h2 style="color: #66799f;" class='h2style'>THANK YOU</h2>
									
				<p style="margin-top: 22px;">
				You have successfully removed from Jhapak
				<br />
				You will no longer hear from us 
				</p>
				<br/>
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
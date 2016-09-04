<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="description" content="Jhapak, page not found, broken page" />
		<meta name="keywords" content="Jhapak, page not found, broken page" />
		<title>Jhapak - Page not found</title>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.brkPage {
			font-size: 20px;
			text-decoration: none;
			font-family: Clarendon;
			}
		</style>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" style="min-height: 395px;">
			<div class="welcomeSignupright" style="margin-top: 12px;">
				<div style="width: 60%; float: left; padding: 80px 0px 0px 50px;">
					<h1 style="color: #66799f; text-align: left; font-weight: bold; font-size: 56px; line-height: 0.8; font-family: Clarendon;">Oops!</h1>
					<h1 style="color: #66799f; text-align: left; line-height: 0.8; font-family: Clarendon;">It's broken.</h1>
					<br />
					<p class="brkPage">
					The link you followed may be broken, or the page may have been removed.
					</p>
				</div>
				<div class="pagNtFd" ></div>
				
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
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
		<meta name="author" content="Jhapak">
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
			line-height: 1.2em;
		 }
		 
		 #container {
		 	background-image:url(${communityEraContext.contextUrl}/images/rConfirm_1.jpg);
		 	margin: 0px;
		 	width: 100%;
		 	max-width: 100%;
		 	background-size: cover;
			background-position: top center !important;
			background-repeat: no-repeat !important;
		 }
		 
		 .carouselCaption {
				box-shadow: 0px 2px 2px 0px rgba(50,58,59,0.15);
				margin-bottom: 1em;
				border-radius: 4px;
				background-color: rgba(255,255,255,0.9);
				position: relative;
				margin-left: auto !important;
				margin-right: auto !important;
				width: 50%;
				top: 4em;
				padding: 1.125em 1.5em;
				background: white;
				-webkit-transition: all 0.2s ease-in-out;
				transition: all 0.2s ease-in-out;
				overflow: hidden;
				opacity: 0.75;
			}
			
			.carouselCaption p {
				width: 60%;
				text-align: center;
				color: #323a3b;
				margin: 0px auto;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
			    font-weight: 300;
			    font-size: 1.27rem;
			    line-height: 2;
			}
			
			.carouselCaption a {
				color: rgb(102, 121, 159);
			}
			
			.carouselCaption a: hover {
				color: #323a3b;
			}
			
			@media only screen and 	(max-width: 1200px) {
				.carouselCaption {
					width: 60%;
				}
				
				.carouselCaption p {
					width: 70%;
				}
			}
			
			@media only screen and (min-width: 768px) and (max-width: 940px) {
				.carouselCaption {
					width: 80%;
				}
				
				.carouselCaption p {
					width: 80%;
				}
			}
			
			@media screen and (min-width: 481px) and (max-width: 767px) {
				.carouselCaption {
					width: 80%;
					top: 2em;
				}
				
				.carouselCaption p {
					width: 80%;
				}
			}
			
			@media only screen and (max-width: 480px) {
				.carouselCaption {
					width: 70%;
					top: 2em;
				}
				
				.carouselCaption h2 {
					top: 2em;
					font-size: 26px;
					text-align: center;
				}
				
				.carouselCaption p {
					width: 80%;
					font-size: 15px;
				}
			}
			
		</style>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="carouselCaption">
				<h2 style="color: #66799f;" class='h2style'>Congratulations - your registration is complete</h2>
									
				<p style="margin-top: 22px;">
				You're almost done! We've sent an activation mail. Please follow the instructions in the email to activate your account.
				<br />
				<strong>If it doesn't arrive, check your spam folder</strong>, or try to <a href="${communityEraContext.contextUrl}/pers/validateResend.do">send another activation mail</a>. 
				</p>
				<br/>
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

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
		
		<style type="text/css">
	    	#communityEra {
			    background: #f5f6f6;
				position: absolute;
				height: 100%;
				width: 100%;
				top: 0;
				left: 0;
				-webkit-font-smoothing: antialiased;
				display: -webkit-box;
				display: -webkit-flex;
				display: -ms-flexbox;
				display: flex;
				-webkit-box-orient: vertical;
				-webkit-box-direction: normal;
				-webkit-flex-direction: column;
				-ms-flex-direction: column;
				flex-direction: column;
				min-height: 100vh;
				color: #323a3b;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				font-weight: 300;
				font-size: 1rem;
				line-height: 1.4;
				vertical-align: baseline;
				margin: 0;
				padding: 0;
				border: 0;
				-moz-box-sizing: border-box;
				box-sizing: border-box;
			}
			
			.banner {
			    padding-bottom: 85px;
			}
			
			.background[data-loaded="true"] {
			    opacity: 1;
			}
			
			.background {
			    background-position: center center;
			    background-repeat: no-repeat;
			    background-size: cover;
			    position: fixed;
				top: 0;
				left: 0;
				bottom: 0;
				right: 0;
				-webkit-transition: opacity .3s ease-in-out;
				transition: opacity .3s ease-in-out;
			}
			
			.carouselCaption {
				box-shadow: 0px 2px 2px 0px rgba(50,58,59,0.15);
				margin-bottom: 1em;
				border-radius: 4px;
				background-color: rgba(255,255,255,0.9);
				position: relative;
				margin-left: auto !important;
				margin-right: auto !important;
				width: 40%;
				top: 2em;
				padding: 1.125em 1.5em;
				background: white;
				-webkit-transition: all 0.2s ease-in-out;
				transition: all 0.2s ease-in-out;
				overflow: hidden;
				opacity: 0.9;
			}
			
			.carouselCaption .lsLogo {
				display: -webkit-box;
				display: -webkit-flex;
				display: -ms-flexbox;
				display: flex;
				margin: 0 auto 0.7em;
				width: 48px;
			    height: 48px;
			}
			
			.carouselCaption .entryField {
				margin-bottom: 10px;
				position: relative;
				width: 100%;
			}
			
			input[type="text"], input[type="password"] {
				padding: 0.15em 1em;
				vertical-align: middle;
				min-height: 34px;
				font-weight: normal;
				color: #828a8b;
				border: 1px solid #cfd2d3;
				font-size: 1em;
				border-radius: 4px;
				-webkit-transition-property: border-color, color, box-shadow;
				transition-property: border-color, color, box-shadow;
				-webkit-transition-duration: 0.15s;
				transition-duration: 0.15s;
				max-width: 100%;
				outline: none;
				display: block;
				width: 94%;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				line-height: 1.4;
			}

			.btn-block {
			    width: 80%;
			    text-align: center;
			}

			a.btnSubmit {
				display: inline-block;
				color: white;
				border: 1px solid #005983;
				background-color: #005983;
				text-decoration: none;
				-webkit-appearance: none;
				text-align: center;
				padding: 0.5em 1.30em 0.5em;
				border-radius: 4px;
				font-size: 14px;
				font-weight: normal;
				outline: none;
				cursor: pointer;
				-webkit-transition-property: background, color, border-color, box-shadow;
				transition-property: background, color, border-color, box-shadow;
				-webkit-transition-timing-function: ease;
				transition-timing-function: ease;
				-webkit-transition-duration: 0.16s;
				transition-duration: 0.16s;
				line-height: 1.4;
				-webkit-touch-callout: none;
				-webkit-user-select: none;
				-moz-user-select: none;
				-ms-user-select: none;
				user-select: none;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				box-sizing: border-box;
			}
			
			a.btnRegister {
				padding-right: 25%;
				padding-left: 25%;
				margin-top: 40px;
			}
			 	
			.forgotp a.arrow {
			    padding: 1px 10px 5px 15px;
			}
			
			.forgotp a {
				font-size: 14px;
			    color: #158aa2;
				text-decoration: none;
				cursor: pointer;
				font-weight: normal;
				float: right;
			}
			
			.signText {
				font-size: 1rem;
				text-align: center;
				box-sizing: border-box;
			}
			
			.signText a {
				color: #005983;
				text-decoration: none;
				cursor: pointer;
				font-weight: normal;
			}
			
			.mAddress {
				width: 60%
			}
			
			.mAddress2 {
				width: 40%; 
				margin-top: 22px;
			}
			
			.passallow {
				width: 60%; 
			}
			
			.signText a:hover, .signText a:focus {
			    color: #004c5a;
			}
			
			.recapt {
				width: 60%;
			}
			
			@media only screen and (min-width: 769px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/signUp_1.jpg);
				}
			}
			
			@media only screen and (max-width: 1200px) {
				.carouselCaption {
					width: 50%
				}
			}
			
			@media only screen and (min-width: 768px) and (max-width: 940px) {
				.carouselCaption {
					width: 60%
				}
			}
			
			@media only screen and (max-width: 767px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/signUp_2.jpg);
				}
				
				.carouselCaption {
					width: 78%;
					top: 1em;
					padding: 1em 1.5em;
				}
				
    			.forgotp a {
			    	font-size: 12px;
			    }
			    
			    .carouselCaption .lsLogo {
			    	width: 30px;
			    	height: 30px;
			    }
			    				
				.mAddress, .mAddress2, .recapt, .passallow {
					width: 100%;
					margin-top: 0px;
				}
				
				input[type="text"], input[type="password"] {
					min-height: 22px;
				}
				
				a.btnSubmit {
					font-size: 12px;
					line-height: 1.2;
					margin-top: 6px;
				}
				
				span.suggest {
				    font-size: 12px;
				    padding: 2px 0px;
			    }
			    
			    .carouselCaption .entryField {
			    	margin-bottom: 4px;
			    }
			    
			    .signText {
				    font-size: 13px;
				}
				
				form label {
    				font-size: 12px;
				}
	    </style>
		
		<script type="text/javascript">
			function checkAvailability(){
				email = document.getElementById('emailAddress').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/checkAvailability.ajx?email="+email,success:function(result){
					$("#checkAvail").html(result);
					// Hide message
			        $("#waitMessage").hide();
				    },
				 	// What to do before starting
			         beforeSend: function () {
				    	$("#checkAvail").html('');
			             $("#waitMessage").show();
			         } 

		    	  });
			}
	
			function registerSubmit(){
				document.registerMeForm.action = "${communityEraContext.contextUrl}/pers/registerMe.do";
				document.registerMeForm.submit();
			}
		</script>
		<script src="https://www.google.com/recaptcha/api.js" async defer></script>
	</head>
	
	<body id="communityEra">
		<div class="banner" >
			<div class="background"  >
				<div class="carouselCaption">
					<a href="${communityEraContext.contextUrl}/welcomeToCommunities.do">
						<img src="images/link_Image.png" style="image-rendering: pixelated;" class="lsLogo" />
					</a>
					<div class="welcomeSignupright">
						<form:form showFieldErrors="true" method="post" name="registerMeForm">		
							<div style="margin-top:6px;">
								<div class="left entryField" >
									<label for="firstName">First Name:</label><br />
									<form:input id="firstName" path="firstName" />
								</div>
								<div class="left entryField" >
									<label for="lastName">Last Name:</label><br />
									<form:input id="lastName" path="lastName" />
								</div>
								<div class="left entryField" >
									<div class="left mAddress" >
										<label for="emailAddress">E-mail Address:</label><br />
										<form:input id="emailAddress" path="emailAddress" />
									</div>
									<div class="right mAddress2" >
										<a href="javascript:void(0);" onclick="checkAvailability();" class="btnSubmit" style="float: right; ">Check Availability</a>
											<div id="waitMessage" style="display: none;">
												<div class="cssload-square" >
													<div class="cssload-square-part cssload-square-green" ></div>
													<div class="cssload-square-part cssload-square-pink" ></div>
													<div class="cssload-square-blend" ></div>
												</div>
											</div>
									</div>
									<div class="entry" id="checkAvail"></div>
								</div>								
								<div class="left entryField" style="margin-bottom: 0px;">
									<div class="left passallow" >
										<label for="password">Password:</label><br />
										<form:password id="password" path="password" />
									</div>
									<div class="right passallow" >
										<span class="suggest">6-12 characters allowed</span>
									</div>
								</div>
								<div class="left entryField" >
									<div class="left recapt" >
										<form:input id="reCaptcha"  path="reCaptcha" cssStyle="visibility: hidden; margin-top: -20px;" />
										<div class="g-recaptcha" data-sitekey="6LfdEygTAAAAAK3StzZeWduvY_ThWFfKGyfMy8HQ"></div>
									</div>
									<div class="right" >
										<a href="javascript:void(0);" onclick="registerSubmit();" class="btnSubmit btnRegister" style="float: right;">Register</a>
									</div>
								</div>
								<div class="left entryField" style="text-align: center;">
									<span class="signText">By signing up you agree to our <a href="/terms" target="_blank">Terms of Use</a> and <a href="/privacy" target="_blank">Privacy Policy</a></span>
								</div>
								<div class="left entryField" >
									<div class="forgotp" style="float: left; margin: 4px 8px; width: 100%;">
										<a href="${communityEraContext.contextUrl}/pers/fPassword.do" class="arrow">Forgot password?</a>
										<a href="${communityEraContext.contextUrl}/login.do" class="arrow" style="float: left">Log in</a>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
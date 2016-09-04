<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />

	  	<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak - Create a new password</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript">
			function registerCNPSubmit(){
				document.cnpForm.action = "${communityEraContext.contextUrl}/pers/createNewPassword.do";
				document.cnpForm.submit();
			}
		</script>
		
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="welcomeSignupright">
				<div class="innerBlock">
					<h1 style="padding-bottom:10px;">Create a new password</h1>
					<form:form showFieldErrors="true" name="cnpForm">
					<input type="hidden" id="emailAddress" name="emailAddress" value="${command.emailAddress}" />
					<input type="hidden" id="changeKey" name="changeKey" value="${command.changeKey}" />
						<div>
							Create a new password you'll remember easily!
						</div>
						<br />
						<div>
							<label for="password">Password:</label><br />
							<form:password id="password" path="password" />
						</div>
						<div class="right" style="width:45%;margin-top:-22px;">
							<span class="suggest">6-12 characters allowed</span>
						</div>
						<br /><br />
								
						<div>
							<label for="confirmPassword">Confirm Password:</label><br />
							<form:password id="confirmPassword" path="confirmPassword" />
						</div>
						
						<br />
						<div class="left">
							<a href="javascript:void(0);" onclick="registerCNPSubmit();" class="btnmain" >Change Password</a>
						</div>
						<br /><br />
					</form:form>
				</div> 
			</div> 
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html> 
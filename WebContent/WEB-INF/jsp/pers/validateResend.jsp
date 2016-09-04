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
	  	<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak - Validate account</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript">
			function registerSubmit(){
				document.passReminderForm.action = "${communityEraContext.contextUrl}/pers/validateResend.do";
				document.passReminderForm.submit();
			}
		</script>
		<script src="https://www.google.com/recaptcha/api.js" async defer></script>
		
		<style type="text/css">
			.entryField {
					margin-bottom: 10px;
					position: relative;
					width: 100%;
				}
		</style>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="welcomeSignupright">
				<div class="innerBlock">
					<h1 style="padding-bottom:10px;">Re-send activation message</h1>
					<form:form showFieldErrors="true" name="passReminderForm">
						<div>
							Enter your email address below and we'll send you activation link. 
							<br />Make sure you enter the same email address you used to create your profile.
						</div>
						<br />
						<div>
							<label for="emailAddress">E-mail Address:</label><br />
							<form:input id="emailAddress" path="emailAddress" />
						</div>
						<br />
								
						<div class="left recapt entryField" >
							<form:input id="reCaptcha"  path="reCaptcha" cssStyle="visibility: hidden; margin-top: -20px;" />
							<div class="g-recaptcha" data-sitekey="6LfdEygTAAAAAK3StzZeWduvY_ThWFfKGyfMy8HQ"></div>
						</div>
						
						<div class="left entryField" style="float: left; width: 100%">
							<a href="javascript:void(0);" onclick="registerSubmit();" class="btnmain" style="line-height: 24px; height: 26px; ">Submit</a>
						</div>
						
					</form:form>
				</div><!-- /#innerBlock --> 
			</div><!-- /#welcomeSignupright --> 
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<title>Jhapak - Change Password</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
	    <%@ include file="/WEB-INF/jspf/header2.jspf" %>
	    <link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
	   	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	   	
	    <script type="text/javascript">
			function loginSubmit(){
				document.loginMeForm.action = "${communityEraContext.contextUrl}/jlogin.do";
				document.loginMeForm.submit();
			}
		</script>
	</head>

	<body id="communityEra">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
	
			<div id="container" style="min-height:300px;">
				<div class="welcomeSignupright" style="padding: 10px 10px 40px; margin: 0px 0px 26px;">
					<div class="innerBlock">
						<h1 style="padding-bottom:10px;">Create a new password</h1>
						<form:form showFieldErrors="true" method="post" name="loginMeForm">	
							<c:if test="${not empty command.emailAddress}">	<c:set var="uname" value="${command.emailAddress}" /></c:if>
							<c:if test="${empty command.emailAddress}">	<c:set var="uname" value="" /></c:if>
							<c:if test="${not empty command.password}">	<c:set var="pword" value="${command.password}" /></c:if>
							<c:if test="${empty command.password}">	<c:set var="pword" value="" /></c:if>
	
							<div style="margin-top:6px;">
								<div class="forgotp" style="float:left;width:100%;margin-bottom:20px;" >
									<c:choose>
										<c:when test="${not empty cecLoginAttribute.loginMessage}">
												<span class="errorText">${cecLoginAttribute.loginMessage}</span><br/>
										</c:when>
										<c:when test="${not empty command.loginMessage}">
												<span class="errorText">${command.loginMessage}</span><br/>
										</c:when>
										<c:when test="${not empty cecFMyPassword.loginMessage}">
											<div class="passMess"> 
												<img src="images/passSen.png" class="success" />
												<p class="pmessage">${cecFMyPassword.loginMessage}</p> 
											</div>
											<br />Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).
										</c:when>
										<c:otherwise>
											<br />Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).
										</c:otherwise>
									</c:choose>
								</div>
								<br />
								<div class="left" style="width:100%;">
									<label  for="email" style="text-align: left;">Email</label> <br />
									<input style="width:70%;" type="text" value="${uname}" id="email" name="j_username" class="text"/><br/>
								</div>
								<br /> 
								<br /> 
								<div class="left" style="width:100%;margin:10px 0px 20px 0px;">
									<label for="password">Password</label> <br />
									<input style="width:70%;" type="password" id="password" name="j_password" value="${pword}" class="text" />
								</div>
							
								<c:if test="${not empty cecLoginAttribute.loginMessage || not empty command.loginMessage}">
									<p style="margin-bottom:20px;">Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).</p>
								</c:if>
								
								<div class="left" style="">
									<a href="javascript:void(0);" onclick="loginSubmit();" class="btnmain" >Log In</a>
									<input type="hidden" name="_acegi_security_remember_me" value="1"/>
								</div>
								<div class="forgotp">
									<a href="${communityEraContext.contextUrl}/pers/fPassword.do" class="arrow">Forgotten your password?</a>
								</div>
						</div>
					</form:form>
					</div>
				</div>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
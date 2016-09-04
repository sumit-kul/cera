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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<title>Jhapak - Feedback </title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
	    <%@ include file="/WEB-INF/jspf/header2.jspf" %>
	    <link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
	    <link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	    
	    <style type="">
		    #container .welcomeSignupright .innerBlock form input {
			    float: left;
			   	width: 86%;
			}
			
			.innerBlockLeft {
				color: #666;
				width: 27%;
				float: left; 
				padding: 40px 0px 20px 20px; 
				min-height: 258px;
				font-family: "Open Sans","HelveticaNeue","Helvetica Neue",Helvetica,Arial,sans-serif;
				font-size: 16px !important;
			}
			
			.innerBlockLeft img {
				width: 220px;
				margin: 23px;
			}
			
			.innerBlock p {
				color: #666;
				font-family: "Open Sans","HelveticaNeue","Helvetica Neue",Helvetica,Arial,sans-serif;
				font-size: 16px !important;
				margin-top: 50px;
			}
		</style>
		
	</head>

	<body id="communityEra">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
	
			<div id="container" >
				<div class="welcomeSignupright" style="padding: 0px 10px 20px 10px; margin: 10px 0px 20px auto;">
					<div style="margin: 20px;">
						<h1 style="">Message Received</h1>
					</div>
					<div class="innerBlock" style="margin: unset; width: 70%; float: left; border-right: 1px solid #E2E2E2; min-height: 358px;">
						<p style="text-align: center;">Thank you for taking the time to provide feedback. We'll get back to you ASAP.</p>
					</div>
					<div class="innerBlockLeft" >
						<p style="text-align: center;">We welcome any comments, suggestions, ideas or feature requests. Your feedback is important to us and helps us to improve our website.</p>
						<p style="padding: 10px 0px;"><i class="fa fa-envelope" style="padding-right: 2px;"></i><strong>Email:</strong> <a href="mailto:support@jhapak.com" style="color: #66799F;">support@jhapak.com</a></p>
						<img src='images/link_Image.png'/>
						<p style="font-size: 18px; padding-left: 44px;">[Team Jhapak]</p>
					</div>
				</div>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
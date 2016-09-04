<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${communityEraContext.currentUser.fullName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>Personal Details</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="my-profile">
<form:form mode="${command.mode}" multipart="true" showFieldErrors="true">
<form:hidden path="id" />
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		<p id="breadcrumb">
					<a href="${communityEraContext.contextUrl}/pers/my-profile.do">Personal Details</a> &gt; Remove My Registration
						
				</p>	


			<div id="columnmain" class="form">
			
				<h1 class="blue"> Remove my registration </h1>
					<div id="compose">
				
					<div class="padding">
						<h2>Warning</h2>
						
						<p class="errorText">This action will remove you from jhapak.com.</p>
						<p>Your existing <a href="${communityEraContext.contextUrl}/pers/communities.do?pageSize=12">community memberships</a> will all be revoked</p>
						<p>You will no longer receive any email alerts.</p>
					</div>
				</div><!-- /#compose  -->

				<div class="hr"><hr /></div>
	
				<p class="mtop10">    <%--  class gives a margin of 10 px to create some white space --%>
					<input class="fleft button" type="image" src="img/btn-blue-cancel.gif" alt="Cancel" name="_cancel" value="1"	 /> 
					<input class="fright button" 	type="image" src="img/btn-blue-go.gif" alt="Go" name="_finish" 	value="1" />
				</p>

			</div>
</form:form>
			
		<%@ include file="/WEB-INF/jspf/light-right-col.jspf" %>
		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
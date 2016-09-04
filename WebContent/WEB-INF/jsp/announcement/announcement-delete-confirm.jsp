<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>Admin Announcements</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="canonical" href="${communityEraContext.requestUrl}" />
	<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
	<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	<style type="text/css"> 	td.unactivated { 	background-color: #red; color: red; 	} 	</style>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	
</head>

<body id="my-profile">

	<%@ include file="/WEB-INF/jspf/open-container2.jspf" %>
	<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
	<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">

				<p id="breadcrumb">						
						You are here: 	<a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a> &gt;
						<a href="${communityEraContext.contextUrl}/announcement/announcement-index.do">Announcements</a> &gt; ${command.subject}
					</p>


			<div id="columnmain" class="forum">
				<form:form showFieldErrors="true" commandName="command" multipart="true">
				<ul id="forumnav">
					<li><a href="${communityEraContext.contextUrl}/announcement/announcement-index.do" class="backarrow">Back to announcements</a></li>
				</ul>
				
				<dl>
					<dt>Announcement deleted</dt>
					<dd><p>The announcement has been deleted. <a href="${communityEraContext.contextUrl}/announcement/announcement-index.do">Click here to return to the announcements</a>.</p></dd>

				</dl>
				</form:form>


			</div>

			
		<div id="columnright">
						
			<div class="padding">
		<h3 class="blue">Tools</h3>
		<ul class="tools">
		   
		</ul>
		
			</div> 
			
		</div> 

		</div>
		
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
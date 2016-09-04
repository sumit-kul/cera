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
	<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
	<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<meta name="description" content="How to make admin confirm in Jhapak" />
	<meta name="keywords" content="Jhapak, admin, confirm" />
	<title>Jhapak - Make Admin Confirm</title>
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="canonical" href="${communityEraContext.requestUrl}" />
	<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
	<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>
 
<body id="my-profile">

<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">
		<div id="main">
				
				<p id="breadcrumb">
						
			You are here: <a href="${communityEraContext.contextUrl}/welcome.do">System Administration</a> &gt; 
								<a href="${communityEraContext.contextUrl}/admin/user-index.do?order=surname" >User List</a> &gt;
								<a href="${communityEraContext.contextUrl}/admin/user-details.do?id=${command.id}" >${command.fullName}</a> &gt
								 Make ${command.fullName} system administrator
						
		 		</p>

			<div id="columnmain" class="forum">
				
				<dl>
					<dt>System Administration rights have been given</dt>
					<dd><p>System Administration rights have been given to ${command.fullName}.<br /> 
					Click here to return to <a href="${communityEraContext.contextUrl}/admin/user-details.do?id=${command.id}" >${command.fullName}</a>.</p></dd>

				</dl>

			</div>

			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>

		</div>
		
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Jhapak - Mail Messages</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="breadcrum">
					<ul>
						<li>You are here:</li>
						<li><a href="${communityEraContext.contextUrl}">Welcome</a></li>
						<li><a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a></li>
						<li class="uppercase">Configure email message</li>
					</ul>
				</div>	
				
				<div class="commSection">
					<div class="communities">
						<h2 style="margin-top:-10px;">Mail Messages</h2>
					</div>
					
					<div class="inputForm">
				 		<div class="innerBlock">
					
						<table class="type1" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th>Name</th>
								<th>Description</th>
							</tr>
					
							<c:forEach items="${command.scrollerPage}" var="row">	
								<tr>
									<td class="nowrap">
										<c:set var="firstLetter" value="${fn:toUpperCase(fn:substring(row.name,0,1))}" />
										<c:set var="remainder" value="${fn:substringAfter(row.name,fn:substring(row.name,0,1))}"/>
										<a href="${communityEraContext.contextUrl}/param/mail-message-details.do?name=${row.name}" >${firstLetter}${fn:replace(remainder, '-', ' ')}</a>
									</td>
			
									<td>
										${row.description}
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				</div> 
				</div> 
			
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
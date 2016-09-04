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
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<%@ include file="/WEB-INF/jspf/header2.jspf" %>
	<title>Join a community</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body>

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>


	<div class="padding">

		<div id="main">

						<p id="breadcrumb">
						
						You are here: <a href="${communityEraContext.contextUrl}/comm/a-z-index.do">All Communities</a> &gt; 
							<c:forEach items="${communityEraContext.currentCommunity.ancestors}" var="ancestor">
								<a href="${communityEraContext.contextUrl}/cid/${ancestor.id}/home.do" >${ancestor.name}</a> &gt;
							</c:forEach>		
							 ${communityEraContext.currentCommunity.name}
							Join ${command.community.name} 
						
						</p>


			<div id="columnmain">
			<form:form showFieldErrors="true" multipart="true">
				<form:errors cssClass="errorText" /> 
			<h1 class="nomargin">${command.community.name} </h1>
			
			
				<ul id="forumnav">
					<li>${command.community.description}</li>
				</ul>
				
	
				<div class="bubblerow">
					<div class="bubble bubblegreen leftbubble fullwidth">
						<h2><strong>Invite a friend to join this community</strong></h2>
						<div>

							
							<p>To invite people to join this community, type their email addresses in here</p>
							<p>
								<form:textarea path="optionalComment" rows="5" cols="65" 
								fieldLabel=""/>
							</p>
							

						 
						  <c:if test="${not command.approvalRequired}">
							<p>To join this community click the Go button below. 
							You will be added to the community member list and will immediately have access to the community content.
							</p>
							
							
							
							
						</c:if>
							<p>
								<form:hidden path="redirect" />
								<form:hidden path="referer" />
								<form:hidden path="approvalRequired" />
								<input type="hidden" name="id" value="${command.community.id}" /> 
								<input class="inlinebutton" type="image" name="go" src="img/btn-green-go.gif" alt="Go" />
							</p>
						</div>
					</div>

				</div>
				
</form:form>
				
			</div>

	<div id="columnright">

			
		</div> 

		</div> 
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
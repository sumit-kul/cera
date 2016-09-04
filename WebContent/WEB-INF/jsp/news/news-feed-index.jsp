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
	<title>RSS Feed</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
</head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
<body id="my-profile">

<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
			
<div id="sectionnav">
 	<h1>Community RSS Feeds</h1>
</div>


			<div id="columnmain" class="forum">
				
				
				<c:set var="currentCommunity" value =''/>
				
				
				<c:forEach items="${command.scrollerPage}" var="row">	
									
					<c:if test="${row.aggregatorId !=currentCommunity}">
						<c:if test="${currentCommunity !=''}">
							</table>
							
						</c:if>

						<h2>${row.newsFeed.communityName}</h2>
		
		
						<%--- TABLE -----------------------------------------------------------------------------------------------------------------------------------%>		
				
						<table class="type1" cellpadding="0" cellspacing="0" border="0" summary="RSS feeds in ${row.newsFeed.communityName}">
						<%-- Header row --%>
						<tr>
							<th>RSS feeds for this community</th>
							
							<th>Last update</th>
							
						</tr>

					</c:if>

					<c:if test="${row.oddRow}">
					<c:set var="trclass" value='' />
					</c:if>
					<c:if test="${row.evenRow}">
					<c:set var="trclass" value='class="alt"' />
					</c:if>
				
					<tr ${trclass}>

						<td>
							<a href="${row.url}">${row.name}</a>
						</td>

						<td class="nowrap">
					
						<fmt:formatDate value="${row.datePublished}" pattern="dd MMM yyyy  kk:mm" />
						
						</td>

					</tr>
								
					<c:set var="currentCommunity" value='${row.aggregatorId}' />		
			
				</c:forEach>
				
		</table>


<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" />

</div>

	<div id="columnright">

	</div>


		</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>		

	</div>

</div>

</body>
</html>
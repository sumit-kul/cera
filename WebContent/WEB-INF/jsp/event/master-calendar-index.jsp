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
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="Jhapak">
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	<style type="text/css"> 
	
		/* Override this style for this page only as width needs to be larger */
		.truncLineWide {
			white-space:nowrap; 	height: 16px; 	width: 450px; overflow: hidden; 	text-overflow: ellipsis;
		}				
		
	</style>
	<%@ include file="/WEB-INF/jspf/header2.jspf" %> 
	<title>${communityEraContext.currentCommunity.name}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	
	<script type="text/javascript">
		function submitRollup() {			
			document.forms["rollup-form"].submit();
		}
	</script>
	
</head>



<body id="all-communities">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

				<div class="padding">
			
					<div id="main">
			
						<p id="breadcrumb">
							<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %> 
							<a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Event Calendar</a> &gt;
						</p>
						
				<div id="columnmain">
				<ul id="forumnav">
					<li><strong>Sort by</strong> </li>
					<li>
						<c:if test='${command.order != "date"}'>
							<a href="${communityEraContext.contextUrl}/master-calendar/index.do?order=date">
						</c:if>
								Upcoming events
						<c:if test='${command.order != "date"}'>
							</a>
						</c:if>	
					</li>
					<li class="divider">|</li>
					
					<li>
						<c:if test='${command.order != "past"}'>
							<a href="${communityEraContext.contextUrl}/master-calendar/index.do?order=past">
						</c:if>	
							Past events
						<c:if test='${command.order != "past"}'>
							</a>
						</c:if>	
					</li>
					
				</ul>
				
				<form:form action="${communityEraContext.requestUrl}" method="get">
					<div class="formelements">
						<form:dropdown path="pageSize">
							<form:option value="5" label="View 5 per page"/>
							<form:option value="8" label="View 8 per page"/>
							<form:option value="10" label="View 10 per page"/>
							<form:option value="15" label="View 15 per page"/>
						</form:dropdown>
						<input type="hidden" name="page" value="${command.page}" /> 
						<input class="inlinebutton" type="image" src="img/btn-green-go.gif" alt="Go" /> 
					</div>
				 </form:form>

				<p>Selected events from all communities.</p>

<form:form method="post" action="${communityEraContext.requestUrl}" name="rollup-form">		
			<%-- Display the event calendar in a scroller, categorized by month --%>	
			<table class="type1" summary="Events">

			<c:set var="monthyear" value='' />
			
					
			<c:forEach items="${command.scrollerPage}" var="row">	

					<c:set target="${command}" property="curId" value="${row.calendarId}" />

					<c:if test="${row.monthYear != monthyear}">
						<c:set var="monthyear" value='${row.monthYear}' />						
						<tr>
							<th>
								<span class="fleft"><fmt:formatDate pattern="MMMM yyyy" value="${row.startDate}"  /></span> 
								<span class="fright"></span>
							</th>
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
							<c:if test="${row.new}">
								<img src="img/icon-newstar-large.gif" alt="New" class="fright" />
							</c:if>
							<a href="${communityEraContext.contextUrl}/master-calendar/display.do?backlink=ref&amp;id=${row.id}">${row.name}</a><br />
							(from '${command.communityForEvent}')<br/>
							<span class="detailsprofile">
							<fmt:formatDate pattern="dd MMM yyyy" value="${row.startDate}"  /> | ${row.location} (${row.region})</span><br />
							<div class="detailsprofile truncLineWide" title="${row.description}">${row.description}</div>
						</td>
					</tr>
					
		</c:forEach>

				</table>

</form:form>

<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" />

			</div>

	<div id="columnright">
							
  	</div>
		</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
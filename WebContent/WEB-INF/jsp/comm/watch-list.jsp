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
	<title>My Alerts</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="homepage">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		
	

  
	<div class="padding">
		<form:form action="${communityEraContext.requestUrl}" method="post" commandName="command">

		<div id="main">
		
			<%@ include file="/WEB-INF/jspf/community-breadcrumb.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

			<div id="columnmain" class="forum">
				<c:if test="${command.rowCount==0}">		
				  You currently have no email alerts set. Navigate to the appropriate section of the site where you want to monitor for changes
			  </c:if>
			  <c:if test="${command.rowCount>0}">		
			  	<p>!!!Select the frequency of your email alerts by choosing 'immediate', 'daily' or 'weekly' from the drop down list below. Then click 'save settings' in the right hand linkBuilder bar.</p>
				<p><strong>You have opted to receive email alerts on the community items listed below:</strong></p>
			  </c:if>
			<form:errors path="*"  />
			
			<%-- 
			
					  Display the user's monitored items, categorized by community, one 4 column table per community. 
			          For each Community, we want to output :
			          
			          1.	<h2> heading
			          2.	table with variable number of rows - alternating with grey / white background
			          3.	update button

			    --%>
			          

				<c:set var="current" value =''/>
				
				<c:forEach items="${command.scrollerPage}" var="row">	
									
					<c:if test="${row.communityName !=current}">
						<c:if test="${current !=''}">
							</table>
							<div class="formelements">
							</div>
						</c:if>

						<h2 class="blue">${row.communityName}</h2>
				
						<table class="type1" summary="Monitored items in ${row.communityName}">
						<%-- Header row --%>
						<tr>
							<th colspan="2">Description</th>
							<th>Type</th>
							<th>Last update</th>
							<th>Frequency</th>
						</tr>

					</c:if>

	<c:if test="${row.oddRow}"> <c:set var="trclass" value='' /> 		</c:if>
		<c:if test="${row.evenRow}">	<c:set var="trclass" value='class="alt"' />		</c:if>
				
				<tr ${trclass}>
				<%-- check box with the subscription id --%>
						<td class="checkonly">
							<input type="checkbox" class="check" name="selectedIds" value="${row.id}"/>
						</td>
				
					
						<td>
							<a href="${row.itemUrl}">${row.itemName}</a>
						</td><!-- note: remove class "bulletblue" from existing template to remove icon -->
						
						<td class="nowrap">${row.itemType}</td>
						
						<td class="nowrap">
					
						<fmt:formatDate value="${row.itemLastUpdateDate}" pattern="dd MMM yyyy" />
						
						</td>
						
						<td class="update">
				
						<form:hidden path="subscriptionIds[${row.resultSetIndex - 1}]" /> 
						<form:dropdown path="subscriptionFreqs[${row.resultSetIndex - 1}]"  > 
							<form:option value="0" label="Immediate"/>
							<form:option value="1" label="Daily"/>
							<form:option value="2" label="Weekly"/>
						</form:dropdown>

						</td>
					</tr>
								
					<c:set var="current" value='${row.communityName}' />		
			
				</c:forEach>
				
		</table>
					<div class="formelements">
						<p></p>
					</div>

		<form:paginator id="paginator" scrollerPage="${command.scrollerPage}" />
		
			</div>
		
		<div id="columnright">
			<h3 class="blue">Tools</h3>
			 <c:if test="${command.rowCount>0}">	
			<div style="background: url('../img/icon-exclamation.gif') no-repeat left top; padding: 0px 0px 0px 36px " >
			<input  type="submit" name="buttonUpdateOptions" value="Save settings" style="font-weight: bold; font-size: 1.1em; border:none;background-color: transparent;text-align: left;text-decoration: underline;"/>
			</div>
			<div style="background: url('../img/icon-exclamation.gif') no-repeat left top; padding: 0px 0px 20px 36px " >	
			<input  type="submit" name="buttonRemoveSelected" value="Remove selected" style="font-weight: bold; font-size: 1.1em; border:none;background-color: transparent;text-align: left;text-decoration: underline;"/>
			</div>
			</c:if>
		</div>
		</form:form>

	</div> 

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
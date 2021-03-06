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
	<title>${communityEraContext.currentCommunity.name}</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

<body id="forum">
		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
		<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		

				<p id="breadcrumb">
					
					<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
					<a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do" >Wiki</a> &gt; 
					Subscribe for email alerts
					</a>
						
				</p>

			<div id="columnmain" class="forum">
				<form:form showFieldErrors="true" commandName="command" >
						<form:hidden path="id"/>
						<form:hidden path="subsId"/>

						<h2>You are now subscribed to the wiki for this community</h2>
						
						<p>You will receive email alerts when new pages are added to the wiki</p>
												
								<div class="box1" id="box-2fields">
						
							<h4>Your email alert settings for this document:</h4>
							
							<label for="frequency" class="left">Email alert frequency:</label>

										<form:dropdown path="frequency">
											<form:option value="1" label="Daily email notification" />
											<form:option value="2" label="Weekly email notification" />
											<form:option value="0" label="Immediate email notification" />
										</form:dropdown> 

						
							<input type="image" class="button right" alt="Subscribe" src="img/b/subscribe.gif" />
							
						
						</div><!-- /.box1 -->
						
												<p>
You can manage the list of items you monitor for changes in detail from the <a href="${communityEraContext.contextUrl}/pers/watch.do">My Alerts</a> page. 
You can remove items from your alert list for which you no longer want to receive email alerts and set the frequency with which you would like to receive 
email alerts for each item.</p>

				</form:form>
			</div>

			
		<%@ include file="/WEB-INF/jspf/entire-right-col.jspf" %>

			</div>
		
			</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
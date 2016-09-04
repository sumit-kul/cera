<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak admin actions" />
		<meta name="keywords" content="Jhapak, admin, community admin, actions, admin-role" />
		<title>Jhapak - Admin Actions</title>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
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
						<li class="uppercase">System Administration</li>
					</ul>
				</div>
				
				<div class="commSection">
					<div class="inputForm">
				 		<div class="innerBlock">
				
							<h2 style="text-align: left;">System Administration</h2>
							<p>
								<a href="${communityEraContext.contextUrl}/admin/user-index.do?order=surname&amp;pageSize=15" title="System Administrator only">Registered users</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/admin/administrator-index.do?order=surname&amp;pageSize=15" title="System Administrator only">System administrators</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/faq/helpQCreate.do" title="System Administrator only">Create new Help Menu</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/faq/helpEntryCreate.do" title="System Administrator only">Create new Help Entry</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/faq/showAllFaqs.do" title="System Administrator only">Frequently asked questions</a><br/><br/>
									
								<a href="${communityEraContext.contextUrl}/param/mail-message-index.do" title="System Administrator only">Configure email message text</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/announcement/announcement-index.do" title="System Administrator only">Announcements</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/admin/delete-unvalidated-users.do" title="System Administrator only">Delete unvalidated users</a><br/><br/>
							</p>
	
							<h2 style="text-align: left;">Metrics</h2>
							<p>
								<a href="${communityEraContext.contextUrl}/common/showUserSessions.do" title="System Administrator only">View User Sessions</a><br/><br/>
								<%-- <a href="${communityEraContext.contextUrl}/metrics/index.do" title="System Administrator only">View site wide metrics</a><br/><br/> --%>
							</p>
	
							<h2 style="text-align: left;">Downloads</h2>
							<p>
								<%--<a href="${communityEraContext.contextUrl}/admin/community-list-export.do" title="System Administrator only">Community list</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/admin/user-list-export.do" title="System Administrator only">User list</a><br/><br/>
								
								<a href="${communityEraContext.contextUrl}/admin/unvalidateduser-list-export.do" title="System Administrator only">Unvalidated user list</a><br/><br/>
								
								User counts per community <a href="${communityEraContext.contextUrl}/metrics/community-authority-user-counts.do?format=csv">CSV file</a>&nbsp;						
								<a href="${communityEraContext.contextUrl}/metrics/community-authority-user-counts.do?format=xml">XML file </a><br/>  --%>
							</p>
						</div>
					</div>
				</div> 
				</div> 
			
				<div class="right-panel">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
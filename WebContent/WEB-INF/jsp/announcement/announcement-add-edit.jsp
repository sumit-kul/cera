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
<form:form showFieldErrors="true" commandName="command" multipart="true">
			<form:hidden path="id" />
			<form:hidden path="status"/>
			
	<%@ include file="/WEB-INF/jspf/open-container2.jspf" %>
	<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
	<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>

	<div class="padding">

		<div id="main">
		
		<p id="breadcrumb">						
						You are here: 	<a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a> &gt;
						<a href="${communityEraContext.contextUrl}/announcement/announcement-index.do">Announcements</a> &gt; ${command.actionLabel} announcement
					</p>
		
		
		<div id="columnmain" >
				<%--  actionLabel says "Edit existing" or "Create a new" --%>
				<h2 class="header blue">${command.actionLabel} announcement</h2>
				<form:errors message="Please correct the errors below" cssClass="errorText" /> 

				<div id="compose">

					<div class="padding">
					
								<cera:formSection cssClass="header" label="Welcome page annoucement" hideOnRead="true">   
					
						<fieldset>
					
					<form:input path="title" fieldLabel="Title" cssClass="text"/>
					
					<p><label for="subject">Annoucement text:</label>
							<form:textarea path="subject"  rows="5" cols="66"/>
						
						<form:radiolist path="messageType" cssClass="radio" fieldLabel="Announcement Type" wrapperTag="div">								
							<form:optionlist options="Information message|0, Service message|1" />
						</form:radiolist>
						<br />

					</p>
						
			<%--
				<p>
				<c:if test="${command.mode=='c'}">
				<p class="mtop10">
							<label  for="file1">File attachments:</label><br>
							<input class="browse" type="file" id="upload1" name="upload1" size="50" />
							<input class="browse" type="file" id="upload2" name="upload2" size="50" />
							<input class="browse" type="file" id="upload3" name="upload3" size="50" />
							<br/><form:errors path="upload1" cssClass="errorText" />
					</p>
					</c:if>	
				
				<c:if test="${command.mode!='c'}">
				<c:if test="${command.file1Present}">
						<em>Upload a new file here to overwrite the existing file, leave blank to preserve the current file, or <a href="/pers/file-remove.do?id=${command.id}&file=1">click here to remove ${command.fileName1}</a>.</em>
				</c:if>
				<c:if test="${not command.file1Present}"><em>Upload a new file here</em></c:if>						
						<input class="browse" type="file" id="file1" name="upload1" size="60" />
						
						<br/><br/>						
			<c:if test="${command.file2Present}">
						<em>Upload a new file here to overwrite the existing file, leave blank to preserve the current file,<br/>or <a href="/pers/file-remove.do?id=${command.id}&file=2">click here to remove ${command.fileName2}</a>.</em>
			</c:if>						
				<c:if test="${not command.file2Present}"><em>Upload a new file here</em></c:if>						
						<input class="browse" type="file" id="file2" name="upload2" size="60" />
				
				<br/><br/>
			<c:if test="${command.file3Present}">
						<em>Upload a new file here to overwrite the existing file, leave blank to preserve the current file,<br/>or <a href="/pers/file-remove.do?id=${command.id}&file=3">click here to remove ${command.fileName3}</a>.</em>
			</c:if>						
				<c:if test="${not command.file3Present}"><em>Upload a new file here</em></c:if>						
						<input class="browse" type="file" id="file3" name="upload3" size="60" />
</c:if>
</p>
						</p>
						
						--%>
				
								
						<p class="mtop10">    <%--  class gives a margin of 10 px to create some white space --%>
						<input class="btnmain" type="image" src="img/btn-blue-submit.gif" alt="Submit topic" />
						</p>				
						
						</fieldset>
						
						</cera:formSection>
					</div>
					
				</div>

	</form:form>

			</div>

			
		<%@ include file="/WEB-INF/jspf/light-right-col.jspf" %>
			</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div>
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
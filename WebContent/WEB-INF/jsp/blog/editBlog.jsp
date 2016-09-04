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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="${command.name}. ${command.description}" />
		<meta name="keywords" content="Jhapak, blog, edit-blog, community, ${command.keywords}" />
		<title>${command.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" type="text/css" href="css/table.css" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
	 	<style type="text/css">
			div.tag-picker {
				background-color: #F0F9FF; border-color: #0099FF;
			}		
			
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            form textarea.editor {
            	margin: 4px 0px;
            ]
		</style>
		<cera:taggingJS context="${communityEraContext}"/>
		
		<script type="text/javascript">
			function updateBlog(){
				document.updateBlogForm.action = "${communityEraContext.requestUrl}";
				document.updateBlogForm.submit();
			}
		</script>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<div class="commSection">
					<div class="abtMe">
						<div class="cvrImg">
							<c:if test="${communityEraContext.currentUser.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${communityEraContext.currentUser.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${communityEraContext.currentUser.id}&backlink=ref" >${communityEraContext.currentUser.firstName} ${communityEraContext.currentUser.lastName}</a>
							</h2>
						</div>
						<div class="groups">
							<div class="picture">
								<c:choose>
									<c:when test="${communityEraContext.currentUser.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${communityEraContext.currentUser.id}" />
										</c:url>
										<img src="${photoUrl}"  width='160' height='160' style=""/> 
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png'  width='160' height='160'/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${communityEraContext.currentUser.id}">Communities</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${communityEraContext.currentUser.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${communityEraContext.currentUser.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${communityEraContext.currentUser.id}">Gallery</a></li>
						</ul>
					</div>
				 <div class="inputForm" style="margin-bottom: 10px; display: inline-block;">
				 	<div class="intHeaderMain">
				 	Manage Blog
				 	</div>
				 	<div style="padding: 10px">
						<form:form showFieldErrors="true" commandName="command" multipart="true" name="updateBlogForm">
							<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
									<label for="title">Name:<img src="img/required.gif" onmouseover="tip(this, 'This field is required. The name of your blog will display at the top of your blog page. (Maximum length: 100 characters).')" width="8" height="8" class="required" /></label>
									<br />
									<form:input id="name" path="name" cssClass="editor" maxlength="100" />
									<br /><br />
									<label for="entry">Description:</label>
									<form:textarea cssClass="editor" path="description" id="description" maxlength="500"></form:textarea> <br />
									<table class="blockType">
										<tr>
										<th width='100%' colspan='2'>Manage settings for this blog</th>
										</tr>
										<tr class="trclass">
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Blog is active:
											</td>
											<td class='checkonly'>
												<form:checkbox path="inactive" cssClass="check" id="inactive"/><label for="inactive" ><span></span></label>
											</td>
										</tr>
										<tr class="trclassAlt">
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Allow authors to edit each other's posts in this blog:
											</td>
											<td class='checkonly'>
												<form:checkbox path="allowCoEdit" cssClass="check" />
											</td>
										</tr>
										<%-- <tr class='trclass'>
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Allow comments for your blog:
											</td>
											<td class='checkonly'>
												<form:checkbox path="allowComments" cssClass="check" />
											</td>
										</tr> --%>
										<tr class='trclass'>
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Allow comments for new posts:
											</td>
											<td class='checkonly'>
												<form:checkbox path="defaultAllowComments" cssClass="check" />
											</td>
										</tr>
										<tr class='trclassAlt'>
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Moderate comments:
											</td>
											<td class='checkonly'>
												<form:checkbox path="moderateComments" cssClass="check" />
											</td>
										</tr>
										<tr class='trclassAlt'>
											<td width="60%" style="text-align: right; vertical-align: middle;">
												Default time to allow comments for new posts:
											</td>
											<td class='checkonly'>
												<form:dropdown id="defaultCommentDays" path="defaultCommentDays">
													<form:optionlist options="${command.defaultCommentDaysList}" />
												</form:dropdown>
											</td>
										</tr>
									</table>
									<div style="float: right; margin-bottom: 10px;">
										<a href="javascript:void(0);" onclick="updateBlog();" class="btnmain" ><i class="fa fa-check" style="margin-right: 4px;"></i>Update Blog</a>
									</div>
								</form:form>
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
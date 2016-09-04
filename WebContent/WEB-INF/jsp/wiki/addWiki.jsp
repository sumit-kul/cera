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
		<title>Jhapak - New Wiki entry</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<cera:taggingJS context="${communityEraContext}"/>	
		<script src="ckeditor/ckeditor.js"></script>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		
		<script src="ckeditor/ckeditor.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript">
		var editor_0, editor_1, editor_2, editor_3, editor_4, editor_5, editor_6, editor_7, editor_8, editor_9;
		var editor_1;
			function submitWikiEntry(){
				var countSec = document.getElementById('secCount').value;
				for (i = 0; i < countSec; i++) {
					if(i == 0){
						document.getElementById('hidden_'+i).value = 0;
						document.getElementById('body_'+i).value = editor_0.getData();
					} else if (i == 1){
						document.getElementById('hidden_'+i).value = 1;
						document.getElementById('body_'+i).value = editor_1.getData();
					} else if (i == 2){
						document.getElementById('hidden_'+i).value = 2;
						document.getElementById('body_'+i).value = editor_2.getData();
					} else if (i == 3){
						document.getElementById('hidden_'+i).value = 3;
						document.getElementById('body_'+i).value = editor_3.getData();
					} else if (i == 4){
						document.getElementById('hidden_'+i).value = 4;
						document.getElementById('body_'+i).value = editor_4.getData();
					} else if (i == 5){
						document.getElementById('hidden_'+i).value = 5;
						document.getElementById('body_'+i).value = editor_5.getData();
					} else if (i == 6){
						document.getElementById('hidden_'+i).value = 6;
						document.getElementById('body_'+i).value = editor_6.getData();
					} else if (i == 7){
						document.getElementById('hidden_'+i).value = 7;
						document.getElementById('body_'+i).value = editor_7.getData();
					} else if (i == 8){
						document.getElementById('hidden_'+i).value = 8;
						document.getElementById('body_'+i).value = editor_8.getData();
					} else if (i == 9){
						document.getElementById('hidden_'+i).value = 9;
						document.getElementById('body_'+i).value = editor_9.getData();
					}
				}
				document.submitWikiAction.action = "${communityEraContext.requestUrl}";
				document.submitWikiAction.submit();
			}

			function addSection() {
				var editor;
				var count = document.getElementById('secCount').value;
				count = parseInt(count);
				var titCnt = count + 1;
				 var sec = "<label for='secHeader_"+count+"'>Section "+titCnt+" <span style='font-weight: 400;'>(100 characters max.)</span></label>";
				 sec += "<input id='secHeader_"+count+"' class='editor' type='text' name='sections["+count+"].header'/>";
				 sec += "<input id='hidden_"+count+"' type='hidden' name='sections["+count+"].sectionSeq'/>";
				 sec += "<textarea id='body_"+count+"' style='display:none;' name='sections["+count+"].body' ></textarea><div id='editor_"+count+"' style='width: 100%; margin: 12px 0px 18px 0px;'></div>";
				$('#sectionArea').append(sec);
				document.getElementById('secCount').value = count+1 ;
				var config = {};
				if(count == 0){
					editor_0 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 1){
					editor_1 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 2){
					editor_2 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 3){
					editor_3 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 4){
					editor_4 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 5){
					editor_5 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 6){
					editor_6 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 7){
					editor_7 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 8){
					editor_8 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
				} else if (count == 9){
					editor_9 = CKEDITOR.appendTo( 'editor_'+count , config, '' );
					$("#addSectionBtn").hide();
				}
			}
		</script>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
				<div class="innerPanl">
					<div class="carousel_caption">
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Private'}">
							<h4><i class="fa fa-lock" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Protected'}">
							<h4><i class="fa fa-shield" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Public'}">
							<h4><i class="fa fa-globe" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<p>${communityEraContext.currentCommunity.welcomeText}</p>							
					</div>
					<div class="menuContainer">
						<ul id="gn-menu" class="gn-menu-main">
							<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
							<li>
								<nav id="menu">
						    		<input type="checkbox" id="toggle-nav"/>
						    		<label id="toggle-nav-label" for="toggle-nav"><i class="fa fa-bars"></i></label>
						    		<div class="box">
							    		<ul>
							    			<li><a href="${communityEraContext.currentCommunityUrl}/home.do"><i class="fa fa-home" style="margin-right: 8px;"></i>Home</a></li>
							    			<c:forEach items="${communityEraContext.enabledFeatureNamesForCurrentCommunity}" var="feature">
												<c:if test="${feature == 'Assignments'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/assignment/showAssignments.do"><i class="fa fa-briefcase" style="margin-right: 8px;"></i>Assignments</a></li>
												</c:if>
												<c:if test="${feature == 'Blog'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>Blogs</a></li>
												</c:if>
												<c:if test="${feature == 'Forum'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do"><i class="fa fa-comments-o" style="margin-right: 8px;"></i>Forums</a></li>
												</c:if>
												<c:if test="${feature == 'Members'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
												</c:if>
												<c:if test="${feature == 'Library'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
												</c:if>
												<c:if test="${feature == 'Events'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
												</c:if>
											</c:forEach>
							    		</ul>
						    		</div>
						    	</nav>
					    	</li>
						</ul>
					</div><!-- /container -->
					<div class="groups">
						<div class="picture">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="290" height="290" /> 
								</c:when>
								<c:otherwise>
									<img src="img/community_Image.png" id="photoImg" width="290" height="290" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="inputForm" >
						<div class="intHeaderMain">
							<span>${command.title}</span>
						</div>
				 		<div class="innerBlock">
				 			<input type="hidden" id="secCount" value="0" />
							<form:form showFieldErrors="true" commandName="command" multipart="true" name="submitWikiAction">
								<form:errors message="Please correct the errors below" cssClass="errorText" /> 
								<label for="response" >Introduction <span style="font-weight: 400;">(Enter a short introduction to quickly introduce the reader to your article.)</span></label>
								<textarea class="ckeditor" name="body" >${command.body}</textarea>
								<br />	
								<div id="sectionArea">
								</div>
								<div style="float: right;">
									<a id="addSectionBtn" href="javascript:void(0);" onclick="addSection();" class="btnmain" tabindex="17"><i class="fa fa-plus" style="margin-right: 4px;"></i>Add section</a>
								</div>
								<div class="tagOutLine" style="margin: 10px 0px; padding: 0px 0px 10px 10px; display: inline-table;">
									<%-- <h4>Related wiki entries:<br />
									
									<h4>Related external sites: <br />
									<p>Enter details of up to 5 related external sites below.</p></h4>
									<span class="halfwidthinput">
										<label class="title1">Title:</label> (appears in page)<br />
										<form:input tabindex="7" id="title1" path="externalTitle1" cssClass="text" />
										<form:input tabindex="9" id="title2" path="externalTitle2" cssClass="text"/>
										<form:input tabindex="11" id="title3" path="externalTitle3"  cssClass="text"/>
										<form:input tabindex="13" id="title4" path="externalTitle4" cssClass="text" />
										<form:input tabindex="15" id="title5" path="externalTitle5"  cssClass="text"/>
									</span>
									<span class="halfwidthinput">
										<label for="www1">URL:</label> (http://...)<br />
										<form:input tabindex="8" id="www1" path="externalURL1" cssClass="text" />
										<form:input tabindex="10" id="www2" path="externalURL2" cssClass="text" />
										<form:input tabindex="12" id="www3" path="externalURL3"  cssClass="text"/>
										<form:input tabindex="14" id="www4" path="externalURL4" cssClass="text" />
										<form:input tabindex="16" id="www5" path="externalURL5"  cssClass="text"/>
									</span> --%>
								</div>
						
								<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="20" parentType="wiki entry" />
								<div style="float: right;">
									<a href="javascript:void(0);" onclick="submitWikiEntry();" class="btnmain" tabindex="17"><i class="fa fa-check" style="margin-right: 4px;"></i>Save page</a>
								</div>
								<p class="licenseCont">
								Contribution in a community wiki is a collaborative writing. You should expect other community members will edit and build upon the writing that you submit here. 
								By submitting your writing, you confirm that you wrote this content or have received permission from the copyright holder to post it here. 
								In addition, you agree to our <a href="/terms.do">Terms of Use</a>, and are willing to have 
								your work released under a <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/">Creative Commons License <i class="fa fa-creative-commons" style="font-size: 16px;"></i></a>. 
								</p>
							</form:form>
						</div>
						</div>
					</div> 
				</div>
				
				<div class="right-panel">
					<div class="inbox">
						<div class="eyebrow">
							<span><i class="fa fa-tags" style="margin-right: 2px;"></i>Popular Tags</span>
						</div>
						<div id="cloud" class="communities"></div>
						<div id="waitCloudMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div id="cloudList" style="display: none; " class="communities"></div>
						<ul id="cloudTabs">
							<c:choose>
								<c:when test="${command.toggleList == 'true'}">
									<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
								</c:when>
								<c:otherwise>
									<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
							  	</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
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
		<title>Jhapak - ${command.title}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			input:focus {
			    outline: medium none;
			}
			
			#container .left-panel .commSection .paginatedList {
				border-width: 0px 0px 1px 0px;
				box-shadow: none;
				border-radius: 0px;
				margin: 10px;
				padding: 0px 0px 10px 0px;
			}
			
			.euInfo {
				margin-left: 10px;
			}
			
			#pagination {
				margin-right: 10px;
			}
			
			#container .left-panel p a.cliLnk {
				font-size: 16px;
				 color: #66799f; 
				 margin: 0px;
			}
			
			#container .left-panel p a.cliLnk:hover {
				text-decoration: underline;
			}
			
			#pagehistory {
			    padding: 10px;
			    list-style: outside none none;
			}

			#pagehistory li {
			    padding: 5px 0px;
			    line-height: 20px;
			}
			
			#pagehistory li .histlinks {
			    font-size: 13px;
			    width: 10%;
			    padding-right: 8px;
			    white-space: nowrap;
			}
			
			#pagehistory li a {
				font-size: 13px;
			    color: #66799f;
			}

			#pagehistory input {
			    width: 4%;
			    padding-top: 6px;
			    padding-right: 2px;
			}
			
			#pagehistory li {
			    padding: 8px 6px;
			}
			
			#pagehistory li:nth-child(2n+1) {
			    background-color: white;
			    border-radius: 5px;
			}
			
			.navigation {
			    margin: 8px 18px;
			    float: right;
			}
			
			.navigation a {
				font-size: 13px;
			    color: #66799f;
			}
			
			.btnmain {
				margin: 12px 0px 0px 12px;
				padding: 4px;
				height: 28px;
				line-height: 19px;
			}
			
			.outerPage {
				margin: 12px;
				background: #F5F5F5 none repeat scroll 0% 0%;
				border-radius: 5px;
				display: inline-block;
				width: 97%;
			}
			
			.topHeader .heading {
			    width: 600px;
			    color: #184A72;
			    margin-bottom: 2px;
			    background: transparent none repeat scroll 0% 0%;
			    font-family: "Linux Libertine",Georgia,Times,serif;
			}
			
			.navigation a.selectedPage {
				color: #184A72;
			}
			
			.paginator_p {
				margin: 10px 5px;
			}
			
			.extraWord {
				text-align: center;
				font-weight: normal;
				font-size: 13px;
			}
		</style>
		
		<script type="text/javascript">
			function handleLeftRadio(currentLeftVersion) {
				var cv = parseInt(currentLeftVersion, 10);
				cv = cv + 1;
				var rightOffset = document.getElementById('rightOffset').value;
				for ( var i = cv; i < rightOffset; i++) {
					document.getElementById('right-'+i).style.visibility = "visible";
				}
				var leftOffset = document.getElementById('leftOffset').value;
				if (leftOffset < currentLeftVersion) {
					for ( var j = leftOffset; j <= currentLeftVersion; j++) {
						document.getElementById('right-'+j).style.visibility = "hidden";
					}
				}
				document.getElementById('leftOffset').value = currentLeftVersion;
			}

			function handleRightRadio(currentRightVersion) {
				//alert(currentRightVersion);
				var cv = parseInt(currentRightVersion, 10);
				var rightOffset = document.getElementById('rightOffset').value;
				for ( var i = cv; i < rightOffset; i++) {
					document.getElementById('left-'+i).style.visibility = "hidden";
				}
				var leftOffset = document.getElementById('leftOffset').value;
				//alert(leftOffset);
				if (leftOffset < currentRightVersion) {
					for ( var j = leftOffset; j < currentRightVersion; j++) {
						document.getElementById('left-'+j).style.visibility = "visible";
					}
				}
				document.getElementById('rightOffset').value = currentRightVersion;
			}
		</script>
		
		<script type="text/javascript">
			function handlePagnation(vcount, isNew){
				var ps = document.getElementsByClassName("selectedPage");
				for(var i = 0; i < ps.length; ++i){
						ps[i].setAttribute("class", "");
				}
				document.getElementById('wiki'+vcount).setAttribute("class", "selectedPage");
				document.getElementById('swiki'+vcount).setAttribute("class", "selectedPage");

				if(${command.pageCount} > 0){
					$("#pagination").jPaginator({ 
						nbPages:${command.pageCount},
						marginPx:5,
						length:6, 
						overBtnLeft:'#over_backward', 
						overBtnRight:'#over_forward', 
						maxBtnLeft:'#max_backward', 
						maxBtnRight:'#max_forward', 
						onPageClicked: function(a,num) { 
							var sUrl;
							if (isNew) {
								sUrl = "${communityEraContext.currentCommunityUrl}/wiki/wikiHistory.do?entryId=${command.entryId}&jPage="+1+"&pageSize="+vcount;
							} else {
								sUrl = "${communityEraContext.currentCommunityUrl}/wiki/wikiHistory.do?entryId=${command.entryId}&jPage="+num+"&pageSize="+vcount;
							}
						$.ajax({url:sUrl,dataType: "json",success:function(result){ 
							var sName = "<ul id='pagehistory'>";
							var top = result.aData.length;
							var count = result.aData.length;
							 $.each(result.aData, function() {
								 var cv;
								 sName += "<li class='selected'>";
								 sName += "<span class='histlinks'>";
								 if(this['sequence'] == 'Latest'){
									 cv = top;
									 sName += "(cur | <a href='${communityEraContext.currentCommunityUrl}/wiki/compareWikiVersions.do?entryId="+this['entryId']+"&amp;first=0&amp;second="+(cv-1)+"' title='"+this['title']+"'>prev</a>)</span>";
									 sName += "<input onclick='handleLeftRadio(&#39;"+this['sequence']+"&#39;)' value='"+this['entryId']+"' style='visibility:hidden' name='first' id='left-"+this['sequence']+"' type='radio'>";
									 sName += "<input onclick='handleRightRadio(&#39;"+top+"&#39;)' style='visibility: visible;' value='"+this['entryId']+"' checked='checked' name='second' id='right-"+this['sequence']+"' type='radio'>";
									 document.getElementById('rightOffset').value = top;
								 } else {
									 cv = parseInt(this['sequence'], 10);
									 sName += "(<a href='${communityEraContext.currentCommunityUrl}/wiki/compareWikiVersions.do?entryId="+this['entryId']+"&amp;first=0&amp;second="+this['sequence']+"' title='"+this['title']+"'>cur</a> | ";
									 if (this['sequence'] == 1) {
									 	sName += "prev)</span>";
									 } else {
										 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/compareWikiVersions.do?entryId="+this['entryId']+"&amp;first="+this['sequence']+"&amp;second="+(cv-1)+"' title='"+this['title']+"'>prev</a>)</span>";
									 }
									 if(this['sequence'] == (top-1)){
									 	sName += "<input onclick='handleLeftRadio(&#39;"+this['sequence']+"&#39;)' value='"+this['entryId']+"' style='visibility: visible;' checked='checked' name='first' id='left-"+this['sequence']+"' type='radio'>";
									 	sName += "<input onclick='handleRightRadio(&#39;"+this['sequence']+"&#39;)' style='visibility:hidden' value='"+this['entryId']+"' name='second' id='right-"+this['sequence']+"' type='radio'>";
									 	document.getElementById('leftOffset').value = this['sequence'];
								 	} else {
								 		sName += "<input onclick='handleLeftRadio(&#39;"+this['sequence']+"&#39;)' value='"+this['entryId']+"' style='visibility: visible;' name='first' id='left-"+this['sequence']+"' type='radio'>";
									 	sName += "<input onclick='handleRightRadio(&#39;"+this['sequence']+"&#39;)' style='visibility:hidden' value='"+this['entryId']+"' name='second' id='right-"+this['sequence']+"' type='radio'>";
								 	}
								 }
								 
								 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId="+this['entryId']+"&amp;entrySequence="+this['entrySequence']+"' title='"+this['title']+"'>"+this['datePostOn']+"</a>";
								 sName += "<span class='history-user'>";
								 sName += " | <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&amp;backlink=ref' class='new userlink' title='"+this['editedBy']+"'>"+this['editedBy']+"</a>";  
								 //sName += "<span class='mw-usertoollinks'>(<a href='/User_talk:X_x_X-X_x_X' title='User_talk:X_x_X X_x_X'>Talk</a> | <a href='/Special:Contributions/X_x_X-X_x_X' title='Special:Contributions/X_x_X X_x_X'>contribs</a>)</span>";
								 sName += " <span style='font-size: 13px;'>("+this['reasonForUpdate']+")</span></span>";
	
								 sName += "</div></li>";
								 count --;
									});
							 sName += "</ul>";
							 $("#page").html(sName);
							// Hide message
					        $("#waitMessage").hide();
					        toggleOnLoad();
					        normalQtip();
							memberInfoQtip();
							optionListQtip();
						    },
						 	// What to do before starting
					         beforeSend: function () {
					             $("#waitMessage").show();
					         } 
					    });
						} 
					});
				}
			}
			$(document).ready(function () {
				handlePagnation(50, false);
			});

			function joinRequest(rowId, ctype) 
			{ 
				$(".qtip").hide();
			    var ref = '${communityEraContext.contextUrl}/community/joinCommunityRequest.ajx?id='+rowId;
			    var mess = '';
			    var type = BootstrapDialog.TYPE_INFO;
			    var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	type = BootstrapDialog.TYPE_DANGER;
			    	mess = '<p class="addTagHeader">You are not logged-in.<br/> Please login first to join a community. </p>';
			    } else {
			    	mess = '<p class="addTagHeader"><strong>This is a public community.</strong><br/><br/>'+
				    'To join this community click the Go button below. '+
					'You will be added to the community member list and will immediately have access to the community content. '+
					'</p>';
			    }
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Join a community',
		                message: mess,
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                	cssClass: 'btn-danger',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
			    }
			}

			function compareVersions() 
			{
				var leftOffset = document.getElementById('leftOffset').value;
				var rightOffset = document.getElementById('rightOffset').value;
				var versionCount = document.getElementById('versionCount').value;

				if (versionCount == rightOffset) {
					rightOffset = 0;
				}
				var href="${communityEraContext.currentCommunityUrl}/wiki/compareWikiVersions.do?entryId=${command.entryId}&first="+rightOffset+"&second="+leftOffset;
				window.location.href=href;
			}

			function deleteWikiEntry(entryId){
				var	msg = "Are you sure you want to remove this wiki page completely (with all revisions).<br />";
				msg += "All revisions and discussions for this Wiki page will be deleted and can&#39;t be restored!";
				var href="${communityEraContext.currentCommunityUrl}/wiki/wikiDelete.do?mode=all&id="+entryId;
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Remove this Wiki Page',
	                message: '<p class="extraWord">'+msg+'</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	label: 'Close',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                },{
	                	label: 'Go',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                        window.location.href=href;
	                    }
	                }]
	            });
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
			<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
			<input type="hidden" id="leftOffset" />
			<input type="hidden" id="rightOffset" value="${command.versionCount}" />
			<input type="hidden" id="versionCount" value="${command.versionCount}" />
			<div class="commSection">
				<div class="paginatedList" style="margin: 0px 0px 10px auto; padding: 0px;">	
					<div class="topSection" style="padding: 0px;">
						<div class="menus" style="float: left; width: 100%; border-bottom: 1px solid #D8D8D8; padding: 0px 10px 10px;">
							<ul style="margin-top: 6px;">
								<li><a class="heaedrMenu"href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Page</a></li>
								<li><a class="heaedrMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Discussion</a></li>
								<li><a class="heaedrMenu selectMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Revision history</a></li>
								<c:if test="${command.currentVersion}">
									<c:if test="${communityEraContext.userCommunityAdmin || communityEraContext.userSysAdmin}">
										<li style="float: right;"><a href="javascript:void(0);" onclick="deleteWikiEntry(&#39;${command.entryId}&#39;);" style="margin-right: 12px;">
										<i class='fa fa-remove' style='margin: 0px 4px; font-size: 14px;'></i>Delete this Wiki page</a></li>
									</c:if>
								</c:if>
							</ul>
						</div>
						<div style="padding: 10px 10px 5px; display: inline-block;">
							<div class="topHeader" >
								<div class="heading" style="font-family: Arial,Helvetica,sans-serif;">
									Revision history: <span style="font-size: 20px; font-weight: bold;" id="topTitle">${command.title}</span>
								</div>
							</div> 
						</div>
					</div>
					<div class="commentBlock">
						<c:if test= "${command.versionCount > 1}" >
							<div style="width: 100%; display: inline-block;">
								<div style="float: left;">
									<a class="btnmain" onclick="compareVersions();" href="javascript:void(0);" >Compare wiki page versions</a></li>			
								</div>
								<div class="navigation">
									(<a onclick="handlePagnation(20, true);" href="javascript:void(0);" id="wiki20">20</a>
									|
									<a onclick="handlePagnation(50, true);" href="javascript:void(0);" id="wiki50" class="selectedPage">50</a>
									|
									<a onclick="handlePagnation(100, true);" href="javascript:void(0);" id="wiki100">100</a>
									|
									<a onclick="handlePagnation(250, true);" href="javascript:void(0);" id="wiki250">250</a>
									|
									<a onclick="handlePagnation(500, true);" href="javascript:void(0);" id="wiki500">500</a>)
								</div>
							</div>
							<div class="outerPage">
								<div id="page"></div>
								<div id="waitMessage" style="display: none;">
									<div class="cssload-square" >
										<div class="cssload-square-part cssload-square-green" ></div>
										<div class="cssload-square-part cssload-square-pink" ></div>
										<div class="cssload-square-blend" ></div>
									</div>
								</div>
								<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
							</div>
							<div style="width: 100%; display: inline-block;">
								<div style="float: left;">
									<a class="btnmain" style="margin: 4px 0px 8px 12px;" onclick="compareVersions();" href="javascript:void(0);" >Compare wiki page versions</a></li>			
								</div>
								<div class="navigation">
									(<a onclick="handlePagnation(20, true);" href="javascript:void(0);" id="swiki20">20</a>
									|
									<a onclick="handlePagnation(50, true);" href="javascript:void(0);" id="swiki50" class="selectedPage">50</a>
									|
									<a onclick="handlePagnation(100, true);" href="javascript:void(0);" id="swiki100">100</a>
									|
									<a onclick="handlePagnation(250, true);" href="javascript:void(0);" id="swiki250">250</a>
									|
									<a onclick="handlePagnation(500, true);" href="javascript:void(0);" id="swiki500">500</a>)
								</div>
							</div>
						</c:if>
						<c:if test="${command.versionCount == 1}">
							<div style="padding: 20px; text-align: center;">
								<p style="font-size:  16px; font-weight: bold">This article has not revised since it was first created.</p>
								<c:choose>
									<c:when test="${command.member}">	
										<p style="font-size:  14px;">If you think you can improve this article please consider editing the article.<br />
										<a href="${communityEraContext.currentCommunityUrl}/wiki/editWiki.do?entryId=${command.entryId}" title="Edit this wiki page"
										class="cliLnk" >Click here</a> to go into edit mode and fix it up.</p>
									</c:when>
									<c:otherwise>
										<c:if test="${communityEraContext.userAuthenticated}">
											<p style="font-size:  14px;">If you think you can improve this article please describe how the article can be improved by 
										simply fill in the text box on <a href="${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.do?entryId=${command.entryId}" 
										class="cliLnk" >discussion</a> page.<br />or<br />
										Please <a onclick="joinRequest('${communityEraContext.currentCommunity.id}' , 'false');" href="javascript:void(0);" title="Join this community" style="margin-right: 0px;">join</a> the community to start contributing to improve this article.</p>
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if> 
					</div>
					<div class="topSection" style="border-width: 1px 0px 0px; border-radius: 0px 0px 3px 3px; margin: 0px;">
						<div>
							<ul class="bottSecLst">
								<li>
									<c:choose>
										<c:when test="${command.creatorPhotoPresent}">						 
											 <img src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${command.creatorId}" title="${command.creatorName}"/>
										</c:when>
										<c:otherwise>
											<img src="img/user_icon.png" title="${command.creatorName}"/>
										</c:otherwise>
									</c:choose>
									<span title="${command.createdOnHover}">${command.createdOn}</span>
									<h4 style="margin-left: 16px;">created</h4>
								</li>
								<c:if test="${command.versionCount > 0}">
									<li>
										<c:choose>
											<c:when test="${command.photoPresent}">						 
												 <img src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${command.posterId}" title="${command.lastEditUserName}"/>
											</c:when>
											<c:otherwise>
												<img src="img/user_icon.png" title="${command.lastEditUserName}"/>
											</c:otherwise>
										</c:choose>
										<span title="${command.datePostOnHover}">${command.datePostOn}</span>
										<h4 style="margin-left: 16px;">current</h4>
									</li>
								</c:if>
								<li>
									<span title="${command.versionCount}">${command.versionCount}</span>
									<h4>revisions</h4>
								</li>
								<li>
									<span title="Last viewed at ${command.lastVisitTime}">${command.visitCount}</span>
									<h4>views</h4>
								</li>
								<li>
									<span id="likeSpan-${command.id}">
										${command.likeCount}
									</span>
									<h4>likes</h4>
								</li>
							</ul>
						</div>
						<div>
						</div>
					</div>
					<div class="topSection" style="border-width: 1px 0px 0px; border-radius: 0px 0px 3px 3px; margin: 0px;">
						<div>
							<ul class="bottSecLst">
								<li>
									<c:if test="${command.visitCount > 1}">
										<h4 style="">Thanks to all contributors for creating a page that has been read ${command.visitCount} times.</h4>
									</c:if>
									<c:if test="${command.visitCount <= 1}">
										<h4 style="">Thanks to all contributors for creating this page.</h4>
									</c:if>
									
									<c:forEach items="${command.contributors}" var="row" varStatus="status">
										<div style="float: left; position: relative; margin: 3px 0px;">
											<span class="contbCount">${row.contributionCount}</span>
											<c:choose>
												<c:when test="${row.contributorPhotoPresent}">						 
													 <img class="contbImg" src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.contributorId}" title="${row.contributorName}"/>
												</c:when>
												<c:otherwise>
													<img class="contbImg" src="img/user_icon.png" title="${row.contributorName}"/>
												</c:otherwise>
											</c:choose>
										</div>
									</c:forEach>
								</li>
							</ul>
						</div>
					</div>
				</div><!-- /.paginatedList --> 
			</div> <!-- .commSection -->
		</div> <!-- .left-pannel -->
			
			<div class="right-panel">
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>							
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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - ${command.title}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script src="ckeditor/ckeditor.js"></script>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.topHeader .heading {
				width: 600px; 
				color: #184A72; 
				margin-bottom: 2px;
				background: transparent none repeat scroll 0% 0%;
				font-family: "Linux Libertine",Georgia,Times,serif;
			}
			
			#container .left-panel .commSection .commentBlock {
			    vertical-align: top;
			    overflow: hidden;
			    clear: both;
			    padding: 16px;
			    position: relative;
			    list-style: outside none none;
			}
			
			#container .left-panel .commSection .commentBlock .internalPagination {
				background: #F5F5F5 none repeat scroll 0% 0%;
			    box-sizing: border-box;
			    border-radius: 4px;
			    border-style: solid;
			    border-color: #D8D8D8;
			    -moz-border-top-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-bottom-colors: none;
			    -moz-border-left-colors: none;
			    border-image: none;
			    overflow: hidden;
			    position: relative;
			    margin: 0px 0px 16px;
			    font-size: 14px;
			    padding: 10px;
			}
			
			#container .left-panel .commSection .paginatedList .details {
			    width: 630px;
			}
		</style>
		
		<script type="text/javascript">
			var editor_0;
			function submitComment(){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Following this Blog',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to post a comment.</p>',
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
			    } else {
			    	document.getElementById('isSubmit').value = "submit";
			    	document.getElementById('comment').value = editor_0.getData();
					document.submitCommentForm.action = "${communityEraContext.requestUrl}";
					document.submitCommentForm.submit();
			    }
			}
			
			// Initial call
			$(document).ready(function(){
				var config = {};
	        	config.toolbarGroups = [
										{ name: 'colors', groups: [ 'colors' ] },
										{ name: 'insert', groups: [ 'insert' ] },
										{ name: 'basicstyles', groups: [ 'bold'] },
										{ name: 'styles', groups: [ 'size' ] },
	                            	];
	        	config.toolbarLocation = 'top';
	        	config.removeButtons = 'Subscript,Superscript,Image,Flash,Table,Iframe,SpecialChar,HorizontalRule,PageBreak';
	        	editor_0 = CKEDITOR.appendTo( 'editor', config, '' );

	        	normalQtip();
				memberInfoQtip();

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
						$.ajax({url:"${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.ajx?jPage="+num+"&entryId="+$("#entryId").val(),dataType: "json",success:function(result){
							var sName = "";
							$.each(result.aData, function() {
								 var rowId = this['id'];
								 sName += "<div class='internalPagination'><div class='leftImg'>";
								 if(this['photoPresent']){
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' /></a>"; }
								 else {
									 sName += "<img src='img/user_icon.png'  />";
								 }
	
								 sName += "</div><div class='details'><div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' style='padding:0px;' ";
								 sName += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"' >"+this['displayName']+"</a><br /> commented on "+this['postedOn']+"</div>";
	
								 sName += "<div class='entry' id='div-"+rowId+"' >";
								 
								sName += "</div>";
								sName += "<p>"+this['comment']+"</p></div></div>";
									});
							 $("#page").html(sName);
							// Hide message
						        $("#waitMessage").hide();
								memberInfoQtip();
							    },
							 	// What to do before starting
						         beforeSend: function () {
						             $("#waitMessage").show();
						         }
					    });
						} 
					});
					normalQtip();
					memberInfoQtip();
				}
			});
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
			<input type="hidden" id="entryId" name="entryId" value="${command.entryId}" />
			<div class="commSection">
				<div class="paginatedList" style="margin: 0px 0px 10px auto; padding: 0px;">	
					<div class="topSection" style="padding: 0px;">
						<div class="menus" style="float: left; width: 100%; border-bottom: 1px solid #D8D8D8; padding: 0px 10px 10px;">
							<ul style="margin-top: 6px;">
								<li><a class="heaedrMenu"href="${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Page</a></li>
								<li><a class="heaedrMenu selectMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiDiscussionDisplay.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">Discussion</a></li>
								<li><a class="heaedrMenu" href="${communityEraContext.currentCommunityUrl}/wiki/wikiHistory.do?entryId=${command.entryId}" style="font-size: 14px; font-weight: bold;">History</a></li>
							</ul>
						</div>
						<div style="padding: 10px 10px 5px; display: inline-block;">
							<div class="topHeader" >
								<div class="heading" style="    font-family: Arial,Helvetica,sans-serif;">
									Discussion: <span style="font-size: 20px; font-weight: bold;" id="topTitle">${command.title}</span>
								</div>
							</div> 
						</div>
					</div>
					<div class="commentBlock">
						<c:if test="${command.commentCount > 0}">
							<div id="page"></div>
							<div id="waitMessage" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
							<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
							<br />
						</c:if>
						<c:if test="${command.commentCount == 0}">
							<div style="padding: 20px;">
								<p style="font-size:  16px; font-weight: bold">This talk page has not yet been started.</p>
								<p style="font-size:  14px;">You have reached a currently empty talk page. You can leave a public message for community members by 
								filling in the text box below and clicking "Post".</p>
							</div>
						</c:if> 
					</div>
					<div class="outerBlock"> 
						<div style="" id="addAComment"> 
							<form:form showFieldErrors="true" commandName="command" name="submitCommentForm">
								<label for="comment" >Add your comment</label>	
								<textarea name='comment' id='comment' style='display:none;'></textarea><div id='editor'></div>
								<c:if test="${command.errorMsg != null}">
									<span class="errorText" style="margin:2px;">${command.errorMsg}</span><br />
								</c:if>
								<form:hidden id="isSubmit" path="isSubmit" />
								<div style="margin: 10px 0px;">
									<a href="javascript:void(0);" onclick="submitComment();" class="btnmain" style='float:right;'>Post</a>
								</div>
							</form:form>
						</div>
					</div>
				</div><!-- /.paginatedList --> 
			</div> <!-- .commSection -->
		</div> <!-- .left-pannel -->
			
			<div class="right-panel">
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>							
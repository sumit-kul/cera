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
		<meta name="description" content="Jhapak, ${communityEraContext.currentCommunity.name}. ${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, blog, community, edit-community, edit community, Connections, Event, Forum, Library, File, Wiki" />
		<title>Membership Settings: ${communityEraContext.currentCommunity.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.intHeader {
				height: 40px;
				background: none repeat scroll 0% 0% #EEF3F6;
				clear: both;
				font-size: 20px;
				font-weight: bold;
				color: #66799f;
				text-decoration: none;
			}
		</style>
		
				<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}&memRequest=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
						 }
						});
					 $("#memReqList").html(temp);
					 
					// Hide message
			        $("#waitMEMMessage").hide();

			        $('.memberInfo').each(function() {
				         $(this).qtip({
				            content: {
				        	 button: true,
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title') // Use href attribute as URL
				                    })
				                    .then(function(content) {
				                        api.set('content.text', content);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top right',  // Position my top left...
				                at: 'bottom right', // at the bottom right of...
				                target: 'mouse', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
								style: {
							        classes: 'qtip-bootstrap myCustomClass'
							    },
							    hide: {
							    	target: $('a')
							    }
				         });
				     });
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitMEMMessage").show();
			         } 
			    });

				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}&memInvt=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
						 }
						});
					 $("#memInvList").html(temp);
					 
					// Hide message
			        $("#waitINVMessage").hide();

			        $('.memberInfo').each(function() {
				         $(this).qtip({
				            content: {
				        	 button: true,
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title') // Use href attribute as URL
				                    })
				                    .then(function(content) {
				                        api.set('content.text', content);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top right',  // Position my top left...
				                at: 'bottom right', // at the bottom right of...
				                target: 'mouse', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
								style: {
							        classes: 'qtip-bootstrap myCustomClass'
							    },
							    hide: {
							    	target: $('a')
							    }
				         });
				     });
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitINVMessage").show();
			         } 
			    });
				dynamicDropDownQtip();

		        $('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
				    }
				});
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
							<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
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
												<c:if test="${feature == 'Wiki'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
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
					<div class="inputForm" style="display: inline-block; width: 99.4%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<div class="menus" style="padding-top:2px;">
								<ul>
									<c:if test="${communityEraContext.userCommunityAdmin}">
										<li>
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=2002&commId=${communityEraContext.currentCommunity.id}'>Membership Actions <span class='ddimgBlk'/></a>
										</li>
									</c:if>
								</ul>
							</div>
							<span style="font-size:13px; margin-top:0px; padding:10px; float: left; color: #AAA6A6;" class="tHeader">
								Membership Settings for this community
							</span>
						</div>
							
							<form:form showFieldErrors="true">
				
								<p><strong>Community Admins</strong></p>
								<p>You can appoint up to 6 Admins. Simply enter their email addresses below:</p>
				
								<p>
								<form:input path="administrator1" cssClass="text" fieldLabel="Community Admin 1:"/>
								</p>
								<p>
								<form:input path="administrator2" cssClass="text" fieldLabel="Community Admin 2:"/>
								</p>
								<p>
								<form:input path="administrator3" cssClass="text" fieldLabel="Community Admin 3:"/>
								</p>
								<p>
								<form:input path="administrator4" cssClass="text" fieldLabel="Community Admin 4:"/>
								</p>
								<p>
								<form:input path="administrator5" cssClass="text" fieldLabel="Community Admin 5:"/>
								</p>
								<p>
								<form:input path="administrator6" cssClass="text" fieldLabel="Community Admin 6:"/>
								</p>
				
								<div class="hr"><hr /></div>

								<c:if test="${command.privateCommunity}">
									<p><strong>Auto registration</strong></p>
									<p>You can allow people from specified email domains to join the community without approval. Simply enter the domains below separated by a comma.</p>
					
									<p><label for="domains">Enter domains:</label>
									<form:input cssClass="almostfullwidth" path="membershipDomains" />
									</p>
									<div class="hr"><hr /></div>
								</c:if>
							
								<p><strong>Add more members</strong></p>
								<p>Add up to 50 more members in one go. Simply enter their email addresses below:</p>
								<p>
								<form:textarea path="additionalMembers" cssClass="almostfullwidth" fieldLabel="Email address(es)"/>
								</p>
								<%-- <p><strong>Note:</strong> All members listed on this page will be notified by email.</p>  --%>
								<div class="hr"><hr /></div>
								<p>
									<input class="search_btn" type="image" src="" alt="Save settings" />
								</p>
							</form:form>
						</div>
						
					</div>
				</div>
				
				<div class="right-panel">
					<c:if test="${command.community.memberShipRequestCount > 0 && command.adminMember}">
						<div class="inbox">
							<div class="eyebrow">
								<span onclick="return false" >Membership Requests</span>
							</div>
							<div id="memReqList" style="padding: 4px;" ></div>
							<div id="waitMEMMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
							<div class="view">
								<a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do?backlink=ref">View All (${command.community.memberShipRequestCount} pending requests)</a>
							</div>
						</div>
					</c:if>
					<c:if test="${command.community.memberInvitationCount > 0 && command.member}">
						<div class="inbox">
							<div class="eyebrow">
								<span onclick="return false" >Membership Invitations</span>
							</div>
							<div id="memInvList" style="padding: 4px;" ></div>
							<div id="waitINVMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
							<div class="view">
								<a href="${communityEraContext.currentCommunityUrl}/connections/showInvitations.do?backlink=ref">View All (${command.community.memberInvitationCount} invitations)</a>
							</div>
						</div>
					</c:if>
				</div>	
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
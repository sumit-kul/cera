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
		<meta name="robots" content="noindex, follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		<meta name="description" content="Jhapak, ${communityEraContext.currentCommunity.name}. ${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, event, events, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>${communityEraContext.currentCommunity.name}' events - Jhapak</title>
		
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
			    width: 180px;
			}
			
			#container .left-panel .commSection .paginatedList .details .heading span.evntDraft {
            	background: #66799f none repeat scroll 0% 0%;
				text-decoration: none;
				display: inline-block;
				color: #FFF;
				padding: 7px 7px 6px;
				vertical-align: text-bottom;
				line-height: 7px;
				font-size: 12px;
				font-weight: bold;
				text-transform: uppercase;
				text-align: center;
				border-radius: 22px;
				margin-left: 10px;
            }
		</style>
		
		<script type="text/javascript">
			function likeEntry(entryId) 
			{ 
			    var isAuthenticated = $("#isAuthenticated").val();
			    if (isAuthenticated == 'false') {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Error',
		                message: '<p class="extraWord">You are not logged-in.<br/> Please login first to like / unlike. </p>',
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
			    } else {
			    	$.ajax({url:"${communityEraContext.contextUrl}/event/likeEvent.ajx?eventId="+entryId,success:function(result){
			    	    $("#likeUnike").html(result);
			    	  }});
			    }
			}

			function replyInvitation(eventId, status) 
			{ 
		    	$.ajax({url:"${communityEraContext.contextUrl}/event/replyInvitation.ajx?eventId="+eventId+"&status="+status,success:function(result){
		    	    $("#stat-"+eventId).html(result);
		    	  }});
			}

			function subscribeEvents(calendarId){
				$.ajax({url:"${communityEraContext.currentCommunityUrl}/event/followEventCalendar.ajx?id="+calendarId,success:function(result){
		    	    $("#subscribeEvents").html(result);
		    	  }});
			}
		
			function unSubscribeEvents(calendarId){
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Stop following this Event Calendar',
	                message: '<p class="extraWord">You will no longer be able to receive email alerts when new events are posted to this community.</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	label: 'Close',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                },
	                {
	                	label: 'Go',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/event/stopFollowingEventCalendar.ajx?id="+calendarId,success:function(result){
	            	    	    $("#subscribeEvents").html(result);
	            	    	  }});
	                    }
	                }]
	            });
			}

		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){

			$.ajax({url:"${communityEraContext.contextUrl}/common/allEntryCloud.ajx?toolType=EventCalendar&toolId=${command.eventCalendarId}",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 aName += "<li>";
					 aName += "<a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 aName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 $("#cloud").html(aName+"</ul>");
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 bName += "<tr><td>";
					 bName += "<span class='size-"+this['cloudSet']+"'  ><a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 bName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a></span>";
					 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
					 bName += "</tr>";
						});
				 $("#cloudList").html(bName+"</table>");

				// Hide message
		        $("#waitCloudMessage").hide();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitCloudMessage").show();
		         } 
		    });
		    
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
					$.ajax({url:"${communityEraContext.currentCommunityUrl}/event/showEvents.ajx?jPage="+num+"&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
						var sName = "";
						var myear = "";
						 $.each(result.aData, function() {
							 var rowId = this['id'];
							 sName += "<div class='paginatedList'><div class='leftImgBig'>";
							 if(this['photoPresent']){
								 sName += "<img src='${communityEraContext.contextUrl}/event/eventPoster.img?id="+rowId+"' width='260' height='120' style='width:260px; height:120px;'/>";
							 } else {
								 sName += "<img src='img/EventImg.png' width='260' height='110' style='width:260px;'/>";
							 }
	
							 sName += "</div><div class='details' style='width: 450px;'>";
							 sName += "<div class='heading' style='width: 430px;word-wrap: normal; margin-bottom: 4px;'>";
							 sName += "<a href='${communityEraContext.currentCommunityUrl}/event/showEventEntry.do?backlink=ref&amp;id="+rowId+"'>"+this['name']+"</a>";
							 if(this['status'] == 0){
							 	sName += "<span class='evntDraft'>Draft</span>";
							 }
							 sName += "</div>";
							 sName += "<div class='person'>Hosted by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' >"+this['displayName']+"</a><i class='a-icon-text-separator'></i>";
							 if(this['dateStarted'] != ""){
								 sName += this['dateStarted'];
								 if(this['dateEnded'] != ""){
									 sName += "&nbsp;to&nbsp;"+this['dateEnded'];
								 }
							 }else {
								 if(this['dateEnded'] != ""){
									 sName += "--&nbsp;"+this['dateEnded'];
								 }
							 }
							 sName += "";
							 sName += "</div><div class='person'>Location: "+this['location']+"</div><div id='stat-"+rowId+"' >";
							 if(this['status'] == 0){
								 sName += "<div class='person' style='font-size:14px;'>Guests: "+this['invited']+" invited / "+this['confirmed']+" going / "+this['mayBe']+" maybe / "+this['declined']+" declined</div>";
							 }
							 
							 if(this['host']){
								 sName += "<div class='person' style='font-size:14px; margin: 10px 0px 0px 0px; padding:0px;'><a href='${communityEraContext.currentCommunityUrl}/event/editEvent.do?id="+rowId+"' style='padding:0px;'>Edit Event</a></div>";
							} else if (this['invitee']){
								if (!this['replied']){
								 sName += "<div class='person' style='font-size:14px; margin: 10px 0px 6px 0px; padding:0px;' ><a onClick='replyInvitation(&#39;"+rowId+"&#39; , &#39;1&#39;)' href='javascript:void(0);' '>Join</a>";
								 sName += "<i class='a-icon-text-separator'></i><a onClick='replyInvitation(&#39;"+rowId+"&#39; , &#39;2&#39;)' href='javascript:void(0);' '>Maybe</a>";
								 sName += "<i class='a-icon-text-separator'></i><a onClick='replyInvitation(&#39;"+rowId+"&#39; , &#39;3&#39;)' href='javascript:void(0);' '>Decline</a></div>";
								}
							 }
							 sName += "</div>";
							 if(this['taggedKeywords']){
								 sName += "<div class='person'><i class='fa fa-tags' style='font-size: 14px; margin-right: 4px;'></i>"+this['taggedKeywords']+"</div>";
							}
							 if(this['status'] == 1){
								 sName += "<div class='entry' id='likeUnike'>";
								 if(this['likeCount'] > 1){
									 sName += "<span>"+this['likeCount']+" likes</span>";
								 } else {
									 sName += "<span>"+this['likeCount']+" like</span>";
								 }
								 if(this['likeAllowed']){
									 if(this['alreadyLike']){
									 sName += "<i class='a-icon-text-separator'></i><a href='javascript: likeEntry("+rowId+")' class='euInfoSelect' style='font-weight: bold; padding-left:0px;' >Unlike</a>";
									 } else {
										 sName += "<i class='a-icon-text-separator'></i><a href='javascript: likeEntry("+rowId+")' class='euInfoSelect' style='font-weight: bold; padding-left:0px;' >Like</a>";
									 }
								 } 
								 sName += "</div>";
							 }
							 sName += "</div></div>";
								});
						 $("#page").html(sName);
					    }});
					} 
				});
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
								<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
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
													<c:if test="${feature == 'Members'}">
														<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
													</c:if>
													<c:if test="${feature == 'Library'}">
														<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
													</c:if>
												</c:forEach>
								    		</ul>
							    		</div>
							    	</nav>
						    	</li>
							</ul>
							<div class='actions2' id='connectionInfo'>
								<div class='btngroup' >
									<c:if test="${command.member}">
										<div class='btnout' id='comaction1'>
											<a href="${communityEraContext.currentCommunityUrl}/event/addEvent.do" title="Add a new event to this community">Add new event</a></div>
										<div class='btnout' id='comaction2'>
											<a href="${communityEraContext.currentCommunityUrl}/event/addEvent.do" title="Add a new event to this community">
												<i class="fa fa-calendar-check-o" style="font-size: 14px; font-weight: bold;"></i>
											<i class="fa fa-plus" style="font-size: 14px; margin-left: -1px;"></i></a></div>
									</c:if>
									<c:if test="${command.currentUserSubscribed}">
										<div id="subscribeEvents" class='btnout'><a onclick="unSubscribeEvents(${command.eventCalendar.id});" href="javascript:void(0);" class="normalTip"
												title="Unsubscribe from email alerts for events in this community">Unfollow</a></div>
									</c:if>
									<c:if test="${not command.currentUserSubscribed}">
										<div id="subscribeEvents" class='btnout'><a onclick="subscribeEvents(${command.eventCalendar.id});" href="javascript:void(0);" class="normalTip"
										 		title="Get email alerts when new events are posted to this community">Follow</a></div>
									</c:if>
									<%-- <c:if test="${communityEraContext.userSysAdmin}">
										<div class='btnout'><a href="${communityEraContext.currentCommunityUrl}/event/feed.rss" class="normalTip"
												title="System Administrators only - View event list as RSS news feed">RSS feed</a></div>
									</c:if>
									<c:if test="${communityEraContext.userSuperAdmin}">
										<div class='btnout'><a href="${communityEraContext.contextUrl}/comm/community-cloud.do" title="Community cloud" class="normalTip">Community cloud</a></div>
									</c:if> --%>
								</div>
							</div>
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
					<div class="communities" style="padding-bottom: 6px;">
						<div class="type">
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Type:" >
									<form:optionlist options="${command.sortOptions}" />
								</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
								<input type="submit" value="Go"/>
							</form:form>
						</div>
					</div>
					
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
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
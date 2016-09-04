<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 

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
		<meta name="description" content="${communityEraContext.currentCommunity.name}. ${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, Forum, Topics, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>${communityEraContext.currentCommunity.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
			    width: 180px;
			}
			
			.gn-icon-menu::before {
    			vertical-align: -28px;
    		}
		</style>
		
	<script type="text/javascript">
			function subscribeForum(forumId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
				$.ajax({url:"${communityEraContext.currentCommunityUrl}/forum/followForum.ajx?id="+forumId,success:function(result){
		    	    $("#subscribeForum").html(result);
		    	  }});
			}
		
			function unSubscribeForum(forumId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Stop Following this Forum',
	                message: '<p class="extraWord">You will no longer be able to receive email alerts when new topics are posted to forum.</p>',
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
	                        dialogRef.close();
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/forum/stopFollowingForum.ajx?id="+forumId,success:function(result){
	            	    	    $("#subscribeForum").html(result);
	            	    	  }});
	                    }
	                }]
	            });
			}

		</script>
	
	<script type="text/javascript">
		// Initial call
		$(document).ready(function(){

			$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/cloudCommunity.ajx",dataType: "json",success:function(result){
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
					$.ajax({url:"${communityEraContext.currentCommunityUrl}/forum/showTopics.do?jPage="+num+"&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
						var sName = "";
						 $.each(result.aData, function() {
							 var isAdsPosition = false;
		   						if(i%6 == 0){
		   							isAdsPosition = true;
		   						 }
							 var rowId = this['id'];
							 sName += "<div class='paginatedList'><div class='leftImg'>";
	
							 if(this['lastPosterId'] != this['authorId']){
								 if(this['photoPresent'] == "Y"){
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['authorId']+"&backlink=ref' title='"+this['authorName']+" - Original Poster'>";
									 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['authorId']+"' style='height: 40px; width: 40px;'/></a>"; 
								 } else {
									 sName += "<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='"+this['authorName']+" - Original Poster' />";
								 }
	
								 if(this['lastPostPhotoPresent'] == "Y"){
									 sName += "<br/><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['lastPosterId']+"&backlink=ref' title='"+this['lastPosterName']+" - Most Recent Poster'>";
									 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['lastPosterId']+"' style='height: 40px; width: 40px; margin-top: 8px;'/></a>"; 
								 } else {
									 sName += "<br/><img src='img/user_icon.png' style='height: 40px; width: 40px; margin-top: 8px;' title='"+this['lastPosterName']+" - Most Recent Poster' />";
								 }
							 } else {
								 if(this['photoPresent'] == "Y"){
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['authorId']+"&backlink=ref' title='"+this['authorName']+" - Original Poster, Most Recent Poster'>";
									 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['authorId']+"' style='height: 40px; width: 40px;'/></a>"; 
								 } else {
									 sName += "<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='"+this['authorName']+" - Original Poster, Most Recent Poster' />";
								 }
							 }
	
							 
							 sName += "</div><div class='details'>";
							 sName += "<div class='heading' style='width: 520px;word-wrap: normal;'>";
							 
							 if(this['closed']){
								 sName += "<i class='fa fa-lock' style='margin-right: 8px; color: #A7A9AB;' title='This topic has now been closed for new replies'></i>";
							 }
							 if(this['sticky']){
								 sName += "<i class='fa fa-thumb-tack' style='margin-right: 8px; color: #A7A9AB;' title='This topic is pinned; it will display at the top of community forum'></i>";
							 }
							 sName += "<a href='${communityEraContext.currentCommunityUrl}/forum/forumThread.do?backlink=ref&amp;id="+rowId+"' >"+this['displaySubject']+"</a></div>";
							 sName += "<div class='person'>";
							 sName += "<span title='First post: "+this['firstPostDate']+"'>"+this['postedOn'] + "</span><i class='fa fa-circle' style='margin: 0px 8px; font-size: 6px;'></i><span title='Last post: "+this['lastPostDate']+"'>"+this['lastPostedOn']+"</span>";
							 sName += "<i class='a-icon-text-separator'></i> "+this['responseCount'] + " replies";
							 sName += "<i class='a-icon-text-separator'></i> "+this['visitors'] + " views</div>";
							 if(this['taggedKeywords']){
								 sName += "<div class='person'><i class='fa fa-tags' style='font-size: 14px; margin-right: 4px;'></i>"+this['taggedKeywords']+"</div>";
							 }
							 sName += "<div>"+this['displayBody']+"</div></div></div>";
							 if(isAdsPosition) {
								 sName += '<div class="inboxAds">';
								 sName += '<ins class="adsbygoogle" style="display:inline-block;width:728px;height:90px" data-ad-client="ca-pub-8702017865901113" data-ad-slot="4053806181"></ins>';
								 sName += '</div>';
							 }
								});
						 $("#page").html(sName);
						// Hide message
					        $("#waitMessage").hide();
					        toggleOnLoad();
					    } ,
	                	complete: function () {
						     $(".adsbygoogle").each(function () { (adsbygoogle = window.adsbygoogle || []).push({}); });
                   		},
					 	// What to do before starting
				         beforeSend: function () {
				             $("#waitMessage").show();
				         } 
					    });
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
								<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do"><i class="fa fa-comments-o" style="margin-right: 8px;"></i>Forums</a></li>
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
													<c:if test="${feature == 'Wiki'}">
														<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
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
							<div class='actions2' id='connectionInfo'>
								<div class='btngroup' >
									<c:if test="${command.member}">
										<div class='btnout' id='comaction1'><a href="${communityEraContext.currentCommunityUrl}/forum/startTopic.do" class="normalTip"
											title="Start a new topic in this forum">Start new topic</a></div>
										<div class='btnout' id='comaction2'><a href="${communityEraContext.currentCommunityUrl}/forum/startTopic.do" class="normalTip"
											title="Start a new topic in this forum"><i class="fa fa-comments-o" style="font-size: 14px;"></i>
											<i class="fa fa-plus" style="font-size: 14px; margin-left: -1px;"></i></a></div>
									</c:if>
									<c:if test="${command.currentUserSubscribed}">
										<div id="subscribeForum" class='btnout'><a onclick="unSubscribeForum(${command.forum.id});" href="javascript:void(0);" class="normalTip"
										title="Unsubscribe from email alerts from this forum">Unfollow</a></div>
									</c:if>
									<c:if test="${not command.currentUserSubscribed}">
										<div id="subscribeForum" class='btnout'><a onclick="subscribeForum(${command.forum.id});" href="javascript:void(0);" class="normalTip"
										title="Send me email alerts when new topics are posted to forum">Follow</a></div>
									</c:if>
									<%-- <c:if test="${communityEraContext.userSysAdmin}">
										<div class='btnout'><a href="${communityEraContext.currentCommunityUrl}/forum/feed.rss" class="normalTip"
											title="System Administrator tool only : View this forum content as an RSS News Feed">RSS feed</a></div>
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
									<form:dropdown path="sortByOption" fieldLabel="Sort By:">
										<form:optionlist options="${command.sortByOptionOptions}" />
									</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="submit" value="Go" class="search_btn" />
							</form:form>
						</div>
					</div>
					
					<div class="inboxAds">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RES -->
							<ins class="adsbygoogle"
							     style="display:block"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="3781277785"
							     data-ad-format="auto"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
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
					<div class="inboxAds" style="display: inline-block; width: 100%;">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
					
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
					
					<div class="inboxAds" style="display: inline-block; width: 100%;">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
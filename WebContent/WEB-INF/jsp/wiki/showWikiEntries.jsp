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
		<title>Jhapak - ${communityEraContext.currentCommunity.name}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<style type="">
			.commBanr .nav-list ul li.selected {
			    width: 180px;
			}
			
			input.titleHeader {
			    background: #FFF none repeat scroll 0% 0%;
			    border: 1px solid #DADEE2;
			    font-size: 14px;
			    padding: 8px 6px;
			    color: #20394D;
			    width: 98%;
			    margin: 12px 0px;
			}
			
			#checkAvail p.availableFail {
				font-size: 12px;
				font-weight: bold;
				color: #AC0000;
				margin-bottom: 10px
			}
			
			.topHeader .heading {
				width: 600px; 
				color: #184A72; 
				margin-bottom: 2px;
				background: transparent none repeat scroll 0% 0%;
				font-family: "Linux Libertine",Georgia,Times,serif;
			}
		</style>
		
		<script type="text/javascript">
			function subscribeWiki(wikiId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Follow this Wiki',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to follow this wiki. </p>',
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
			    	$.ajax({url:"${communityEraContext.currentCommunityUrl}/wiki/followWiki.ajx?id="+wikiId,success:function(result){
			    	    $("#subscribeWiki").html(result);
			    	  }});
			    }
			}
		
			function unSubscribeWiki(wikiId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Stop following this Wiki',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to stop following this wiki. </p>',
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
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_WARNING,
		                title: 'Stop Following this Forum',
		                message: '<p class="extraWord">You will no longer be able to receive email alerts when new entries are posted to this wiki.</p>',
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Go',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/wiki/stopFollowingWiki.ajx?id="+wikiId,success:function(result){
		            	    	    $("#subscribeWiki").html(result);
		            	    	  }});
		                    }
		                }, {
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
			    }
			}

			function writeWikiArticle(){
				BootstrapDialog.show({
	                title: 'Write an Article',
	                message: '<input id="edtTitle" name="title" class="titleHeader" type="text"/><div id="checkAvail"></div>',
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
	                	cssClass: 'btn-primary',
	                    action: function(dialogRef){
	                	var newTitle = dialogRef.getModalBody().find('#edtTitle').val();
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/wiki/updateWikiTitle.ajx?entryId=0&title="+newTitle,success:function(result){
		                        if(result.iserror) {
		                        	dialogRef.getModalBody().find('#checkAvail').html(result.titleError);
		                        } else {
		                        	var href="${communityEraContext.currentCommunityUrl}/wiki/addWiki.do?entryId="+result.entryId;
		            				window.location.href=href;
		                        }
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
						$.ajax({url:"${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do?jPage="+num+"&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
							var sName = "";
							 $.each(result.aData, function() {
								 var isAdsPosition = false;
			   						if(i%6 == 0){
			   							isAdsPosition = true;
			   						 }
								 sName += "<div class='paginatedList'><div class='leftImg'>";
	
								 if(this['photoPresent'] == "Y"){
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' /></a>"; }
								 else {
									 sName += "<img src='img/user_icon.png'  />";
								 }
								 							 
								 sName += "</div><div class='details'>";
								 sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
								 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/wikiDisplay.do?backlink=ref&amp;entryId="+this['entryId']+"' >"+this['title']+"</a></div>";
		
								 sName += "<div class='person'>Updated by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"'>"+this['lastEditedBy']+"</a> on "+this['datePosted']+"</div>";
	
								 sName += "<div class='entry'>";							
								 if(this['userCommunityAdmin'] || this['userSysAdmin']){
									 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/wikientry-delete.do?mode=u&amp;id="+this['entryId']+"'>Delete</a>";
									 sName += "<i class='a-icon-text-separator'></i>";
								 }
								 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/editWiki.do?entryId="+this['entryId']+"' >Edit</a></div>";
		
								 //if(${command.currentUserSubscribed}){
								//	 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/unsubscribe-wiki-entry.do?id="+this['entryId']+"' title='Unsubscribe from email alerts for updates to this wiki page' class='commNameRight' >Unsubscribe from email alerts for this wiki page</a>";
								// } else {
								//	 sName += "<a href='${communityEraContext.currentCommunityUrl}/wiki/addSubscriptionForWikiEntry.do?id="+this['entryId']+"' title='Subscribe for email alerts for updates to this wiki entry' class='commNameRight' >Subscribe for email alerts for this wiki page</a>";
								// }
		
								 sName += "<p>"+this['displayBody']+"</p></div></div>";
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
						<div class='actions2' id='connectionInfo'>
							<div class='btngroup' >
								<c:if test="${command.member}">
									<div class='btnout' id='comaction1'><a href="javascript:void(0);" onclick="writeWikiArticle();" class="normalTip"
										title="Write a new article for this community wiki">Write An Article</a></div>
									<div class='btnout' id='comaction2'><a href="javascript:void(0);" onclick="writeWikiArticle();" class="normalTip"
										title="Write a new article for this community wiki"><i class="fa fa-book" style="font-size: 14px; font-weight: bold;"></i>
											<i class="fa fa-plus" style="font-size: 14px; margin-left: -1px;"></i></a></div>
								</c:if>
								<c:if test="${command.currentUserSubscribed}">
									<div id="subscribeWiki" class='btnout'><a onclick="unSubscribeWiki(${command.wiki.id});" href="javascript:void(0);" class="normalTip"
									title="Unsubcribe from email alerts for this wiki">Unfollow</a></div>
								</c:if>
								<c:if test="${not command.currentUserSubscribed}">
									<div id="subscribeWiki" class='btnout'><a onclick="subscribeWiki(${command.wiki.id});" href="javascript:void(0);" class="normalTip"
									title="Get email alerts when new entries are posted to this wiki">Follow</a></div>
								</c:if>
								
								<%--<c:if test="${communityEraContext.userSysAdmin}">
									<div class='btnout'><a href="${communityEraContext.currentCommunityUrl}/wiki/feed.rss" class="normalTip"
									title="System Administrator tool only - view wiki entries as RSS news feed">RSS feed</a></div>
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
					<div class="communities"  style="padding-bottom: 6px;">
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
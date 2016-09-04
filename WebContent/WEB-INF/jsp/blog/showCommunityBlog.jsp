<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, ${command.cons.name}" />
		<meta name="keywords" content="Jhapak, blog, community" />
		<title>${command.cons.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<meta name="robots" content="index, follow">
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/newCommScreen.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
			    width: 180px;
			}
		</style>
		
		<script type="text/javascript">
			function subscribeBlog(consId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Following this Blog',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to start following this blog. </p>',
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
					$.ajax({url:"${communityEraContext.currentCommunityUrl}/blog/followBlogCons.ajx?id="+consId,success:function(result){
			    	    $("#subscribeBlog").html(result);
			    	    $('.normalTip').qtip({ 
						    content: {
						        attr: 'title'
						    },
							style: {
						        classes: 'qtip-tipsy'
						    }
						});
			    	  }
			    	 });
			    }
			}
		
			function unSubscribeBlog(consId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Stop following this Blog',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to stop following this blog. </p>',
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
		                title: 'Stop following this Blog',
		                message: '<p class="addTagHeader">You will no longer be able to receive email alerts for blog entries published to this community.</p>',
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
		                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/blog/stopFollowingBlogCons.ajx?id="+consId,success:function(result){
		            	    	    $("#subscribeBlog").html(result);
		            	    	    $('.normalTip').qtip({ 
		        					    content: {
		        					        attr: 'title'
		        					    },
		        						style: {
		        					        classes: 'qtip-tipsy'
		        					    }
		        					});
		            	    	  }});
		                    }
		                }]
		            });
			    }
			}

			function applyFilter(filterUrl){
				var fTagList = document.getElementById("fTagList").value;
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&fTagList="+fTagList+"&toggleList="+toggleList;
				window.location.href = filterUrl;
			}

			var count = 2;
			function infinite_scrolling(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    	var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
							$.ajax({url:"${communityEraContext.currentCommunityUrl}/blog/viewBlog.do?jPage="+count+"&userId=${command.userId}",dataType: "json",success:function(result){
								var sName = "";
								 $.each(result.aData, function() {
									 var rowId = this['id'];
									 sName += "<div class='paginatedListLarge'><div class='leftImgLarge'>";
									 sName += "<a href='${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id="+rowId+"&backlink=ref' >";
									 if(this['imageCount'] > 0){
										 sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId="+rowId+"' />";
									 } else if(this['photoPresent'] == "Y"){
										 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id="+this['posterId']+"' />"; 
								 	 } else {
										 sName += "<img src='img/user_icon.png' height='150' width='150' style='border-radius: 50%;' />";
									 }
									 sName += "</a></div><div class='details'>";
									 sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
									 sName += "<a href='${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id="+rowId+"&backlink=ref";
									 if(this['userId'] > 0){
										 sName += "&amp;userId="+this['userId']; 
									 }

									 sName += "'>"+this['title']+"</a></div>";

									 sName += "<div class='person'>by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
									 sName += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a> <i class='a-icon-text-separator'></i> "+this['postedOn'];
									 sName += "</div>";

									 sName += "<p style='padding: 6px 0px;'>"+this['displayBody']+"</p>";
									 
									 sName += "<div class='person'>"+this['visitors']+" view";
									 if(this['visitors'] > 1){
										 sName += "s";
									 }
									 sName += "<i class='a-icon-text-separator'></i>";

									 if(this['likeCount'] > 1){
										 sName += this['likeCount'] + " likes";
									 } else {
										 sName += this['likeCount'] + " like";
									 }
									 if(this['commentCount'] > 0){
										 sName += "<i class='a-icon-text-separator'></i><a href='${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id="+rowId+"#showComments";
										 if(this['userId'] > 0){
											 sName += "&amp;userId="+this['userId'];
										 }
										 sName += "'>"+this['commentCount'];
										 if(this['commentCount'] = 1){
										 sName += " comment</a>";
										 } else {
											 sName += " comments</a>";
										 }
									 }
									 if(this['commentCount'] == 0){
										 sName += "<i class='a-icon-text-separator'></i> 0 comment";
									 }
									 sName += "</div>";
									 sName += "</div></div>";
										});
								 $("#rowSection").append(sName);
								// Hide message
							        $("#waitMessage").hide();
							        
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
							        $('.normalTip').qtip({ 
									    content: {
									        attr: 'title'
									    },
										style: {
									        classes: 'qtip-tipsy'
									    }
									});
							        toggleOnLoad();
								    },
								    complete: function () {
									     count++;
			                    	},
								 	// What to do before starting
							         beforeSend: function () {
							             $("#waitMessage").show();
							         } 
						    });
				        }
				    }
				}

			// debounce multiple requests
			var _scheduledRequest = null;
			function infinite_scroll_debouncer(callback_to_run, debounce_time) {
			    debounce_time = typeof debounce_time !== 'undefined' ? debounce_time : 200;
			    if (_scheduledRequest) {
			        clearTimeout(_scheduledRequest);
			    }
			    _scheduledRequest = setTimeout(callback_to_run, debounce_time);
			}
			// usage
			$(window).scroll(function() {
		        infinite_scroll_debouncer(infinite_scrolling, 400);
		    });
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){
	        $('.dynaDropDown').each(function() {
		         $(this).qtip({
		            content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: api.elements.target.attr('title'), // Use href attribute as URL
		                        type: 'GET', // POST or GET
		                        dataType: 'json', // Tell it we're retrieving JSON
		                        data: {
		                        }
		                    })
		                    .then(function(data) {
		                    	var mName = "";
		                        api.set('content.text', data.optionInfo);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', 'Loading...');
		                    });
		                    return 'Loading...'; // Set some initial text
		                }
		            },
		            position: {
		            	viewport: $(window),
		            	my: 'top center',  // Position my top left...
		                at: 'bottom center', // at the bottom right of...
		                target: '', // Position it where the click was...
						adjust: { 
							mouse: false 
						} // ...but don't follow the mouse
					},
					style: {
				        classes: 'qtip-light myCustomClass3'
				    },
				    hide: {
		                fixed: true,
		                delay: 300
		            }
		         });
		     });

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?blogId=${command.cons.id}",dataType: "json",success:function(result){
				var temp = "";
				
				$.each(result.aData, function() {
					
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref'  class='memberInfo' ";
						temp += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref'  class='memberInfo' ";
						 temp += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
					 }
					});
				 $("#authorsList").html(temp);
				 
				// Hide message
		        $("#waitBLAthMessage").hide();
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
		             $("#waitBLAthMessage").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.contextUrl}/common/allEntryCloud.ajx?toolType=CommunityBlog&toolId=${command.cons.id}",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 aName += "<li>";
					 aName += "<a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 aName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='normalTip size-"+this['cloudSet']+"' >"+this['tagText']+"</a>";
					 aName += "</li>";
						});
					if(aName == "<ul>"){
						$("#cloud").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
					}else{
						$("#cloud").html(aName+"</ul>");
					}
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 bName += "<tr><td>";
					 bName += "<span><a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 bName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='normalTip size-1' >"+this['tagText']+"</a></span>";
					 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
					 bName += "</tr>";
						});
				 if(bName == "<table >"){
					$("#cloudList").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
				}else{
					$("#cloudList").html(bName+"</table>");
				}
				// Hide message
		        $("#waitCloudMessage").hide();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitCloudMessage").show();
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
								<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>Blogs</a></li>
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
										<div class='btnout' id='comaction1' ><a href="${communityEraContext.currentCommunityUrl}/blog/addEditBlog.do?mode=c" >New Blog Entry</a></div>
										<div class='btnout' id='comaction2' >
											<a href="${communityEraContext.currentCommunityUrl}/blog/addEditBlog.do?mode=c" ><i class="fa fa-quote-left" style="font-size: 14px;"></i>
											<i class="fa fa-plus" style="font-size: 14px; margin-left: -1px;"></i></a></div>
									</c:if>
									<c:if test="${command.currentUserSubscribed}">
										<div id="subscribeBlog" class='btnout' ><a href="javascript:void(0);" onclick="unSubscribeBlog(${command.cons.id})" class="normalTip"
												title="Unsubscribe from user blog entries published to this community">Unfollow</a></div>
									</c:if>
									<c:if test="${not command.currentUserSubscribed}">
										<div id="subscribeBlog" class='btnout' ><a href="javascript:void(0);" onclick="subscribeBlog(${command.cons.id})" class="normalTip"
										 		title="Subscribe for email alerts for blog entries published to this community">Follow</a></div>
									</c:if>
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
				<input type="hidden" id="pgCount" value="${command.pageCount}" />
				<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
				<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
				<div class="commSection" id="rowSection">
					<div class="inboxAds">
						<c:if test="${communityEraContext.production}">
							<!-- CERA -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:728px;height:90px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4053806181"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
					<c:forEach items="${command.scrollerPage}" var="row" > 
						<div class="paginatedListLarge" >
							<div class='leftImgLarge'>
								<a href='${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id=${row.id}&backlink=ref' >
									<c:choose>
										<c:when test="${row.imageCount > 0}">	
											<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId=${row.id}' />	
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${row.photoPresent == 'Y'}">	
													<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${row.posterId}' />	
												</c:when>
												<c:otherwise>
													<img src='img/user_icon.png' height='150' width='150' style='border-radius: 50%;' />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose> 
								</a>
							</div>
							<div class='details'>
								<div class='heading' style='width: 540px;word-wrap: normal;'>
									<a href="${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id=${row.id}&backlink=ref" >${row.title}</a>
								</div>
								<div class='person'>by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}&backlink=ref' class='memberInfo' 
									title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${row.posterId}'>${row.displayName}</a>
									 <i class='a-icon-text-separator'></i> ${row.postedOn}
								</div>
								<p style='padding: 6px 0px;'>${row.displayBody}</p>
								<div class='person'>${row.visitors} view
									<c:if test="${row.visitors > 1}">s</c:if>
									<i class='a-icon-text-separator'></i>
									<c:choose>
										<c:when test="${row.likeCount > 1}">	
											${row.likeCount} likes	
										</c:when>
										<c:otherwise>
											${row.likeCount} like
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${row.commentCount > 0}">	
											<i class='a-icon-text-separator'></i><a href='${communityEraContext.currentCommunityUrl}/blog/blogEntry.do?id=${row.id}#showComments'>${row.commentCount}
											<c:choose>
												<c:when test="${row.commentCount > 1}">	
													comments	
												</c:when>
												<c:otherwise>
													comment
												</c:otherwise>
											</c:choose>
											</a>	
										</c:when>
										<c:otherwise>
											<i class='a-icon-text-separator'></i> 0 comment
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<c:if test="${row.adsPosition}">
							<div class="inboxAds">
								<c:if test="${communityEraContext.production}">
									<!-- CERA -->
									<ins class="adsbygoogle"
									     style="display:inline-block;width:728px;height:90px"
									     data-ad-client="ca-pub-8702017865901113"
									     data-ad-slot="4053806181"></ins>
									<script>
									(adsbygoogle = window.adsbygoogle || []).push({});
									</script>
								</c:if>
							</div>
						</c:if>
	   				</c:forEach>
					</div>
					<div id="waitMessage" style="display: none;">
						<div class="cssload-square" >
							<div class="cssload-square-part cssload-square-green" ></div>
							<div class="cssload-square-part cssload-square-pink" ></div>
							<div class="cssload-square-blend" ></div>
						</div>
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
							<span onclick="return false" class="normalTip" title="Blog Authors">Blog Authors</span>
						</div>
						<div id="authorsList" style="padding: 4px;" ></div>
						<div id="waitBLAthMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div class="view">
							<a href="${communityEraContext.currentCommunityUrl}/blog/showBlogAuthors.do?backlink=ref">View All (${command.authorsCount} Authors)</a>
						</div>
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
						</ul> <br/>
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
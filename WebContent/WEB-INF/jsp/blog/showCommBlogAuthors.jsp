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
		<meta name="description" content="Jhapak, blog, ${communityEraContext.currentCommunityName}" />
		<meta name="keywords" content="Jhapak, blog, community" />
		<title>${communityEraContext.currentCommunityName}'s invitations - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		
 		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
 		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
 		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.qtip {
			    max-width: 380px;
			    max-height: unset;
			}
			
			.commBanr .nav-list ul li.selected {
			    width: 200px;
			}
		</style>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){

				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}",dataType: "json",success:function(result){
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
			                        api.set('content.text', data.connInfo);
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
	
		<script type="text/javascript">
		
	    var count = 2;
		function infinite_scrolling_allConn(){
			if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
		    		var total = document.getElementById('pgCount').value;
                	if (count > total){
                		return false;
		        	} else {
		            	$.ajax({
		                	url:"${communityEraContext.currentCommunityUrl}/connections/showInvitations.do?jPage="+count,
		                	dataType: "json",
		                	success:function(result){ 
		    					var sName = "";
		   					 $.each(result.aData, function() {
		   						 var rowId = this['id'];
		   						 var trclass = '';
		   						 if(this['evenRow']){
		   							 trclass = 'Alt';
		   						 }
		   						 sName += "<div class='userList"+trclass+"' id='connResult-"+rowId+"'><div class='leftImg'>";
		   						 if(this['photoPresent']){
		   							 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id="+rowId+"' width='120' height='120' /></a>"; }
		   						 else {
		   							 sName += "<img src='img/user_icon.png'  width='120' height='120' />";
		   						 }
		   						 sName += "</div><div class='details'><div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref'>"+this['firstName']+" "+this['lastName']+"</a></div>";
		   						 if(this['connectionCount'] == 0){
		   							 sName += "<div class='person'>"+this['connectionCount']+" Connection</a></div>";
		   						 }
		   						 if(this['connectionCount'] == 1){
		   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connection</a></div>";
		   						 }
		   						 if(this['connectionCount'] > 1){
		   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connections</a></div>";
		   						 }
		   						 //sName += "<div id='connectionInfo-"+rowId+"' class='connectionInfo'>"+this['connectionInfo']+"</div>";
		   						 sName += "</div></div>";
		   							});
		   					 $("#innerSection").append(sName);
		   					$("#waitMessage").hide();
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
		   			                        api.set('content.text', data.connInfo);
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

		   		        $('.normalTip').qtip({ 
		   				    content: {
		   				        attr: 'title'
		   				    },
		   					style: {
		   				        classes: 'qtip-tipsy'
		   				    }
		   				});
		                	} ,
		                	beforeSend: function () {
					             $("#waitMessage").show();
					         },
		                	complete: function () {
		                		count++;
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
				infinite_scroll_debouncer(infinite_scrolling_allConn, 400);
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
								<li >
									<a href="${communityEraContext.currentCommunityUrl}/blog/showBlogAuthors.do" 
										class='dynaDropDown' title='community/communityOptions.ajx?currId=1004&commId=${communityEraContext.currentCommunity.id}' >
											Blog Authors (${command.rowCount})<i class="fa fa-sort-down" style="margin-left: 8px; font-size: 20px;"></i>
									</a>
								</li>
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
							<span class='firLevAlt' title="Blog authors list" class="normalTip" >
								Blog Authors <span id="allConnectionCount">(${command.rowCount})</span>
							</span>
						</div>
						<input type="hidden" id="pgCount" value="${command.pageCount}" />
						<div id="innerSection">
							<c:forEach items="${command.scrollerPage}" var="row">
								<c:if test="${row.oddRow}"> <c:set var="trclass" value='class="userList"' /></c:if>
								<c:if test="${row.evenRow}">	<c:set var="trclass" value='class="userListAlt"' /></c:if>
								<div ${trclass} id='connResult-${row.id}'>
									<div class='leftImg'>
										<c:choose>
											<c:when test="${row.photoPresent}">
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref' >
												<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${row.id}' width='120' height='120' />
											</a>
											</c:when>
											<c:otherwise>
												<img src='img/user_icon.png'  width='120' height='120' />
											</c:otherwise>
										</c:choose>
									</div>
									<div class='details'>
										<div class='heading'>
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref'>${row.firstName} ${row.lastName}</a>
										</div>
										<c:if test="${row.connectionCount  == 0}">
											<div class='person'>${row.connectionCount} Connection</div>
										</c:if>
										<c:if test="${row.connectionCount  == 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connection</a>
											</div>
										</c:if>
										<c:if test="${row.connectionCount  > 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connections</a>
											</div>
										</c:if>
								 		<%--<div id='connectionInfo-${row.id}' class='connectionInfo'>${row.connectionInfo}</div> --%>
								 	</div>
								</div>
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
						
					</div>
				</div> 
				
				<div class="right-panel">
					<div class="inbox">
						<div class="eyebrow">
							<span onclick="return false" >Community Members</span>
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
							<a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do?backlink=ref">View All (${command.community.memberCount} Persons)</a>
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
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
		<title>${communityEraContext.currentCommunity.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
				width: 200px;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry span {
				color: #A7A9AB;
				float: right;
			}
			.intHeader {
				height: 40px;
				background: none repeat scroll 0% 0% #EEF3F6;
				clear: both;
				text-decoration: none;
			}
			
			.intHeader form {
			    color: #008EFF;
			    font-size: 13px;
			    font-weight: normal;
			    padding: 8px;
			}
			
			.intHeader form label {
			    color: #707070;
			    float: left;
			    font-size: 12px;
			    font-weight: normal;
			    height: 20px;
			    line-height: 20px;
			    margin-right: 6px;
			}
			
			.intHeader form select {
			    border: 1px solid #D6D6D6;
			    background: none repeat scroll 0% 0% #F2F2F2;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 150px;
			    float: left;
			}
			
			.intHeader form input[type="submit"] {
			    background: none repeat scroll 0% 0% #8E9BA4;
			    border: medium none;
			    cursor: pointer;
			    color: #FFF;
			    font-size: 12px;
			    font-weight: bold;
			    padding: 3px 5px;
			}
		</style>
		
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
			function handleMemberRequest(requestId, actionType){
				$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/membershipDecision.ajx?requestId="+requestId+"&actionType="+actionType,success:function(result){
					$("#requestInfo-"+requestId).html("<span style='font-size: 14px; margin-right: 20px;'>"+result.returnString+"</span>");
		    	  }});
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
							<li><a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do"
								class='dynaDropDown' title='community/communityOptions.ajx?currId=2005&commId=${communityEraContext.currentCommunity.id}'>
									Membership requests (${command.rowCount})<i class="fa fa-sort-down" style="margin-left: 8px; font-size: 20px;"></i> 
									</a>
							</li>
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
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.currentCommunityUrl}/home.do">Home</a></li>
							<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do">Blogs</a></li>
							<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Events</a></li>
							<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do">Forums</a></li>
							<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do">Library</a></li>
							<li class="selected"><a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do">Membership requests (${command.rowCount})</a>
								<span class='dynaDropDown' title='community/communityOptions.ajx?currId=2005&commId=${communityEraContext.currentCommunity.id}'><span class='ddimgWht'/></span>
							</li>
							<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do">Wikis</a></li>  
						</ul>
					</div>
				</div>
			</div>
			<div id="container">
				<div class="left-panel">
					<div class="commSection">
						<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
							<div class="intHeader">
								<span style="font-size:12px; margin-top:0px; padding:10px; float: right; color: #AAA6A6; font-weight: bold;">
									<c:if test="${command.rowCount  == 1}">
									${command.rowCount} Request
									</c:if>
									<c:if test="${command.rowCount  > 1}">
									${command.rowCount} Requests
									</c:if>
								</span>
								<span class='firLev'>
									<form:form showFieldErrors="true">
										<form:dropdown path="filterOption" fieldLabel="Filter By:">
											<form:optionlist options="${command.filterOptions}" />
										</form:dropdown>
										<form:dropdown path="sortByOption" fieldLabel="Sort By:">
											<form:optionlist options="${command.sortByOptionOptions}" />
										</form:dropdown>
										<input type="hidden" name="page" value="${command.page}" />
										<input type="hidden" id="filterOption" name="filterOption" value="${command.filterOption}" />
										<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
										<input type="submit" value="Go"/>
									</form:form>
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
										<div class='details' style="width: 222px;">
											<div class='heading'>
												<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref'>${row.firstName} ${row.lastName}</a>
											</div>
											<div class='person'>Registered since ${row.registredDate}</div>
											<div class='person'>Requested on ${row.requestedDate}</div>
									 		<div id='requestInfo-${row.requestId}' class='connectionInfo'>${row.requestInfo}</div>
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
				
				<div class="right-panel" style="margin-top: 0px;">
				</div>	
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
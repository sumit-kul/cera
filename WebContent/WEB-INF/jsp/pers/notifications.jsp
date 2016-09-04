<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${command.user.fullName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, ${command.user.fullName}" />
		<meta name="keywords" content="Jhapak, blog, community, ${command.user.fullName}" />
		<title>${command.user.fullName}'s Blogs - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/newStyle.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/accord.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			#container .left-panel .commSection .paginatedList .details {
			    width: 624px;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry a {
			    margin: 8px;
			}
			
			
			#container .right-panel .pannel {
			    float: left;
				width: 100%;
				padding-bottom: 8px;
			}
			
			#container .right-panel .pannel .pannelHeadder a {
			    color: #696969;
			    font-size: 12px;
			}
			
			#container .right-panel .pannel .pannelHeadder a:hover {
			    color: #66799f;
				text-align: center;
			}
			
			#container .left-panel .commSection .communities .type form {
				float: right;
				clear: none;
			}
			
			#container .left-panel .commSection .paginatedList:hover {
				cursor: pointer;
				background: #d8e3ea none repeat scroll 0% 0%;
			}
		</style>	
		
		<script type="text/javascript">
		</script>
	
		<script type="text/javascript">
		// Initial call
		$(document).ready(function () {
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
						$.ajax({url:"${communityEraContext.contextUrl}/pers/notifications.ajx?jPage="+num,dataType: "json",success:function(result){
						var sName = "";
						 $.each(result.aData, function() {
							 var rowId = this['blogId'];
							 sName += "<li class='paginatedList'><div class='leftImg'>";
							 if(this['photoPresent'] == "Y"){
								 if(this['commId'] > 0){
									 sName += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['commId']+"'/></div>";
								 } else {
									 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['ownerId']+"' /></div>";
								 }
							} else {
								 if(this['commId'] > 0){
									 sName += "<img src='img/community_Image.png' /></div>";
								 } else {
									 sName += "<img src='img/user_icon.png' /></div>";
								 }
							 }
	   						sName += "</div>";
							sName += "</li>";
								});
						// Hide message
				        $("#waitMessage").hide();
				        $('.normalTip').qtip({ 
						    content: {
						        attr: 'title'
						    },
							style: {
						        classes: 'qtip-tipsy'
						    }
						});
						
				        $('.memberInfo').each(function() {
				        	$(".qtip").hide();
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
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<div class="commSection">
					<div class="abtMe">
						<div class="cvrImg">
							<c:if test="${command.user.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${command.user.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}&backlink=ref" >${command.user.firstName} ${command.user.lastName}</a>
							</h2>
						</div>
						<div class="groups">
							<div class="picture">
								<c:choose>
									<c:when test="${command.user.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.user.id}" />
										</c:url>
										<img src="${photoUrl}"  width='160' height='160' style=""/> 
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png'  width='160' height='160'/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:if test="${communityEraContext.currentUser.id != command.user.id}">
							<c:if test="${command.contactionAllowed}">				
								<div class='actions3' id='connectionInfo'>${command.returnString}</div>
							</c:if>
                       	</c:if>
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					<div class="communities" style="padding: 0px;">
						<div class="headerIcon">
							<i class="fa fa-bell" ></i>
						</div>
						<div class="type">
							<h4>Your Notifications</h4>
						</div>
					</div>
					
					<div class="rowLine" id="rowSection">
						<c:forEach items="${command.scrollerPage}" var="row">
							<div class='paginatedList' onclick="goToNotification(&#39;${row.itemUrl}&#39;)">
								<div class='leftImg'>
									<c:choose>
										<c:when test="${row.logoPresent}">						 
						 					<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId=${row.communityId}' style='height: 40px; width: 40px;'/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${row.photoPresent}">						 
								 					<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.posterId}' style='height: 40px; width: 40px;'/>
												</c:when>
												<c:otherwise>
													<img src='img/user_icon.png' style='height: 40px; width: 40px;' />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</div>
									
								<div class='details' style="width: 670px;">
									<div class='heading' style='word-wrap: normal; line-height: 1.4; width: 670px;'>${row.itemTitleDisplay}</div>
									<div class='person'>${row.datePostedOn}</div>
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
			<div class="right-panel" style="margin-top: 0px;">
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
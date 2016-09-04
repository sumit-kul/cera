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
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - Calendar</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<link rel="stylesheet" type="text/css" href="css/fullcalendar.css" />
		<link rel="stylesheet" media="print" href="css/fullcalendar.print.css" />
		<script type="text/javascript" src="js/moment.min.js"></script>
		<script type="text/javascript" src="js/fullcalendar.min.js"></script>
		
		<style type="">
			#container .left-panel .nav-list ul li.selected {
			    width: 160px;
			}
			
			#container .left-panel .commSection .paginatedList p {
				font-size: 14px;
			}
			
			#container .left-panel .commSection .paginatedList p a {
				margin-right: 0px;
			}
			
			.fc-toolbar {
			    text-align: center;
			    margin-bottom: 1em;
			    padding-top: 0.4em;
			}
		</style>
		
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
			$('#calendar').fullCalendar({
					header: {
						left: 'prev,next today',
						center: 'title',
						right: 'month,basicWeek,basicDay'
					},
					weekNumbers: true,
					businessHours: true, // display business hours
					editable: true,
					eventLimit: true, // allow "more" link when too many events
					selectable: true,
					events: {
						url: '${communityEraContext.contextUrl}/pers/eventPannel.ajx',
						success: function(data) {
						},
						error: function() {
							$('#script-warning').show();
						}
					},
					loading: function(bool) {
						$('#loading').toggle(bool);
					}
				});
			
			$.ajax({url:"${communityEraContext.contextUrl}/common/mediaPannel.ajx?profileId=${command.user.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					temp += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['mediaId']+"' width='89' height='89' style='padding: 3px; border-radius: 10px;' />";
					});
				if(result.aData.length > 0){
					$("#mediaList").html(temp);
				} else {
					$("#mediaList").html("<span style ='font-size: 12px; padding-left: 64px;'>No Media found</span>");
				}
		        $("#waitMediaMessage").hide();
			    },
		         beforeSend: function () {
		             $("#waitMediaMessage").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?pymkId=${command.user.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					temp += "<div style='width: 100%; display: inline-block;'>";
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' ";
						temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
						temp += this['name'];
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'  style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
						 temp += "<div style=''><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' ";
						 temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
						 temp += this['name'];
						 temp += "</a></div>";
					 }
					temp += "</div>";
					});
				 $("#pymkList").html(temp);
				 
				// Hide message
		        $("#waitPymkMessage").hide();
		        memberInfoQtip();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitPymkMessage").show();
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
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
				
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
					<div class="nav-list" style="margin-bottom:6px;">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px; margin: 4px 0px 10px 0px;" id="inputForm">
						<div id="calendar" style="padding: 4px;" ></div>
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
				<div class="inbox">
					<div class="eyebrow">
						<span onclick="return false" >People You May Know</span>
					</div>
					<div id="pymkList" style="padding: 4px;" ></div>
					<div id="waitPymkMessage" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
					<div class="view">
						<a href="${communityEraContext.contextUrl}/pers/pymk.do">View All</a>
					</div>
				</div>
				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
					<div class="eyebrow">
						<span onclick="return false" >Media Gallery</span>
					</div>
					<div id="mediaList" style="padding: 4px;" ></div>
					<div id="waitMediaMessage" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
					<div class="view">
						<a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?backlink=ref&id=${command.user.id}">View Gallery</a>
					</div>
				</div>
				
				<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
			</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
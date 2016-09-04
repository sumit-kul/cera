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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, ${command.owner.fullName}" />
		<meta name="keywords" content="Jhapak, blog, free blog, personal blog, weblog, create blog, new blog, blog, community, ${command.owner.fullName}" />
		<title>${command.owner.fullName}'s Blogs - Jhapak</title>
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
		
		<link rel="stylesheet" media="all" type="text/css" href="css/newStyle.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/accord.css" />
		
		<script type="text/javascript" src="js/jquery.accordion.js"></script>
		<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<noscript>
			<style>
				.st-accordion ul li{
					height:auto;
				}
				.st-accordion ul li > a span{
					visibility:hidden;
				}
			</style>
		</noscript>
		
		<style type="text/css">
			#container .left-panel .nav-list ul li.selected {
			    width: 180px;
			}

			#container .left-panel .commSection .paginatedList .details {
			    width: 624px;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry a {
			    margin: 8px;
			}
			
			.st-accordion ul li {
				height: 90x;
			}
			
			.st-accordion ul li>a {
				line-height: 75px;
				top: -30px;
			}
			
			.st-content {
			    margin-top: -20px;
			}
			
			.st-accordion ul li span.person {
			    top: 30px;
			}
			
			.st-accordion ul li span.person2 {
			    top: 46px;
			}
			
			.st-content .bottomRight {
			    margin: 0px 0px 0px 430px;
			    float: right;
			    width: unset;
			}
			
			.st-content p {
				padding: 0px;
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
		</style>	
		
		<script type="text/javascript">
			function applyFilter(filterUrl){
				var fTagList = document.getElementById("fTagList").value;
				var sortOption = document.getElementById("sortByOption").value;
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&fTagList="+fTagList+"&sortByOption="+sortOption+"&toggleList="+toggleList;
				window.location.href = filterUrl;
			}
		</script>
	
		<script type="text/javascript">
		// Initial call
		$(document).ready(function () {

			$.ajax({url:"${communityEraContext.contextUrl}/blog/blogPannel.ajx?type=top",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='30' height='30' style='padding: 3px; float: left;' ";
					temp += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'/>";
					temp += "<div class='pannelTitle' style='width: 86%; word-wrap: normal;'>";
					 if(this['communityId'] > 0){
						 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 } else {
						 temp += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 }
					 temp += "</div></div>";
					});
				temp += "</div>";
				$("#topStoriesList").html(temp);
		        $("#waitTpStories").hide();
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
		         beforeSend: function () {
		             $("#waitTpStories").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.contextUrl}/blog/blogPannel.ajx?type=latest",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='30' height='30' style='padding: 3px; float: left;' ";
					temp += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'/>";
					temp += "<div class='pannelTitle' style='width: 86%; word-wrap: normal;'>";
					 if(this['communityId'] > 0){
						 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 } else {
						 temp += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 }
					 temp += "</div></div>";
					});
				temp += "</div>";
				$("#latestPostsList").html(temp);
		        $("#waitLtPosts").hide();
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
		         beforeSend: function () {
		             $("#waitLtPosts").show();
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
						$.ajax({url:"${communityEraContext.contextUrl}/blog/personalBlog.ajx?jPage="+num+"&id="+$("#userId").val()+"&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
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
							 var bName = this['blogName'];
								if (bName.length > 81) {
									bName = bName.substring(0,80)+"...";
								} else {
									bName = bName;
								}
							 sName += "<a href='#'><span class='toggleHeader'>"+bName+"</span><span class='st-arrow'>Open or Close</span></a>";
	
							 if(this['member']){
								 sName += "<span class='person'>Started by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['ownerId']+"&backlink=ref' ";
								 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['ownerId']+"' class='memberInfo' >"+this['ownerName']+"</a> on "+this['createdOn'];
								 sName += "</span>";
							 } else {
								 sName += "<span class='person'>Started on "+this['createdOn'];
								 sName += "</span>";
							 }
	
							 sName += "<span class='person2'>";
							 
							 if(this['entryData'].length > 0){
								 sName += this['entryData'].length;
								 if(this['entryData'].length == 1){
									 sName += " post";
								 } else {
									 sName += " posts";
								 }
							 } else {
								 sName += "0 post";
							 }
	
							 sName += "</span>";
	
							 if(this['communityName'] == ""){
								 sName += "<span class='person2' style='top: 60px;'><span style='font-size: 12px; font-weight: bold;'>Personal blog</span></span>";
							 } else {
								 var comm = this['communityName'];
								 if(comm.length > 60){
									 comm = comm.substring(0,60)+"...";
								 }
								 sName += "<span class='person2' style='top: 60px;'>Community blog :&nbsp;";
								 sName += "<span style='font-size: 12px; font-weight: bold;' >'"+comm+"'</span>";
								 sName += "</span>";
							 }
							 
							sName += "<div class='st-content'>";
							if(this['member']){
								sName += "<p style='width: 640px; margin-bottom: 4px; padding: 6px;'>"+this['displayBody']+"</p>";
							}
	
							sName += "<div class='bottomRight' style='padding: 0px 10px 10px ; word-wrap: break-word;'>";
							if(this['commId'] > 0){
								 sName += "<a class='arrow normalTip' href='${communityEraContext.contextUrl}/cid/"+this['commId']+"/blog/viewBlog.do' >View this Blog</a></div>";
							 } else {
								 sName += "<a class='arrow normalTip' href='${communityEraContext.contextUrl}/blog/viewBlog.do?bid="+rowId+"' >View this Blog</a></div>";
							 }
							 
							var commId = this['commId'];
							if(this['member']){
								$.each(this['entryData'], function() {
									var rowId = this['id'];
									sName += "<div class='paginatedList' style=''><div class='leftImg'>";
									 
									 if(this['photoPresent'] == "Y"){
										 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' style='height: 35px; width:35px;' /></a>"; 
									 } else {
										 sName += "<img src='img/user_icon.png'  style='height: 35px; width:35px;' />";
									 }
									 sName += "</div><div class='details' style='width: 576px;'>";
									 sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
									 if(commId > 0){
										 sName += "<a href='${communityEraContext.contextUrl}/cid/"+commId+"/blog/blogEntry.do?id="+rowId+"'>"+this['title']+"</a>";
									 } else {
										 sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"'>"+this['title']+"</a>";
									 }
									 sName += "</div>";
									 sName += "<div class='person'>Posted by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
									 sName += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a> <i class='a-icon-text-separator'></i> "+this['postedOn']+"</div>";
	
										if(this['displayBody'].length > 300) {
											sName += "<p>"+this['displayBody'].substring(0,300)+"..."+"</p>";
										} else {
											sName += "<p>"+this['displayBody']+"</p>";
										}
									 
									 sName += "</div></div>";
		   						});
							}
	   						
	   						sName += "</div>";
							sName += "</li>";
								});
						 $("#accordList").html(sName);
						// Hide message
				        //$("#waitMessage").hide();
				        //toggleOnLoad();
				        $('#st-accordion').accordion({
	   						oneOpenedItem	: true
	   					});
				        //$("#waitMessage").hide();
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
	
	<script type="text/javascript">
			function subscribePersonalBlog(blogId, blogOwnerFirstName){
				$.ajax({url:"${communityEraContext.contextUrl}/blog/followBlog.ajx?id="+blogId+"&blogOwnerFirstName="+blogOwnerFirstName,success:function(result){
		    	    $("#subscribePersonalBlog").html(result);
		    	  }});
			}
		
			function unSubscribePersonalBlog(blogId, blogOwnerFirstName){
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Stop following this Blog',
	                message: '<p class="extraWord">You will no longer be able to receive email alerts for this blog.</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	label: 'Go',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                        $.ajax({url:"${communityEraContext.contextUrl}/blog/stopFollowingBlog.ajx?id="+blogId+"&blogOwnerFirstName="+blogOwnerFirstName,success:function(result){
	            	    	    $("#subscribePersonalBlog").html(result);
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
		
		</script>
		
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<div class="commSection">
					<div class="abtMe">
						<div class="cvrImg">
							<c:if test="${command.owner.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${command.owner.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.owner.id}&backlink=ref" >${command.owner.firstName} ${command.owner.lastName}</a>
							</h2>
						</div>
						<div class="groups">
							<div class="picture">
								<c:choose>
									<c:when test="${command.owner.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.owner.id}" />
										</c:url>
										<img src="${photoUrl}"  width='160' height='160' style=""/> 
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png'  width='160' height='160'/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:if test="${communityEraContext.currentUser.id != command.owner.id}">
							<c:if test="${command.contactionAllowed}">				
								<div class='actions3' id='connectionInfo'>${command.returnString}</div>
							</c:if>
                       	</c:if>
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.owner.id}">Communities</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.owner.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.owner.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.owner.id}">Gallery</a></li>
						</ul>
					</div>
					<div class="communities" style="padding: 0px;">
						<div class="headerIcon">
							<i class="fa fa-quote-left" ></i>
						</div>
						<div class="type">
							<h4>${command.owner.fullName}'s Blogs</h4>
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Sort By:">
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="hidden" id="userId" name="id" value="${command.id}" />
								<input type="hidden" name="page" value="${command.page}" />
								<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
								<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
								<input type="submit" value="Go" class="search_btn" />
							</form:form>
						</div>
					</div>
					
					<div id="page"><div class="wrapper"><div id="st-accordion" class="st-accordion"><ul id="accordList"></ul></div></div></div>
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
			<div class="right-panel" style="margin-top: 0px;">
				<a href="${communityEraContext.contextUrl}/pers/startBlog.do" class="btnmain normalTip" style="width: 100%; margin-bottom: 10px;" title="Start a new blog">
					<i class="fa fa-quote-left" ></i><i class="fa fa-plus" style="margin-right: 8px; font-size: 10px; margin-left: -1px;"></i>Start a Blog</a>

				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
					<div class="eyebrow">
						<span onclick="return false" >Top Stories</span>
					</div>
					<div id="topStoriesList" style="padding: 4px;" ></div>
					<div id="waitTpStories" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
				</div>

				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
					<div class="eyebrow">
						<span onclick="return false" >Latest Posts</span>
					</div>
					<div id="latestPostsList" style="padding: 4px;" ></div>
					<div id="waitLtPosts" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
				</div>

			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
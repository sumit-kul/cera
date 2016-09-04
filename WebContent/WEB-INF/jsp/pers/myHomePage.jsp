<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="-RZIi8J9lRpAmXmpk_4i4-LTTVCV0eqiB6DXgw1vg_E" />
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>	
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		
		<script src='js/jquery.swipebox.js'></script>
		<link rel='stylesheet' href='css/swipebox.css' type='text/css' />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/home.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/media.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/captionMedia.css" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/newStyle.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<link rel="stylesheet" type="text/css" href="css/fullcalendar.css" />
		<link rel="stylesheet" media="print" href="css/fullcalendar.print.css" />
		<script type="text/javascript" src="js/moment.min.js"></script>
		<script type="text/javascript" src="js/fullcalendar.min.js"></script>
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="text/css">
			#container .left-panel .commSection .paginatedList .details {
				width: 100%;
			}
			#container .left-panel .commSection .paginatedList .details .person a {
				padding: 0px;
			}
			
			span.profileMe a#myHome {
				color: #27333E;
			}
			
			.intHeader {
				height: 40px;
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
			
			span.myHome {
			    float: left;
			    padding: 4px 6px;
			    border-radius: 4px;
			    background-color: rgb(36, 63, 82);
			    color: rgb(255, 255, 255);
			    margin: 0px 4px;
			    padding: 4px 2px;
			}
			
			span.myHome a:hover {
			    color: #C4D0DA;
			    text-decoration: none;
			}
			
			span.myHome a {
			    font-size: 13px;
			    font-weight: bold;
			    color: #FFF;
			    cursor: pointer;
			    display: block;
			    margin: 0px;
			    outline: medium none;
			    padding: 0px 6px;
			    position: relative;
			    text-decoration: none;
			}
			
			span.myHome a#myHome {
			    color: #FFF;
			}
			
			
			#container .left-panel .commSection .paginatedList .detailRes span {
				font-size: 13px;
				color: #A7A9AB;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry {
			    position: absolute;
			    right: 20px;
			    top: 30px;
			}
			
			.fc-left h2 {
				margin-top: 8px;
				font-size: 16px;
				color: #546E86;
			}
			
			.imgOuter2 a img {
				width: auto;
				position: relative;
				display: block;
				max-width: 100%;
				max-height: 350px;
				border: 0px none;
			}
			
			.justified-gallery .caption2 {
				height: 100%;
				opacity: 0.3;
			    display: block;
			    position: absolute;
			    bottom: 0;
			    padding: 5px;
			    background-color: #000000;
			    left: 0;
			    right: 0;
			    margin: 0;
			    color: white;
			    font-size: 12px;
			    font-weight: 300;
			    font-family: sans-serif;
			    text-align: center;
			}
			
			.justified-gallery .caption2 span {
				top: 50%;
				position: relative;
				font-size: 5em;
				color: #fff;
			}
			
			.linkUrl {
			    background: #fdfdfd;
			    vertical-align: middle;
			    -webkit-border-radius: 2px;
			    -moz-border-radius: 2px;
			    -ms-border-radius: 2px;
			    -o-border-radius: 2px;
			    border-radius: 2px;
			    padding: 5px 6px 4px;
			    -webkit-box-shadow: 0 0 3px rgba(0,0,0,0.1) inset;
			    -moz-box-shadow: 0 0 3px rgba(0,0,0,0.1) inset;
			    box-shadow: 0 0 3px rgba(0,0,0,0.1) inset;
			    border: 1px solid #c1c1c1;
			    font-size: 13px;
			    color: #333;
			    width: 100%;
			    box-sizing: border-box;
			}
		</style>
		
		<script type="text/javascript">
			function updateFollowing(contactId, userId, contactName, actionFor, actionType){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType="+actionType+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    //$("#connectionInfo").html(result);
		    	  }});
			}

			function updateSubscription(communityId, userId, actionFor, type){
				$.ajax({url:"${communityEraContext.contextUrl}/cid/"+communityId+"/community/updateSubscription.ajx?communityId="+communityId+"&actionFor="+actionFor+"&type="+type+"&userId="+userId,success:function(result){
		    	    //$("#connectionInfo").html(result);
		    	  }});
			}

			function copyLink(commId, userId, itemId, type) {
				BootstrapDialog.show({
					title: 'Copy update link',
	                message: '<p class="addTagHeader">Copy below link to share with others. </p><div id="mainclick"><input class="linkUrl" id="mainclickinput" onclick="this.select()" tabindex="0" readonly="" value="${communityEraContext.contextUrl}/cid/" type="text"  /></div>',
	                closable: true,
	                closeByBackdrop: true,
	                closeByKeyboard: true,
	                onshown: function(dialogRef){
		                var slink = "";
	                	if (type == "BlogEntry") {
	                		slink = "${communityEraContext.contextUrl}/cid/"+commId+"/blog/blogEntry.do?id="+itemId;
				    	} else if (type == "PersonalBlogEntry") {
				    		slink = "${communityEraContext.contextUrl}/blog/blogEntry.do?id="+itemId;
				    	} else if (type == "WikiEntry") {
				    		slink = "${communityEraContext.contextUrl}/cid/"+commId+"/wiki/wikiDisplay.do?id="+itemId;
				    	} else if (type == "Document") {
				    		slink = "${communityEraContext.contextUrl}/cid/"+commId+"/library/documentdisplay.do?id="+itemId;
				    	} else if (type == "ForumTopic") {
				    		slink = "${communityEraContext.contextUrl}/cid/"+commId+"/forum/forumThread.do?id="+itemId;
				    	}  else if (type == "Event") {
				    		slink = "${communityEraContext.contextUrl}/cid/"+commId+"/event/showEventEntry.do?id="+itemId;
				    	}
	                	$('#mainclickinput').val(slink);
	                	$('#mainclickinput').select();
	            	}
	            });
			}

			function dynamicDropDownQtip(cnt){ //dynaDropDown
				$(".qtip").hide();
				$(document).on('click', '.dynaDDNoti_'+cnt, function(event) {
				     $(this).qtip({
			        	overwrite: true,
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: api.elements.target.attr('title'),
			                        type: 'GET', // POST or GET
			                        dataType: 'json',
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
			            	my: 'top right',  // Position my top left...
			                at: 'bottom right', // at the bottom right of...
			                target: '', // Position it where the click was...
							adjust: { 
								mouse: false 
							} 
						},
						style: {
					        classes: 'qtip-light myCustomClass4'
					    },
					    hide: {
			                fixed: true,
			                delay: 300
			            },
			            show: {
			                event: event.type, 
			                ready: true 
			            }
			         }, event);
			     }).each(function(i) {
		            $.attr(this, 'oldtitle', $.attr(this, 'title'));
		        });
	        }

			function memberInfoQtip(cnt){ //dynaDropDown
				$(".qtip").hide();
				$('.memberInfoId'+cnt).each(function() {
					$(this).qtip({
			        	 overwrite: true,
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
	        }
        
			function applyFilter(filterUrl){
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&toggleList="+toggleList;
				window.location.href = filterUrl;
			}

			function showComments(entryId, communitytId, type) 
			{ 
				var elId = type + entryId + communitytId;
				var oId = type + "-" + entryId + "-" + communitytId;
				var pfcnt = $("#pgCount"+elId).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showComments.ajx?jPage="+pfcnt+"&entryId="+entryId+"&communityId="+communitytId+"&type="+type,success:function(result){
					var fList = "";
					var rowCnt = result.rowCnt;
					var pgCnt = result.pgCnt;
					var nxtPage = result.nxtPage;

					if (nxtPage <= pgCnt) {
						$("#pgCount"+elId).val(nxtPage);
					} else {
						$("#spc"+elId).hide();
						$("#pgCount"+elId).val(-1);
					}
					$.each(result.aData, function() {
						var sName = "";
						 sName += "<li class='commentEntity'><div class='content'>";
						 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"' class='actorImage' ";
						 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>";
						 if(this['photoPresent']){
						 	sName += " <img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' />";
						 } else {
							 sName += "<img src='img/user_icon.png' />";
						 }
						 sName += "</a></div>";
						 sName += "<div class='header'><h4 class='subHeadline'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"' class='name' ";
						 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['firstName']+ " " + this['lastName']+"</a>";
						 sName += "<span class='textEntity'>"+this['commentToDisplay']+"</span>";

						 sName += "<div class='commLike'><a id='lkunlk"+this['id']+elId+"' href='javascript:void(0);' class='likeBtn'";
						 sName += " onclick='likeEntry(&#39;"+entryId+"&#39;, &#39;"+communitytId+"&#39;, &#39;"+type+"&#39;, &#39;"+this['id']+"&#39;);' >"+this['likeString']+"</a> ";
						 sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 0px; font-size: 6px;'></i>";
						 sName += "<span id='lkunlkcnt"+this['id']+elId+"'>"+this['likeCount']+"</span><i class='fa fa-thumbs-up' style='margin: 0px 8px; '></i>";
						 sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 0px; font-size: 6px;'></i>"+this['datePostedOn']+"</div>";

						 sName += "</h4></div></li>";
						 		
						 fList = sName + fList;
					 });
					$("#waitMessage"+elId).hide();
					 $("#"+elId).prepend(fList);
		    	  },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#"+oId).show();
			             $("#waitMessage"+elId).show();
			         } 
		    	  });
			}

			function showCommentsTop(entryId, communitytId, type) 
			{ 
				var elId = type + entryId + communitytId;
				var pfcnt = $("#pgCount"+elId).val();
				if (pfcnt != -1) {
					showComments(entryId, communitytId, type);
				}
			}

			function toggleBtn(entryId, communitytId, type) 
			{ 
				var elId = type + entryId + communitytId;
				var textA = $("#intComm"+elId).val();
				if (textA == "") {
					$("#addc"+elId).removeClass("enabled")
				} else {
					$("#addc"+elId).addClass("enabled");

				}
			}

			function addComments(entryId, communitytId, type) 
			{ 
				var elId = type + entryId + communitytId;
				var comment = $("#intComm"+elId).val();
				if (comment == "") {
					$("#addc"+elId).removeClass("enabled");
				} else {
					$.ajax({type:"POST",url:"${communityEraContext.contextUrl}/pers/addComments.ajx?entryId="+entryId+"&communityId="+communitytId+"&type="+type,data:{comment:comment},success:function(result){
						 var sName = "";
						 sName += "<li class='commentEntity'><div class='content'>";
						 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+result.posterId+"' class='memberInfoId1000 actorImage' ";
						 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+result.posterId+"'>";
						 if(result.photoPresent){
						 	sName += " <img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+result.posterId+"' />";
						 } else {
							 sName += "<img src='img/user_icon.png' />";
						 }
						 sName += "</a></div>";
						 sName += "<div class='header'><h4 class='subHeadline'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+result.posterId+"' class='memberInfo name' ";
						 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+result.posterId+"'>"+result.posterName+"</a>";
						 sName += "<span class='textEntity'>"+result.comment+"</span>";

						 sName += "<div class='commLike'><a id='lkunlk"+result.id+elId+"' href='javascript:void(0);' class='likeBtn'";
						 sName += " onclick='likeEntry(&#39;"+entryId+"&#39;, &#39;"+communitytId+"&#39;, &#39;"+type+"&#39;, &#39;"+result.id+"&#39;);' >Like</a> ";
						 sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 0px; font-size: 6px;'></i>";
						 sName += "<span id='lkunlkcnt"+result.id+elId+"'>0</span><i class='fa fa-thumbs-up' style='margin: 0px 8px; '></i>";
						 sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 0px; font-size: 6px;'></i>"+result.datePosted+"</div>";

						 sName += "</h4></div></li>";
						 
						 $("#"+elId).append(sName);
						 memberInfoQtip(1000);
						 $("#intComm"+elId).val("");
						 $("#addc"+elId).removeClass("enabled");
			    	  },
					 	// What to do before starting
				         beforeSend: function () {
				         } 
			    	  });

				}
			}

			function likeEntry(entryId, communitytId, type, commentId) 
			{ 
				var elId = commentId + type + entryId + communitytId;
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (isAuthenticated == 'false') {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Error',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to like / unlike. </p>',
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
			    	if (type == "BlogEntry") {
					    if (commentId > 0) {
					    	$.ajax({url:"${communityEraContext.contextUrl}/blog/likeBlogEntryComment.ajx?entryId="+entryId+"&commentId="+commentId,success:function(result){
					    		$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	} else {
					    	$.ajax({url:"${communityEraContext.contextUrl}/blog/likeBlogEntry.ajx?blogEntryId="+entryId,success:function(result){
					    	    $("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	}
			    	} else if (type == "WikiEntry") {
					    if (commentId > 0) {
					    	$.ajax({url:"${communityEraContext.contextUrl}/wiki/likeWikiEntryComment.ajx?entryId="+entryId+"&commentId="+commentId,success:function(result){
					    		$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	} else {
				    		$.ajax({url:"${communityEraContext.contextUrl}/wiki/likeWikiEntry.ajx?wikiEntryId="+entryId,success:function(result){
				    			$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	}
			    	} else if (type == "Document") {
					    if (commentId > 0) {
					    	$.ajax({url:"${communityEraContext.contextUrl}/library/likeFileComment.ajx?fileId="+entryId+"&commentId="+commentId,success:function(result){
					    		$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	} else {
				    		$.ajax({url:"${communityEraContext.contextUrl}/library/likeFile.ajx?fileId="+entryId,success:function(result){
				    			$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	}
			    	} else if (type == "ForumTopic") {
			    		$.ajax({url:"${communityEraContext.contextUrl}/forum/likeForumItem.ajx?forumItemId="+entryId,success:function(result){
			    			$("#lkunlk"+elId).html(result.newLType);
				    	    $("#lkunlkcnt"+elId).html(result.count);
				    	  }});
			    	}  else if (type == "ForumResponse") {
			    		$.ajax({url:"${communityEraContext.contextUrl}/forum/likeForumItem.ajx?forumItemId="+commentId,success:function(result){
			    			$("#lkunlk"+elId).html(result.newLType);
				    	    $("#lkunlkcnt"+elId).html(result.count);
				    	  }});
			    	} else if (type == "Event") {
					    if (commentId > 0) {
				    	} else {
				    		$.ajax({url:"${communityEraContext.contextUrl}/event/likeEvent.ajx?eventId="+entryId,success:function(result){
				    			$("#lkunlk"+elId).html(result.newLType);
					    	    $("#lkunlkcnt"+elId).html(result.count);
					    	  }});
				    	}
			    	}
			    }
			} 
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				var offset = 300,
				offset_opacity = 1200,
				scroll_top_duration = 700,
				$back_to_top = $('.cd-top');
				$(window).scroll(function(){
					( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
					if( $(this).scrollTop() > offset_opacity ) { 
						$back_to_top.addClass('cd-fade-out');
					}
				});
		
				$back_to_top.on('click', function(event){
					event.preventDefault();
					$('body,html').animate({
						scrollTop: 0 ,
					 	}, scroll_top_duration
					);
				});

				$('#calendar').fullCalendar({
					header: {
						right: ''
					},
					businessHours: true, // display business hours
					editable: false,
					eventLimit: true, // allow "more" link when too many events
					selectable: false,
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

		        $.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?profileId=${command.user.id}",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId1000' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
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
			        memberInfoQtip(1000);
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitBLAthMessage").show();
			         } 
			    });

		        $.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?pymkId=${command.user.id}",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						temp += "<div style='width: 100%; display: inline-block;'>";
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId1000' style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId1000 rightPannelName' ";
							temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
							temp += this['name'];
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId1000'  style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
							 temp += "<div style=''><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId1000 rightPannelName' ";
							 temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
							 temp += this['name'];
							 temp += "</a></div>";
						 }
						temp += "</div>";
						});
					 $("#pymkList").html(temp);
					 
					// Hide message
			        $("#waitPymkMessage").hide();
			        memberInfoQtip(1000);
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitPymkMessage").show();
			         } 
			    });

				var count = 2;
				var count2 = 1;
				function infinite_scrolling_allConn(){
					if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
				    		var total = document.getElementById('pgCount').value;
		                	if (count > total){
		                		return false;
				        	} else if (count2 < count){
				        		count2++;
				        		var items = [];
					        	$( "<div id='rowSection"+count+"'></div>" ).insertAfter( "#rowSection"+(count-1) );
				            	$.ajax({
				                	url:"${communityEraContext.contextUrl}/pers/myHome.ajx?jPage="+count,
				                	dataType: "json",
				                	success:function(result){ 
				    					var sName = "";
				    					var commId = this['communityId'];
				    					var i = 1;
				   					 $.each(result.aData, function() {
				   						var isAdsPosition = false;
				   						if(i%4 == 0){
				   							isAdsPosition = true;
				   						 }
				   						var rowws = this;
				   						sName += "<div class='outerPost' style='height: unset;'><div class='details'><div class='mainHeader'><div class='leftImg'>";
				   						sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterid']+"' >";
				   						if(this['photoPresent']){
				   							sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterid']+"'/>";
				   						} else {
				   							sName += "<img src='img/user_icon.png'  />";
				   						}
				   						sName += "</a></div>";
				   						sName += "<div class='heading'>";
				   						sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterid']+"' class='memberInfoId0' ";
				   						sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterid']+"'>"+this['firstName']+" "+this['lastName']+"</a> ";
				   						if (this['itemType'] == "Assignment") {
				   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >assignment</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Assignment&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "ForumTopic") {
				   							sName += "started <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >forum topic</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=ForumTopic&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "BlogEntry") {
				   							sName += "posted <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >blog entry</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=BlogEntry&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "PersonalBlogEntry") {
				   							sName += "posted <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >blog entry</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=PersonalBlogEntry&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "Event") {
				   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >event</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Event&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "WikiEntry") {
				   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >wiki article</a> "+this['postedOn'];
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=WikiEntry&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "Media") {
				   							sName += "added ";
				   							if (this['contentType'] == "Group") {
				   								sName += this['imageCount']+" <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >Photos</a> "+this['postedOn'];
				   							} else {
				   								sName += "<a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >Photo</a> "+this['postedOn'];
				   							}
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Document&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "Photo") {
				   							sName += "added ";
				   							if (this['contentType'] == "Group") {
				   								sName += this['imageCount']+" <a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/library/mediaGallery.do' >Photos</a> "+this['postedOn'];
				   							} else {
				   								sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/library/mediaGallery.do' >Photo</a> "+this['postedOn'];
				   							}
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Document&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						} else if (this['itemType'] == "Document") {
				   							sName += "added ";
				   							if (this['contentType'] == "Group") {
				   								sName += this['imageCount']+" <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Files</a> "+this['postedOn'];
				   							} else {
				   								sName += "<a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >File</a> "+this['postedOn'];
				   							}
				   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
				   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Document&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
				   						}
				   						sName += "</div>";
				   						if (!(this['itemType'] == "Media" || this['itemType'] == "PersonalBlog")) {
				   							sName += "<div class='person'>";
				   							if (this['communityType'] == "Private") {
				   								sName += "<i class='fa fa-lock' style='font-size: 15px;'></i>";
				   							} else if (this['communityType'] == "Protected") {
				   								sName += "<i class='fa fa-shield' style='font-size: 15px;'></i>";
				   							} else if (this['communityType'] == "Public") {
				   								sName += "<i class='fa fa-globe' style='font-size: 15px;'></i>";
				   							}
				   							sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/home.do' target='_blank' >"+this['communityName']+"</a></div>";
				   						}
				   						sName += "</div>";
				   						if (this['itemType'] == "Event") {
											sName += "<div class='pListInner' ><div class='leftImgLarge' style='height: 160px; margin-right: 10px;'>";
											if (this['eventPhotoPresent']) {
												sName += "<img src='${communityEraContext.contextUrl}/event/eventPoster.img?id="+this['itemId']+"' style='min-height: 160px;'/>";
											} else {
												sName += "<img src='img/EventImg.png' style='min-height: 160px;'/>";
											}
											sName += "</div><div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div>";
											sName += "<div style='font-size: 13px; padding : 10px; line-height: 22px; font-weight: 500;'><b>";
											if (this['venue'] != "") {
												sName += this['venue'];
											} else {
												sName += this['location'];
											}
											sName += "</b> <br />"+this['address']+this['dateStarted']+"<br />";
											if (this['dateStarted'] != "") {
												sName += this['dateStarted'];
												if (this['dateEnded'] != "") {
													sName += "&nbsp;to&nbsp; "+this['dateEnded'];
												}
											} else {
												if (this['dateEnded'] != "") {
													sName += "--&nbsp;"+this['dateEnded'];
												}
											}
											sName += "</div></div>";
			   							}
				   						if (this['itemType'] == "Document") {
				   							if (this['contentType'] == "Single") {
				   								sName += "<div class='pListInner' ><div class='leftImgLarge2'>";
				   								if (this['iconType'] == "xls") {
				   									sName += "<img src='img/file/excelicon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "zip") {
				   									sName += "<img src='img/file/zipicon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "pdf") {
				   									sName += "<img src='img/file/pdficon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "xml") {
				   									sName += "<img src='img/file/xmlicon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "img") {
				   									sName += "<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id="+this['itemId']+"' style='min-height: 160px;'/>";
				   								} else if (this['iconType'] == "doc") {
				   									sName += "<img src='img/file/wordicon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "ppt") {
				   									sName += "<img src='img/file/powerpointicon.gif' class='fileimg'/>";
				   								} else if (this['iconType'] == "file") {
				   									sName += "<img src='img/file/fileicon.gif' class='fileimg'/>";
				   								} else {
				   									sName += "<img src='img/file/fileicon.gif' class='fileimg'/>";
				   								}
				   								sName += "</div><div class='coverHeader' ><a target='_blank' ";
				   								sName += " href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/documentdisplay.do?backlink=ref&amp;id="+this['itemId']+"' >"+this['itemTitle']+"</a></div>";
				   								sName += " <div class='coverBody'>"+this['bodyDisplay']+"</div></div>";
				   							} else if (this['contentType'] == "Group") {
				   								sName += "<div class='pListInner' >";
				   								$.each(this.pData, function() {
				   									sName += "<div class='leftImgLarge3'><a href='${communityEraContext.contextUrl}/cid/"+commId+"/library/documentdisplay.do?backlink=ref&amp;id="+this['docId']+"'";
				   									sName += " target='_blank' title='"+this['title']+"'>";
				   									if (this['iconType'] == "xls") {
					   									sName += "<img src='img/file/excelicon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "zip") {
					   									sName += "<img src='img/file/zipicon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "pdf") {
					   									sName += "<img src='img/file/pdficon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "xml") {
					   									sName += "<img src='img/file/xmlicon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "img") {
					   									sName += "<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id="+this['itemId']+"' style='min-height: 160px;'/>";
					   								} else if (this['iconType'] == "doc") {
					   									sName += "<img src='img/file/wordicon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "ppt") {
					   									sName += "<img src='img/file/powerpointicon.gif' class='fileimg'/>";
					   								} else if (this['iconType'] == "file") {
					   									sName += "<img src='img/file/fileicon.gif' class='fileimg'/>";
					   								} else {
					   									sName += "<img src='img/file/fileicon.gif' class='fileimg'/>";
					   								}
				   									sName += "</a></div>";
				   								});
				   								sName += "</div>";
				   							}
				   						}
				   						if (this['itemType'] == "Media") {
				   							sName += "<div id='ltbox_"+this['albumId']+"_"+this['mediaGroupNumber']+"' class='imgOuter2'>";
				   							if (this['contentType'] == "Group") {
												sName += "<div id='demo-gallery"+this['albumId']+"_"+this['mediaGroupNumber']+"' class='demo-gallery'>";
												var incount = 0;
												var itemid = this['albumId']+"_"+this['mediaGroupNumber'];
												var liNumber = this['lastItemNumber'];
												var cDoc = this['countDoc'];
												$.each(this.pData, function() {
													sName += "<a rel='gallery-"+this['albumId']+"_"+this['mediaGroupNumber']+"' href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['docId']+"' id='image_"+this['docId']+"' ";
													sName += "data-id='"+this['docId']+"' data-type='photo' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['docId']+"'";
													sName += "data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['docId']+"' class='swipebox swipebox"+this['albumId']+"_"+this['mediaGroupNumber']+"' >";
													sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['docId']+"' />";
													if (cDoc > 0 && liNumber == incount) {
														sName += "<div class='caption2' ><span>+"+cDoc+"</span></div>";
													}
													sName += "</a>";
													incount++;
												});
												sName += "</div>";
												if (cDoc > 0) {
													$.each(this.tData, function() {
														sName += "<div style='display: none; height: 0px; width: 0px;' rel='gallery-"+this['albumId']+"_"+this['mediaGroupNumber']+"' href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['docId']+"' ";
														sName += " id='image_"+this['docId']+"' data-id='"+this['docId']+"' data-type='photo' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['docId']+"' ";
														sName += " data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['docId']+"' class='swipebox swipebox"+this['albumId']+"_"+this['mediaGroupNumber']+"' >";
														sName += " <img style='display: none;' src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['docId']+"' /></div>";
													});
												}
												items.push(itemid);
												sName += "</div>";
				   							} else if (this['contentType'] == "Single") {
				   								sName += "<div ><a href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['itemId']+"' id='image_"+this['itemId']+"'";
				   								sName += " data-id='"+this['itemId']+"' data-type='photo' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['itemId']+"'";
				   								sName += " data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['itemId']+"' class='swipebox swipebox"+this['albumId']+"_"+this['mediaGroupNumber']+"' >";
				   								sName += " <img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['itemId']+"' /></a></div>";
				   								items.push(itemid);
				   							}
				   							sName += "</div>";
				   						}
				   						if (this['itemType'] == "Photo") {
				   							sName += "<div id='ltbox_"+this['folderId']+"_"+this['docGroupNumber']+"' class='imgOuter2'>";
				   							if (this['contentType'] == "Group") {
				   								sName += "<div id='demo-gallery"+this['folderId']+"_"+this['docGroupNumber']+"' class='demo-gallery'>";
												var incount = 0;
												var itemid = this['folderId']+"_"+this['docGroupNumber'];
												var liNumber = this['lastItemNumber'];
												var cDoc = this['countDoc'];
												$.each(this.pData, function() {
													sName += "<a rel='gallery-"+itemid+"' href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['docId']+"' id='image_"+this['docId']+"' ";
													sName += "data-id='"+this['docId']+"' data-type='library' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['docId']+"'";
													sName += "data-url='${communityEraContext.contextUrl}/cid/"+commId+"/library/mediaGallery.do' class='swipebox swipebox"+itemid+"' >";
													sName += "<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['docId']+"' />";
													if (cDoc > 0 && liNumber == incount) {
														sName += "<div class='caption2' ><span>+"+cDoc+"</span></div>";
													}
													sName += "</a>";
													incount++;
												});
												sName += "</div>";
												if (cDoc > 0) {
													$.each(this.tData, function() {
														sName += "<div style='display: none; height: 0px; width: 0px;' rel='gallery-"+itemid+"' href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['docId']+"' ";
														sName += " id='image_"+this['docId']+"' data-id='"+this['docId']+"' data-type='library' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['docId']+"' ";
														sName += " data-url='${communityEraContext.contextUrl}/cid/"+commId+"/library/mediaGallery.do' class='swipebox swipebox"+itemid+"' >";
														sName += " <img style='display: none;' src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['docId']+"' /></div>";
													});
												}
												items.push(itemid);
												sName += "</div>";
				   							} else if (this['contentType'] == "Single") {
				   								sName += "<div ><a href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['itemId']+"' id='image_"+this['itemId']+"'";
				   								sName += " data-id='"+this['itemId']+"' data-type='library' data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['itemId']+"'";
				   								sName += " data-url='${communityEraContext.contextUrl}/cid/"+commId+"/library/mediaGallery.do' class='swipebox swipebox"+this['folderId']+"_"+this['docGroupNumber']+"' >";
				   								sName += " <img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['itemId']+"' /></a></div>";
				   								items.push(itemid);
				   							}
				   							sName += "</div>";
				   						}

				   						if (!(this['itemType'] == "Document" || this['itemType'] == "Photo" || this['itemType'] == "Event" || this['itemType'] == "Media")) {
				   							if (this['imageCount'] > 0) {
												sName += "<div class='pListInner' ><div class='leftImgLarge'>";
												if (this['itemType'] == "PersonalBlogEntry") {
													sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId="+this['itemId']+"' />";
												} else {
													sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type="+this['itemType']+"&itemId="+this['itemId']+"' />";
												}
												sName += "</div><div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div><div class='coverBody'>"+this['bodyDisplay']+"</div></div>";
											}
											if (this['imageCount'] == 0) {
												sName += "<div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div>";
												sName += "<div class='coverBody'>"+this['bodyDisplay']+"</div>";
											}
				   						}

				   						if (!(this['itemType'] == "Document" || this['contentType'] == "Group")) {
											if (this['itemType'] == "ForumTopic") {
												if (this['commentCount'] > 0) {
													$.each(this.forumResponses, function(nRow) {
														sName += "<div class='pListInner' style='background-color: #F1F3F5;'><div class='leftImgSml' style='padding: 6px 0px 0px 6px;'>";
														if (this.authorPhoto) {
															sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this.authorId+"&backlink=ref' title='"+this.authorName+"'>";
															sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this.authorId+"' style='height: 40px; width: 40px;'/></a>";
														} else {
															sName += "<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='"+this.authorName+"' />";
														}
														sName += "</div><div class='detailRes' style='margin-left: 6px;'>";
														sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this.authorId+"&backlink=ref' class='memberInfoId"+count+"' ";
														sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this.authorId+"'>"+this.authorName+"</a><span> replied on "+this.repliedOn+"</span>";
														sName += "<div class='heading' style='word-wrap: normal;'>"+this.subject+"</div><div style='padding: 0px 0px 0px 3px;'>"+this.body+"</div>";
	
														sName += "<div class='commLike'><a id='lkunlk"+this.id+"ForumResponse"+this.topicId+commId+"' href='javascript:void(0);' class='likeBtn'";
														sName += " onclick='likeEntry(&#39;"+this.topicId+"&#39;, &#39;"+commId+"&#39;, &#39;"+"ForumResponse"+"&#39;, &#39;"+this.id+"&#39;);' >"+this.likeString+"</a> ";
														sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 6px; font-size: 6px; color: #A7A9AB;'></i>";
														sName += "<span id='lkunlkcnt"+this.id+"ForumResponse"+this.topicId+commId+"'>"+this.likeCount+"</span><i class='fa fa-thumbs-up' style='margin: 0px 8px; color: #A7A9AB;'></i></div>";
														 
														sName += "</div></div>";
													});
												}
											}
											
											sName += "<div class='person personLast' >";
											sName += "<a id='lkunlk0"+this['itemType']+this['itemId']+this['communityId']+"' href='javascript:void(0);' ";
											sName += " onclick='likeEntry(&#39;"+this['itemId']+"&#39;, &#39;"+this['communityId']+"&#39;, &#39;"+this['itemType']+"&#39;, &#39;0&#39;);' class='lkBtn'>"+this['likeString']+"</a>";
											if (!(this['itemType'] == "Event" || this['itemType'] == "ForumTopic")) {
												sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 12px; font-size: 6px;'></i>";
												sName += "<a href='javascript:void(0);' onclick='showCommentsTop(&#39;"+this['itemId']+"&#39;, &#39;"+this['communityId']+"&#39;, &#39;"+this['itemType']+"&#39;);' class='lkBtn'>Comment</a>";
											}
											sName += "<span style='float:right;'><span id='lkunlkcnt0"+this['itemType']+this['itemId']+this['communityId']+"'>"+this['likeCount']+"</span><i class='fa fa-thumbs-up' style='margin: 0px 8px;'></i>";
											if (this['itemType'] != "Event") {
												sName += "<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 12px; font-size: 6px;'></i>";
												sName += "<i class='fa fa-eye' style='margin: 0px 8px;'></i>"+this['visitors'];
											}
											sName += "</span></div>";
	
											if (!(this['itemType'] == "Event" || this['itemType'] == "ForumTopic")) {
												sName += "<div class='commentSec' id='"+this['itemType']+"-"+this['itemId']+"-"+this['communityId']+"' style='display: none;'>";
												if (this['commentCount'] > 3) {
													sName += "<a href='javascript:void(0);' onclick='showComments(&#39;"+this['itemId']+"&#39;, &#39;"+this['communityId']+"&#39;, &#39;"+this['itemType']+"&#39;);' ";
														sName += " id='spc"+this['itemType']+this['itemId']+this['communityId']+"' class='lkBtn'>Show previous comments</a>";
												}
												sName += "<input type='hidden' id='pgCount"+this['itemType']+this['itemId']+this['communityId']+"' value='0' />";
												sName += "<div id='waitMessage"+this['itemType']+this['itemId']+this['communityId']+"' style='display: none;'><p class='showWaitMessage'></p></div>";
												sName += "<div class='commentList'><ul id='"+this['itemType']+this['itemId']+this['communityId']+"'></ul></div>";
	
												sName += "<div class='addCommForm'>";
												sName += "<textarea id='intComm"+this['itemType']+this['itemId']+this['communityId']+"' placeholder='Add a comment...' cols='60' rows='1' class='edit' ";
													sName += " onkeyup='toggleBtn(&#39;"+this['itemId']+"&#39;, &#39;"+this['communityId']+"&#39;, &#39;"+this['itemType']+"&#39;);'></textarea>";
														sName += "<div class='buttonContainer'>";
														sName += "<a href='javascript:void(0);' onclick='addComments(&#39;"+this['itemId']+"&#39;, &#39;"+this['communityId']+"&#39;, &#39;"+this['itemType']+"&#39;);' ";
															sName += "id='addc"+this['itemType']+this['itemId']+this['communityId']+"' class='commentSubmit'>Comment</a></div></div></div>";
											}
					   					 }
										
										sName += "</div></div>";

										if(isAdsPosition) {
											 sName += '<div class="inboxAds">';
											 sName += '<ins class="adsbygoogle adsbygoogle'+count+'" style="display:inline-block;width:728px;height:90px" data-ad-client="ca-pub-8702017865901113" data-ad-slot="4053806181"></ins>';
											 sName += '</div>';
										 }
										 i++;
										
				   						});

									 if (count == total){
										 var pageUserId = document.getElementById('PageUserId').value;
										 var userFullName = document.getElementById('UserFullName').value;
										 var userJoinOn = document.getElementById('UserJoinOn').value;
										 sName += "<div class='paginatedList' style='height: unset;'><div class='details' style='width: 100%;'><div class='heading' style='text-align: center; width: 100%;'>";
										 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+pageUserId+"&backlink=ref' class='memberInfoId"+count+"' ";
										 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+pageUserId+"' >"+userFullName+"</a>";
										 sName += " <span style='line-height: 1.4; color:#A7A9AB;'> joined <Strong>Jhapak</Strong> on "+userJoinOn+"</span>";
										 sName += "</div></div></div>";
									 }

									 $("#rowSection"+count).html(sName);
									 dynamicDropDownQtip(count);
									 memberInfoQtip(count);
									 
									 for(var j = 0; j < items.length; j++ ){
									 	var selItem = items[j];
									 	$('#demo-gallery'+selItem).justifiedGallery({
									 	    rowHeight : 180,
									 	   	captions : false, 
											margins : 4,
											fixedHeight : false,
											randomize: false,
											lastRow : 'nojustify'
									 	});
									 	$( '.swipebox'+selItem ).swipebox();

									 	
				 				   	}
									 
				   					$("#waitMessage").hide();
				                	} ,
				                	beforeSend: function () {
							             $("#waitMessage").show();
							         },
				                	complete: function () {
				                		$(".adsbygoogle"+count).each(function () { (adsbygoogle = window.adsbygoogle || []).push({}); });
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
			
				normalQtip();
				memberInfoQtip(0);
				dynamicDropDownQtip(0);
			});
		</script>
	</head>

	<body id="communityEra">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
			<div id="container" >
				<div class="left-panel" style="margin-top: -11px;">
					<div class="commSection" id="communitySection">
						<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
						<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
						<input type="hidden" id="pgCount" value="${command.pageCount}" />
						<input type="hidden" id="UserFullName" value="${command.user.fullName}" />
						<input type="hidden" id="PageUserId" value="${command.user.id}" />
						<input type="hidden" id="UserJoinOn" value="${command.registeredOn}" />
						<div class="abtMe">
							<div class="cvrImg">
								<c:if test="${command.user.coverPresent}">
									<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${command.user.id}" />
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
				
						<c:if test="${command.numberOfMemberships == 0}">
							<div class="type" style="width:100%; padding: 14px; text-align: justify;">
								<h2 style="font-size: 19px; width:100%;">You are currently not a member of any community.</h2><br/>
								<p class="noCommunity" style="text-align: center;">Click <a href="${communityEraContext.contextUrl}/community/showCommunities.do" >here</a> to view existing communities
								<br /><strong style="font-size: 15px;">OR</strong><br />
								<a href="${communityEraContext.contextUrl}/community/createNewCommunity.do" class="headerTextLink">Start</a> your own community.</p>
							</div>
						</c:if>
						
						<div class="rowLine" id="rowSection1">
							<c:forEach items="${command.scrollerPage}" var="row">
								<div class='outerPost' style='height: unset;'>
									<div class='details'>
										<div class='mainHeader'>
									 		<div class='leftImg'>
												<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterid}' >
													<c:choose>
														<c:when test="${row.photoPresent}">
															<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.posterid}'/>
														</c:when>
														<c:otherwise>
															<img src='img/user_icon.png'  />
														</c:otherwise>
													</c:choose>
												</a>
										 	</div>
									 		<div class='heading'>
									 			<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterid}&backlink=ref' class='memberInfoId0'
											 				title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${row.posterid}'>${row.firstName} ${row.lastName}</a>
											 	<c:if test="${row.itemType == 'Assignment'}">
											 		created <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >assignment</a> on ${row.postedOn}
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=Assignment&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
											 	<c:if test="${row.itemType == 'ForumTopic'}">
											 		started <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >forum topic</a> on ${row.postedOn}
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&fromCommunity=YES&commId=${row.communityId}&type=ForumTopic&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'BlogEntry'}">
										 			posted <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >blog entry</a> on ${row.postedOn}
											 		<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=BlogEntry&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'PersonalBlogEntry'}">
										 			posted <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >blog entry</a> on ${row.postedOn}
											 		<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=BlogEntry&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'Event'}">
										 			created <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Event</a> on ${row.postedOn}
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&fromCommunity=YES&commId=${row.communityId}&type=Event&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'WikiEntry'}">
										 			created <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Wiki article</a> on ${row.postedOn}
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=WikiEntry&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'Media'}">
										 			added
										 			<c:choose>
														<c:when test="${row.contentType == 'Group'}">
															${row.imageCount} <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Photos</a> ${row.postedOn}
														</c:when>
														<c:otherwise>
															<a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Photo</a> ${row.postedOn}
														</c:otherwise>
													</c:choose>
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=Document&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'Photo'}">
										 			added
										 			<c:choose>
														<c:when test="${row.contentType == 'Group'}">
															${row.imageCount} <a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do' >Photos</a> ${row.postedOn}
														</c:when>
														<c:otherwise>
															<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do' >Photo</a> ${row.postedOn}
														</c:otherwise>
													</c:choose>
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=Document&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
										 		<c:if test="${row.itemType == 'Document'}">
										 			added
										 			<c:choose>
														<c:when test="${row.contentType == 'Group'}">
															${row.imageCount} <a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do' >Files</a> ${row.postedOn}
														</c:when>
														<c:otherwise>
															<a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >File</a> on ${row.postedOn}
														</c:otherwise>
													</c:choose>
										 			<div class='entry'>
											 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
											 				title='pers/subscriptionOptions.ajx?itemId=${row.itemId}&commId=${row.communityId}&type=Document&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
											 		</div>
										 		</c:if>
									 		</div>
									 		<c:if test="${not (row.itemType == 'Media' || row.itemType == 'PersonalBlog')}">
										 		<div class='person'>
										 			<c:if test="${row.communityType == 'Private'}">
										 				<i class='fa fa-lock' style='font-size: 15px;'></i>
										 			</c:if>
										 			<c:if test="${row.communityType == 'Protected'}">
										 				<i class='fa fa-shield' style='font-size: 15px;'></i>
										 			</c:if>
										 			<c:if test="${row.communityType == 'Public'}">
										 				<i class='fa fa-globe' style='font-size: 15px;'></i>
										 			</c:if>
										 			<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/home.do' target='_blank' >${row.communityName}</a>
										 		</div>
										 	</c:if>
									 	</div>
									 	<c:if test="${row.itemType == 'Event'}">
											<div class="pListInner" >
												<div class='leftImgLarge' style='height: 160px; margin-right: 10px;'>
													<c:choose>
														<c:when test='${row.eventPhotoPresent}'>
															<img src='${communityEraContext.contextUrl}/event/eventPoster.img?id=${row.itemId}' style="min-height: 160px;"/>
														</c:when>
														<c:otherwise>
															<img src='img/EventImg.png' style="min-height: 160px;"/>
														</c:otherwise>
													</c:choose>
												</div>
												<div class="coverHeader" >
													<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/event/showEventEntry.do?id=${row.itemId}' target='_blank' >${row.itemTitle}</a>
												</div>
												<div style="font-size: 16px; padding : 10px; line-height: 22px; font-weight: 500;">
													<b>
														<c:choose>
															<c:when test='${row.venue != ""}'>
																${row.venue}
															</c:when>
															<c:otherwise>
																${row.location}
															</c:otherwise>
														</c:choose>
													</b> <br />
													${row.address}${row.dateStarted}
													<br />
													<c:choose>
														<c:when test='${row.dateStarted != ""}'>					 
															 ${row.dateStarted}
															<c:if test='${row.dateEnded != ""}'>
																&nbsp;to&nbsp; ${row.dateEnded}
															</c:if>
														</c:when>
														<c:otherwise>
															<c:if test='${row.dateEnded != ""}'>
																--&nbsp;${row.dateEnded}
															</c:if>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</c:if>
										<c:if test="${row.itemType == 'Document'}">
											<c:if test="${row.contentType == 'Single'}">
												<div class="pListInner" >
													<div class='leftImgLarge2'>
														<c:if test="${row.iconType == 'xls'}">
															<img src='img/file/excelicon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'zip'}">
															<img src='img/file/zipicon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'pdf'}">
															<img src='img/file/pdficon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'xml'}">
															<img src='img/file/xmlicon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'img'}">
															<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id=${row.itemId}' style="min-height: 160px;"/>
														</c:if>
														<c:if test="${row.iconType == 'doc'}">
															<img src='img/file/wordicon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'ppt'}">
															<img src='img/file/powerpointicon.gif' class="fileimg"/>
														</c:if>
														<c:if test="${row.iconType == 'file'}">
															<img src='img/file/fileicon.gif' class="fileimg"/>
														</c:if>
													</div>
													<div class='coverHeader' ><a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/documentdisplay.do?backlink=ref&amp;id=${row.itemId}' target='_blank' >${row.itemTitle}</a></div>
													<div class="coverBody">
														${row.bodyDisplay}
													</div>
												</div>
											</c:if>
											<c:if test="${row.contentType == 'Group'}">
												<div class="pListInner" >
													<c:forEach items="${row.photoList}" var="row2" varStatus="status">
														<div class='leftImgLarge3'>
															<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/documentdisplay.do?backlink=ref&amp;id=${row2.docId}' 
															target='_blank' title="${row2.title}">
																<c:if test="${row2.iconType == 'xls'}">
																	<img src='img/file/excelicon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'zip'}">
																	<img src='img/file/zipicon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'pdf'}">
																	<img src='img/file/pdficon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'xml'}">
																	<img src='img/file/xmlicon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'img'}">
																	<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id=${row2.docId}' style="min-height: 160px;"/>
																</c:if>
																<c:if test="${row2.iconType == 'doc'}">
																	<img src='img/file/wordicon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'ppt'}">
																	<img src='img/file/powerpointicon.gif' class="fileimg"/>
																</c:if>
																<c:if test="${row2.iconType == 'file'}">
																	<img src='img/file/fileicon.gif' class="fileimg"/>
																</c:if>
															</a>
														</div>
													</c:forEach>
												</div>
											</c:if>
										</c:if>
										<c:if test="${row.itemType == 'Media'}">
											<div id="ltbox_${row.albumId}_${row.mediaGroupNumber}" class='imgOuter2'>
												<c:if test="${row.contentType == 'Group'}">
													<div id='demo-gallery${row.albumId}_${row.mediaGroupNumber}' class='demo-gallery'>
														<c:forEach items="${row.mediaList}" var="row2" varStatus="status">
															<a rel="gallery-${row.albumId}_${row.mediaGroupNumber}" href="${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row2.docId}" id="image_${row2.docId}" data-id="${row2.docId}" data-type="photo"
																data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row2.docId}" 
																data-url="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${row2.docId}" class="swipebox swipebox${row.albumId}_${row.mediaGroupNumber}" >
																<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row2.docId}' />
																<c:if test="${row.countDoc > 0 && status.index == row.lastItemNumber}">
																	<div class="caption2" ><span>+${row.countDoc}</span></div>
																</c:if>
															</a>
														</c:forEach>
													</div>
													<c:forEach items="${row.secondMediaList}" var="row3">
														<div style="display: none; height: 0px; width: 0px;" rel="gallery-${row.albumId}_${row.mediaGroupNumber}" href="${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row3.docId}" 
															id="image_${row3.docId}" data-id="${row3.docId}" data-type="photo"
															data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row3.docId}" 
															data-url="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${row3.docId}" class="swipebox swipebox${row.albumId}_${row.mediaGroupNumber}" >
															<img style="display: none;" src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row3.docId}' />
														</div>
													</c:forEach>
													<script type="text/javascript">
													  (function() {
														  $('#demo-gallery${row.albumId}_${row.mediaGroupNumber}').justifiedGallery({
														 	   	captions : false, 
																margins : 4,
																rowHeight: 200,
																border: -1,
																maxRowHeight: 260,
																fixedHeight : false	,
																randomize: false,
																lastRow : 'nojustify'
														 	});
														  $( '.swipebox${row.albumId}_${row.mediaGroupNumber}' ).swipebox();
													 	})();
													</script>
												</c:if>
												<c:if test="${row.contentType == 'Single'}">
													<div class=''>
														<a href="${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row.itemId}" id="image_${row.itemId}" data-id="${row.itemId}" data-type="library"
															data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row.itemId}" 
															data-url="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${row.itemId}" class="swipebox swipeboxxx${row.itemId}" >
															<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row.itemId}' />
														</a>
													</div>
													<script type="text/javascript">
													  (function() {
														  $( '.swipeboxxx${row.itemId}' ).swipebox();
													 	})();
													</script>
												</c:if>
											</div>
											<c:if test="${row.contentType == 'File'}">
												<div class='coverHeader' ><a href='${row.itemUrl}' target='_blank' >${row.itemTitle}</a></div>
											</c:if>
										</c:if>
										<c:if test="${row.itemType == 'Photo'}">
												<div id="ltbox_${row.folderId}_${row.docGroupNumber}" class='imgOuter2'>
													<c:if test="${row.contentType == 'Group'}">
														<div id='demo-gallery${row.folderId}_${row.docGroupNumber}' class='demo-gallery'>
															<c:forEach items="${row.photoList}" var="row2" varStatus="status">
																<a rel="gallery-${row.folderId}_${row.docGroupNumber}" href="${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row2.docId}" id="image_${row2.docId}" data-id="${row2.docId}" data-type="library"
																	data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row2.docId}" 
																	data-url="${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do" class="swipebox swipebox${row.folderId}_${row.docGroupNumber}" >
																	<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row2.docId}' />
																	<c:if test="${row.countDoc > 0 && status.index == row.lastItemNumber}">
																		<div class="caption2" ><span>+${row.countDoc}</span></div>
																	</c:if>
																</a>
															</c:forEach>
														</div>
														<c:forEach items="${row.secondPhotoList}" var="row3">
															<div style="display: none; height: 0px; width: 0px;" rel="gallery-${row.folderId}_${row.docGroupNumber}" href="${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row3.docId}" id="image_${row3.docId}" data-id="${row3.docId}" data-type="library"
																data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row3.docId}" 
																data-url="${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do" class="swipebox swipebox${row.folderId}_${row.docGroupNumber}" >
																<img style="display: none;" src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row3.docId}' />
															</div>
														</c:forEach>
														<script type="text/javascript">
														  (function() {
															  $('#demo-gallery${row.folderId}_${row.docGroupNumber}').justifiedGallery({
															 	   	captions : false, 
																	margins : 4,
																	rowHeight: 200,
																	border: -1,
																	maxRowHeight: 260,
																	fixedHeight : false	,
																	randomize: false,
																	lastRow : 'nojustify'
															 	});
															  $( '.swipebox${row.folderId}_${row.docGroupNumber}' ).swipebox();
														 	})();
														</script>
													</c:if>
													<c:if test="${row.contentType == 'Single'}">
														<div class=''>
															<a href="${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row.itemId}" id="image_${row.itemId}" data-id="${row.itemId}" data-type="library"
																data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row.itemId}" 
																data-url="${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do" class="swipebox swipeboxxx${row.itemId}" >
																<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row.itemId}' />
															</a>
														</div>
														<script type="text/javascript">
														  (function() {
															  $( '.swipeboxxx${row.itemId}' ).swipebox();
														 	})();
														</script>
													</c:if>
												</div>
											<c:if test="${row.contentType == 'File'}">
												<div class='coverHeader' ><a href='${row.itemUrl}' target='_blank' >${row.itemTitle}</a></div>
											</c:if>
										</c:if>
										<c:if test="${not (row.itemType == 'Document' || row.itemType == 'Photo' || row.itemType == 'Event' || row.itemType == 'Media')}">
											<c:if test="${row.imageCount > 0}">
												<div class="pListInner" >
													<div class='leftImgLarge'>
														<c:choose>
															<c:when test="${row.itemType == 'PersonalBlogEntry'}">
																<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId=${row.itemId}' />
															</c:when>
															<c:otherwise>
																<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=${row.itemType}&itemId=${row.itemId}' />
															</c:otherwise>
														</c:choose>
													</div>
													<div class='coverHeader' ><a href='${row.itemUrl}' target='_blank' >${row.itemTitle}</a></div>
													<div class="coverBody">
														${row.bodyDisplay}
													</div>
												</div>
											</c:if>
											<c:if test="${row.imageCount == 0}">
												<div class='coverHeader' ><a href='${row.itemUrl}' target='_blank' >${row.itemTitle}</a></div>
												<div class="coverBody">${row.bodyDisplay}</div>
											</c:if>
										</c:if>
										<c:if test="${not (row.itemType == 'Document' || row.contentType == 'Group')}">
											<c:if test="${row.itemType == 'ForumTopic'}">
												<c:if test="${row.commentCount > 0}">
										 			<c:forEach items="${row.forumResponses}" var="rowRes">
										 				<div class="pListInner" style="background-color: #F1F3F5;">
											 				<div class="leftImgSml" style="padding: 6px 0px 0px 6px;">
											 					<c:choose>
																	<c:when test="${rowRes.author.photoPresent}">
																		<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${rowRes.authorId}&backlink=ref' title='${rowRes.author.fullName}'>
													 						<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${rowRes.authorId}' style='height: 40px; width: 40px;'/>
													 					</a>
																	</c:when>
																	<c:otherwise>
																		<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='${rowRes.author.fullName}' />
																	</c:otherwise>
																</c:choose>
											 				</div> 
											 				<div class="detailRes" style='margin-left: 6px;'>
												 				<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${rowRes.authorId}&backlink=ref' class='memberInfoId0'
										 							title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${rowRes.authorId}'>${rowRes.author.fullName}</a><span> replied on ${rowRes.repliedOn}</span>
												 				<div class='heading' style='word-wrap: normal;'>${rowRes.subject}</div>
												 				<div style='padding: 0px 0px 0px 3px;'>${rowRes.body}</div>
												 				<div class='commLike'><a id='lkunlk${rowRes.id}ForumResponse${rowRes.topicId}${row.communityId}' href='javascript:void(0);' class='likeBtn'
												 					 onclick='likeEntry(&#39;${rowRes.topicId}&#39;, &#39;${row.communityId}&#39;, &#39;ForumResponse&#39;, &#39;${rowRes.id}&#39;);' >${rowRes.likeString}</a>
												 				<i class='fa fa-circle' style='position: relative; top: -2px; margin: 0px 8px 0px 6px; font-size: 6px; color: #A7A9AB;'></i>
												 				<span id='lkunlkcnt${rowRes.id}ForumResponse${rowRes.topicId}${row.communityId}'>${rowRes.likeCount}</span><i class='fa fa-thumbs-up' style='margin: 0px 8px; color: #A7A9AB;'></i>
												 				</div>
											 				</div>
											 			</div>
										 			</c:forEach>
										 		</c:if>
										 	</c:if>
										 	<div class='person personLast' >
										 		<a id='lkunlk0${row.itemType}${row.itemId}${row.communityId}' href='javascript:void(0);' 
										 			onclick='likeEntry(&#39;${row.itemId}&#39;, &#39;${row.communityId}&#39;, &#39;${row.itemType}&#39;, &#39;0&#39;);' class='lkBtn'>${row.likeString}</a>
										 		<c:if test="${!(row.itemType == 'Event' || row.itemType == 'ForumTopic')}">
											 		<i class="fa fa-circle" style="position: relative; top: -2px; margin: 0px 8px 0px 12px; font-size: 6px;"></i>
											 		<a href='javascript:void(0);' onclick='showCommentsTop(&#39;${row.itemId}&#39;, &#39;${row.communityId}&#39;, &#39;${row.itemType}&#39;);' class='lkBtn'>Comment</a>
											 	</c:if>
									 			<span style="float:right;">
													<span id='lkunlkcnt0${row.itemType}${row.itemId}${row.communityId}'>${row.likeCount}</span><i class="fa fa-thumbs-up" style="margin: 0px 8px; "></i>
													<c:if test="${row.itemType != 'Event'}">
														<i class="fa fa-circle" style="position: relative; top: -2px; margin: 0px 8px 0px 12px; font-size: 6px;"></i>
														<i class="fa fa-eye" style="margin: 0px 8px;"></i>${row.visitors}
													</c:if>
												</span>
								 			</div>
								 			<c:if test="${!(row.itemType == 'Event' || row.itemType == 'ForumTopic')}">
									 			<div class='commentSec' id='${row.itemType}-${row.itemId}-${row.communityId}' style="display: none;">
									 				<c:if test="${row.commentCount > 3}">
									 					<a href='javascript:void(0);' onclick='showComments(&#39;${row.itemId}&#39;, &#39;${row.communityId}&#39;, &#39;${row.itemType}&#39;);' 
									 						id='spc${row.itemType}${row.itemId}${row.communityId}' class='lkBtn'>Show previous comments</a>
									 				</c:if>
									 				<input type="hidden" id="pgCount${row.itemType}${row.itemId}${row.communityId}" value="0" />
									 				<div id="waitMessage${row.itemType}${row.itemId}${row.communityId}" style="display: none;">
									 					<div class="cssload-square" >
															<div class="cssload-square-part cssload-square-green" ></div>
															<div class="cssload-square-part cssload-square-pink" ></div>
															<div class="cssload-square-blend" ></div>
														</div>
									 				</div>
									 				<div class='commentList'>
									 					<ul id='${row.itemType}${row.itemId}${row.communityId}'>
									 					</ul>
									 				</div>
									 				<div class='addCommForm'>
														<textarea id='intComm${row.itemType}${row.itemId}${row.communityId}' placeholder='Add a comment...' cols='60' rows='1' class='edit' 
															onkeyup='toggleBtn(&#39;${row.itemId}&#39;, &#39;${row.communityId}&#39;, &#39;${row.itemType}&#39;);'></textarea>
														<div class='buttonContainer'>
														<a href='javascript:void(0);' onclick='addComments(&#39;${row.itemId}&#39;, &#39;${row.communityId}&#39;, &#39;${row.itemType}&#39;);' 
									 						id='addc${row.itemType}${row.itemId}${row.communityId}' class='commentSubmit'>Comment</a>
														</div>
													</div>
												</div>
											</c:if>
										</c:if>
									</div>
								</div>
								<c:if test="${row.adsPosition}">
									<div class="inboxAds">
										<c:if test="${communityEraContext.production}">
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
										</c:if>
									</div>
								</c:if>
							</c:forEach>
							<c:if test="${command.pageCount == 1}">
								<div class='paginatedList' style='height: unset;'>
								 	<div class='details' style='width: 100%;'>
								 		<div class='heading' style='text-align: center; width: 100%;'>
								 			<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}&backlink=ref' class='memberInfoId0' 
								 				title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${command.user.id}' >${command.user.fullName}</a>
								 			<span style='line-height: 1.4; color:#A7A9AB;'> joined <Strong>Jhapak</Strong> on ${command.user.dateRegistered}</span>
								 		</div>
									</div>
								</div>
							</c:if>
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
							<span onclick="return false" >Your Calendar</span>
						</div>
						<div id="calendar" style="padding: 4px;" ></div>
						<div id="waitBLAthMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div class="view">
							<a href="${communityEraContext.contextUrl}/pers/showCalendar.do?backlink=ref">View Calendar</a>
						</div>
					</div>
					<c:if test="${command.countConnections > 0}">
						<div class="inbox">
							<div class="eyebrow">
								<span onclick="return false" >Your Connections</span>
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
								<a href="${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref">View All (${command.countConnections} connections)</a>
							</div>
						</div>
					</c:if>
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
					
					<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
				</div>
				<a href="#0" class="cd-top">Top</a>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
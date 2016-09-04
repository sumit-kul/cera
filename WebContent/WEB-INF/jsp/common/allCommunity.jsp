<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, forum, wiki" />
		<meta name="keywords" content="Jhapak, blog, community, forum, wiki, how-to" />
		<title>Jhapak - Communities</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="">
			#container .left-panel .commSection .paginatedList .details .heading {
			    max-width: 630px;
			    overflow: hidden;
			    white-space: nowrap;
				text-overflow: ellipsis;
			}
			
			#container .left-panel .commSection .communities .type form {
				float: right;
				clear: none;
			}
			
			@media  only screen and (max-width: 480px) {
				#container .left-panel .commSection .communities .type form select {
				    margin: 0px 6px 5px 0px;
				    padding: 2px 3px;
				    width: 100px;
				}
				
				#container .left-panel .commSection .communities .headerIcon {
				    padding: 20px 5px 3px;
					font-size: 20px;
					width: 20px;
					height: 40px;
				}
				
				#community .clist li {
					width: 90%;
					float: none;
					max-height: 310px;
				}
				
				#community .clist li.columnsAlt {
					margin-right: 0px;
				}
				
				#community .clist li a {
					height: 300px;
					background-size: 100% !important;
				}
				
				#community .clist li {
					width: 90%;
					max-height: 300px;
					height: 100%;
				}
				
				#community .clist li a {
					width: 100%;
				}
			}
			
			@media only screen and (min-width: 481px) and (max-width: 598px) {
				#community .clist li.columns {
					margin-left: 0px;
					margin-right: 2%;
				}
				#community .clist li.columnsAlt {
					margin-right: 2%;
				}
				
				#community .clist li {
					width: 44%;
					max-height: 410px;
					min-height: 260px;
				}
				
				#community .clist li a {
					max-height: 400px;
					min-height: 250px;
					width: 100%;
					background-size: 100% !important;
				}
			}
			
			@media only screen and (min-width: 599px) and (max-width: 767px) {
				#community .clist li.columns {
					margin-left: 0px;
					margin-right: 2%;
				}
				#community .clist li.columnsAlt {
					margin-right: 2%;
				}
				#community .clist li {
					width: 45%;
					max-height: 410px;
					min-height: 260px;
				}
				
				#community .clist li a {
					max-height: 400px;
					min-height: 250px;
					width: 100%;
					background-size: 100% !important;
				}
				
				#community .clist li.SecAlt {
					margin-left: 2%;
				}
			}
			
			@media only screen and (min-width: 992px) and (max-width: 1060px) {
				#container .left-panel {
				    width: 760px;
				}
				
				#community .clist {
				    list-style: outside none none;
				    display: inline-block;
				    width: 760px;
				}
			}
		</style>
		
		<script type="text/javascript">
			function joinRequest(rowId, ctype) { 
			    var ref = '${communityEraContext.contextUrl}/community/joinCommunityRequest.ajx?id='+rowId;
			    var mess = '';
			    var type = BootstrapDialog.TYPE_INFO;
			    var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	type = BootstrapDialog.TYPE_INFO;
			    	mess = '<p class="addTagHeader">You are not logged-in.<br/> Please login first to join a community. </p>';
			    } else if (ctype == 'Protected') {
			    	mess = '<p class="addTagHeader"><strong>This is a protected community.</strong><br/><br/>'+		
					'To request membership click the Go button below.  '+
					'Your request to join the community will be actioned by the community admin.  '+
					'Notification of your acceptance to the community will be sent to your email address.<br/><br />Please write a suitable message'+
					'</p><br/><div id="statusText3"><textarea class="form-control" name="body" id="textareaId">I\'d like to join this community b\'coz... </textarea></div>';
					
			    } else {
			    	mess = '<p class="addTagHeader"><strong>This is a public community.</strong><br/><br/>'+
				    'To join this community click the Go button below. '+
					'You will be added to the community member list and will immediately have access to the community content. '+
					'</p>';
			    }
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Join a community',
		                message: mess,
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
			    	var succBtn = 'Join';
				    if (ctype == 'Protected') {
				    	succBtn = 'Apply';
				    }
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Join a community',
		                message: mess,
		                closable: false,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                	id: 'button-close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                },
		                {
		                	label: succBtn,
		                	cssClass: 'btn-info',
		                	id: 'button-Go',
		                	action: function(dialogRef){
		                	dialogRef.getButton('button-Go').disable();
		                	dialogRef.getButton('button-close').disable();
		                	dialogRef.getButton('button-Go').spin();
		                	if (ctype == 'Protected') {
			                	var fruit = dialogRef.getModalBody().find('#textareaId').val();
			                	ref = ref + '&optionalComment=' + fruit;
		                	}
		                	$.ajax({url:ref,success:function(result){
		                		$('#reqId-'+rowId).html(result.returnString);
	                        	dialogRef.close();
	            	    	  }});
	                    	}
		                }]
		            });
			    }
			}

			function applyFilter(filterUrl){
				var fTagList = document.getElementById("fTagList").value;
				var commOption = document.getElementById("communityOption").value;
				var sortOption = document.getElementById("sortByOption").value;
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&fTagList="+fTagList+"&communityOption="+commOption+"&sortByOption="+sortOption+"&toggleList="+toggleList;
				window.location.href = filterUrl;
			}

			var count = 2;
			function infinite_scrolling(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    	var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
							$.ajax({url:"${communityEraContext.contextUrl}/community/showCommunities.ajx?jPage="+count+"&communityOption="+$("#communityOption").val()+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){ 
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									 var welcome = this['bodyForDisplay'];
									 var rowId = this['id'];
									 var trclass = '';
									 var isAdsPosition = false;
			   						 if(i%3 != 0){
			   							 trclass = 'Alt';
			   						 } 
			   						if(i%6 == 0){
			   							isAdsPosition = true;
			   						 }
									 sName += "<li class='columns"+trclass+"' >";
									 var imgsrc = "";
									 if(this['logoPresent'] == "true"){
										 imgsrc = "${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+rowId;
									 } else {
										 imgsrc = "img/community_Image.png";
									 }
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/home.do' style='background:url(&#39;"+imgsrc+"&#39;) no-repeat' target='_blank'>";
									 sName += "<span class='likes'><strong>"+this['name']+"</strong>"+this['memberCountString']+"</span>";
									 sName += "</a>";
									 
									 if(!this['member'] && this['type'] == "Protected"){
										 if(this['membershipRequested']){
										 	sName += "<div class='entry' id='reqId-"+rowId+"' ><span>Membership requested</span></div>";
										 } else {
											 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest(&#39;"+rowId+"&#39;,&#39;"+this['type']+"&#39;)' href='javascript:void(0);' >Apply to join</a></div>";
										 }
									 }
									 
									 if(!this['member'] && this['type'] == "Public"){
										 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest(&#39;"+rowId+"&#39;, &#39;"+this['type']+"&#39;)' href='javascript:void(0);' >Join</a></div>";
									 }
									 
									 sName += "</li>";
									 if(isAdsPosition) {
										 sName += '<div class="inboxAds">';
										 sName += '<ins class="adsbygoogle" style="display:block" data-ad-client="ca-pub-8702017865901113" data-ad-slot="3781277785" data-ad-format="auto"></ins>';
										 sName += '</div>';
									 }
									 i++;
										}
									);
								 $(".clist").append(sName);
								// Hide message
						        $("#waitMessage").hide();

						        toggleOnLoad();
						        normalQtip();
								memberInfoQtip();
								optionListQtip();
							    } ,
			                	complete: function () {
								     count++;
								     $(".adsbygoogle").each(function () { (adsbygoogle = window.adsbygoogle || []).push({}); });
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
			   
			$.ajax({url:"${communityEraContext.contextUrl}/community/allCommunitiesCloud.ajx?communityOption="+$("#communityOption").val()+"&fTagList="+$("#fTagList").val(),dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 var count = 'community';
					 if (this['count'] > 1) {
						 count = 'communities';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);'";
					 aName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' id='"+this['tagText']+"'>"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 if(aName == "<ul>"){
					$("#cloud").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
				}else{
					$("#cloud").html(aName+"</ul>");
				}
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 var count = 'community';
					 if (this['count'] > 1) {
						 count = 'communities';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);' ";
					 bName += " title='"+content+"' class='normalTip size-1' >"+this['tagText']+"</a></span>";
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
				normalQtip();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitCloudMessage").show();
		         } 
		    });
		    
			$.ajax({url:"${communityEraContext.contextUrl}/community/communityPannel.ajx?type=top",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					if(this['logoPresent']){
						temp += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['entryId']+"' width='50' height='50' style='padding: 3px; float: left;' />";
					} else {
						temp += "<img src='img/community_Image.png' width='50' height='50' style='padding: 3px; float: left;' />";
					}
					temp += "<div class='pannelTitle'>";
					temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['entryId']+"/home.do' class='normalTip' title='"+this['name']+"' >"+this['editedName']+"</a>";
					temp += "<p style='width: 100%;'>"+this['memberCount']+" members</p>";
					temp += "</div>";
					temp += "</div>";
					});
				temp += "</div>";
				$("#topStoriesList").html(temp);
		        $("#waitTpStories").hide();
		        normalQtip();
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
		});
		</script>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel">
				<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
				<div class="commSection" style="display: inline-block;">
					<div class="communities">
						<div class="headerIcon">
							<i class="fa fa-users" ></i>
						</div>
						<div class="type">
							<h4>Communities</h4>
							<form:form showFieldErrors="true">
								<form:dropdown id="communityOption" path="communityOption" fieldLabel="Type:">
									<form:optionlist options="${command.communityOptions}" />
								</form:dropdown>
								
								<form:dropdown id="sortByOption" path="sortByOption" fieldLabel="Sort By:">
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="submit" value="Go" />
								<input type="hidden" name="page" value="${command.page}" />
								<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
								<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
								<input type="hidden" id="pgCount" value="${command.pageCount}" />
							</form:form>
						</div>
						<c:if test="${not empty command.displayedFilterTag}">
							<div class="filterBox">
								<label style="overflow: hidden; position: relative;">
								     Filtered by: 
								    <span>
								    	${command.displayedFilterTag}
								    </span>
								</label>
							</div>
						</c:if>
					</div>

					<div id="community" class="communitysection">
						<ul class="clist">
							<div class="inboxAds" style="margin-top: 10px;">
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
							<c:forEach items="${command.scrollerPage}" var="row">
								<c:if test="${row.oddRow}">
									<c:set var="trclass" value='Alt' />
								</c:if>
								<c:if test="${row.evenRow}">
									<c:set var="trclass" value='' />
								</c:if>
								<c:if test="${row.secOddRow}">
									<c:set var="sectrclass" value='SecAlt' />
								</c:if>
								<c:if test="${row.secEvenRow}">
									<c:set var="sectrclass" value='' />
								</c:if>
								<li class="columns${trclass} ${sectrclass}">
									<c:choose>
										<c:when test="${row.logoPresent == 'true'}">
											<c:set var="imgsrc" value='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId=${row.id}' />
										</c:when>
										<c:otherwise>
											<c:set var="imgsrc" value='img/community_Image.png' />
										</c:otherwise>
									</c:choose>
									<a href="${communityEraContext.contextUrl}/cid/${row.id}/home.do" style="background:url(${imgsrc}) no-repeat" target="_blank">
										<span class="likes">
											<strong>${row.name}</strong>
											${row.memberCountString}
										</span>
									</a>
									<c:if test="${!row.member && row.type == 'Protected'}">
			      						<c:choose>
											<c:when test="${row.membershipRequested}">
												<div class="entry" id="reqId-${row.id}"><span>Membership requested</span></div>
											</c:when>
											<c:otherwise>
												<div class="entry" id="reqId-${row.id}" ><a onclick="joinRequest(&#39;${row.id}&#39;, &#39;${row.type}&#39;)" href="javascript:void(0);" >Apply to join</a></div>
											</c:otherwise>
										</c:choose>
			      					</c:if>
			      					<c:if test="${!row.member && row.type == 'Public'}">
			      						<div class="entry" id="reqId-${row.id}" ><a onclick="joinRequest(&#39;${row.id}&#39;, &#39;${row.type}&#39;)" href="javascript:void(0);" >Join</a></div>
			      					</c:if>
					      		</li>
					      		<c:if test="${row.adsPosition}">
					      			<c:if test="${communityEraContext.production}">
										<div class="inboxAds">
											<!-- CERA_RES -->
											<ins class="adsbygoogle"
											     style="display:block"
											     data-ad-client="ca-pub-8702017865901113"
											     data-ad-slot="3781277785"
											     data-ad-format="auto"></ins>
											<script>
											(adsbygoogle = window.adsbygoogle || []).push({});
											</script>
										</div>
									</c:if>
								</c:if>
				      		</c:forEach>
				      	</ul>
			      	</div>
				</div> 	
				<div id="waitMessage" style="display: none;">
					<div class="cssload-square" >
						<div class="cssload-square-part cssload-square-green" ></div>
						<div class="cssload-square-part cssload-square-pink" ></div>
						<div class="cssload-square-blend" ></div>
					</div>
				</div>
			</div> 	
			
			<div class="right-panel" style="margin-top: 0px;">
				<a href="${communityEraContext.contextUrl}/community/createNewCommunity.do" class="btnmain normalTip" style="width: 100%; margin-bottom: 10px;" title="Start a new community">
					<i class="fa fa-users" ></i><i class="fa fa-plus" style="margin-right: 8px; font-size: 10px; margin-left: -1px;"></i>Start a Community</a>

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
					
				<div class="inbox" style="display: inline-block;">
					<div class="eyebrow">
						<span onclick="return false" ><i class="fa fa-users" style="margin-right: 8px;"></i>Most Active Communities</span>
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
								<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-sep-Cloud'></i></h3>
								<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-sep-Cloud'></i></a>
								<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
							</c:when>
							<c:otherwise>
								<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-sep-Cloud'></i></h3>
								<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-sep-Cloud'></i></a>
								<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
						  	</c:otherwise>
						</c:choose>
					</ul> <br/>
				</div>
				
				<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
			</div>
			<a href="#0" class="cd-top">Top</a>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
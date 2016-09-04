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
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="All upcoming events created at Jhapak" />
		<meta name="keywords" content="Jhapak, Event, upcoming, all-events, event-list" />
		<title>Jhapak - All Upcoming Events</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		
		<script type="text/javascript" src="js/jquery.aniview.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/animateScroll.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="">
			.columns .footer {
				min-height: 60px;
			}
			
			#container .left-panel .commSection .communities .type form {
				float: right;
				clear: none;
			}
			
			header .section .rowBlock .form-part .search .advance {
			    float: right;
			    padding: 15px 0px 0px 10px;
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

			var count = 2;
			function infinite_scrolling(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    	var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
							$.ajax({url:"${communityEraContext.contextUrl}/event/allEvents.ajx?jPage="+count+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){ 
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									 var welcome = this['bodyForDisplay'];
									 var rowId = this['id'];
									 var trclass = '';
			   						 if(i%3 != 0){
			   							 trclass = 'Alt';
			   						 }
									 sName += "<div class='aniview reallyslow columns panelBlk"+trclass+"' av-animation='fadeInUp' id='viewSlide"+count+"'>";
									 sName += "<div class='panelBox' >";
									 sName += "<div class='top-info-bar'>";
									 sName += "</div>";
									 sName += "<div class='image-section'>";
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityID']+"/event/showEventEntry.do?backlink=ref&amp;id="+rowId+"'>";
									 if(this['logoPresent'] == 'Y'){
										 sName += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['communityID']+"'/>";
									 } else {
										 sName += "<img src='img/community_Image.png'/>";
									 }
									 sName += "<div class='text'><span class='normalTip' title='"+this['name']+"'>"+this['subject']+"</span></div>";
									 sName += "</a></div>";
									 
									 sName += "<div class='footer'><div class='box-grid-author-meta'>";
									 sName += "<ul class='bottSecLst' style='margin-left: 10px; padding-top: 6px;'>";
									 sName += "<li style='padding: 0px 30px 10px 0px; margin: 0px 0px 0px 10px;' >";
									 if(this['photoPresent'] == "Y"){
										 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id="+this['authorId']+"' title='"+this['authorName']+"' class='normalTip'/>";
									 } else {
										 sName += "<img src='img/user_icon.png' title='${row.authorName}' class='normalTip'/>";
									 }
									 sName += "<span title='"+this['firstPostDate']+"'class='normalTip'>"+this['postedOn']+"</span>";
									 sName += "<h4 style='margin-left: 16px;'>created</h4></li>";
									 sName += "</ul></div></div>";
									 sName += "</div>";
									 sName += "</div>";
									 i++;
										}
									);
								 $("#rowSection").append(sName);
								// Hide message
						        $("#waitMessage").hide();
						        normalQtip();
								memberInfoQtip();
								optionListQtip();
							    } ,
			                	complete: function () {
								     count++;
								     var options = {
											    animateThreshold: 100,
											    scrollPollInterval: 20
											}
											$('viewSlide'+count).AniView(options);
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
			
			var options = {
				    animateThreshold: 100,
				    scrollPollInterval: 50
				}
				$('.aniview').AniView(options);
			normalQtip();
			$.ajax({url:"${communityEraContext.contextUrl}/event/eventPannel.ajx?type=popular",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					if(this['eventPhotoPresent']){
						temp += "<img src='${communityEraContext.contextUrl}/event/eventPoster.img?id="+this['entryId']+"' width='60' height='36' style='padding: 3px; float: left; border-radius: 6px;' />";
					} else {
						temp += "<img src='img/EventImg.png' width='60' height='36' style='padding: 3px; float: left; border-radius: 6px;' />";
					}
					temp += "<div class='pannelTitle' style='width: 75%; word-wrap: normal;'>";
					temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"//event/showEventEntry.do?id="+this['entryId']+"'>"+this['name']+"</a>";
					 temp += "</div></div>";
					});
				temp += "</div>";
				$("#popularEventsList").html(temp);
		        $("#waitPEEvents").hide();
			    },
		         beforeSend: function () {
		             $("#waitPEEvents").show();
		         } 
		    	});

			$.ajax({url:"${communityEraContext.contextUrl}/event/eventPannel.ajx",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					if(this['eventPhotoPresent']){
						temp += "<img src='${communityEraContext.contextUrl}/event/eventPoster.img?id="+this['entryId']+"' width='60' height='36' style='padding: 3px; float: left; border-radius: 6px;' />";
					} else {
						temp += "<img src='img/EventImg.png' width='60' height='36' style='padding: 3px; float: left; border-radius: 6px;' />";
					}
					temp += "<div class='pannelTitle' style='width: 75%; word-wrap: normal;'>";
					temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"//event/showEventEntry.do?id="+this['entryId']+"'>"+this['name']+"</a>";
					 temp += "</div></div>";
					});
				temp += "</div>";
				$("#justPublishedList").html(temp);
		        $("#waitJPEvents").hide();
			    },
		         beforeSend: function () {
		             $("#waitJPEvents").show();
		         } 
		    	});
			$.ajax({url:"${communityEraContext.contextUrl}/common/allToolsCloud.ajx?parentType=Event",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				var sortOption = $("#sortByOption").val();
				 $.each(result.aData, function() {
					 var count = 'event';
					 if (this['count'] > 1) {
						 count = 'events';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/event/allEvents.do?filterTag="+this['tagText']+"&#39;);'";
					 aName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' id='"+this['tagText']+"'>"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 $("#cloud").html(aName+"</ul>");
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 var count = 'topic';
					 if (this['count'] > 1) {
						 count = 'topics';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span class='size-"+this['cloudSet']+"' ><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/event/allEvents.do?filterTag="+this['tagText']+"&#39;);' ";
					 bName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' >"+this['tagText']+"</a></span>";
					 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
					 bName += "</tr>";
						});
				 $("#cloudList").html(bName+"</table>");
				 
				// Hide message
		        $("#waitCloudMessage").hide();
		        normalQtip();
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
		<div id="container" >
			<div class="left-panel">
				<div class="commSection">
					<div class="communities">
						<div class="headerIcon">
							<i class="fa fa-calendar-check-o" ></i>
						</div>
						<div class="type">
							<h4>Upcoming Events</h4>
							<form:form showFieldErrors="true">
									<form:dropdown path="sortByOption" fieldLabel="Sort By:" >
										<form:optionlist options="${command.sortByOptionOptions}" />
									</form:dropdown>
									<input type="hidden" id="sortByOption" value="${command.sortByOption}" />
									<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
									<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
									<input type="hidden" id="pgCount" value="${command.pageCount}" />
									<input type="submit" value="Go" class="search_btn" />
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
					
					<div class="rowLine" id="rowSection">
						<c:forEach items="${command.scrollerPage}" var="row">
							<c:if test="${row.oddRow}">
								<c:set var="trclass" value='Alt' />
							</c:if>
							<c:if test="${row.evenRow}">
								<c:set var="trclass" value='' />
							</c:if>
			      			<div class="aniview reallyslow columns panelBlk${trclass}" av-animation="fadeInUp" style="width: 48.2%; margin-bottom: 120px;">
			      				<div class="panelBox">
			      					<div class='top-info-bar'>
			      					</div>
			      					<div class="image-section" style="height: 130px;">
			      						<a href="${communityEraContext.contextUrl}/cid/${row.communityID}/event/showEventEntry.do?id=${row.entryId}">
			      							<c:choose>
												<c:when test="${row.eventPhotoPresent}">						 
													 <img src="${communityEraContext.contextUrl}/event/eventPoster.img?id=${row.entryId}" width="365" height="130" style="min-height: 140px;"/>
												</c:when>
												<c:otherwise>
													<img src='img/EventImg.png' width='365' height='130' style="min-height: 140px;" />
												</c:otherwise>
											</c:choose>
			      							
											<div class='text' style="width: 362px;"><span class="normalTip" title="${row.name}">${row.name}</span></div>
										</a>
			      					</div>
			      					<div class='footer' style="max-width: 100%; bottom: -100px; min-height: 100px;">
										<div class='box-grid-author-meta'>
											<ul class="bottSecLst" style="margin-left: 10px; padding-top: 6px;">
												<li style="padding: 0px 30px 10px 0px; margin: 0px 0px 0px 10px;" >
													<c:choose>
														<c:when test="${row.photoPresent == 'Y'}">						 
															 <img src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.posterId}" title="${row.contactName}" class='normalTip' style="width: 40px; height: 40px;" />
														</c:when>
														<c:otherwise>
															<img src="img/user_icon.png" title="${row.contactName}" class='normalTip' style="width: 40px; height: 40px;" />
														</c:otherwise>
													</c:choose>
													<span style="color: #184A72; padding: 6px 0px 0px; font-size: 14px; line-height: 20px; width: 100%;">
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
													</span>
													<span style="text-align: justify; font-size: 16px; line-height: 20px;">${row.city}, ${row.country}</span>
													<h4 style="margin-left: 16px;">Hosted by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}&backlink=ref' class='memberInfo author-name' 
														title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${row.posterId}'>${row.contactName}</a></h4>
												</li>
											</ul>
										</div>
									</div>
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

				<div class="inbox" style="display: inline-block;">
					<div class="eyebrow">
						<span onclick="return false" >Popular Events</span>
					</div>
					<div id="popularEventsList" style="padding: 4px;" ></div>
					<div id="waitPEEvents" style="display: none;"><p class="showCloudWaitMessage"></p></div>
				</div>

				<div class="inbox" style="display: inline-block;">
					<div class="eyebrow">
						<span onclick="return false" >Just Published</span>
					</div>
					<div id="justPublishedList" style="padding: 4px;" ></div>
					<div id="waitJPEvents" style="display: none;"><p class="showCloudWaitMessage"></p></div>
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
				<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
			</div>
			<a href="#0" class="cd-top">Top</a>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
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
		<meta name="robots" content="noindex, follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="All forum topic posted at Jhapak" />
		<meta name="keywords" content="Jhapak, Forum, Topics, question, asked, all-forum-topics, topic-list" />
		<title>Jhapak - All Forum Topics</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/animateScroll.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
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
			    padding: 15px 0px 0px 10px;
			}
			
			@media only screen and (max-width: 991px) {
				#container .left-panel .commSection .paginatedList {
					margin: 10px;
					width: 96%;
				}
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
							$.ajax({url:"${communityEraContext.contextUrl}/forum/allForums.ajx?jPage="+count+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){ 
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									var isAdsPosition = false;
			   						if(i%6 == 0){
			   							isAdsPosition = true;
			   						 }
									 var rowId = this['id'];
									 sName += "<div class='paginatedList' >";
									 sName += "<div class='leftImg' >";
									 if(this['lastPosterId'] != this['authorId']){
										 if(this['photoPresent'] == "Y"){
											 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['authorId']+"&backlink=ref' title='"+this['authorName']+" - Original Poster'>";
												 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['authorId']+"' style='height: 40px; width: 40px;'/></a>";
										 } else{
											 sName += "<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='"+this['authorName']+" - Original Poster' />";
										 }
										 if(this['photoPresent'] == "Y"){
											 sName += "<br/><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['lastPosterId']+"&backlink=ref' title='"+this['lastPosterName']+" - Most Recent Poster'>";
											 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['lastPosterId']+"' style='height: 40px; width: 40px; margin-top: 8px;'/></a>";
										 } else {
											 sName += "<br/><img src='img/user_icon.png' title='"+this['lastPosterName']+" - Most Recent Poster' style='height: 40px; width: 40px; margin-top: 8px;'/>";
										 }
			   						 } else {
			   							if(this['lastPostPhotoPresent'] == "Y"){
			   								sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['authorId']+"&backlink=ref' title='"+this['authorName']+" - Original Poster, Most Recent Poster'>";
			   									sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['authorId']+"' style='height: 40px; width: 40px;'/></a>";
			   							} else {
			   								sName += "<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='"+this['authorName']+" - Original Poster, Most Recent Poster'' />";
			   							}
			   						 }
									 sName += "</div><div class='details'>";
									 sName += "<div class='heading' style='word-wrap: normal;'>";
									 if(this['closed']){
										 sName += "<i class='fa fa-lock' style='margin-right: 8px; color: #A7A9AB;' title='This topic has now been closed for new replies'></i>";
			   						 }
									 if(this['sticky']){
										 sName += "<i class='fa fa-thumb-tack' style='padding: 4px; font-size: 16px; color: #A7A9AB;' title='This topic is pinned; it will display at the top of community forum'></i>";
			   						 }
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/forum/forumThread.do?backlink=ref&amp;id="+this['id']+"' >"+this['displaySubject']+"</a></div>";
									 sName += "<div class='person'>";
									 sName += "<span title='First post: "+this['firstPostDate']+"'>"+this['postedOn']+"</span><i class='fa fa-circle' style='margin: 0px 8px; font-size: 6px;'></i>";
									 sName += "<span title='Last post: "+this['lastPostDate']+"'>"+this['lastPostedOn']+"</span><i class='a-icon-text-separator'></i> "+this['responseCount']+" replies";
									 sName += "<i class='a-icon-text-separator'></i> "+this['visitors']+" views";
									 sName += "</div>";
									 sName += "<p style='font-size: 13px;'>"+this['displayBody']+"</p></div></div>";

									 if(isAdsPosition) {
										 sName += '<div class="inboxAds" style="margin-bottom: 0px;">';
										 sName += '<ins class="adsbygoogle" style="display:inline-block;width:728px;height:90px" data-ad-client="ca-pub-8702017865901113" data-ad-slot="4053806181"></ins>';
										 sName += '</div>';
									 }
									 
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

			$.ajax({url:"${communityEraContext.contextUrl}/forum/forumPannel.ajx?type=hot",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					if(this['logoPresent']){
						temp += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['communityId']+"' width='30' height='30' style='padding: 3px; float: left;' title='"+this['communityName']+"'/>";
					} else {
						temp += "<img src='img/community_Image.png' width='30' height='30' style='padding: 3px; float: left;' title='"+this['communityName']+"' />";
					}
					temp += "<div class='pannelTitle' style='width: 85%; word-wrap: normal;'>";
					temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/forum/forumThread.do?id="+this['topicId']+"' class='normalTip' title='"+this['subject']+"'>"+this['editedSubject']+"</a>";
					 temp += "</div></div>";
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
	    	
			normalQtip();
			$.ajax({url:"${communityEraContext.contextUrl}/common/allToolsCloud.ajx?parentType=ForumTopic",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				var sortOption = $("#sortByOption").val();
				 $.each(result.aData, function() {
					 var count = 'topic';
					 if (this['count'] > 1) {
						 count = 'topics';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/forum/allForums.do?filterTag="+this['tagText']+"&#39;);'";
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
					 bName += "<span class='size-"+this['cloudSet']+"' ><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/forum/allForums.do?filterTag="+this['tagText']+"&#39;);' ";
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
					<div class="communities" style="margin-bottom: 10px;">
						<div class="headerIcon">
							<i class="fa fa-comments-o" ></i>
						</div>
						<div class="type">
							<h4>Forum Topics</h4>
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
					
					<div class="inboxAds" style="margin-bottom: 0px;">
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
					
					<div class="rowLine" id="rowSection" style="margin-top: 0px;">
						<c:forEach items="${command.scrollerPage}" var="row">
							<div class='paginatedList'>
								<div class='leftImg'>
									<c:choose>
										<c:when test="${row.lastPosterId != row.authorId}">						 
											 <c:choose>
												<c:when test="${row.photoPresent == 'Y'}">						 
													<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.authorId}&backlink=ref' title='${row.authorName} - Original Poster'>
								 						<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.authorId}' style='height: 40px; width: 40px;'/>
								 					</a>
												</c:when>
												<c:otherwise>
													<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='${row.authorName} - Original Poster' />
												</c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${row.lastPostPhotoPresent == 'Y'}">						 
													<br/><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.lastPosterId}&backlink=ref' title='${row.lastPosterName} - Most Recent Poster'>
								 						<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.lastPosterId}' style='height: 40px; width: 40px; margin-top: 8px;'/>
								 					</a>
												</c:when>
												<c:otherwise>
													<br/><img src='img/user_icon.png' title='${row.lastPosterName} - Most Recent Poster' style='height: 40px; width: 40px; margin-top: 8px;'/>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${row.photoPresent == 'Y'}">						 
													<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.authorId}&backlink=ref' title='${row.authorName} - Original Poster, Most Recent Poster'>
								 						<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.authorId}' style='height: 40px; width: 40px;'/>
								 					</a>
												</c:when>
												<c:otherwise>
													<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='${row.authorName} - Original Poster, Most Recent Poster'' />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</div>
									
								<div class='details'>
									<div class='heading' style='word-wrap: normal;'>
										<c:if test="${row.closed}">
											<i class='fa fa-lock' style='margin-right: 8px; color: #A7A9AB;' title='This topic has now been closed for new replies'></i>
										</c:if>
										<c:if test="${row.sticky}">
											<i class='fa fa-thumb-tack' style='margin-right: 8px; color: #A7A9AB;' title='This topic is pinned; it will display at the top of community forum'></i>
										</c:if>
										<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/forum/forumThread.do?backlink=ref&amp;id=${row.id}' >${row.displaySubject}</a>
									</div>
									<div class='person'>
										<span title='First post: ${row.firstPostDate}'>${row.postedOn}</span><i class='fa fa-circle' style='margin: 0px 8px; font-size: 6px;'></i>
										<span title='Last post: ${row.lastPostDate}'>${row.lastPostedOn}</span><i class='a-icon-text-separator'></i> ${row.responseCount} replies
										<i class='a-icon-text-separator'></i> ${row.visitors} views
									</div>
									
									<p style=" font-size: 13px;">${row.displayBody}</p>
								</div>
							</div>
							
							<c:if test="${row.adsPosition}">
								<div class="inboxAds" style="margin-bottom: 0px;">
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

				<div class="inbox" style="display: inline-block;">
					<div class="eyebrow">
						<span onclick="return false" >Hot Topics</span>
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
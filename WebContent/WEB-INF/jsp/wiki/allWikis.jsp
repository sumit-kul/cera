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
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="All wiki articles posted at Jhapak" />
		<meta name="keywords" content="Jhapak, wiki, article, page, question, asked, all-wiki-article, article-list" />
		<title>Jhapak - All Wiki Articles</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		
		<script type="text/javascript" src="js/jquery.aniview.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/animateScroll.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="">
			#container .left-panel .image-section {
			    overflow: hidden;
			}
			
			#container .left-panel .commSection .communities .type form {
				float: right;
				clear: none;
			}
			
			#container .left-panel .image-section a img {
			    width: 100%;
			}
			
			@media only screen and (max-width: 480px) {
								
				.av-container .columns {
				    width: 95%;
				    margin-left: 10px;
				}
			}
			
			@media  only screen and (min-width: 481px) and (max-width: 766px) {
				.av-container .columns {
				    width: 48%;
				    margin-left: 1%;
				    margin-right: 1%;
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
			        		$.ajax({url:"${communityEraContext.contextUrl}/wiki/allWikis.ajx?jPage="+count+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									 var rowId = this['entryId'];
									 var trclass = '';
									 var sectrclass = '';
			   						 if(i%3 != 0){
			   							 trclass = 'Alt';
			   						 }
			   						if(i%2 == 1){
			   							sectrclass = 'SecAlt';
			   						 }

			   						var isAdsPosition = false;
			   						if(i%6 == 0){
			   							isAdsPosition = true;
			   						 }
								
									 sName += "<div class='aniview reallyslow columns panelBlk"+trclass+" SecAlt' av-animation='fadeInUp' id='viewSlide"+count+"'>";
									 sName += "<div class='panelBox' style='border-radius: 6px;'>";
									 sName += "<div class='image-section' style='border-radius: 6px;'>";
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/wiki/wikiDisplay.do?backlink=ref&amp;entryId="+rowId+"'>";
									 if(this['imageCount'] > 0){
										 sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&showType=t&type=WikiEntry&itemId="+rowId+"'/>";
									 } else {
										 if(this['sectionImageCount'] > 0){
											 sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&showType=t&type=WikiEntrySectionAll&itemId="+rowId+"'/>";
										 } else {
											 if(this['logoPresent'] == 'Y'){
												 sName += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['communityId']+"'/>";
											 } else {
												 sName += "<img src='img/community_Image.png'/>";
											 }
										 }
									 }
									 sName += "</a></div>";
									 sName += "<div class='footer'><div class='box-grid-author-meta'>";
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/wiki/wikiDisplay.do?backlink=ref&amp;entryId="+rowId+"' >"+this['title']+"</a>";
									 sName += "</div>";
									 sName += "<div class='cover-stat-fields-wrap'>";
									 sName += "<div class='cover-stat-wrap'>";
									 sName += "<span class='cover-stat'><i class='fa fa-retweet'></i> "+this['versionCount']+"</span>";
									 sName += "<span class='cover-stat'><i class='fa fa-eye' style='margin-left: 6px;'></i> "+this['visitors']+"</span>";
									 sName += "<span class='cover-stat'><i class='fa fa-thumbs-o-up'></i> "+this['likeCount']+"</span>";
									 sName += "</div></div></div>";

									 sName += "</div>";
									 sName += "</div>";

									 if(isAdsPosition) {
										 sName += '<div class="inboxAds">';
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
							    } ,
			                	complete: function () {
								     count++;
								     var options = {
											    animateThreshold: 100,
											    scrollPollInterval: 20
											}
											$('viewSlide'+count).AniView(options);

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
			
			var options = {
				    animateThreshold: 100,
				    scrollPollInterval: 50
				}
				$('.aniview').AniView(options);
			normalQtip();
			$.ajax({url:"${communityEraContext.contextUrl}/common/allToolsCloud.ajx?parentType=WikiEntry",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				var sortOption = $("#sortByOption").val();
				 $.each(result.aData, function() {
					 var count = 'article';
					 if (this['count'] > 1) {
						 count = 'articles';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/wiki/allWikis.do?filterTag="+this['tagText']+"&#39;);'";
					 aName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' id='"+this['tagText']+"'>"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 $("#cloud").html(aName+"</ul>");
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 var count = 'wiki entry';
					 if (this['count'] > 1) {
						 count = 'wiki entries';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span class='size-"+this['cloudSet']+"' ><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/wiki/allWikis.do?filterTag="+this['tagText']+"&#39;);' ";
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
		    
			$.ajax({url:"${communityEraContext.contextUrl}/blog/blogPannel.ajx?type=top",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='30' height='30' style='padding: 3px; float: left;' ";
					temp += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'/>";
					temp += "<div class='pannelTitle' style='width: 85%; word-wrap: normal;'>";
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
				<div class="commSection">
					<div class="communities" style="margin-bottom: 12px;">
						<div class="headerIcon">
							<i class="fa fa-book" ></i>
						</div>
						<div class="type">
							<h4>Wiki Entries</h4>
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Sort By:" cssClass="almostshortwidth">
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
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
					
					<div class="inboxAds">
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
					
					<div class="rowLine" id="rowSection">
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
							
			      			<div class="aniview reallyslow columns panelBlk${trclass} ${sectrclass}" av-animation="fadeInUp" >
			      				<div class="panelBox" style="border-radius: 6px;">
			      					<div class="image-section" style="border-radius: 6px;">
			      						<a href="${communityEraContext.contextUrl}/cid/${row.communityId}/wiki/wikiDisplay.do?backlink=ref&amp;entryId=${row.entryId}" >
			      							<c:if test="${row.imageCount > 0}">
			      								<img src="${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=WikiEntry&itemId=${row.entryId}" />
			      							</c:if>
			      							<c:if test="${row.imageCount == 0}">
			      								<c:if test="${row.sectionImageCount > 0}">
				      								<img src="${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=WikiEntrySectionAll&itemId=${row.entryId}" />
				      							</c:if>
				      							<c:if test="${row.sectionImageCount == 0}">
				      								<c:choose>
														<c:when test="${row.logoPresent == 'Y'}">						 
															 <img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId=${row.communityId}'/>
														</c:when>
														<c:otherwise>
															<img src='img/community_Image.png'/>
														</c:otherwise>
													</c:choose>
				      							</c:if>
			      							</c:if>
										</a>
			      					</div>
			      					<div class='footer'>
										<div class='box-grid-author-meta'>
											<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/wiki/wikiDisplay.do?backlink=ref&amp;entryId=${row.entryId}' >${row.title}</a>
										</div>
										<div class="cover-stat-fields-wrap">
											<div class="cover-stat-wrap">
											<span class="cover-stat"><i class="fa fa-retweet"></i> ${row.versionCount}</span>
											<span class="cover-stat"><i class='fa fa-eye' style='margin-left: 6px;'></i> ${row.visitors}</span>
											<span class="cover-stat"><i class="fa fa-thumbs-o-up"></i> ${row.likeCount}</span>
											</div>
										</div>
									</div>
			      				</div>
			      			</div>
			      			
			      			<c:if test="${row.adsPosition}">
								<div class="inboxAds">
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
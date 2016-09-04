<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex, follow" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="All blog entries posted at Jhapak. Blogs in Jhapak are for easily sharing your thoughts with the world. Blog feature in Jhapak makes it simple to post text, photos and video onto your personal or team blog" />
		<meta name="keywords" content="Jhapak, blog, all blogs, blog list, free blog, community blog, personal blog, weblog, create blog, new blog" />
		<title>Jhapak - All Blogs</title>	
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="text/css">
			#container .left-panel .commSection .communities .type form {
			    float: right;
			    clear: none;
			}
			
			.entryRow {
				display: flex;
				flex-wrap: wrap;
				justify-content: space-between;
				box-sizing: border-box;
				margin-top: 16px;
			}
			
			.entryRow .articleLarge {
				display: block;
				min-width: 0px;
				flex-basis: calc(50% - 10px);
				width: 100%;
				box-shadow: 0 1px 6px 0 rgba(17, 17, 17, .37);
				margin-bottom: 30px;
				background: #fff;
				position: relative;
				box-sizing: border-box;
			}
			
			.entryRow .articleLarge .header {
				/* margin-bottom: 40px !important;
				margin-top: 20px !important; */
			}
			
			.entryRow .articleLarge .header .headerBar {
				position: relative;
				width: 100%;
				margin: 0px;
				height: 224px;
				overflow: hidden;
				display: inline-block;
			}
			
			#container .left-panel p a {
			    color: #464646;
			    text-decoration: none;
			    margin-right: 10px;
			    font-size: 1.4em;
				line-height: 1.286em;
				font-family: "Roboto", Arial, Helvetica, sans-serif;
				font-weight: 500;
				letter-spacing: -1px;
				text-align: center;
			}
			.entryRow .articleLarge .header .headerBar a img {
				display: inline-block;
				min-height: 100%;
				position: absolute;
				top: 0px;
				bottom: 0px;
				left: 0px;
				right: 0px;
				margin: auto;
			}
			
			.entryRow .articleLarge .header a {
				color: #3594BA;
				outline: medium none;
				text-decoration: none;
				cursor: pointer;
			}
			
			.entryRow .articleLarge .header .author {
				position: relative !important;
			}
			
			.entryRow .articleLarge .header .author .pullLeft {
			    /* margin-right: 20px; */
			    float: left !important;
			}
			
			#container .left-panel .entryRow .articleLarge .header p.title {
				color: #66799F;
				line-height: 1.2em;
				text-rendering: optimizelegibility;
				font-size: 17px;
				margin: 6px 0px;
				border-left: 5px solid #3594BA;
				padding: 0px 2.85437% 0px 2.04531%;
			}
			
			#container .left-panel .entryRow .articleLarge .header p {
				margin: 0px 6px;
				font-size: 1em;
				line-height: 1.7em;
				margin-bottom: 100px;
			}
			
			.author .pullLeft .avatar {
				margin: -36px 10px 0px 6px;
				overflow: hidden;
				background-color: #F0F7F7;
				border-radius: 50%;
				width: 64px;
				height: 64px;
			}
			
			.author .pullLeft .avatar .avatarInner {
				position: relative;
				top: 50%;
				transform: translate(0px, -50%);
				box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.34), 0px 0px 15px rgba(0, 0, 0, 0.3) inset;
			}
			
			.author .pullLeft .avatar .avatarInner a {
				background-color: transparent;
			}
			
			.author .pullLeft .avatar .avatarInner a img {
				border-radius: 0px;
				display: block;
				margin: 0px auto;
				border: 0px none;
				box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.34), 0px 0px 15px rgba(0, 0, 0, 0.3) inset;
			}
			
			.author .authorBody {
				line-height: 30px;
			}
			
			.header .postBody {
				display: inline-block;
				width: 100%;
				word-wrap: break-word;
			}
			
			.bfooter {
				position: absolute;
				bottom: 50px;
				left: 0;
				width: 100%;
				padding-right: 11px;
				text-align: center;
			}
			
			.bfooter a {
				font-family: "Roboto Slab", Arial, Helvetica, sans-serif;
				font-weight: bold;
				text-transform: uppercase;
			}
			
			.author .authorBody a.authorName {
				color: #546E86;
				font-family: "Lato","Helvetica Neue",Helvetica,Arial,sans-serif;
				font-weight: bold;
				font-size: 14px;
			}
			
			.author .authorBody .postTime {
				font-size: 12px;
				margin-right: 6px;
				float: right !important;
			}
			
			.entryRow .footer .cover-stat-wrap {
			    background-color: #F6F6F6;
			    border-top: 1px solid #E7E7E7;
			    padding: 7px 0;
			    position: relative;
				width: 100%;
			}
			
			.entryRow .footer .cover-stat-wrap .cover-stat {
			    color: #99A2A3;
			    margin-left: 10%;
				margin-right: 10%;
			}
			
			#container .left-panel .header .postBody p a.morePost {
				color: #3594BA !important;
			}
			
			#container .right-panel .pannel .pannelHeadder:not(:last-child) {
				display: inline-block; 
				width: 100%; 
				margin-bottom: 20px;
			}
			
			@media only screen and (max-width: 480px) {
				.entryRow {
    				display: block;
    			}
    			
    			.entryRow .articleLarge {
    				width: 95%;
    				margin: 10px;
    			}
    			
    			.entryRow .footer {
    				bottom: 0px;
    			}
			}
			
			@media only screen and (min-width: 481px) {
				.entryRow .footer {
				    position: absolute;
					bottom: 0px;
					left: 0;
					width: 100%;
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
							$.ajax({url:"${communityEraContext.contextUrl}/blog/allBlogs.ajx?jPage="+count+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									 var rowId = this['id'];
									 var trclass = '';
									 var isAdsPosition = false;
			   						 if(this['oddRow']){
			   							 trclass = 'Alt';
			   						 }
			   						if(i%6 == 0){
			   							isAdsPosition = true;
			   						 }
			   						 sName += "<div class='articleLarge "+trclass+"' ><div class='header' style='min-height: 100%; width: 100%;'><div class='headerBar' >";
			   						if(this['commId'] > 0){
			   							sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['commId']+"/blog/blogEntry.do?id="+rowId+"'>";
			   						} else {
			   							sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"'>";
			   						}
									 
									 if(this['imageCount'] > 0){
										 sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId="+rowId+"' />";
									 } else {
										 sName += "<img src='img/blog-background.png'/>";
									 }
									 sName += "</a>";
									 sName += "</div><div class='author' ><div class='pullLeft' ><div class='avatar' ><div class='avatarInner' >";
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"' title='"+this['displayName']+"'>";
									 if(this['photoPresent'] == 'Y'){
										 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' />";
									 } else {
										 sName += "<img src='img/user_icon.png'/>";
									 }
									 sName += "</a></div></div></div>";
									 sName += "<div class='authorBody' >";
									 sName += "By: <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"' class='memberInfo authorName' ";
									 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayShortName']+"</a>";
									 sName += "<span class='postTime'>"+this['postedOn']+"</span></div></div>";
									 sName += "<div class='postBody' >";
									 sName += "<p class='title'>";
									 if(this['commId'] > 0){
										 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['commId']+"/blog/blogEntry.do?id="+rowId+"'>"+this['title']+"</a>";
				   						} else {
				   							sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"'>"+this['title']+"</a>";
				   						}
									 
									 sName += "</p><p class='body' >"+this['displayBody']+"</p></div>";
									 sName += " <div class='bfooter' >";
									 if(this['commId'] > 0){
										 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['commId']+"/blog/blogEntry.do?id="+rowId+"' class='morePost'>- CONTINUE READING -</a>";
				   						} else {
				   							sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"' class='morePost'>- CONTINUE READING -</a>";
				   						}
									
									 sName += " </div></div>";
									 sName += "<div class='footer'><div class='cover-stat-fields-wrap'><div class='cover-stat-wrap'>";
									 sName += "<span class='cover-stat'><i class='fa fa-comment' style='margin-left: 6px;'></i> "+this['commentCount']+"</span>";
									 sName += "<span class='cover-stat'><i class='fa fa-eye' style='margin-left: 6px;'></i> "+this['visitors']+"</span>";
									 sName += "<span class='cover-stat'><i class='fa fa-thumbs-up' style='margin-left: 6px;'></i> "+this['likeCount']+"</span>";
									 sName += "</div></div></div></div>";

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

								memberInfoQtip();
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
		
			$.ajax({url:"${communityEraContext.contextUrl}/common/allToolsCloud.ajx?parentType=Consolidator&fTagList="+$("#fTagList").val(),dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				var sortOption = $("#sortByOption").val();
				 $.each(result.aData, function() {
					 var count = 'blog entry';
					 if (this['count'] > 1) {
						 count = 'blog entries';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/blog/allBlogs.do?filterTag="+this['tagText']+"&#39;);'";
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
					 var count = 'blog entry';
					 if (this['count'] > 1) {
						 count = 'blog entries';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/blog/allBlogs.do?filterTag="+this['tagText']+"&#39;);' ";
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

			$.ajax({url:"${communityEraContext.contextUrl}/blog/blogPannel.ajx?type=top",dataType: "json",success:function(result){
				var temp = "<div class='pannel' >";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder'>";
					temp += "<div style='display: inline-block; width: 100%; overflow: hidden; height: 160px;'>";
					if(this['imageCount'] > 0){
						temp += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId="+this['entryId']+"' style='float: left; width: 100%; top: 0; right: 0; bottom: 0; left:0;' />";
					} else {
						temp += "<img src='img/blog-background.png' style='float: left; width: 100%; top: 0; right: 0; bottom: 0; left:0;' />";
					}
					temp += "</div><div class='pannelTitle' style='width: 100%; word-wrap: normal;'>";
					 if(this['communityId'] > 0){
						 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/blog/blogEntry.do?id="+this['entryId']+"' class='normalTip' title='"+this['title']+"' >"+this['editedTitle']+"</a>";
					 } else {
						 temp += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+this['entryId']+"' class='normalTip' title='"+this['title']+"' >"+this['editedTitle']+"</a>";
					 }
					 temp += "<p>";
					 temp += "<span style='margin-right: 10%; margin-left: 10%;'><i class='fa fa-comment' style='margin-left: 6px;'></i> "+this['commentCount']+"</span>";
					 temp += "<span style='margin-right: 10%; margin-left: 10%;'><i class='fa fa-eye' style='margin-left: 6px;'></i> "+this['visitors']+"</span>";
					 temp += "<span style='margin-right: 10%; margin-left: 10%;'><i class='fa fa-thumbs-up' style='margin-left: 6px;'></i> "+this['likeCount']+"</span>";
					 temp +="</p>";
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
				<div class="commSection" >
					<div class="communities">
						<div class="headerIcon">
							<i class="fa fa-quote-left" ></i>
						</div>
						<div class="type">
							<h4 style="float: left;">Blogs</h4>
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Sort By:" >
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="hidden" name="page" value="${command.page}" />
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
					<div class="entryRow" id="rowSection">
						<div class="inboxAds" style="margin-top: 0px;">
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
			      			<div class="articleLarge ${trclass}" >
			      				<div class="header" style="min-height: 100%; width: 100%;">
			      					<div class="headerBar" >
			      						<c:choose>
											<c:when test="${row.commId > 0}">
												<a href="${communityEraContext.contextUrl}/cid/${row.commId}/blog/blogEntry.do?id=${row.id}"> 
											</c:when>
											<c:otherwise>
												<a href="${communityEraContext.contextUrl}/blog/blogEntry.do?id=${row.id}" >
											</c:otherwise>
										</c:choose>
				      					
			      						<c:choose>
											<c:when test="${row.imageCount > 0}">
												<img src="${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=BlogEntry&itemId=${row.id}" />
											</c:when>
											<c:otherwise>
												<img src='img/blog-background.png'/>
											</c:otherwise>
										</c:choose>
				      					</a>
			      					</div>
			      					<div class="author" >
			      						<div class="pullLeft" >
			      							<div class="avatar" >
			      								<div class="avatarInner" >
			      									<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}' 
														title='${row.displayName}'>
														<c:choose>
															<c:when test="${row.photoPresent == 'Y'}">
																<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img" var="photoUrl">
																	<c:param name="id" value="${row.posterId}" />
																</c:url>
																<img src="${photoUrl}" /> 
															</c:when>
															<c:otherwise>
																<img src='img/user_icon.png'/>
															</c:otherwise>
														</c:choose>
													</a>
			      								</div>
			      							</div>
			      						</div>
			      						<div class="authorBody" >
			      							By: <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}' class='memberInfo authorName' 
														title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${row.posterId}'>${row.displayShortName}</a>
														<span class='postTime'>${row.postedOn}</span>
			      						</div>
			      					</div>
			      					<div class="postBody" >
			      						<p class='title'>
				      						<c:choose>
												<c:when test="${row.commId > 0}">
													<a href="${communityEraContext.contextUrl}/cid/${row.commId}/blog/blogEntry.do?id=${row.id}">${row.title}</a>
												</c:when>
												<c:otherwise>
													<a href="${communityEraContext.contextUrl}/blog/blogEntry.do?id=${row.id}" >${row.title}</a>
												</c:otherwise>
											</c:choose>
			      						</p>
		      							<p class="body" >${row.displayBody}</p>
		      						</div>
		      						<div class="bfooter" >
		      							<c:choose>
											<c:when test="${row.commId > 0}">
												<a href="${communityEraContext.contextUrl}/cid/${row.commId}/blog/blogEntry.do?id=${row.id}" class='morePost'>- CONTINUE READING -</a> 
											</c:when>
											<c:otherwise>
												<a href="${communityEraContext.contextUrl}/blog/blogEntry.do?id=${row.id}" class='morePost'>- CONTINUE READING -</a>
											</c:otherwise>
										</c:choose>
		      						</div>
			      				</div>
			      				<div class='footer'>
									<div class="cover-stat-fields-wrap">
										<div class="cover-stat-wrap">
											<span class="cover-stat"><i class='fa fa-comment' style='margin-left: 6px;'></i> ${row.commentCount}</span>
											<span class="cover-stat"><i class='fa fa-eye' style='margin-left: 6px;'></i> ${row.visitors}</span>
											<span class="cover-stat"><i class='fa fa-thumbs-up' style='margin-left: 6px;'></i> ${row.likeCount}</span>
										</div>
									</div>
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
				<c:if test="${communityEraContext.userAuthenticated}">
					<a href="${communityEraContext.contextUrl}/pers/startBlog.do" class="btnmain normalTip" style="width: 100%; margin-bottom: 10px;" title="Start a personal blog">
					<i class="fa fa-quote-left" style="margin-right: 4px;"></i>Start a Blog</a>
				</c:if>
				
				<div class="inboxAds" style="display: inline-block; width: 100%;">
					<c:if test="${communityEraContext.production}">
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
					</c:if>
				</div> 
				
				<div class="inbox" style="display: inline-table;">
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
				
				<%-- <div class="inboxAds" style="display: inline-block; width: 100%;">
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
				</div>  --%>
				
				<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
			</div>
			<a href="#0" class="cd-top">Top</a>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
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
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - Search</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.searchIndex {
				font-size: 11px;
			    padding: 0px 0px 0px 3px;
			    color: #898F9C;
			    clear: both;
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function () {
				$.ajax({url:"${communityEraContext.contextUrl}/community/communityPannel.ajx?type=top",dataType: "json",success:function(result){
					var temp = "<div class='pannel'>";
					$.each(result.aData, function() {
						temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
						if(this['logoPresent']){
							temp += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['entryId']+"' width='30' height='30' style='padding: 3px 6px 0px 3px; float: left;' />";
						} else {
							temp += "<img src='img/community_Image.png' width='30' height='30' style='padding: 3px; float: left;' />";
						}
						temp += "<div class='pannelTitle' style='width: 86%; word-wrap: normal;'>";
							 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['entryId']+"/home.do' class='normalTip' title='"+this['name']+"' >"+this['editedName']+"</a>";
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

				$.ajax({url:"${communityEraContext.contextUrl}/community/communityPannel.ajx?type=latest",dataType: "json",success:function(result){
					var temp = "<div class='pannel'>";
					$.each(result.aData, function() {
						temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
						if(this['logoPresent']){
							temp += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['entryId']+"' width='30' height='30' style='padding: 3px 6px 0px 3px; float: left;' />";
						} else {
							temp += "<img src='img/community_Image.png' width='30' height='30' style='padding: 3px 6px 0px 3px; float: left;' />";
						}
						temp += "<div class='pannelTitle' style='width: 86%; word-wrap: normal;'>";
							 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['entryId']+"/home.do' class='normalTip' title='"+this['name']+"' >"+this['editedName']+"</a>";
						 temp += "</div></div>";
						});
					temp += "</div>";
					$("#latestPostsList").html(temp);
			        $("#waitLtPosts").hide();
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
			             $("#waitLtPosts").show();
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
						<div class="menus">
							<script>
								  (function() {
								    var cx = '000937791762657326997:duztx-oomg0';
								    var gcse = document.createElement('script');
								    gcse.type = 'text/javascript';
								    gcse.async = true;
								    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
							        '//cse.google.com/cse.js?cx=' + cx;
								    var s = document.getElementsByTagName('script')[0];
								    s.parentNode.insertBefore(gcse, s);
								  })();
								</script>
								<gcse:searchresults-only></gcse:searchresults-only>
						</div>
					</div>
					
					
				</div>
			</div> 	
			
			<div class="right-panel" style="margin-top: 0px;">
				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
					<div class="eyebrow">
						<span onclick="return false" ><i class="fa fa-users" style="margin-right: 8px;"></i>Latest Communities</span>
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
				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
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
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>	
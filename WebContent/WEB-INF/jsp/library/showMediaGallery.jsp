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
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Jhapak - ${communityEraContext.currentCommunity.name}' media gallery</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<!-- Twitter Card data -->
		<meta name="twitter:card" content="summary">
		<meta name="twitter:site" content="@EraCommunity">
		<meta name="twitter:title" content="${communityEraContext.currentCommunity.name}">
		<meta name="twitter:description" content="${communityEraContext.currentCommunity.welcomeText}">
		<meta name="twitter:url" content="${communityEraContext.requestUrl}">
		
		<!-- Open Graph data -->
		<meta property="og:title" content="${communityEraContext.currentCommunity.name}" />
		<meta property="og:type" content="article" />
		<meta property="og:url" content="${communityEraContext.requestUrl}" />
		<meta property="og:description" content="${communityEraContext.currentCommunity.welcomeText}" />
		<meta property="og:site_name" content="Jhapak" />

		<meta property="fb:admins" content="100001187666357" /> 
		
		  <meta property="og:title" content="${communityEraContext.currentCommunity.name}" />
		  <meta property="og:type" content="Sharing Widgets" />
		  <meta property="og:url" content="${communityEraContext.requestUrl}" />
		  <c:choose>
				<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
					<meta property="og:image" content="${communityEraContext.contextUrl}/commLogoDisplay.img?communityId=${communityEraContext.currentCommunity.id}" />
				</c:when>
				<c:otherwise>
					<meta property="og:image" content="${communityEraContext.contextUrl}/img/community_Image.png" />
				</c:otherwise>
			</c:choose>
		  <meta property="og:description" content="${communityEraContext.currentCommunity.welcomeText}" />
		  <meta property="og:site_name" content="Jhapak" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/dropzone.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/media.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/captionMedia.css" />
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/dropzone.js"></script>
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		
		<script src='js/jquery.swipebox.js'></script>
		<link rel='stylesheet' href='css/swipebox.css' type='text/css' />
		
		<script src='js/jquery.masonry.min.js'></script>
		<link rel='stylesheet' media='screen' type='text/css' href='css/masonryLikeImage.css' />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			.commBanr .nav-list ul li.selected {
			    width: 160px;
			}
			
			.qtip {
			    max-height: unset;
			    max-width: 380px;
			}
			.swipebox-media {
				max-height: 100%;
				max-width: 100%;
				min-height: 402px;
				margin: 0px;
				width: 100%;
				height: 100%;
				display: inline-block;
				position: relative;
				vertical-align: middle;
				top: 0px;
			}
			
 			.modal-dialog {
			    width: 620px;
			    margin: 60px auto;
			    height: 300px;
			}
			
 			.modal-body {
			    padding: 0px;
			 }
			 
			 .dropzone {
			    border: 0;
			    background: transparent repeating-linear-gradient(45deg, #F6F6F6, #F6F6F6 10px, #F8F9F9 10px, #F8F9F9 20px) repeat scroll 0% 0%;
			    padding: 0px;
			    height: 256px;
			}
			
			.scroll-pane
			{
				width: 100%;
				height: 310px;
				overflow: auto;
			}
			
			.scroll-pane2
			{
				width: 100%;
				height: 258px;
				overflow: auto;
			}
			
			.scroll-pane3
			{
				width: 100%;
				height: 90px;
				overflow: auto;
			}
			
			.scroll-pane4
			{
				width: 100%;
				min-height: 100px;
				overflow: auto;
				height: 550px;
			}
			
			.scroll-pane5
			{
				width: 334px;
				height: auto;
				overflow: auto;
				max-height: 80px;
			}
			
			.scroll-pane6
			{
				width: 100%;
				height: auto;
				overflow: auto;
				min-height: 80px;
			}
						
			.jspCap
			{
				display: block;
				background: #eeeef4;
			}
			
			.jspVerticalBar {
			    width: 8px;
			}
			
			.jspHorizontalBar {
			    height: 8px;
			}
			
			.jspVerticalBar .jspCap
			{
				height: 1px;
			}
			
			#mainSection {
				margin-top: 0px;
			}
			
			.dropzone .dz-preview .dz-image {
			    border-radius: 10px;
			}
			
			.dropzone .dz-preview {
			    margin: 15px;
			}
			
			.dropzone .dz-message .note {
			    font-size: 0.8em;
			    font-weight: 200;
			    display: block;
			    margin-top: 1.4rem;
			}
			
			.dropzone .dz-preview .dz-details .dz-filename span,
			.dropzone .dz-preview .dz-details .dz-size span {
				max-width: 60px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: normal;
			}
			
			.dropzone .dz-preview .dz-details .dz-filename:hover span {
				max-width: 120px;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			#upperSectionAlbum {
				height: 150px;
				border-bottom: medium dotted #555;
				padding: 14px 0px 14px 14px;
				width: 98%;
			}
			
			#lowerSectionAlbum {
				width: 100%;
				float: left;
				height: 240px;
			}
			
			#upperSectionAlbum .createAlbumForm {
				height:150px;
				width: 50%;
				float: left;
			}
			
			#upperSectionAlbum2 {
				padding: 14px 0px 14px 14px;
				width: 98%;
				height: 200px;
			}
			
			#upperSectionAlbum span, #upperSectionAlbum2 span {
				word-wrap: break-word;
				font-weight: bold;
				font-size: 12px;
				color: #AAA6A6;
				float: left;
			}
			
			#upperSectionAlbum span.top, #upperSectionAlbum2 span.top {
				float: right;
				margin: 0pc 30px 16px 0px;
			}
			
			#upperSectionAlbum span a, #upperSectionAlbum span a {
				margin: -9px 0px 0px 94px;
				float: right;
				color: #4E5665;
				text-shadow: 0px 1px 0px #FFF;
				line-height: 20px;
				display: inline-block;
				height: 20px;
				padding: 0px 8px;
				background: none repeat-x scroll 0% 0% #F6F7F8;
				border: 1px solid;
				border-radius: 2px;
				box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.05);
				box-sizing: content-box;
				font-weight: bold;
				font-size: 12px;
				text-align: center;
				vertical-align: middle;
			}
			
			input[type="checkbox"] {
			    display:none;
			}
			
			input[type="checkbox"] + label span {
			    display:inline-block;
			    width:19px;
			    height:19px;
			    margin:-1px 4px 0 0;
			    vertical-align:middle;
			    background:url(img/check_radio_sheet.png) left top no-repeat;
			    cursor:pointer;
			}
			
			input[type="radio"] {
				opacity: 0;
			  	position: absolute;
			}
			
			input[type="radio"] + label span {
			    display:inline-block;
			    width:19px;
			    height:19px;
			    margin:-1px 4px 0 0;
			    vertical-align:middle;
			    background:url(img/check_radio_sheet.png) -38px top no-repeat;
			    cursor:pointer;
			}
			input[type="radio"]:checked + label span {
			    background:url(img/check_radio_sheet.png) -56.5px top no-repeat;
			}
			
			label {
				display: inline-block;
				overflow: hidden;
				max-width: 135px;
				margin: 0px 0px 1px 1px;
				vertical-align: middle;
				white-space: nowrap;
				text-overflow: ellipsis;
				font-size: 11px;
			}
			
			input[type="checkbox"]:checked + label span {
			    background:url(img/check_radio_sheet.png) -19px top no-repeat;
			}
			
			#upperSectionAlbum input[type="text"], #upperSectionAlbum2 input[type="text"] {
				width: 280px;
				margin-bottom: 10px;
				padding: 3px;
				cursor: default;
				background-color: transparent;
				border: 1px solid #DADEE2;
				font-size: 13px;
				padding: 5px;
				color: #20394D;
				outline: 0px none;
				font-size: 13px;
				padding: 5px;
				color: #20394D;
			}
			
			#upperSectionAlbum input.searchResult, #upperSectionAlbum2 input.searchResult {
				width: 272px;
				margin-bottom: 2px;
			}
			
			#upperSectionAlbum .createAlbumForm textarea, #upperSectionAlbum2 .createAlbumForm2 textarea {
				width: 280px;
				height: 106px;
				line-height: 1.28;
				font-size: 12px;
				border: 1px solid rgb(215, 215, 215);
				word-wrap: break-word;
				padding: 3px;
				text-decoration: none;
				resize: none;
				overflow: hidden;
				text-align: left;
			}
			
			.addContr {
				width: 280px;
				border: 1px solid rgb(210, 210, 210); 
				height:90px; 
				margin-top: 6px;
				float: left;
				padding-left:3px;
			}
			
			.addContr img{
				padding: 2px 2px 2px 2px;
			}
			
			#mainSectionAlbum {
				padding-bottom: 20px;
				height: 200px;
				margin-top: 19px;
			}
			
			#createAlbum {
				height: 440px;
			}
			
			
			.myCustomClass31 li.srchRslt {
				padding: 2px;
				font-size: 12px;;
			}
			
			.myCustomClass31 li.srchRslt:hover {
				padding: 2px;
				background: none repeat scroll 0% 0% #F7F7F7;
			}
			
			.myCustomClass31 {
			    width: 270px;
			    left: 386px;
			    border-radius: 6px;
			    margin: 1px 0px 10px 8px;
				box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
				white-space: nowrap;
				text-align: left;
				background-color: #F0EDED;
				border: 2px solid #BBB;
				color: #524D4D;
				line-height: 2em;
				list-style: none outside none;
				padding: 4px;
			}
			
			.myCustomClass31 li {
				cursor: pointer;
				padding: 2px 4px;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			.myCustomClass31 li:hover {
				color: #66799f;
				text-decoration: none;
				background-color: #243F52;
			}
						
			.jg-entry {
				-webkit-box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.75);
				-moz-box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.75);
				box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.75);
			}
			
			.dynaDropDown {
				font-size: 13px;
				font-weight: bold;
				color: white;
			}
			
			#innerSection {
			    overflow: hidden;
			    padding: 3px 2px 14px 4px;
			}
			
			.row {
				margin: 0px;
			}		
				
			.captionBottom2 {
				display: none;
				border-radius: 2px;
				background-color: #000;
				top: 2px;
				margin: 0px;
				width: 40px;
				height: 40px;
				opacity: 0.7;
				padding: 0px;
				cursor: pointer;
				left: 2px;
			}
			
			.picture:hover .captionBottom2 {
				display: inline-block;
			}
			
			#upperSectionAlbum2 .captionBottom2 span {
				font-size: 28px;
				font-weight: bold;
				color: #66799f;
				padding: 4px 10px;
			}
			
			.columnLeft {
				width: 48%;
				float: left;
				font-size: 12px;
				word-wrap: break-word;
			}
			
			.columnRight {
				width: 48%;
				float: right;
				font-size: 12px;
				word-wrap: break-word;
			}
 		</style>
		
		<script type="text/javascript">
			$(document).ready(function () {
				Dropzone.autoDiscover = false;
				normalQtip();
				dynamicDropDownQtip();
				$('.demo-gallery').each(function (i, el) {
					$(el).justifiedGallery({
						lastRow : 'nojustify', 
						rowHeight : 200, 
						fixedHeight : true, 
						captions : true, 
						margins : 14,
						sizeRangeSuffixes: {
							'lt100':'_t', 
							'lt240':'_m', 
							'lt320':'_n', 
							'lt500':'', 
							'lt640':'_z', 
							'lt1024':'_b'
						},
						rel: 'gal' + i
						});
					});
				});
		</script>
		
		<script type="text/javascript">
			function showCommunityPhotos(commId) {
				document.getElementById('addPhotos').style.display = 'inline';
				document.getElementById('communityAlbums').className = "";
	    		document.getElementById('communityPhotos').className = "selected";
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaList.ajx?communityId="+commId ,dataType: "json",success:function(result){
					document.getElementById('pgCount').value = result.pgCount;
					count = 2;
					var sName = "<div class='section section--head'><div class='row'><div id='demo-test-gallery' class='demo-gallery'>";
					$.each(result.aData, function() {
						sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' id='image_"+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
						 sName += "data-download-url='${communityEraContext.currentCommunityUrl}/library/downloadFile.do?id="+this['id']+"'  data-type='library'";
						 sName += "data-url='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?id="+this['id']+"' class='swipebox'>";
						 sName += "<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
						 sName += "</a>";
					 });
					sName += "</div></div></div>";
					$('#innerSection').html(sName);

					$( '.swipebox' ).swipebox();
					
					$('.demo-gallery').each(function (i, el) {
						$(el).justifiedGallery({lastRow : 'nojustify', 
							rowHeight : 200, 
							fixedHeight : true, 
							captions : true, 
							margins : 14,
							sizeRangeSuffixes: {
								'lt100':'_t', 
								'lt240':'_m', 
								'lt320':'_n', 
								'lt500':'', 
								'lt640':'_z', 
								'lt1024':'_b'
							},
							rel: 'gal' + i}).on('jg.complete', function () {
						});
					});
					
					}
				});
			}
		</script>
		
		<script type="text/javascript">
	 			var titems = 0;
				function likeMedia(mediaId, commentId) 
				{ 
					if (commentId > 0) {
				    	$.ajax({url:"pers/likeMedia.ajx?mediaId="+mediaId+"&commentId="+commentId,success:function(result){
				    	    $("#divComm-"+commentId).html(result);
				    	  }});
				    } else {
				    	$.ajax({url:"pers/likeMedia.ajx?mediaId="+mediaId+"&commentId=0",success:function(result){
				    	    $("#divMed-"+mediaId).html(result);
				    	  }});
				    }
				}

				function runScript(e, itemId, commCount, tf) {
	 			    if ((e.which == 13 || e.keyCode == 13) && !e.shiftKey) {
	 			    	var content = tf.value;
	 			    	var libraryId = document.getElementById('libraryId').value;
	 			    	content = content.replace(new RegExp('\r?\n','g'), '<br />');
	 			    	$.ajax({url:"pers/postMediaComment.ajx?mediaId="+itemId+"&libraryId="+libraryId+"&comment="+content ,dataType: "json", success:function(result){
		 			    	var ncom = "";
		 			    	ncom += "<div class='showComment'><img src='pers/userPhoto.img?id="+result.posterId+"'/>";
		 			    	ncom += "<div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+result.posterId+"&backlink=ref' class='memberInfo' ";
		 			    	ncom += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+result.posterId+"'>"+result.posterName+"</a>"+result.datePosted+"</div>";
	
		 			    	ncom += "<div class='incomm'>"+result.comment+"</div>"; //showComment
		 			    	ncom += "<div class='heading'><span class='rlike' id='divComm-"+result.photoId+"'>";
		 			    	ncom += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+itemId+"&#39;, &#39;"+result.photoId+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' >Like</a></span></div>";
		 			    	ncom += "</span></div></div>";
	   						
		 			    	$( ncom ).prependTo( "#cmntInput");
	 			    	}
	 			    	});
	 			    	$( '#itmCmt'+itemId ).val('');
	 			    	if (commCount == 0) {
	 			    		$( '#theaderComm' ).html('<p class="theader">Comments</p>');
						}
	 			    	$('.scroll-pane4').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
	 			        return true;
	 			    }
	 			    return true;
	 			}

			    var count = 2;
				function infinite_scrolling(){
					if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
				    	var total = document.getElementById('pgCount').value;
				    	var folderId = document.getElementById('currentAlbum').value;
		                	if (count > total){
		                		return false;
				        	} else {
				            	$.ajax({
				                	url:"${communityEraContext.currentCommunityUrl}/library/mediaGallery.ajx?folderId="+folderId+"&jPage="+count,
				                	dataType: "json",
				                	success:function(result){ 
				    					$.each(result.aData, function() {
				    						 var sName = "";
				    						 sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' id='image_"+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
				    						 sName += "data-download-url='${communityEraContext.currentCommunityUrl}/library/downloadFile.do?id="+this['id']+"'  data-type='library'";
				    						 sName += "data-url='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?id="+this['id']+"' class='swipebox'>";
				    						 sName += "<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
				    						 sName += "</a>";
				    						 $('#demo-test-gallery').append(sName);
				    					 });

				    					$( '.swipebox' ).swipebox();
				    					
				    					$('.demo-gallery').justifiedGallery('norewind');
				                	} ,
				                	complete: function () {
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
			        infinite_scroll_debouncer(infinite_scrolling, 400);
			    });
		
			function showAlbums() {
				$('#innerSection').html("");
				document.getElementById('addPhotos').style.display = 'none';
				document.getElementById('communityAlbums').className = "selected";
	       		document.getElementById('communityPhotos').className = "";
	       		var libraryId = document.getElementById('libraryId').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMedia.ajx?libraryId="+libraryId+"&type=album" ,dataType: "json",success:function(result){
					var sName = "<div class='main_container'>";
					$.each(result.aData, function() {
						sName += "<div class='pin'><div class='holder'><div class='actions' pin_id='"+this['id']+"'></div>";
						sName += "<a class='image ajax' pin_id='"+this['id']+"' href='javascript:void(0);' onclick='showAlbumInfo(&#39;"+this['id']+"&#39;)'>";
						if(this['photoCount'] > 0) {
							sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?mediaId="+this['coverPhotoId']+"' title='"+this['title']+"'/>";
						} else {
							sName += "<div style='width : auto; height: auto;'><p style='padding: 50px; font-size: 16px; color: #66799f; width: auto;'>No Photo Added<p></div>";
						}
						sName += "</a></div>";
						sName += "<p class='desc'>"+this['title']+"</p><p class='info'><span>"+this['photoCount']+" photo</span></p>";
						sName += "</div>";
					 });
					sName += "</div>";
					$('#innerSection').html(sName);
	
					// masonry initialization
				    $('.main_container').masonry({
				        // options
				        itemSelector : '.pin',
				        isAnimated: true,
				        isFitWidth: true
				    });
					
					}
				});
			}
	
			function showAlbumInfo(groupMediaId) {
				document.getElementById('currentAlbum').value = groupMediaId;
				var currentCommunity = document.getElementById('currentCommunity').value;
				var libraryId = document.getElementById('libraryId').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupLibMediaInfo.ajx?groupMediaId="+groupMediaId+
					"&currentCommunity="+currentCommunity+"&type=album"+"&libraryId="+libraryId ,dataType: "json",success:function(result){
					prepareAlbum(result);
					}
				});
			}
			
			function editMediaContent(mediaId, title, desc){
				desc = unescape(desc);
				title = unescape(title);
				var sName = '<div class="editMediaForm"><input id="intTitle'+mediaId+'" class="edit" value="'+title+'" placeholder="Add a title" maxlength="50" autocomplete="off" type="text" />';
				sName += '<br/><textarea id="intDesc'+mediaId+'" placeholder="Add a description" cols="60" rows="10" class="edit">'+desc+'</textarea><div>';
				sName += "<div class='editMediaForm'>";
				sName += "<span class='secLev'><a onclick='saveMediaInfo(&#39;"+mediaId+"&#39;);' href='javascript:void(0);' title='Done editing' ><i class='fa fa-check'></i>Done</a></span>";
				sName += "<span class='secLev'><a onclick='cancelEditing(&#39;"+mediaId+"&#39;, &#39;"+title+"&#39;, &#39;"+desc+"&#39;);' href='javascript:void(0);' title='Cancel' ><i class='fa fa-times'></i>Cancel</a></span><div>";
				$( "#capTopSpan" ).html("");
				$( "#mediaInfo"+mediaId ).html(sName);
				$( ".edit" ).click(function() {
		        	this.focus();
		        	});
			}
	
			function saveMediaInfo(mediaId){
				var title = document.getElementById("intTitle"+mediaId).value;
				var desc = document.getElementById("intDesc"+mediaId).value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateMediaInfo.ajx?docId="+mediaId+"&title="+title+"&description="+desc 
					,success:function(result){
				}
				});
				var innInfo = "<div class='medinf'><p class='header' style='font-weight: bold;color: #2A6496;' id='medTitle'>"+title+"</p>";
				innInfo += "<div class='scroll-pane5 horizontal-only'><p style='margin-left: 10px;width: 320px;' id='medDesc'>"+desc+"</p></div>";
				$( "#mediaInfo"+mediaId ).html(innInfo);
	
				$("<span><i class='fa fa-pencil'></i></span>")
		        .addClass("capTop")
		        .appendTo("#capTopSpan")
		        .click(function() { editMediaContent(mediaId, title, desc); });
	
				$('.scroll-pane5').jScrollPane(
	       		{
	       			autoReinitialise: true
	       		});
			}
	
			function cancelEditing(mediaId, title, desc){
				var innInfo = "<div class='medinf'><p class='header' style='font-weight: bold;color: #2A6496;' id='medTitle'>"+title+"</p>";
				innInfo += "<div class='scroll-pane5 horizontal-only'><p style='margin-left: 10px;width: 320px;' id='medDesc'>"+desc+"</p></div>";
				$( "#mediaInfo"+mediaId ).html(innInfo);
	
				$("<span><i class='fa fa-pencil'></i></span>")
		        .addClass("capTop")
		        .appendTo("#capTopSpan")
		        .click(function() { editMediaContent(mediaId, title, desc); });
	
				$('.scroll-pane5').jScrollPane(
	       		{
	       			autoReinitialise: true
	       		});
			}
			
			function createAlbum(communityId){
				$(".qtip").hide();
				var newalbId = 0;
				var msg = "<div id='createAlbum'><div id='upperSectionAlbum'><div class='createAlbumForm'>";
				msg += "<input id='intTitle' style='width: 576px;' tabindex='0' maxlength='100' value='' placeholder='Untitled Album' maxlength='50' autocomplete='off' type='text' />";
				msg += "<br/><textarea id='intDesc' style='width: 580px;' placeholder='Say something about this album...' cols='60' rows='10'></textarea>";
				msg += "</div></div>";
				msg += "<div id='lowerSectionAlbum'><div class='scroll-pane2 horizontal-only'><div id='mainSectionDrop' class='dropzone dz-clickable'></div></div></div></div>";
				var myDropzone;
				var dialogInstance = BootstrapDialog.show({
				title: 'Create Album',
				message: msg,
	            closable: false,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	                dialog.getButton('button-add').disable();
	                dialog.getButton('button-done').hide();
	            },
	            buttons: [{
	            	id: 'button-done',
	                label: 'Done!',
	                cssClass: 'btn-primary', 
	                autospin: false,
	                action: function(dialog){
	            	dialog.close();
	            	showAlbumInfo(newalbId);
	            }
	            },{
	            	id: 'button-close',
	                label: 'Close',
	                action: function(dialog){
	            	dialog.close();
	            }
	            }, {
	            	id: 'button-add',
	                label: 'Upload photos',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	var title = document.getElementById('intTitle');
	            	var libraryId = document.getElementById('libraryId').value;
	            	Dropzone.autoDiscover = false;
	            		myDropzone = new Dropzone("#mainSectionDrop", { url: "${communityEraContext.currentCommunityUrl}/library/addDocument.ajx?type=folder"
		            		+"&action=createFolder&communityId="+communityId+"&libraryId="+libraryId,
	            		paramName :'files',
	            		autoProcessQueue: false,
	            		acceptedFiles: "image/*",
	            		parallelUploads: 100,
	            		maxFiles: 100,
	            		uploadMultiple :true
	            		});
	            		
		            	myDropzone.on("addedfile", function (file) {
		            		dialogRef.getButton('button-add').enable();
		                });
	
		            	myDropzone.on("queuecomplete", function (file) {
		            		dialogRef.getButton('button-add').hide();
		            		dialogRef.getButton('button-done').show();
		                });
	
		            	var $footerButton = dialogRef.getButton('button-add');
		            	$footerButton.click(function (e) {
		            		e.preventDefault();
		            	      e.stopPropagation();
		                    myDropzone.processQueue();
		                    dialogRef.getButton('button-add').disable();
		                    dialogRef.getButton('button-add').spin();
		                    dialogRef.getButton('button-close').hide();
		                });
						var counter = 1;
		            	myDropzone.on("sending", function (file, xhr, formData) {
			            	if(counter == 1){
			            		var title = document.getElementById('intTitle');
			            		var description = document.getElementById('intDesc');
			            		
				            	formData.append("title", title.value);
				            	formData.append("description", description.value);
				            	counter = 0;
			            	}
		                });
	
		            	myDropzone.on("successmultiple", function(files, response) {
		            		var obj = jQuery.parseJSON(response);
		            		newalbId = obj.albumId;
		            		var albmId = document.getElementById('currentAlbum').value = obj.albumId;
		            	});
	
		        		dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
		        		dialogRef.getModalBody().find('.scroll-pane3').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
	            	}
		        });
			}
	
			function prepareAlbum(response)
			{
				Dropzone.autoDiscover = false;
				document.getElementById('communityAlbums').className = "selected";
	       		document.getElementById('communityPhotos').className = "";
	       		document.getElementById('currentAlbumName').value = response.title;
	       		var sName = "<div class='albInfoDetail'><span class='secLev'>";
	       		if (response.isAlbumEditAllowed) {
	       			sName += "<a onclick='uploadPhotos();' class='btnmain normalTip' href='javascript:void(0);' title='Add more photos in this album' ><i class='fa fa-photo' style='font-size: 18px; margin-right: 8px;'></i>Add Photos</a>";
				}
	       		if (response.isAlbumEditAllowed) {
	       			sName += "<a href='javascript:void(0)' id='"+response.albumId+"' class='dynaDropDown btnmain normalTip' title='community/communityOptions.ajx?albumId="+response.albumId+"'><i class='fa fa-cog' style='font-size: 18px;'></i></a>";
				}
	       		sName += "</span><div class='albHeading'>"+response.title+"</div><div class='albdetail'>"+response.description+"</div><br /><div class='albdetail'>Added Now</div><br /></div>";
	
	       		sName += "<div class='section section--head'><div class='row'><div id='demo-test-gallery' class='demo-gallery'>";
				$.each(response.photos, function() {
					sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
					 sName += "data-download-url='${communityEraContext.currentCommunityUrl}/library/downloadFile.do?id="+this['id']+"'";
					 sName += "data-url='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?id="+this['id']+"' data-type='library' class='swipebox'>";
					 sName += "<img src='${communityEraContext.contextUrl}/library/mediaDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
					 sName += "</a>";
				 });
				sName += "</div></div></div>";
				$('#innerSection').html(sName);

				$( '.swipebox' ).swipebox();
				
				$('.demo-gallery').each(function (i, el) {
					$(el).justifiedGallery({lastRow : 'nojustify', 
						rowHeight : 200, 
						fixedHeight : true, 
						captions : true, 
						margins : 14,
						sizeRangeSuffixes: {
							'lt100':'_t', 
							'lt240':'_m', 
							'lt320':'_n', 
							'lt500':'', 
							'lt640':'_z', 
							'lt1024':'_b'
						},
						rel: 'gal' + i}).on('jg.complete', function () {
					});
				});
	
	       		if(response.otherAlbums.length > 0){
	       			var aName = "<div class='othralbm'>Other Albums</div>";
	       			aName += "<div class='main_container'>";
					$.each(response.otherAlbums, function() {
						aName += "<div class='pin'><div class='holder'><div class='actions' pin_id='"+this['id']+"'></div>";
						aName += "<a class='image ajax' pin_id='"+this['id']+"' href='javascript:void(0);' onclick='showAlbumInfo(&#39;"+this['id']+"&#39;)'>";
						aName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?mediaId="+this['coverPhotoId']+"' title='"+this['title']+"'/></a></div>";
						aName += "<p class='desc'>"+this['title']+"</p><p class='info'><span>"+this['photoCount']+" photo</span></p>";
						aName += "</div>";
					 });
					aName += "</div>";
					$('#innerSection').append(aName);
	
					// masonry initialization
				    $('.main_container').masonry({
				        // options
				        itemSelector : '.pin',
				        isAnimated: true,
				        isFitWidth: true
				    });
	       		}
	
	   	         $('.dynaDropDown').qtip({
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
	
	       		var body = $("html, body");
	       		body.animate({scrollTop:400}, '500', 'swing', function() { 
	       		});
			}
			
			function uploadPhotos(){
				var msg = "<div class='scroll-pane horizontal-only'><div id='mainSection' class='dropzone dz-clickable'></div></div>";
				var myDropzone;
				var dialogInstance = BootstrapDialog.show({
				title: 'Add Photos',
				cssClass: 'album-dialog',
				message: msg,
	            closable: false,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	                dialog.getButton('button-add').disable();
	                dialog.getButton('button-done').hide();
	            },
	            buttons: [{
	            	id: 'button-done',
	            	//icon: 'glyphicon glyphicon-check',
	                label: 'Done!',
	                cssClass: 'btn-primary', 
	                autospin: false,
	                action: function(dialog){
	            	dialog.close();
	            	var albmId = document.getElementById('currentAlbum').value;
	            	if(albmId > 0){
		            	showAlbumInfo(albmId);
	            	}else {
	            		location.reload(); 
	            	}
	            }
	            },{
	            	id: 'button-close',
	                label: 'Close',
	                action: function(dialog){
	            	dialog.close();
	            }
	            }, {
	            	id: 'button-add',
	                label: 'Upload photos',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	Dropzone.autoDiscover = false;
	            		myDropzone = new Dropzone("#mainSection", { url: "${communityEraContext.currentCommunityUrl}/library/addDocument.ajx",
	            		paramName :'files',
	            		autoProcessQueue: false,
	            		acceptedFiles: "image/*",
	            		parallelUploads: 20,
	            		uploadMultiple :true
	            		});
	            		
		            	myDropzone.on("addedfile", function (file) {
		            		dialogRef.getButton('button-add').enable();
		            		//dialogRef.getButton('button-close').hide();
		                });
	
		            	myDropzone.on("queuecomplete", function (file) {
		            		dialogRef.getButton('button-add').hide();
		            		dialogRef.getButton('button-done').show();
		                });
	
		            	var $footerButton = dialogRef.getButton('button-add');
		            	$footerButton.click(function () {
		                    myDropzone.processQueue();
		                    dialogRef.getButton('button-add').disable();
		                    dialogRef.getButton('button-add').spin();
		                    dialogRef.getButton('button-close').hide();
		                });
	
		            	var counter = 1;
		            	myDropzone.on("sending", function (file, xhr, formData) {
		            		var albmId = document.getElementById('currentAlbum').value;
			            	if(counter == 1 && albmId > 0){
				            	formData.append("folderId", albmId);
				            	counter = 0;
			            	}
		                });
	
	           			dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
	        			{
	        			autoReinitialise: true
	        			}
	   		        );
	            }
		        });
			}
	
			function deletePhoto(photoId){
				$(".qtip").hide();
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Delete Photo',
	                message: '<p style="padding: 15px;">Are you sure you want to delete this photo?</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	id: 'button-cancel',
	                	label: 'Cancel',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                }, {
	                	id: 'button-confirm',
	                	label: 'Confirm',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                	dialogRef.getButton('button-cancel').disable();
	                	dialogRef.getButton('button-confirm').disable();
	                    dialogRef.getButton('button-confirm').spin();
	                    var currentCommunity = document.getElementById('currentCommunity').value;
	                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/deletePhotos.ajx?mediaId="+photoId+"&communityId="+currentCommunity,success:function(result){
	                		 $( "#image_"+photoId ).remove();
	                		 var albSize = $(".demo-gallery > a").length;
	                		 //var imRowCnt = document.getElementById('imRowCnt').value;
	                		 var imRowCnt = $('#imRowCnt').html();
	                		 imRowCnt = imRowCnt -1;
	                		 $('#imRowCnt').html(imRowCnt);
	                		if (albSize == 0) {
	                			$('.demo-gallery').height( 10 );
							} else {
	 	         				$('.demo-gallery').each(function (i, el) {
		         					$(el).justifiedGallery({lastRow : 'nojustify', 
		         						rowHeight : 200, 
		         						fixedHeight : true, 
		         						captions : true, 
		         						margins : 14,
		         						sizeRangeSuffixes: {
		         							'lt100':'_t', 
		         							'lt240':'_m', 
		         							'lt320':'_n', 
		         							'lt500':'', 
		         							'lt640':'_z', 
		         							'lt1024':'_b'
		         						},
		         						rel: 'gal' + i}).on('jg.complete', function () {
		         					});
		         				});
							}
	         				dialogRef.close();
	            	    	}});
	                    }
	                }]
	            });
			}
	
			function deleteAlbum(albumId){
				$(".qtip").hide();
				var albumName = document.getElementById('currentAlbumName').value
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Delete Album',
	                message: '<p style="padding: 15px;">Do you really want to delete &#39;'+albumName+'&#39;? <br />Photos in this album will also be deleted.</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	id: 'button-cancel',
	                	label: 'Cancel',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                }, {
	                	id: 'button-confirm',
	                	label: 'Confirm',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                	dialogRef.getButton('button-cancel').disable();
	                	dialogRef.getButton('button-confirm').disable();
	                    dialogRef.getButton('button-confirm').spin();
	                    var currentCommunity = document.getElementById('currentCommunity').value;
	                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/deleteAlbum.ajx?folderId="+albumId+"&communityId="+currentCommunity,success:function(result){
	                		dialogRef.close();
	                		showAlbums();
	            	    	}});
	                    }
	                }]
	            });
			}
	
			function editAlbum(albumId)
			{
				$(".qtip").hide();
				var newalbId = 0;
				var msg = "<div id='createAlbum' style='height: 360px;'><div id='upperSectionAlbum2'><div class='createAlbumForm2'>";
				msg += "<input id='intTitle' style='width: 576px;' tabindex='0' maxlength='100' value='' placeholder='Untitled Album' maxlength='50' autocomplete='off' type='text' style='width: 576px;' />";
				msg += "<br/><textarea id='intDesc' style='width: 580px;' placeholder='Say something about this album...' cols='60' rows='10'></textarea></div>";
				msg += "</div>";
				msg += "</div>";
				var myDropzone;
				var dialogInstance = BootstrapDialog.show({
				title: 'Edit Album',
				message: msg,
	            closable: false,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	            },
	            buttons: [{
	            	id: 'button-close',
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
	            }
	            },
	            {
	            	id: 'button-done',
	                label: 'Done!',
	                cssClass: 'btn-primary', 
	                autospin: false,
	                action: function(dialog){
	            	var tit = dialog.getModalBody().find('#intTitle').val();
	           		var desc = dialog.getModalBody().find('#intDesc').val();
	           		var currentCommunity = document.getElementById('currentCommunity').value;
	           		var data = new FormData();
      			  	data.append('title', tit);
      			  	data.append('description', desc);
					$.ajax({url:"${communityEraContext.contextUrl}/pers/updateMediaInfo.ajx?folderId="+albumId+"&communityId="+currentCommunity,
						method: "POST",
        			    data: data,
        			    processData: false,
        			    contentType: false,success:function(result){
							dialog.close();
			            	showAlbumInfo(albumId);
						}
					});
	            }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	var currentCommunity = document.getElementById('currentCommunity').value;
	            	$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupLibMediaInfo.ajx?groupMediaId="+albumId+"&type=albumOnly"+
		            	"&currentCommunity="+currentCommunity,dataType: "json",success:function(result){
	            		dialogRef.getModalBody().find('#intTitle').val(result.title);
	            		dialogRef.getModalBody().find('#intDesc').val(result.description);
	            		$('.normalTip').qtip({ 
						    content: {
						        attr: 'title'
						    },
							style: {
						        classes: 'qtip-tipsy'
						    }
						});
						}
					});
	            }
		        });
			}
	
			function moveToAlbum(photoId, albumId)
			{
				$(".qtip").hide();
				var libraryId = document.getElementById('libraryId').value
				BootstrapDialog.show({
	                title: 'Move photo to another album?',
	                message: '<p style="padding: 15px 0px 0px 15px; font-size: 12px; color: #898F9C; font-weight: bold;">Select album</p><div id="selectedAlbum"></div>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	id: 'button-cancel',
	                	label: 'Cancel',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                }, {
	                	id: 'button-confirm',
	                	cssClass: 'btn-primary',
	                	label: 'Move',
	                    action: function(dialogRef){
	                	dialogRef.getButton('button-cancel').disable();
	                	dialogRef.getButton('button-confirm').disable();
	                    dialogRef.getButton('button-confirm').spin();
	                    var somval = $( "input:radio[name=category]:checked" ).attr('id');
	                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/moveToOtherAlbum.ajx?photoId="+photoId+"&folderId="+somval,success:function(result){
	                		dialogRef.close();
	                		showAlbums();
	            	    	}});
	                    }
	                }],
	                onshown: function(dialogRef){
		            	$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMedia.ajx?type=album"+"&folderId="+albumId+"&libraryId="+libraryId,
			            	dataType: "json",success:function(result){
		            		var sName = "";
							var lfcol = "<div style='margin:10px 0px 10px 10px;'>";
							if (result.aData.length > 0) {
			            		 $.each(result.aData, function() {
			            			 var odd = 0;
										var checked = "";
										var stl = "max-width: 240px;";
										if(odd == 0){
											lfcol += "<div style='margin:10px; float: left; width:45%;'><input name='category' id='"+this['id']+"' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='"+this['id']+"'><span></span>"+this['title']+"</label></div>";
											odd = 1;
										} else {
											lfcol += "<div style='margin:10px; float: right; width:45%;'><input name='category' id='"+this['id']+"' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='"+this['id']+"'><span></span>"+this['title']+"</label></div>";
											odd = 0;
										}
			            		 });
							} else {
								lfcol += "<p style='padding: 15px 0px 0px 15px; font-size: 12px; color: #898F9C; font-weight: bold;'>No album available</p>";
							}
		            		lfcol += "</div>";
							sName += "<div  style='width:100%; display: inline-block; min-height:80px;'><div class='scroll-pane6 horizontal-only'>"+lfcol;
							sName += "</div></div>";
							dialogRef.getModalBody().find('#selectedAlbum').html(sName);
			    		    dialogRef.getModalBody().find('.scroll-pane6').jScrollPane(
	   		        			{   verticalDragMinHeight: 20,
	   		        			verticalDragMaxHeight: 200,
	   		        			autoReinitialise: true
	   		        			});
	   			    		}
						});
		            }
	            });
			}
		</script>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
				<div class="innerPanl">
					<div class="carousel_caption">
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Private'}">
							<h4><i class="fa fa-lock" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Protected'}">
							<h4><i class="fa fa-shield" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Public'}">
							<h4><i class="fa fa-globe" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<p>${communityEraContext.currentCommunity.welcomeText}</p>							
					</div>
					
					<div class="menuContainer">
						<ul id="gn-menu" class="gn-menu-main">
							<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
							<li>
								<nav id="menu">
						    		<input type="checkbox" id="toggle-nav"/>
						    		<label id="toggle-nav-label" for="toggle-nav"><i class="fa fa-bars"></i></label>
						    		<div class="box">
							    		<ul>
							    			<li><a href="${communityEraContext.currentCommunityUrl}/home.do"><i class="fa fa-home" style="margin-right: 8px;"></i>Home</a></li>
							    			<c:forEach items="${communityEraContext.enabledFeatureNamesForCurrentCommunity}" var="feature">
												<c:if test="${feature == 'Assignments'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/assignment/showAssignments.do"><i class="fa fa-briefcase" style="margin-right: 8px;"></i>Assignments</a></li>
												</c:if>
												<c:if test="${feature == 'Blog'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>Blogs</a></li>
												</c:if>
												<c:if test="${feature == 'Forum'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do"><i class="fa fa-comments-o" style="margin-right: 8px;"></i>Forums</a></li>
												</c:if>
												<c:if test="${feature == 'Wiki'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
												</c:if>
												<c:if test="${feature == 'Members'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
												</c:if>
												<c:if test="${feature == 'Events'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
												</c:if>
											</c:forEach>
							    		</ul>
						    		</div>
						    	</nav>
					    	</li>
						</ul>
					</div><!-- /container -->
					
					<div class="groups">
						<div class="picture">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="290" height="290" /> 
								</c:when>
								<c:otherwise>
									<img src="img/community_Image.png" id="photoImg" width="290" height="290" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="inputForm" style="padding: 0px;" id="inputForm">
						<input type="hidden" id="currentAlbum" name="currentAlbum" />
						<input type="hidden" id="currentAlbumName" name="currentAlbumName" />
						<input type="hidden" id="pgCount" value="${command.pageCount}" />
						<input type="hidden" id="photoCount" value="${command.rowCount}" />
						<input type="hidden" id="libraryId" value="${command.libraryId}" />
						<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
						<input type="hidden" id="currentCommunity" value="${communityEraContext.currentCommunity.id}" />
						<input type="hidden" id="currentUserPhotoPresent" value="${communityEraContext.currentUser.photoPresent}" />
						<div class="intHeader">
							<span class='firLev'>
								<a onclick="showCommunityPhotos(&#39;${communityEraContext.currentCommunity.id}&#39;);" href="javascript:void(0);" title="Community Photos" class="selected" id="communityPhotos">Community Photos</a>
							</span>
							<span class='firLev'>
								<a onclick="showAlbums(&#39;${communityEraContext.currentCommunity.id}&#39;);" href="javascript:void(0);" title="Your Albums" class="" id="communityAlbums">Albums</a>
							</span>
							<c:if test="${command.member}">
								<span class='secLev' id="addPhotos">
									<a class="btnmain normalTip" onclick="uploadPhotos();" href="javascript:void(0);" 
										title="Add Photos" ><i class="fa fa-photo" style="font-size: 18px; margin-right: 8px;"></i>Add Photos</a>
								</span>
								<span class='secLev'>
									<a class="btnmain normalTip" onclick="createAlbum('${communityEraContext.currentCommunity.id}');" href="javascript:void(0);" 
										title="Create Album" ><i class="fa fa-folder-o" style="font-size: 18px; margin-right: 8px;"></i>Create Album</a>
								</span>
							</c:if>
							<c:if test="${!command.member}">
								<input type="hidden" id="addPhotos" value="" />
							</c:if>
						</div>
								
						<div id="innerSection">
							<div class="section section--head">
							    <div class="row">
							      	<div id="demo-test-gallery" class="demo-gallery">
							      		<c:forEach items="${command.scrollerPage}" var="row">
							      			<a rel="gallery-1" href="${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row.id}" data-id="${row.id}" title="${row.title}" 
							      				data-download-url="${communityEraContext.currentCommunityUrl}/library/downloadFile.do?id=${row.id}" 
							      				data-url="${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?id=${row.id}" data-type="library" class="swipebox">
									          	<img id="${row.id}" src="${communityEraContext.contextUrl}/library/mediaDisplay.img?id=${row.id}" alt="${row.title}" />
									        </a>
							      		</c:forEach>
									</div>
								</div>
							</div>
						</div>
				 	</div> <!-- /myUpload --> 
				 </div>
				</div><!-- /#leftPannel -->
				
				<div class="right-panel">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
		<script type="text/javascript">
		;( function( $ ) {

			$( '.swipebox' ).swipebox();

		} )( jQuery );
		</script>
	</body>
</html>
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
		<meta name="author" content="${command.firstName} ${command.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - ${command.firstName} ${command.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
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
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
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
			    background: none repeat scroll 0% 0% #FFF;
			    padding: 0px;
			    min-height: 230px;
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
				height: 390px;
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
			
			#innerSection .section {
			    padding: 0px 14px;
			}
 		</style>
 		
		<script type="text/javascript">
 			var titems = 0;

 			function downloadPhoto(mediaId)
 			{
 	 			alert('dddd');
 				window.location.href='${communityEraContext.contextUrl}/pers/downloadMedia.do?id='+mediaId;
 			}
 			
			function likeMedia(mediaId, commentId) 
			{ 
				if (commentId > 0) {
			    	$.ajax({url:"pers/likeMedia.ajx?photoId="+mediaId+"&commentId="+commentId,success:function(result){
			    	    $("#divComm-"+commentId).html(result);
			    	  }});
			    } else {
			    	$.ajax({url:"pers/likeMedia.ajx?photoId="+mediaId+"&commentId=0",success:function(result){
			    	    $("#divMed-"+mediaId).html(result);
			    	  }});
			    }
			}
			
				function runScript(e, itemId, commCount, tf) {
 			    if ((e.which == 13 || e.keyCode == 13) && !e.shiftKey) {
 			    	var content = tf.value;
 			    	content = content.replace(new RegExp('\r?\n','g'), '<br />');
 			    	$.ajax({url:"pers/postMediaComment.ajx?mediaId="+itemId+"&comment="+content ,dataType: "json", success:function(result){
	 			    	var ncom = "";
	 			    	ncom += "<div class='showComment'><img src='pers/userPhoto.img?id="+result.posterId+"'/>";
	 			    	ncom += "<div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+result.posterId+"&backlink=ref' class='memberInfo' ";
	 			    	ncom += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+result.posterId+"'>"+result.posterName+"</a>"+result.datePosted+"</div>";

	 			    	ncom += "<div class='incomm'>"+result.comment+"</div>"; //showComment
	 			    	ncom += "<div class='heading' style='float: right;'><span class='lkCnt' id='divComm-"+result.photoId+"'></span>";
	 			    	ncom += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+itemId+"&#39;, &#39;"+result.photoId+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' >Like</a></div>";
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
 			
		    function callSwipebox(className){
				var initPhotoSwipeFromDOM = function(gallerySelector) {
		
					var parseThumbnailElements = function(el) {
					    var thumbElements = el.childNodes,
					        numNodes = thumbElements.length,
					        items = [],
					        el,
					        childElements,
					        thumbnailEl,
					        size,
					        id,
					        item;
		
					    for(var i = 0; i < numNodes; i++) {
					        el = thumbElements[i];
					        // include only element nodes 
					        if(el.nodeType !== 1) {
					          continue;
					        }
					        childElements = el.children;
					        size = el.getAttribute('data-size').split('x');
					        // create slide object
					        item = {
								src: el.getAttribute('href'),
								w: parseInt(size[0], 10),
								h: parseInt(size[1], 10),
								author: el.getAttribute('data-author'),
								id: el.getAttribute('id').substring(6)
					        };
					        item.el = el; // save link to element for getThumbBoundsFn
					        if(childElements.length > 0) {
					          item.msrc = childElements[0].getAttribute('src'); // thumbnail url
					          if(childElements.length > 1) {
					              item.title = childElements[1].innerHTML; // caption (contents of figure)
					          }
					        }
							var mediumSrc = el.getAttribute('data-med');
				          	if(mediumSrc) {
				            	size = el.getAttribute('data-med-size').split('x');
				            	// "medium-sized" image
				            	item.m = {
				              		src: mediumSrc,
				              		w: parseInt(size[0], 10),
				              		h: parseInt(size[1], 10)
				            	};
				          	}
				          	// original image
				          	item.o = {
				          		src: item.src,
				          		w: item.w,
				          		h: item.h
				          	};
					        items.push(item);
					    }
					    return items;
					};
					// find nearest parent element
					var closest = function closest(el, fn) {
					    return el && ( fn(el) ? el : closest(el.parentNode, fn) );
					};
					var onThumbnailsClick = function(e) {
					    e = e || window.event;
					    e.preventDefault ? e.preventDefault() : e.returnValue = false;
					    var eTarget = e.target || e.srcElement;
					    var clickedListItem = closest(eTarget, function(el) {
					        return el.tagName === 'A';
					    });
					    if(!clickedListItem) {
					        return;
					    }
					    var clickedGallery = clickedListItem.parentNode;
					    var childNodes = clickedListItem.parentNode.childNodes,
					        numChildNodes = childNodes.length,
					        nodeIndex = 0,
					        index;
					    for (var i = 0; i < numChildNodes; i++) {
					        if(childNodes[i].nodeType !== 1) { 
					            continue; 
					        }
					        if(childNodes[i] === clickedListItem) {
					            index = nodeIndex;
					            break;
					        }
					        nodeIndex++;
					    }
					    if(index >= 0) {
					        openPhotoSwipe( index, clickedGallery );
					    }
					    return false;
					};
					var photoswipeParseHash = function() {
						var hash = window.location.hash.substring(1),
					    params = {};
					    if(hash.length < 5) { // pid=1
					        return params;
					    }
					    var vars = hash.split('&');
					    for (var i = 0; i < vars.length; i++) {
					        if(!vars[i]) {
					            continue;
					        }
					        var pair = vars[i].split('=');  
					        if(pair.length < 2) {
					            continue;
					        }           
					        params[pair[0]] = pair[1];
					    }
					    if(params.gid) {
					    	params.gid = parseInt(params.gid, 10);
					    }
					    if(!params.hasOwnProperty('pid')) {
					        return params;
					    }
					    params.pid = parseInt(params.pid, 10);
					    return params;
					};
					var openPhotoSwipe = function(index, galleryElement, disableAnimation) {
					    var pswpElement = document.querySelectorAll('.pswp')[0],
					        gallery,
					        options,
					        items;
						items = parseThumbnailElements(galleryElement);
					    // define options (if needed)
					    options = {
					        index: index,
					        galleryUID: galleryElement.getAttribute('data-pswp-uid'),
					        getThumbBoundsFn: function(index) {
					            // See Options->getThumbBoundsFn section of docs for more info
					            var thumbnail = items[index].el.children[0],
					                pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
					                rect = thumbnail.getBoundingClientRect(); 
					            return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
					        },
					        addCaptionHTMLFn: function(item, captionEl, isFake) {
								if(!item.title) {
									captionEl.children[0].innerText = '';
									return false;
								}
								//captionEl.children[0].innerHTML = item.title +  '<br/><small>Photo: ' + item.author + '</small>';
								captionEl.children[0].innerText = '';
								return true;
					        },
					        addCaptionLeftHTMLFn: function(item, captionLeftEl, isFake) {
								var innInfo = "";
								$.ajax({url:"pers/mediaContent.ajx?imgId="+item.id ,dataType: "json", success:function(result){
									var currentUser = document.getElementById('currentUser').value;
									var currentUserPhotoPresent = document.getElementById('currentUserPhotoPresent').value;
			    					innInfo += "<div class='swipebox-content'>";
			    					if(result.photoPresent == "Y"){
			    						innInfo += "<img src='pers/userPhoto.img?id="+result.userId+"'/>";
			    					}else{
			    						innInfo += "<img src='img/user_icon.png'/>";
			    					}
			    					innInfo += "<div class='details'><div class='heading'><a href='pers/connectionResult.do?id="+result.userId+"'>"+result.ownerName+"</a></div>"; //heading
			    					innInfo += "<div class='person'>"+result.created+"</div></div>"; //heading details

			    					innInfo += "<div class='rlikeTopEdit' ><div id='divMed-"+item.id+"' style='float: right;'>";

			    					if (result.photoLikeCount > 0) {
			    						innInfo += "<span class='lkCnt' style='margin-right: 40px; margin-top: -4px;'>";
		    							if (result.photoLikeCount == 1) {
		    								innInfo += result.photoLikeCount + " like";
		    							} else {
		    								innInfo += result.photoLikeCount + " likes";
		    							}
		    							if (result.photoLikeAllowed){
		    								innInfo += " - ";
		    							}
		    							innInfo += "</span>";
			    					}
		    							if (result.photoLikeAllowed){
				    						if (result.photoLikeId > 0) {
				    							innInfo += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+item.id+"&#39;, &#39;0&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
				    						} else {
				    							innInfo += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+item.id+"&#39;, &#39;0&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
				    						}
				    					} 
			    					innInfo += "</div>"; //firstCapTop
			    					
			    					if ( result.editAllowed ) {
			    						innInfo += "<div id='capTopSpan'><span class='capTop' onclick='editMediaContent(&#39;"+item.id+"&#39;, &#39;"+result.title+"&#39;, &#39;"+result.description+"&#39;);'><i class='fa fa-pencil'></i></span></div>"; //capTopSpan
			    					}
			    					innInfo += "</div>"; //rlikeTop
			    					//innInfo += "</div></div></div>";
			    					innInfo += "<div style='display:inline-block;' id='iii'>"; 
			    					innInfo += "<div id='mediaInfo"+item.id+"' class='mediaInfo'><div class='medinf'><p class='header' style='font-weight: bold;color: #2A6496;' id='medTitle"+item.id+"'>"+result.title+"</p>"; //mediaInfo
			    					
			    					innInfo += "<div class='scroll-pane5 horizontal-only'><p id='medDesc'>"+result.description+"</p></div>"; // scroll-pane5
			    					innInfo += "</div></div></div>"; // medinf mediaInfo iii

			    					if ( currentUser > 0 ) {
			    						innInfo += "<div class='addComment'>";
			    						if(currentUserPhotoPresent == "Y"){
			    							innInfo += "<img src='pers/userPhoto.img?id="+currentUser+"'/>";
			    						} else {
			    							innInfo += "<img src='img/user_icon.png'/>";
			    						}
			    						innInfo += "<textarea id='itmCmt"+item.id+"' placeholder='Write a comment...' cols='60' rows='4' class='editItmCmt' ";
			    						innInfo += " onkeypress='return runScript(event, &#39;"+item.id+"&#39;, &#39;"+result.aData.length+"&#39;,this)' ></textarea></div>"; //addComment
			    					} 
			    					innInfo += "<div class='cmntSec'  id='theaderComm'>";
			    					if (result.aData.length > 0) {
			    						innInfo += "<p class='theader'>Comments</p>";
									}
			    					innInfo += "</div>";
			    					innInfo += "<div id='commentArea' class='commentArea'><div class='scroll-pane4 horizontal-only'><div id='cmntInput'>";
			    					var pageNumber = 0;
			    					$.each(result.aData, function() {
			    						innInfo += "<div class='showComment'>";
			    						if(this['photoPresent'] == "Y"){
			    							innInfo += "<img src='pers/userPhoto.img?id="+this['posterId']+"'/>";
				    					}else{
				    						innInfo += "<img src='img/user_icon.png'/>";
				    					}
			    						innInfo += "<div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
			    						innInfo += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a>"+this['datePosted'];
			    						
			    						innInfo += "</div><div class='incomm'>"+this['comment']+"</div>"; //showComment
			    						innInfo += "<div class='heading' style='float:right;' id='divComm-"+this['id']+"'>";
			    						if (this['likeCommentCount'] > 0) {
			    							innInfo += "<span class='lkCnt' >"+this['likeCommentCount'];
			    							if (this['likeCommentCount'] = 1) {
			    								innInfo += "like - ";
			    							} else {
			    								innInfo += "likes - ";
			    							}
										} else {
											innInfo += "<span class='lkCnt' id='divComm-"+this['id']+"'>";
										}
			    						if (this['userLikeId'] > 0) {
			    							innInfo += "</span><a href='javascript:void(0);' onclick='likeMedia(&#39;"+item.id+"&#39;, &#39;"+this['id']+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a></div>";
			    						} else {
			    							innInfo += "</span><a href='javascript:void(0);' onclick='likeMedia(&#39;"+item.id+"&#39;, &#39;"+this['id']+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a></div>";
			    						}
			    						
			    						innInfo += " </div>";//showComment
			    					});
			    					innInfo += "</div></div>"; //.commentArea .scroll-pane4 .swipebox-content
							        captionLeftEl.children[0].innerHTML = innInfo; // caption (contents of figure)
							        $( "textarea" ).click(function() {
							        	this.focus();
							        	});
			    					$('.scroll-pane4').jScrollPane(
	    			        		{
	    			        			autoReinitialise: true
	    			        		});
			    					$('.scroll-pane5').jScrollPane(
	    			        		{
	    			        			autoReinitialise: true
	    			        		});
									return true;
			    				   }
			    				});
					        }
					    };
						var radios = document.getElementsByName('gallery-style');
						for (var i = 0, length = radios.length; i < length; i++) {
						    if (radios[i].checked) {
						        if(radios[i].id == 'radio-all-controls') {
						        } else if(radios[i].id == 'radio-minimal-black') {
						        	options.mainClass = 'pswp--minimal--dark';
							        options.barsSize = {top:0,bottom:0};
									options.captionEl = false;
									options.captionLeftEl = false;
									options.fullscreenEl = false;
									options.shareEl = false;
									options.bgOpacity = 0.85;
									options.tapToClose = false;
									options.tapToToggleControls = true;
						        }
						        break;
						    }
						}
					    if(disableAnimation) {
					        options.showAnimationDuration = 0;
					    }
					    // Pass data to PhotoSwipe and initialize it
					    gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
						var realViewportWidth,
						    useLargeImages = false,
						    firstResize = true,
						    imageSrcWillChange;
							gallery.listen('beforeResize', function() {
							var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
							dpiRatio = Math.min(dpiRatio, 2.5);
						    realViewportWidth = gallery.viewportSize.x * dpiRatio;
						    if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200 ) {
						    	if(!useLargeImages) {
						    		useLargeImages = true;
						        	imageSrcWillChange = true;
						    	}
						    } else {
						    	if(useLargeImages) {
						    		useLargeImages = false;
						        	imageSrcWillChange = true;
						    	}
						    }
						    if(imageSrcWillChange && !firstResize) {
						        gallery.invalidateCurrItems();
						    }
						    if(firstResize) {
						        firstResize = false;
						    }
						    imageSrcWillChange = false;
						});
						gallery.listen('gettingData', function(index, item) {
						    if( useLargeImages ) {
						        item.src = item.o.src;
						        item.w = item.o.w;
						        item.h = item.o.h;
						    } else {
						        item.src = item.m.src;
						        item.w = item.m.w;
						        item.h = item.m.h;
						    }
						});
					    gallery.init();
					};
					// select all gallery elements
					var galleryElements = document.querySelectorAll( gallerySelector );
					for(var i = 0, l = galleryElements.length; i < l; i++) {
						galleryElements[i].setAttribute('data-pswp-uid', i+1);
						galleryElements[i].onclick = onThumbnailsClick;
					}
					// Parse URL and open gallery if it contains #&pid=3&gid=1
					var hashData = photoswipeParseHash();
					if(hashData.pid > 0 && hashData.gid > 0) {
						openPhotoSwipe( hashData.pid - 1 ,  galleryElements[ hashData.gid - 1 ], true );
					}
				};
				initPhotoSwipeFromDOM(className);
			}
		    var count = 2;
			function infinite_scrolling(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    	var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
			            	var currentOwner = document.getElementById('currentOwner').value;
			            	$.ajax({
			                	url:"${communityEraContext.contextUrl}/pers/mediaGallery.ajx?jPage="+count+"&id="+currentOwner,
			                	dataType: "json",
			                	success:function(result){ 
			    					$.each(result.aData, function() {
			    						 var sName = "";
			    						 sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
			    						 sName += "data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['id']+"'  data-type='photo'";
			    						 sName += "data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['id']+"' class='swipebox'>";
			    						 sName += "<img src='pers/photoDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
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
	
		function showYourPhotos(ownerId) {
			document.getElementById('addPhotos').style.display = 'inline';
			document.getElementById('yourAlbums').className = "";
       		document.getElementById('yourPhotos').className = "selected";
			$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaList.ajx?ownerId="+ownerId ,dataType: "json",success:function(result){
				document.getElementById('pgCount').value = result.pgCount;
				count = 2;
				var sName = "<div class='section section--head'><div class='row'><div id='demo-test-gallery' class='demo-gallery'>";
				$.each(result.aData, function() {
					sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
					 sName += "data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['id']+"'  data-type='photo'";
					 sName += "data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['id']+"' class='swipebox'>";
					 sName += "<img src='pers/photoDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
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

		function showAlbums(ownerId) {
			$('#innerSection').html("");
			document.getElementById('addPhotos').style.display = 'none';
			document.getElementById('yourAlbums').className = "selected";
       		document.getElementById('yourPhotos').className = "";
			$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMedia.ajx?ownerId="+ownerId+"&type=album" ,dataType: "json",success:function(result){
				var sName = "<div class='main_container'>";
				$.each(result.aData, function() {
					sName += "<div class='pin'><div class='holder'><div class='actions' pin_id='"+this['id']+"'></div>";
					sName += "<a class='image ajax' pin_id='"+this['id']+"' href='javascript:void(0);' onclick='showAlbumInfo(&#39;"+this['id']+"&#39;, &#39;"+ownerId+"&#39;)'>";
					if(this['photoCount'] > 0) {
						sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['coverPhotoId']+"' title='"+this['title']+"'/>";
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

		function showAlbumInfo(groupMediaId, ownerId) {
			document.getElementById('currentAlbum').value = groupMediaId;
			$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMediaInfo.ajx?groupMediaId="+groupMediaId+"&ownerId="+ownerId+"&type=album" ,dataType: "json",success:function(result){
				prepareAlbum(result);
				}
			});
		}

		function showCommunityPhotos() {
		}
		
		function editMediaContent(mediaId, title, desc){
			var sName = "<div class='editMediaForm'><input id='intTitle"+mediaId+"' class='edit' value='"+title+"' placeholder='Add a title' maxlength='50' autocomplete='off' type='text' />";
			sName += "<br/><textarea id='intDesc"+mediaId+"' placeholder='Add a description' cols='60' rows='10' class='edit'>"+desc+"</textarea><div>";
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
			$.ajax({url:"${communityEraContext.contextUrl}/pers/updateMediaInfo.ajx?mediaId="+mediaId+"&title="+title+"&description="+desc ,success:function(result){
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
		
		function createAlbum(ownerId){
			$(".qtip").hide();
			var newalbId = 0;
			var msg = "<div id='createAlbum'><div id='upperSectionAlbum'><div class='createAlbumForm'>";
			msg += "<input id='intTitle' tabindex='0' maxlength='100' value='' placeholder='Untitled Album' maxlength='50' autocomplete='off' type='text' />";
			msg += "<br/><textarea id='intDesc' placeholder='Say something about this album...' cols='60' rows='10'></textarea></div>";
			msg += "<span style='margin: 6px 0px 2px 0px;'>Contributors</span>";
			msg += "<input id='intSearch' tabindex='0' value='' placeholder='Enter connection name' maxlength='50' autocomplete='off' type='text' class='searchResult' onkeydown='searchConnections()' onmousedown='searchConnections()' />";
			msg += "<div id='addContr' class='addContr'><div class='scroll-pane3 horizontal-only'><div id='maincontr' class=''></div></div></div></div>";
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
            	var curown = document.getElementById('currentOwner').value;
            	showAlbumInfo(newalbId, curown);
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
            	document.getElementById('maincontr').value = "";
            	Dropzone.autoDiscover = false;
            		myDropzone = new Dropzone("#mainSectionDrop", { url: "${communityEraContext.contextUrl}/pers/savePhotos.ajx?type=album",
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
		            		var conList = document.getElementById('finalList');
		            		var contrs = document.getElementById('maincontr');
		            		
			            	formData.append("title", title.value);
			            	formData.append("description", description.value);
			            	formData.append("contributors", conList.value);
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
			document.getElementById('yourAlbums').className = "selected";
       		document.getElementById('yourPhotos').className = "";
       		var sName = "<div class='albInfoDetail'><span class='secLev'>";
       		if (response.isAlbumEditAllowed || response.isContributor) {
       			sName += "<a onclick='uploadPhotos();' class='btnmain normalTip' href='javascript:void(0);' title='Add more photos in this album' ><i class='fa fa-photo' style='font-size: 18px; margin-right: 8px;'></i>Add Photos</a>";
			}
       		if (response.isAlbumEditAllowed) {
       			sName += "<a href='javascript:void(0)' id='"+response.albumId+"' class='dynaDropDown btnmain normalTip' title='community/communityOptions.ajx?albumId="+response.albumId+"&albumName="+response.title+"'><i class='fa fa-cog' style='font-size: 18px;'></i></a>";
			}
       		sName += "</span><div class='albHeading'>"+response.title+"</div><div class='albdetail'>"+response.description+"</div><br /><div class='albdetail'>"+response.createdOn+"</div><br /></div>";

       		sName += "<div class='section section--head'><div class='row'><div id='demo-test-gallery' class='demo-gallery'>";
			$.each(response.photos, function() {
				sName += "<a rel='gallery-1' href='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+this['id']+"' data-id='"+this['id']+"' title='"+this['title']+"' ";
				 sName += "data-download-url='${communityEraContext.contextUrl}/pers/downloadMedia.do?id="+this['id']+"'  data-type='photo'";
				 sName += "data-url='${communityEraContext.contextUrl}/pers/mediaGallery.do?id="+this['id']+"' class='swipebox'>";
				 sName += "<img src='pers/photoDisplay.img?id="+this['id']+"' id='"+this['id']+"' alt='"+this['title']+"'/>";
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
					aName += "<a class='image ajax' pin_id='"+this['id']+"' href='javascript:void(0);' onclick='showAlbumInfo(&#39;"+this['id']+"&#39;, &#39;"+this['ownerId']+"&#39;)'>";
					aName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['coverPhotoId']+"' title='"+this['title']+"'/></a></div>";
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
	 	                at: 'bottom left', // at the bottom right of...
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

		function searchConnections()
		{
			var searchString = document.getElementById('intSearch');
			$('.searchResult').each(function() {
		         $(this).qtip({
		            content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: '${communityEraContext.contextUrl}/pers/connectionSearch.ajx?searchString='+searchString.value // Use href attribute as URL
		                    })
		                    .then(function(content) {
		                    	if (content.aData.length == 0) {
			                    	$(".qtip").hide();
								}
		                    	var sList = "<ul>";
		                    	$.each(content.aData, function() {
		                    		sList += "<li class='srchRslt' onclick='addToMyList(&#39;"+this['id']+"&#39;, &#39;"+this['photoPresent']+"&#39;)' >";
		                    		if(this['photoPresent'] == "Y"){
		                    			sList += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['id']+"' width='30' height='30' />&nbsp;&nbsp;";
		                    		} else {
		                    			sList += "<img src='img/user_icon.png'  width='30' height='30'/>&nbsp;&nbsp;";
		                    		}
		                    		sList += this['firstName']+" "+this['lastName']+"</li>";
		                    	});
		                    	sList += "</ul>";
		                    	
		                        api.set('content.text', sList);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', 'Loading...');
		                    });
		                    return 'Loading...'; // Set some initial text
		                }
		            },
		            show: {
		            	event: 'keyup mouseenter',
		                target: $('.searchResult')
		            },
		            position: {
		                my: 'top left',  // Position my top left...
		                at: 'bottom left', // at the bottom left of...
		                target: $('.searchResult') // my target
		            },
		            hide: {
		                event: 'unfocus'
		            },
					style: {
		            	tip:false,
				        classes: 'qtip-bootstrap myCustomClass31'
				    }
		         });
		     });
		}

		function addToMyList(userId, photoPresent) {
			$(".qtip").hide();
			var added = document.getElementById('finalList');
			if (added.value != '') {
				var array = added.value.split(',');
				var arraycontainsturtles = (array.indexOf(userId) > -1);
				if (arraycontainsturtles){
					return false;
				}
			}
			var divcontr = $('#maincontr');
			var inner = "";
			if (photoPresent == 'Y') {
				inner += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+userId+"&backlink=ref' id="+userId+" >";
				inner += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+userId+"' width='40' height='41'/></a>";
			} else {
				inner += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+userId+"&backlink=ref' id="+userId+" >";
				inner += "<img src='img/user_icon.png' id='photoImg' width='40' height='41' /></a>";
			}
			divcontr.append(inner);
			var flist = "";
			if(added.value == ""){
				flist = userId;
			}else{
				flist = added.value+","+userId;
			}
			document.getElementById('finalList').value = flist;
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
            		var curown = document.getElementById('currentOwner').value;
	            	showAlbumInfo(albmId, curown);
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
            		myDropzone = new Dropzone("#mainSection", { url: "${communityEraContext.contextUrl}/pers/savePhotos.ajx",
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
			            	formData.append("albumId", albmId);
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
                    action: function(dialogRef){
                	dialogRef.getButton('button-cancel').disable();
                	dialogRef.getButton('button-confirm').disable();
                    dialogRef.getButton('button-confirm').spin();
                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/deletePhotos.ajx?photoId="+photoId,success:function(result){
                		 $( "#image_"+photoId ).remove();
                		 var albSize = $(".demo-gallery > a").length;
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

		function deleteAlbum(albumId, albumName){
			$(".qtip").hide();
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
                    action: function(dialogRef){
                	dialogRef.getButton('button-cancel').disable();
                	dialogRef.getButton('button-confirm').disable();
                    dialogRef.getButton('button-confirm').spin();
                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/deleteAlbum.ajx?albumId="+albumId,success:function(result){
                		dialogRef.close();
                		var owner = document.getElementById('currentOwner').value;
                		showAlbums(owner);
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
			msg += "<input id='intTitle' tabindex='0' maxlength='100' value='' placeholder='Untitled Album' maxlength='50' autocomplete='off' type='text' style='width: 576px;' />";
			msg += "<br/><textarea id='intDesc' placeholder='Say something about this album...' cols='60' rows='10' style='width: 580px;'></textarea></div>";
			msg += "<br/><span style='margin: 6px 0px 2px 0px;'>Contributors</span>";
			msg += "<input id='intSearch' tabindex='0' value='' placeholder='Enter connection name' maxlength='50' autocomplete='off' type='text' class='searchResult' onkeydown='searchConnections()' onmousedown='searchConnections()' style='width: 576px;' />";
			msg += "<br/><div id='addContr' class='addContr' style='width: 584px;' ><div class='scroll-pane3 horizontal-only'><div id='maincontr' class=''></div></div></div></div>";
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
            	var title = dialog.getModalBody().find('#intTitle').val();
           		var desc = dialog.getModalBody().find('#intDesc').val();
           		var added = document.getElementById('finalList').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateMediaInfo.ajx?albumId="+albumId+"&title="+title+"&description="+desc+"&contList="+added ,success:function(result){
						dialog.close();
		            	var curown = document.getElementById('currentOwner').value;
		            	showAlbumInfo(albumId, curown);
					}
				});
            }
            }],
            cssClass: 'login-dialog',
            onshown: function(dialogRef){
            	$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMediaInfo.ajx?groupMediaId="+albumId+"&type=albumOnly",dataType: "json",success:function(result){
            		dialogRef.getModalBody().find('#intTitle').val(result.title);
            		dialogRef.getModalBody().find('#intDesc').val(result.description);
            		var cList = result.contList;
            		document.getElementById('finalList').value = cList;
            		var added = document.getElementById('finalList');
            		if (added.value != '') {
            			var divcontr = dialogRef.getModalBody().find('#maincontr');
	    				var inner = "<div style='position: relative; display: inline-flex;' id='contSection'>";
	            		$.each(result.links, function() {
	            			inner += "<div class='picture' style='position: relative;' id='cont_"+this['contributorId']+"'>";
	            			if (this['photoPresent'] == 'Y') {
		    					inner += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['contributorId']+"' width='40' height='41' title='"+this['displayName']+"' class='normalTip' />";
		    				} else {
		    					inner += "<img src='img/user_icon.png' id='photoImg' width='40' height='41' title='"+this['displayName']+"' class='normalTip' />";
		    				}
	            			inner += "<div class='captionBottom2' onclick='removeContributor(&#39;"+this['contributorId']+"&#39;,&#39;"+this+"&#39;);' style='position: absolute;'><span>X</span></div></div>";
	            		});
	            		inner += "</div>";
	            		divcontr.append(inner);
					}
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

		function removeContributor (contributorId, curr){
			//var divcontr = dialogRef.getModalBody().find('#maincontr').html();
			var fset = document.getElementById('contSection');
			var ch = document.getElementById('cont_'+contributorId)
			fset.removeChild(ch);

			var added = document.getElementById('finalList');
			var newList = "";
			if (added.value != '') {
				var array = added.value.split(',');
				for(var x=0; x < array.length ;x++){
					var inner = array[x];
					if(inner != contributorId){
						newList += ","+inner;
					}
				}
			}
			
			document.getElementById('finalList').value = newList;
		}

		function moveToAlbum(photoId, albumId)
		{
			$(".qtip").hide();
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
                	label: 'Move',
                    action: function(dialogRef){
                	dialogRef.getButton('button-cancel').disable();
                	dialogRef.getButton('button-confirm').disable();
                    dialogRef.getButton('button-confirm').spin();
                    var somval = $( "input:radio[name=category]:checked" ).attr('id');
                	 $.ajax({url:"${communityEraContext.contextUrl}/pers/moveToOtherAlbum.ajx?photoId="+photoId+"&albumId="+somval,success:function(result){
                		dialogRef.close();
                		var owner = document.getElementById('currentOwner').value;
                		showAlbums(owner);
            	    	}});
                    }
                }],
                onshown: function(dialogRef){
                	var owner = document.getElementById('currentOwner').value;
	            	$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupMedia.ajx?ownerId="+owner+"&type=album"+"&albumId="+albumId,
		            	dataType: "json",success:function(result){
	            		var sName = "";
						var lfcol = "<div style='margin:10px 0px 10px 10px;'>";
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
		
		<script>
			$(document).ready(function () {
				Dropzone.autoDiscover = false;
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
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
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
					
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.id}">Connections</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="padding: 0px; margin-bottom: 10px;" id="inputForm">
					<input type="hidden" id="currentAlbum" name="currentAlbum" />
					<input id="finalList" type="hidden" name="finalList" />
					<input type="hidden" id="pgCount" value="${command.pageCount}" />
					<input type="hidden" id="currentOwner" name="currentOwner" value="${command.user.id}"/>
					<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
					<input type="hidden" id="currentUserPhotoPresent" value="${communityEraContext.currentUser.photoPresent}" />
					
						<c:choose>
							<c:when test="${command.user.inactive}">
								 <div class="innerBlock" style="padding: 10px 10px 20px;">
								 	<p style="color: rgb(137, 143, 156); word-wrap: break-word;">This user is no longer registered. The information below may be obsolete </p>
								 </div>
							</c:when>
							<c:otherwise>
								<c:if test="${command.user.id == communityEraContext.currentUser.id}">
									<div class="intHeader">
										<span class='firLev'>
											<a onclick="showYourPhotos(&#39;${command.user.id}&#39;);" href="javascript:void(0);" title="Your Photos" class="selected" id="yourPhotos">Your Photos</a>
										</span>
										<span class='firLev'>
											<a onclick="showAlbums(&#39;${command.user.id}&#39;);" href="javascript:void(0);" title="Your Albums" class="" id="yourAlbums">Albums</a>
										</span>
										<%--<span class='firLev'>
											<a onclick="showCommunityPhotos();" href="javascript:void(0);" title="Your Community Photos" class="" id="communityPhotos" >Community Photos</a>
										</span>
										<span class='secLev'>
											<a onclick="uploadVideos();" href="javascript:void(0);" title="Add Video" >Add Video</a>
										</span> --%>
										<span class='secLev' id="addPhotos">
											<a class="btnmain normalTip" onclick="uploadPhotos();" href="javascript:void(0);" title="Add Photos" >
												<i class="fa fa-photo" style="font-size: 18px; margin-right: 8px;"></i>Add Photos</a>
										</span>
										<span class='secLev'>
											<a class="btnmain normalTip" onclick="createAlbum('${communityEraContext.currentUser.id}');" href="javascript:void(0);" title="Create Album" >
												<i class="fa fa-folder-o" style="font-size: 18px; margin-right: 8px;"></i>Create Album</a>
										</span>
									</div>
								</c:if>
								
								<c:if test="${command.user.id != communityEraContext.currentUser.id}">
									<div class="intHeader">
										<span class='firLev'>
											<a onclick="showYourPhotos(&#39;${command.user.id}&#39;);" href="javascript:void(0);" id="yourPhotos" title="${command.user.firstName}'s Photos" class="selected">${command.user.firstName}'s Photos</a>
										</span>
										<span class='firLev'>
											<a onclick="showAlbums(&#39;${command.user.id}&#39;);" href="javascript:void(0);" id="yourAlbums" title="${command.user.firstName}'s Albums" class="">Albums</a>
										</span>
										<%--<span class='firLev'>
											<a onclick="showCommunityPhotos();" href="javascript:void(0);" title="Your Photos" class="" id="communityPhotos" >Community Photos</a>
										</span> --%>
										<span class='secLev' id="addPhotos">
										</span>
									</div>
								</c:if>
								<div id="innerSection">
									<div class="section section--head">
									    <div class="row">
									      	<div id="demo-test-gallery" class="demo-gallery">
									      		<c:forEach items="${command.scrollerPage}" var="row">
									      			<a rel="gallery-1" href="${communityEraContext.contextUrl}/pers/photoDisplay.img?id=${row.id}" id="image_${row.id}" data-id="${row.id}" title="${row.title}"
									      			data-download-url="${communityEraContext.contextUrl}/pers/downloadMedia.do?id=${row.id}" data-type="photo"
							      					data-url="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${row.id}" class="swipebox">
											          <img src="pers/photoDisplay.img?id=${row.id}" alt="${row.title}" id="${row.id}"/>
											        </a>
									      		</c:forEach>
											</div>
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
						
				 	</div> <!-- /myUpload --> 
				 </div>
				</div><!-- /#leftPannel -->
				
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
		<script type="text/javascript">
		  (function() {
			  $( '.swipebox' ).swipebox();
		 	})();
		</script>
	</body>
</html>
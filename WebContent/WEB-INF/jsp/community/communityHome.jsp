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
		<meta name="author" content="${command.creator.fullName}">
		<meta name="robots" content="index, follow">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, blog, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>${communityEraContext.currentCommunity.name} - Jhapak</title>	
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>

		
		<link rel="stylesheet" type="text/css" href="css/jquery.tosrus.all.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" media="all" type="text/css" href="css/media.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/captionMedia.css" />
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		
		<script src='js/jquery.swipebox.js'></script>
		<link rel='stylesheet' href='css/swipebox.css' type='text/css' />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/home.css" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/accord.css" />
		<script type="text/javascript" src="js/jquery.accordion.js"></script>
		<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/newStyle.css" />
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<script src='js/jquery.swipebox.js'></script>
		<link rel='stylesheet' href='css/swipebox.css' type='text/css' />
		
		<script type="application/ld+json">
			{
  				"@context" : "http://schema.org",
  				"@type" : "Article",
  				"name" : "${communityEraContext.currentCommunity.name}",
  				"author" : {
    				"@type" : "Person",
    				"name" : "${command.creator.fullName}"
  				},
  				"datePublished" : "${command.isoPostedOn}",
				"headline" : "${communityEraContext.currentCommunity.name}",
  				"image" : "${command.markupImage}",
  				"articleBody" : "${communityEraContext.currentCommunity.welcomeText}",
  				"url" : "${communityEraContext.requestUrl}"
			}
		</script>
		
		<!-- Twitter Card data -->
		<meta name="twitter:card" content="summary">
		<meta name="twitter:site" content="@EraCommunity">
		<meta name="twitter:title" content="${communityEraContext.currentCommunity.name}">
		<meta name="twitter:description" content="${communityEraContext.currentCommunity.welcomeText}">
		<meta name="twitter:url" content="${communityEraContext.requestUrl}">
		<meta name="twitter:image:src" content="${command.markupImage}">
		
		<!-- Open Graph data -->
		<meta property="og:title" content="${communityEraContext.currentCommunity.name}" />
		<meta property="og:type" content="article" />
		<meta property="og:url" content="${communityEraContext.requestUrl}" />
		<meta property="og:image" content="${command.markupImage}" />
		<meta property="og:description" content="${communityEraContext.currentCommunity.welcomeText}" />
		<meta property="og:site_name" content="Jhapak" />
		<meta property="article:published_time" content="${command.isoPostedOn}" />
		<meta property="article:modified_time" content="${command.isoPostedOn}" />

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

		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<link rel="stylesheet" type="text/css" href="css/topscroll.css" />
		
		<style type="text/css">
			.fpassLnk {
				font-size: 12px;
				font-weight: bold;
				color: #66799f;
				text-decoration: none;
			}
			
			.fpassLnk:hover {
				outline: medium none;
				color: #2A3A47;
			}
			
			.success-dialog .modal-dialog {
                width: 500px;
            }
            
            .addTag-dialog .modal-dialog {
                width: 720px;
            }
            
            #container .left-panel .commSection .paginatedList .details .person a {
            	padding:0px;
            }
            
            #container .left-panel .commSection .paginatedList .detailNF {
            	font-size: 20px;
				font-weight: bold;
				opacity: 1;
				color: #66799F;
				background: transparent none repeat scroll 0% 0%;
				text-indent: 0px;
				text-decoration: none;
				line-height: 50px;
            }
            
			.addTag-dialog .modal-dialog {
                width: 720px;
				margin: 30px auto;
                padding : 20px;
            }
            			
            .st-accordion ul li span.person {
			    top: 0px;
			    left: 0px;
			    position: relative;
			    width: 100%;
			}
			
			.st-content {
			    padding: 0px 3px 10px;
			    width: 100%;
			    background-color: #fff;
			}
			
			.st-accordion ul li > a {
				top: 10px;
				line-height: 0px;
				height: 60px;
				
			}
			
			.st-accordion ul li > a span.st-arrow { 
				top: 14%;
			}
			
			.st-accordion ul li span.person {
			    top: -10px;
			    left: 0px;
			    position: relative;
			    float: left;
			}
			
			.st-content {
				top: -10px;
				position: relative;
				margin-bottom: 30px;
			}
			
			.st-content p {
				padding: 0px 10px 20px 10px;
			}
			
			#main .featureLnk {
				text-decoration: none;
				width: auto;
				display: inline-block;
				white-space: normal;
				border-color: #546E86;
				border-bottom-color: #374757;
				background-color: #546E86;
				border-width: 0px 0px 2px 0px;
				border-style: solid;
				color: #fff;
				padding: 10px;
				font-size: 16px;
				margin-top: 16px;
				margin-bottom: 8px;
				margin-left: 10px;
				margin-right: 10px;
				border-radius: 2px;
				webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				text-align: center;
				vertical-align: middle;
				font-weight: bold;
				cursor: pointer;
			}
			
			#main .featureLnkMand {
				text-decoration: none;
				width: auto;
				display: inline-block;
				white-space: normal;
				border-color: #546E86;
				border-bottom-color: #374757;
				background-color: #a3b5c5;
				border-width: 0px 0px 2px 0px;
				border-style: solid;
				color: #fff;
				padding: 10px;
				font-size: 16px;
				margin-top: 16px;
				margin-bottom: 8px;
				margin-left: 10px;
				margin-right: 10px;
				border-radius: 2px;
				webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				text-align: center;
				vertical-align: middle;
				font-weight: bold;
				cursor: text;
			}
			
			#main .featureLnk:hover, #main .featureLnk:focus {
				border-color: #009aab;
				border-bottom-color: #006671;
				background-color: #64829e;
				color: #fff;
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
		</style>
		
		<script type="text/javascript">
		function dynamicDropDownQtip(cnt){ //dynaDropDown
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

		function memberInfoQtip(cnt){ 
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
			function submitTagAction(){
				var toElm = document.getElementById('addedTags');
				var toArray = toElm.value.split(' ');
				var isSpecialCharPresent = false;
				for (i=0; i<toArray.length; i++) { 				
					var str = toArray[i].toLowerCase();		
					if (str.match("[$&+,:;=?@#|'<>.-^*()%!]")) {
						isSpecialCharPresent = true;
						break;
					}
				}
				if (isSpecialCharPresent) {
					var type = BootstrapDialog.TYPE_DANGER;
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Error',
		                message: 'Special characters like &, #, @, etc are not allowed. in tags',
		                cssClass: 'tag-dialog',
		                closable: true,
		                closeByBackdrop: true,
		                closeByKeyboard: true,
		                draggable: true,
		                buttons: [{
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
				} 
			}

			function addEditTag(tagName)
			{
				var toElm = document.getElementById('addedTags');
				var toArray = toElm.value.split(' ');
				var isAlreadyPresent = false;
				for (i=0; i<toArray.length; i++) { 
					if (toArray[i].replace( /^\s+/g, "" ) == tagName) {
						isAlreadyPresent = true;
					}
				}
				if (!isAlreadyPresent) {
					var sep = '';
					if (toElm.value != '') sep = ' ';
					toElm.value = toElm.value + sep + tagName;
				}
			}
			
			function addEditTags(parentType, commId) {
				$(".qtip").hide();
				BootstrapDialog.show({
	                title: 'Add/Edit tags for Community',
	                message: function(dialog) {
						var $message = $('<div id="main" style="display: inline-block;width: 660px;"></div>');
			            return $message;
			        },
	                closable: false,
	                cssClass: 'addTag-dialog',
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                onshow: function(dialog) {
			        	var addtags = "";
			        	var form = "<div style='display:inline-block;'>";
			        		form += "<p class='addTagHeader'>A tag is a keyword / one word label which members can assign to a community to categorize it. A tag make it easier to find related communities. Popular tags appear in larger text in the tag cloud.<br /><br />";
			        		form += "<strong>Use space to separate tags. Special characters like &, #, @, etc are not allowed.</strong></p><br />";
			        		form += "<label for='txtKeywords' class='tagFieldLab'>Add Tags:</label>";
			        		form += "<input id='addedTags' name='tags' type='text' class='tagFieldInput' autocomplete='off'></input>";
			        		$.ajax({url:"${communityEraContext.contextUrl}/tag/mostPopularTags.ajx?maxTags=30&parentType="+parentType+"&communityId="+commId,success:function(result){
			        			if (result.aData.length > 0) {
			        				form += "<p class='addTagHeader2' >Popular tags can also be reused. Just click to add to the tag list</p><p class='addTagHeader3'>";
			        				$.each(result.aData, function() {
			        					form += "<a href='javascript:void(0);' onclick='addEditTag(&#39;"+this['tagText']+"&#39;)' title='Click to add tag &#39;"+this['tagText']+"&#39; to the tag list'>"+this['tagText']+"</a>";
			        				});
			        				form += "</p>";
				        		}
			        			addtags = result.tags;
			        			form += "</div>";
					            dialog.getModalBody().find('#main').html(form);
					            dialog.getModalBody().find('#addedTags').val(addtags);
	            	    	  }});
			        },
	                buttons: [{
	                	label: 'Cancel',
	                	id: 'button-close',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                },
	                {
	                	label: 'Submit tags',
	                	id: 'button-Go',
	                	cssClass: 'btn-primary',
	                    action: function(dialogRef){
	                	dialogRef.getButton('button-Go').disable();
	                	dialogRef.getButton('button-close').disable();
	                	dialogRef.getButton('button-Go').spin();
	                		submitTagAction();
	                		var alredaytags = document.getElementById('addedTags').value;
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/addTag.ajx?backlink=ref&parentType="+parentType+"&parentId="+commId+"&tags="+alredaytags,success:function(result){
								if(result.tagCount > 0){
									$('#taggedKeywords').html(result.taggedKeyWords);
								}else{
									$('#taggedKeywords').html("");
								}
	                        	dialogRef.close();
	            	    	  }});
	                    }
	                }]
	            });
			}

			function addMoreFeatures(commId) {
				$(".qtip").hide();
				BootstrapDialog.show({
	                title: 'Add Features for Community',
	                message: function(dialog) {
						var $message = $('<div id="main" style="display: inline-block;width: 660px;"></div>');
			            return $message;
			        },
	                closable: false,
	                cssClass: 'addTag-dialog',
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                onshow: function(dialog) {
			        	var form = "<div style='display:inline-block;'>";
			        		form += "<p class='addTagHeader'></p>";
			        		form += "<div style='display:inline-block;'>";
			        		$.ajax({url:"${communityEraContext.contextUrl}/communities/showCommunityFeatures.ajx?communityId="+commId,success:function(result){
			        			if (result.aData.length > 0) {
			        				$.each(result.aData, function() {
				        				if (this['anabled']) {
				        					form += "<a class='featureLnkMand' href='javascript:void(0);' onclick='' title='"+this['title']+"'>"+this['label']+"<i class='fa fa-check' style='margin: 0px 4px; font-size: 18px; color: #32cd32;'></i></a>";
										} else {
											form += "<a class='featureLnk' href='javascript:void(0);' onclick='addNewFeature(&#39;"+this['name']+"&#39;, &#39;"+this['communityId']+"&#39;)' title='"+this['title']+"' id='"+this['name']+"-1'>"+this['label'];
											form += "</a>";
											form += "<a class='featureLnkMand' href='javascript:void(0);' onclick='' title='"+this['title']+"' style='display:none;' id='"+this['name']+"-2'>"+this['label'];
											form += "<i class='fa fa-check' style='margin: 0px 4px; font-size: 18px; color: #32cd32;' id=''></i>";
											form += "</a>";
										}
			        				});
			        				form += "</div>";
				        		}
			        			addtags = result.tags;
			        			form += "</div>";
					            dialog.getModalBody().find('#main').html(form);
	            	    	  }});
			        },
	                buttons: [
	                {
	                	label: 'Done',
	                	id: 'button-Go',
	                	cssClass: 'btn-primary',
	                    action: function(dialogRef){
	                		dialogRef.close();
	                		location.reload();
	                    }
	                }]
	            });
			}

			function addNewFeature(feature, communityId) {
				$.ajax({url:"${communityEraContext.contextUrl}/communities/addCommunityFeatures.ajx?featureName="+feature+"&communityId="+communityId,success:function(result){
					$("#"+feature+"-2").show();
					$("#"+feature+"-1").hide();
    	    	  }});
			}
			
			function leaveCommunity(ctype) 
			{
				$(".qtip").hide();
				var ref = '${communityEraContext.currentCommunityUrl}/comm/leaveCommunity.do';
		    	type = BootstrapDialog.TYPE_WARNING;
		    	mess = '<p class="addTagHeader"><strong>You will no longer be a member of this community.<strong><br /><br />' +
			    	'As a result of this, you will have no access to community content or receive email alerts when new content is published to this community.</p>';
		    	BootstrapDialog.show({
	                type: type,
	                title: 'Leave this Community?',
	                message: mess,
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	label: 'Cancel',
	                	id: 'button-close',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                },{
	                	label: 'OK',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                	$.ajax({url:ref,success:function(result){
                        	dialogRef.close();
                        	var mess = '<div id="main"><p class="seccMsg"><strong>You are no longer a member of this community.<br /></strong>';
                        	mess += 'Click <a href="${communityEraContext.contextUrl}/community/showCommunities.do" class="fpassLnk">here</a> to return to the Community List.</p></div>';
                        	BootstrapDialog.show({
                        		type: BootstrapDialog.TYPE_SUCCESS,
            	                title: 'You have left the community!',
            	                message: function(dialog) {
    	        					var $message = $(mess);
    	        	                return $message;
    	        	            },
            	                cssClass: 'success-dialog',
            	                closable: false,
            	                closeByBackdrop: false,
            	                closeByKeyboard: false,
            	                buttons: [{
            	                	label: 'Close',
            	                	cssClass: 'btn-success',
            	                	id : 'btnSuccessClose',
            	                    action: function(dialogRef){
	            	                	if (ctype == true) { //private
	        	                			window.location.href="${communityEraContext.contextUrl}/community/showCommunities.do";
	        	                		} else {
	        	                			dialogRef.close();
	        	                		}
            	                    }
            	                }]
            	            });
            	    	  }});
	                    }
	                }]
	            });
			}
		
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

			function subscribeCommunity(){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Follow this community',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to follow this community. </p>',
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
				$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/subscribeCommunity.ajx",success:function(result){
		    	    $("#subscribeCommunity").html(result);
		    	  }});
			    }
			}
		
			function unSubscribeCommunity(){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Stop following this community',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to join a community. </p>',
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
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Stop following this community',
	                message: '<p class="addTagHeader">You will no longer be able to receive updates for community and it\'s items.</p>',
	                closable: true,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
	                buttons: [{
	                	label: 'Close',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                    }
	                },
	                {
	                	label: 'Go',
	                	cssClass: 'btn-warning',
	                    action: function(dialogRef){
	                        dialogRef.close();
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/community/unSubscribeCommunity.ajx",success:function(result){
	            	    	    $("#subscribeCommunity").html(result);
	            	    	  }});
	                    }
	                }]
	            });
			    }
			}

			function updateSubscription(communityId, userId, actionFor, type){
				$.ajax({url:"${communityEraContext.contextUrl}/cid/"+communityId+"/community/updateSubscription.ajx?communityId="+communityId+"&actionFor="+actionFor+"&type="+type+"&userId="+userId,success:function(result){
		    	    //$("#connectionInfo").html(result);
		    	  }});
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
		// Initial call
		$(document).ready(function(){
			$('#st-accordion').accordion({
				open			: 0,
				oneOpenedItem	: true
			});

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
			
			$.ajax({url:"${communityEraContext.contextUrl}/common/mediaPannel.ajx?communityId=${communityEraContext.currentCommunity.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					temp += "<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id="+this['mediaId']+"' width='89' height='89' style='padding: 3px; border-radius: 10px;' />";
					});
				if(result.aData.length > 0){
					$("#viewMedLis").html('<a href="${communityEraContext.currentCommunityUrl}/library/mediaGallery.do?backlink=ref">View Gallery</a>');
					$("#mediaList").html(temp);
				} else {
					$("#viewMedLis").html('<a href="${communityEraContext.currentCommunityUrl}/library/mediaGallery.do?backlink=ref">Add Media</a>');
					$("#mediaList").html("<span style ='font-size: 12px; padding-left: 64px;'>No Media found</span>");
				}
		        $("#waitMediaMessage").hide();
			    },
		         beforeSend: function () {
		             $("#waitMediaMessage").show();
		         } 
		    });
			
			$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/cloudCommunity.ajx",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 var count = 'item';
					 if (this['count'] > 1) {
						 count = 'items';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 aName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' >"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 if(aName == "<ul>"){
						$("#cloud").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
					}else{
						$("#cloud").html(aName+"</ul>");
					}
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 var count = 'item';
					 if (this['count'] > 1) {
						 count = 'items';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span><a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
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

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId9999' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId9999' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
					 }
					});
				 $("#authorsList").html(temp);
				 
				// Hide message
		        $("#waitBLAthMessage").hide();
		        memberInfoQtip(9999);
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitBLAthMessage").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}&memRequest=Y",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId9998' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfoId9998' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
					 }
					});
				 $("#memReqList").html(temp);
				 
				// Hide message
		        $("#waitMEMMessage").hide();
		        memberInfoQtip(9998);
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitMEMMessage").show();
		         } 
		    });

			var count = 2;
			var count2 = 1;
			function infinite_scrolling_allComm(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    		var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else if (count2 < count){
			        		count2++;
			        		var items = [];
				        	$( "<div id='rowSection"+count+"'></div>" ).insertAfter( "#rowSection"+(count-1) );
			            	$.ajax({
			                	url:"${communityEraContext.currentCommunityUrl}/home.ajx?jPage="+count,
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
			   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >assignment</a> on "+this['postedOn'];
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Assignment&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						} else if (this['itemType'] == "ForumTopic") {
			   							sName += "started <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >forum topic</a> on "+this['postedOn'];
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=ForumTopic&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						} else if (this['itemType'] == "BlogEntry") {
			   							sName += "posted <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >blog entry</a> on "+this['postedOn'];
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=BlogEntry&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						} else if (this['itemType'] == "Event") {
			   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >event</a> on "+this['postedOn'];
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Event&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						} else if (this['itemType'] == "WikiEntry") {
			   							sName += "created <a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >wiki article</a> on "+this['postedOn'];
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=WikiEntry&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						} else if (this['itemType'] == "Document") {
			   							sName += "added ";
			   							if (this['contentType'] == "PhotoGroup") {
			   								sName += this['imageCount']+" <a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/library/mediaGallery.do' >Photos</a> "+this['postedOn'];
			   							} else if (this['contentType'] == "Photo") {
			   								sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/library/mediaGallery.do' >Photo</a> "+this['postedOn'];
			   							} else {
			   								sName += "<a href='${communityEraContext.contextUrl}"+this['communityId']+"' >File</a> on "+this['postedOn'];
			   							}
			   							sName += "<div class='entry'><a href='javascript:void(0);' class='dynaDDNoti_"+count+"' ";
			   							sName += " title='pers/subscriptionOptions.ajx?commId="+this['communityId']+"&type=Document&userId="+this['posterid']+"' ><i class='fa fa-th-large'></i></a></div>";
			   						}
			   						sName += "</div></div>";
			   						if (this['itemType'] == "Document" || this['itemType'] == "Event") {
			   							if (this['itemType'] == "Document") {
			   								if (this['contentType'] == "PhotoGroup" || this['contentType'] == "Photo") {
			   									sName += "<div id='ltbox_"+this['itemId']+"' class='imgOuter2'>";
												sName += "<div id='demo-gallery"+this['itemId']+"' class='demo-gallery'>";
												var incount = 0;
												var itemid = this['itemId'];
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
			   								} else {
			   									sName += "<div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div>";
			   								}
			   							}
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
			   						} else {
			   							if (this['imageCount'] > 0) {
											sName += "<div class='pListInner' ><div class='leftImgLarge'>";
											sName += "<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type="+this['itemType']+"&itemId="+this['itemId']+"' />";
												sName += "</div><div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div><div class='coverBody'>"+this['bodyDisplay']+"</div></div>";
										}
										if (this['imageCount'] == 0) {
											sName += "<div class='coverHeader' ><a href='"+this['itemUrl']+"' target='_blank' >"+this['itemTitle']+"</a></div>";
											sName += "<div class='coverBody'>"+this['bodyDisplay']+"</div>";
										}
			   						}

			   						if (!(this['itemType'] == "Document" && this['contentType'] == "PhotoGroup")) {
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
									 //sName += "<div class='paginatedList' style='height: unset;'>--- X ---</div>";
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
										justifiedGallery : false
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
		        infinite_scroll_debouncer(infinite_scrolling_allComm, 400);
		    });
		    
			normalQtip();
			memberInfoQtip(0);
			dynamicDropDownQtip(0);
		});
		</script>
		
				
	</head>

	<body id="communityEra" style="padding: 0px;">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg parallax" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
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
							<li><a href="${communityEraContext.currentCommunityUrl}/home.do"><i class="fa fa-home" style="margin-right: 8px;"></i>Home</a></li>
							<li>
								<nav id="menu">
						    		<input type="checkbox" id="toggle-nav"/>
						    		<label id="toggle-nav-label" for="toggle-nav"><i class="fa fa-bars"></i></label>
						    		<div class="box">
							    		<ul>
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
												<c:if test="${feature == 'Library'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
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
						<div class='actions2' id='connectionInfo'>
							<div class='btngroup' >
								<c:if test="${communityEraContext.userAuthenticated}">
									<div class='btnout' >
										<a href="javascript:void(0);" class='dynaDDNoti_0' id='comaction1'
										title='community/communityOptions.ajx?currId=2001&commId=${communityEraContext.currentCommunity.id}'>Community Actions <i class="fa fa-caret-down"></i></a>
										<a href="javascript:void(0);" class='dynaDDNoti_0' id='comaction2' 
										title='community/communityOptions.ajx?currId=2001&commId=${communityEraContext.currentCommunity.id}'><i class="fa fa-th-large" style="font-size: 16px;"></i></a>
									</div>
								</c:if>
								<c:choose>
									<c:when test="${command.follower}">
										<div class='btnout' id ="subscribeCommunity" >
											<a href="javascript:void(0);" onclick="unSubscribeCommunity()">Unfollow</a>
										</div>
									</c:when>
									<c:otherwise>
										<div class='btnout' id ="subscribeCommunity" >
											<a href="javascript:void(0);" onclick="subscribeCommunity()" >Follow</a>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
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
				<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
				<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
				<input type="hidden" id="isPCommunity" name="isPCommunity" value="${command.privateCommunity}" />
				<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
				<input type="hidden" id="pgCount" value="${command.pageCount}" />
				<input type="hidden" id="currCommName" value="${command.community.name}" />
				<input type="hidden" id="CreatorName" value="${command.creator.fullName}" />
				<input type="hidden" id="CreatorID" value="${command.community.creatorId}" />
				<input type="hidden" id="CreatorPhotoPresent" value="${command.creator.photoPresent}" />
				<input type="hidden" id="CreatedOn" value="${command.createdOn}" />
				
				<div class="commSection" style="margin-top: -10px;">
					<div class="wrapper">
		                <div id="st-accordion" class="st-accordion">
		                    <ul id="accordList" class="accordList">
								<li class="paginatedList">
									<a href="#" >
										<span class="toggleHeader" style="max-width: 680px; top: -12px;">
											<span style="font-size: 20px; font-weight: bold;">${command.community.name}</span>
										</span>
										<span class="st-arrow">Open or Close</span>
									</a>
									<span class='person'>
										Started by 
										<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.community.creatorId}&backlink=ref" class='memberInfoId0'
										title="${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${command.community.creatorId}" >${command.creator.fullName}</a> on ${command.createdOn}
									</span>
									<span class='person' id="taggedKeywords" style="">
										<c:if test="${not empty command.taggedKeywords}">
										<i class='fa fa-tags' style='font-size: 14px; margin-right: 4px; color: #184A72;'></i>${command.taggedKeywords}
										</c:if>
									</span>
		                            <div class="st-content">
										<p>
											${command.community.description}
										</p>
				                    </div>
								</li>
							</ul>
						</div>
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
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=Assignment&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
										 	<c:if test="${row.itemType == 'ForumTopic'}">
										 		started <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >forum topic</a> on ${row.postedOn}
									 			<div class='entry'>
										 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=ForumTopic&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
									 		<c:if test="${row.itemType == 'BlogEntry'}">
									 			posted <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >blog entry</a> on ${row.postedOn}
										 		<div class='entry'>
										 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=BlogEntry&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
									 		<c:if test="${row.itemType == 'Event'}">
									 			created <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Event</a> on ${row.postedOn}
									 			<div class='entry'>
										 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=Event&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
									 		<c:if test="${row.itemType == 'WikiEntry'}">
									 			created <a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >Wiki article</a> on ${row.postedOn}
									 			<div class='entry'>
										 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=WikiEntry&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
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
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=Document&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
									 		<c:if test="${row.itemType == 'Document'}">
									 			added
									 			<c:choose>
													<c:when test="${row.contentType == 'Group'}">
														${row.imageCount} <a href='${communityEraContext.contextUrl}/cid/${row.communityId}/library/mediaGallery.do' >Photos</a> ${row.postedOn}
													</c:when>
													<c:otherwise>
														<a href='${communityEraContext.contextUrl}${row.itemBaseTitle}' >File</a> on ${row.postedOn}
													</c:otherwise>
												</c:choose>
									 			<div class='entry'>
										 			<a href="javascript:void(0);" class='dynaDDNoti_0' 
										 				title='pers/subscriptionOptions.ajx?commId=${row.communityId}&type=Document&userId=${row.posterid}' ><i class="fa fa-th-large"></i></a>
										 		</div>
									 		</c:if>
								 		</div>
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
												<div class='coverHeader' ><a href='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?backlink=ref&amp;id=${row.itemId}' target='_blank' >${row.itemTitle}</a></div>
												<div class="coverBody">
													${row.bodyDisplay}
												</div>
											</div>
										</c:if>
										<c:if test="${row.contentType == 'Group'}">
											<div class="pListInner" >
												<c:forEach items="${row.photoList}" var="row2" varStatus="status">
													<div class='leftImgLarge3'>
														<a href='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?backlink=ref&amp;id=${row2.docId}' 
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
																justifiedGallery : false
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
									<c:if test="${not (row.itemType == 'Document' || row.itemType == 'Photo' || row.itemType == 'Event')}">
										<c:if test="${row.imageCount > 0}">
											<div class="pListInner" >
												<div class='leftImgLarge'>
													<img src='${communityEraContext.contextUrl}/common/showImage.img?showType=t&type=${row.itemType}&itemId=${row.itemId}' />
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
									<c:if test="${not (row.itemType == 'Document' && row.contentType == 'PhotoGroup')}">
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
							 	--- X ---
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
			
			<div class="right-panel">
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
						<span onclick="return false" >Community Members</span>
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
						<a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do?backlink=ref">View All (${command.community.memberCount} Persons)</a>
					</div>
				</div>
				
				<div class="inbox">
					<div class="eyebrow">
						<span onclick="return false" >Media Gallery</span>
					</div>
					<div id="mediaList" style="padding: 4px 0px 0px 4px;" ></div>
					<div id="waitMediaMessage" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
					<div class="view" id="viewMedLis"></div>
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
				
				<c:if test="${command.community.memberShipRequestCount > 0 && command.adminMember}">
					<div class="inbox">
						<div class="eyebrow">
							<span onclick="return false" >Membership requests</span>
						</div>
						<div id="memReqList" style="padding: 4px;" ></div>
						<div id="waitMEMMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div class="view">
							<a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do?backlink=ref">View All (${command.community.memberShipRequestCount} pending requests)</a>
						</div>
					</div>
				</c:if>
				
				<div class="inbox">
					<div class="eyebrow">
						<span><i class="fa fa-tags" style="margin-right: 2px;"></i>Popular Tags</span>
					</div>
					<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
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
	</body>
</html>
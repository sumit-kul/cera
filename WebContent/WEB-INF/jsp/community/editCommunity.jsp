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
		<meta name="description" content="Jhapak, ${communityEraContext.currentCommunity.name}. ${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, blog, community, edit-community, edit community, Connections, Event, Forum, Library, File, Wiki" />
		<title>Edit ${communityEraContext.currentCommunity.name} - Jhapak</title>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<cera:taggingJS context="${communityEraContext}"/>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		
 		<script type="text/javascript" src="js/cropper.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropper.css" />
		
		<script type="text/javascript" src="js/cropbox.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropImage.css" />
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			.radio {
				display: inline-block;
				top: 6px;
			}
			            
            .modal-body {
			    padding: 0px;
			    height: 460px;
			    font-size: 14px;
				line-height: 18px;
			}
			
			.modal-footer {
			    padding: 8px;
			    text-align: right;
			    border-top: 1px solid #E5E5E5;
			}
			
			.btn {
				padding: 4px;
			}
			
			.scroll-pane
			{
				height: 430px;
				overflow: auto;
			}
			
			.scroll-pane2
			{
				height: 455px;
				overflow: auto;
			}
			
			.jspCap
			{
				display: block;
				background: #eeeef4;
			}
			
			.jspVerticalBar {
			    width: 8px;
			}
			
			.jspVerticalBar .jspCap
			{
				height: 1px;
			}
			
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            .tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
			#map_canvas {
				width: 690px;
				height: 400px;
				background-color: #CCC;
			}
			
			.login-dialog .modal-dialog {
				width: 788px;
				height: 500px;
			}
			
			.login-dialog .modal-dialog .modal-body {
				height: 500px;
			}
			
			.crop-dialog .modal-dialog {
				width: 788px;
				height: 500px;
			}
			
			.crop-dialog .modal-dialog .modal-body {
				height: 500px;
			}
						
			button.Zebra_DatePicker_Icon_Inside {
			    margin: 8px 3px 0px 0px;
			}
			
			.othralbm {
			    width: 98%;
			    background: #E6E6E6 none repeat scroll 0% 0%;
			    display: inline-block;
			    font-weight: normal;
			    font-size: 14px;
			    padding: 6px;
			    word-wrap: normal;
			    color: #333;
			    border-radius: 3px;
			    margin: 16px 13px 2px 0px;
			}
			
			.btnZoomIn .icon {
			    vertical-align: middle;
			    margin: 0px -3px;
			    height: 20px;
			    width: 20px;
			    display: inline-block;
			}
			
			.btnZoomIn .icon_zoomin {
				background-image: url("img/btn_icons.png");
			    background-position: -60px 0px;
			}
			
			.btnZoomIn .icon_zoomout {
				background-image: url("img/btn_icons.png");
			    background-position: -90px 0px;
			}
			
			.btnZoomIn .icon_rotateleft {
				background-image: url("img/btn_icons.png");
			    background-position: -120px 0px;
			}
			
			.btnZoomIn .icon_rotateright {
				background-image: url("img/btn_icons.png");
			    background-position: -150px 0px;
			}
			
			.btnZoomIn .icon_uploadfile {
			    width: 140px;
			}
			
			.btnZoomIn .icon_mediagallery {
			    width: 140px;
			}
			
			.btnZoomIn .icon_communitygallery {
			    width: 140px;
			}
			
			.btnZoomIn .icon_takephoto {
			    width: 140px;
			}
			
			.btnZoomIn .icon_editthumbnail {
			    width: 140px;
			}
			
			.login-dialog .modal-footer {
			    padding: 8px;
			    text-align: right;
			    border-top: 1px solid #E5E5E5;
			}

			.imageBox {
				position: relative;
				height: 480px;
				width: 784px;
				background: #fff;
				overflow: hidden;
				background-repeat: no-repeat;
				cursor: move;
			}
			
			.imageBox .thumbBox {
				position: absolute;
				top: 50%;
				left: 50%;
				width: 200px;
				height: 200px;
				margin-top: -100px;
				margin-left: -100px;
				box-sizing: border-box;
				border: 1px solid rgb(102, 102, 102);
				box-shadow: 0 0 0 1000px rgba(0, 0, 0, 0.5);
				background: none repeat scroll 0% 0% transparent;
			}
			
            .change-photo {
			    display: none;
			    height: 40px;
			    border: medium none;
				padding: 0px;
				-moz-appearance: none;
				display: block;
				position: absolute;
				right: 0px;
				left: 0px;
				bottom: 0px;
				background: none repeat scroll 0% 0% rgba(0, 0, 0, 0.5);
				text-align: center;
				line-height: 1;
				cursor: pointer;
			}
			
			.innerBlock .cke_reset {
			    width: 725px;
			}
			
			.tag-dialog2 .modal-dialog .modal-body {
                height: 120px;
            }
            
            .tag-dialog2 .modal-dialog .modal-body p {
            	color: #b30000;
				padding-top: 50px;
				text-align: center;
				vertical-align: sub;
				font-size: 14px;
				font-weight: bold;
            }
            
            .groups:hover .captionBottom {
			    visibility: visible;
			}
		</style>
		
		<script type="text/javascript">
			function publishCommunityChanges(){
				document.editCommunityForm.action = "${communityEraContext.requestUrl}";
				document.editCommunityForm.submit();
			}
		</script>	
		
		<script type="text/javascript">
			function showPersonalMedia(type) {
				var main = '<div id="main"><div class="lowersec"><div id="scrollpane" class="scroll-pane2"><div id="yourPhotos" class="">';
				main += '<p id="waitCropImmage" style="margin-left: 380px; margin-top: 180px; min-height:250px;" class="showCloudWaitMessage" ></p></div></div>';
				main += '</div></div>';
				var dialogInstance = BootstrapDialog.show( {
					title : 'Update Community '+type,
					message : function(dialog) {
						var $message = $(main);
						return $message;
					},
					cssClass : 'login-dialog',
					buttons: [{
		            	id: 'uploadfile',
		                label: '<span class="icon icon_uploadfile">Upload '+type+'</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
			                uploadFileFromMyPictures(type);
	                	}
		            },{
		            	id: 'communityGallery',
		                label: '<span class="icon icon_communitygallery">My Media Gallery</span>',
		                cssClass: 'btn-primary btnZoomIn',
		                action: function(dialog){
		                	showPersonalMedia(type);
		                	dialog.close();
	                	}
		            },{
		            	id: 'mediaGallery',
		                label: '<span class="icon icon_mediagallery">Community Gallery</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
		                	showCommunityMedia(type);
		                	dialog.close();
	                	}
		            },{
		            	id: 'takePhoto',
		                label: '<span class="icon icon_takephoto">Take Photo</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
	                	}
		            },{
		            	id: 'editThumbnail',
		                label: '<span class="icon icon_editthumbnail">Edit Thumbnail</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
		                	uploadMediaToAddEdit('0', '0', type);
	                	}
		            }],
					onshown : function(dialogRef) {
						var ownerId = document.getElementById('currentUserId').value;
						$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaToEdit.ajx?ownerId="+ownerId+"&type=media" ,dataType: "json",success:function(result){
							var sName = "";
							$.each(result.pList, function() {
								var albId = this['albId'];
								sName += "<div class='othralbm'>"+this['albTitle']+" ("+this['photoCount']+")</div>";
								sName += "<div id='demo-test-gallery_"+albId+"' class='demo-gallery'>";
								$.each(this.aData, function() {
									sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+albId+", "+this['id']+", &#39;"+type+"&#39;);' id='"+albId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
									 sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+albId+"_"+this['id']+"' alt='"+this['title']+"'/>";
									 sName +="<figure>"+this['title']+"</figure></a>";
								 });
								sName += "</div>";
								if (this['photoRemain'] > 0){
									sName += "<span id='seeMore_"+albId+"'><a href='javascript:void(0);' onclick='seeMoreMedia(&#39;"+albId+"&#39;, &#39;"+this['pNumber']+"&#39;, &#39;"+type+"&#39;);'>See more</a></span>";
								}
									
							});
							dialogRef.getModalBody().find('#yourPhotos').html(sName);
							$("#waitCropImmage").hide();
							dialogRef.getModalBody().find('.demo-gallery').each(function (i, el) {
								$(el).justifiedGallery({lastRow : 'nojustify', 
									rowHeight: 100, 
									fixedHeight : false, 
									captions : false, 
									margins : 4,
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
	
							$('.scroll-pane2').jScrollPane(
				        		{
				        			autoReinitialise: true
				        		});
							}
						});
					}
				});
			}

			function uploadMediaToAddEdit (albId, phId, type){
				var ownerId = document.getElementById('currentUserId').value;
				var communityId = document.getElementById('commId').value;
				var mainfiles = [];
				var cropper;
				var key = 'image_'+albId+'_'+phId;
				
		    	var main2 = '<div id="main2"><div class="container"><div class="imageBox"><img src="" id="imageBoxSrc" style="display: none;"></div>';
		    	main2 += '<div class="cropped"></div></div></div>';

				var dialogInstance = BootstrapDialog.show({
					title: 'Crop Picture',
					message: function(dialog) {
					var $message = $(main2);
	                return $message;
	            },
	            buttons: [{
	            	id: 'btnZoomOut',
	                label: '<span class="icon icon_zoomout"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog){
	            		cropper.cropper('zoom', -0.1);
                	}
	            }, {
	            	id: 'btnZoomIn',
	                label: '<span class="icon icon_zoomin"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog) {
	            		cropper.cropper('zoom', 0.1);
	                }
	            },
	            {
	            	id: 'rotateLeft',
	                label: '<span class="icon icon_rotateleft"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog){
	            		cropper.cropper('rotate', -45);
                	}
	            }, {
	            	id: 'rotateRight',
	                label: '<span class="icon icon_rotateright"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog) {
	            		cropper.cropper('rotate', 45);
	                }
	            },
	            	{
	            	id: 'btnCrop',
	                label: 'Crop & Save',
	                cssClass: 'btn-primary',
	                action: function(dialog){
		            	dialog.getButton('btnCrop').disable();
		            	dialog.getButton('btnCrop').spin();
		            	dialog.getButton('rotateRight').hide();
		            	dialog.getButton('rotateLeft').hide();
		            	dialog.getButton('btnZoomIn').hide();
		            	dialog.getButton('btnZoomOut').hide();
	            		cropper.cropper('getCroppedCanvas').toBlob(function (blob) {
	            			  var data = new FormData();
	            			  data.append('file', blob);
	            			  data.append('communityId', communityId);
	            			  $.ajax({url:"${communityEraContext.contextUrl}/cid/"+communityId+"/community/commLogoAddUpdate.img?type="+type,
	            			    method: "POST",
	            			    data: data,
	            			    processData: false,
	            			    contentType: false,
	            			    success: function () {
	            				  dialog.close();
	            				  location.reload();
	            			    }
	            			  });
	            			});
	            	}
	            }],
	            cssClass: 'crop-dialog',
	            onshown: function(dialogRef){
	            	var src = '';
	            	if(phId > 0){
	            		src = $('#'+key).attr('src');
					} else {
						src = '${communityEraContext.contextUrl}/commLogoDisplay.img?type=editThimbnail&imgType='+type+'&communityId='+communityId;
					}
			    	dialogRef.getModalBody().find('#imageBoxSrc').attr("src",src);
	            	var $image = $('#imageBoxSrc'),
				    cropBoxData,
				    canvasData;
	            	if(type == "Banner") {
	            		cropper = $image.cropper({
	            			autoCropArea: 1.0,
			            	aspectRatio: 34 / 9,
		            		modal: true,
		            		dragCrop: false,
		            		movable: true,
		            		highlight: true,
		            		cropBoxResizable: false,
	            		    built: function () {
	            		      // Strict mode: set crop box data first
	            		      $image.cropper('setCropBoxData', cropBoxData);
	            		      $image.cropper('setCanvasData', canvasData);
	            		    }
	            		});
          		} else {
          			cropper = $image.cropper({
	            		    autoCropArea: 0.5,
		            		aspectRatio: 1 / 1,
		            		modal: true,
		            		dragCrop: false,
		            		movable: true,
		            		cropBoxResizable: false,
	            		    built: function () {
	            		      // Strict mode: set crop box data first
	            		      $image.cropper('setCropBoxData', cropBoxData);
	            		      $image.cropper('setCanvasData', canvasData);
	            		    }
	            		});
          		}
	            	},
		            onshow: function(dialogRef){
	            		$("#waitCropImmage").hide();
		            }
		        });
			}

			function showCommunityMedia(type) {
				var main = '<div id="main"><div class="lowersec"><div id="scrollpane" class="scroll-pane2"><div id="yourPhotos" class="">';
				main += '<p id="waitCropImmage" style="margin-left: 380px; margin-top: 180px; min-height:250px;" class="showCloudWaitMessage" ></p></div></div>';
				main += '</div></div>';
				var dialogInstance = BootstrapDialog.show( {
					title : 'Update Community '+type,
					message : function(dialog) {
						var $message = $(main);
						return $message;
					},
					cssClass : 'login-dialog',
					buttons: [{
		            	id: 'uploadfile',
		                label: '<span class="icon icon_uploadfile">Upload '+type+'</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
			                uploadFileFromMyPictures(type);
	                	}
		            },{
		            	id: 'mediaGallery',
		                label: '<span class="icon icon_mediagallery">My Media Gallery</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
		                	showPersonalMedia(type);
		                	dialog.close();
	                	}
		            },{
		            	id: 'communityGallery',
		                label: '<span class="icon icon_communitygallery">Community Gallery</span>',
		                cssClass: 'btn-primary btnZoomIn',
		                action: function(dialog){
		                	showCommunityMedia(type);
		                	dialog.close();
	                	}
		            },{
		            	id: 'takePhoto',
		                label: '<span class="icon icon_takephoto">Take Photo</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
	                	}
		            },{
		            	id: 'editThumbnail',
		                label: '<span class="icon icon_editthumbnail">Edit Thumbnail</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
		                	uploadMediaToAddEdit('0', '0', type);
	                	}
		            }],
					onshown : function(dialogRef) {
						var ownerId = document.getElementById('currentUserId').value;
						$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaToEdit.ajx?ownerId="+ownerId+"&type=community" ,dataType: "json",success:function(result){
							var sName = "";
							$.each(result.pList, function() {
								var libId = this['libId'];
								sName += "<div class='othralbm'>"+this['communityName']+" ("+this['photoCount']+")</div>";
								sName += "<div id='demo-test-gallery_"+libId+"' class='demo-gallery'>";
								$.each(this.aData, function() {
									sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+libId+", "+this['id']+",&#39;"+type+"&#39;);' id='"+libId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
									sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?mediaId="+this['id']+"' id='image_"+libId+"_"+this['id']+"' alt='"+this['title']+"'/>";
									sName +="<figure>"+this['title']+"</figure></a>";
								 });
								sName += "</div>";
								if (this['photoRemain'] > 0){
									sName += "<span id='seeMore_"+libId+"'><a href='javascript:void(0);' onclick='seeMoreCommunityMedia(&#39;"+libId+"&#39;, &#39;"+this['pNumber']+"&#39;,&#39;"+type+"&#39;);'>See more</a></span>";
								}
									
							});
							dialogRef.getModalBody().find('#yourPhotos').html(sName);
							dialogRef.getModalBody().find('.demo-gallery').each(function (i, el) {
								$(el).justifiedGallery({lastRow : 'nojustify', 
									rowHeight: 100, 
									fixedHeight : false, 
									captions : false, 
									margins : 4,
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

							$('.scroll-pane2').jScrollPane(
				        		{
				        			autoReinitialise: true
				        		});
							}
						});
					}
				});
			}

			function seeMoreMedia(albId, pNumber, type) 
			{
				var pNum = +pNumber + 1;
				var ownerId = document.getElementById('currentUserId').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaToEdit.ajx?ownerId="+ownerId+
					"&albumId="+albId+"&page="+pNum+"&type=media" ,dataType: "json",success:function(result){
					var sName = "";
					var lName = "";
					$.each(result.pList, function() {
						var albId = this['albId'];
						var pNumber = this['pNumber'];
						$.each(this.aData, function() {
							//alert(et.title);
							sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+albId+", "+this['id']+", &#39;"+type+"&#39;);' id='"+albId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
							 sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+albId+"_"+this['id']+"' alt='"+this['title']+"'/>";
							 sName +="<figure>"+this['title']+"</figure></a>";
						 });
						sName += "</div>";
						if (this['photoRemain'] > 0){
							lName = "<a href='javascript:void(0);' onclick='seeMoreMedia("+albId+", "+pNumber+", &#39;"+type+"&#39;)'>See more</a>";
						}
							
					});
					$('#demo-test-gallery_'+albId).append(sName);
					$('#seeMore_'+albId).html(lName);
					$('#demo-test-gallery_'+albId).each(function (i, el) {
						$(el).justifiedGallery({lastRow : 'nojustify', 
							rowHeight: 100, 
							fixedHeight : false, 
							captions : false, 
							margins : 4,
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

					$('.scroll-pane2').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
					}
				});
			}

			function seeMoreCommunityMedia(libId, pNumber, type) 
			{
				var pNum = +pNumber + 1;
				var ownerId = document.getElementById('currentUserId').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showMediaToEdit.ajx?ownerId="+ownerId+
					"&libraryId="+libId+"&page="+pNum+"&type=community" ,dataType: "json",success:function(result){
					var sName = "";
					var lName = "";
					$.each(result.pList, function() {
						var libId = this['libId'];
						var pNumber = this['pNumber'];
						$.each(this.aData, function() {
							sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+libId+", "+this['id']+", &#39;"+type+"&#39;);' id='"+libId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
							 sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?mediaId="+this['id']+"' id='image_"+libId+"_"+this['id']+"' alt='"+this['title']+"'/>";
							 sName +="<figure>"+this['title']+"</figure></a>";
						 });
						sName += "</div>";
						if (this['photoRemain'] > 0){
							lName = "<a href='javascript:void(0);' onclick='seeMoreCommunityMedia("+libId+", "+pNumber+", &#39;"+type+"&#39;)'>See more</a>";
						}
							
					});
					$('#demo-test-gallery_'+libId).append(sName);
					$('#seeMore_'+libId).html(lName);
					$('#demo-test-gallery_'+libId).each(function (i, el) {
						$(el).justifiedGallery({lastRow : 'nojustify', 
							rowHeight: 100, 
							fixedHeight : false, 
							captions : false, 
							margins : 4,
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

					$('.scroll-pane2').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
					}
				});
			}
	
		    function uploadFileFromMyPictures(type) 
			{ 
		    	var mainfiles = [];
		    	var cropper = "";
		    	var main2 = '<div id="main2">';
		    	main2 += '<div class="container"><div class="imageBox"><img src="" id="imageBoxSrc" style="display: none;"></div>';
		    	main2 += '<div class="cropped"></div><input type="file" id="file" style="display: none" /></div></div>';

				var dialogInstance = BootstrapDialog.show({
					title: 'Crop '+type,
					message: function(dialog) {
					var $message = $(main2);
	                return $message;
	            },
	            buttons: [{
	            	id: 'btnZoomOut',
	                label: '<span class="icon icon_zoomout"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog){
	            		cropper.cropper('zoom', -0.1);
                	}
	            }, {
	            	id: 'btnZoomIn',
	                label: '<span class="icon icon_zoomin"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog) {
	            		cropper.cropper('zoom', 0.1);
	                }
	            },
	            {
	            	id: 'rotateLeft',
	                label: '<span class="icon icon_rotateleft"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog){
	            		cropper.cropper('rotate', -45);
                	}
	            }, {
	            	id: 'rotateRight',
	                label: '<span class="icon icon_rotateright"></span>',
	                cssClass: 'btn-primary btnZoomIn',
	                action: function(dialog) {
	            		cropper.cropper('rotate', 45);
	                }
	            },
	            	{
	            	id: 'btnCrop',
	                label: 'Done',
	                cssClass: 'btn-primary',
	                action: function(dialog){
		            	dialog.getButton('btnCrop').disable();
	            		dialog.getButton('btnCrop').spin();
		            	dialog.getButton('rotateRight').hide();
		            	dialog.getButton('rotateLeft').hide();
		            	dialog.getButton('btnZoomIn').hide();
		            	dialog.getButton('btnZoomOut').hide();
	            		var communityId = document.getElementById('commId').value;	
		            	var data2 = new FormData();
		                $.each(mainfiles, function(key, value)
		                {
		                	data2.append('file', value.file[0]);
		                    data2.append('fileName', value.file[0].name);
		                    data2.append('type', 'community');
		                    data2.append('communityId', communityId);
		                    $.ajax({url:"${communityEraContext.contextUrl}/pers/addPhotoInAlbum.img?type=community&imgType="+type+"&photoId=0&communityId="+communityId,
		            	  		data: data2,
		            	  		cache: false,
		            	        dataType: 'json',
							    processData: false,  // do not process the data as url encoded params
							    contentType: false,   // by default jQuery sets this to urlencoded string
							    type: 'POST',
								success:function(result){
							    },
						         beforeSend: function () {
							    	mainfiles = [];
						         } 
				    	  	});
		                });
	            		cropper.cropper('getCroppedCanvas').toBlob(function (blob) {
	            			  var data = new FormData();
	            			  data.append('file', blob);
	            			  data.append('communityId', communityId);
	            			  $.ajax({url:"${communityEraContext.contextUrl}/cid/"+communityId+"/community/commLogoAddUpdate.img?type="+type,
	            			    method: "POST",
	            			    data: data,
	            			    processData: false,
	            			    contentType: false,
	            			    success: function () {
	            				  dialog.close();
	            				  location.reload();
	            			    }
	            			  });
	            			});
	            	}
	            }],
	            cssClass: 'crop-dialog',
	            onshown: function(dialogRef){
	            	var ntype = type;
	            	$(dialogRef.getModalBody().find('#file')).change(function(event) {
	            		var $image = dialogRef.getModalBody().find('#imageBoxSrc'),
	            		cropBoxData,
					    canvasData;
	            		object = {};
                	 	object.file = event.target.files;
                	 	var size = 0;
                	 	if (typeof (event.target.files[0]) != "undefined") {
                            size = parseFloat(event.target.files[0].size / (1024*1024)).toFixed(2);
                        } else {
                            var outdai = dialogRef;
                        	var type = BootstrapDialog.TYPE_WARNING;
        			    	BootstrapDialog.show({
        		                type: type,
        		                title: 'Warning',
        		                message: '<p>This browser does not support HTML5.<br/>Please note larger file (> 2 MB) is not allowed.</p>',
        		                cssClass: 'tag-dialog2',
        		                closable: true,
        		                closeByBackdrop: true,
        		                closeByKeyboard: true,
        		                draggable: true,
        		                buttons: [{
        		                	label: 'Close',
        		                    action: function(dialogRef2){
        		                        dialogRef.close();
        		                    }
        		                }]
        		            });
                        }
                	 	if (size < 2) {
                	 	mainfiles.push(object);
	            		var reader = new FileReader();
	                     reader.onload = function(e) {
	                    	 $image.attr("src",e.target.result);
	                    	 if(ntype == "Banner") {
		 		            		cropper = $image.cropper({
		 		            		    autoCropArea: 1.0,
		 			            		aspectRatio: 34 / 9,
		 			            		modal: true,
		 			            		dragCrop: false,
		 			            		movable: true,
		 			            		highlight: true,
		 			            		cropBoxResizable: false,
		 		            		    built: function () {
		 		            		      // Strict mode: set crop box data first
		 		            		      $image.cropper('setCropBoxData', cropBoxData);
		 		            		      $image.cropper('setCanvasData', canvasData);
		 		            		    }
		 		            		});
		 	            		} else {
		 	            			cropper = $image.cropper({
		 		            		    autoCropArea: 0.5,
		 			            		aspectRatio: 1 / 1,
		 			            		modal: true,
		 			            		dragCrop: false,
		 			            		movable: true,
		 			            		cropBoxResizable: false,
		 		            		    built: function () {
		 		            		      // Strict mode: set crop box data first
		 		            		      $image.cropper('setCropBoxData', cropBoxData);
		 		            		      $image.cropper('setCanvasData', canvasData);
		 		            		    }
		 		            		});
		 	            		}
	                     }
	                     reader.readAsDataURL(this.files[0]);
	                     this.files = [];
                	 	} else {
                	 		var type = BootstrapDialog.TYPE_DANGER;
        			    	BootstrapDialog.show({
        		                type: type,
        		                title: 'Error',
        		                message: '<p>Larger file (> 2 MB) is not allowed. Please try another file.</p>',
        		                cssClass: 'tag-dialog2',
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
        			    	 dialogRef.close();
                	 	}
                	});
	            },
	            onshow: function(dialogRef){
	            	dialogRef.getModalBody().find('#file').click();
	            }
		        });
			}
		</script>	
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function () {
	        	
				normalQtip();
				dynamicDropDownQtip();
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.comm.id}",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
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
			        normalQtip();
					memberInfoQtip();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitBLAthMessage").show();
			         } 
			    });
			});
		</script>	
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
				<div class="innerPanl">
										
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
						<c:choose>
							<c:when test="${communityEraContext.currentCommunity.bannerPresent}">	
								<a href="javascript:void(0);" onclick="showCommunityMedia('Banner');" class="photocontainer" 
									style="text-decoration: none;">Update Community Banner</a>	
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);" onclick="showCommunityMedia('Banner');" class="photocontainer" 
									style="text-decoration: none;">Add Community Banner</a>
							</c:otherwise>
						</c:choose>
					</div><!-- /container -->
					
					<div class="groups">
						<div class="picture">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="290" height="290" /> 
									<div class="captionBottom" onclick="showCommunityMedia('Logo');" style="height: 290px; top: -290px;">
										<span><i class="fa fa-camera" aria-hidden="true" style="margin-top: 110px;"></i></span>
									</div>
								</c:when>
								<c:otherwise>
									<img src="img/community_Image.png" id="photoImg" width="290" height="290" />
									<div class="captionBottom" onclick="showCommunityMedia('Logo');" style="height: 290px; top: -290px;">
										<span><i class="fa fa-camera" aria-hidden="true" style="margin-top: 110px;"></i></span>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div class="left-panel">
				<input type="hidden" id="commId" value="${communityEraContext.currentCommunity.id}" />
				<input type="hidden" id="currentUserId" value="${communityEraContext.currentUser.id}" />
				
				<div class="commSection">
					<div class="inputForm">
						<div class="intHeaderMain">
							Edit <span>${communityEraContext.currentCommunity.name}</span>
						</div>
						<div class="innerBlock" style="display: inherit;">
							<form:form showFieldErrors="true" multipart="true" name="editCommunityForm">
								<form:errors message="Please correct the errors below" cssClass="errorText" /> 
								<div>
									<label for="name" class="newCommLabel">Name (100 characters max.) <img src="img/required.gif" title="This field is required" width="8" height="8" class="required normalTip" /></label><br />
									<form:input id="name" path="name" maxlength="100" cssClass="editor" ></form:input>
									<form:hidden path="id" />
									<form:hidden path="communityType" />
									<form:hidden path="showCommunityType" />
								</div>
								<br />
								<div style="width: 690px;">
									<label for="welcomeText" class="newCommLabel">Introductory text (500 characters max.) <img src="img/required.gif" title="This field is required" width="8" height="8" class="required normalTip" /></label>
									<form:textarea id="welcomeText" path="welcomeText" maxlength="500" rows="14" tabindex="1" cssClass="newCommTextArea editor"  cssStyle="height: 150px;"/>
									<br/> <br/>
											
									<label for="communityType" class="newCommLabel">Type of community <img src="img/required.gif" title="This field is required" width="8" height="8" class="required normalTip" /></label>
									<div style="display:inline;">
										<c:if test='${command.communityType == "Private"}'>
											<form:radiolist path="showCommunityType" cssClass="radio" fieldLabel="" wrapperTag="div" cssStyle="width: 20px;">
												<form:option value="${command.showCommunityType}" />
											</form:radiolist>	
											<br /><br />
											<a href="${communityEraContext.currentCommunityUrl}/community/changeToProtected.do?id=${communityEraContext.currentCommunity.id}" class="btnmain" style="float: right; position: relative; top: -30px; right: -40px;">Make community protected</a>
											<a href="${communityEraContext.currentCommunityUrl}/community/changeToPublic.do?id=${communityEraContext.currentCommunity.id}" class="btnmain" style="float: right; position: relative; top: -30px; right: -40px;">Make community public</a>
										</c:if>
										<c:if test='${command.communityType == "Protected"}'>
											<form:radiolist path="showCommunityType" cssClass="radio" fieldLabel="" wrapperTag="div" cssStyle="width: 20px;">
												<form:option value="${command.showCommunityType}" />
											</form:radiolist>	
											<a href="${communityEraContext.currentCommunityUrl}/community/changeToPublic.do?id=${communityEraContext.currentCommunity.id}" class="btnmain" style="float: right; position: relative; top: -30px; right: -40px;">Make community public</a>
										</c:if>
										<c:if test='${command.communityType == "Public"}'>
											<form:radiolist path="showCommunityType" cssClass="radio" fieldLabel="" wrapperTag="div" cssStyle="width: 20px;">
												<form:option value="${command.showCommunityType}" />
											</form:radiolist>
										</c:if>
									</div>
									<br/>
									
									<label for="description" class="newCommLabel">Description</label>
									<form:textarea cssClass="ckeditor" path="description" id="description"></form:textarea>
								</div>
								<br/>
								<div style="width:100%; display: inline-block;">
									<a href="javascript:void(0);" onclick="publishCommunityChanges();" class="btnmain" style="float:right;"><i class="fa fa-check" style="margin-right: 4px;"></i>Update</a>
									<a href="${communityEraContext.currentCommunityUrl}/home.do" class="btnmain" style="float:right; margin-right: 8px;"><i class="fa fa-times" style="margin-right: 4px;"></i>Cancel</a>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div> 
			
			<div class="right-panel">
				<div class="inbox">
					<div class="eyebrow">
						<span onclick="return false" title="Community Members" class="normalTip">Community Members</span>
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
						<a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do?backlink=ref">View All (${command.comm.memberCount} Persons)</a>
					</div>
				</div>
					
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
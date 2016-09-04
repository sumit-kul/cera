<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${command.firstName} ${command.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		
		<script>UPLOADCARE_PUBLIC_KEY = "2e593929b451359708cc";</script>
		<script src="https://ucarecdn.com/widget/2.8.2/uploadcare/uploadcare.full.min.js" charset="utf-8"></script>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.firstName} ${command.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>

		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
		
		<link rel="stylesheet" href="css/DatePicker/default.css" type="text/css" />
        <link type="text/css" rel="stylesheet" href="css/DatePicker/shCoreDefault.css" />
        <script type="text/javascript" src="js/DatePicker/XRegExp.js"></script>
        <script type="text/javascript" src="js/DatePicker/shCore.js"></script>
        <script type="text/javascript" src="js/DatePicker/shLegacy.js"></script>
        <script type="text/javascript" src="js/DatePicker/shBrushJScript.js"></script>
        <script type="text/javascript" src="js/DatePicker/shBrushXml.js"></script>
        <script type="text/javascript" src="js/DatePicker/zebra_datepicker.js"></script>
        <script type="text/javascript" src="js/DatePicker/core.js"></script>
        <script type="text/javascript" src="js/dateFormat.js"></script>
		
		<script type="text/javascript" src="js/cropper.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropper.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/cropImage.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
		
		<style>
		    .abtMe .actions3 {
		    	position: absolute;
		    	top: 150px;
				right: 430px;
		    }
		    
		    .abtMe .actions3 a {
			    position: relative;
			    display: inline;
			    clear: none;
			    margin: 0 5px 0 0;
			}
			
			#map_canvas {
				width: 690px;
				height: 400px;
				background-color: #CCC;
			}
			
			.login-dialog .modal-dialog {
				width: 788px;
				height: 480px;
			}
			
			.modal-body {
				padding: 0px;
				height: 440px;
				font-size: 14px;
				line-height: 18px;
			}
			
			.login-dialog .modal-dialog .modal-body {
				height: 480px;
			}
			
			.crop-dialog .modal-dialog {
				width: 788px;
				height: 500px;
			}
			
			.crop-dialog .modal-dialog .modal-body {
				height: 500px;
			}
			
			.modal-footer {
				padding: 8px;
				text-align: right;
				border-top: 1px solid #E5E5E5;
			}
			
			.btn {
				padding: 4px;
			}
			
			.scroll-pane {
				height: 430px;
				overflow: auto;
			}
			
			.scroll-pane2 {
				height: 460px;
				overflow: auto;
			}
			
			.jspCap {
				display: block;
				background: #eeeef4;
			}
			
			.jspVerticalBar {
				width: 8px;
			}
			
			.jspVerticalBar .jspCap {
				height: 1px;
			}
			
			.imageBox {
				position: relative;
				height: 500px;
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
			
			.tag-dialog .modal-dialog .modal-body {
                height: 120px;
            }
            
            .tag-dialog .modal-dialog .modal-body p {
            	color: #b30000;
				padding-top: 50px;
				text-align: center;
				vertical-align: sub;
				font-size: 14px;
				font-weight: bold;
            }
		</style>
		
		<script type="text/javascript">
			var placeSearch, autocomplete;
			var componentForm = {
					locality: 'long_name',
					postal_code: 'long_name',
					administrative_area_level_1: 'long_name',
			  		country: 'long_name'
			};
			
	        function initialize() {
	    	  autocomplete = new google.maps.places.Autocomplete((document.getElementById('location')),
	    	      { types: [] });
	    	  google.maps.event.addListener(autocomplete, 'place_changed', function() {
	    	    fillInAddress();
	    	  });
	    	}

	        function fillInAddress() {
	    	  // Get the place details from the autocomplete object.
	    	  var place = autocomplete.getPlace();
	    	  var placeLoc = place.geometry.location;
	    	  document.getElementById('latlng').value = placeLoc;
	    	  var add = place.formatted_address;
	    	  document.getElementById('address').value = add;
	    	  
	    	  // Get each component of the address from the place details
	    	  // and fill the corresponding field on the form.
	    	  for (var i = 0; i < place.address_components.length; i++) {
	    	    var addressType = place.address_components[i].types[0];
	    	    if (componentForm[addressType]) {
	    	      var val = place.address_components[i][componentForm[addressType]];
	    	      document.getElementById(addressType).value = val;
	    	    }
	    	  }
	    	}
	    </script>
        
		<script type="text/javascript">
			function showPersonalMedia(type) {
				var main = '<div id="main"><div class="lowersec"><div id="scrollpane" class="scroll-pane2"><div id="yourPhotos" class="">';
				main += '<p id="waitCropImmage" style="margin-left: 380px; margin-top: 180px; min-height:250px;" class="showCloudWaitMessage" ></p></div></div>';
				main += '</div></div>';
				var dialogInstance = BootstrapDialog.show( {
					title : 'Update your Profile '+type,
					message : function(dialog) {
						var $message = $(main);
						return $message;
					},
					cssClass : 'login-dialog',
					buttons: [{
		            	id: 'uploadfile',
		                label: '<span class="icon icon_uploadfile">Upload Photo</span>',
		                cssClass: 'btn-default btnZoomIn',
		                action: function(dialog){
			                	uploadFileFromMyPictures(type);
	                	}
		            },{
		            	id: 'mediaGallery',
		                label: '<span class="icon icon_mediagallery">My Media Gallery</span>',
		                cssClass: 'btn-primary btnZoomIn',
		                action: function(dialog){
		                	showPersonalMedia(type);
		                	dialog.close();
	                	}
		            },{
		            	id: 'communityGallery',
		                label: '<span class="icon icon_communitygallery">Community Gallery</span>',
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
				var mainfiles = [];
				var cropper;
				var key = 'image_'+albId+'_'+phId;
				
		    	var main2 = '<div id="main2"><div class="container"><div class="imageBox"><img src="" id="imageBoxSrc" style="display: none"></div>';
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
	                autospin: false,
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
	            			  $.ajax({url:"${communityEraContext.contextUrl}/pers/registeruserPhoto.img?type="+type,
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
	            	if(phId > 0){
	            		src = $('#'+key).attr('src');
					} else {
						src = '${communityEraContext.contextUrl}/pers/userPhoto.img?type=editThimbnail&imgType='+type+'&id='+ownerId;
					}
	            	dialogRef.getModalBody().find('#imageBoxSrc').attr("src",src);
	            	var $image = $('#imageBoxSrc'),
				    cropBoxData,
				    canvasData;
               	 if(type == "Cover") {
	            		cropper = $image.cropper({
	            		    autoCropArea: 0.8,
		            		aspectRatio: 24 / 9,
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
		            }
		        });
			}
			
			function showCommunityMedia(type) {
				var main = '<div id="main"><div class="lowersec"><div id="scrollpane" class="scroll-pane2"><div id="yourPhotos" class="">';
				main += '<p id="waitCropImmage" style="margin-left: 380px; margin-top: 180px; min-height:250px;" class="showCloudWaitMessage" ></p></div></div>';
				main += '</div></div>';
				var dialogInstance = BootstrapDialog.show( {
					title : 'Update your Profile '+type,
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
									sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+libId+", "+this['id']+", &#39;"+type+"&#39;);' id='"+libId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
									 sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?mediaId="+this['id']+"' id='image_"+libId+"_"+this['id']+"' alt='"+this['title']+"'/>";
									 sName +="<figure>"+this['title']+"</figure></a>";
								 });
								sName += "</div>";
								if (this['photoRemain'] > 0){
									sName += "<span id='seeMore_"+libId+"'><a href='javascript:void(0);' onclick='seeMoreCommunityMedia(&#39;"+libId+"&#39;, &#39;"+this['pNumber']+"&#39;, "+type+");'>See more</a></span>";
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
							sName += "<a href='javascript:void(0);' onclick='uploadMediaToAddEdit("+albId+", "+this['id']+", &#39;"+type+"&#39;);' id='"+albId+"_"+this['id']+"' data-size='"+this['width']+"x"+this['height']+"' >";
							 sName += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['id']+"' id='image_"+albId+"_"+this['id']+"' alt='"+this['title']+"'/>";
							 sName +="<figure>"+this['title']+"</figure></a>";
						 });
						sName += "</div>";
						if (this['photoRemain'] > 0){
							lName = "<a href='javascript:void(0);' onclick='seeMoreMedia("+albId+", "+pNumber+", "+type+")'>See more</a>";
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
		    	main2 += '<div class="container"><div class="imageBox"><img src="" id="imageBoxSrc" style="display: none" /></div>';
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
	                autospin: false,
	                cssClass: 'btn-primary',
	                action: function(dialog){
	            		dialog.getButton('btnCrop').disable();
	            		dialog.getButton('btnCrop').spin();
		            	dialog.getButton('rotateRight').hide();
		            	dialog.getButton('rotateLeft').hide();
		            	dialog.getButton('btnZoomIn').hide();
		            	dialog.getButton('btnZoomOut').hide();
		            	var data2 = new FormData();
		                $.each(mainfiles, function(key, value)
		                {
			                var imgg = value.file[0].size;
		                    data2.append('file', value.file[0]);
		                    data2.append('fileName', value.file[0].name);
		                    data2.append('type', 'profile');
		                    $.ajax({url:"${communityEraContext.contextUrl}/pers/addPhotoInAlbum.img?type=profile&imgType="+type+"&photoId=0",
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
	            			  $.ajax({url:"${communityEraContext.contextUrl}/pers/registeruserPhoto.img?type="+type,
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
                        	var btype = BootstrapDialog.TYPE_WARNING;
        			    	BootstrapDialog.show({
        		                type: btype,
        		                title: 'Warning',
        		                message: '<p>This browser does not support HTML5.<br/>Please note larger file (> 2 MB) is not allowed.</p>',
        		                cssClass: 'tag-dialog',
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
	                    	 if(ntype == "Cover") {
	 		            		cropper = $image.cropper({
	 		            		    autoCropArea: 0.8,
	 			            		aspectRatio: 24 / 9,
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
			function doneAboutMe(){
				var about = document.getElementById('editAboutMe').value;
				about = about.replace(new RegExp('\r?\n','g'), '<br />');
				alert(about);
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateProfile.ajx?section=aboutMe",
					data: {about:about},method: "POST",dataType: "json",success:function(result){
					var caboutMe = about.replace('<br />', '\n');
					document.getElementById('captionHeader').innerHTML = caboutMe;
				    }
			    });
				cancelAboutMe();
			}

			function editAboutMe(){
				document.getElementById('editAboutMe').style.display = 'inline';
				document.getElementById('btncnl').style.display = 'inline';
				document.getElementById('btndon').style.display = 'inline';
				document.getElementById('captionHeader').style.display = 'none';
				document.getElementById('editAboutMebtn').style.display = 'none';
				document.getElementById('captionHeader').style.display = 'none';
			}

			function cancelAboutMe(){
				document.getElementById('editAboutMe').style.display = 'none';
				document.getElementById('btncnl').style.display = 'none';
				document.getElementById('btndon').style.display = 'none';
				document.getElementById('captionHeader').style.display = 'inline-block';
				$("#editAboutMebtn").css("display", "inline");
			}

			function doneSignIn(){
				var profileName = document.getElementById('profileName').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateProfile.ajx?section=signIn"+"&profileName="+profileName,method: "POST" ,dataType: "json"
					,success:function(result){
					$("#waitSignIn").hide();
					if(result.iserror){
						if (result.profileNameError) {
							$("#checkPAvail").html(result.profileNameError);
						} 
					} else {
						$("#sProfileName").html(profileName);
						cancelSignIn(profileName);
					}
				    },beforeSend: function () {
			            $("#waitSignIn").show();
			         }
			    });
			}
			
			function cancelSignIn(profileName){
				document.getElementById('editSignIn').style.display = 'inline';
				document.getElementById('editSignInbtn').style.display = 'inline';

				document.getElementById('formSignIn').style.display = 'none';
				document.getElementById('cancelSignInbtn').style.display = 'none';
				document.getElementById('doneSignbtn').style.display = 'none';

				document.getElementById('checkPAvail').innerHTML = '';
				document.getElementById('checkAvail').innerHTML = '';
				$("#profileName").val(profileName);
			}

			function checkProfileNameAvailability(){
				var pName = document.getElementById('profileName').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/checkProfileNameAvailability.ajx?pName="+pName,success:function(result){
					$("#checkPAvail").html(result);
			        $("#waitMessage2").hide();
				    },
			         beforeSend: function () {
				    	$("#checkPAvail").html('');
			             $("#waitMessage2").show();
			         } 
		    	  });
			}

			function editPersonal(){
				document.getElementById('editPersonal').style.display = 'none';
				document.getElementById('editPersonalbtn').style.display = 'none';

				document.getElementById('formPersonal').style.display = 'inline-block';
				document.getElementById('cancelPersonalbtn').style.display = 'inline';
				document.getElementById('donePersonalbtn').style.display = 'inline';
				var datepicker = document.getElementById('datepicker-example1').value;
				if(datepicker != ""){
					var isoDate2 = new Date(datepicker).format("isoDate");
					$("#datepicker-example1").val(isoDate2);
				}
			}

			function donePersonal(){
				var profileName = document.getElementById('profileName').value;
				var firstName = document.getElementById('firstName').value;
				var lastName = document.getElementById('lastName').value;
				var relationship = document.getElementById('relationship').value;
				var relationshipLabel = $('#relationship option:selected').text()
				var datepicker = document.getElementById('datepicker-example1').value;
				var isoDate;
				var now;
				if(datepicker != ""){
					isoDate = new Date(datepicker).format("isoDate");
					now = new Date(datepicker).format("mediumDate");
				}
				
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateProfile.ajx?section=personal&profileName="+profileName+"&firstName="+firstName+"&lastName="
					+lastName+"&dob="+isoDate+"&relationship="+relationship ,
					dataType: "json",success:function(result){
					$("#waitPersonal").hide();
					if(result.iserror){
						if (result.profileNameError) {
							$("#checkPAvail").html(result.profileNameError);
						}
						if (result.firstNameError) {
							$("#checFirstName").html(result.firstNameError);
						} 
					} else {
						$("#sProfileName").html(profileName);
						$("#sName").html(firstName + " " +lastName);
						$("#sDateOfBirth").html(now);
						$("#sRelationship").html(relationshipLabel);
						cancelPersonal(profileName, firstName, lastName, datepicker, relationshipLabel);
					}
				    },beforeSend: function () {
			            $("#waitPersonal").show();
			        }
			    });
				cancelAboutMe();
			}
			
			function cancelPersonal(profileName, firstName, lastName, dateOfBirth, relationship){
				document.getElementById('editPersonal').style.display = 'inline';
				document.getElementById('editPersonalbtn').style.display = 'inline';

				document.getElementById('formPersonal').style.display = 'none';
				document.getElementById('cancelPersonalbtn').style.display = 'none';
				document.getElementById('donePersonalbtn').style.display = 'none';

				$("#profileName").val(profileName);
				$("#firstName").val(firstName);
				$("#lastName").val(lastName);
				$("#datepicker-example1").val(dateOfBirth);
				$('#relationship option:selected').text(relationship)
				$("#checkPAvail").html("");
			}

			function editAdditional(){
				document.getElementById('editAdditional').style.display = 'none';
				document.getElementById('editAdditionalbtn').style.display = 'none';
				
				document.getElementById('formAdditional').style.display = 'inline-block';
				document.getElementById('cancelAdditionalbtn').style.display = 'inline';
				document.getElementById('doneAdditionalbtn').style.display = 'inline';
				document.getElementById('phoneCode').value = document.getElementById('cuntryCodeId').value;
				
				initialize();
			}

			function doneAdditional(){
				var address = document.getElementById('address').value;
				if (address == "") {
					address = document.getElementById('location').value;
				}
				var city = document.getElementById('locality').value;
				var state = document.getElementById('administrative_area_level_1').value;
				var postalCode = document.getElementById('postal_code').value;
				var country = document.getElementById('country').value;
				var latlng = document.getElementById('latlng').value;
				
				var cuntryCodeId = $("#phoneCode").val();
				var phoneCode = $("#phoneCode option:selected").text();
				var mobileNumber = document.getElementById('mobileNumber').value;
				
				var webSiteAddress = document.getElementById('webSiteAddress').value;
				//var education = document.getElementById('education').value;
				//var occupation = document.getElementById('occupation').value;
				
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateProfile.ajx?section=additional"+"&latlng="+latlng+"&cuntryCodeId="+cuntryCodeId
					+"&mobileNumber="+mobileNumber+"&webSiteAddress="+webSiteAddress+"&address="+address
					+"&city="+city+"&state="+state+"&postalCode="+postalCode+"&country="+country ,
					dataType: "json",success:function(result){
					
					$("#waitAdditional").hide();
					$("#sLocation").html(address);
					$("#location").html(address);
					//$("#sphoneCode").html(phoneCode);
					$("#sMobileNumber").html(phoneCode.substring(phoneCode.indexOf("(")+1,phoneCode.indexOf(")")) + " - " + mobileNumber);
					$("#sWebSiteAddress").html(webSiteAddress);
					//$("#sWducation").html(education);
					//$("#sOccupation").html(occupation);
					cancelAdditional(address, mobileNumber, webSiteAddress);
				    },beforeSend: function () {
			            $("#waitAdditional").show();
			        }
			    });
			}
			
			function cancelAdditional(address, mobileNumber, webSiteAddress){
				document.getElementById('editAdditional').style.display = 'inline';
				document.getElementById('editAdditionalbtn').style.display = 'inline';

				document.getElementById('formAdditional').style.display = 'none';
				document.getElementById('cancelAdditionalbtn').style.display = 'none';
				document.getElementById('doneAdditionalbtn').style.display = 'none';

				$("#location").val(address);
				$("#mobileNumber").val(mobileNumber);
				$("#webSiteAddress").val(webSiteAddress);
			}

			function updateProfileName(){
				var cVal = document.getElementById('profileName').value;
				$('#profileUrlPart').html("<strong>"+cVal+"</strong>");
			}

			function onClickSettingMenu(id){
				var divnode = document.getElementById(id+'Subs');
				divnode.style.display = 'inline';
			}

			function updatePrivacy(field, privacy){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updatePrivacy.ajx?field="+field+"&privacy="+privacy ,dataType: "json",success:function(result){
					setPrivacyField(field, privacy);
                	var sName = "";
                	$('.subsSetting').css('display', 'none');
    			    }
    		    });
			}

			function setPrivacyField(field, privacy){
				var priLvl = "Public";
				if (privacy == 1) {
					priLvl = "Connections";
				} else if (privacy == 2) {
					priLvl = "Community Members";
				} else if (privacy == 3) {
					priLvl = "Followers";
				} else if (privacy == 4) {
					priLvl = "Only Me";
				} else if (privacy == 5) {
					priLvl = "Custom";
				}
				$('#'+field+'_inner li').each(function(i, li) {
					  var $inner = $(li);  
					  var $sp = $inner.find( ".selSett" );
					  if(jQuery.type($sp.html()) === 'string'){ 
					  } else {
					  }					  
					});
				
				$('#'+field+"_header").html(priLvl);
				//$('#innerList_'+field).html("<span class='selSett' ><span class='selTxt'>"+priLvl+"</span></span>");
			}

			function accountSettings(){
				var acUrl = '${communityEraContext.contextUrl}/pers/accountSettings.do';
				window.location.href = acUrl;
			}

			function emailSettings(){
				var emailUrl = 'pers/manageSubscriptions.do';
				window.location.href = emailUrl;
			}
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				normalQtip();
				dynamicDropDownQtip();

				$( document ).on( 'click', function( event ) {
					  var target = $( event.target );
					  if (target.is( "li" ) || target.is( "span" )) {
					  } else {
						  $('.subsSetting').css('display', 'none');
					  }
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
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${command.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<input type="hidden" id="address" value="" />
						<input type="hidden" id="locality" value="" />
						<input type="hidden" id="administrative_area_level_1" value="" />
						<input type="hidden" id="postal_code" value="" />
						<input type="hidden" id="country" value="" />
						<input type="hidden" id="latlng" value="" />
						<input type="hidden" id="cuntryCodeId" value="${command.cuntryCodeId}" />
						<input type="hidden" id="currentUserId" value="${communityEraContext.currentUser.id}" />
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}&backlink=ref" >${command.firstName} ${command.lastName}</a>
							</h2>
						</div>
						<c:choose>
							<c:when test="${command.user.coverPresent}">	
								<a href="javascript:void(0);" onclick="showPersonalMedia('Cover');" class="button photocontainer" 
									style="text-decoration: none; right: 24px;">Update Profile Cover</a>	
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);" onclick="showPersonalMedia('Cover');" class="button photocontainer" 
									style="text-decoration: none; right: 32px;">Add Profile Cover</a>
							</c:otherwise>
						</c:choose>
						<div class="groups" style="top: 58px;">
							<div class="picture">
								<c:choose>
									<c:when test="${command.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.id}" />
										</c:url>
										<img src="${photoUrl}"  width='160' height='160' /> 
										<c:if test="${communityEraContext.currentUser.id == command.id}">
											<div class="captionBottom" onclick="showPersonalMedia('Photo');" >
												<span><i class="fa fa-camera" aria-hidden="true"></i></span>
											</div>
										</c:if>
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png'  width='160' height='160' />
										<c:if test="${communityEraContext.currentUser.id == command.id}">
											<div class="captionBottom" onclick="showPersonalMedia('Photo');" >
												<span><i class="fa fa-camera" aria-hidden="true"></i></span>
											</div>
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="padding: 0px; display: inline-block;">
						<div class="intHeader" style="">
							<span class="settingHdrSelected" onclick="profileSettings(0)">
								Profile Settings
							</span>
							<span class="settingHdr" onclick="accountSettings();" style="border-right: 2px solid #CCC; border-left: 2px solid #CCC;" >
								Account Settings
							</span>
							<span class="settingHdr" onclick="emailSettings()">
								E-mail Notifications
							</span>
						</div>
				 		<div class="innerBlock" style="padding: 20px 20px 20px;">
							<c:if test="${command.user.inactive}">
								<p style="color: rgb(137, 143, 156); word-wrap: break-word;">This user is no longer registered. The information below may be obsolete </p>
							</c:if>
							<c:if test="${!command.user.inactive}">
								<c:if test="${communityEraContext.currentUser.id == command.id}">
									<div class="otrSec" >
										<div style="margin-bottom: 40px;">
											<h2 class="headerSection" >About</h2>
											<a href="javascript:void(0);" onclick="editAboutMe();" class="btnsec edit" id="editAboutMebtn" style="margin-top: 0px;">Edit</a>
											<a href="javascript:void(0);" onclick="doneAboutMe();" class="btnsec save" style="display: none; top: 0px;" id="btndon">Done</a>
				                        	<a href="javascript:void(0);" onclick="cancelAboutMe();" class="btnsec delete" style="display: none; top: 0px; margin-left: 12px;" 
				                        		id="btncnl">Cancel</a> 
										</div>
										
										<c:if test="${empty command.about}">
											<p id="captionHeader" style="margin-bottom: 30px; font-size: 13px;">Say something about yourself...</p>
										</c:if>
										<c:if test="${not empty command.about}">
											<p id="captionHeader" style="margin-bottom: 30px; font-style: normal; font-size: 13px;">${command.about}</p>
										</c:if>
										<textarea placeholder="Say something about yourself..." class="comment" maxlength="500" id="editAboutMe">${command.about}</textarea>
									</c:if>
									<c:if test="${communityEraContext.currentUser.id != command.id}">
										<p id="captionHeader" style="margin-bottom: 30px; font-style: normal; font-size: 13px;">${command.about}</p>
			                       	</c:if>
		                       	</div>
								
								<div class="otrSec" >
									<div style="margin-bottom: 40px;">
										<h2 class="headerSection" >Personal details</h2>
										<c:if test="${communityEraContext.currentUser.id == command.id}">
											<a href="javascript:void(0);" onclick="editPersonal('');" class="btnsec edit" id="editPersonalbtn" style="margin: 0px 0px;">Edit</a>
											<a href="javascript:void(0);" onclick="donePersonal();" class="btnsec save" style="display: none; top: 0px; margin-left: 12px;" 
												id="donePersonalbtn">Done</a>
											<a href="javascript:void(0);" onclick="cancelPersonal('${command.profileName}', '${command.firstName}', '${command.lastName}', '${command.dob}', '${command.relationshipDisplay}');" class="btnsec delete" style="display: none; top: 0px; margin-left: 12px;" id="cancelPersonalbtn">Cancel</a> 
										</c:if>
									</div>
									<div id="editPersonal" class="mainSec" >
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="profileName">Profile Name:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sProfileName">${command.profileName}</span>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="title">Name:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sName">${command.title} ${command.firstName} ${command.lastName}</span>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="dateOfBirth" >Date of Birth:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sDateOfBirth">${command.dob}</span>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:50px;">
											<label for="relationship">Relationship:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sRelationship">${command.relationshipDisplay}</span>
										</div>
									</div>
									<div id="waitPersonal" style="display: none;">
										<div class="cssload-square" style="width: 13px; height: 13px;">
											<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
											<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
											<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
										</div>
									</div>
									<div id="formPersonal" class="formSection">
										<div style="float:left; width: 48%;">
											<%--<label for="title">Title:</label><br />
											<form:dropdown path="title" mandatory="true">
												<form:option value="" label="Select..."/>
												<form:optionlist options="${referenceData.userTitleOptions}"/>
											</form:dropdown> --%>
										</div>
										<div style="float:left; width: 48%;">
											<%--<label for="title">Gender:</label><br />
											<form:dropdown path="title" mandatory="true">
												<form:option value="" label="Select..."/>
												<form:optionlist options="${referenceData.userTitleOptions}"/>
											</form:dropdown> --%>
										</div>
										
										<div style="float:left; width: 100%; ">
											<div style="float:left; width: 55%;">
												<label for="profileName">Profile Name:</label><br />
												<input type="text" id="profileName" name="profileName" style="width: 100%;" class="editInfo" value="${command.profileName}" 
												onkeyup="updateProfileName();"/>
											</div>
											<div style="float:left; width: 40%; margin: 12px;">
												<a href="javascript:void(0);" onclick="checkProfileNameAvailability();" class="btnsec" style="float:left;">Check Availability</a>
											</div>
										</div>
										<div id="waitMessage2" style="display: none;">
											<div class="cssload-square" >
												<div class="cssload-square-part cssload-square-green" ></div>
												<div class="cssload-square-part cssload-square-pink" ></div>
												<div class="cssload-square-blend" ></div>
											</div>
										</div>
										<div id="checkPAvail"></div>
										<div style="float:left; width: 55%;">
											<p class="profileURL">${communityEraContext.contextUrl}/profile/<span id="profileUrlPart" class="pUrlPart">${command.profileName}</span></p>
										</div>
										
										<div style="float:left; width: 55%;">
											<label for="firstName">First Name:</label><br />
											<input type="text" id="firstName" class="editInfo" name="firstName" style="width: 100%;" value="${command.firstName}"/>
										</div>
										<div class="entry" id="checkfirstName"></div>
										<div style="float:left; width: 55%; margin-top: 22px;">
											<label for="lastName">Last Name:</label><br />
											<input type="text" id="lastName" class="editInfo" name="lastName" style="width: 100%;" value="${command.lastName}"/>
										</div>
										<div style="float:left; width: 100%; margin-top: 22px; ">
											<label for="dateOfBirth" >Date of Birth:</label><br />
											<span style="display: inline-block; position: relative; float:left;">	
												<input type="text" id="datepicker-example1" class="editInfo" name="dateOfBirth" style="width: 176px;" value="${command.dob}"/>	
											</span>
											<div class="privSett" style="margin: -15px 0px 0px 20px;">
												<ul class="navSetting">
													<li class="outer" id="dateOfBirthSetting" onclick="onClickSettingMenu('dateOfBirthSetting');">
														<span class="selSett" ><span class="selTxt" id="dateOfBirth_header" >${command.userPrivacy.dateOfBirthPrivacy}</span></span><span class="ddimgBlk"></span>
									                    <div class="subsSetting" id="dateOfBirthSettingSubs">
									                        <div class="col">
									                            <ul style="line-height:32px;" id="dateOfBirth_inner">
									                                <li class="innerList" onclick="updatePrivacy('dateOfBirth', 0);">
											                                <c:if test="${command.userPrivacy.dateOfBirth == 0}">
											                                	<span class="selSett" ><span class="selTxt">Public</span></span>
													                       	</c:if>
													                       	<c:if test="${command.userPrivacy.dateOfBirth != 0}">
											                                	<span class="selTxt">Public</span>
													                       	</c:if>
										                                <div class="desc">Anyone on Jhapak</div>
									                                </li>
																	<li class="innerList" onclick="updatePrivacy('dateOfBirth', 1);">
																		<c:if test="${command.userPrivacy.dateOfBirth == 1}">
										                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.dateOfBirth != 1}">
										                                	<span class="selTxt">Connections</span>
												                       	</c:if>
												                       	<div class="desc">All from your connections</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('dateOfBirth', 2);">
																		<c:if test="${command.userPrivacy.dateOfBirth == 2}">
										                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.dateOfBirth != 2}">
										                                	<span class="selTxt">Community Members</span>
												                       	</c:if>
												                       	<div class="desc">Your each community member</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('dateOfBirth', 3);">
																		<c:if test="${command.userPrivacy.dateOfBirth == 3}">
										                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.dateOfBirth != 3}">
										                                	<span class="selTxt">Followers</span>
												                       	</c:if>
												                       	<div class="desc">Everyone who follows you</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('dateOfBirth', 4);">
																		<c:if test="${command.userPrivacy.dateOfBirth == 4}">
										                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.dateOfBirth != 4}">
										                                	<span class="selTxt">Only Me</span>
												                       	</c:if>
												                       	<div class="desc">Only me</div>
												                    </li>
																	<li class="innerList" onclick="customPrivacy();">
																		<c:if test="${command.userPrivacy.dateOfBirth == 5}">
										                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.dateOfBirth != 5}">
										                                	<span class="selTxt">Custom</span>
												                       	</c:if>
												                       	<div class="desc">Custom Privacy</div>
												                   </li>
									                            </ul>
									                        </div>
									                    </div>
									                </li>
									        	</ul>
											</div>
										</div>
										
										<div style="float:left; width: 100%; margin-top: 22px; padding-bottom:50px;">
											<div style="float:left; width: 55%;">
												<label for="relationship">Relationship:</label><br />
												<select name="relationship" id="relationship" style="width: 100%;">
													<c:forEach items="${command.relationshipList}" var="relationshipRow" >
														<c:if test="${command.relationship == relationshipRow.value}">
						                                	<option value="${relationshipRow.value}" selected="selected">${relationshipRow.label}</option>
								                       	</c:if>
								                       	<c:if test="${command.relationship != relationshipRow.value}">
						                                	<option value="${relationshipRow.value}" >${relationshipRow.label}</option>
								                       	</c:if>
													</c:forEach>
												</select>
											</div>

											<div class="privSett" style="margin-left: 34px;">
												<ul class="navSetting">
													<li class="outer" id="relationshipSetting" onclick="onClickSettingMenu('relationshipSetting');">
														<span class="selSett" ><span class="selTxt" id="relationship_header" >${command.userPrivacy.relationshipPrivacy}</span></span><span class="ddimgBlk"></span>
									                    <div class="subsSetting" id="relationshipSettingSubs">
									                        <div class="col">
									                            <ul style="line-height:32px;" id="relationship_inner">
									                                <li class="innerList" onclick="updatePrivacy('relationship', 0);">
										                                <c:if test="${command.userPrivacy.relationship == 0}">
										                                	<span class="selSett" ><span class="selTxt">Public</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 0}">
										                                	<span class="selTxt">Public</span>
												                       	</c:if>
										                                <div class="desc">Anyone on Jhapak</div>
									                                </li>
																	<li class="innerList" onclick="updatePrivacy('relationship', 1);">
																		<c:if test="${command.userPrivacy.relationship == 1}">
										                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 1}">
										                                	<span class="selTxt">Connections</span>
												                       	</c:if>
												                       	<div class="desc">All from your connections</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('relationship', 2);">
																		<c:if test="${command.userPrivacy.relationship == 2}">
										                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 2}">
										                                	<span class="selTxt">Community Members</span>
												                       	</c:if>
												                       	<div class="desc">Your each community member</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('relationship', 3);">
																		<c:if test="${command.userPrivacy.relationship == 3}">
										                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 3}">
										                                	<span class="selTxt">Followers</span>
												                       	</c:if>
												                       	<div class="desc">Everyone who follows you</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('relationship', 4);">
																		<c:if test="${command.userPrivacy.relationship == 4}">
										                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 4}">
										                                	<span class="selTxt">Only Me</span>
												                       	</c:if>
												                       	<div class="desc">Only me</div>
												                    </li>
																	<li class="innerList" onclick="customPrivacy();">
																		<c:if test="${command.userPrivacy.relationship == 5}">
										                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.relationship != 5}">
										                                	<span class="selTxt">Custom</span>
												                       	</c:if>
												                       	<div class="desc">Custom Privacy</div>
												                   </li>
									                            </ul>
									                        </div>
									                    </div>
									                </li>
									        	</ul>
											</div>
										</div>
									</div>
								</div>
								
								<div class="otrSec" >
									<div style="margin-bottom: 40px;">
										<h2 class="headerSection" >Additional details</h2>
										<c:if test="${communityEraContext.currentUser.id == command.id}">
											<a href="javascript:void(0);" onclick="editAdditional();" class="btnsec edit" id="editAdditionalbtn" style="margin: 0px 0px;">Edit</a>
											<a href="javascript:void(0);" onclick="doneAdditional();" class="btnsec save" style="display: none; top: 0px; margin-left: 12px;" id="doneAdditionalbtn">Done</a>
											<a href="javascript:void(0);" onclick="cancelAdditional('${command.location}', '${command.mobileNumber}', '${command.webSiteAddress}');" class="btnsec delete" 
												style="display: none;" id="cancelAdditionalbtn">Cancel</a> 
										</c:if>
									</div>
									<div id="editAdditional" class="mainSec" >
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="location">Location:</label>
											<p style="font-weight:bold; width: 620px; float: right; word-wrap: normal;" id="sLocation">${command.location}</p>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="mobileNumber">MobileNumber:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sMobileNumber">
												<c:if test="${command.cuntryCodeId > 0}">
												${command.mobPhoneCode}
												</c:if> - 
												<c:if test="${command.mobileNumber > 0}">
												${command.mobileNumber}
												</c:if>
											</span>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="webSiteAddress">WebSite:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sWebSiteAddress">${command.webSiteAddress}</span>
										</div>
										
										<%--<div style="float:left; width: 100%; padding-bottom:20px;">
											<label for="education">Education:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sEducation">${command.education}</span>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:0px;">
											<label for="occupation">Occupation:</label>
											<span style="font-weight:bold; padding-left:10px;" id="sOccupation">${command.occupation}</span>
										</div> --%>
									</div>
									<div id="waitAdditional" style="display: none;">
										<div class="cssload-square" style="width: 13px; height: 13px;">
											<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
											<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
											<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
										</div>
									</div>
									<div id="formAdditional" class="formSection">
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<div style="float:left; width: 55%;">
												<label for="location">Location:</label><br />
												<input type="text" class="editInfo" id="location" name="location" style="width: 100%;" value="${command.location}"/>
											</div>
											<div class="privSett" style="margin: 0px 0px 0px 35px;">
												<ul class="navSetting">
													<li class="outer" id="addressSetting" onclick="onClickSettingMenu('addressSetting');">
														<span class="selSett" ><span class="selTxt" id="address_header" >${command.userPrivacy.addressPrivacy}</span></span><span class="ddimgBlk"></span>
									                    <div class="subsSetting" id="addressSettingSubs">
									                        <div class="col">
									                            <ul style="line-height:32px;" id="address_inner">
									                                <li class="innerList" onclick="updatePrivacy('address', 0);">
										                                <c:if test="${command.userPrivacy.address == 0}">
										                                	<span class="selSett" ><span class="selTxt">Public</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 0}">
										                                	<span class="selTxt">Public</span>
												                       	</c:if>
										                                <div class="desc">Anyone on Jhapak</div>
									                                </li>
																	<li class="innerList" onclick="updatePrivacy('address', 1);">
																		<c:if test="${command.userPrivacy.address == 1}">
										                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 1}">
										                                	<span class="selTxt">Connections</span>
												                       	</c:if>
												                       	<div class="desc">All from your connections</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('address', 2);">
																		<c:if test="${command.userPrivacy.address == 2}">
										                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 2}">
										                                	<span class="selTxt">Community Members</span>
												                       	</c:if>
												                       	<div class="desc">Your each community member</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('address', 3);">
																		<c:if test="${command.userPrivacy.address == 3}">
										                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 3}">
										                                	<span class="selTxt">Followers</span>
												                       	</c:if>
												                       	<div class="desc">Everyone who follows you</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('address', 4);">
																		<c:if test="${command.userPrivacy.address == 4}">
										                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 4}">
										                                	<span class="selTxt">Only Me</span>
												                       	</c:if>
												                       	<div class="desc">Only me</div>
												                    </li>
																	<li class="innerList" onclick="customPrivacy();">
																		<c:if test="${command.userPrivacy.address == 5}">
										                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.address != 5}">
										                                	<span class="selTxt">Custom</span>
												                       	</c:if>
												                       	<div class="desc">Custom Privacy</div>
												                   </li>
									                            </ul>
									                        </div>
									                    </div>
									                </li>
									        	</ul>
											</div>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<div style="float:left; width: 48%;">
												<label for="title">Phone Code:</label><br />
												<select name="phoneCode" id="phoneCode">
													<c:forEach items="${command.countryList}" var="row" >
														<option value="${row.value}">${row.label}</option>
													</c:forEach>
												</select>
											</div>
											<div style="float:left; width: 220px; margin-left: -170px;">
												<label for="mobileNumber">MobileNumber:</label><br />
												<input type="text" class="editInfo" id="mobileNumber" name="mobileNumber" style="width: 100%;" size="16" value="${command.mobileNumber}"/>
											</div>
											<div class="privSett" style="margin: 0px 0px 0px 35px;">
												<ul class="navSetting">
													<li class="outer" id="mobileNumberSetting" onclick="onClickSettingMenu('mobileNumberSetting');">
														<span class="selSett" ><span class="selTxt" id="mobileNumber_header" >${command.userPrivacy.mobileNumberPrivacy}</span></span><span class="ddimgBlk"></span>
									                    <div class="subsSetting" id="mobileNumberSettingSubs">
									                        <div class="col">
									                            <ul style="line-height:32px;" id="mobileNumber_inner">
									                                <li class="innerList" onclick="updatePrivacy('mobileNumber', 0);">
										                                <c:if test="${command.userPrivacy.mobileNumber == 0}">
										                                	<span class="selSett" ><span class="selTxt">Public</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 0}">
										                                	<span class="selTxt">Public</span>
												                       	</c:if>
										                                <div class="desc">Anyone on Jhapak</div>
									                                </li>
																	<li class="innerList" onclick="updatePrivacy('mobileNumber', 1);">
																		<c:if test="${command.userPrivacy.mobileNumber == 1}">
										                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 1}">
										                                	<span class="selTxt">Connections</span>
												                       	</c:if>
												                       	<div class="desc">All from your connections</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('mobileNumber', 2);">
																		<c:if test="${command.userPrivacy.mobileNumber == 2}">
										                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 2}">
										                                	<span class="selTxt">Community Members</span>
												                       	</c:if>
												                       	<div class="desc">Your each community member</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('mobileNumber', 3);">
																		<c:if test="${command.userPrivacy.mobileNumber == 3}">
										                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 3}">
										                                	<span class="selTxt">Followers</span>
												                       	</c:if>
												                       	<div class="desc">Everyone who follows you</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('mobileNumber', 4);">
																		<c:if test="${command.userPrivacy.mobileNumber == 4}">
										                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 4}">
										                                	<span class="selTxt">Only Me</span>
												                       	</c:if>
												                       	<div class="desc">Only me</div>
												                    </li>
																	<li class="innerList" onclick="customPrivacy();">
																		<c:if test="${command.userPrivacy.mobileNumber == 5}">
										                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.mobileNumber != 5}">
										                                	<span class="selTxt">Custom</span>
												                       	</c:if>
												                       	<div class="desc">Custom Privacy</div>
												                   </li>
									                            </ul>
									                        </div>
									                    </div>
									                </li>
									        	</ul>
											</div>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:20px;">
											<div style="float:left; width: 55%;">
												<label for="webSiteAddress">WebSite:</label><br />
												<input type="text" class="editInfo" id="webSiteAddress" name="webSiteAddress" style="width: 100%;" value="${command.webSiteAddress}"/>
											</div>
											<div class="privSett" style="margin: 0px 0px 0px 35px;">
												<ul class="navSetting">
													<li class="outer" id="webSiteAddressSetting" onclick="onClickSettingMenu('webSiteAddressSetting');">
														<span class="selSett" ><span class="selTxt" id="webSiteAddress_header" >${command.userPrivacy.webSiteAddressPrivacy}</span></span><span class="ddimgBlk"></span>
									                    <div class="subsSetting" id="webSiteAddressSettingSubs" style="margin-top: -44px;">
									                        <div class="col">
									                            <ul style="line-height:32px;" id="webSiteAddress_inner">
									                                <li class="innerList" onclick="updatePrivacy('webSiteAddress', 0);">
										                                <c:if test="${command.userPrivacy.webSiteAddress == 0}">
										                                	<span class="selSett" ><span class="selTxt">Public</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 0}">
										                                	<span class="selTxt">Public</span>
												                       	</c:if>
										                                <div class="desc">Anyone on Jhapak</div>
									                                </li>
																	<li class="innerList" onclick="updatePrivacy('webSiteAddress', 1);">
																		<c:if test="${command.userPrivacy.webSiteAddress == 1}">
										                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 1}">
										                                	<span class="selTxt">Connections</span>
												                       	</c:if>
												                       	<div class="desc">All from your connections</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('webSiteAddress', 2);">
																		<c:if test="${command.userPrivacy.webSiteAddress == 2}">
										                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 2}">
										                                	<span class="selTxt">Community Members</span>
												                       	</c:if>
												                       	<div class="desc">Your each community member</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('webSiteAddress', 3);">
																		<c:if test="${command.userPrivacy.webSiteAddress == 3}">
										                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 3}">
										                                	<span class="selTxt">Followers</span>
												                       	</c:if>
												                       	<div class="desc">Everyone who follows you</div>
												                    </li>
																	<li class="innerList" onclick="updatePrivacy('webSiteAddress', 4);">
																		<c:if test="${command.userPrivacy.webSiteAddress == 4}">
										                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 4}">
										                                	<span class="selTxt">Only Me</span>
												                       	</c:if>
												                       	<div class="desc">Only me</div>
												                    </li>
																	<li class="innerList" onclick="customPrivacy();">
																		<c:if test="${command.userPrivacy.webSiteAddress == 5}">
										                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
												                       	</c:if>
												                       	<c:if test="${command.userPrivacy.webSiteAddress != 5}">
										                                	<span class="selTxt">Custom</span>
												                       	</c:if>
												                       	<div class="desc">Custom Privacy</div>
												                   </li>
									                            </ul>
									                        </div>
									                    </div>
									                </li>
									        	</ul>
											</div>
										</div>
										
										<%-- <div style="float:left; width: 100%; padding-bottom:20px;">
											<div style="float:left; width: 55%;">
												<label for="education">Education:</label><br />
												<input type="text" class="editInfo" id="education" name="education" style="width: 100%;" value="${command.education}"/>
											</div>
										</div>
										
										<div style="float:left; width: 100%; padding-bottom:0px;">
											<div style="float:left; width: 55%;">
												<label for="occupation">Occupation:</label><br />
												<input type="text" class="editInfo" id="occupation" name="occupation" style="width: 100%;" value="${command.occupation}"/>
											</div>
										</div> --%>
									</div>
								</div>
								
								<%-- <h2 class="header" style="font-size:13px; padding-top:5px;">Areas of expertise<li class='lineseparaterProfile' style="width: 570px;"></li></h2>
								<div id="checkboxgroup">
									<fieldset>
										<form:checkboxlist path="expertiseIds" cssClass="check">
											<form:optionlist options="${referenceData.userExpertiseOptions}" />
										</form:checkboxlist>
									</fieldset>
								</div> --%>
							</c:if>
						</div>
					</div>
				 </div> <!-- /myUpload --> 
				</div><!-- /#leftPannel -->
				
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
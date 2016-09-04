<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${communityEraContext.currentUser.fullName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<title>Jhapak - ${communityEraContext.currentUser.firstName}'s profile</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<cera:taggingJS context="${communityEraContext}"/>
		
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
        
		<script type="text/javascript" src="js/cropbox.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropImage.css" />
        <link rel="stylesheet" type="text/css" href="css/allMedia.css" />
        
        <style>
	      #map_canvas {
	        width: 690px;
	        height: 400px;
	        background-color: #CCC;
	      }
	      
	      .login-dialog .modal-dialog {
                width: 788px;
            }
            
            .modal-body {
			    padding: 0px;
			    height: 520px;
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
			
			.imageBox {
				position: relative;
				height: 450px;
				width: 700px;
				border: 1px solid #aaa;
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
	    </style>
	    
	    <script type="text/javascript">
		    function uploadImage(communityId) 
			{ 
		    	var cropper = "";
		    	var main2 = '<div id="main2"><p id="waitCloudMessage" style="margin-left: 380px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p>';
		    	main2 += '<div class="container"><div class="imageBox"><div class="thumbBox"></div><div class="spinner" style="display: none">Loading...</div></div>';
		    	main2 += '<div class="action"><input type="file" id="file" style="display: none" /></div><div class="cropped"></div></div></div>';

				var dialogInstance = BootstrapDialog.show({
					title: 'Crop Picture',
					message: function(dialog) {
					var $message = $(main2);
	                return $message;
	            },
	            buttons: [{
	            	id: 'btnZoomOut',
	                label: '-',
	                cssClass: '',
	                action: function(dialog){
	            	cropper.zoomOut();
                	}
	            }, {
	            	id: 'btnZoomIn',
	                label: '+',
	                cssClass: 'btnZoomIn',
	                action: function(dialog) {
	            	cropper.zoomIn();
	                }
	            },
	            	{
	            	id: 'btnCrop',
	                label: 'Crop & Save',
	                cssClass: '',
	                action: function(dialog){
		            	var img = cropper.getBlob();
						var data = new FormData(); 
						data.append("file", img);
						$.ajax({url:"${communityEraContext.contextUrl}/pers/registeruserPhoto.img",
							data: data,
						    enctype: 'multipart/form-data',
						    processData: false,  // do not process the data as url encoded params
						    contentType: false,   // by default jQuery sets this to urlencoded string
						    type: 'POST',
							success:function(result){
							location.reload(); 
					    },
					 	// What to do before starting
				         beforeSend: function () {
				         } 
		
			    	  });
	            }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	 var options =
	                 {
	                     thumbBox: '.thumbBox',
	                     spinner: '.spinner',
	                     imgSrc: ''
	                 };
	                 cropper = dialogRef.getModalBody().find('.imageBox').cropbox(options);
	            	 dialogRef.getModalBody().find('#file').on('change', function(){
	                     var reader = new FileReader();
	                     reader.onload = function(e) {
	                         options.imgSrc = e.target.result;
	                         cropper = $('.imageBox').cropbox(options);
	                     }
	                     reader.readAsDataURL(this.files[0]);
	                     this.files = [];
	                 });
	                 
	            },
	            onshow: function(dialogRef){
	            	dialogRef.getModalBody().find('#file').click();
	            }
		        });
			}

			function addProfilePhoto() 
			{ 
				var main = '<div id="main"><p id="waitCloudMessage" style="margin-left: 380px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p>';
				main += '<div class="upersec"><div class="uploadPhoto" onclick="uploadImage();"><div class="firOuter"><span class="">Upload Photo</span></div></div><i class="photo-separator"></i><div class="takePhoto"></div></div><div class="lowersec"><div id="scrollpane" class="scroll-pane2"></div></div></div>'; 
				var dialogInstance = BootstrapDialog.show({
					title: 'Update Profile Picture',
					message: function(dialog) {
					var $message = $(main);
	                return $message;
	            },
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            }
		        });
			}
		</script>

        <script src="https://maps.googleapis.com/maps/api/js"></script>
        <script>
	      function initialize() {
	        var mapCanvas = document.getElementById('map_canvas');
	        var mapOptions = {
	          center: new google.maps.LatLng(44.5403, -78.5463),
	          zoom: 8,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        }
	        var map = new google.maps.Map(mapCanvas, mapOptions)
	      }
	      google.maps.event.addDomListener(window, 'load', initialize);
	    </script>
        		
		<script language="Javascript" type="text/javascript">
		    function checkAvailability(){
				email = document.getElementById('emailAddress').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/checkAvailability.ajx?email="+email,success:function(result){
					$("#checkAvail").html(result);
			        $("#waitMessage").hide();
				    },
			         beforeSend: function () {
				    	$("#checkAvail").html('');
			             $("#waitMessage").show();
			         } 
		    	  });
			}

		    function checkProfileNameAvailability(){
				pName = document.getElementById('profileName').value;
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
	
			function saveChanges(){
				document.registerUserForm.action = "${communityEraContext.requestUrl}";
				document.registerUserForm.submit();
			}

			function replaceAbout(obj){
				if (obj.value == ''){
					var newO=document.createElement('textarea');
					newO.setAttribute('name', obj.getAttribute('name'));
					newO.setAttribute('id', obj.getAttribute('id'));
					newO.setAttribute('class', obj.getAttribute('class'));
					newO.setAttribute('onblur', 'if (this.value == \'\') {this.value = \'About me (A short description of yourself - 100 words max\';}');
					newO.setAttribute('onclick', 'if (this.value == \'About me \(A short description of yourself - 100 words max\)') {this.value = '';}');
					newO.setAttribute('onfocus', obj.getAttribute('onfocus'));
					obj.parentNode.replaceChild(newO,obj);
					newO.focus();
				}
			}
	    </script>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="communities">
						<h2 >${command.firstName} ${command.lastName}</h2>
						<div class="menus">
							<ul>
								<c:if test="${communityEraContext.currentUser.id==command.id && command.mode!='u'}">
									<li><a href="${communityEraContext.contextUrl}/pers/connectionResult.do" 
									title="Edit my personal profile">Edit my profile</a></li>
								</c:if>
								<c:if test="${communityEraContext.currentUser.id==command.id}">
									<li><a href="${communityEraContext.contextUrl}/pers/unregisterMe.do" 
									title="Remove my personal profile">Remove my registration</a></li>
									
									<li><a href="${communityEraContext.contextUrl}/pers/manageSubscriptions.do" 
									title="Manage my subscriptions">Manage my subscriptions</a></li>
									
									<li><a href="${communityEraContext.contextUrl}/pers/changePass.do" 
									title="Manage my subscriptions">Change password</a></li>
								</c:if>
							</ul>
						</div>
					</div>
					
					<div class="groups">
						<c:choose>
							<c:when test="${command.photoPresent}">
								<div class="picture">
									<c:if test="${command.mode=='u'}">
										<a href="${communityEraContext.contextUrl}/pers/userPhotoRemove.img" id="removeLogoLink" class="editPicture" title="Remove logo" ><span style="background: #66799f; padding: 2px;">X</span></a>
									</c:if>
									<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
										<c:param name="id" value="${command.id}" />
									</c:url>
									<img src="${photoUrl}"  width="160" height="160"/> 
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${command.mode!='u'}">
									<div class="picture" >
										<img src='img/user_icon.png'  width="160" height="160"/>
									</div>
								</c:if>
								<c:if test="${command.mode=='u'}">
									<div class="picture" onclick="addProfilePhoto();"></div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
					<form:form mode="${command.mode}" multipart="true" showFieldErrors="true" name="registerUserForm">
					<form:hidden path="id" />
					<form:errors message="Please correct the errors below" cssClass="errorText" />
					<div class="abtMe">
						<c:if test="${command.mode=='u'}">
							<textarea id="about" name="about"  class="abtMeTextArea" onclick="this.onclick='test'; replaceAbout(this);" onfocus="if (this.value == 'About me (A short description of yourself)') {this.value = '';}" 
							onblur="if (this.value == '') {this.value = 'About me (A short description of yourself)';}" >${command.about}</textarea>
						</c:if>
						<c:if test="${command.mode!='u'}">
							<div class="abtMeSection">
								${command.about}
							</div>
						</c:if>
					</div>
					
					<div class="inputForm">
				 		<div class="innerBlock">
					<c:if test="${command.mode=='u'}">
						<c:if test="${command.allowLoginUpdate}">
							<h2 class="header" style="font-size:13px; margin-top:0px; padding-top:5px;">Log In details<li class='lineseparaterProfile' style="width: 590px;"></li></h2>
							<div>
								<div style="float:left; width: 100%; padding-bottom:20px;">
									<div style="float:left; width: 55%;">
										<label for="profileName">Profile Name:</label><br />
										<form:input id="profileName" path="profileName" cssStyle="width: 100%;"/>
									</div>
									<div style="float:left; width: 40%; padding:26px 0px 0px 20px;">
										<a href="javascript:void(0);" onclick="checkProfileNameAvailability();" class="button" style="float:left;">Check Availability</a>
									</div>
								</div>
								<div id="waitMessage2" style="display: none;">
									<div class="cssload-square" >
										<div class="cssload-square-part cssload-square-green" ></div>
										<div class="cssload-square-part cssload-square-pink" ></div>
										<div class="cssload-square-blend" ></div>
									</div>
								</div>
								<div id="checkPAvail">
								</div>
							</div>
							<div>
								<div style="float:left; width: 100%; padding-bottom:20px;">
									<div style="float:left; width: 55%;">
										<label for="emailAddress">E-mail Address:</label><br />
										<form:input id="emailAddress" path="emailAddress" cssStyle="width: 100%;"/>
									</div>
									<div style="float:left; width: 40%; padding:26px 0px 0px 20px;">
										<a href="javascript:void(0);" onclick='checkAvailability();' class="button" style="float:left;">Check Availability</a>
									</div>
								</div>
								<div id="waitMessage" style="display: none;">
									<div class="cssload-square" >
										<div class="cssload-square-part cssload-square-green" ></div>
										<div class="cssload-square-part cssload-square-pink" ></div>
										<div class="cssload-square-blend" ></div>
									</div>
								</div>
								<div class="entry" id="checkAvail">
								</div>
							</div>
							<div>
								<div style="float:left; width: 100%; padding-bottom:60px;">
									<div style="float:left; width: 55%;">
										<label for="confirmEmailAddress">Confirm E-mail:</label><br />
										<form:input id="confirmEmailAddress" path="confirmEmailAddress" cssStyle="width: 100%;"/>
									</div>
								</div>
							</div>
						</c:if>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Personal details<li class='lineseparaterProfile' style="width: 584px;"></li></h2>
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 48%;">
								<label for="title">Title:</label><br />
								<form:dropdown path="title" mandatory="true">
									<form:option value="" label="Select..."/>
									<form:optionlist options="${referenceData.userTitleOptions}"/>
								</form:dropdown>
							</div>
							<div style="float:left; width: 48%;">
								<label for="title">Gender:</label><br />
								<form:dropdown path="title" mandatory="true">
									<form:option value="" label="Select..."/>
									<form:optionlist options="${referenceData.userTitleOptions}"/>
								</form:dropdown>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="firstName">First Name:</label><br />
								<form:input id="firstName" path="firstName" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="lastName">Last Name:</label><br />
								<form:input id="lastName" path="lastName" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="dateOfBirth" >Date of Birth:</label><br />
							<span style="display: inline-block; position: relative; width: 80%; float:left;">	
								<form:input id="datepicker-example1" path="dateOfBirth"  cssStyle="width: 176px;"/>	
							</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:60px;">
							<div style="float:left; width: 55%;">
								<label for="relationship">Relationship:</label><br />
								<form:input id="relationship" path="relationship" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Additional details<li class='lineseparaterProfile' style="width: 570px;"></li></h2>
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="location">Location:</label><br />
								<form:input id="location" path="location" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 48%;">
								<label for="title">Phone Code:</label><br />
								<form:dropdown path="phoneCode" mandatory="true">
									<form:optionlist options="${command.countryList}"/>
								</form:dropdown>
							</div>
							<div style="float:left; width: 230px; margin-left: -180px;">
								<label for="mobileNumber">MobileNumber:</label><br />
								<form:input id="mobileNumber" path="mobileNumber" cssStyle="width: 100%;" size="16"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="webSiteAddress">WebSite:</label><br />
								<form:input id="webSiteAddress" path="webSiteAddress" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="education">Education:</label><br />
								<form:input id="education" path="education" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<div style="float:left; width: 55%;">
								<label for="occupation">Occupation:</label><br />
								<form:input id="occupation" path="occupation" cssStyle="width: 100%;"/>
							</div>
						</div>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Areas of expertise<li class='lineseparaterProfile' style="width: 570px;"></li></h2>
						<p style="margin-top:-15px;padding-top:0px;">Enter your areas of expertise to help people network with you for relevant activities.</p>
						<div id="checkboxgroup">
							<fieldset>
								<form:checkboxlist path="expertiseIds" cssClass="check">
									<form:optionlist options="${referenceData.userExpertiseOptions}" />
								</form:checkboxlist>
							</fieldset>
						</div>
					</c:if>
					
					<c:if test="${command.mode!='u'}">
						<h2 class="header" style="font-size:13px; margin-top:0px; padding-top:5px;">Log In details<li class='lineseparaterProfile' style="width: 590px;"></li></h2>
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="profileName">Profile Name:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.profileName}</span>
						</div>
						<div style="float:left; width: 100%; padding-bottom:40px;">
							<label for="emailAddress">E-mail Address:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.emailAddress}</span>
						</div>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Personal details<li class='lineseparaterProfile' style="width: 584px;"></li></h2>
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="title">Name:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.title} ${command.firstName} ${command.lastName}</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="dateOfBirth" >Date of Birth:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.dateOfBirth}</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:40px;">
							<label for="relationship">Relationship:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.relationship}</span>
						</div>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Additional details<li class='lineseparaterProfile' style="width: 570px;"></li></h2>
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="location">Location:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.location}</span>
						</div>
						
						<div id="map_canvas"></div> 
						<br />
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="mobileNumber">MobileNumber:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.mobileNumber}</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="webSiteAddress">WebSite:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.webSiteAddress}</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="education">Education:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.education}</span>
						</div>
						
						<div style="float:left; width: 100%; padding-bottom:20px;">
							<label for="occupation">Occupation:</label>
							<span style="font-weight:bold; padding-left:10px;">${command.occupation}</span>
						</div>
						
						<h2 class="header" style="font-size:13px; padding-top:5px;">Areas of expertise<li class='lineseparaterProfile' style="width: 570px;"></li></h2>
						<div id="checkboxgroup">
							<fieldset>
								<form:checkboxlist path="expertiseIds" cssClass="check">
									<form:optionlist options="${referenceData.userExpertiseOptions}" />
								</form:checkboxlist>
							</fieldset>
						</div>
					</c:if>

					<c:if test="${command.mode=='u'}">		
						<div class="left" style="margin-bottom: 20px;">
							<a href="javascript:void(0);" onclick="saveChanges();" class="button" >Save changes</a>
						</div>	
					</c:if>			
			    </div> 
			   </div> 
			</form:form>
			</div> 
			</div> 
			
			<div class="right-panel" style="margin-top: 0px;">
			</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
		
	</body>
</html>
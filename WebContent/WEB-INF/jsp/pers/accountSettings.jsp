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
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>

		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
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
			
			.modal-footer {
				padding: 8px;
				text-align: right;
				border-top: 1px solid #E5E5E5;
			}
			
			.btn {
				padding: 4px;
			}
			
			.deactivateButton {
				background-image: linear-gradient(#E63D44, #C11A22);
				background-color: #AB171E;
				box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.22);
				border: 1px solid #920C12;
				color: #FFF;
				text-shadow: 0px -1px rgba(0, 0, 0, 0.11);
				border-radius: 3px;
				float: right;
				margin: 8px 2px 20px;
    			padding: 7px 15px;
    			cursor: pointer;
				font-weight: bold;
				outline: medium none;
				position: relative;
			}
			
			#checkPassword p.availableFail {
			    font-size: 12px;
			    font-weight: bold;
			    color: #AC0000;
			    margin-bottom: 10px;
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
				width: 80%;
				margin: 0px 0px 1px 1px;
				vertical-align: middle;
				white-space: nowrap;
				text-overflow: ellipsis;
				font-size: 14px;
				line-height: 1.4;
			}
			
			textarea {
				border: 1px solid #CCC;
				padding: 5px 3px;
				box-sizing: border-box;
				border-radius: 0px;
				font-size: 13px;
				line-height: 18px;
				width: 94%; 
				height: 160px; 
				resize: none;
			}
		</style>
		
		<script type="text/javascript">

			function profileSettings(){
				var acUrl = 'pers/profileSettings.do';
				window.location.href = acUrl;
			}

			function emailSettings(){
				var emailUrl = 'pers/manageSubscriptions.do';
				window.location.href = emailUrl;
			}

			function checkAvailability(){
				email = document.getElementById('emailAddress').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/checkAvailability.ajx?email="+email,success:function(result){
					$("#checkAvail").html(result);
					// Hide message
			        $("#waitMessage").hide();
				    },
				 	// What to do before starting
			         beforeSend: function () {
				    	$("#checkAvail").html('');
			             $("#waitMessage").show();
			         } 

		    	  });
			}

			function saveAccountSettings(){
				var emailAddress = document.getElementById('emailAddress').value;
				var password = document.getElementById('password').value;
				var confirmPassword = document.getElementById('confirmPassword').value;
				
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateProfile.ajx?section=account"+"&emailAddress="+emailAddress+"&password="+password
					+"&confirmPassword="+confirmPassword ,
					dataType: "json",success:function(result){
					$("#waitAccount").hide();
					if(result.iserror){
						if (result.emailError) {
							$("#checkAvail").html(result.emailError);
						}
						if (result.passwordError) {
							$("#checkPassword").html(result.passwordError);
						} 
					} else {
						var sUrl = "${communityEraContext.contextUrl}/pers/connectionResult.do";
						window.location.href = sUrl;
					}
				    },beforeSend: function () {
			            $("#waitAccount").show();
			        }
			    });
			}

			function deactivateMyAccount(){
				var dialogInstance = BootstrapDialog.show({
					title: 'Deactivate Account',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:200px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            closable: true,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	                dialog.getButton('button-add').disable();
	            },
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }, {
	            	id: 'button-add',
	                label: 'Deactivate My Account',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	            	var data = new FormData();
	    			data.append('reason', '');
	    			data.append('explain', '');
	            	$.ajax({
	            		type:"POST",
	                    url: "${communityEraContext.contextUrl}/pers/deactivateMe.ajx",
	                    data: data,
	                    success:function(result){
	                    	//dialog.close();
	                    		                    	
	        				var dialogInstance = BootstrapDialog.show({
	        					title: 'Your account has been deactivated',
	        					type: BootstrapDialog.TYPE_SUCCESS,
	        					message: function(dialog) {
	        					var $message = $('<div id="main"><p class="seccMsg">To access the full functionality of Jhapak you must reactivate your account.</p></div>');
	        	                return $message;
	        	            },
	        	            cssClass: 'success-dialog',
	        	            closable: true,
	        	            closeByBackdrop: false,
	        	            buttons: [{
	        	                label: 'Close',
	        	                cssClass: 'btn-success',
	        	                action: function(dialog){
	        	            		dialog.close();
	                        	}
	        	            	},{
		        	                label: 'Reactivate Your Account',
		        	                cssClass: 'btn-success',
		        	                action: function(dialog){
		        	            		dialog.close();
		                        	}
		        	            	}]
	        				});
	        			}
	                });
	                	//dialog.close();
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	var sName = "";
					sName += "<div class='' style='margin: 0px 20px; width:100%; color: #201F1E; font-size: 16px; line-height: 24px;'>";
					sName += "<p style='line-height: 2; color: #E63D44'>Are you sure you want to deactivate your account?</p>";
					sName += "<p style='font-weight: bold; font-size: 18px; line-height: 24px;'>Why are you deactivating?<span style='color: #E63D44'>*</span></p>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1001' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I receive too many emails, notifications and requests from Jhapak.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1002' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I have another Jhapak account.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1003' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I need to fix something in my account.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1004' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I don't find Jhapak useful.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1005' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I spend too much time using Jhapak.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1006' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>I don't feel safe on Jhapak.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1007' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>This is temporary. I'll be back.</label></div>";
					sName += "<div style='margin:0px 10px;'><input name='reason' id='1008' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='reason'><span></span>Other</label></div>";
					sName += "<br /><p style='line-height: 2;'>Please explain further</p>";
					sName += "<textarea id='explain' name='additionalMembers'></textarea>";
					sName += "</div>";
	            	dialogRef.getModalBody().find('#main').html(sName);
	            }
		        });
			}

			function changeStatus(){
				alert('ddd');
				var btn = document.getElementById('button-add');
				document.getElementById('button-add').className = "";
				document.getElementById('button-add').className = "newButton";
				document.getElementById('button-add').disabled = false;
			}
			
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				normalQtip();
				dynamicDropDownQtip();
			});
		</script>
		
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<input type="hidden" id="currentUserId" value="${communityEraContext.currentUser.id}" />
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
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="padding: 0px; display: inline-block; width: 100%;">
						<div class="intHeader" style="">
							<span class="settingHdr" onclick="profileSettings();">
								Profile Settings
							</span>
							<span class="settingHdrSelected" style="border-right: 2px solid #CCC; border-left: 2px solid #CCC;" >
								Account Settings
							</span>
							<span class="settingHdr" onclick="emailSettings();">
								E-mail Notifications
							</span>
						</div>
				 		<div class="innerBlock" style="padding: 20px 20px 20px;">
							<c:if test="${command.user.inactive}">
								<p style="color: rgb(137, 143, 156); word-wrap: break-word;">This user is no longer registered. The information below may be obsolete </p>
							</c:if>
							<c:if test="${!command.user.inactive}">
								<a href="javascript:void(0);" onclick="saveAccountSettings('');" class="btnsec save"  style="">Save</a>
								<h2 class="headerSection" >Account Settings</h2>
								<li class='lineseparaterProfile' style="width: 100%;"></li>
								<div id="waitAccount" style="display: none;">
									<div class="cssload-square" >
										<div class="cssload-square-part cssload-square-green" ></div>
										<div class="cssload-square-part cssload-square-pink" ></div>
										<div class="cssload-square-blend" ></div>
									</div>
								</div>
								<div style="float:left; width: 100%; padding-bottom:20px;">
									<div style="float:left; width: 55%;">
										<label for="profileName">E-mail Address:</label><br />
										<input type="text" id="emailAddress" name="emailAddress" style="width: 100%;" class="editInfo" value="${command.emailAddress}" />
										<div id="waitMessage" style="display: none;">
											<div class="cssload-square" style="width: 13px; height: 13px;">
												<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
												<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
												<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
											</div>
										</div>
										<div class="entry" id="checkAvail" style="width: 500px;"></div>
									</div>
									<div style="float:left; width: 40%; ">
										<a href="javascript:void(0);" onclick="checkAvailability();" class="btnsec" style="margin: 22px; float:left;"><i class="fa fa-question" style="font-size: 16px; margin-right: 4px;"></i>Check Availability</a>
									</div>
								</div>
								<div style="float:left; width: 100%; padding-bottom:20px;">
									<div style="float:left; width: 55%;">
										<label for="profileName">Password:</label><br />
										<input type="text" id="password" name="password" style="width: 100%;" class="editInfo" />
										<div class="entry" id="checkPassword" style="width: 600px;"></div>
									</div>
									<div style="float:left; width: 40%; padding:30px 0px 0px 20px;">
									6-12 characters allowed
									</div>
								</div>
								<div style="float:left; width: 100%; padding-bottom:20px;">
									<div style="float:left; width: 55%;">
										<label for="profileName">Confirm Password:</label><br />
										<input type="text" id="confirmPassword" name="confirmPassword" style="width: 100%;" class="editInfo" />
									</div>
								</div>
								<li class='lineseparaterProfile' style="width: 100%;"></li>
								<a href="javascript:void(0);" onclick="deactivateMyAccount();" class="deactivateButton"><i class="fa fa-remove" style="font-size: 16px; margin-right: 4px;"></i>Deactivate my Account</a>
							</c:if>
						</div>
					</div>
				 </div>  
				</div><!-- /#leftPannel -->
				<div class="right-panel" style="margin-top: 0px;">
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
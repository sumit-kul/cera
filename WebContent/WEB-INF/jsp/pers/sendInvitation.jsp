<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<title>Jhapak - Send Invitation</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
	    <link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
	    <link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	    
	    <style type="text/css">
	    	.row {
			    margin-left: auto;
			    margin-right: auto;
			    margin-bottom: 20px;
			}
			
			.collection {
			    margin: 0.5rem 0px 1rem;
			    border: 1px solid #E0E0E0;
			    border-radius: 2px;
			    overflow: hidden;
			    position: relative;
			}
				
			.collection .collection-item.avatar {
			    min-height: 54px;
			    padding-left: 72px;
			    position: relative;
			}
			
			.collection .collection-item {
			    background-color: #FFF;
			    line-height: 1.5rem;
			    padding: 10px 20px;
			    margin: 0px;
			    border-bottom: 1px solid #E0E0E0;
			}    
			
			.collection .collection-item.avatar .circle {
			    position: absolute;
			    width: 50px;
			    height: 50px;
			    overflow: hidden;
			    left: 15px;
			    display: inline-block;
			    vertical-align: middle;
			}
			
			.circle {
			    border-radius: 50%;
			}
			
			.collection .collection-item.avatar p {
			    margin: 0px;
			}
			
			strong {
			    font-weight: 500;
			}
			
			.collection .collection-item.avatar .secondary-content {
			    position: absolute;
			    top: 26px;
			    right: 16px;
			    font-size: 30px;
			}
			.secondary-content {
			    float: right;
			    color: #26A69A;
			}
			a {
			    text-decoration: none;
			    background-color: transparent;
			}
			img {
			    border: 0px none;
			}
			
			.zmdi-hc-2x {
			    font-size: 2em;
			}
			
			.scroll-pane
			{
				width: 100%;
				height: 410px;
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
 			.login-dialog .modal-dialog {
                width: 510px;
            }
            #selectAction{
            	float: right;
            }
            
            #selectAction a {
            	text-decoration: underline;
            	color: #243F52;
            	font-size: 14px;
            	font-weight: bold;
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
			
			input[type="checkbox"]:checked + label span {
			    background:url(img/check_radio_sheet.png) -19px top no-repeat;
			}
			
	    	#communityEra {
			    background: #f5f6f6;
				position: absolute;
				height: 100%;
				width: 100%;
				top: 0;
				left: 0;
				-webkit-font-smoothing: antialiased;
				display: -webkit-box;
				display: -webkit-flex;
				display: -ms-flexbox;
				display: flex;
				-webkit-box-orient: vertical;
				-webkit-box-direction: normal;
				-webkit-flex-direction: column;
				-ms-flex-direction: column;
				flex-direction: column;
				min-height: 100vh;
				color: #323a3b;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				font-weight: 300;
				font-size: 1rem;
				line-height: 1.4;
				vertical-align: baseline;
				margin: 0;
				padding: 0;
				border: 0;
				-moz-box-sizing: border-box;
				box-sizing: border-box;
			}
			
			.banner {
			    padding-bottom: 85px;
			}
			
			.background[data-loaded="true"] {
			    opacity: 1;
			}
			
			.background {
			    background-position: center center;
			    background-repeat: no-repeat;
			    background-size: cover;
			    position: fixed;
				top: 0;
				left: 0;
				bottom: 0;
				right: 0;
				-webkit-transition: opacity .3s ease-in-out;
				transition: opacity .3s ease-in-out;
			}
			
	    	.passMess {
				padding: 10px 0px;
				border-radius: 6px;
			    position: relative;
			    min-height: 26px;
			    width: 92%;
			    background: none repeat scroll 0% 0% #C8FFA4;
			}
			
			.pmessage {
				margin: 0px;
				padding: 2px 47px !important;
				color: #31680D;
				font-size: 1.1em;
			}
			
			.success {
			    position: absolute;
			    top: 50%;
			    left: 10px;
			    margin-top: -13px;
			}

			.carouselCaption {
				box-shadow: 0px 2px 2px 0px rgba(50,58,59,0.15);
				margin-bottom: 1em;
				border-radius: 4px;
				background-color: rgba(255,255,255,0.9);
				position: relative;
				margin-left: auto !important;
				margin-right: auto !important;
				width: 30%;
				top: 2em;
				padding: 1.125em 1.5em;
				background: white;
				-webkit-transition: all 0.2s ease-in-out;
				transition: all 0.2s ease-in-out;
				overflow: hidden;
				opacity: 0.95;
			}
			
			.carouselCaption .lsLogo {
				display: -webkit-box;
				display: -webkit-flex;
				display: -ms-flexbox;
				display: flex;
				margin: 0 auto 0.7em;
				width: 48px;
			    height: 48px;
			}
			
			.carouselCaption .entryField {
				position: relative;
			}
			
			input[type="text"], input[type="password"] {
				padding: 0.15em 1em;
				vertical-align: middle;
				min-height: 34px;
				font-weight: normal;
				color: #828a8b;
				border: 1px solid #cfd2d3;
				font-size: 1em;
				border-radius: 4px;
				-webkit-transition-property: border-color, color, box-shadow;
				transition-property: border-color, color, box-shadow;
				-webkit-transition-duration: 0.15s;
				transition-duration: 0.15s;
				max-width: 100%;
				outline: none;
				display: block;
				width: 94%;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				line-height: 1.4;
			}

			.btn-block {
			    width: 80%;
			    text-align: center;
			}

			.carouselCaption p.infotext {
				text-align: center;
				margin: 10px 0px 0px 0px;
				color: #323a3b;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				font-weight: 300;
				font-size: 1rem;
				line-height: 1.4;
				width: 90%;
				display: inline-block;
			}
				
			a.btnSubmit {
				display: block;
				width: 100%;
				color: white;
				border: 1px solid #005983;
				background-color: #005983;
				text-decoration: none;
				-webkit-appearance: none;
				text-align: center;
				padding: 0.5em 1.38em 0.5em;
				border-radius: 4px;
				font-size: 1.25rem;
				font-weight: normal;
				outline: none;
				cursor: pointer;
				-webkit-transition-property: background, color, border-color, box-shadow;
				transition-property: background, color, border-color, box-shadow;
				-webkit-transition-timing-function: ease;
				transition-timing-function: ease;
				-webkit-transition-duration: 0.16s;
				transition-duration: 0.16s;
				line-height: 1.4;
				-webkit-touch-callout: none;
				-webkit-user-select: none;
				-moz-user-select: none;
				-ms-user-select: none;
				user-select: none;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				box-sizing: border-box;
			}
			 	
			.forgotp a.arrow {
			    padding: 1px 10px 5px 15px;
			}
			
			.forgotp a {
				font-size: 14px;
			    color: #158aa2;
				text-decoration: none;
				cursor: pointer;
				font-weight: normal;
				float: right;
			}
			
			#customBtn {
			    width: 90%;
			}
			
			#customBtnFB {
			    width: 90%;
			    margin-top: 10px;
			}
			
			#linkedin-login-button {
			    width: 90%;
			}

			.btn-lg {
    			padding: 10px 0px;
    			font-size: 16px;
    		}
    		
    		.btn-social.btn-lg {
			    padding-left: 10%;
			}
			
			.passMess {
				border-radius: 6px;
			    position: relative;
			    width: 72%;
			    background: none repeat scroll 0% 0% #C8FFA4;
			    padding: 14px;
				margin-top: 10px;
			}
			
			.failMess {
				border-radius: 6px;
			    position: relative;
			    width: 72%;
			    background: none repeat scroll 0% 0% #ff6f6f;
			    padding: 14px;
				margin-top: 10px;
			}
			
			.almostfullwidth {
				width: 98%;
				height: 40px;
				margin-top: 6px;
				margin-bottom: 6px;
				padding: 4px;
			}

			@media only screen and (min-width: 769px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/sendInvitation_1.jpg);
				}
			}
			
			@media only screen and (min-width: 768px) and (max-width: 940px) {
				.carouselCaption {
					width: 40%
				}
			}
			
			/* Extra small devices (phones, up to 480px) */
			@media screen and (min-width: 481px) and (max-width: 767px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/sendInvitation_2.jpg);
				}
				
				.carouselCaption {
					width: 56%
				}
			}
			
			@media only screen and (max-width: 480px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/sendInvitation_3.jpg);
				}
				
				.carouselCaption {
					width: 78%;
					top: 1em;
					padding: 1em 1.5em;
				}
				
				.btn-lg {
				    padding: 5px;
				    font-size: 14px;
			    }
			    
			    .btn-social.btn-lg > :first-child {
				    width: 30px;
				    font-size: 1.3em;
				    line-height: 30px;
				}
				
    			.forgotp a {
			    	font-size: 12px;
			    }
			    
			    .carouselCaption .lsLogo {
			    	width: 30px;
			    	height: 30px;
			    }
			    
				input[type="text"], input[type="password"] {
					min-height: 28px;
				}
				
				a.btnSubmit {
					font-size: 1.10rem;
					line-height: 1.2;
				}
			}
	    </style>
	    
		<script type="text/javascript"> 
			(function ($) {
				$(function () {
					$("a#customBtnFB").on("click", function () {
						FB.ui({
							  method: 'send',
							  link: 'https://jhapak.com'
							});
					});
				});
			})(jQuery);
		</script>
		
    	<script type="text/javascript"> 
		   var gFriends = null;
		   var gShareRecipient = null;

           function checkAuth() {
               gapi.auth.authorize({
            	   client_id: '780434385765-37gcdkfnviimsp3q6948bq810i485pl7.apps.googleusercontent.com',
            	   cookiepolicy: 'single_host_origin',
			        immediate: false,
			        scope: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/contacts.readonly'
               }, handleAuthResult);
           }
           
           function handleAuthResult(authResult) {
               if (authResult && !authResult.error) {
                   $.ajax({
                       url: "https://www.google.com/m8/feeds/contacts/default/full?alt=json&access_token=" + authResult.access_token + "&max-results=2000&v=3.0",
                       dataType: 'jsonp'
                   }).success(function(data) {
                       //console.log(JSON.stringify(data));
                       var type = BootstrapDialog.TYPE_INFO;
                       BootstrapDialog.show({
   		                type: type,
   		             	title: 'Invite your friends',
   		             	message: function(dialog) {
   							var $message = $('<div id="main"><div class=""><strong id="totalfound"></strong><strong id="selectAction">'+
   		   							'<a onclick="selectAll(0);" href="javascript:void(0);"  id="selectall" style="display: none;">Select All</a><a onclick="unselectAll(0);" href="javascript:void(0);" id="unselectall" >Unselect All</a>'+
   		   							'</strong></div><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:200px; display: none;" class="showCloudWaitMessage" ></p>'+
   		   							'<div class="row"><div class="scroll-pane horizontal-only"> <ul id="contact-list" class="collection list"></ul></div></div></div>');
   			                return $message;
   			            },
   		                closable: true,
   		                closeByBackdrop: false,
   		                closeByKeyboard: false,
   		                buttons: [{
   		                	label: 'Ask selected friends',
   		                	cssClass: 'btn-danger',
   		                    action: function(dialogRef){
   		                    	var cUser = data.feed.id['$t'];
   		                		var json = []; 
		   		             	$('input:checkbox[name=selectContact]:checked').each(function(i, selected){ 
		   		             		json[i] = $(selected).attr('id');
		   		             	});
		   		                 $.ajax({
		   		                 	type:"POST",
		   		                 	url:"${communityEraContext.contextUrl}/pers/manageSocialContacts.ajx?action=invite",
		   		                 	data: {userMail:cUser, json:json},
		   		 		            success:function(result){
			   		 		            if (result.isNewUser) {
			   		 		            window.location.href = '${communityEraContext.contextUrl}/jlogout.do';
										}
		   		                 	 	dialogRef.close();
		   		     			    }
		   		     		    });
   		                       
   		                    }
   		                }],
   			            cssClass: 'login-dialog',
   			            onshown: function(dialogRef){
   	                       var count = 0;
						   var itemId = "";
						   
   	                       $.each(data.feed.entry, function(i, item) {
   	                    	if (item.hasOwnProperty('gd$email')) {
   	   	                    	var email = item.gd$email[0]['address'];
   	   	                  		var checked = "checked";
   	                               count++
   	                               itemId = "gitem_"+count;
   	                               var html = '<li class="collection-item avatar" id="'+itemId+'">';
   	                               if (item.link[0]['href']) {
   	                                   var imagesrc = item.link[0]['href'] + '&access_token=' + authResult.access_token;
   	                                   html += '<img src="' + imagesrc + '" class="circle" onerror="this.src=\'img/user_icon.png\'" > ';

   	                               }else{
   	                            	 html += '<img class="circle" src="img/user_icon.png" > ';
   	                               }
   	                               html += '<p class="name"><strong>' + item.title.$t + '<\strong><br>';

   	                               if (item.hasOwnProperty('gd$phoneNumber')) {
   	                                   html += '<i class="zmdi zmdi-phone"></i></a> ' + item.gd$phoneNumber[0]['$t'] + '<br>';
   	                               }
   	                                   html += email + '</p>';
   	 							
   	                            	html += "<input name='selectContact' id='"+email+"#~~#"+item.title.$t+"' "+checked+" tabindex='2' type='checkbox' /><label for='"+email+"#~~#"+item.title.$t+"' class='secondary-content'><span></span></label>";
   	                    		}
   	                           $('#contact-list').append(html);
   	                       });
   	                    dialogRef.getModalBody().find('#totalfound').append(count + " Contacts Found");
   	                    	dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
   			            }
   		            });
                    
                   }); //Success
               } //if
           } //function 

           function selectAll()
			{
				var checkboxes = document.getElementsByName('selectContact'), val = null;    
			     for (var i = 0; i < checkboxes.length; i++)
			     {
			         if (checkboxes[i].type == 'checkbox')
			         {
			             if (val === null) val = checkboxes[i].checked;
			             checkboxes[i].checked = true;
			         }
			     }
			     document.getElementById('unselectall').style.display = "inline";
			     document.getElementById('selectall').style.display = "none";
			}

			function unselectAll()
			{
				var checkboxes = document.getElementsByName('selectContact'), val = null;    
			     for (var i = 0; i < checkboxes.length; i++)
			     {
			         if (checkboxes[i].type == 'checkbox')
			         {
			             if (val === null) val = checkboxes[i].checked;
			             checkboxes[i].checked = false;
			         }
			     }
			     document.getElementById('unselectall').style.display = "none";
			     document.getElementById('selectall').style.display = "inline";
			}

         </script> 
	    
	    <script type="text/javascript">
		    $(document).ready(function () {
		    	var stMessage = "I would like to invite you to join Jhapak.";
		    	document.getElementById("messageArea").value = stMessage;
		    });
	
		    function inviteFriends() {
		    	document.sendInviteForm.action = "${communityEraContext.requestUrl}";
				document.sendInviteForm.submit();
		    }
		</script>
	</head>

	<body id="communityEra">
		<div class="sticky" id="sticky"></div>
		<div class="banner" >
			<div class="background"  >
				<div class="carouselCaption">
					<a href="${communityEraContext.contextUrl}/welcomeToCommunities.do">
						<img src="images/link_Image.png" style="image-rendering: pixelated;" class="lsLogo" />
					</a>
					<div class="sec">
						<a class="btn btn-block btn-social btn-lg btn-google googleContactsButton" id="customBtn" onclick="checkAuth();" >
						    <i class="fa fa-google-plus"></i> Invite google friends
						</a>
						<div id="waitMessageG" style="display: none; position: absolute; top: 136px; right: 60px;">
							<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
						</div>
	  					<a class="btn btn-block btn-social btn-lg btn-facebook" id="customBtnFB" >
		  					<i class="fa fa-facebook"></i> Invite facebook friends
		  				</a>
		  				<div id="waitMessageF" style="display: none; position: absolute; top: 206px; right: 60px;">
		  					<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
		  				</div>
		  				<p class="infotext">or invite using email</p>
					</div>
					<div class="welcomeSignupright">
						<form:form showFieldErrors="true" method="post" name="sendInviteForm" id="sendInviteForm">	
							<div style="margin-top:6px;">
								<div class="entryField" >
									<label  for="email" style="text-align: left;">Email addresses (50 friends in one go, each on a new line)</label> <br />
									<form:textarea path="invitedFriends" cssClass="almostfullwidth" cols="5"/>
								</div>
								<div class="entryField" >
									<label for="password">Invitation message</label> <br />
									<form:textarea path="message" cssClass="almostfullwidth" id="messageArea"/>
								</div>
								<span style="margin: 10px; display: inline-block;">
									By continuing, you're giving us permission to invite selected contacts to join Jhapak. 
									This invitation will include your name and photo.
								</span>
								<div class="entryField" >
									<a href="javascript:void(0);" onclick="inviteFriends();" class="btnSubmit" >Invite</a>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
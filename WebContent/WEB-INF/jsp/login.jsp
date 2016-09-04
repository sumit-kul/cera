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
		<title>Jhapak - Login</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader2.jspf" %>
	    <%@ include file="/WEB-INF/jspf/header2.jspf" %>
	    <link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
	    <link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	    
	    <style type="text/css">
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
				opacity: 0.9;
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
				margin-bottom: 1em;
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
				margin: 10px;
				color: #323a3b;
				font-family: "Proxima", Arial, Helvetica, sans-serif;
				font-weight: 300;
				font-size: 1rem;
				line-height: 1.4;
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
			}
			
			#linkedin-login-button {
			    width: 90%;
			}

			.btn-lg {
    			padding: 10px 0px;
    		}
    		
    		.btn-social.btn-lg {
			    padding-left: 10%;
			}

			@media only screen and (min-width: 769px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/logIn_1.jpg);
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
				 	background-image:url(${communityEraContext.contextUrl}/images/logIn_2.jpg);
				}
				
				.carouselCaption {
					width: 56%
				}
			}
			
			@media only screen and (max-width: 480px) {
				.background {
				 	background-image:url(${communityEraContext.contextUrl}/images/logIn_3.jpg);
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
	    
	    <script type="text/javascript" src="//platform.linkedin.com/in.js">
	    	lang: en_US
		    api_key: 81quho30d1dbck
		    scope: r_basicprofile r_emailaddress
		    authorize: true
		</script>
	    
		<script type="text/javascript">
			function loginSubmit(){
				document.loginMeForm.action = "${communityEraContext.contextUrl}/jlogin.do";
				document.loginMeForm.submit();
			}
		</script>
		
		<script type="text/javascript">
			function submitLinkedInProfile(data) {
			  $.ajax({url:"${communityEraContext.contextUrl}/loginSN.ajx",
				    method: "POST",
				    data: data,
				    processData: false,
				    contentType: false,
				    success: function () {
				  		$("#waitMessageL").css('display','none');
					  	location.reload();
					  //$("#showLoginWaitMessage").hide();
				    	}
				 	});
		  		}
			function displayLinkedInProfiles(profiles) {
			    var member = profiles.values[0];
			    var data = new FormData();
				data.append('firstName', member.firstName);
				data.append('lastName', member.lastName);
				data.append('email', member.emailAddress);
				data.append('loginId', member.id);
				data.append('photoUrl', member.pictureUrl);
				data.append('about', member.headline);
				data.append('gender', member.gender);
				data.append('birthday', member.dateofbirth);
				data.append('snType', 3); 
				submitLinkedInProfile(data);
			}
			function onLinkedInAuth() {    
			    IN.API.Profile("me").fields([ "id","firstName", "location","lastName","phone-numbers","emailAddress","mainAddress", "summary", "picture-url", "date-of-birth"]).result(displayLinkedInProfiles);
			}
			function LinkedINAuth()
			{
				IN.UI.Authorize().place();
			}
			function onLinkedInLoad() {
				$("#waitMessageL").css('display','inline-block');
				LinkedINAuth();
				IN.Event.on(IN, "auth", function () { onLinkedInAuth(); });
				IN.Event.on(IN, "logout", function () { onLinkedInAuth(); });
			}
		</script>
	    
		<script type="text/javascript">
			function fetch(token) {
	          $.ajax({
	            url: 'https://www.google.com/m8/feeds/contacts/default/full?alt=json&access_token=' + token + '&max-results=2000&v=3.0',
	            dataType: 'jsonp'
	          }).success(function(data) {
	        	  var json = [];
                     $.each(data.feed.entry, function(i, item) {
                   	if (item.hasOwnProperty('gd$email')) {
                   		var obj = new Object();
                   		obj.email = item.gd$email[0]['address'];
                   		if (item.hasOwnProperty('gd$name')) {
                       		var lname = item.gd$name;
                     			if (lname.hasOwnProperty('gd$givenName')) {
                      		  console.log(JSON.stringify(lname.gd$givenName, undefined, 2));
                      		obj.firstName = lname.gd$givenName.$t;
                      	  	}
                      		if (lname.hasOwnProperty('gd$familyName')) {
                      		  console.log(JSON.stringify(lname.gd$familyName, undefined, 2));
                      		obj.lastName = lname.gd$familyName.$t;
                      	  	}
                     	  	}
  	                   		var phNumber = "";
                           if (item.hasOwnProperty('gd$phoneNumber')) {
                        	   phNumber = item.gd$phoneNumber[0]['$t'];
                           }
                           obj.phNumber = phNumber;
                           var imagesrc = "";
                           if (item.link[0]['href']) {
                           	imagesrc = item.link[0]['href'] + '&access_token=' + token;
                           }
                           json[i] = obj;
                   	}
                    });
                     var jsonString= JSON.stringify(json);
                     $.ajax({
		                 	type:"POST",
		                 	url:"${communityEraContext.contextUrl}/pers/manageSocialContacts.ajx?action=saveContacts",
		                 	data: {jsonString:jsonString},
		 		            success:function(result){
		                 		$("#waitMessageG").css('display','none');
		                 		 location.reload();
		     			    }
		     		    });
	            });
	        }

			function handleAuthResult(authResult) {
	               if (authResult && !authResult.error) {
	            	   $("#waitMessageG").css('display','inline-block');
	            	   $.ajax({
	                       url: "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + authResult.access_token,
	                       dataType: 'jsonp'
	                   }).success(function(data) {
		   			    	var fdata = new FormData();
		   			    	fdata.append('firstName', data.given_name);
		   			    	fdata.append('lastName', data.family_name);
		   			    	fdata.append('email', data.email);
		   			    	fdata.append('loginId', authResult.access_token);
		   			    	fdata.append('photoUrl', data.picture);
		   			    	fdata.append('pLink', data.link);
		   			    	fdata.append('gender', data.gender);
		   			    	fdata.append('snType', 1);
							$.ajax({url:"${communityEraContext.contextUrl}/loginSN.ajx",
							    method: "POST",
							    data: fdata,
							    processData: false,
							    contentType: false,
							    success: function () {
									fetch(authResult.access_token);
							    }
							  });	                	                 
	                   }); 
	               } //if
	           } //function 

			function checkAuth() {
	        	   $("#waitMessageG").css('display','inline-block');
	               gapi.auth.authorize({
	            	   client_id: '780434385765-37gcdkfnviimsp3q6948bq810i485pl7.apps.googleusercontent.com',
	            	   cookiepolicy: 'single_host_origin',
				        immediate: false,
				        scope: 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email https://www.google.com/m8/feeds'
	               }, handleAuthResult);
	           }

			
			(function ($) {
				$(function () {
					$("#customBtnFB").on("click", function () {
						$("#waitMessageF").css('display','inline-block');
						FB.login(function(response) {
							   if (response.authResponse) 
							   {
								   console.log(JSON.stringify(response));
							    	getUserInfo();
					  			} else 
					  			{
					  	    	 console.log('User cancelled login or did not fully authorize.');
					   			}
							 },{scope: 'public_profile,email,user_about_me,user_location'});
					});
				});
			})(jQuery);

			function getUserInfo() {
				$("#waitMessage").css('display','inline-block');
				FB.api('/me?fields=first_name,last_name,link,gender,email,bio,birthday,cover,website,location,picture', function(response) {
					
					
		    	var data = new FormData();
				data.append('firstName', response.first_name);
				data.append('lastName', response.last_name);
				data.append('link', response.link);
				data.append('gender', response.gender);
				data.append('about', response.bio);
				data.append('birthday', response.birthday);
				data.append('cover', response.cover);
				data.append('website', response.website);
				data.append('location', response.location);
				data.append('photoUrl', response.picture.data.url);
				data.append('email', response.email);
				data.append('loginId', response.id);
				data.append('snType', 2);
				  $.ajax({url:"${communityEraContext.contextUrl}/loginSN.ajx",
				    method: "POST",
				    data: data,
				    processData: false,
				    contentType: false,
				    success: function () {
					  $("#waitMessageF").css('display','inline-block');
					  location.reload();
				    }
				  });
				});
		    }
		</script>
	</head>

	<body id="communityEra">
		<div class="banner" >
			<div class="background"  >
				<div class="carouselCaption">
					<a href="${communityEraContext.contextUrl}/welcomeToCommunities.do">
						<img src="images/link_Image.png" style="image-rendering: pixelated;" class="lsLogo" />
					</a>
					<div class="sec">
						<a class="btn btn-block btn-social btn-lg btn-google" id="customBtn" style="" onclick="checkAuth();">
						    <i class="fa fa-google-plus"></i> Sign in with Google
						</a> 
						<div id="waitMessageG" style="display: none; position: absolute; top: 136px; left 250px;">
							<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
						</div>
	  					<br /> 
		  				<a class="btn btn-block btn-social btn-lg btn-facebook" id="customBtnFB" style="" >
		  					<i class="fa fa-facebook"></i> Sign in with Facebook
		  				</a> 
		  				<div id="waitMessageF" style="display: none; position: absolute; top: 206px; left 250px;">
		  					<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
		  				</div>
		  				<br /> 
		  				 <a class="btn btn-block btn-social btn-lg btn-linkedin" id="linkedin-login-button" style="" onclick="onLinkedInLoad();">
		  					<i class="fa fa-linkedin"></i> Sign in with LinkedIN
		  				</a> 
		  				<div id="waitMessageL" style="display: none; position: absolute; top: 278px; left 250px;">
		  					<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
		  				</div>
		  				<p class="infotext">or Log in with email</p>
					</div>
					<div class="welcomeSignupright">
						<form:form showFieldErrors="true" method="post" name="loginMeForm" id="loginMeForm">	
							<c:if test="${not empty command.emailAddress}">	<c:set var="uname" value="${command.emailAddress}" /></c:if>
							<c:if test="${empty command.emailAddress}">	<c:set var="uname" value="" /></c:if>
							<c:if test="${not empty command.password}">	<c:set var="pword" value="${command.password}" /></c:if>
							<c:if test="${empty command.password}">	<c:set var="pword" value="" /></c:if>
	
							<div style="margin-top:6px;">
								<div class="forgotp" style="float:left;width:100%;margin-bottom:2px;" >
									<c:choose>
										<c:when test="${not empty cecLoginAttribute.loginMessage}">
												<span class="errorText" style="padding-right: 20px;">${cecLoginAttribute.loginMessage}</span><br/>
										</c:when>
										<c:when test="${not empty command.loginMessage}">
												<span class="errorText" style="padding-right: 20px;">${command.loginMessage}</span><br/>
										</c:when>
										<c:when test="${not empty cecFMyPassword.loginMessage}">
											<div class="passMess"> 
												<img src="images/passSen.png" class="success" />
												<p class="pmessage">${cecFMyPassword.loginMessage}</p> 
											</div>
										</c:when>
										<c:when test="${not empty sucsFMyPassword.loginMessage}">
											<div class="passMess"> 
												<img src="images/passSen.png" class="success" />
												<p class="pmessage">${sucsFMyPassword.loginMessage}</p> 
											</div>
										</c:when>
									</c:choose>
								</div>
								<div class="entryField" >
									<label  for="email" style="text-align: left;">Email</label> <br />
									<input type="text" value="${uname}" id="email" name="j_username" class="text"/>
								</div>
								<div class="entryField" >
									<label for="password">Password</label> <br />
									<input type="password" id="password" name="j_password" value="${pword}" class="text" />
								</div>
								<div class="entryField" >
									<a href="javascript:void(0);" onclick="loginSubmit();" class="btnSubmit" >
										<i class="fa fa-sign-in" style="margin-right: 8px;"></i>Log In</a>
									<input type="hidden" name="_acegi_security_remember_me" value="1"/>
								</div>
								
								<div>
									<div class="forgotp" style="float: left; margin: 4px 8px; width: 100%;">
										<a href="${communityEraContext.contextUrl}/pers/fPassword.do" class="arrow">Forgot password?</a>
										<a href="${communityEraContext.contextUrl}/pers/registerMe.do" class="arrow" style="float: left">Sign up</a>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
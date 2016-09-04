<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="Jhapak">
		<title>Jhapak - Login</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
	    <%@ include file="/WEB-INF/jspf/header2.jspf" %>
	    <link rel="stylesheet" media="all" type="text/css" href="css/registerStyle.css" />
	    <link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	    
	    <style type="text/css">
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
	    </style>
	    
	    <script type="text/javascript">
			function loginSubmit(){
				document.loginMeForm.action = "${communityEraContext.contextUrl}/jlogin.do";
				document.loginMeForm.submit();
			}
			$(document).ready(function () {
				document.getElementById('customBtn').click();
			});
		</script>
	    
		<script>
		  var googleUser = {};
		  var startApp = function() {
		    gapi.load('auth2', function(){
		      // Retrieve the singleton for the GoogleAuth library and set up the client.
		      auth2 = gapi.auth2.init({
		        client_id: '780434385765-37gcdkfnviimsp3q6948bq810i485pl7.apps.googleusercontent.com',
		        cookiepolicy: 'single_host_origin',
		        // Request scopes in addition to 'profile' and 'email'
		        scope: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/contacts.readonly'
		      });
		      attachSignin(document.getElementById('customBtn'));
		    });
		  };
		
		  function attachSignin(element) {
		    auth2.attachClickHandler(element, {},
		        function(googleUser) {
		        var prof = googleUser.getBasicProfile();
		    	$("#showLoginWaitMessage").show();
		    	var data = new FormData();
				data.append('firstName', prof['Za']);
				data.append('lastName', prof['Na']);
				data.append('email', googleUser.getBasicProfile().getEmail());
				data.append('loginId', googleUser.getAuthResponse().id_token);
				data.append('photoUrl', prof['Ph']);
				data.append('snType', 1);
				  $.ajax({url:"${communityEraContext.contextUrl}/loginSN.ajx",
				    method: "POST",
				    data: data,
				    processData: false,
				    contentType: false,
				    success: function () {
					  auth();
					  location.reload();
					  $("#showLoginWaitMessage").hide();
				    }
				  });
		        }, function(error) {
		          alert(JSON.stringify(error, undefined, 2));
		          $("#showLoginWaitMessage").hide();
		        });
		  }
	
	      function auth() {
	    	  gapi.auth.authorize({
                  client_id: '780434385765-37gcdkfnviimsp3q6948bq810i485pl7.apps.googleusercontent.com',
                  scope: 'https://www.googleapis.com/auth/gmail.readonly',
                  immediate: true
              }, handleAuthResult);
	        }
	        function handleAuthResult(authResult)  {
	        	$.ajax({
                    url: "https://www.google.com/m8/feeds/contacts/default/full?alt=json&access_token=" + authResult.access_token + "&max-results=2000&v=3.0",
                    dataType: 'jsonp'
                }).success(function(data) {
                	var json = [];
                      $.each(data.feed.entry, function(i, item) {
                    	if (item.hasOwnProperty('gd$email')) {
                    		var obj = new Object();
                    		obj.email = item.gd$email[0]['address'];
                    		obj.name = item.title.$t;
   	                   		var phNumber = "";
                            if (item.hasOwnProperty('gd$phoneNumber')) {
                         	   phNumber = item.gd$phoneNumber[0]['$t'];
                            }
                            obj.phNumber = phNumber;
                            json[i] = obj;
                    	}
                     });
                      var jsonString= JSON.stringify(json);
                      $.ajax({
 		                 	type:"POST",
 		                 	url:"${communityEraContext.contextUrl}/pers/manageSocialContacts.ajx?action=saveContacts",
 		                 	data: {jsonString:jsonString},
 		 		            success:function(result){
 		                 		location.reload();
 		     			    }
 		     		    });
	            });
	        }
	        
			(function ($) {
				$(function () {
					$("#customBtnFB").on("click", function () {
						FB.login(function(response) {
							   if (response.authResponse) 
							   {
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
			  	$("#showLoginWaitMessage").show();
				FB.api('/me?fields=first_name,last_name,link,gender,email,bio,birthday,cover,website,location,picture', function(response) {
		    	//alert(JSON.stringify(response));
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
					  location.reload();
					  $("#showLoginWaitMessage").hide();
				    }
				  });
				});
		    }
		
		</script>
	</head>

	<body id="communityEra">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
	
			<div id="container" >
				<div class="welcomeSignupright" style="padding: 30px;">
					<div class="innerBlock" style="margin: unset; width: 58%; float: left; border-right: 1px solid #E2E2E2; min-height: 358px;">
						<h1 style="padding-bottom:10px;">Login to Jhapak</h1>
						<form:form showFieldErrors="true" method="post" name="loginMeForm" >	
							<c:if test="${not empty command.emailAddress}">	<c:set var="uname" value="${command.emailAddress}" /></c:if>
							<c:if test="${empty command.emailAddress}">	<c:set var="uname" value="" /></c:if>
							<c:if test="${not empty command.password}">	<c:set var="pword" value="${command.password}" /></c:if>
							<c:if test="${empty command.password}">	<c:set var="pword" value="" /></c:if>
	
							<div style="margin-top:6px;">
								<div class="forgotp" style="float:left;width:100%;margin-bottom:20px;" >
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
											<br />Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).
										</c:when>
										<c:when test="${not empty sucsFMyPassword.loginMessage}">
											<div class="passMess"> 
												<img src="images/passSen.png" class="success" />
												<p class="pmessage">${sucsFMyPassword.loginMessage}</p> 
											</div>
											<br />Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).
										</c:when>
										<c:otherwise>
											<br />Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).
										</c:otherwise>
									</c:choose>
								</div>
								<br />
								<div class="left" style="width:100%;">
									<label  for="email" style="text-align: left;">Email</label> <br />
									<input style="width:70%;" type="text" value="${uname}" id="email" name="j_username" class="text"/><br/>
								</div>
								<br /> 
								<div class="left" style="width:100%;margin:10px 0px 20px 0px;">
									<label for="password">Password</label> <br />
									<input style="width:70%;" type="password" id="password" name="j_password" value="${pword}" class="text" />
								</div>
							
								<c:if test="${not empty cecLoginAttribute.loginMessage || not empty command.loginMessage}">
									<p style="margin-bottom:20px;">Please log in using your e-mail address and password (or <a href="${communityEraContext.contextUrl}/pers/registerMe.do">click here to register</a>).</p>
								</c:if>
								
								<div class="left" >
									<a href="javascript:void(0);" onclick="loginSubmit();" class="btnmain" style="float: left; width: 20%; line-height: 24px; height: 26px;"><i class="fa fa-sign-in" style="margin-right: 8px;"></i>Log In</a>
									<input type="hidden" name="_acegi_security_remember_me" value="1"/>
									<div class="forgotp" style="float: left; margin: 4px 8px;">
										<a href="${communityEraContext.contextUrl}/pers/fPassword.do" class="arrow">Forgot your password?</a>
									</div>
								</div>
							</div>
						</form:form>
					</div>
					<div class="innerBlock" style="width: 40%; float: left; padding: 50px 6px; min-height: 258px;">
						<div style="margin: 50px 26px; width: 76%; float: left;">
							<a class="btn btn-block btn-social btn-lg btn-google" id="customBtn" style="width: unset; padding-left: 58px; margin-top: 0px;">
							    <i class="fa fa-google-plus"></i> Sign in with Google
							</a>
						    <div id="name"></div>
		  					<script>startApp();</script>
		  					<br /> 
			  					<a class="btn btn-block btn-social btn-lg btn-facebook" id="customBtnFB" style="width: unset; padding-left: 58px; margin-top: 6px;" >
			  					<i class="fa fa-facebook"></i> Sign in with Facebook</a> 
						</div>
					</div>
					
				</div>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
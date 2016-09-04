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
		<meta name="keywords" content="Jhapak, event, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>Jhapak - New Event</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
        <link rel="stylesheet" href="css/DatePicker/default.css" type="text/css" />
        <link type="text/css" rel="stylesheet" href="css/DatePicker/shCoreDefault.css" />
        
        <link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
        
        <script type="text/javascript" src="js/DatePicker/XRegExp.js"></script>
        <script type="text/javascript" src="js/DatePicker/shCore.js"></script>
        <script type="text/javascript" src="js/DatePicker/shLegacy.js"></script>
        <script type="text/javascript" src="js/DatePicker/shBrushJScript.js"></script>
        <script type="text/javascript" src="js/DatePicker/shBrushXml.js"></script>
        
        <script type="text/javascript" src="js/DatePicker/zebra_datepicker.js"></script>
        <script type="text/javascript" src="js/DatePicker/core.js"></script>
		<link rel="stylesheet" type="text/css" href="css/DatePicker/jquery.ptTimeSelect.css" />
 		<script type="text/javascript" src="js/DatePicker/jquery.ptTimeSelect.js"></script>
 		
 		<script src="ckeditor/ckeditor.js"></script>
 		
 		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
 		<link rel="stylesheet" type="text/css" href="css/events.css" />
 		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
 		
		<style type="text/css">
			.checkbox-cell label {	
				margin-right: 5px;	
				float: left !important;	
				font-weight: normal !important;
				width: auto !important;		
			}
			
			#rollup { 	
				width: auto !important;		
			}
			
			div.tag-picker {	
				position: relative;		
				top: 5px;	
				width: 94%;		
			}	
			
			span.tag-picker-label {	
				position: relative; 		
				top: 5px;
			}
			
			.ui-widget-content {
			    border: 1px solid #DDD;
			    background: url('images/ui-bg_highlight-soft_100_eeeeee_1x100.png') repeat-x scroll 50% top #EEE;
			    color: #333;
			}
			
			.ui-widget, .Zebra_DatePicker {
			    font-family: Arial,Helvetica,sans-serif;
			    font-size: 11px;
			}
			
			#ptTimeSelectCntr .ui-widget.ui-widget-content {
				width: 200px;
			}
			
			#ptTimeSelectCntr .ui-widget-header {
			    padding: 0px;
			    height: 26px;
			}
			
			.ui-widget-header {
			    background: none repeat scroll 0% 0% #0072A7;
				border: 3px solid #0072A7;
			    color: #FFF;
			    font-weight: bold;
			}
			
			.ui-widget-header .ui-state-default {
			    border: 1px solid #CCC;
			    background: url('images/ui-bg_glass_100_f6f6f6_1x400.png') no-repeat 2px 2px #F6F6F6;
			    font-weight: bold;
			    color: #1C94C4;
			}
			
			.ui-widget-header .ui-state-default:hover {
			    background: url('images/ui-bg_glass_100_f6f6f6_1x400.png') no-repeat 2px 2px #F6F6F6;
			}
			
			.login-dialog .modal-dialog {
                width: 788px;
            }
            
            .modal-body {
			    padding: 0px;
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
				padding-top: 10px;
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
				height: 200px;
				border: 1px solid #aaa;
				overflow: hidden;
				background-repeat: no-repeat;
				cursor: move;
			}
			
			.imageBox .thumbBox {
				position: absolute;
				width: 100%;
				height: 100%;
				background: none repeat scroll 0% 0% transparent;
			}
			
			.container {
			    position: absolute;
				top: 0%;
				left: 0%;
				right: 0%;
				bottom: 0%;
				width: 100%;
				padding: 0px;
				height: 100%;
			}
			
			.instructionDrag {
			    display: block;
			    line-height: 26px;
			    position: absolute;
			    text-align: center;
			    top: 95px;
			    width: 100%;
			}
			
			.instructions {
			    background: no-repeat scroll 9px 8px rgba(84, 97, 133, 0.4);
			    border-radius: 2px;
			    box-shadow: 0px 1px 0px rgba(0, 0, 0, 0.12) inset;
			    color: #FFF;
			    display: inline;
			    font-size: 13px;
			    font-weight: bold;
			    padding: 4px 9px 6px 9px;
			}
			
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            #container .inputForm .innerBlock form input.disField{
            	background: #F5F5F5 none repeat scroll 0% 0%;
            }
		</style>
		
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
        <script>
	      function initialize(lat, lng) {
	        var mapCanvas = document.getElementById('map_canvas');
	        var mapOptions = {
	          center: new google.maps.LatLng(lat, lng),
	          zoom: 8,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        }
	        var map = new google.maps.Map(mapCanvas, mapOptions)
	      }
	      //google.maps.event.addDomListener(window, 'load', initialize);
	    </script>
		
		
		<script type="text/javascript">
			var placeSearch, autocomplete;
			var componentForm = {
					locality: 'long_name',
					postal_code: 'long_name',
					administrative_area_level_1: 'long_name',
			  		country: 'long_name',
			  		premise: 'long_name'
			};

			var options = {
					  bounds: defaultBounds,
					  types: ['geocode']
					};
			
	        function findLocation() {
	    	  autocomplete = new google.maps.places.Autocomplete((document.getElementById('locationField')),
	    			  options);
	    	  google.maps.event.addListener(autocomplete, 'place_changed', function() {
	    	    fillInAddress();
	    	  });
	    	}

	        function fillInAddress() {
	    	  // Get the place details from the autocomplete object.
	    	  var place = autocomplete.getPlace();
	    	  var add = place.formatted_address;
	    	  document.getElementById('address').value = add;
	    	  var lat = place.geometry.location.lat();
	    	  var lng = place.geometry.location.lng();
	    	  
	    	  // Get each component of the address from the place details
	    	  // and fill the corresponding field on the form.
	    	  for (var i = 0; i < place.address_components.length; i++) {
	    	    var addressType = place.address_components[i].types[0];
	    	    if (componentForm[addressType]) {
	    	      var val = place.address_components[i][componentForm[addressType]];
	    	      document.getElementById(addressType).value = val;
	    	    }
	    	  }
	    	  document.getElementById("hcity").value = document.getElementById("locality").value;
	    	  document.getElementById("hstate").value = document.getElementById("administrative_area_level_1").value;
	    	  document.getElementById("hpostalCode").value = document.getElementById("postal_code").value;
	    	  document.getElementById("hcountry").value = document.getElementById("country").value;
	    	  document.getElementById("latitude").value = lat;
	    	  document.getElementById("longitude").value = lng;
	    	  
	    	  initialize(lat, lng);
	    	}
	    </script>
		
		<script type="text/javascript">
            SyntaxHighlighter.defaults['toolbar'] = false;
            SyntaxHighlighter.all();

            $(document).ready(function () {
            	normalQtip();
            	findLocation();
            	$("#name").attr("placeholder", "Give event a short distinct name");
            	$("#premise").attr("placeholder", "Venue");
            	$("#address").attr("placeholder", "Address");
            	$("#locality").attr("placeholder", "City");
            	$("#administrative_area_level_1").attr("placeholder", "State");
            	$("#postal_code").attr("placeholder", "Zip/Postal code");
            	$("#country").attr("placeholder", "Country");
            });
        </script>
        
        <script type="text/javascript">
			function publishEvent(finalVersion){
				var toElm = document.getElementById('tags');
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
		                message: 'Special characters like &, #, @, etc are not allowed in tags',
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
				} else {
					var somval = $( "input:radio[name=newEventType]:checked" ).attr('id');
					if(somval == 1000){
						document.getElementById('eventType').value = 0;
					}
					if(somval == 2000){
						document.getElementById('eventType').value = 1;
					}
					document.newEventForm.action = "${communityEraContext.requestUrl}";
					document.newEventForm.submit();
			    }
			}
		</script>
		
		<script type="text/javascript">
			function validateFields(type) {
				var somval = document.getElementById('inviteeSection').value;
				if(somval == ''){
					document.getElementById('inviteeSection').style.display = 'none';
				}
				if(somval == 2000){
					document.getElementById('inviteeSection').style.display = 'inline-block';
				}
			}
			function changeEventType() {
				var somval = $( "input:radio[name=newEventType]:checked" ).attr('id');
				if(somval == 1000){
					document.getElementById('eventType').value = 0;
				}
				if(somval == 2000){
					document.getElementById('eventType').value = 1;
				}
			}
		</script>
		<cera:taggingJS context="${communityEraContext}"/>
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
							<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
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
												<c:if test="${feature == 'Library'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
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
					<div class="inputForm" style="display: inline-block;">
						<div class="intHeaderMain">
					 	Create a new Event
					 	</div>
					 	<div class="innerBlock" >
							<form:form commandName="command" multipart="true" name="newEventForm">
								<input id="finalList" type="hidden" name="finalList" />
								<input type="hidden" id="eventId" name="eventId" value="${command.eventId}" />
								<input type="hidden" id="hcity" name="hcity"/>
								<input type="hidden" id="hstate" name="hstate" />
								<input type="hidden" id="hpostalCode" name="hpostalCode" />
								<input type="hidden" id="hcountry" name="hcountry" />
								<input type="hidden" id="eventType" name="eventType" value="${command.eventType}"/>
								
								<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
								<div>
									<label for="eventname" class="normalTip" title="This field is required."><strong>Event name</strong> (100 characters max.) <img src="img/required.gif" width="8" height="8" class="required" /></label><br />
									<form:input id="name" cssClass="editInfo" path="name" maxlength="100"  cssStyle="width: 700px;"/>
								</div>
								<br />
								<div style="width: 100%; display: inline-block;">
									<label for="description" ><strong>Event Description</strong></label><br />
									<div style="margin-top:4px;">
									<form:textarea id="description" cssClass="ckeditor" path="description" ></form:textarea>
									</div>
								</div><br />
								<div id="locationSection" style="margin-top: 16px;">
									<div id="locId" style="width: 98%;" >
										<label for="location"><strong>Location</strong></label><br />
										<form:input id="locationField" path="location" cssClass="editInfo" cssStyle="width: 98%;"/>
									</div>
									<div id="" class="locationDetails">
										<div id="locDis" class="locArea" >
											<form:input id="premise" path="venue" cssClass="editInfo" /><br />
											<form:input id="address" path="address" cssClass="editInfo" /><br />
											<form:input id="locality" path="city" cssClass="editInfo disField" readonly="readonly" /><br />
											<form:input id="administrative_area_level_1" path="cstate" cssClass="editInfo disField" readonly="readonly" cssStyle="width: 42%; float: left;" />
											<form:input id="postal_code" path="postalCode" cssClass="editInfo disField" readonly="readonly" cssStyle="width: 42%; float: right;" /><br />
											<form:input id="country" path="country" cssClass="editInfo disField" readonly="readonly" /><br />
											<form:input id="latitude" path="hlatitude" cssStyle="display: none;" />
											<form:input id="longitude" path="hlongitude" cssStyle="display: none;" />
										</div>
										<div id="mapDis" class="mapArea">
											<div id="map_canvas"></div> 
										</div>
									</div>
								</div>
								
								<div style="width: 100%;">
						    		<%--<label for="contactName"><strong>Who's hosting this event</strong></label><br />
						    		<input type="text" class="editInfo" id="contactName" name="contactName" disabled="true" value="${communityEraContext.currentUser.fullName}" style="width: 96%;"/><br />
						    		<select name="phoneCode" id="phoneCode" style="width: 25%;">
										<option value="0" >Country code</option>
										<c:forEach items="${command.phoneCodeList}" var="phCode" >
			                                <option value="${phCode.value}" >${phCode.label}</option>
										</c:forEach>
									</select>
									<input type="text" class="editInfo" id="contactTel" name="contactTel" placeholder="Add contact number" value="${communityEraContext.currentUser.mobileNumber}" style="width: 25%;"/>
									<input type="text" class="editInfo" id="contactEmail" name="contactEmail" placeholder="Add e-mail address" value="${communityEraContext.currentUser.emailAddress}" style="width: 40%; margin-left: 8px;"/><br />
									<input type="text" class="editInfo" id="weblink" name="weblink" placeholder="Add weblink for this event" value="${communityEraContext.currentUser.webSiteAddress}" style="width: 96%;"/><br />
									--%>
								</div>
								<br />
								<div style="width: 100%;">
									<div style="width: 48%; float: left;" id="startEvent">
										<label style=""><strong>Starts</strong></label><br />
										<div style="float: left; margin-right:6px;">
											<input type="text" style="width: 200px;" class="editInfo" id="datepicker-example1" name="inStartDate" value="${command.inStartDate}" placeholder="Start date" />		
										</div>
										<div id="timePicker1" style="float: left; width: 90px;">
											<input type="text" class="editInfo" id="s1Time1" name="inStartTime" value="${command.inStartTime}" placeholder="Start time" readonly="readonly"/>
										</div>
										<script type="text/javascript">
									        $(document).ready(function(){
									            // find the input fields and apply the time select to them.
									            $('#timePicker1 input').ptTimeSelect();
									        });
									    </script>
									</div>
									<div style="width: 48%; float: right; margin-right: 6px;" id="endEvent">
										<label style=""><strong>Ends</strong></label><br />
										<div style="float: left; margin-right:6px;">
											<input type="text" style="width: 200px;" class="editInfo" id="datepicker-example2" name="inEndDate" value="${command.inEndDate}" placeholder="End date" />
										</div>
										<div id="timePicker2" style="float: left; width: 100px;">
											<input type="text" class="editInfo" id="s1Time2" name="inEndTime" value="${command.inEndTime}" placeholder="End time" readonly="readonly"/>
										</div>
										<script type="text/javascript">
									        $(document).ready(function(){
									            // find the input fields and apply the time select to them.
									            $('#timePicker2 input').ptTimeSelect();
									        });
									    </script>
								   	</div>
								</div>
								<br />
								<div class='radio-group' style='display: inline-block; width: 30%; margin-top: 20px;'>
									<label style="float: left;"><strong>Event Type</strong></label><br />
									<c:if test="${communityEraContext.currentCommunity.communityType != 'Private'}">
										<c:if test="${command.eventType == 0}">
											<div style='margin:10px;'><input style="width: 50px;" name='newEventType' id='1000' tabindex='2' type='radio' onclick='changeEventType();' checked="checked"/>
											<label for='newEventType' class="normalTip" title="Global events are meant for everyone and anyone on or off Jhapak can see and join this event."><span></span>Global Event</label></div>
											<div style='margin:10px;'><input style="width: 50px;" name='newEventType' id='2000' tabindex='2' type='radio' onclick='changeEventType();' />
											<label for='newEventType' class="normalTip" title="Private events have limited numbers of Guest. Only people invited by hosts or guests will join this event."><span></span>Limited</label></div>
										</c:if>
										<c:if test="${command.eventType == 1}">
											<div style='margin:10px;'><input style="width: 50px;" name='newEventType' id='1000' tabindex='2' type='radio' onclick='changeEventType();' />
											<label for='newEventType' class="normalTip" title="Global events are meant for everyone and anyone on or off Jhapak can see and join this event."><span></span>Global Event</label></div>
											<div style='margin:10px;'><input style="width: 50px;" name='newEventType' id='2000' tabindex='2' type='radio' onclick='changeEventType();' checked="checked"/>
											<label for='newEventType' class="normalTip" title="Private events have limited numbers of Guest. Only people invited by hosts or guests will join this event."><span></span>Limited</label></div>
										</c:if>
									</c:if>
									<c:if test="${communityEraContext.currentCommunity.communityType == 'Private'}">
										<div style='margin:10px;'><input style="width: 50px;" name='newEventType' id='2000' tabindex='2' type='radio' onclick='changeEventType();' checked="checked"/>
										<label for='newEventType' class="normalTip" title="Private events have limited numbers of Guest. Only people invited by hosts or guests will join this event."><span></span>Limited</label></div>
									</c:if>
								</div>
								
								<div style='display: inline-block; width: 70%; margin-top: 20px; float: right;'>
									<label style="float: left;"><strong>Event Category</strong><img src="img/required.gif" width="8" height="8" class="required" /></label><br />
										<form:dropdown path="eventCategory" mandatory="true">
										<form:option value="0" label="Select the category of event..."/>
										<form:optionlist options="${referenceData.eventCategoryOptions}"/>
									</form:dropdown>
								</div>
								<div style="width: 100%; margin-top:20px;">
								   	<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="20" parentType="event" />
								</div>
						    </form:form>
					  	</div>
					   	
						<div class="right" style="margin: 10px 10px; display: inline-block; float: right;">
							<%-- <a href="javascript:void(0);" onclick="publishEvent(1);" class="btnmain" style="float: right;">Make Event Live</a> --%>
							<a href="javascript:void(0);" onclick="publishEvent(0);" class="btnmain" style="float: right;"><i class="fa fa-save" style="margin-right: 4px;"></i>Save as draft & Next</a>
						</div>
					</div><!-- /.innerBlock  -->
				</div>
				<div class="right-panel">
					<div class="inbox">
						<div class="eyebrow">
							<span><i class="fa fa-tags" style="margin-right: 2px;"></i>Popular Tags</span>
						</div>
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
									<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
								</c:when>
								<c:otherwise>
									<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
									<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
									<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
									<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
							  	</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
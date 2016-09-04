<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<meta name="robots" content="index, follow">
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
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
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/interest.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<script type="text/javascript" src="js/cropper.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropper.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/cropImage.css" />
		
		
		<link type="text/css" rel="stylesheet" href="css/justifiedGallery.css"  media="all" />
		<script src='js/jquery.justifiedGallery.js'></script>
		
		<style type="">
			#myProfile {
				color: #27333E;
			}
				
			#container {
				padding-top: 0px;
			}
			
            .success-dialog .modal-dialog {
                width: 200px;
            }
            
			.ico--search {
			    top: 38px;
			 }
			 
			 #intSearch {
			 	padding: 4px;
			 }
			 
			 ul.optionTags li.selectedItem {
			 	background-color: #cbcbcb;
				border: 1px solid #666;
				color: #242424;
				cursor: text;
			 }
			 
			 ul.optionTags {
			    display: -webkit-box;
			    display: -webkit-flex;
			    display: -ms-flexbox;
			    display: flex;
			    -webkit-flex-flow: row wrap;
			    -ms-flex-flow: row wrap;
			    flex-flow: row wrap;
			    -webkit-box-pack: center;
			    -webkit-justify-content: center;
			    -ms-flex-pack: center;
			    justify-content: center;
			}
			
			ul.optionTags li {
				background-color: #7c7c7c;
				border: 1px solid #666;
				text-transform: capitalize;
				font-size: 14px;
				margin: 4px;
				border-radius: 5px;
				text-align: center;
				position: relative;
				color: #fff;
				min-width: 0;
				list-style: none;
				cursor: pointer;
			}
			
			ul.refBound {
				text-align: center;
			}
			 
			 span.optionText {
			 	white-space: nowrap; 
			 	float: none; 
			 	display: block;
				text-align: center;
				padding: 4px; 
			 	position: static;
				overflow: hidden;
				text-overflow: ellipsis;
			 }
			 
			 ul.refBound li {
			 	white-space: nowrap;
				float: none;
				display: inline-block;
				padding: 2px;
				position: static;
				overflow: hidden;
				text-overflow: ellipsis;
			 	background-color: #394471;
				border: 1px solid #666;
				font-size: 14px;
				color: #fff;
				margin: 3px;
				text-align: center;
				cursor: pointer;
				position: relative;
				border-radius: 5px;
				text-transform: capitalize;
			 }
			 
			 ul.refBound li span.optionSelectedText {
			    padding-right: 6px;
			    display: inline;
			 }
			 
			 .itm-close {
			    display: block;
			    position: absolute;
			    right: 0;
			    top: 0;
			    font-size: 12.5px;
			    width: 24px;
			    height: 34px;
			    line-height: 34px;
			    opacity: .3;
			 }
			 
			 #inputwkitem {
			    width: 476px;
			    height: 30px;
			    width: 100%;
			}
			
			.wkItem {
				margin: 10px;
			}
			
			li.hiddent, span.hiddent {
				visibility: hidden;
			}
			
			.displayCard:hover .changlnk {
				visibility: visible;
				background-color: #716639;
				color: #FFF;
				transition:ease 1s;
				padding-top: 8px;
				padding-bottom: 8px;
			}
			
			.displayCard:hover .changlnk2 {
				visibility: visible;
				transition:ease 1s;
				padding-top: 8px;
				padding-bottom: 8px;
			}
			
			.displayCard:hover .changlnk3 {
				visibility: visible;
				background-color: #716639;
				color:#fff;
				transition:ease 1s;
			}
			
			.displayCard:hover .changlnk4 {
				visibility: visible;
				transition:ease 1s;
			}
			
			.disnone {
				display: none;
				text-align: center;
			}
			
			.displayCard:hover .sptlgtid {
				display: block;
			}
			
			.displayCard:hover .captionBottom {
				visibility: visible;
			}
			
			.slContainer {
				padding: 10px;
			}
			
			ul.spotlights {
			    display: inline-block;
			    justify-content: left;
			    list-style: none;
			}
			
			li.spotlight {
				float: left;
			    width: calc(24.6% - 7px);
			    border-radius: 5px;
			    margin: 5px 9px 5px 0;
			    box-sizing: border-box;
			    position: relative;
			    cursor: pointer;
			    color: #fff;
			    background-color: #7c7c7c;
			    height: 120px;
			}
			
			li.spotlight:hover {
				background-color: #c8c8c8;
				color: #666;
			}
			
			li.selectedSpot {
				background-color: #394471;
				color: #FFF;
			}
			
			.slIcon {
			    position: relative;
			    display: block;
			    top: 15px;
			    line-height: 1;
			    font-size: 26px;
			    text-align: center;
			}
			
			.slText {
			   position: relative;
				top: 30px;
			    text-align: center;
			    padding-bottom: 15px;
			    padding-left: 10px;
			    padding-right: 10px;
			    width: 100%;
			    box-sizing: border-box;
			    line-height: 1.2;
			    font-size: 14px;
			    font-weight: 700;
			    white-space: normal;
				float: none;
				display: block;
			}
			
			#intSpot {
				width: 86%;
				margin: 50px 30px;
				padding: 10px;
			}
			
			.sptext {
				white-space: normal;
				float: none;
				font-size: 14px;
				text-align: center;
				padding: 40px 0px;;
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
			
			.login-dialog .modal-dialog .modal-body {
				height: 480px;
			}
			
			.occu-dialog .modal-dialog {
				width: 525px;
				height: 440px;
			}
			
			.occu-dialog .modal-dialog .modal-body {
				height: 440px;
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
            
            .displayCard:hover .addintst {
            	float: right;
            	font-size: 13px;
            	padding: 2px;
            }
            
            .interest-dialog .modal-dialog {
                width: 510px;
            }
            
            .myCustomClass3 {
			    width: 460px;
			    left: 386px;
			    border-radius: 6px;
			    margin: 1px 0px 0px 8px;
				border: 1px solid #C4C7CC;
				box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
				white-space: nowrap;
				text-align: left;
			}
			
			.footer-hint {
			    padding: 10px;
			    border-top: 1px solid #E6E6E6;
			    background: none repeat scroll 0% 0% #F7F7F7;
			    color: #737880;
			    font-size: 0.917em;
			}
			
			.qtip {
			    max-width: 480px;
			}
			
			.contentMsg {
			    position: relative;
			    padding: 10px;
			    color: #3F4752;
			}
			
			.contentMsg h2 {
			    margin: 0px 0px 11px;
			    padding: 0px;
			    color: #3F4752;
			    letter-spacing: -0.02em;
			    font-weight: 300;
			    font-size: 20px;
			}
			
			.intrst {
			    margin: 0px 0px 0px;
				padding: 3px 7px;
				border: 1px solid #D3D5D7;
				border-radius: 4px;
				font-size: 0.917em;
				line-height: 1.364;
				display: inline-block;
				position: relative;
			}
			
			.intrst-seleceted {
			    margin: 0px 0px 5px;
				padding: 3px 7px;
				border: 1px solid #D3D5D7;
				border-radius: 4px;
				background: none repeat scroll 0% 0% #F6F8F7;
				font-size: 0.917em;
				line-height: 1.364;
				display: inline-block;
				position: relative;
			}
			
			.promo-interests-new {
				display: inline-block;
			}
			
			.intrst-txt {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    text-overflow: ellipsis;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    font-size: 11px;
			}
			
			.intrst-txt-seleceted {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    color: #365BD4;
			}
			
			.large {
			    font-size: 1.25em;
			    line-height: 1.2667;
			    margin: 10px 0px 15px;
			    white-space: normal;
				text-align: left;
			}
			
			.columnLeft {
				width: 40%;
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
			
			.radio-group .column {
			    position: relative;
			    list-style: none;
			    padding: 10px;
			}
			
			li.srchRslt {
				padding: 2px 2px 2px 8px;
				font-size: 12px;;
			}
			
			li.srchRslt:hover {
				padding: 2px 2px 2px 8px;
				background: none repeat scroll 0% 0% #F7F7F7;
			}
			
			p.seccMsg {
				font-size: 1.10em;
				line-height: 1.2667;
				white-space: normal;
				text-align: left;
				color: #3F4752;
				padding: 40px;
			}
			
			li.countClass {
				background-color: #F6F7F8;
				border-color: rgba(0, 0, 0, 0.1);
				box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.08);
				cursor: pointer;
				text-decoration: none;
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
			
			.refBound {
				list-style: none outside none;
				right: 0px;
				padding: 8px 14px 0px;
				border: 1px solid #DADADA;
				border-radius: 5px;
				background-color: #FFF;
				width: 250px;
				top: 6px;
				left: -50px;
				position: relative;
			}
			
			.refBound2 {
				list-style: none outside none;
				right: 0px;
				padding: 8px 14px 0px;
				border: 1px solid #DADADA;
				border-radius: 5px;
				top: 6px;
			}
			
			li.lientry2 {
				border: 1px solid #FFF;
				-moz-border-radius: 2px;
				-webkit-border-radius: 2px;
				border-radius: 2px;
				-khtml-border-radius: 2px;
				text-decoration: none;
				padding: 3px;
				cursor: pointer;
				max-width: 200px;
			    text-overflow: ellipsis;
			    overflow: hidden;
			    vertical-align: middle;
			    white-space: nowrap;
			}
			
			li.lientry2:hover {
				background-color: #F6F7F8;
				border-color: rgba(0, 0, 0, 0.1);
				box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.08);
				cursor: pointer; 
				text-decoration: none;
				max-width: 200px;
			    text-overflow: ellipsis;
			}
			
			.rmInst {
				padding:3px 1px 2px 4px; 
				font-weight:bold; 
				color: rgb(137, 143, 156); 
				font-size: 12px;
				cursor: pointer;
			}
			
			.newButton {
			    color: #FFF;
			    background-color: #243F52;
			    border-color: #152531;
			    display: inline-block;
				padding: 4px;
				margin-bottom: 0px;
				margin-left: 5px;
				font-size: 14px;
				font-weight: 400;
				line-height: 1.42857;
				text-align: center;
				white-space: nowrap;
				vertical-align: middle;
				cursor: pointer;
				-moz-user-select: none;
				background-image: none;
				border: 1px solid transparent;
				border-radius: 4px;
			}
			
			.newButton:hover,.newButton:focus,.newButton:active
				{
				color: #fff;
				background-color: #192c39;
				border-color: #152531
			}
			
			.newButton.disabled, .newButton[disabled], .btn.disabled, .btn[disabled] {
				opacity: 0.25;
			}
		</style>
		
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
				
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

	    	  document.getElementById("latitude").value = lat;
	    	  document.getElementById("longitude").value = lng;
	    	}
	    </script>
	    
	    <script type="text/javascript">

	    function addConnectionInner(userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnectionInner.ajx?id="+userId+"&userName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	    $(".qtip").hide();
	    	    $('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
				    }
				});

	    	    dynamicDropDownQtip();
	    	  }});
		}

		function updateConnectionInner(userId, contactId, newStatus, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnectionInner.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo-"+userId).html(result);
		    	    $(".qtip").hide();
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});

		    	    dynamicDropDownQtip();
	    	  }});
		}

		function stopFollowingInner(contactId, actionFor, userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#followId"+userId).replaceWith(result);
	    	    $(".qtip").hide();
	    	    $('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
				    }
				});
	    	  }});
		}

		function startFollowingInner(contactId, actionFor, userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#followId"+userId).replaceWith(result);
	    	    $(".qtip").hide();
	    	    $('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
				    }
				});
	    	  }});
		}
			

	    function changeStatus(){
			var btn = document.getElementById('button-save');
			document.getElementById('button-save').className = "";
			document.getElementById('button-save').className = "newButton";
			document.getElementById('button-save').disabled = false;
		}
		
	    function addToMyList(interestId, interest) {
			$.ajax({url:"${communityEraContext.contextUrl}/pers/makeMyInterest.ajx?interestId="+interestId ,dataType: "json",success:function(result){
				if(result.addedInterestId > 0) {
	               	var sName = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.addedInterestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+interest+"</span><span class='blocker'></span></div></div>";
					$( "#myInterests" ).append(sName);
					var sName2 = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.addedInterestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+interest+"</span><span class='normalTip rmInst' onclick='removeInterest(&#39;"+result.interestId+"&#39;);' title='Remove &#39;"+interest+"&#39; from your interest list' >X</span></div></div>";
					$( "#intListCount" ).append(sName2);
					currSize = $( "#interestListSize" ).val();
					var next = Number(currSize) + Number(1);
					$( "#interestListSize" ).val(next);
					$('#intNumber').val(next);
					$('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
				}
			    }
		    });
		}
		
		function addNewInterest()
		{
			var searchString = document.getElementById('intSearch');
			$(".qtip").hide();
			var dialogInstance = BootstrapDialog.show({
				title: 'Add a new interest',
				message: function(dialog) {
				var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:200px; display: none;" class="showCloudWaitMessage" ></p></div>');
                return $message;
            },
            closable: true,
            closeByBackdrop: false,
            onshow: function(dialog) {
                dialog.getButton('button-save').disable();
            },
            buttons: [{
                label: 'Cancel',
                action: function(dialog){
            	dialog.close();
            }
            }, {
            	id: 'button-save',
                label: 'Add this interest',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    dialog.close();
                    var somval = $( "input:radio[name=category]:checked" ).attr('id');
                    $.ajax({url:"${communityEraContext.contextUrl}/pers/addNewInterest.ajx?interest="+searchString.value+"&categoryId="+somval ,dataType: "json",success:function(result){
                    	var sName = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.interestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+result.interest+"</span><span class='blocker'></span></div></div>";
    					$( "#myInterests" ).append(sName);
    					var sName2 = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.interestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+result.interest+"</span><span class='normalTip rmInst' onclick='removeInterest(&#39;"+result.interestId+"&#39;);' title='Remove &#39;"+result.interest+"&#39; from your interest list' >X</span></div></div>";
    					$( "#intListCount" ).append(sName2);
    					currSize = $( "#interestListSize" ).val();
						var next = Number(currSize) + Number(1);
						$( "#interestListSize" ).val(next);
						$('#intNumber').val(next);
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
            }],
            cssClass: 'login-dialog',
            onshown: function(dialogRef){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/interesCatList.ajx" ,dataType: "json",success:function(result){
					var count = 0;
					var sName = "<div class='contentMsg'><h2 class='contentMsg'>Add below interest in your list</h2>";
					sName += "<div class='promo-interests-new'><div class='intrst'><span class='intrst-txt'>"+searchString.value+"</span><span class='blocker'></span></div></div>";
					sName += "<p class='large'>Help us pick a category for this interest:</p>";
					sName += "<div class='radio-group' style='display: inline-block; width: 100%;'>";
					var lfcol = "<div class='columnLeft'>";
					var rhcol = "<div class='columnRight'>";
					$.each(result.aData, function() {
						if(count < 7) {
							lfcol += "<div style='margin:10px;'><input name='category' id='"+this['categoryId']+"' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='"+this['categoryId']+"'><span></span>"+this['category']+"</label></div>";
						} else {
							rhcol += "<div style='margin:10px;'><input name='category' id='"+this['categoryId']+"' class='' tabindex='2' type='radio' onclick='changeStatus();'/><label for='"+this['categoryId']+"'><span></span>"+this['category']+"</label></div>";
						}
							count++;
							});
					lfcol += "</div>";
					rhcol += "</div>";
					sName += "<div style='width:100%;'>"+lfcol+rhcol+"</div>";
					 dialogRef.getModalBody().find('#main').html(sName);
                	// Hide message
    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();
    			    },
    			 	// What to do before starting
    		         beforeSend: function () {
    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
    		         } 
    		    });
            	}
	        });
		}
		
		function searchInterest()
		{
			var searchString = document.getElementById('intSearch');
			$('.searchResult').each(function() {
		         $(this).qtip({
		            content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: '${communityEraContext.contextUrl}/pers/interestSearch.ajx?searchString='+searchString.value // Use href attribute as URL
		                    })
		                    .then(function(content) {
		                    	var sList = "<ul>";
		                    	$.each(content.bData, function() {
		                    		sList += "<li class='srchRslt' onclick=\"addToMyList(&#39;"+this['interestId']+"&#39;, &#39;"+this['interest']+"&#39;)\" >"+this['interest']+"</li>";
		                    	});
		                    	sList += "</ul>";
		                    	sList += "<div class='footer-hint' onclick=\"addNewInterest()\" >Add \"<span style=\'cursor: pointer;\'>"+searchString.value+"</span>\" as a new interest</div>";
		                    	
		                        api.set('content.text', sList);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', 'Loading...');
		                    });
		                    return '<div class=\'footer-hint\' style=\'margin-top:10px;\'>Search for interests</div>'; // Set some initial text
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
				        classes: 'qtip-bootstrap myCustomClass3'
				    }
		         });
		     });
		}

		function refreshInterests(categoryId, offset, interestCount)
		{
			$( ".lientry2" ).each(function( index ) {
				$( this ).removeClass("countClass")
				});
			$("#"+categoryId).addClass("countClass");
			$('#refBound2').html("");
			$.ajax({url:"${communityEraContext.contextUrl}/pers/interestList.ajx?catId="+categoryId+"&offset="+offset+"&interestCount="+interestCount ,dataType: "json",success:function(result){
				var sName = "";
				$.each(result.aData, function() {
					sName += "<li class='lientry2' style='color: #2A6496;' id='"+this['id']+"' onclick='addToMyList(&#39;"+this['interestId']+"&#39;, &#39;"+this['interest']+"&#39;);'>"+this['interest']+"</li>";
				});
				if(result.interestCount > 14) {
					sName += "<li class='lientry2' style='color: #20394D; font-weight: bold;' id='"+this['id']+"' onclick='refreshInterests(&#39;"+categoryId+"&#39;, &#39;"+result.offset+"&#39;, &#39;"+result.interestCount+"&#39;);'>More interests</li>";
				}
				$('#refBound2').html(sName);
            	// Hide message
		        $('#waitCloudMessage').hide();
			    },
			 	// What to do before starting
		         beforeSend: function () {
			    	$('#waitCloudMessage').show();
		         } 
		    });
		}
		
			function addInterest(categoryId) 
			{ 
				var dialogInstance = BootstrapDialog.show({
					title: 'Add Interest',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            closable: true,
	            closeByBackdrop: false,
	            buttons: [{
	                label: 'Close',
	                action: function(dialog){
	            	dialog.close();
	            }
	            }, {
	                label: 'Done',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }],
	            cssClass: 'interest-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/interesCatList.ajx" ,dataType: "json",success:function(result){
						var sName = "<div class='searchInt' id='searchInt'>";
						sName += "<input id='intSearch' tabindex='0' style='width: 99%;' value='' class='searchResult' placeholder='Search for interests...' maxlength='50' autocomplete='off' type='text' onkeydown='searchInterest()' onmousedown='searchInterest()' />";
						sName += "<i class='ico--search' style='top: 24px;'></i>";
						sName += "</div>";
						
						sName += "<div class='scroll-pane horizontal-only'> <div style='height:405px;'>";
						sName += "<div id='myInterests'></div><p class='addListHead' >Add intrest from below list</p>";
						
						sName += "<div class='finallist'>";
						
						sName += "<div class='columnLeft' style='margin:0px 5px 10px 10px; width:40%;'><ul class='refInt'>";
						var count = 0;
						var countClass = "countClass";
						$.each(result.aData, function() {
							if(count > 0 ){
								countClass = "";
							}
							sName += "<li class='lientry2 "+countClass+"' style='margin:4px 0px; padding:2px;' id='"+this['categoryId']+"' onclick='refreshInterests(&#39;"+this['categoryId']+"&#39;, &#39;1&#39;, &#39;0&#39;);'>"+this['category']+"</li>";
							count ++;
						});
						sName += "</ul></div>";
						sName += "<div class='columnRight' style='width:250px;'><ul class='refBound2' id='refBound2' style='min-height: 355px;'></ul></div>";
						sName += "</div></div></div>";
						 dialogRef.getModalBody().find('#main').html(sName);
						// var ii = 8;
	                	// Hide message
	    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();
		    		       // if(ii > 8){
	    		        dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
	    		       // }
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

			function removeInterest(interestId) {
				$.ajax({url:"${communityEraContext.contextUrl}/pers/removeInterest.ajx?interestId="+interestId ,dataType: "json",success:function(result){
					$( "#myOldInterests"+interestId ).html("");
					var elem = document.getElementById('myOldInterests'+interestId);
					elem.style.removeProperty("margin");
					
					var currSize = $( "#interestListSize" ).val();
					var next = Number(currSize) - Number(1);
					$( "#interestListSize" ).val(next);
					$('#intNumber').val(next);
					$(".qtip").hide();
    			    }
    		    });
			}
			
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
			function addConnection(userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnection.ajx?id="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
		    	  }});
			}
	
			function updateConnection(userId, contactId, newStatus, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnection.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
		    	  }});
			}
		
			function stopFollowing(contactId, actionFor, userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	  }});
			}
	
			function startFollowing(contactId, actionFor, userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	  }});
			}

			function joinRequest(rowId, ctype) { 
			    var ref = '${communityEraContext.contextUrl}/community/joinCommunityRequest.ajx?id='+rowId;
			    var mess = '';
			    var type = BootstrapDialog.TYPE_INFO;
			    var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	type = BootstrapDialog.TYPE_DANGER;
			    	mess = '<p class="addTagHeader">You are not logged-in.<br/> Please login first to join a community. </p>';
			    } else if (ctype == 2) {
			    	mess = '<p class="addTagHeader"><strong>This is a private community.</strong><br/><br/>'+		
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
				    if (ctype == 2) {
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
		                	if (ctype == 2) {
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

			function editPrfileLnk() {
				document.getElementById('editProfile2').style.display = 'inline-block';
				document.getElementById('editProfile1').style.display = 'none';
			}
			function cancelPrfileLnk() {
				document.getElementById('editProfile2').style.display = 'none';
				document.getElementById('editProfile1').style.display = 'inline';
			}
			function savePrfileLnk() {
				var profileName = $( "#profileField2" ).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",data: {profileName:profileName},success:function(result){
					$( "#profileField1" ).html(profileName);
					document.getElementById('editProfile2').style.display = 'none';
					document.getElementById('editProfile1').style.display = 'inline';
		    	  }});
			}

			function editName() {
				document.getElementById('editname2').style.display = 'inline-block';
				document.getElementById('editname1').style.display = 'none';
			}
			function cancelName() {
				document.getElementById('editname2').style.display = 'none';
				document.getElementById('editname1').style.display = 'inline-block';
			}
			function saveName() {
				var fName = $( "#firstName" ).val();
				var lName = $( "#lastName" ).val();
				var data = new FormData();
  			  	data.append('firstName', fName);
  			  	data.append('lastName', lName);
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
					method: "POST",
    			    data: data,
    			    processData: false,
    			    contentType: false,
    			    success:function(result){
					$( "#fNameEntry" ).html(fName);
					$( "#lNameEntry" ).html(lName);
					document.getElementById('editname2').style.display = 'none';
					document.getElementById('editname1').style.display = 'inline';
		    	  }});
			}
			
			function editAbout() {
				document.getElementById('aboutEntry2').style.display = 'block';
				document.getElementById('aboutEntry1').style.display = 'none';
			}
			function cancelAbout() {
				document.getElementById('aboutEntry2').style.display = 'none';
				document.getElementById('aboutEntry1').style.display = 'inline-block';
			}
			function saveAbout() {
				var about = $( "#editAboutMe" ).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",data: {about:about},success:function(result){
					$( "#mdobItem" ).html(result.dob);
					document.getElementById('dobItem2').style.display = 'none';
					document.getElementById('dobItem1').style.display = 'inline';
		    	  }});
			}
			
			function editBday() {
				document.getElementById('dobItem2').style.display = 'inline-block';
				document.getElementById('dobItem1').style.display = 'none';
			}
			function cancelBday() {
				document.getElementById('dobItem2').style.display = 'none';
				document.getElementById('dobItem1').style.display = 'inline-block';
			}
			function saveBday() {
				var dob = $( "#datepicker-example1" ).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",data: {dob:dob},success:function(result){
					$( "#mdobItem" ).html(result.dob);
					document.getElementById('dobItem2').style.display = 'none';
					document.getElementById('dobItem1').style.display = 'inline';
		    	  }});
			}
			
			function editGender() {
				document.getElementById('genderItem2').style.display = 'inline-block';
				document.getElementById('genderItem1').style.display = 'none';
			}
			function cancelGender() {
				document.getElementById('genderItem2').style.display = 'none';
				document.getElementById('genderItem1').style.display = 'inline-block';
			}
			function saveGender() {
				var gender = $( "#genderslct" ).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",data: {gender:gender},success:function(result){
					$( "#genderItem" ).html(result.gender);
					document.getElementById('genderItem2').style.display = 'none';
					document.getElementById('genderItem1').style.display = 'inline';
		    	  }});
			}

			function editPhone() {
				document.getElementById('phoneItem2').style.display = 'inline-block';
				document.getElementById('phoneItem1').style.display = 'none';
			}
			function cancelPhone() {
				document.getElementById('phoneItem2').style.display = 'none';
				document.getElementById('phoneItem1').style.display = 'inline-block';
			}
			function savePhone() {
				var code = $( "#phoneCode" ).val();
				var number = $( "#mobileNumber" ).val();
				var data = new FormData();
  			  	data.append('cuntryCodeId', code);
  			  	data.append('mobileNumber', number);
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
					method: "POST",
    			    data: data,
    			    processData: false,
    			    contentType: false,
					success:function(result){
					$( "#mobItem" ).html(result.mobNumber);
					document.getElementById('phoneItem2').style.display = 'none';
					document.getElementById('phoneItem1').style.display = 'inline';
		    	  }});
			}
			
			function editLocation() {
				document.getElementById('locationtem2').style.display = 'inline-block';
				document.getElementById('locationtem1').style.display = 'none';
			}
			function cancelLocation() {
				document.getElementById('locationtem2').style.display = 'none';
				document.getElementById('locationtem1').style.display = 'inline-block';
			}
			function saveLocation() {
					var data = new FormData();
	  			  	data.append('address', document.getElementById("address").value);
	  			  	data.append('city', document.getElementById("locality").value);
	  			  	data.append('venue', document.getElementById("premise").value);
	  			  	data.append('cstate', document.getElementById("administrative_area_level_1").value);
		  			data.append('postalCode', document.getElementById("postal_code").value);
		  			data.append('country', document.getElementById("country").value);
		  			data.append('latitude', document.getElementById("latitude").value);
		  			data.append('longitude', document.getElementById("longitude").value);
		  			
					$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
						method: "POST",
	    			    data: data,
	    			    processData: false,
	    			    contentType: false,
						success:function(result){
						$( "#locationItem" ).html(result.caddress);
						document.getElementById('locationtem2').style.display = 'none';
						document.getElementById('locationtem1').style.display = 'inline';
			    	  }});
			}
			
			function editRelationship() {
				document.getElementById('relationshipItem2').style.display = 'inline-block';
				document.getElementById('relationshipItem1').style.display = 'none';
			}
			function cancelRelationship() {
				document.getElementById('relationshipItem2').style.display = 'none';
				document.getElementById('relationshipItem1').style.display = 'inline-block';
			}
			function saveRelationship() {
				var relationship = $( "#relationship" ).val();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",data: {relationship:relationship},success:function(result){
					$( "#relationshipItem" ).html(result.relationship);
					document.getElementById('relationshipItem2').style.display = 'none';
					document.getElementById('relationshipItem1').style.display = 'inline';
		    	  }});
			}

			var jsondata = [];
			var selectedItem = [];
			var stext, iniLst = "";
			function editOccupation() {
				jsondata = [];
				selectedItem = [];
				stext, iniLst = "";
				var dialogInstance = BootstrapDialog.show({
					title: 'Add Occupation',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            closable: true,
	            cssClass: 'success-dialog',
	            closeByBackdrop: false,
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }, {
	                label: 'Save',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                	var work = $('#inputwkitem').val();
	                	var data = new FormData();
	      			  	data.append('company', work);
	      			  	for(var j = 1; j <= selectedItem.length; j++ ){
		      			  	if (selectedItem[j-1] > 0) {
		      			  		data.append('occupation'+j, selectedItem[j-1]);
							}
	 				   	}
	      			  	
	    				$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
	    					method: "POST",
	        			    data: data,
	        			    processData: false,
	        			    contentType: false,
	    					success:function(result){
	    					$( "#companyName" ).html(result.company);
	    					$( "#basicInfo" ).html(result.basicInfo);
	    		    	  }});
	                	dialog.close();
	                }
	            }],
	            cssClass: 'occu-dialog',
	            onshown: function(dialogRef){
	            	jsondata = [];
	    			selectedItem = [];
	    			stext = "";
	    			iniLst = "";
					$.ajax({url:"${communityEraContext.contextUrl}/pers/occupationList.ajx" ,dataType: "json",success:function(result){
						var citem2 = "";
						var sName = "<div class='searchInt' id='searchInt'><p class='addListHead' style='padding:0px;'>Add occupations (max three)</p>";
						sName += "<input id='intSearch' tabindex='0' value='' class='searchResult' placeholder='Search for occupations...' maxlength='50' autocomplete='off' type='text' onkeyup='searchOccupation()' />";
						sName += "<i class='ico--search'></i>";
						sName += "</div>";
						
						
						sName += "<div class='finallist'>";
						
						sName += "<div class='columnLeft2'><ul class='optionTags' id='optionTagLst'>";
						var count = 0;
						var countClass = "countClass";
						$.each(result.aData, function() {
							if(count > 0 ){
								countClass = "";
							}
							if (this['selectedOcc']) {
								iniLst += "<li class='lientry "+countClass+" selectedItem ' id='occu-"+this['occupationId']+"' >";
								iniLst += "<span class='optionText'>"+this['occupation']+"</span></li>";
								selectedItem.push(this['occupationId']);
								citem2 += "<li id='occuitm-"+this['occupationId']+"' onclick='cancelOccupation(&#39;"+this['occupationId']+"&#39;);'>";
								citem2 += "<span class='optionText optionSelectedText'>"+this['occupation']+"</span><i class='fa fa-close' ></i></li>";
							} else {
								iniLst += "<li class='lientry "+countClass+"' id='occu-"+this['occupationId']+"' onclick='selectOccupation(&#39;"+this['occupationId']+"&#39;);'>";
								iniLst += "<span class='optionText'>"+this['occupation']+"</span></li>";
							}
							jsondata.push(this['occupation']+"#"+this['occupationId']);
							count ++;
						});
						sName += iniLst + "</ul></div></div>";
						
						sName += "<div class='columnRight2'><ul class='refBound' id='refBound' style='width: 100%; padding: 0px; left: 0px; border: none;'></ul>";
						sName += "<div class='wkItem'><p class='addListHead' style='padding:0px;'>Add your company</p>";
						sName += "<input id='inputwkitem' type='text' tabindex='0' value='"+result.compname+"' class='searchResult' maxlength='50' />";
						sName += "</div></div>";
						
						 dialogRef.getModalBody().find('#main').html(sName);
						 dialogRef.getModalBody().find('#refBound').html(citem2);
						// var ii = 8;
	                	// Hide message
	    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();

	    		       // }
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

			function searchOccupation (){
				var ismatch = false;
				var newItems = "";
				stext = $('#intSearch').val();
				if (typeof stext !== "undefined") {
					for(var i=0; i<jsondata.length; i++) {
					        var item = jsondata[i];
					        var values = item.split('#');
					        var occup = values[0];
					        var occupId = values[1];
					        if (occup.toUpperCase() === stext.toUpperCase()) {
						        newItems = "<li id='occu-"+occupId+"' onclick='selectOccupation(&#39;"+occupId+"&#39;);'><span class='optionText'>"+occup+"</span></li>"
					        	ismatch = true;
					        	break;
							} else {
								if (occup.toUpperCase().search(stext.toUpperCase()) != -1) {
									newItems += "<li id='occu-"+occupId+"' onclick='selectOccupation(&#39;"+occupId+"&#39;);'>";
									newItems += "<span class='optionText'>"+occup+"</span></li>";
								}
							}
				    }
	
				    if (!ismatch) {
				    	newItems = "<li id='occu-0' onclick='selectOccupation(&#39;0&#39;);'><span class='optionText'>"+stext+"</span></li>" + newItems;
					}
				    $('#optionTagLst').html(newItems);
				}
			}

			function selectOccupation(occupationId) {
				var citem = "";
				if (selectedItem.length < 3) {
					var inar = false;
			        for(var j = 0; j < selectedItem.length; j++ ){
				       if(selectedItem[j] == occupationId){
				    	   inar = true; 
				       }
				   	}
			        if (!inar) {
						$('#intSearch').val('');
						selectedItem.push(occupationId);
						$("#occu-"+occupationId).addClass("selectedItem");
						$("#occu-"+occupationId).removeAttr("onclick");
						if (occupationId == 0) {
							citem = "<li id='occuitm-"+occupationId+"' onclick='cancelOccupation(&#39;"+occupationId+"&#39;);'><span class='optionText optionSelectedText'>"+stext+"</span><i class='fa fa-close' ></i></li>";
						} else {
							for(var i=0; i<jsondata.length; i++) {
								var item = jsondata[i];
						        var values = item.split('#');
						        var occup = values[0];
						        var occupId = values[1];
						        if (selectedItem.length == 3) {
									$("#occu-"+occupId).addClass("selectedItem");
									$("#occu-"+occupId).removeAttr("onclick");
						        }
						        if (occupationId == occupId) {
						        	citem = "<li id='occuitm-"+occupId+"' onclick='cancelOccupation(&#39;"+occupId+"&#39;);'><span class='optionText optionSelectedText'>"+occup+"</span><i class='fa fa-close' ></i></li>";
						        }
							}
						}
						$('#refBound').append(citem);
			        }
				} 
			}

			function cancelOccupation(occupationId) {
				var ritem = selectedItem.length;
				for(var i = 0; i < selectedItem.length; i++ ){
					
			       if(selectedItem[i] == occupationId){
			    	   selectedItem.splice(i, 1);
			        	i--; 
			       }
			   	}
				if(ritem == 3){
					for(var i=0; i<jsondata.length; i++) {
						var item = jsondata[i];
				        var values = item.split('#');
				        var occup = values[0];
				        var occupId = values[1];
				        var inar = false;
				        for(var j = 0; j < selectedItem.length; j++ ){
					       if(selectedItem[j] == occupId){
					    	   inar = true; 
					       }
					   	}
					   	
				        if (!inar) {
				        	$("#occu-"+occupId).removeClass("selectedItem");
							$("#occu-"+occupId).attr("onclick","selectOccupation("+occupId+");");
						}
					}
				} else {
					$("#occu-"+occupationId).attr("onclick","selectOccupation("+occupationId+");");
					$("#occu-"+occupationId).removeClass("selectedItem");
				}
				$("#occuitm-"+occupationId).remove();
			}

			var firstdia;
			function addSpotlight() {
				var sptext = $("#spotlightItem").val();
				var msg = "<div id='main'><div class='scroll-pane horizontal-only'><div class='slContainer'>";
				msg += "<ul class='spotlights'>";
				if (sptext == 'Attend my event') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Attend my event&#39;);'><span class='slIcon'><i class='fa fa-calendar'></i></span><span class='slText'>Attend my event</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Attend my event&#39;);'><span class='slIcon'><i class='fa fa-calendar'></i></span><span class='slText'>Attend my event</span></li>";
				}
				
				if (sptext == 'Visit my website') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Visit my website&#39;);'><span class='slIcon'><i class='fa fa-globe'></i></span><span class='slText'>Visit my website</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Visit my website&#39;);'><span class='slIcon'><i class='fa fa-globe'></i></span><span class='slText'>Visit my website</span></li>";
				}
				if (sptext == 'Date me') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Date me&#39;);'><span class='slIcon'><i class='fa fa-heart'></i></span><span class='slText'>Date me</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Date me&#39;);'><span class='slIcon'><i class='fa fa-heart'></i></span><span class='slText'>Date me</span></li>";
				}
				if (sptext == 'Schedule an appointment') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Schedule an appointment&#39;);'><span class='slIcon'><i class='fa fa-calendar'></i></span><span class='slText'>Schedule an appointment</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Schedule an appointment&#39;);'><span class='slIcon'><i class='fa fa-calendar'></i></span><span class='slText'>Schedule an appointment</span></li>";
				}
				
				if (sptext == 'Read my book') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Read my book&#39;);'><span class='slIcon'><i class='fa fa-book'></i></span><span class='slText'>Read my book</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Read my book&#39;);'><span class='slIcon'><i class='fa fa-book'></i></span><span class='slText'>Read my book</span></li>";
				}
				if (sptext == 'Work out with me') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Work out with me&#39;);'><span class='slIcon'><i class='fa fa-bolt'></i></span><span class='slText'>Work out with me</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Work out with me&#39;);'><span class='slIcon'><i class='fa fa-bolt'></i></span><span class='slText'>Work out with me</span></li>";
				}
				if (sptext == 'Sign up for my newsletter') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Sign up for my newsletter&#39;);'><span class='slIcon'><i class='fa fa-newspaper-o'></i></span><span class='slText'>Sign up for my newsletter</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Sign up for my newsletter&#39;);'><span class='slIcon'><i class='fa fa-newspaper-o'></i></span><span class='slText'>Sign up for my newsletter</span></li>";
				}
				if (sptext == 'Read my blog') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Read my blog&#39;);'><span class='slIcon'><i class='fa fa-quote-left'></i></span><span class='slText'>Read my blog</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Read my blog&#39;);'><span class='slIcon'><i class='fa fa-quote-left'></i></span><span class='slText'>Read my blog</span></li>";
				}
				if (sptext == 'Visit my galary') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Visit my galary&#39;);'><span class='slIcon'><i class='fa fa-photo'></i></span><span class='slText'>Visit my galary</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Visit my galary&#39;);'><span class='slIcon'><i class='fa fa-photo'></i></span><span class='slText'>Visit my galary</span></li>";
				}
				if (sptext == 'Visit my company website') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Visit my company website&#39;);'><span class='slIcon'><i class='fa fa-building'></i></span><span class='slText'>Visit my company website</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Visit my company website&#39;);'><span class='slIcon'><i class='fa fa-building'></i></span><span class='slText'>Visit my company website</span></li>";
				}
				if (sptext == 'Watch my videos') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Watch my videos&#39;);'><span class='slIcon'><i class='fa fa-video-camera'></i></span><span class='slText'>Watch my videos</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Watch my videos&#39;);'><span class='slIcon'><i class='fa fa-video-camera'></i></span><span class='slText'>Watch my videos</span></li>";
				}
				if (sptext == 'Download my app') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Download my app&#39;);'><span class='slIcon'><i class='fa fa-cloud-download'></i></span><span class='slText'>Download my app</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Download my app&#39;);'><span class='slIcon'><i class='fa fa-cloud-download'></i></span><span class='slText'>Download my app</span></li>";
				}
				if (sptext == 'Visit my store') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Visit my store&#39;);'><span class='slIcon'><i class='fa fa-shopping-cart'></i></span><span class='slText'>Visit my store</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Visit my store&#39;);'><span class='slIcon'><i class='fa fa-shopping-cart'></i></span><span class='slText'>Visit my store</span></li>";
				}
				if (sptext == 'Support my campaign') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Support my campaign&#39;);'><span class='slIcon'><i class='fa fa-thumbs-up'></i></span><span class='slText'>Support my campaign</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Support my campaign&#39;);'><span class='slIcon'><i class='fa fa-thumbs-up'></i></span><span class='slText'>Support my campaign</span></li>";
				}
				if (sptext == 'Support my charity') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Support my charity&#39;);'><span class='slIcon'><i class='fa fa-leaf'></i></span><span class='slText'>Support my charity</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Support my charity&#39;);'><span class='slIcon'><i class='fa fa-leaf'></i></span><span class='slText'>Support my charity</span></li>";
				}
				if (sptext == 'Take my class') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Take my class&#39;);'><span class='slIcon'><i class='fa fa-university'></i></span><span class='slText'>Take my class</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Take my class&#39;);'><span class='slIcon'><i class='fa fa-university'></i></span><span class='slText'>Take my class</span></li>";
				}
				if (sptext == 'Try my food') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Try my food&#39;);'><span class='slIcon'><i class='fa fa-birthday-cake'></i></span><span class='slText'>Try my food</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Try my food&#39;);'><span class='slIcon'><i class='fa fa-birthday-cake'></i></span><span class='slText'>Try my food</span></li>";
				}
				if (sptext == 'Visit my restaurant') {
					msg += "<li class='spotlight selectedSpot' onclick='saveSpolight(&#39;Visit my restaurant&#39;);'><span class='slIcon'><i class='fa fa-cutlery'></i></span><span class='slText'>Visit my restaurant</span></li>";
				} else {
					msg += "<li class='spotlight' onclick='saveSpolight(&#39;Visit my restaurant&#39;);'><span class='slIcon'><i class='fa fa-cutlery'></i></span><span class='slText'>Visit my restaurant</span></li>";
				}
				
				msg += "</ul>";
				msg += "</div></div></div>";
				firstdia = BootstrapDialog.show({
				title: 'Add a Spotlight',
				message: msg,
				closable: false,
                closeByBackdrop: false,
                closeByKeyboard: false,
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }],
	            onshown: function(dialogRef){
					 dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
  		        		{
  		        			verticalDragMinHeight: 20,
  		        			verticalDragMaxHeight: 200,
  		        			autoReinitialise: true
  		        			}
  	    		        );
	            }
		        });
			}

			function saveSpolight(sptext) {
				var title = "";
				var msg = "<div class=''><input id='intSpot' tabindex='0' value='' class='editInfo' placeholder='' maxlength='250' type='text' /></div>";
				if (sptext == 'Attend my event') {
					title = "Add a link to your event";
				} 
				if (sptext == 'Visit my website') {
					title = "Add a link to your website";
				} 
				if (sptext == 'Date me') {
					title = "Add a link to your dating profile";
				}
				if (sptext == 'Schedule an appointment') {
					title = "Add a link for appointment";
				} 
				if (sptext == 'Read my book') {
					title = "Add a link to your book";
				}
				if (sptext == 'Work out with me') {
					title = "Add a link to your fitness profile";
				}
				if (sptext == 'Sign up for my newsletter') {
					title = "Add a link to your newsletter form";
				}
				if (sptext == 'Read my blog') {
					title = "Add a link to your blog";
				}
				if (sptext == 'Visit my galary') {
					title = "Add a link to your photo galary";
				}
				if (sptext == 'Visit my company website') {
					title = "Add a link to your company website";
				}
				if (sptext == 'Watch my videos') {
					title = "Add a link to your videos";
				}
				if (sptext == 'Download my app') {
					title = "Add a link to your app";
				}
				if (sptext == 'Visit my store') {
					title = "Add a link to your online store";
				}
				if (sptext == 'Support my campaign') {
					title = "Add a link to your online store";
				}
				if (sptext == 'Support my charity') {
					title = "Add a link to your favorite charity";
				}
				if (sptext == 'Take my class') {
					title = "Add a link to your class or course";
				}
				if (sptext == 'Try my food') {
					title = "Add a link to your recipe";
				}
				if (sptext == 'Visit my restaurant') {
					title = "Add a link to your restaurant";
				}

		    	BootstrapDialog.show({
	                title: title,
	                message: msg,
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
	                	label: 'Save',
	                	cssClass: 'btn-info',
	                	id: 'button-Go',
	                	action: function(dialogRef){
	                	dialogRef.getButton('button-Go').disable();
	                	dialogRef.getButton('button-close').disable();
	                	dialogRef.getButton('button-Go').spin();
	                	var surl = dialogRef.getModalBody().find('#intSpot').val();
	                	var data = new FormData();
	      			  	data.append('spotlight', sptext);
	      			  	data.append('spotlightUrl', surl);
	                	$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
	                		method: "POST",
	        			    data: data,
	        			    processData: false,
	        			    contentType: false,
	    					success:function(result){
	    					$( "#spotlightItem" ).val(result.spotlight);
	    					var spupdate = "";
	    					if (result.spotlight == 'Attend my event') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-calendar' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					} 
	    					
	    					if (result.spotlight == 'Visit my website') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-globe' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					} 
	    					if (result.spotlight == 'Date me') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-heart' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Schedule an appointment') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-calendar' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					} 
	    					if (result.spotlight == 'Read my book') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-book' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Work out with me') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-bolt' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Sign up for my newsletter') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-newspaper-o' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Read my blog') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-quote-left' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Visit my galary') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-photo' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					
	    					if (result.spotlight == 'Visit my company website') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-building' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Watch my videos') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-video-camera' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Download my app') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-cloud-download' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Visit my store') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-shopping-cart' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Support my campaign') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-thumbs-up' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Support my charity') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-leaf' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Take my class') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-university' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Try my food') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-birthday-cake' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}
	    					if (result.spotlight == 'Visit my restaurant') {
	    						spupdate += "<a class='buttonSecondary changlnk2' href='"+result.spotlightUrl+"' target='_blank'>";
	    						spupdate += "<i class='fa fa-cutlery' style='margin-right: 8px;'></i>"+result.spotlight+"</a>";
	    					}

	    					$( "#spotlightentry" ).html(spupdate);
	    					dialogRef.close();
	    					firstdia.close();
	    		    	  }});
                    	}
	                }]
	            });
			}

			function addSocialLink() {
				var sptext = $("#spotlightItem").val();
				var msg = "<div id='main'><div class='scroll-pane horizontal-only'><div class='slContainer'>";
				msg += "<ul class='spotlights' id='slinkList'></ul></div></div></div>";
				firstdia = BootstrapDialog.show({
				title: 'Add Social Links',
				message: msg,
				closable: false,
                closeByBackdrop: false,
                closeByKeyboard: false,
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }],
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/socialLinkList.ajx" ,dataType: "json",success:function(result){
						var sName = "";
						var countClass = "countClass";
						$.each(result.aData, function() {
							var selectedClass = "";
							if (this['selected'] > 0) {
								selectedClass = "selectedSpot";
							}
							sName += "<li class='spotlight "+selectedClass+"' onclick='saveSocialLink(&#39;"+this['name']+"&#39;);' style='border-radius: 50%;padding-top: 10px;'><span class='slIcon'>";
							sName += "<i class='fa fa-"+this['iconname']+"'></i>";
							sName += "</span><span class='slText'>"+this['name']+"</span></li>";
						});
						
						
						 dialogRef.getModalBody().find('#slinkList').html(sName);
						// var ii = 8;
	                	// Hide message
	    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();
		    		       // if(ii > 8){
	    		        dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
	    		       // }
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

			function saveSocialLink(sptext) {
				var title = "Add your " + sptext + " profile link";
				var msg = "<div class=''><input id='intSpot' tabindex='0' value='' class='editInfo' placeholder='' maxlength='250' type='text' /></div>";
				if (sptext == 'Twitter' || sptext == 'Facebook' || sptext == 'Instagram' || sptext == 'Pinterest') {
					title = "Add your " +sptext+" username or profile link";
				} 
				if (sptext == 'Tumblr' || sptext == 'WordPress' || sptext == 'Medium') {
					title = "Add your " +sptext+" blog link";
				}
				if (sptext == 'Wikipedia' || sptext == 'Yelp' || sptext == 'Foursquare' || sptext == 'GitHub') {
					title = "Add your " +sptext+" page link";
				} 
				if (sptext == 'YouTube') {
					title = "Add your " +sptext+" channel link";
				}
				if (sptext == 'Dribbble') {
					title = "Add your " +sptext+" portfolio link";
				}

		    	BootstrapDialog.show({
	                title: title,
	                message: msg,
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
	                	label: 'Save',
	                	cssClass: 'btn-info',
	                	id: 'button-Go',
	                	action: function(dialogRef){
	                	dialogRef.getButton('button-Go').disable();
	                	dialogRef.getButton('button-close').disable();
	                	dialogRef.getButton('button-Go').spin();
	                	var surl = dialogRef.getModalBody().find('#intSpot').val();
	                	var data = new FormData();
	      			  	data.append('slName', sptext);
	      			  	data.append('slLink', surl);
	                	$.ajax({url:"${communityEraContext.contextUrl}/pers/saveProfileAction.ajx",
	                		method: "POST",
	        			    data: data,
	        			    processData: false,
	        			    contentType: false,
	    					success:function(result){
		    					var slupdate = "<li class='socialItem'><a class='addSL' href='"+result.link+"' target='_blank'><i class='fa fa-"+result.iconname+"' ></i></a></li>";
		    					slupdate += "<li class='socialItem' id='lastlogo' onclick='addSocialLink();'><a class='hiddent addSL changlnk3 '><i class='fa fa-plus'></i></a></li>";
		    					$("#sociLnksItems").css("display","block");
		    					$("#lastlogo").remove();
		    					
		    					$( "#sociLnksItems" ).append(slupdate);
	    					dialogRef.close();
	    					firstdia.close();
	    		    	  }});
                    	}
	                }]
	            });
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
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				findLocation();
				normalQtip();
				dynamicDropDownQtip();

				$("#lnkCommunities").click(function() {
				    $('html,body').animate({
				        scrollTop: $("#A12345").offset().top-50},
				        'slow');
				});

				$("#lnkConnections").click(function() {
				    $('html,body').animate({
				        scrollTop: $("#B12345").offset().top-50},
				        'slow');
				});

				$.ajax({url:"${communityEraContext.contextUrl}/common/mediaPannel.ajx?profileId=${command.user.id}",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						temp += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['mediaId']+"' width='89' height='89' style='padding: 3px; border-radius: 10px;' />";
						});
					if(result.aData.length > 0){
						$("#mediaList").html(temp);
					} else {
						$("#mediaList").html("<span style ='font-size: 12px; padding-left: 64px;'>No Media found</span>");
					}
			        $("#waitMediaMessage").hide();
				    },
			         beforeSend: function () {
			             $("#waitMediaMessage").show();
			         } 
			    });
			    
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?profileId=${command.user.id}&profVisitors=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						temp += "<div style='width: 100%; display: inline-block;'>";
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += this['name'];
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'  style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
							 temp += "<div style=''><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += this['name'];
							 temp += "</a></div>";
						 }
						temp += "</div>";
						});
					if(result.aData.length > 0){
						$("#visitorsList").html(temp);
					} else {
						$("#visitorsList").html("<span style ='font-size: 12px; padding-left: 64px;'>No recent visitors</span>");
					}
					 
					// Hide message
			        $("#waitVisitMessage").hide();
			        memberInfoQtip();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitVisitMessage").show();
			         } 
			    });
			    
				$.ajax({url:"${communityEraContext.contextUrl}/pers/connectionCommunities.ajx?jPage=1&id="+$("#userId").val(),dataType: "json",success:function(result){ 
					var sName = "";
					 $.each(result.aData, function() {
						 sName += "<div class='paginatedList'><div class='leftImg'>";
				
						 var ctype = 1;
						 var rowId = this['id'];
						 if(this['type'] == "Private"){
							 ctype = 2;
						 }
						 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/'home.do'>";
						 if(this['logoPresent'] == "true"){
							 sName += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+rowId+"'/>";
							} else {
							 sName += "<img src='img/community_Image.png' />";
						 }
						 sName += "</a>";

						 sName += "</div><div class='details'>";
						 sName += "<div class='heading'>";
						 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/home.do'>"+this['name']+"</a></div>";
						 
						 sName += "<div class='person'>";

						 if(this['type'] == "Private" && !this['member']){
							 if(this['memberCountString'] != "0 member"){
								 sName += "<span>"+this['memberCountString']+"</span>";
							 } else {
								 sName += this['memberCountString'];
							 }
						 } else {
							 if(this['memberCountString'] != "0 member"){
								 sName += "<span class='optionList' title='${communityEraContext.contextUrl}/pers/memberList.ajx?communityId="+rowId+"'>"+this['memberCountString']+"</span>";
							 } else {
								 sName += this['memberCountString'];
							 }
						 }
						 sName += "</div>";
						 
						 if(!this['member'] && this['type'] == "Private"){
							 if(this['membershipRequested']){
							 	sName += "<div class='entry' id='reqId-"+rowId+"' ><span>Membership requested</span></div>";
							 } else {
								 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest("+rowId+","+ctype+")' href='javascript:void(0);' title='Apply to join "+this['name']+"'>Apply to join</a></div>";
							 }
						 }
						 
						 if(!this['member'] && this['type'] == "Public"){
							 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest("+rowId+","+ctype+")' href='javascript:void(0);' title='Join "+this['name']+"'>Join</a></div>";
						 }
						 sName += "</div></div>";
							});
					 $("#lstCommunities").html(sName);
					// Hide message
			        $("#waitMessageCommunities").hide();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitMessageCommunities").show();
			         } 
			    });

            	$.ajax({
                	url:"${communityEraContext.contextUrl}/pers/connectionList.ajx?jPage="+1+"&id="+$("#userId").val(),
                	dataType: "json",
                	success:function(result){ 
    					var sName = "";
   					 $.each(result.aData, function() {
   						 var rowId = this['id'];
   						 var trclass = '';
   						 if(this['evenRow']){
   							 trclass = 'Alt';
   						 }
   						 sName += "<div class='userList"+trclass+"' id='connResult-"+rowId+"'><div class='leftImg'>";
   						 if(this['photoPresent']){
   							 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id="+rowId+"' width='120' height='120' /></a>"; }
   						 else {
   							 sName += "<img src='img/user_icon.png'  width='120' height='120' />";
   						 }
   						 sName += "</div><div class='details'><div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref'>"+this['firstName']+" "+this['lastName']+"</a></div><br />";
   						 //sName += "<div class='person'>Connected since "+this['connectionDate']+"</div>";
   						 if(this['connectionCount'] == 0){
   							 sName += "<div class='person'>"+this['connectionCount']+" Connection</a></div>";
   						 }
   						 if(this['connectionCount'] == 1){
   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connection</a></div>";
   						 }
   						 if(this['connectionCount'] > 1){
   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connections</a></div>";
   						 }
   						 if(this['communityCount'] == 0){
   						 	sName += "<div class='person'>"+this['communityCount']+" Community</a></div>";
   						 }
   						 if(this['communityCount'] == 1){
   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id="+rowId+"'>"+this['communityCount']+" Community</a></div>";
   						 }
   						 if(this['communityCount'] > 1){
   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id="+rowId+"'>"+this['communityCount']+" Communities</a></div>";
   						 }
   						 sName += "<div id='connectionInfo-"+rowId+"' class='connectionInfo'>"+this['connectionInfo']+"</div>";
   						 sName += "</div></div>";
   							});
   					 $("#lstConnections").html(sName);
   					$("#waitMessageConnections").hide();
                	} ,
                	beforeSend: function () {
			             $("#waitMessageConnections").show();
			         }
            	});
			});
		</script>
		
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${command.user.id});" >
				<c:choose>
					<c:when test="${command.user.coverPresent}">	
						<a href="javascript:void(0);" onclick="showPersonalMedia('Cover');" class="button photocontainer" 
							style="text-decoration: none; top: 30%;">Update Profile Cover</a>	
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" onclick="showPersonalMedia('Cover');" class="button photocontainer" 
							style="text-decoration: none; top: 30%;">Add Profile Cover</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div id="container" style="margin-top: -100px;">
			<div class="left-panel" >
				<div class="commSection">
					<input type="hidden" id="currentUserId" value="${communityEraContext.currentUser.id}" />
					<input type="hidden" id="userId" value="${command.id}" />
					<input type="hidden" id="premise" />
					<input type="hidden" id="address" />
					<input type="hidden" id="locality" />
					<input type="hidden" id="administrative_area_level_1" />
					<input type="hidden" id="postal_code" />
					<input type="hidden" id="country" />
					<input type="hidden" id="latitude" />
					<input type="hidden" id="longitude" />
					<input type="hidden" id="spotlightItem" value="${command.spotlight}"/>
					<input type="hidden" id="interestListSize" name="interestListSize" value="${command.interestListSize}" />
					
					<div class="displayCard" >
						<div class="topusrsec">
							<div class="topurl">
								<i class="fa fa-link" aria-hidden="true" style="margin-left: 8px; color: #fff;"></i>
								<span id="editProfile1" >
									<a href="${communityEraContext.contextUrl}/profile/${command.user.profileName}" id="prfileLnk" class="prohyplnk">
									${communityEraContext.contextUrl}/profile/<strong style="color: #fff;" class="changlnk" id="profileField1">${command.user.profileName}</strong>
									</a>
									<span class="editEntry hiddent changlnk" onclick="editPrfileLnk();" id="editPrfileLnk"><i class="fa fa-pencil" ></i></span>
								</span>
								<span id="editProfile2" style="display: none;">
									${communityEraContext.contextUrl}/profile/<input type="text" id="profileField2" class="editInfo" value="${command.user.profileName}"/>
									<a class='buttonSecondary' href='javascript:void(0);' onclick="savePrfileLnk();">Save</a>
									<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelPrfileLnk();">Cancel</a>
								</span>
							</div>
						</div>
						<div class="usrsec">
							<div class="picture">
								<c:choose>
									<c:when test="${command.user.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.user.id}" />
										</c:url>
										<img src="${photoUrl}"  style="width: 200px;"/> 
										<div class="captionBottom" onclick="showPersonalMedia('Photo');" >
											<span><i class="fa fa-camera" aria-hidden="true"></i></span>
										</div>
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png' style="width: 200px;"/>
										<div class="captionBottom" onclick="showPersonalMedia('Photo');" >
											<span><i class="fa fa-camera" aria-hidden="true"></i></span>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="infosec">
								<div id="editname1" style="display: inline-block;">
									<h2 class="changlnk" id="fNameEntry">${command.user.firstName}</h2> <h2 class="changlnk" id="lNameEntry">${command.user.lastName}</h2>
									<span class="editEntry hiddent changlnk" onclick="editName();"><i class="fa fa-pencil" ></i></span>
								</div>
								<span id="editname2" style="display: none;">
									<input type="text" id="firstName" class="editInfo" value="${command.firstName}"/>
									<input type="text" id="lastName" class="editInfo" value="${command.lastName}"/>
									<a class='buttonSecondary' href='javascript:void(0);' onclick="saveName();">Save</a>
									<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelName();">Cancel</a>
								</span>
								<div class="basicinfo" id="basicInfo">
									${command.basicInfo}
								</div>
								<div class="proNav">
									<ul class="proNavList">
										<li class="proNavItm" id="lnkCommunities">
											COMMUNITIES ${command.communitiesSize}
										</li>
										<li class="proNavItm" id="lnkConnections">
											CONNECTIONS ${command.connectionCount}
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="usrsec">
							<div class="about" id="aboutEntry1">
								<span class="itmHdr">Bio</span><span class="editEntry hiddent changlnk" onclick="editAbout();"><i class="fa fa-pencil" ></i></span><br />
								<p class="changlnk">${command.bioInfo}</p>
							</div>
							<div id="aboutEntry2" style="padding: 20px; display: none;">
								<span class="itmHdr">Bio</span>
								<textarea placeholder="Say something about yourself..." class="comment" maxlength="500" id="editAboutMe">${command.bioInfo}</textarea>
								<a class='buttonSecondary' href='javascript:void(0);' onclick="saveAbout();">Save</a>
								<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelAbout();">Cancel</a>
							</div>
							<c:choose>
								<c:when test="${empty command.spotlight}">
									<div class="proNav sptlgtid disnone" >
										<span class="editEntry hiddent ">
											<a class='buttonSecondary changlnk2' href='javascript:void(0);' onclick="addSpotlight();" >
												<i class="fa fa-bullseye" style="margin-right: 8px;"></i>Add a Spotlight
											</a>
										</span>
									</div>
								</c:when>
								<c:otherwise>
									<div class="proNav" style="text-align: center; margin: 10px;" >
										<span id="spotlightentry" >
											<c:if test="${command.spotlight == 'Attend my event'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-calendar" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											
											<c:if test="${command.spotlight == 'Visit my website'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-globe" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Date me'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-heart" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Schedule an appointment'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-calendar" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Read my book'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-book" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Work out with me'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-bolt" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Sign up for my newsletter'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-newspaper-o" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Read my blog'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-quote-left" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Visit my galary'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-photo" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Visit my company website'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-building" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Watch my videos'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-video-camera" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Download my app'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-cloud-download" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Visit my store'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-shopping-cart" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Support my campaign'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-thumbs-up" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Support my charity'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-leaf" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Take my class'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-university" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Try my food'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-birthday-cake" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
											<c:if test="${command.spotlight == 'Visit my restaurant'}">
												<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
													<i class="fa fa-cutlery" style="margin-right: 8px;"></i>${command.spotlight}
												</a>
											</c:if>
										</span>
										<span class="editEntry hiddent changlnk" onclick="addSpotlight();"><i class="fa fa-pencil" ></i></span>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="usrsec">
							<c:choose>
								<c:when test="${command.sociallinkcount > 0}">
									<ul class="sociLnks" id="sociLnksItems">
										<c:forEach items="${command.socialLinks}" var="row" >
											<li class="socialItem" >
												<a href='${row.link}' target='_blank' class="addSL"><i class="fa fa-${fn:toLowerCase(row.name)}" ></i></a>
											</li>
										</c:forEach>
										<li class="socialItem hiddent " id="lastlogo" >
											<a class="addSL changlnk3 " href='javascript:void(0);' onclick="addSocialLink();"><i class="fa fa-plus" ></i></a>
										</li>
									</ul>
								</c:when>
								<c:otherwise>
									<div class="proNav sptlgtid disnone" >
										<span class="editEntry hiddent ">
											<a class='buttonSecondary changlnk2 changlnk' href='javascript:void(0);' onclick="addSocialLink();" >
												<i class="fa fa-hashtag" style="margin-right: 8px;"></i>Add Social Links
											</a>
										</span>
									</div>
									<ul class="sociLnks" id="sociLnksItems" style="display: none;">
										<li class="socialItem hiddent" id="lastlogo">
											<a class="changlnk3 addSL" href='javascript:void(0);' onclick="addSocialLink();"><i class="fa fa-plus" ></i></a>
										</li>
									</ul>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					<div class="displayCard" style="overflow-y: visible;">
						<h2 class="headerSection" >Personal details</h2>
						<div class="usrsec">
							<ul class="itemNav">
								<li class="navItem">
									<span class="itmHdr">Birthday</span>
									<span id="dobItem1">
										<span class="itmEntry changlnk" id="mdobItem">${command.dob}</span>
										<span class="editEntry hiddent changlnk" id="editBdayLnk" onclick="editBday();"><i class="fa fa-pencil" ></i></span>
									</span>
									<span style="display: none;" id="dobItem2">	
										<input type="text" id="datepicker-example1" class="editInfo" style="width: 176px;" value="${command.dobToEdit}"/>	
										<a class='buttonSecondary' href='javascript:void(0);' onclick="saveBday();">Save</a>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelBday();">Cancel</a>
									</span>
									<div class="privSett changlnk4" >
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
								</li>
								<li class="navItem">
									<span class="itmHdr">Gender</span>
									<span id="genderItem1">
										<span class="itmEntry changlnk" id="genderItem">${command.genderTodisplay}</span>
										<span class="editEntry hiddent changlnk" id="editGenderLnk" onclick="editGender();"><i class="fa fa-pencil" ></i></span>
									</span>
									<span id="genderItem2" style="display: none;">
										<select id="genderslct" style="">
											<c:forEach items="${referenceData.genderTypeOptions}" var="genderRow" >
												<c:if test="${command.gender == genderRow.value}">
				                                	<option value="${genderRow.value}" selected="selected">${genderRow.label}</option>
						                       	</c:if>
						                       	<c:if test="${command.gender != genderRow.value}">
				                                	<option value="${genderRow.value}" >${genderRow.label}</option>
						                       	</c:if>
											</c:forEach>
										</select>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="saveGender();">Save</a>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelGender();">Cancel</a>
									</span>
									<div class="privSett changlnk4">
										<ul class="navSetting">
											<li class="outer" id="relationshipSetting" onclick="onClickSettingMenu('genderSetting');">
												<span class="selSett" ><span class="selTxt" id="relationship_header" >${command.userPrivacy.genderPrivacy}</span></span><span class="ddimgBlk"></span>
							                    <div class="subsSetting" id="genderSettingSubs">
							                        <div class="col">
							                            <ul style="line-height:32px;" id="gender_inner">
							                                <li class="innerList" onclick="updatePrivacy('gender', 0);">
								                                <c:if test="${command.userPrivacy.gender == 0}">
								                                	<span class="selSett" ><span class="selTxt">Public</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 0}">
								                                	<span class="selTxt">Public</span>
										                       	</c:if>
								                                <div class="desc">Anyone on Jhapak</div>
							                                </li>
															<li class="innerList" onclick="updatePrivacy('gender', 1);">
																<c:if test="${command.userPrivacy.gender == 1}">
								                                	<span class="selSett" ><span class="selTxt">Connections</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 1}">
								                                	<span class="selTxt">Connections</span>
										                       	</c:if>
										                       	<div class="desc">All from your connections</div>
										                    </li>
															<li class="innerList" onclick="updatePrivacy('gender', 2);">
																<c:if test="${command.userPrivacy.gender == 2}">
								                                	<span class="selSett" ><span class="selTxt">Community Members</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 2}">
								                                	<span class="selTxt">Community Members</span>
										                       	</c:if>
										                       	<div class="desc">Your each community member</div>
										                    </li>
															<li class="innerList" onclick="updatePrivacy('gender', 3);">
																<c:if test="${command.userPrivacy.gender == 3}">
								                                	<span class="selSett" ><span class="selTxt">Followers</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 3}">
								                                	<span class="selTxt">Followers</span>
										                       	</c:if>
										                       	<div class="desc">Everyone who follows you</div>
										                    </li>
															<li class="innerList" onclick="updatePrivacy('gender', 4);">
																<c:if test="${command.userPrivacy.gender == 4}">
								                                	<span class="selSett" ><span class="selTxt">Only Me</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 4}">
								                                	<span class="selTxt">Only Me</span>
										                       	</c:if>
										                       	<div class="desc">Only me</div>
										                    </li>
															<li class="innerList" onclick="customPrivacy();">
																<c:if test="${command.userPrivacy.gender == 5}">
								                                	<span class="selSett" ><span class="selTxt">Custom</span></span>
										                       	</c:if>
										                       	<c:if test="${command.userPrivacy.gender != 5}">
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
								</li>
								<li class="navItem">
									<span class="itmHdr">Mobile Phone</span>
									<span id="phoneItem1">
										<span class="itmEntry changlnk" id="mobItem">${command.mobPhoneCode} - ${command.mobileNumber}</span>
										<span class="editEntry hiddent changlnk" id="editPhoneLnk" onclick="editPhone();"><i class="fa fa-pencil" ></i></span>
									</span>
									<span id="phoneItem2" style="display: none;">
										<select id="phoneCode">
											<c:forEach items="${command.countryList}" var="row" >
												<c:if test="${command.cuntryCodeId == row.value}">
				                                	<option value="${row.value}" selected="selected">${row.label}</option>
						                       	</c:if>
						                       	<c:if test="${command.cuntryCodeId != row.value}">
				                                	<option value="${row.value}" >${row.label}</option>
						                       	</c:if>
											</c:forEach>
										</select>
										<input type="text" class="editInfo" id="mobileNumber" style="" size="16" value="${command.mobileNumber}"/>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="savePhone();">Save</a>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelPhone();">Cancel</a>
									</span>
									<div class="privSett changlnk4" >
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
								</li>
								<li class="navItem">
									<span class="itmHdr">Address</span>
									<span id="locationtem1">
										<span class="itmEntry changlnk" id="locationItem">${command.location}</span>
										<span class="editEntry hiddent changlnk" id="editLocationLnk" onclick="editLocation();"><i class="fa fa-pencil" ></i></span>
									</span>
									<span id="locationtem2" style="display: none;">
										<input type="text" class="editInfo" id="locationField" style="width: 280px;" size="16" value="${command.location}"/>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="saveLocation();">Save</a>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelLocation();">Cancel</a>
									</span>
									<div class="privSett changlnk4" >
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
								</li>
								<li class="navItem">
									<span class="itmHdr">Relationship</span>
									<span id="relationshipItem1">
										<span class="itmEntry changlnk" id="relationshipItem">${command.relationshipDisplay}</span>
										<span class="editEntry hiddent changlnk" id="editRelationshipLnk" onclick="editRelationship();"><i class="fa fa-pencil" ></i></span>
									</span>
									<span id="relationshipItem2" style="display: none;">
										<select id="relationship" style="">
											<c:forEach items="${referenceData.relationTypeOptions}" var="relationshipRow" >
												<c:if test="${command.relationship == relationshipRow.value}">
				                                	<option value="${relationshipRow.value}" selected="selected">${relationshipRow.label}</option>
						                       	</c:if>
						                       	<c:if test="${command.relationship != relationshipRow.value}">
				                                	<option value="${relationshipRow.value}" >${relationshipRow.label}</option>
						                       	</c:if>
											</c:forEach>
										</select>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="saveRelationship();">Save</a>
										<a class='buttonSecondary' href='javascript:void(0);' onclick="cancelRelationship();">Cancel</a>
									</span>
									<div class="privSett changlnk4" >
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
								</li>
								<%-- <li class="navItem">
									<span class="itmHdr">High school</span><span class="itmEntry"></span>
								</li>
								<li class="navItem">
									<span class="itmHdr">College</span><span class="itmEntry"></span>
								</li> --%>
								<li class="navItem">
									<span class="itmHdr">Work</span>
									<span id="OccupationItem1">
										<span class="itmEntry changlnk" id="companyName">${command.company}</span>
										<span class="editEntry hiddent changlnk" id="editOccupationLnk" onclick="editOccupation();"><i class="fa fa-pencil" ></i></span>
									</span>
								</li>
							</ul>
						</div>
						<h2 class="headerSection" >Interests<span class="editEntry hiddent changlnk addintst" style="float:right" id="editInterestLnk" onclick="addInterest(0);"><i class="fa fa-plus" ></i>Add more interests</span></h2>
						<div class="usrsec" id="intListCount" style="margin: 0px 10px 10px 10px;">
							<c:forEach items="${command.interestList}" var="row" >
								<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests${row.id}'>
									<div class='intrst' id="outInt"><span id='ID${row.id}' class='intrst-txt'>${row.interest}</span>
												<span class='normalTip rmInst' onclick="removeInterest(&#39;${row.id}&#39;);" 
												title="Remove &#39;${row.interest}&#39; from your interest list" >X</span></div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="displayCard" id="A12345">
						<h2 class="headerSection" >Communities<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${command.id}">See All</a></h2>
						<div class="usrsec" id="lstCommunities">
							<div id="waitMessageCommunities" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="displayCard" id="B12345">
						<h2 class="headerSection" >Connections<a href="${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${command.id}">See All</a></h2>
						<div class="usrsec" id="lstConnections">
							<div id="waitMessageConnections" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
						</div>
					</div>
				 </div> <!-- /myUpload --> 
				</div><!-- /#leftPannel -->
				
				<div class="right-panel" style="margin-top: 0px;">
					<c:if test="${communityEraContext.userAuthenticated}">
						<div class="inbox" style="display: inline-block; width: 296px; float: right;">
							<div class="eyebrow">
								<span onclick="return false" >People Also Viewed</span>
							</div>
							<div id="visitorsList" style="padding: 4px;" ></div>
							<div id="waitVisitMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
						</div>
					</c:if>
					
					<div class="inbox" style="display: inline-block; width: 296px; float: right;">
						<div class="eyebrow">
							<span onclick="return false" >Media Gallery</span>
						</div>
						<div id="mediaList" style="padding: 4px;" ></div>
						<div id="waitMediaMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div class="view">
							<a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?backlink=ref&id=${command.user.id}">Visit Gallery</a>
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
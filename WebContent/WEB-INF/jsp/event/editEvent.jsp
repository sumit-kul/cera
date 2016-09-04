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
		<meta name="keywords" content="Jhapak, event, edit-event, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>Edit ${command.name} - Jhapak</title>
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
 		
 		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		
 		<script type="text/javascript" src="js/cropbox.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/cropImage.css" />
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
				height: 260px;
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
            
            span.evntDraft {
            	background: #66799f none repeat scroll 0% 0%;
            	text-decoration: none;
				display: inline-block;
				color: #FFF;
				padding: 7px 10px 6px;
				vertical-align: text-bottom;
				line-height: 12px;
				font-size: 12px;
				font-weight: bold;
				text-transform: uppercase;
				text-align: center;
				border-radius: 22px;
            }
            
            span.evntLive {
            	background: #00CC52 none repeat scroll 0% 0%;
            	text-decoration: none;
				display: inline-block;
				color: #FFF;
				padding: 7px 10px 6px;
				vertical-align: text-bottom;
				line-height: 12px;
				font-size: 12px;
				font-weight: bold;
				text-transform: uppercase;
				text-align: center;
				border-radius: 22px;
            }
            
            .intSubHeaderMain {
            	font-size: 15px;
				line-height: 1.2em;
				font-weight: 400;
				margin-top: 4px;
            }
            
            .leftImgBig {
				float: left;
				width: 170px;
				margin-top: 8px;
			}
			
			.leftImgBig img {
				-webkit-border-radius: 3px;
				-moz-border-radius: 3px;
				border-radius: 3px;
				height: 170px;
				overflow: hidden;
				/*padding: 0 5px; */
				width: 170px;
				margin: 0 10px 0 0;
			}
			
			#container .inputForm .innerBlock form input.disField{
            	background: #F5F5F5 none repeat scroll 0% 0%;
            }
		</style>
		
		<script src="https://maps.googleapis.com/maps/api/js"></script>
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
            	
            	var lat = document.getElementById('latitude').value;
            	var lng = document.getElementById('longitude').value;
            	initialize(lat, lng);

            	var eType = document.getElementById('heventType').value;

				if(eType == 0){
					document.getElementById('inviteeSection').style.display = 'none';
				}
				if(eType == 1){
					document.getElementById('inviteeSection').style.display = 'inline-block';
				}
            	
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
						document.getElementById('heventType').value = 0;
					}
					if(somval == 2000){
						document.getElementById('heventType').value = 1;
					}
					document.getElementById('status').value = finalVersion;
					document.newEventForm.action = "${communityEraContext.requestUrl}";
					document.newEventForm.submit();
			    }
			}
		</script>
		
		<script type="text/javascript">
			function addInvitees(communityId) 
			{ 
				var dialogInstance = BootstrapDialog.show({
					title: 'Invitee list',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 380px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }, {
	                label: 'Save',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                var flist = $( ".clearfix2" ).map(function() {return this.id;}).get().join();
	                    processSave(flist);
	                    dialog.close();
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/event/makeInviteeList.ajx?communityId="+communityId ,dataType: "json",success:function(result){
						var stoggle = "";
						var sName = "<div style='overflow: hidden;'><div class='finallist' style='padding-right: 7px;'><div style='padding:10px;'>Final list</div><div id='scrollpane' class='scroll-pane2'><form id='formset' method='post' action=''></form></div>";
						sName += "</div><div><div style='width:70%;'>";						
						//sName += "<div class='search'>";
						//sName += "<input tabindex='0' value='' class='' type='text' />";
						//sName += "</div>";
						
						sName += "<div class='lev'><div class='scroll-pane horizontal-only'><ul>";
						$.each(result.aData, function() {
							if(this['commId'] == communityId){
								sName += "<li class='lientrySelected' style='margin:4px 0px;' id='commID-"+this['commId']+"' onclick='refreshCommunities(&#39;"+this['commId']+"&#39;, &#39;"+dialogRef+"&#39;);'>"+this['commName']+"</li>";
							} else {
								sName += "<li class='lientry' style='margin:4px 0px; padding:2px;' id='commID-"+this['commId']+"' onclick='refreshCommunities(&#39;"+this['commId']+"&#39;, &#39;"+dialogRef+"&#39;);'>"+this['commName']+"</li>";
							}
						});

						sName += "</ul></div></div>";
						
						var temp = "";
						var counter = 0;
						$.each(result.bData, function() {
							counter += 1;
							var rid = this['userId'];
							stoggle += rid + "#" + this['photoPresant'] + "#" +this['name']+ ",";
							temp += "<li class='lientry'><div class='clearfix' id='"+rid+"' onclick='expand(&#39;"+rid+"&#39;, &#39;"+this['photoPresant']+"&#39; , &#39;"+this['name']+"&#39;)'>";
							if(this['photoPresant'] == "1"){
								temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' />"; }
							 else {
								 temp += "<img src='img/user_icon.png' />";
							 }
							temp += "<span id='selection-"+rid+"' class='selection2'></span><div class='header'>"+this['name']+"</div><div class='loc'>"+this['location']+"</div></div></li>";
							});

						sName += "<div class='rev'><div class='clearfix'><div class='tohele'>INVITEES ("+counter+")</div><div class='toheri'><a href='javascript:void(0);' onclick='toggleSelect(&#39;0&#39;,&#39;"+stoggle+"&#39;);'>Select All</a></div></div><div class='scroll-pane horizontal-only'><ul>";
						sName += temp;
						sName += "</ul></div></div>";

						
						sName += "</div></div>";
						sName += "</div>";
						
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
	    		        dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
	    		       // }
	    		        $.each(result.bData, function() {
		    		        var id = this['userId'];
		    		        var photoPresant = this['photoPresant'];
		    		        var name = this['name'];
	    		        	var fset = document.getElementById('formset');
	    					var spanTag = document.getElementById('selection-'+id);
	    					var ss = id+"#"+photoPresant+"#"+name;
    					   spanTag.className = 'selection2';
    					   
    					   var tag = "<div class='clearfix2' id='"+ss+"' onclick='rmFlist(&#39;"+id+"&#39;, &#39;"+ss+"&#39;)'>";
    					   if(photoPresant == "1"){
    						   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+id+"'  width='30' height='30' style='float: left;'/>"; }
    						 else {
    							 tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
    						 }
    					   tag += "<span id='finalSelection-"+id+"' class='selection3'></span><div class='header2'>"+name+"</div></div>";
    					   document.getElementById('formset').innerHTML += tag;
    					   dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
   	    		        		{
   	    		        			verticalDragMinHeight: 20,
   	    		        			verticalDragMaxHeight: 200,
   	    		        			autoReinitialise: true
   	    		        			}
   	    	    		        );
    					   dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
      	    		        		{
      	    		        			verticalDragMinHeight: 20,
      	    		        			verticalDragMaxHeight: 200,
      	    		        			autoReinitialise: true
      	    		        			}
      	    	    		        );
	    		        });
						
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

			function refreshCommunities(communityId, dialogRef) {
				$.ajax({url:"${communityEraContext.contextUrl}/event/makeInviteeList.ajx?communityId="+communityId ,dataType: "json",success:function(result){
					var stoggle = "";
					var sName = "";						
					$.each(result.aData, function() {
						if(this['commId'] == communityId){
							var spanTag = document.getElementById('commID-'+this['commId']);
							   spanTag.className = 'lientrySelected';
						} else {
							var spanTag = document.getElementById('commID-'+this['commId']);
							   spanTag.className = 'lientry';
						}
					});

					
					var temp = "";
					var counter = 0;
					$.each(result.bData, function() {
						counter += 1;
						var rid = this['userId'];
						stoggle += rid + "#" + this['photoPresant'] + "#" +this['name']+ ",";
						temp += "<li class='lientry'><div class='clearfix' id='"+rid+"' onclick='expand(&#39;"+rid+"&#39;, &#39;"+this['photoPresant']+"&#39; , &#39;"+this['name']+"&#39;)'>";
						if(this['photoPresant'] == "1"){
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' />"; }
						 else {
							 temp += "<img src='img/user_icon.png' />";
						 }
						var trigerList = $("div[class='clearfix2']");
						var selCss = "selection";
						var temp2 = rid + "#" + this['photoPresant'] + "#" +this['name'];
						 $.each(trigerList, function() {
	  					   if ( $( this ).attr("id") ==  temp2) {
	  						 selCss = "selection2";
	  					   }
						   });
						temp += "<span id='selection-"+rid+"' class='"+selCss+"'></span><div class='header'>"+this['name']+"</div><div class='loc'>"+this['location']+"</div></div></li>";
						});

					sName += "<div class='clearfix'><div class='tohele'>INVITEES ("+counter+")</div><div class='toheri'><a href='javascript:void(0);' onclick='toggleSelect(&#39;0&#39;,&#39;"+stoggle+"&#39;);'>Select All</a></div></div><div class='scroll-pane horizontal-only'><ul>";
					sName += temp;
					sName += "</ul></div></div>";

					
					sName += "</div></div>";

					$(".rev").html(sName);
					$('.scroll-pane').jScrollPane(
    		        		{
    		        			verticalDragMinHeight: 20,
    		        			verticalDragMaxHeight: 200,
    		        			autoReinitialise: true
    		        			}
    	    		        );
    		       // }
    			    }
    			 	
    		    });
			}

			function expand(id, photoPresant, name){
				var fset = document.getElementById('formset');
				var spanTag = document.getElementById('selection-'+id);
				var ss = id+"#"+photoPresant+"#"+name;
			   if(spanTag.className == "selection") {
				   if (spanTag.parentNode != fset){
				   spanTag.className = 'selection2';
				   
				   var tag = "<div class='clearfix2' id='"+ss+"' onclick='rmFlist(&#39;"+id+"&#39;, &#39;"+ss+"&#39;)'>";
				   if(photoPresant == "1"){
					   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+id+"'  width='30' height='30' style='float: left;'/>"; }
					 else {
						 tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
					 }
				   tag += "<span id='finalSelection-"+id+"' class='selection3'></span><div class='header2'>"+name+"</div></div>";
				   document.getElementById('formset').innerHTML += tag;
				   }
			   } else {
				   spanTag.className = 'selection';
				   fset.removeChild(document.getElementById(ss));
			   }

			   $('#scrollpane').jScrollPane(
		        		{
		        			verticalDragMinHeight: 20,
		        			verticalDragMaxHeight: 200,
		        			autoReinitialise: true
		        			}
	    		        );
			}

			function rmFlist(id, fid){
				var fset = document.getElementById('formset');
				fset.removeChild(document.getElementById(fid));
				var spanTag = document.getElementById('selection-'+id);
				spanTag.className = 'selection';
			}

			function processSave(ids){
				var invtList = document.getElementById('invtList');
				var fset = document.getElementById('finalList');
				
				if (ids != "") {
				var array = ids.split(',');
				if (array.length > 0){
					var tag = "";
					var temp ="";
					for(var x=0; x < array.length ;x++){
						var inner = array[x];
						var innerArray = inner.split('#');
						var spanTag = document.getElementById('selection-'+innerArray[0]);
						if (x < 15) {
						   if(innerArray[1] == "1"){
							   tag += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+innerArray[0]+"&backlink=ref' title='"+innerArray[2]+"' >";
							   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+innerArray[0]+"'  width='43' height='43' style='float: left;padding: 0px 3px 3px 0px;'/></a>"; 
							   } else {
								   tag += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+innerArray[0]+"&backlink=ref' onmouseover='"+${row.firstName} ${row.lastName}+"' >";
								   tag += "<img src='img/user_icon.png'  width='43' height='43' style='float: left;padding: 0px 3px 3px 0px;'/></a>"; 
							 }
						}
						temp += innerArray[0]+",";
					}
					var fc = invtList.firstChild;
					while( fc ) {
						invtList.removeChild( fc );
					    fc = invtList.firstChild;
					}
					invtList.innerHTML += tag;
					fset.value = ids;
				}
				} else {
					var fc2 = invtList.firstChild;
					while( fc2 ) {
						invtList.removeChild( fc2 );
					    fc2 = invtList.firstChild;
					}
					fset.value = ids;
				}
			}

			
	function toggleSelect(isAllSelect, ids) {
		var array = ids.split(',');
		var invtList = document.getElementById('invtList');
		var fset = document.getElementById('formset');
		if (array.length > 0) {
			
			for ( var x = 0; x < array.length; x++) {
				var tag = "";
				var ss = array[x];
				var innerArray = ss.split('#');
				var spanTagleft = document.getElementById('selection-' + innerArray[0]);
				var spanTagright = document.getElementById('finalSelection-' + innerArray[0]);
				
				spanTagleft.className = 'selection2';
				if (!spanTagright) {
					tag += "<div class='clearfix2' id='" + ss
							+ "' onclick='rmFlist(&#39;" + innerArray[0] + "&#39;, &#39;"
							+ ss + "&#39;)'>";
					if (innerArray[1] == "1") {
						tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="
								+ innerArray[0]
								+ "'  width='30' height='30' style='float: left;'/>";
					} else {
						tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
					}
					tag += "<span id='finalSelection-"
							+ innerArray[0]
							+ "' class='selection3'></span><div class='header2'>"
							+ innerArray[2] + "</div></div>";
					document.getElementById('formset').innerHTML += tag;
						}
						
					}
					
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
					document.getElementById('inviteeSection').style.display = 'none';
					document.getElementById('heventType').value = 0;
				}
				if(somval == 2000){
					document.getElementById('inviteeSection').style.display = 'inline-block';
					document.getElementById('heventType').value = 1;
				}
			}
			
		    function uploadImage(communityId) 
			{ 
		    	var cropper = "";
		    	var options = {thumbBox: '.thumbBox', spinner: '.spinner', imgSrc: '' };
		    	
       	 		$('#file').trigger('click');
       	 		cropper = $('.imageBox').cropbox(options);
       	 		
       	 		$('#file').on('change', function(){
       	 		$('.instructionDrag').css({'display': 'inline'});
       	 		$('.zoomOut').css({'display': 'inline'});
       	 		$('.zoomIn').css({'display': 'inline'});
       			$('.btnSave').css({'display': 'inline'});

       			$('.zoomOut').on('click', function(){
       				cropper.zoomOut();
       			});

       			$('.zoomIn').on('click', function(){
       				cropper.zoomIn();
       			});

       			$('.btnSave').on('click', function(){
       				var img = cropper.getBlob();
					var data = new FormData(); 
					var evId = $('#eventId');
					data.append("file", img);
					$.ajax({url:"${communityEraContext.contextUrl}/event/saveEventPoster.img?eventId="+evId.val()+"&communityId="+communityId,
						data: data,
					    enctype: 'multipart/form-data',
					    processData: false,  // do not process the data as url encoded params
					    contentType: false,   // by default jQuery sets this to urlencoded string
					    type: 'POST',
					    dataType: "json",
						success:function(result){
						var rs = result.eventId;
						$("#eventId").val(rs);
						$('.instructionDrag').css({'display': 'none'});
		       	 		$('.zoomOut').css({'display': 'none'});
		       	 		$('.zoomIn').css({'display': 'none'});
		       			$('.btnSave').css({'display': 'none'});

		       			$("#waitMessage").hide();
				    },
				 	// What to do before starting
			         beforeSend: function () {
				    	$("#waitMessage").show();
			         } 
	
		    	  });
       			});
                
                var reader = new FileReader();
                reader.onload = function(e) {
                    options.imgSrc = e.target.result;
                    cropper = $('.imageBox').cropbox(options);
                }
                reader.readAsDataURL(this.files[0]);
                this.files = [];
            });
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
				<div class="nav-list">
					<ul>
						<li><a href="${communityEraContext.currentCommunityUrl}/home.do">Home</a></li>
						<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do">Blogs</a></li>
						<li class="selected"><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do">Events</a></li>
						<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do">Forums</a></li>
						<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do">Library</a></li>
						<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do">Connections</a></li>
						<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do">Wikis</a></li>  
					</ul>
				</div>
			</div>
		</div>
			<div id="container">
				<div class="left-panel">
					<div class="inputForm" style="display: inline-block;">
						<div class="intHeaderMain">
						 	${command.name} 
						 	<c:choose>
								<c:when test="${command.hstate == 0}">	
									<span class="evntDraft">Draft</span>	
								</c:when>
								<c:otherwise>
									<span class="evntLive">Live</span>
								</c:otherwise>
							</c:choose>
							<div class="intSubHeaderMain">
								<c:choose>
									<c:when test='${command.dateStarted != ""}'>					 
										 ${command.dateStarted}
										<c:if test='${command.dateEnded != ""}'>
											&nbsp;to&nbsp; ${command.dateEnded}
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test='${command.dateEnded != ""}'>
											--&nbsp;${command.dateEnded}
										</c:if>
									</c:otherwise>
								</c:choose>
								<br />
								<c:choose>
									<c:when test='${command.venue != ""}'>					 
										 ${command.venue}
										<c:if test='${command.city != ""}'>
											&nbsp;,&nbsp; ${command.city}
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test='${command.city != ""}'>
											${command.city}
										</c:if>
									</c:otherwise>
								</c:choose>
								, ${command.country} 
							</div>
					 	</div>
					 	<div class="innerBlock" >
							<form:form showFieldErrors="true" commandName="command" multipart="true" name="newEventForm">
								
								<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
								<div>
									<label for="eventname" class="normalTip" title="This field is required."><strong>Event name</strong> (100 characters max.) <img src="img/required.gif" width="8" height="8" class="required" /></label><br />
									<form:input id="name" cssClass="editInfo" path="name" maxlength="100"  cssStyle="width: 700px;"/>
								</div>
								<br />
								<div class="inputForm2">
									<div id="photocontainerEvent">
										<c:choose>
											<c:when test="${command.eventPhotoPresent}">	
												<a href="javascript:void(0);" onclick="uploadImage('${communityEraContext.currentCommunity.id}');" class="button" style="text-decoration: none;">Edit Event Poster</a>
												<div class="container" style="background-image: url('${communityEraContext.contextUrl}/event/eventPoster.img?id=${command.id}');" >	
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0);" onclick="uploadImage('${communityEraContext.currentCommunity.id}');" class="button" style="text-decoration: none;">Add Event Poster</a>
												<div class="container">
											</c:otherwise>
										</c:choose>
										<div class="imageBox">
										
										<div class="thumbBox"></div><div class="spinner" style="display: none">Loading...</div></div>
										<div class="instructionDrag" style="display: none"><div class="instructions">Drag to Reposition Poster</div></div>
										<div id="waitMessage" style="display: none;position: absolute;top: 90px;">
											<div class="cssload-square" >
												<div class="cssload-square-part cssload-square-green" ></div>
												<div class="cssload-square-part cssload-square-pink" ></div>
												<div class="cssload-square-blend" ></div>
											</div>
										</div>
										<div class="zoomOut" style="display: none; right: 144px;font-size: 14px;padding: 4px 12px;" id="btnZoomOut"></div>
										<div class="zoomIn" style="display: none; right: 106px;font-size: 14px;padding: 4px 12px;" id="btnZoomIn"></div>
										<div class="btnSave" style="display: none; right: 6px;" id="btnCrop">Save Poster</div>
						    			<div class="action"><input type="file" id="file" style="display: none" /></div><div class="cropped"></div></div>
									</div><!-- /#photocontainer -->
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
											<form:input id="eventId" path="eventId" cssStyle="display: none;" />
											<form:input id="heventType" path="heventType" cssStyle="display: none;" />
											<form:input id="status" path="hstate" cssStyle="display: none;" />
											<form:input id="dateStarted" path="dateStarted" cssStyle="display: none;" />
											<form:input id="dateEnded" path="dateEnded" cssStyle="display: none;" />
											<form:input id="posterPhotoPresent" path="posterPhotoPresent" cssStyle="display: none;" />
											<form:input id="hposterId" path="hposterId" cssStyle="display: none;" />
											
											<input id="finalList" type="hidden" name="finalList" />
										</div>
										<div id="mapDis" class="mapArea">
											<div id="map_canvas"></div> 
										</div>
									</div>
								</div>
								
								<div style="width: 100%; display: inline-block;">
						    		<label for="contactName"><strong>Who's hosting this event</strong></label><br />
						    		<div class='leftImgBig'>
						    			<c:choose>
											<c:when test="${command.posterPhotoPresent}">		
												<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${command.hposterId}" width="160" height="160" /> 
											</c:when>
											<c:otherwise>
												<img src='img/user_icon.png' width='160' height='160' />
											</c:otherwise>
										</c:choose>
						    		</div>
						    		<div style="width: 72%; float: right; margin: 8px 8px 0px 0px;">
							    		<form:input cssClass="editInfo disField" id="contactName" path="posterName" readonly="readonly" cssStyle="width: 96%;"/><br />
										<form:input cssClass="editInfo disField" id="contactTel" path="posterMobile" readonly="readonly" cssStyle="width: 25%;"/>
										<form:input cssClass="editInfo disField" id="contactEmail" path="posterEmail"  readonly="readonly" cssStyle="width: 64%; "/><br />
										<form:input cssClass="editInfo disField" id="weblink" path="posterWeb" readonly="readonly" cssStyle="width: 96%;"/><br />
									</div>
								</div>
								<br /><br />
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
									<label style="float: left;"><strong>Event Category</strong></label><br />
										<form:dropdown path="eventCategory" mandatory="true">
										<form:option value="" label="Select the category of event..."/>
										<form:optionlist options="${referenceData.eventCategoryOptions}"/>
									</form:dropdown>
								</div>
								<div id="inviteeSection" style="width: 100%; clear: both; position: relative; margin: 10px 0px; ">
									<label style="float: left;"><strong>Guests</strong></label><br />
							   		<div id="invtList" style="padding: 2px 0px 0px 2px; width: 720px; border: 1px solid rgb(210, 210, 210); height:93px; margin: 4px 0px 20px;">
							   			<c:forEach items="${command.inviteeList}" var="row" varStatus="status">	
							   				<c:choose>
												<c:when test="${row.photoPresent}">		
													<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref" title="${row.firstName} ${row.lastName}">
														<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.id}" width="43" height="43" style="padding: 0px 0px 3px 0px;"/>
													</a> 
												</c:when>
												<c:otherwise>
													<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref" onmouseover="${row.firstName} ${row.lastName}" >
														<img src="img/user_icon.png"  id="photoImg" width="43" height="43" style="padding: 0px 0px 3px 0px;" />
													</a>
												</c:otherwise>
											</c:choose>
							   			</c:forEach>
							   		</div>
							   		<a href="javascript:void(0);" onclick="addInvitees('${communityEraContext.currentCommunity.id}');" class="btnmain" >Invite Guests and Friends</a><br />
							   	</div>
							   	
							   	<div style="width: 100%; margin-top:20px;">
								   	<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="20" parentType="event" />
								</div>
						    </form:form>
					  	</div>
					   	
						<div class="right" style="margin: 10px 10px; display: inline-block; float: right;">
							<c:choose>
								<c:when test="${command.hstate == 0}">
									<a href="javascript:void(0);" onclick="publishEvent(1);" class="btnmain" style="float: right;"><i class="fa fa-check" style="margin-right: 4px;"></i>Make Event Live</a>
									<a href="javascript:void(0);" onclick="publishEvent(0);" class="btnmain" style="float: right; margin-right: 6px;"><i class="fa fa-save" style="margin-right: 4px;"></i>Save as Draft</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" onclick="publishEvent(1);" class="btnmain" style="float: right;"><i class="fa fa-check" style="margin-right: 4px;"></i>Save Changes</a>
								</c:otherwise>
							</c:choose>
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
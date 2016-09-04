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
		<meta name="author" content="${command.firstName} ${command.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.firstName} ${command.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/interest.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
 		<style>
 			#container .left-panel .nav-list ul li.selected {
			    width: 140px;
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
            
            .success-dialog .modal-dialog {
                width: 400px;
            }
            
            .modal-body {
			    padding: 0px;
			    min-height: 140px;
			}
			
			.modal-footer {
			    padding: 8px;
			    text-align: right;
			    border-top: 1px solid #E5E5E5;
			}
			
			.btn {
				padding: 4px;
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
				background: none repeat scroll 0% 0% #FFF;
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
			
			.count-txt {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    padding-left: 20px;
			    font-size: 11px;
			}
			
			.copyLnk{
				color: #2A3A47;
				text-decoration: none;
				font-size: 11px;
				font-weight: bold;
				cursor: pointer;
			}
			
			.copyLnk:hover{
				outline: medium none;
			}
			
			.large {
			    font-size: 1.25em;
			    line-height: 1.2667;
			    margin: 10px 0px 15px;
			    white-space: normal;
				text-align: left;
			}
			
			.columnLeft {
				width: 48%;
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
				width: 200px;
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
			
			li.lientry {
				border: 1px solid #FFF;
				-moz-border-radius: 2px;
				-webkit-border-radius: 2px;
				border-radius: 2px;
				-khtml-border-radius: 2px;
				text-decoration: none;
				padding: 3px;
				/*width: 280px; */
				cursor: pointer;
				max-width: 250px;
			    text-overflow: ellipsis;
			    overflow: hidden;
			    vertical-align: middle;
			    white-space: nowrap;
			}
			
			li.lientry:hover {
				background-color: #F6F7F8;
				border-color: rgba(0, 0, 0, 0.1);
				box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.08);
				cursor: pointer; 
				/*width: 280px; */
				text-decoration: none;
				max-width: 250px;
			    text-overflow: ellipsis;
			}
			
			.rmInst {
				padding:3px 1px 2px 4px; 
				font-weight:bold; 
				color: rgb(137, 143, 156); 
				font-size: 12px;
				cursor: pointer;
			}
 		</style>
 		
 		 		
		<script type="text/javascript">
			function changeStatus(){
				var btn = document.getElementById('button-save');
				document.getElementById('button-save').className = "";
				document.getElementById('button-save').className = "newButton";
				document.getElementById('button-save').disabled = false;
			}

			function calculateSum(commonInterest){
				var somval = $( "input:checkbox[name=copyInterest]:checked" ).length - commonInterest;
				$( "#selectedInterest" ).html("<span class='count-txt'>"+somval+" selected</span>");

				var btn = document.getElementById('button-add');
				if (somval == 0) {
					document.getElementById('button-add').className = "newButton";
					document.getElementById('button-add').disabled = true;
				} else {
					document.getElementById('button-add').className = "";
					document.getElementById('button-add').className = "newButton";
					document.getElementById('button-add').disabled = false;
				}
				
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
					var newtxt = '';
					if (next == 1) {
						newtxt = 'You have added '+next+' interest';
					} else {
						newtxt = 'You have added '+next+' interests';
					}
					$('#intListCount').html(newtxt);
    			    }
    		    });
			}

			function copyInterstToMe(name, profileId, commonInterest){
				var dialogInstance = BootstrapDialog.show({
					title: 'Copy '+name+'\'s interests',
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
	                label: 'Add interests',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	            	var json = []; 
	            	$('input:checkbox[name=copyInterest]:checked').each(function(i, selected){ 
	            		json[i] = $(selected).attr('id');
	            		var intid = 'ID'+json[i];
	            		document.getElementById(intid).className = "";
	            		document.getElementById(intid).className = "intrst-txt-seleceted";
	            	});
	            	$.ajax({
	            		type:"POST",
	                    url: "${communityEraContext.contextUrl}/pers/makeMyInterest.ajx",
	                    data: {json:json},
	                    success:function(result){
	                    	dialog.close();
	                    	var somval = $( "input:checkbox[name=copyInterest]:checked" ).length;
	                    	var common = $( "#commonInterest" ).val();
	                    	var remain = Number(somval) - Number(common);
	                    	$( "#currCommInt" ).html(somval);
	                    	
	        				var dialogInstance = BootstrapDialog.show({
	        					title: 'Copy '+name+'\'s interests',
	        					type: BootstrapDialog.TYPE_SUCCESS,
	        					message: function(dialog) {
	        					var $message = $('<div id="main"><p class="seccMsg">'+remain+' interest of '+name+' was added to your profile.</p></div>');
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
	        	            	}]
	        				});
	        			}
	                });
	                	//dialog.close();
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/listInterestToCopy.ajx?profileId="+profileId ,dataType: "json",success:function(result){
						var sName = "";
						var lfcol = "<div class='columnLeft' style='margin:20px 5px 10px 10px; width:46%;'>";
						var rhcol = "<div class='columnRight' style='margin:20px 10px 10px 5px; width:46%;'>";
						$.each(result.aData, function() {
							var select = "";
							var checked = "";
							var stl = "max-width: 240px;";
							if (this['selected'] > 0) {
								select = "disabled";
								checked = "checked";
								stl = "color: #365BD4; font-weight:bold; max-width: 240px;";
							} 
							if(this['oddRow']){
								lfcol += "<div style='margin-bottom:10px;'><input name='copyInterest' id='"+this['id']+"' "+checked+" "+select+" tabindex='2' type='checkbox' onclick='calculateSum("+commonInterest+");'/><label for='"+this['id']+"' style='"+stl+"'><span></span>"+this['interest']+"</label></div>";
							} else {
								rhcol += "<div style='margin-bottom:10px;'><input name='copyInterest' id='"+this['id']+"' "+checked+" "+select+" tabindex='2' type='checkbox' onclick='calculateSum("+commonInterest+");'/><label for='"+this['id']+"' style='"+stl+"'><span></span>"+this['interest']+"</label></div>";
							}
						});
						lfcol += "</div>";
						rhcol += "</div>";
						sName += "<div  style='width:100%; display: inline-block; height:400px;'><div class='scroll-pane horizontal-only'>"+lfcol+rhcol+"";
						sName += "<div id='selectedInterest'  style='width:100%; float: left;'><span class='count-txt'>0 selected</span></div></div></div>";
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

			function addToMyList(interestId, interest) {
				$.ajax({url:"${communityEraContext.contextUrl}/pers/makeMyInterest.ajx?interestId="+interestId ,dataType: "json",success:function(result){
					if(result.addedInterestId > 0) {
		               	var sName = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.addedInterestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+interest+"</span><span class='blocker'></span></div></div>";
						$( "#myInterests" ).append(sName);
						var sName2 = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.addedInterestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+interest+"</span><span class='normalTip rmInst' onclick='removeInterest(&#39;"+result.interestId+"&#39;);' title='Remove &#39;"+interest+"&#39; from your interest list' >X</span></div></div>";
						$( "#inputForm" ).append(sName2);
						currSize = $( "#interestListSize" ).val();
						var next = Number(currSize) + Number(1);
						$( "#interestListSize" ).val(next);
						$('#intNumber').val(next);
						var newtxt = '';
						if (next == 1) {
							newtxt = 'You have added '+next+' interest';
						} else {
							newtxt = 'You have added '+next+' interests';
						}
						$('#intListCount').html(newtxt);
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
	    					var sName2 = "<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests"+result.interestId+"'><div class='intrst' id='"+result.interestId+"'><span class='intrst-txt'>"+result.interest+"</span><span class='blocker'></span></div></div>";
	    					$( "#inputForm" ).append(sName2);
	    					currSize = $( "#interestListSize" ).val();
							var next = Number(currSize) + Number(1);
							$( "#interestListSize" ).val(next);
							$('#intNumber').val(next);
							var newtxt = '';
							if (next == 1) {
								newtxt = 'You have added '+next+' interest';
							} else {
								newtxt = 'You have added '+next+' interests';
							}
							$('#intListCount').html(newtxt);
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
				$( ".lientry" ).each(function( index ) {
					$( this ).removeClass("countClass")
					});
				$("#"+categoryId).addClass("countClass");
				$('#refBound').html("");
				$.ajax({url:"${communityEraContext.contextUrl}/pers/interestList.ajx?catId="+categoryId+"&offset="+offset+"&interestCount="+interestCount ,dataType: "json",success:function(result){
					var sName = "";
					$.each(result.aData, function() {
						sName += "<li class='lientry' style='color: #2A6496;' id='"+this['id']+"' onclick='addToMyList(&#39;"+this['interestId']+"&#39;, &#39;"+this['interest']+"&#39;);'>"+this['interest']+"</li>";
					});
					if(result.interestCount > 14) {
						sName += "<li class='lientry' style='color: #20394D; font-weight: bold;' id='"+this['id']+"' onclick='refreshInterests(&#39;"+categoryId+"&#39;, &#39;"+result.offset+"&#39;, &#39;"+result.interestCount+"&#39;);'>More interests</li>";
					}
					$('#refBound').html(sName);
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
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/interesCatList.ajx" ,dataType: "json",success:function(result){
						var sName = "<div class='searchInt' id='searchInt'>";
						sName += "<input id='intSearch' tabindex='0' value='' class='searchResult' placeholder='Search for interests...' maxlength='50' autocomplete='off' type='text' onkeydown='searchInterest()' onmousedown='searchInterest()' />";
						sName += "<i class='ico--search'></i>";
						sName += "</div>";
						
						sName += "<div class='scroll-pane horizontal-only'> <div style='height:405px;'>";
						sName += "<div id='myInterests'></div><p class='addListHead' >Add intrest from below list</p>";
						
						sName += "<div class='finallist'>";
						
						sName += "<div class='columnLeft' style='margin:0px 5px 10px 10px; width:46%;'><ul class='refInt'>";
						var count = 0;
						var countClass = "countClass";
						$.each(result.aData, function() {
							if(count > 0 ){
								countClass = "";
							}
							sName += "<li class='lientry "+countClass+"' style='margin:4px 0px; padding:2px;' id='"+this['categoryId']+"' onclick='refreshInterests(&#39;"+this['categoryId']+"&#39;, &#39;1&#39;, &#39;0&#39;);'>"+this['category']+"</li>";
							count ++;
						});
						sName += "</ul></div>";
						sName += "<div class='columnRight' style='width:250px;'><ul class='refBound' id='refBound' style='min-height: 355px;'></ul></div>";
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
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {

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
			    
				$('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
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
						<c:if test="${communityEraContext.currentUser.id != command.user.id}">
							<c:if test="${command.contactionAllowed}">				
								<div class='actions3' id='connectionInfo'>${command.returnString}</div>
							</c:if>
                       	</c:if>
					</div>
					
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.id}">Gallery</a></li>
						</ul>
					</div>
					
					
					<div class="inputForm" style="padding: 0px;" id="inputForm">
					<input type="hidden" id="interestListSize" name="interestListSize" value="${command.interestListSize}" />
					<input type="hidden" id="commonInterest" name="commonInterest" value="${command.commonInterest}" />
						<c:choose>
							<c:when test="${command.user.inactive}">
								 <div class="innerBlock" style="padding: 10px 10px 20px;">
								 	<p style="color: rgb(137, 143, 156); word-wrap: break-word;">This user is no longer registered. The information below may be obsolete </p>
								 </div>
							</c:when>
							<c:when test="${command.interestListSize == 0}">
								
								<c:if test="${command.user.id != communityEraContext.currentUser.id}">
									<div class="intHeader">
										<span style="font-size:13px; margin-top:0px; padding:10px; float: left; color: #AAA6A6;">Unfortunately, ${command.fullName} has not added any interests yet.</span>
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${command.user.id == communityEraContext.currentUser.id}">
									<div class="intHeader">
										<span id='connectionInfo'>
											<a onclick="addInterest(0)" href="javascript:void(0);" title="Add Interest" class="btnmain normalTip" ><i class="fa fa-plus" style="font-size: 14px; margin-right: 4px;"></i>Add more interest</a>
										</span>
										<span style="font-size:13px; margin-top:0px; padding:10px; float: left; color: #AAA6A6;" id="intListCount">You have added <i id="intNumber">${command.interestListSize}</i>
											<c:if test="${command.interestListSize == 1}">
											interest
											</c:if>
											<c:if test="${command.interestListSize > 1}">
											interests
											</c:if>
										</span>
									</div>
								</c:if>
								<c:if test="${command.user.id != communityEraContext.currentUser.id}">
									<div class="intHeader">
										<c:if test="${communityEraContext.userAuthenticated}">
											<span style="font-size:11px; margin-top:0px; padding:10px; float: right; color: #2A3A47;">
												<c:if test="${command.commonInterest == 1}">
													<i id="currCommInt">${command.commonInterest}</i> INTEREST IN COMMON
												</c:if>
												<c:if test="${command.commonInterest > 1}">
													<i id="currCommInt">${command.commonInterest}</i> INTERESTS IN COMMON
												</c:if>
											</span>
										</c:if>
										<span style="font-size:13px; margin-top:0px; padding:10px; float: left; color: #AAA6A6;">${command.fullName} has added ${command.interestListSize}
											<c:if test="${command.interestListSize == 1}">
												interest
												</c:if>
												<c:if test="${command.interestListSize > 1}">
												interests
											</c:if>
										</span>
									</div>
								</c:if>
								
								<c:forEach items="${command.interestList}" var="row" >
									<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests${row.id}'>
										<c:choose>
											<c:when test="${row.selected > 0}">
												<c:if test="${command.user.id == communityEraContext.currentUser.id}">
													<div class='intrst' id="outInt"><span id='ID${row.id}' class='intrst-txt'>${row.interest}</span><span class='normalTip rmInst' onclick="removeInterest(&#39;${row.id}&#39;);" title="Remove &#39;${row.interest}&#39; from your interest list" >X</span></div>
												</c:if>
												<c:if test="${command.user.id != communityEraContext.currentUser.id}">
													<div class='intrst-seleceted' id="outInt"><span id='ID${row.id}' class='intrst-txt-seleceted toBeCopied'>${row.interest}</span><span class='blocker'></span></div>
												</c:if>
											</c:when>
											<c:otherwise>
												<div class='intrst' id="outInt"><span id='ID${row.id}' class='intrst-txt toBeCopied'>${row.interest}</span><span class='blocker'></span></div>
											</c:otherwise>
										</c:choose>
									</div>
								</c:forEach>
								
								<c:if test="${communityEraContext.userAuthenticated}">
									<c:if test="${command.user.id != communityEraContext.currentUser.id}">
										 <br />
										 <p style="margin: 0px 0px 10px 6px;">See some shared interests? <span class="copyLnk" onclick="copyInterstToMe(&#39;${command.firstName}&#39;, &#39;${command.user.id}&#39;, &#39;${command.commonInterest}&#39;);">Add them to your profile!</span></p>
									</c:if>
								</c:if>
							</c:otherwise>
						</c:choose>
				 	</div> <!-- /myUpload --> 
				 </div>
				</div><!-- /#leftPannel -->
				
				<div class="right-panel" style="margin-top: 0px;">
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
							<a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?backlink=ref&id=${command.user.id}">View Gallery</a>
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>
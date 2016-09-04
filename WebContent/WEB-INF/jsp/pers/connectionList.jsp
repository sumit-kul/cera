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
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			#container .left-panel .nav-list ul li.selected {
			    width: 160px;
			}
			
			#container .left-panel .commSection .paginatedList p {
				font-size: 14px;
			}
			
			#container .left-panel .commSection .paginatedList p a {
				margin-right: 0px;
			}
		</style>
		
		<script type="text/javascript">
			function addConnectionInner(userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnectionInner.ajx?id="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo-"+userId).html(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});

			        $('.dynaDropDown').each(function() {
				         $(this).qtip({
				            content: {
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title'), // Use href attribute as URL
				                        type: 'GET', // POST or GET
				                        dataType: 'json', // Tell it we're retrieving JSON
				                        data: {
				                        }
				                    })
				                    .then(function(data) {
				                    	var mName = "";
				                        api.set('content.text', data.optionInfo);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top center',  // Position my top left...
				                at: 'bottom center', // at the bottom right of...
				                target: '', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
							style: {
						        classes: 'qtip-light myCustomClass3'
						    },
						    hide: {
				                fixed: true,
				                delay: 300
				            }
				         });
				     });
		    	  }});
			}

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

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?pymkId=${command.user.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					temp += "<div style='width: 100%; display: inline-block;'>";
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' ";
						temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
						temp += this['name'];
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'  style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='50' height='50' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
						 temp += "<div style=''><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' ";
						 temp += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"' style='width: 226px; margin-top: 2px;'>";
						 temp += this['name'];
						 temp += "</a></div>";
					 }
					temp += "</div>";
					});
				 $("#pymkList").html(temp);
				 
				// Hide message
		        $("#waitPymkMessage").hide();
		        memberInfoQtip();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitPymkMessage").show();
		         } 
		    });
	
			function updateConnectionInner(userId, contactId, newStatus, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnectionInner.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
					var yourConnections = document.getElementById('yourConnections');
					var isConnAllow = document.getElementById('isConnAllow').value;
					if(yourConnections.className == "selected" && newStatus == 1 && isConnAllow == 'false'){
						$("#connResult-"+userId).remove();
						var allConnCnt = document.getElementById('allConnCnt').value - 1;
						$("#allConnectionCount").html("("+allConnCnt+")");
					}else{
			    	    $("#connectionInfo-"+userId).html(result);
			    	    $('.normalTip').qtip({ 
						    content: {
						        attr: 'title'
						    },
							style: {
						        classes: 'qtip-tipsy'
						    }
						});
	
				        $('.dynaDropDown').each(function() {
					         $(this).qtip({
					            content: {
					                text: function(event, api) {
					                    $.ajax({
					                        url: api.elements.target.attr('title'), // Use href attribute as URL
					                        type: 'GET', // POST or GET
					                        dataType: 'json', // Tell it we're retrieving JSON
					                        data: {
					                        }
					                    })
					                    .then(function(data) {
					                    	var mName = "";
					                        api.set('content.text', data.optionInfo);
					                    }, function(xhr, status, error) {
					                        api.set('content.text', 'Loading...');
					                    });
					                    return 'Loading...'; // Set some initial text
					                }
					            },
					            position: {
					            	viewport: $(window),
					            	my: 'top center',  // Position my top left...
					                at: 'bottom center', // at the bottom right of...
					                target: '', // Position it where the click was...
									adjust: { 
										mouse: false 
									} // ...but don't follow the mouse
								},
								style: {
							        classes: 'qtip-light myCustomClass3'
							    },
							    hide: {
					                fixed: true,
					                delay: 300
					            }
					         });
					     });
						}
		    	  }});
			}

			function stopFollowingInner(contactId, actionFor, userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#followId"+userId).replaceWith(result);
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
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#followId"+userId).replaceWith(result);
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

			function showCommonConns(ownerId) {
				$(".qtip").hide();
				$('#innerSection').html("");
				document.getElementById('commonConns').className = "selected";
        		document.getElementById('yourConnections').className = "";
        		document.getElementById('tabName').value = "commConn";
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showCommonConns.ajx?ownerId="+ownerId ,dataType: "json",success:function(result){
					var sName = "";
					document.getElementById('pgCountcommConn').value = result.pageCountcommConn;
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
					 $("#innerSection").html(sName);
					 
			        $('.dynaDropDown').each(function() {
				         $(this).qtip({
				            content: {
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title'), // Use href attribute as URL
				                        type: 'GET', // POST or GET
				                        dataType: 'json', // Tell it we're retrieving JSON
				                        data: {
				                        }
				                    })
				                    .then(function(data) {
				                    	var mName = "";
				                        api.set('content.text', data.optionInfo);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top center',  // Position my top left...
				                at: 'bottom center', // at the bottom right of...
				                target: '', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
							style: {
						        classes: 'qtip-light myCustomClass3'
						    },
						    hide: {
				                fixed: true,
				                delay: 300
				            }
				         });
				     });

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
			
			function showYourConnections(ownerId) {
				$(".qtip").hide();
				$('#innerSection').html("");
				document.getElementById('commonConns').className = "";
        		document.getElementById('yourConnections').className = "selected";
        		document.getElementById('tabName').value = "allConn";
				$.ajax({url:"${communityEraContext.contextUrl}/pers/connectionList.ajx?id="+ownerId+"&jPage="+1 ,dataType: "json",success:function(result){
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
					 $('#innerSection').html(sName);
					 
			        $('.dynaDropDown').each(function() {
				         $(this).qtip({
				            content: {
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title'), // Use href attribute as URL
				                        type: 'GET', // POST or GET
				                        dataType: 'json', // Tell it we're retrieving JSON
				                        data: {
				                        }
				                    })
				                    .then(function(data) {
				                    	var mName = "";
				                        api.set('content.text', data.optionInfo);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top center',  // Position my top left...
				                at: 'bottom center', // at the bottom right of...
				                target: '', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
							style: {
						        classes: 'qtip-light myCustomClass3'
						    },
						    hide: {
				                fixed: true,
				                delay: 300
				            }
				         });
				     });

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

		    var count = 2;
			function infinite_scrolling_allConn(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    		var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
			            	var currentOwner = document.getElementById('userId').value;
			            	$.ajax({
			                	url:"${communityEraContext.contextUrl}/pers/connectionList.ajx?jPage="+count+"&id="+currentOwner,
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
			   					 $("#innerSection").append(sName);
			   					$("#waitMessage").hide();
			   					$('.dynaDropDown').each(function() {
			   			         $(this).qtip({
			   			            content: {
			   			                text: function(event, api) {
			   			                    $.ajax({
			   			                        url: api.elements.target.attr('title'), // Use href attribute as URL
			   			                        type: 'GET', // POST or GET
			   			                        dataType: 'json', // Tell it we're retrieving JSON
			   			                        data: {
			   			                        }
			   			                    })
			   			                    .then(function(data) {
			   			                    	var mName = "";
			   			                        api.set('content.text', data.optionInfo);
			   			                    }, function(xhr, status, error) {
			   			                        api.set('content.text', 'Loading...');
			   			                    });
			   			                    return 'Loading...'; // Set some initial text
			   			                }
			   			            },
			   			            position: {
			   			            	viewport: $(window),
			   			            	my: 'top center',  // Position my top left...
			   			                at: 'bottom center', // at the bottom right of...
			   			                target: '', // Position it where the click was...
			   							adjust: { 
			   								mouse: false 
			   							} // ...but don't follow the mouse
			   						},
			   						style: {
			   					        classes: 'qtip-light myCustomClass3'
			   					    },
			   					    hide: {
			   			                fixed: true,
			   			                delay: 300
			   			            }
			   			         });
			   			     });

			   		        $('.normalTip').qtip({ 
			   				    content: {
			   				        attr: 'title'
			   				    },
			   					style: {
			   				        classes: 'qtip-tipsy'
			   				    }
			   				});
			                	} ,
			                	beforeSend: function () {
						             $("#waitMessage").show();
						         },
			                	complete: function () {
			                		count++;
		                    	} 
			            	});
				        }
				    }
				}

		    var commConncount = 2;
			function infinite_scrolling_commConn(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    		var totalcommConn = document.getElementById('pgCountcommConn').value;
	                	if (commConncount > totalcommConn){
	                		return false;
			        	} else {
			            	var currentOwner = document.getElementById('userId').value;
			            	$.ajax({
			                	url:"${communityEraContext.contextUrl}/pers/showCommonConns.ajx?jPage="+totalcommConn+"&ownerId="+currentOwner,
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
			   					 $("#innerSection").append(sName);
			   					$("#waitMessage").hide();
			   					$('.dynaDropDown').each(function() {
			   			         $(this).qtip({
			   			            content: {
			   			                text: function(event, api) {
			   			                    $.ajax({
			   			                        url: api.elements.target.attr('title'), // Use href attribute as URL
			   			                        type: 'GET', // POST or GET
			   			                        dataType: 'json', // Tell it we're retrieving JSON
			   			                        data: {
			   			                        }
			   			                    })
			   			                    .then(function(data) {
			   			                    	var mName = "";
			   			                        api.set('content.text', data.optionInfo);
			   			                    }, function(xhr, status, error) {
			   			                        api.set('content.text', 'Loading...');
			   			                    });
			   			                    return 'Loading...'; // Set some initial text
			   			                }
			   			            },
			   			            position: {
			   			            	viewport: $(window),
			   			            	my: 'top center',  // Position my top left...
			   			                at: 'bottom center', // at the bottom right of...
			   			                target: '', // Position it where the click was...
			   							adjust: { 
			   								mouse: false 
			   							} // ...but don't follow the mouse
			   						},
			   						style: {
			   					        classes: 'qtip-light myCustomClass3'
			   					    },
			   					    hide: {
			   			                fixed: true,
			   			                delay: 300
			   			            }
			   			         });
			   			     });

			   		        $('.normalTip').qtip({ 
			   				    content: {
			   				        attr: 'title'
			   				    },
			   					style: {
			   				        classes: 'qtip-tipsy'
			   				    }
			   				});
			                	} ,
			                	beforeSend: function () {
						             $("#waitMessage").show();
						         },
			                	complete: function () {
						        	 commConncount++;
		                    	} 
			            	});
				        }
				    }
				}
			
			// debounce multiple requests
			var _scheduledRequest = null;
			function infinite_scroll_debouncer(callback_to_run, debounce_time) {
			    debounce_time = typeof debounce_time !== 'undefined' ? debounce_time : 200;
			    if (_scheduledRequest) {
			        clearTimeout(_scheduledRequest);
			    }
			    _scheduledRequest = setTimeout(callback_to_run, debounce_time);
			}

			// usage
			$(window).scroll(function() {
				var tab = document.getElementById('tabName').value;
				if(tab == "allConn"){
					infinite_scroll_debouncer(infinite_scrolling_allConn, 400);
				}
				if(tab == "commConn"){
					infinite_scroll_debouncer(infinite_scrolling_commConn, 400);
				}
		    });
		</script>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
		        $('.dynaDropDown').each(function() {
			         $(this).qtip({
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: api.elements.target.attr('title'), // Use href attribute as URL
			                        type: 'GET', // POST or GET
			                        dataType: 'json', // Tell it we're retrieving JSON
			                        data: {
			                        }
			                    })
			                    .then(function(data) {
			                    	var mName = "";
			                        api.set('content.text', data.optionInfo);
			                    }, function(xhr, status, error) {
			                        api.set('content.text', 'Loading...');
			                    });
			                    return 'Loading...'; // Set some initial text
			                }
			            },
			            position: {
			            	viewport: $(window),
			            	my: 'top center',  // Position my top left...
			                at: 'bottom center', // at the bottom right of...
			                target: '', // Position it where the click was...
							adjust: { 
								mouse: false 
							} // ...but don't follow the mouse
						},
						style: {
					        classes: 'qtip-light myCustomClass3'
					    },
					    hide: {
			                fixed: true,
			                delay: 300
			            }
			         });
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
				<input type="hidden" name="page" value="${command.page}" />
				<input type="hidden" id="userId" name="userId" value="${command.id}" />
				<input type="hidden" id="isConnAllow" value="${command.contactionAllowed}" />
				<input type="hidden" id="allConnCnt" value="${command.rowCount}" />
				<input type="hidden" id="tabName" value="allConn" />
				<input type="hidden" id="pgCount" value="${command.pageCount}" />
				<input type="hidden" id="pgCountcommConn" value="${command.commonConnPageCount}" />
				<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
				
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
					<div class="nav-list" style="margin-bottom:6px;">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.id}">Blogs</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.id}">Connections (${command.rowCount})</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.id}">Gallery</a></li>
						</ul>
					</div>
					
					<c:if test="${not empty command.retMess}">
						<div class='paginatedList' style='height: unset;'>
							<p>${command.retMess}</p>
						</div>
					</c:if>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px; margin: 4px 0px 10px 0px;" id="inputForm">
						<div class="intHeader">
							<c:if test="${communityEraContext.userAuthenticated}">
								<c:if test="${communityEraContext.currentUser.id != command.user.id}">
									<span class='firLev'>
										<a onclick="showYourConnections(&#39;${command.user.id}&#39;);" href="javascript:void(0);" id="yourConnections" title="${command.user.firstName}'s connections" class="selected normalTip" >All Connections <span id="allConnectionCount">(${command.rowCount})</span></a>
									</span>
									<c:if test="${command.commonConnCount > 0}">
										<span class='firLev'>
											<a onclick="showCommonConns(&#39;${command.user.id}&#39;);" href="javascript:void(0);" id="commonConns" class="normalTip" title="${command.user.firstName}'s and yours common connections" >Common Connections <span id="allConnectionCount">(${command.commonConnCount})</span></a>
										</span>
									</c:if>
								</c:if>
								<c:if test="${command.user.id == communityEraContext.currentUser.id}">
									<span class='firLevAlt' title="${command.user.firstName}'s connections" class="normalTip">
										All Connections <span id="allConnectionCount">(${command.rowCount})</span>
									</span>
									<c:choose>
										<c:when test="${command.countReceived > 0}">
											<span class='secLev' id="">
												<a class="btnmain normalTip" href="${communityEraContext.contextUrl}/pers/pendingConnectionRequests.do">Received Requests <span class="redMark">${command.countReceived}</span></a>
											</span>   							  						
										</c:when>
										<c:otherwise>
											<c:if test="${command.countSent > 0}" >
												<span class='secLev' id="">
													<a class="btnmain normalTip" href="${communityEraContext.contextUrl}/pers/pendingConnectionRequests.do?type=sent">Sent Requests <span class="redMark">${command.countSent}</span></a>
												</span>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:if>
							<c:if test="${!communityEraContext.userAuthenticated}" >
								<span class='firLevAlt' title="${command.user.firstName}'s connections" class="normalTip" >
									All Connections <span id="allConnectionCount">(${command.rowCount})</span>
								</span>
							</c:if>
						</div>
						<div id="innerSection">
							<c:forEach items="${command.scrollerPage}" var="row">
								<c:if test="${row.oddRow}"> <c:set var="trclass" value='class="userList"' /></c:if>
								<c:if test="${row.evenRow}">	<c:set var="trclass" value='class="userListAlt"' /></c:if>
								<div ${trclass} id='connResult-${row.id}'>
									<div class='leftImg'>
										<c:choose>
											<c:when test="${row.photoPresent}">
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref' >
												<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${row.id}' width='120' height='120' />
											</a>
											</c:when>
											<c:otherwise>
												<img src='img/user_icon.png'  width='120' height='120' />
											</c:otherwise>
										</c:choose>
									</div>
									<div class='details'>
										<div class='heading'>
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref'>${row.firstName} ${row.lastName}</a>
										</div>
										<div class='person'>Connected since ${row.connectionDate}</div>
										<c:if test="${row.connectionCount  == 0}">
											<div class='person'>${row.connectionCount} Connection</a></div>
										</c:if>
										<c:if test="${row.connectionCount  == 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connection</a>
											</div>
										</c:if>
										<c:if test="${row.connectionCount  > 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connections</a>
											</div>
										</c:if>
										<c:if test="${row.communityCount  == 0}">
											<div class='person'>${row.communityCount} Community</a></div>
										</c:if>
										<c:if test="${row.communityCount  == 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${row.id}'>${row.communityCount} Community</a>
											</div>
										</c:if>
										<c:if test="${row.communityCount  > 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${row.id}'>${row.communityCount} Communities</a>
											</div>
										</c:if>
								 		<div id='connectionInfo-${row.id}' class='connectionInfo'>${row.connectionInfo}</div>
								 	</div>
								</div>
							</c:forEach>
						</div>
						<div id="waitMessage" style="display: none;">
							<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
						</div>
					</div>
				</div> 	
			</div> 	
			
			<div class="right-panel" style="margin-top: 0px;">
				<div class="inbox">
					<div class="eyebrow">
						<span onclick="return false" >People You May Know</span>
					</div>
					<div id="pymkList" style="padding: 4px;" ></div>
					<div id="waitPymkMessage" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
					<div class="view">
						<a href="${communityEraContext.contextUrl}/pers/pymk.do">View All</a>
					</div>
				</div>
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
				
				<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
			</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
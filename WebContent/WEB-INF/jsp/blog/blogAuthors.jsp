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
		<meta name="robots" content="noindex, follow" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="${command.cons.name}. ${command.cons.description}" />
		<meta name="keywords" content="Jhapak, blog, all-blogs, blog-list" />
		<title>Jhapak - Blog Authors</title>
		<meta name="author" content="Jhapak">
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.connectionInfo {
			    top: unset;
				bottom: -40px;
			    right: 20px;
			}

			#container .left-panel .nav-list ul li.selected {
			    width: 200px;
			}
			
			.intHeader {
				height: 40px;
				clear: both;
				text-decoration: none;
			}
			
			.intHeader form {
			    color: #008EFF;
			    font-size: 13px;
			    font-weight: normal;
			    padding: 8px;
			}
			
			.intHeader form label {
			    color: #707070;
			    float: left;
			    font-size: 12px;
			    font-weight: normal;
			    height: 20px;
			    line-height: 20px;
			    margin-right: 6px;
			}
			
			.intHeader form select {
			    border: 1px solid #D6D6D6;
			    background: none repeat scroll 0% 0% #F2F2F2;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 100px;
			    float: left;
			}
			
			.connectionInfo span.staticDropDown {
			    background-color: #F0EDED;
			    border: 1px solid #BBB;
			    border-radius: 2px;
			    color: #524D4D;
			    display: inline-block;
			    float: right;
			    font-weight: bold;
			    line-height: 1em;
			    margin-right: 5px;
			    padding: 5px 9px;
			    text-align: center;
			    text-shadow: 0px 1px rgba(255, 255, 255, 0.9);
			}
		</style>
		
		<script type="text/javascript">
		    var count = 2;
			function infinite_scrolling_allConn(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    		var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
			            	var currentOwner = document.getElementById('userId').value;
			            	$.ajax({
			                	url:"${communityEraContext.contextUrl}/pers/visitMyProfile.ajx?jPage="+count+"&filterOption="+$("#filterOption").val()+"&sortByOption="+$("#sortByOption").val(),
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
			   					dynamicDropDownQtip();
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
				infinite_scroll_debouncer(infinite_scrolling_allConn, 400);
		    });
		</script>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
				$(".qtip").hide();
				normalQtip();
				memberInfoQtip();
				dynamicDropDownQtip();
			});
		</script>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<input type="hidden" name="page" value="${command.page}" />
				<input type="hidden" id="userId" name="userId" value="${command.user.id}" />
				<input type="hidden" id="isConnAllow" value="" /> 
				<input type="hidden" id="allConnCnt" value="${command.rowCount}" />
				<input type="hidden" id="tabName" value="allConn" />
				<input type="hidden" id="pgCount" value="${command.pageCount}" />
				<%-- <input type="hidden" id="pgCountcommConn" value="${command.commonConnPageCount}" /> --%>
				<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
				
				<div class="commSection">
					<div class="abtMe">
						<div class="cvrImg">
							<c:if test="${command.user.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${command.user.id}" 
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
							<li><a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}">Profile</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li class="selected">
								<a href="${communityEraContext.contextUrl}/blog/manageBlogAuthors.do?bid=${command.cons.id}" class='dynaDropDown' 
									title='community/communityOptions.ajx?currId=3009&itemId=${command.user.id}'>Blog Authors (${command.rowCount}) <span class='ddimgWht'/></a>
							</li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<span class='firLevAlt' title="Blog authors list" class="normalTip" >
								Blog Authors <span id="allConnectionCount">(${command.rowCount})</span>
							</span>
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
											<c:if test="${command.cons.userId == row.id}">
												<div class='person'>Started at ${command.startedOn}</div>
											</c:if>
										</div>
										<div class='connectionInfo'><span class='staticDropDown'>Owner</span></div>
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
			</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
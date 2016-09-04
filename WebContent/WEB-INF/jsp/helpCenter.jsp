<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="robots" content="index,follow" />
		<meta name="author" content="Jhapak">
		<title>Jhapak - Help Center</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<link type="text/css" rel="stylesheet" href="css/help.css" />
		
		<script type="text/javascript" src="js/jquery.accordion.js"></script>
		<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
		
		<link type="text/css" rel="stylesheet" href="css/multiLevelMenu.css" />
		<script type="text/javascript" src="js/modernizr.custom.js"></script>
		<script type="text/javascript" src="js/jquery.dlmenu.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<noscript>
			<style>
				.st-accordion ul li{
					height:auto;
					margin-left: 30px;
				}
				     
				.st-accordion ul li > a span{
					visibility:hidden;
				}
			</style>
		</noscript>
		<style>
			.st-accordion ul li, .st-accordion ol li{
				margin-left: 30px;
				line-height: 20px;
			}
			
			header .section .rowBlock .form-part .search form {
				/* width: 510px; */
			} 
			
			.selectedMenu {
				background-color: #3A5795;
				color: #FFF;
				font-weight: bold;
				text-decoration: none;
			}
			
			.dl-menuwrapper li.selectedMenu a {
				color: #FFF;
				background: #3A5795 none repeat scroll 0% 0%;
				font-weight: bold;
				text-decoration: none;
			}
			
			.no-touch .dl-menuwrapper li.selectedMenu a:hover {
			    background: #3A5795 none repeat scroll 0% 0%;
			    text-decoration: none;
			}
			
			.st-content p {
				line-height: 24px;
			}
		</style>

		<script type="text/javascript">
			$(document).ready( function() {
				$( '#dl-menu' ).dlmenu({
					animationClasses : { classin : 'dl-animate-in-2', classout : 'dl-animate-out-2' }
				});

				$( '#trigerMenu' ).click();
			});

			function showHelpEntries(menuId){
				var x = document.getElementsByClassName("selectedMenu");
				var i;
				for (i = 0; i < x.length; i++) {
					x[i].classList.remove("selectedMenu");
				}
				$.ajax({url:"${communityEraContext.contextUrl}/faq/showMenuEntries.ajx?parentId="+menuId,success:function(result){
					var sName = "<div class='wrapper'><div id='st-accordion' class='st-accordion'><ul id=accordList' class='accordList' style='display: inline;'>";
					$.each(result.aData, function() {
						sName += "<li class='paginatedList'>";
						sName += "<a href='#' >"+this['question']+"<span class='st-arrow'>Open or Close</span></a>";
						sName += "<div class='st-content'>";
						sName += "<p class='st-cont'>"+this['answer']+"</p>";
						if(${communityEraContext.userSysAdmin}){
							sName += "<a href='${communityEraContext.contextUrl}/faq/helpEntryCreate.do?mode=e&id="+this['id']+"'>Edit entry</a>";
						}
						sName += "</div></li>";
					});
					sName += "</ul></div></div>";
		    	    $("#mainSection").html(sName);
		    	    $('#st-accordion').accordion( {
						oneOpenedItem : true
					});
		    	    $("#"+menuId).addClass("selectedMenu");
		    	  }});
			}
		</script>
	</head>
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="help">
				<div class="header" style="height: 30px;">
					<a href="${communityEraContext.contextUrl}/help.do">Help Center</a>
				</div>
				<div class="demo-2" style="width: 24%; float: left">	
					<div id="dl-menu" class="dl-menuwrapper">
						<button class="dl-trigger" id="trigerMenu" style="display: none;">Open Menu</button>
						<ul class="dl-menu">
							<c:forEach items="${command.topHeaders}" var="row">		
								<li id="${row.id}">
									<c:choose>
										<c:when test="${row.subHeaderCnt > 0}">
											<a href="#">${row.title}</a>
											<ul class="dl-submenu">
												<c:forEach items="${row.headers}" var="row2">
													<li id="${row2.id}">
														<c:choose>
															<c:when test="${row2.subHeaderCnt > 0}">
																<a href="#">${row2.title}</a>
																<ul class="dl-submenu">
																	<c:forEach items="${row2.headers}" var="row3">
																		<li id="${row3.id}">
																			<c:choose>
																				<c:when test="${row3.subHeaderCnt > 0}">
																					<a href="#">${row3.title}</a>
																				</c:when>
																				<c:otherwise>
																					<a href="javascript:void(0);" onclick="showHelpEntries(${row3.id})">${row3.title}</a>
																				</c:otherwise>
																			</c:choose>
																			<ul class="dl-submenu">
																				<c:forEach items="${row3.headers}" var="row4">
																					<li>
																						<a >${row4.title}</a>
																					</li>
																				</c:forEach>
																			</ul>
																		</li>
																	</c:forEach>
																</ul>
															</c:when>
															<c:otherwise>
																<a href="javascript:void(0);" onclick="showHelpEntries(${row2.id})">${row2.title}</a>
															</c:otherwise>
														</c:choose>
													</li>
												</c:forEach>
											</ul>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0);" onclick="showHelpEntries(${row.id})">${row.title}</a>
										</c:otherwise>
									</c:choose>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div id="mainSection" class="mainSection" style="padding: 20px 20px 20px 0px; margin-right: 28px; width: 70%; float: right;">
				</div>
			</div>
		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
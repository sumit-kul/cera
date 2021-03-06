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
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/table.css" />
		<title>User Sessions</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			#container .inputForm .innerBlock form select {
				width: 90%;
			}
			
			#container .inputForm .innerBlock {
			    margin: 0px auto;
			    display: inline-block;
			    background-color: #FFF;
			    width: 100%;
			    padding: 0px;
			}
		</style>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function () {
			$('.normalTip').qtip({ 
			    content: {
			        attr: 'title'
			    },
				style: {
			        classes: 'qtip-tipsy'
			    }
			});

			if(${command.pageCount} > 0){
				$("#pagination").jPaginator({ 
					nbPages:${command.pageCount},
					marginPx:5,
					length:6, 
					overBtnLeft:'#over_backward', 
					overBtnRight:'#over_forward', 
					maxBtnLeft:'#max_backward', 
					maxBtnRight:'#max_forward', 
					onPageClicked: function(a,num) { 
					$.ajax({url:"${communityEraContext.contextUrl}/common/showUserSessions.ajx?jPage="+num,dataType: "json",success:function(result){ 
						var sName = "<table class='type1' style='width: 100%;'><tr style='width: 100%;'><th >Ip</th><th>User</th><th width='30%'>Referer</th><th>Device</th>";
						sName += "<th>Brand</th><th>Browser</th><th width='30%'>Location</th><th>S</th><th>Time Spent</th></tr>";
						 $.each(result.aData, function() {
							 var rowId = this['id'];
							 var trclass = 'trclass';
							 if(this['evenRow']){
								 trclass = 'trclassAlt';
							 }
							 sName += "<tr class='"+trclass+"'>";
							 sName += "<td class='nowrap' ><a href='${communityEraContext.contextUrl}/common/showInsight.do?sessionId="+this['id']+"' target='_blank'>"+this['ip']+"</a></td>";
							 sName += "<td class='nowrap' >"+this['userName']+"</td>";
							 sName += "<td class='' >"+this['referer']+"</td>";
							 sName += "<td class='nowrap' >"+this['deviceType']+"</td>";
							 sName += "<td class='nowrap' >"+this['deviceBrand']+" : "+this['deviceOS']+"</td>";
							 sName += "<td class='nowrap' >"+this['browser']+" : "+this['version']+"</td>";
							 sName += "<td class='' >"+this['country']+" - "+this['city']+" ("+this['region']+")</td>";
							 sName += "<td class='nowrap' >"+this['screenCounts']+"</td>";
							 sName += "<td class='nowrap' >"+this['stayTime']+" ("+this['created']+")</td>";
							 
								}
							);
						 sName += "</table>";
						 $("#page").html(sName);
						// Hide message
				        $("#waitMessage").hide();
				        toggleOnLoad();
					    },
					 	// What to do before starting
				         beforeSend: function () {
				             $("#waitMessage").show();
				         } 
				    });
					} 
				});
			}
		});
		</script>
		
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" style="width: 100%; max-width: 100%;">
			<div class="left-panel" style="width: 100%;">
				<div class="commSection">
					<div class="inputForm" style="display: inline-block; width: 100%;  margin-bottom: 10px;">
						<div class="intHeader" style="">
							<span class="settingHdrSelected" style="padding: 12px;">
								${command.rowCount} - User Sessions [Total IP : ${command.totalIP}]
							</span>
						</div>
				 		<div class="innerBlock" style="">
							<form:form action="${communityEraContext.requestUrl}" method="post" commandName="command" name="newEventForm">	
								<form:errors path="*"  />
								<div class="commSection">
									<c:if test="${command.rowCount > 0}">
										<div id="page"></div>
										<div id="waitMessage" style="display: none;">
											<div class="cssload-square" >
												<div class="cssload-square-part cssload-square-green" ></div>
												<div class="cssload-square-part cssload-square-pink" ></div>
												<div class="cssload-square-blend" ></div>
											</div>
										</div>
										<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
									</c:if>
								</div>
							</form:form> 
						</div>
					</div>
				</div>
			</div> 
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
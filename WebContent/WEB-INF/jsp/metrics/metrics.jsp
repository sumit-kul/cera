<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

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
	<link rel="icon" type="image/png" href="img/link_Image.png"/>

	<%-- Emitting a base tag gives IE6 severe problems with dojo charting --%>
	<%-- <base href="${communityEraContext.contextUrl}"/> --%>

	<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/new2.css" />
	<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/section.css" />
	<!--[if lt IE 7]><link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/ltie7.css" /><![endif]-->
	<!--[if lt IE 6]><link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/ltie6.css" /><![endif]-->
	<link type="text/css" media="print" rel="stylesheet" href="${communityEraContext.contextUrl}css/print.css" />
	<!--[if IE]>
		<style type="text/css" media="print">@import url("${communityEraContext.contextUrl}css/print_IE.css");</style>
		<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/ie.css" />
	<![endif]-->

	
		<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}css/metrics.css" />

     <script src="${communityEraContext.contextUrl}dojo/dojo.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
	 <script type="text/javascript">
	 
		dojo.require("dojo.collections.Store");
		dojo.require("dojo.charting.Chart");
		dojo.require('dojo.json');

			djConfig={ 
				isDebug:true 
			}

			dojo.addOnLoad(function(){

				var json = eval( '(${command.userCountSeries})' );
				
				var store = new dojo.collections.Store();
				store.setData(json.data);
	
	
				//	define the chart.
				var s1 = new dojo.charting.Series({
					dataSource:store,
					bindings:{ x:"x", y:"y" },
					label:"User growth"
				});
				var s2 = new dojo.charting.Series({
					dataSource:store,
					bindings:{ x:"y", y:"size" },
					label:"Series 2"
				});
				
				var xA = new dojo.charting.Axis();
				xA.range={upper:json.xAxis.max, lower:json.xAxis.min}; 
				xA.origin="max";
				xA.showTicks = true;
				xA.labels = json.xAxis.labels;

				var yA = new dojo.charting.Axis();
				yA.range={upper:json.yAxis.max, lower:json.yAxis.min};
				yA.showLines = true;
				yA.showTicks = true;
				yA.labels = json.yAxis.labels;
				
				<%-- Title for the chart Y axis --%>
				yA.label = "Registered Users"
	
				var pa = new dojo.charting.PlotArea();
				pa.size={width:368,height:160};
				pa.padding={top:20, right:20, bottom:30, left:50 };

	
				s1.color = pa.nextColor();
				s2.color = pa.nextColor();
				
				var plot = new dojo.charting.Plot(xA, yA);
							
				plot.addSeries({ data:s1, plotter:dojo.charting.Plotters.CurvedLine });
				pa.plots.push(plot);

				var node = pa.initialize();
				node.style.position = "absolute";
				node.style.top = "0px";
				node.style.left = "0px";
				var frame = dojo.byId("userGrowthChart"); 
				frame.appendChild(node);
				pa.render();
				//pa.render(null, function(node, obj){ dojo.event.connect(node, .........)  });

/*				
				var chart = new dojo.charting.Chart(null, "User Growth", "This is the description");
				chart.addPlotArea({ x:0,y:0, plotArea:pa });
				chart.node = dojo.byId("userGrowthChart");
				chart.render();
*/
				
			});
		</script>
		<style>
			#userGrowthChart {
			    position: relative;
				margin:6px;
				width:368px;
				height:160px;
				background-color:#dedeed;
				border:1px solid #999;
			}
		</style>

 </script>
	 
	 
	<title>Site Metrics</title>
</head>

<body id="homepage">

		<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
		<%@ include file="/WEB-INF/jspf/topnav.jspf" %>

	<div class="padding">

		<div id="main">
		
				<p id="breadcrumb-orange">
						
				You are here: 	<a href="${communityEraContext.contextUrl}/admin/actions.do">System Administration</a> &gt; Registered Users

				</p>
		
			<div id="heading">
			<h2>Site Metrics ${command.groupRegion} - ${command.todayAsString}</h2> 
			</div>
			
			<c:if test="${command.groupId==0}">
			
			<div style="width:50%; float:left">
			Download user counts per authority: <a href="${communityEraContext.contextUrl}/metrics/authority-user-counts.do?format=csv">XLS</a>&nbsp;<a href="${communityEraContext.contextUrl}/metrics/authority-user-counts.do?format=xml">XML</a><br/>
			</div>
			
			<div style="width:49%; float:right">
			Download user counts per community: <a href="${communityEraContext.contextUrl}/metrics/community-authority-user-counts.do?format=csv">XLS</a>&nbsp;<a href="${communityEraContext.contextUrl}/metrics/community-authority-user-counts.do?format=xml">XML</a><br/>
			</div>
			
			<br /><br />
			</c:if>
			
<%-- Data boxes --%>

			<div class="bubblerow">
			
				<div class="metric-box metric-box-left" style="height:200px">
					<h2>Communities and members</h2>
					<div>
					<table>
						<tr ><td>Total communities</td><td align="right">${command.communityCount}</td></tr>
	
						<c:if test="${command.groupId==0}">
						   <tr ><td>Total registered users</td><td align="right">${command.userCount}</td></tr>
						</c:if>						

						    <tr><td>Total members</td><td align="right">${command.memberCount}</td></tr>

						
						<tr><td>Total contributors</td><td align="right">${command.contributorCount}</td></tr>
						
						<tr><td>Members per community</td>
								<td align="right">
								<fmt:formatNumber maxFractionDigits="1">${command.membershipCount/command.communityCount}</fmt:formatNumber>
								</td>
						</tr>
					</table>
					</div>
				</div>

			<div class="metric-box metric-box-right">
				<div>
					<h2 class="bubbleicon-registerprofile">${command.userGrowthChartTitle}</h2>
					<div id="userGrowthChart"></div>
				</div>
			</div>

			</div>
			
			<div class="bubblerow">

				<div class="metric-box metric-box-left">
					<h2>Forums</h2>
					<div>
					<table>
						<tr><td>Total topics</td><td  align="right">${command.topicCount}</td></tr>
						<tr><td>Threads with responses</td><td align="right">${command.threadCount}</td></tr>
						<tr><td>Responses per thread</td><td align="right"><fmt:formatNumber maxFractionDigits="1">${command.responseCount/command.threadCount}</fmt:formatNumber></td></tr>
						<tr><td>Participating users</td><td align="right">${command.participantCount}</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>
				
				
			<div class="metric-box metric-box-right">
					<h2>Events</h2>
					<div>
					<table>
						<tr ><td>Total events posted</td><td align="right">${command.eventCount}</td></tr>
						<tr ><td>Events per community</td><td align="right"><fmt:formatNumber maxFractionDigits="1">${command.eventCount/command.communityCount}</fmt:formatNumber></td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>

				
				
			</div>

			<div class="bubblerow">
				<div class="metric-box metric-box-left">
					<h2>Wikis</h2>
					<div>
					<table>
						<tr ><td>Total articles</td><td align="right">${command.wikiEntryCount}</td></tr>
						<tr><td>Total edits</td><td align="right">${command.wikiEditCount}</td></tr>
						<tr><td>Articles per community</td><td align="right"><fmt:formatNumber maxFractionDigits="1">${command.wikiEntryCount/command.communityCount}</fmt:formatNumber></td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>

				<div class="metric-box metric-box-right">
					<h2>Documents</h2>
					<div>
					<table>
						<tr ><td>Total documents</td><td align="right">${command.documentCount}</td></tr>
						<tr><td>Total comments</td><td align="right">${command.documentCommentCount}</td></tr>
						<tr><td>Documents per community</td><td align="right"><fmt:formatNumber maxFractionDigits="1">${command.documentCount/command.communityCount}</fmt:formatNumber></td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>
			</div>
			
				<c:if test="${command.groupId==0}">
				

			<div class="bubblerow">
				<div class="metric-box metric-box-left">
					<h2>Messages</h2>
					<div>
					<table>
						<tr ><td>Total messages</td><td align="right">${command.messageCount}</td></tr>
						<tr><td>Users sending messages</td><td align="right">${command.messageSenderCount}</td></tr>
						<tr><td>Users receiving messages</td><td align="right">${command.messageReceiverCount}</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>

			<div class="metric-box metric-box-right">
					<h2>Blogs</h2>
					<div>
					<table>
						<tr ><td>Total blogs</td><td align="right">${command.blogCount}</td></tr>
						<tr><td>Total posts</td><td align="right">${command.blogEntryCount}</td></tr>
						<tr><td>Total comments</td><td align="right">${command.blogCommentCount}</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>

				</div>
</c:if>

		

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
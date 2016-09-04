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
	<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/new2.css" />
	<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/section.css" />
	<!--[if lt IE 7]><link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/ltie7.css" /><![endif]-->
	<!--[if lt IE 6]><link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/ltie6.css" /><![endif]-->
	<link type="text/css" media="print" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/print.css" />
	<!--[if IE]>
		<style type="text/css" media="print">@import url("${communityEraContext.contextUrl}/cssTest/print_IE.css");</style>
		<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/ie.css" />
	<![endif]-->


	<link type="text/css" media="all" rel="stylesheet" href="${communityEraContext.contextUrl}/cssTest/metrics.css" />
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
				yA.label = "Members"
	
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
			    position: relative; margin:6px; 	width:368px; 	height:160px; 	background-color:#dedeed; border:1px solid #999;
			}
		</style>
	 
	<title>Jhapak | Community Metrics</title>
</head>

<body>

<div id="container">
	<div id="from_reg" class="left">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
		<a href="${communityEraContext.contextUrl}/welcome.do"><img src="img/coc_img/images/coc_logo.gif" width="204"></a>
		</td>
	</tr>
</table>
</div> <!-- /#from_reg -->

<div id="right_shadow">
	
<div id="bottom_curve_right">		
<div id="bottom_curve_left">
			<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
	<div class="padding">
		
		<div id="main">
	
						<p id="breadcrumb-orange">
						<%@ include file="/WEB-INF/jspf/community-breadcrumb-start.jspf" %>
					Metrics
						</p>

			<c:if test="${communityEraContext.userSysAdmin}">		
			<div style="width:49%; float:left">
			<p>
			Download user counts per authority: <a href="${communityEraContext.contextUrl}/metrics/authority-user-counts.do?format=csv&amp;commId=${command.id}" title="System Administrator only tool">XLS</a><br/>
			</p>
			</div>

			</c:if>
			<div id="heading">
			<h2>${command.community.name} - Metrics ${command.todayAsString}</h2>
			</div>
			
<%-- Data boxes --%>

			<div class="bubblerow">
			
				<div class="metric-box metric-box-left" style="height:200px">
					<h2>Membership</h2>
					<div>
					<table summary="Membership">
						<tr><td>Total members</td><td align="right">${command.userCount}</td><td width="10%"></tr>
						<tr><td>Total contributors</td><td align="right">${command.contributorCount}</td><td width="10%"></tr>
					</table>
					</div>
				</div>

			<div class="metric-box metric-box-right">
				<div>
					<h2 class="bubbleicon-registerprofile">Membership Growth</h2>
					<div id="userGrowthChart"></div>
				</div>
			</div>

			</div>
			
			<div class="bubblerow">

				<div class="metric-box metric-box-left">
					<h2>Forum</h2>
					<div>
					<table summary="Forum Metrics">
						<tr><td>Total topics</td><td align="right">${command.topicCount}</td><td width="10%"></tr>
						<tr><td>Threads with responses</td><td align="right">${command.threadCount}</td><td width="10%"></tr>
						<tr><td>Responses per thread</td><td align="right">
						<fmt:formatNumber maxFractionDigits="1">${command.responsesPerThread}</fmt:formatNumber></td><td width="10%"></tr>
						<tr><td>Participating users</td><td align="right">${command.participantCount}</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td><td width="10%"></tr>
					</table>
					</div>
				</div>

				<div class="metric-box metric-box-right">
					<h2>Events</h2>
					<div>
					<table summary="Events Metrics">
						<tr><td>Total events posted</td><td align="right">${command.eventCount}</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					</table>
					</div>
				</div>
				
			</div>
				
		

			<div class="bubblerow">
				<div class="metric-box metric-box-left">
					<h2>Wiki</h2>
					<div>
					<table summary="Wiki Metrics">
						<tr ><td>Total articles</td><td align="right">${command.wikiEntryCount}</td><td width="10%"></tr>
						<tr><td>Total edits</td><td align="right">${command.wikiEditCount}</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td><td width="10%"></tr>
					</table>
					</div>
				</div>

				<div class="metric-box metric-box-right">
					<h2>Library</h2>
					<div>
					<table summary="Library Metrics">
						<tr ><td>Total entries</td><td align="right">${command.documentCount}</td><td width="10%"></tr>
						<tr><td>Total comments</td><td align="right">${command.documentCommentCount}</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td><td width="10%"></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td><td width="10%"></tr>
					</table>
					</div>
				</div>
			</div>

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</div> 
	<%@ include file="/WEB-INF/jspf/close-container.jspf" %>
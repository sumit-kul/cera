<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %>   
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %>   
 
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak is a hub of online communities. It enables like-minded people to explore, learn and share. It provides you tools to communicate like blog, forums, wikis etc." />
		<meta name="keywords" content="Jhapak community, communities, discover, share, explore, learn, like-minded people, forum, wiki, blog, post, events, howto, knowledge, latest communities" />
		<title>Jhapak - Discover and share, starts with communities</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<meta name="robots" content="index, follow">
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<link rel="canonical" href="${communityEraContext.contextUrl}/welcomeToCommunities.do" />
		
		<script type="application/ld+json">
			{
   				"@context": "http://schema.org",
   				"@type": "WebSite",
				"name" : "Jhapak",
				"alternateName" : "Jhapak",
   				"url": "//www.jhapak.com",
   				"potentialAction": {
     			"@type": "SearchAction",
     			"target": "http://jhapak.com/search/searchComplete.do?queryText={search_term_string}",
     			"query-input": "required name=search_term_string"
   						}
			}
		</script>
		
		<!-- Twitter Card data -->
		<meta name="twitter:card" content="summary">
		<meta name="twitter:site" content="@EraCommunity">
		<meta name="twitter:title" content="Jhapak - Discover and share, starts with communities">
		<meta name="twitter:description" content="Share, explore and learn information and ideas, a complete social networking website. connecting people and build communities to exchange and discover knowledge using blog, forum and wiki.">
		<meta name="twitter:url" content="http://jhapak.com">
		<meta name="twitter:image:src" content="http://jhapak.com/img/community_Image.png">
		
		<meta property="og:url" content="http://jhapak.com" />
		<meta property="og:site_name" content="Jhapak" />
		<meta property="og:type" content="website" />
		<meta property="og:title" content="jhapak.com" />
		<meta property="og:description" content="Share, explore and learn information and ideas, a complete social networking website. connecting people and build communities to exchange and discover knowledge using blog, forum and wiki." />
		<meta property="og:image" content="http://jhapak.com/img/link_Image.png" />
		<meta property="fb:admins" content="100001187666357" />
		
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-81620530-1', 'auto');
		  ga('send', 'pageview');
		
		</script>
		
		
		<link rel="stylesheet" type="text/css" href="css/commScreen.css" />
	<link rel="stylesheet" type="text/css" href="css/communityStyle.css" />
	
	<link type="text/css" rel="stylesheet" href="css/qtip/jquery.qtip.min.css" />
	<link type="text/css" rel="stylesheet" href="css/qtip/qtip-extra.css" />
	<link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	  	  <style type="text/css">.gradient{filter:none;}</style>      
	<![endif]-->
	
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bowser.min.js"></script>
	<script src="js/mobile-detect.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-dialog.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap-dialog.min.css" />
	
	<script type="text/javascript" src="js/qtip/jquery.qtip.min.js"></script>
	<script type="text/javascript" src="js/qtip/imagesloaded.pkg.min.js"></script>
	
	<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 	<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 	<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
	
	<link type="text/css" rel="stylesheet" href="css/bootstrap-social.css" />
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link href="css/docs.css" rel="stylesheet" >
  	<script src="https://apis.google.com/js/api:client.js"></script>
  	
  	  	<!-- Facebook Pixel Code -->
	<script>
	!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?
	n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;
	n.push=n;n.loaded=!0;n.version='2.0';n.queue=[];t=b.createElement(e);t.async=!0;
	t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,
	document,'script','https://connect.facebook.net/en_US/fbevents.js');
	
	fbq('init', '678534068982510');
	fbq('track', "PageView");</script>
	<noscript><img height="1" width="1" style="display:none"
	src="https://www.facebook.com/tr?id=678534068982510&ev=PageView&noscript=1"
	/></noscript>
	<!-- End Facebook Pixel Code -->
	
	
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<script type="text/javascript" src="js/jquery.flexslider.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/flexslider.css" />
		
		<%--  <script src="http://connect.facebook.net/en_US/all.js"></script> --%>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		     
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

		<!-- Google Tag Manager -->
		<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-56267Z"
		height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
		<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-56267Z');</script>
		<!-- End Google Tag Manager -->
				
		<!-- Place this tag in your head or just before your close body tag. -->
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<script type="text/javascript">

		function goToSearchItem(url)
		{
			window.location.href = url;
		}

		function searchOnTheFly()
			{
				var searchString = document.getElementById('queryText');
				$('.qtext').each(function() {
			         $(this).qtip({
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: '${communityEraContext.contextUrl}/search/searchOnTheFly.ajx?queryText='+searchString.value // Use href attribute as URL
			                    })
			                    .then(function(content) {
			                    	var searchList = "<div class='scroll-pane-search horizontal-only'><ul>";
			                    	$.each(content.aData, function() {
			                    		var varItemType = "";
			                    		if(this['entityTypeName'] != "PrivateCommunity"){
				   						 if(this['iscommunity'] == "true"){
					   						 
				   							if(this['entityTypeName'] == "PublicCommunity"){
				   								varItemType = "Public Community:";
				   							}
				   							if(this['entityTypeName'] == "ProtectedCommunity"){
				   								varItemType = "Protected Community:";
				   							}
				   							
				   							searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
				                    		if(this['logoPresent'] == "true"){
				                    			searchList += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['communityId']+"' width='36' height='36' />&nbsp;&nbsp;";
				                    		}else{
				                    			searchList += "<img src='img/community_Image.png'  width='36' height='36'/>&nbsp;&nbsp;";
				                    		}
				                    		searchList += "<p><span class='header'>"+this['title']+"</span><br />";
				                    		searchList += "<span class='subHeader'>"+varItemType+"</span>"+this['memberCount']+" members <i class='a-icon-text-separator'></i>Created by "+this['creatorFullName']+" on "+this['dateCommunityCreation']+"</p></li>";
										 } else {
											 if(this['isUser'] == "false"){
												 if (this['entityTypeName'] == "BlogEntry"){
													 varItemType = "Blog Entry:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 if (this['entityTypeName'] == "Document"){
													 varItemType = "File:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 if (this['entityTypeName'] == "WikiEntry"){
													 varItemType = "Wiki Entry:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 if (this['entityTypeName'] == "Event"){
													 varItemType = "Event:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 if (this['entityTypeName'] == "ForumTopic"){
													 varItemType = "Forum Topic:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 if (this['entityTypeName'] == "ForumResponse"){
													 varItemType = "Forum Response:";
													 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 }
												 
												 if(this['isPhotoPresent'] == "true"){
													 searchList += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"'  width='36' height='36' />&nbsp;&nbsp;";
												 } else {
													 searchList += "<img src='img/user_icon.png' id='photoImg' width='36' height='36' />&nbsp;&nbsp;";
												 }
												 searchList += "<p><span class='header'>"+this['title']+"</span><br />";
												 searchList += "<span class='subHeader'>"+varItemType+"</span>By "+this['userName'];
												 
												 searchList += "</p></li>";
											 } else {
												 varItemType = "People:";
												 searchList += "<li class='srchRslt' onclick=\"goToSearchItem(&#39;"+this['link']+"&#39;)\" >";
												 if(this['isPhotoPresent'] == "true"){
													 searchList += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"'  width='36' height='36' />&nbsp;&nbsp;";
												 } else {
													 searchList += "<img src='img/user_icon.png' id='photoImg'  width='36' height='36' />&nbsp;&nbsp;";
												 }
												 searchList += "<p><span class='header'>"+this['userName']+"</span><br />";
												 searchList += "<span class='subHeader'>"+varItemType+"</span>"+this['connectionCount']+" connections</p></li>";
											 }
										 }
			                    		}
			                    	});
			                    	searchList += "</ul></div>";
			                    	
			                        api.set('content.text', searchList);
			                        $('.scroll-pane-search').jScrollPane(
				    		        		{
				    		        			verticalDragMinHeight: 20,
				    		        			verticalDragMaxHeight: 200,
				    		        			autoReinitialise: true
				    		        			}
				    	    		        );
			                    }, function(xhr, status, error) {
			                        api.set('content.text', 'Finding results...');
			                    });
			                    return ''; // Set some initial text
			                }
			            },
			            show: {
			            	event: 'keyup mouseenter',
			                target: $('.qtext')
			            },
			            position: {
			                my: 'top left',  // Position my top left...
			                at: 'bottom left', // at the bottom left of...
			                target: $('.qtext') // my target
			            },
			            hide: {
			                event: 'unfocus'
			            },
						style: {
					        classes: 'qtip-bootstrap myCustomSearchClass'
					    }
			         });
			     });
			}
	</script>

		<style type="text/css">
			
			header .section .rowBlock .linkBuilder ul li {
			    background: none;
			}
			
			header .section .rowBlock .linkBuilder ul li:last-child {
			    background: none;
			    padding: 28px 0px 0px 8px;
			}
			
			header .section .rowBlock .linkBuilder ul li:first-child {
				padding: 28px 8px 0px 0px;
			}
			
			header .section .rowBlock .linkBuilder ul li a {
		    	font-size: 18px;
		    	box-sizing: border-box;
				z-index: 10;
				text-shadow: 0px 1px 2px rgba(0, 0, 0, 0.6);
				font-family: Circular,"Helvetica Neue",Helvetica,Arial,sans-serif;
		    }
			
			.a-icon-text-sep-Cloud {
			    display: inline-block;
			    width: 1px;
			    background-color: #FFF;
			    line-height: 0;
			    height: 24px;
			    vertical-align: middle;
			}
			
			.stretchHeight {
				margin: 0 auto;
				position: relative;
				box-sizing: border-box;
				padding-left: 0;
				padding-right: 0;
				z-index: 9;
				bottom: 70px;
			}
			
			header .section .rowBlock {
    			max-width: 1200px;
    		}
			
			.searchBarContainer {
				max-width: 580px;
				margin: 0.5em auto;
				margin-top: 2em;
				width: 90%;
			}
			
			.sbContainerInput {
				position: relative;
				border: none;
			}
			
			.faSearchicon {
				color: #828a8b;
				margin-left: 0.1rem;
				padding: 10px;
				position: absolute;
				font-size: 1.5em;
			}
			
			.sbContainerInput .sbInput {
			    padding-left: 2.5em;
			    z-index: 50;
			    border: white;
			    width: 90%;
			    vertical-align: middle;
				min-height: 40px;
				font-weight: normal;
				color: #828a8b;
				font-size: 1.2em;
				border-radius: 4px;
				-webkit-transition-property: border-color, color, box-shadow;
				transition-property: border-color, color, box-shadow;
				-webkit-transition-duration: 0.15s;
				transition-duration: 0.15s;
				max-width: 100%;
				outline: none;
			}
			
			input[type="text"]:hover {
				border: none;
				box-shadow: unset;
			}
			
			input[type="text"]:focus {
				color: #323a3b;
				outline: none;
				border: none;
				box-shadow: unset;
			}
			
			.linkBuilderItem {
				width: 20%;
				float: left;
				padding: .5em 0;
				text-align: center;
			}
			
			.linkBuilderItem a {
				font-weight: 600;
				font-size: 1.25rem;
				color: white;
				text-align: center;
				text-shadow: 1px 1px 0 rgba(0,0,0,.54);
				text-rendering: optimizelegibility;
			}

			.sliderTop {
				width: 100%;
				background: #FFF none repeat scroll 0% 0%;
				box-shadow: 5px 5px 6px 0px rgba(0, 0, 0, 0.22);
			}
			
			.slider {
				width: 100%;
				background: #FFF none repeat scroll 0% 0%;
			}
			
			.flexslider .slides .groups .picture {
				height: 350px;
				width: 100%;
				overflow: hidden;
			}
			
			.flexslider .slides .groups .picture img {
			    display: inline-block;
			    max-width: 900px;
				max-height: 300px;
			    position: relative;
			    top: 0;
			    bottom: 0;
			    left: 0;
			    right: 0;
			    margin: auto;
			}
			
			.mainflexs {
			    margin: 0px 0px 10px;
			    height: 260px;
		    }
		    
			.bannerShort .bnrImg {
				background-size: cover;
				background-position: top center !important;
				background-repeat: no-repeat !important;
				image-rendering: optimizequality;
				position: relative;
				height: 300px;
			}
			
			.bannerShort .bnrStImg {
				background-size: cover;
				background-position: top center !important;
				background-repeat: no-repeat !important;
				image-rendering: optimizequality;
				position: relative;
				height: 300px;
				cursor: pointer;
			}
			
			.bannerShort .bnrEvImg {
				background-size: cover;
				background-position: top center !important;
				background-repeat: no-repeat !important;
				image-rendering: optimizequality;
				position: relative;
				height: 300px;
			}
			
			.bannerTitle {
				font-size: 20px; 
				line-height: 1.1; 
				margin-bottom: 25px; 
				margin-top: 25px;
			}
			
			.bnrImg {
				height:600px;
			}
			
			.topBnrImg {
				 -moz-background-size: cover;
				-webkit-background-size: cover;
				background-size: cover;
				/*background-position: top center !important; */
				background-repeat: no-repeat !important;
				image-rendering: optimizequality;
				position: relative;
			}
			
			.bnrImg .otrCover {
				width: 100%;
				display: table;
				text-align: center;
				position: relative;
				height: 300px;
				top: 200px;
			}
			
			.innerGroups {
			    float: left;
			    background-color: black;
			    opacity: 0.6;
			    color: #fff;
				width: 100%;
				padding: 3px;
				position: relative;
				top: 0px;
				left: 0px;
				overflow: hidden;
				text-overflow: ellipsis;
				word-wrap: break-word;
				min-height: 30px;
			}
			
			.innerGroups a {
				color: #fff;
				text-decoration: none;
				cursor: pointer;
				overflow: hidden;
				text-overflow: ellipsis;
				word-wrap: break-word;
			    vertical-align: middle;
			    vertical-align: -moz-middle-with-baseline;
			}
			
			#container {
				color: #243F52;
			}
			
			#container .community {
			    clear: both;
			    overflow: hidden;
			    width: 100%;
			    display: inline-block;
			}
			
			.carousel_caption2 {
				position: relative;
			}
			
			.outlined {
			    position: relative;
			    display: inline-block;
			    line-height: 0;
			    padding: 10px 22px;
			    color: #fff;
			    margin-top: 20px;
			}
			
			.coBranded {
			    background: url(${communityEraContext.contextUrl}/img/darkBack.png);
			    margin: 0;
			    padding: 18px 0px;
			    width: 100%;
			    min-width: 0px;
			}
			
			.floatLeft {
			    display: inline;
			    float: left;
			}
			
			.carousel_caption2 .infotext {
				margin-bottom: 25px;
				stroke: transparent;
				fill: #fff;
				color: #fff;
				opacity: 1;
				font-size: 18px;
				font-weight: 400;
				line-height: 1.1;
				margin-left: 180px;
				margin-right: 180px;
				text-shadow: 1px 1px 0 rgba(0,0,0,.54);
				text-rendering: optimizelegibility;
			}
			
			.sliderOuter {
				width: 100%; 
				display: inline-flex; 
				margin-top: -88px;
				height: 600px;
			}
					
			.carousel_caption2 {
				box-sizing: border-box;
				z-index: 10;
				text-shadow: 0px 1px 2px rgba(0, 0, 0, 0.6);
				font-family: Circular,"Helvetica Neue",Helvetica,Arial,sans-serif;
				font-size: 14px;
				line-height: 1.43;
			}
			
			.carousel_caption2 p {
			    color: #000;
			    margin: 0px;
			}
			
			#container .wycdTop {
				width: 100%;
				float: left;
				padding: 0 0 10px 0;
			}
			
			#container .wycdTop h1 {
				margin: 0 0 10px 0;
				font-size: 38px;
				color: #7A7A7A;
				font-family: 'eagle_bookregular', Helvetica, Arial;
				line-height: 48px;
			}
			
			#container .wycdSection {
				font-family: 'eagle_bookregular', Helvetica, Arial;
				width: 66%;
				float: left;
				background: #FFF none repeat scroll 0% 0%;
				box-shadow: 5px 5px 6px 0px rgba(0, 0, 0, 0.22);
				display: inline-block;
				overflow: hidden;
				margin: 30px 10px;
			}
			
			#container .wycdSection .bannerOuter {
				margin-bottom: 20px;
				float: left;
				width: 40%;
			}
			
			#container .wycdCommSection {
				font-family: 'eagle_bookregular', Helvetica, Arial;
				width: 100%;
				background: #FFF none repeat scroll 0% 0%;
				box-shadow: 5px 5px 6px 0px rgba(0, 0, 0, 0.22);
				display: inline-block;
				overflow: hidden;
				margin-top: 30px;
				margin-bottom: 30px;
			}
			
			.btnBanner {
				text-decoration: none;
				width: 90%;
				display: inline-block;
				white-space: normal;
				border-color: #546E86;
				border-bottom-color: #374757;
				background-color: #546E86;
				border-width: 0px 0px 2px 0px;
				border-style: solid;
				color: #fff;
				padding: 10px;
				font-size: 16px;
				margin-top: 16px;
				margin-bottom: 8px;
				border-radius: 2px;
				webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				text-align: center;
				vertical-align: middle;
				font-weight: bold;
				cursor: pointer;
			}
			
			.btnBanner:hover, .btnBanner:focus {
				border-color: #009aab;
				border-bottom-color: #006671;
				background-color: #64829e;
				color: #fff;
			}
			
			#flexslider3 .slides .groups .picture {
			    max-height: 100%;
			    max-width: 100%;
			}
			
			#flexslider3 .slides .groups .picture img 
			{
				display: inline-block;
				width: 500px;
				max-height: unset;
				position: relative;
				top: -20%;
			}
			
			#container .slider .paginatedList {
    			border-top: 1px solid #999;
				box-sizing: border-box;
				background-color: #FFF;
				vertical-align: top;
				clear: both;
				overflow: hidden;
				margin: 2px auto;
				padding: 10px;
				position: relative;
				width: 100%
			}
			
			#container .slider .paginatedList:first-child {
				border-top: none;
			}
			
			#container .slider .paginatedList .leftImg {
				float: left;
			}
			
			#container .slider .paginatedList .leftImg img {
				-webkit-border-radius: 3px;
				-moz-border-radius: 3px;
				border-radius: 3px;
				height: 50px;
				overflow: hidden;
				width: 50px;
				margin: 0 10px 0 0;
			}
			
			#container .slider .paginatedList .details {
				float: left;
				width: 82%;
				word-wrap: normal;
				overflow: hidden;
				white-space: nowrap;
			}
			
			#container .frmwdsec {
				height: 250px;
			}
			
			#container .slider .paginatedList .details .heading {
				clear: both;
				word-wrap: break-word;
				font-family: "HelveticaNeue-Light","Helvetica Neue Light","Helvetica Neue",Helvetica,Arial,"Lucida Grande",sans-serif;
				font-size: 14px;
				line-height: 0.8;
			}
			
			#container .slider .paginatedList .details .heading span
				{
				color: rgb(24, 74, 114);
			}
			
			#container .slider .paginatedList .details .heading a {
				color: #66799f;
				text-decoration: none;
				word-wrap: break-word;
				line-height: 1.2;
			}
			
			#container .slider .paginatedList .details .heading a:hover
				{
				color: #2a3a47;
				text-decoration: none;
			}
			
			#container .slider .paginatedList .details .person {
				clear: both;
				font-size: 13px;
				color: #a7a9ab;
				padding: 3px 0 0 0;
			}
			
			#flexslider4 .slides .groups .picture img {
				max-height: 400px;
				width: 450px;
			}
			
			#flexslider5 .slides .groups .picture img {
				max-width: 550px;
				max-height: 250px;
				width: 550px;
			}
			
			.carousel_caption2 h2 {
				color: #fff;
			    font-size: 35px;
				font-weight: 700;
				margin-bottom: 15px;
				transform: matrix(1, 0, 0, 1, 0, 0);
				opacity: 1;
				text-transform: uppercase;
				line-height: 0;
				font-weight: 200;
				letter-spacing: .02em;
				position: relative;
			}
			
			.otrCover h3 {
				color: #fff;
			    font-size: 22px;
				font-weight: 500;
				font-family: ClarendonLTStdBold;
				margin-top: 65px;
				transform: matrix(1, 0, 0, 1, 0, 0);
				opacity: 1;
				line-height: 0;
				letter-spacing: .03em;
				position: relative;
			}
			
			.outlined .offset, .outlined .regular {
			    position: absolute;
			    left: 0;
			    top: -2px;
			    width: 100%;
			    height: calc(100% + 4px);
			    opacity: 1;
			}
			
			.outlined .offset {
			    -webkit-transform: translateX(5px)translateY(5px);
			    -ms-transform: translateX(5px)translateY(5px);
			    transform: translateX(5px)translateY(5px);
			}
			
			.outlined .top, .outlined .bottom {
			    width: 100%;
			    background: #fff;
			    position: absolute;
			    top: 0;
			    left: 0;
			    border-top: 2px solid #fff;
			}
			
			.outlined .bottom {
			    bottom: 0;
			    top: auto;
			    right: 0;
			    left: auto;
			}
			
			.outlined .left {
			    top: auto;
			    bottom: 0;
			}
			
			.outlined .left, .outlined .right {
			    height: 100%;
			    position: absolute;
			    left: 0;
			    top: 0;
			    border-left: 2px solid #fff;
			}
			
			.outlined .right {
			    right: 0;
			    left: auto;
			}
			
			.bannerContent {
				padding-right: 40px;
				padding-left: 40px;
				vertical-align: middle;
				display: table-cell;
				text-align: center;
			}
			
			#container .outerwycdSection {
				width: 100%;
				float: left;
			}
			
			#container .wycdCommSection .sliderLowerOuter {
				margin-bottom: 20px; 
				width: 66.66%; 
				float: left;
			}
			
			#container .wycdCommSection .bannerOuter {
				margin-bottom: 20px;
				float: left;
				width: 33.33%;
				max-height: 280px;
			}
			
			#container .wycdSection .sliderLowerOuter {
				width: 60%; 
				float: left;
			}
			
			#container .inboxAds {
				display: inline-block; 
				width: 30%; 
				margin-top: 30px;
			}
			
			.myCustomSearchClass {
				min-width: unset;
			}
			
			.myCustomSearchClass li {
			    width: 560px;
			}
			
			@media only screen and (min-width: 769px) {
				.topBnrImg1 {
				 	background-image:url(${communityEraContext.contextUrl}/images/banner_1.jpg);
				}
				
				.topBnrImg2 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_2.jpg);
				}
				
				.topBnrImg3 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_3.jpg);
				}
				
				.topBnrImg4 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_4.jpg);
				}
			}
			
			@media only screen and (min-width: 941px) and (max-width: 1124px) {
				.carousel_caption2 h2 {
					font-size: 42px;
					font-weight: 600;
				}
			}
			
			@media only screen and (min-width: 768px) and (max-width: 940px) {
				.carousel_caption2 h2 {
					font-size: 38px;
					font-weight: 600;
				}
				
				.stretchHeight {
					bottom: 220px;
				}
				
				.bannerContent {
					padding-right: 10px;
					padding-left: 10px;
					vertical-align: middle;
					display: table-cell;
				}
				
				#container .wycdSection {
					width: 64%;
					float: left;
					margin-right: 6px;
					margin-left: 6px;
				}
				
				.middleSection {
					text-align: center;
					width: 100%;
					display: inline-block;
				}
			}
			
			.middleSection h2 {
				text-align: center;
				margin-bottom: 6.25px; 
				font-size: 45px;
				font-family: "canada-type-gibson",sans-serif;
				font-style: normal;
				font-weight: 600;
				line-height: 1;
				letter-spacing: -2px;
			}
			
			.middleSection p {
				text-align: center;
				font-size: 18px;
				font-family: "canada-type-gibson",sans-serif;
				font-style: normal;
				font-weight: normal;
				line-height: 1.4;
				margin: 10px 0px;
			}
			
			.linkBuilderList {
			    max-width: 1060px;
				margin: 0px auto;
			}
				
			/* Extra small devices (phones, up to 480px) */
			@media screen and (min-width: 481px) and (max-width: 767px) {
			
				#container #add2 {
					display: none;
				}
				
				#container #add4 {
					display: none;
				}
			
				.topBnrImg1 {
				 	background-image:url(${communityEraContext.contextUrl}/images/banner_M_1.jpg);
				}
				
				.topBnrImg2 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_M_2.jpg);
				}
				
				.topBnrImg3 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_M_3.jpg);
				}
				
				.topBnrImg4 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_M_4.jpg);
				}
				
				.carousel_caption2 h2 {
					font-size: 32px;
					font-weight: 600;
				}
				
				.stretchHeight {
					bottom: 200px;
				}

				.bnrImg {
				    height: 530px;
				}
				
				.myCustomSearchClass li {
				    width: 481px;
				}
			}
			
			/* Extra small devices (phones, up to 480px) */
			@media screen and (max-width: 767px) {
			
				#container #add2 {
					display: none;
				}
				
				#container #add4 {
					display: none;
				}
				
				#container .wycdCommSection {
					width: 96%;
					margin: 30px 10px auto;
				}
				
				#container .wycdCommSection {
					width: 96%;
					margin: 30px 10px auto;
				}
				
				#container .outerwycdSection {
				    width: 96%;
					margin: 30px 10px auto;
				    float: left;
				}

				#container .wycdCommSection .sliderLowerOuter {
					margin-bottom: 20px; 
					width: 100%; 
					float: left;
				}
				
				#container .wycdCommSection .bannerOuter {
					margin-bottom: 20px;
					float: left;
					width: 98%;
				}
				
				#container .wycdSection .bannerOuter {
					margin-bottom: 20px;
					float: left;
					width: 98%;
				}
				
				#container .wycdSection {
					width: 98%;
					float: left;
					height: 430px;
				}
				
				#container .inboxAds::before {
				    content: "";
				 }
				
				#container .wycdSection .sliderLowerOuter {
					width: 100%; 
					float: left;
				}
				
				.flexslider {
				    margin: 0;
				    padding: 0;
				    height: 220px;
				}
								
				#container .wycdCommSection .innerGroups {
					height: 40px;
					top: 256px;
					right: 10px;
				}
				
				#container .wycdSection .innerGroups {
					top: 214px;
					right: 10px;
				}
				
				#container .inboxAds {
					display: inline-block;
					width: 100%;
					margin-top: 0px;
					text-align: center;
				}
				
				#flexslider1 .flex-viewport {
				    height: 310px;
				}
				
				#container .sliderOuter {
					height: auto;
				}
				
				.flexslider {
				    height: auto;
				    margin: 0px;
				}
				
				.flex-control-nav {
					display: none;
				}
				
				.info span {
   					font-size: 12px;
   				}
				
				#flexslider2 .flex-viewport {
					height: auto;
				}
				
				.carousel_caption2 h2 {
				    font-size: 30px;
					font-weight: 500;
				}
				
				.carousel_caption2 .infotext {
					margin-left: 40px;
					margin-right: 40px;
				}
				
				header .section .rowBlock .linkBuilder {
					float: right;
				}
				
				header .section .rowBlock .linkBuilder ul {
					display: inline-block;
				}
				
				.bannerTitle {
					font-size: 18px; 
					line-height: 1.1; 
					margin-bottom: 15px; 
					margin-top: 15px;
				}
				
				.btnBanner {
					font-size: 14px; 
				}
			}
			
			@media only screen and (min-width: 480px) and (max-width: 600px) {
				#container #add2 {
					display: none;
				}
				
				#container #add4 {
					display: none;
				}
				.carousel_caption2 h2 {
				    font-size: 28px;
					font-weight: 500;
				}
				
				.carousel_caption2 .infotext {
					font-size: 16px;
				}
				
				header .section .rowBlock .linkBuilder ul.mainLB {
					display: inline-block;
				}
				
				.a-icon-text-sep-Cloud {
					margin-top: 8px;
				}
				
				.myCustomSearchClass li {
				    width: 481px;
				}
			}
			
			@media only screen and (max-width: 480px) {
				#container #add2 {
					display: none;
				}
				
				#container #add4 {
					display: none;
				}
				.topBnrImg1 {
				 	background-image:url(${communityEraContext.contextUrl}/images/banner_S_1.jpg);
				}
				
				.topBnrImg2 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_S_2.jpg);
				}
				
				.topBnrImg3 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_S_3.jpg);
				}
				
				.topBnrImg4 {
					 background-image:url(${communityEraContext.contextUrl}/images/banner_S_4.jpg);
				}
				
				header .section .rowBlock .linkBuilder ul.mainLB {
					display: inline-block;
				}
				
				header .section .rowBlock .linkBuilder ul li a {
    				font-size: 15px;
    			}
				.bnrImg {
				    height: 420px;
				}
				
				#container .outerwycdSection {
					margin: 0px;
				}				
				
				#container .wycdCommSection {
					margin: 30px 7px auto;
				}
				
				.carousel_caption2 h2 {
				    font-size: 22px;
					font-weight: 500;
				}
				
				.stretchHeight {
					bottom: 140px;
				}
				
				.faSearchicon {
				    padding: 6px;
				    font-size: 18px;
				}
				
				.sbContainerInput .sbInput {
				    padding-left: 2.2em;
				    z-index: 50;
				    border: white;
				    vertical-align: middle;
				    min-height: 30px;
				    font-size: 1em;
				    margin: 0px auto;
				}
				
				header .section .rowBlock .linkBuilder ul li:first-child {
				    padding: 12px 8px 0px 0px;
				}
				
				header .section .rowBlock .linkBuilder ul li {
				    padding: 8px;
				}
				
				header .section .rowBlock .linkBuilder ul li:last-child {
				    padding: 12px 0px 0px 8px;
				}
				
				.linkBuilderItem {
    				width: 17%;
    			}
    			
    			.linkBuilderItem:first-child {
    				width: 28%;
    			}
				
				.linkBuilderItem a {
				    font-weight: 600;
				    font-size: 14px;
    			}
				
				#container .wycdCommSection .innerGroups {
				    height: 40px;
				    top: 174px;
				    right: 10px;
				}
				
				.carousel_caption2 .infotext {
					font-size: 15px;
					margin-left: 16px;
					margin-right: 16px;
				}
				
				.myCustomSearchClass li {
				    width: 320px;
				}
				
				.bannerShort .bnrImg {
				    height: 260px;
				}
				
				.bannerShort .bnrStImg {
				    height: 216px;
				}
				
				.bannerShort .bnrEvImg{
				    height: 240px;
				}
			}
		</style>

		<script type="text/javascript">
			$(document).ready(function () {
			normalQtip();
			var parallax = document.querySelectorAll(".parallax"),
		    speed = 0.5;

			  window.onscroll = function(){
			    [].slice.call(parallax).forEach(function(el,i){
			      var windowYOffset = window.pageYOffset,
			          elBackgrounPos = "50% " + (windowYOffset * speed) + "px";

			      el.style.backgroundPosition = elBackgrounPos;

			    });
			  };
		      $('#flexslider1').flexslider({
		    	  animation: "fade",
		          itemWidth: 10000,
		          pauseOnHover: false,
		          directionNav: false,
		          slideshowSpeed: 8000,
		          controlNav: false,
		          randomize: true,   
		          slideToStart: 0,
		          start: function(slider){
		          }
		        });

		      $('#flexslider2').flexslider({
		          animation: "slide",
		          itemWidth: 10000,
		          pauseOnHover: true,
		          directionNav: false,
		          slideshowSpeed: 5000,
		          controlNav: false,
		          randomize: true, 
		          start: function(slider){
		          }
		        });

		      $('#flexslider3').flexslider({
		          animation: "slide",
		          itemWidth: 10000,
		          pauseOnHover: true,
		          directionNav: false,
		          slideshowSpeed: 5000,
		          controlNav: false,
		          randomize: true, 
		          start: function(slider){
		          }
		        });
		      $('#flexslider4').flexslider({
		          animation: "slide",
		          itemWidth: 10000,
		          pauseOnHover: true,
		          directionNav: false,
		          slideshowSpeed: 5000,
		          controlNav: false,
		          randomize: true, 
		          start: function(slider){
		          }
		        });
		      $('#flexslider5').flexslider({
		          animation: "slide",
		          itemWidth: 10000,
		          pauseOnHover: true,
		          directionNav: false,
		          slideshowSpeed: 5000,
		          controlNav: false,
		          randomize: true, 
		          start: function(slider){
		          }
		        });
			});
		</script>
		<script type="text/javascript">
			function onCommunityClick (commId){
				window.location.href = '${communityEraContext.contextUrl}/cid/'+commId+'/home.do';
			}
			function onBlogClick (commId, blogId){
				window.location.href = '${communityEraContext.contextUrl}/cid/'+commId+'/blog/blogEntry.do?id='+blogId;
			}
			function onWikiClick (commId, entryId){
				window.location.href = '${communityEraContext.contextUrl}/cid/'+commId+'/wiki/wikiDisplay.do?entryId='+entryId;
			}
			function onEventClick (commId, eventId){
				window.location.href = '${communityEraContext.contextUrl}/cid/'+commId+'/event/showEventEntry.do?id='+eventId;
			}
		</script>
	</head>

	<body id="communityEra">
		<header style="width: 100%;">
			<div class="section coBranded floatLeft" style="padding: 0px;">
				<div class="rowBlock" style="position: relative !important;">
					<div class="logo floatLeft">
						<a href="${communityEraContext.contextUrl}">
							<img src="images/link_Image.png" width="46" height="46" style="image-rendering: pixelated;">
						</a>
					</div>
					<div class="linkBuilder floatLeft" >
						<ul class="mainLB" >
					    	<li><a href="${communityEraContext.contextUrl}/pers/registerMe.do" >Sign up</a></li>
					    	<li><i class='a-icon-text-sep-Cloud'></i></li>
							<li><a href="${communityEraContext.contextUrl}/login.do" >Log in</a></li>
					    </ul>
					</div>
				</div>
			</div>
		</header>
		
			<div class="sliderOuter" >
				<div class="sliderTop" >
					<div id="flexslider1" class="flexslider ">
						<ul class="slides" >
						 	<li>
								 <div class="groups">
									<div class="banner" >
										<div class="bnrImg topBnrImg topBnrImg1 parallax"  >
											<div class="otrCover">
												<div class="carousel_caption2">
													<div class="outlined">
														<h2>Community</h2>
														<span style="opacity: 1;" class="regular outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
														<span style="opacity: 1;" class="offset outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
													</div>
													<div class="outlined" style="margin-left: 20px; margin-right: 20px;">
														<h2>Connection</h2>
														<span style="opacity: 1;" class="regular outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
														<span style="opacity: 1;" class="offset outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
													</div>
													<div class="outlined">
														<h2>Collaboration</h2>
														<span style="opacity: 1;" class="regular outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
														<span style="opacity: 1;" class="offset outline">
															<span style="width: 100%; opacity: 1;" class="top"></span>
															<span style="width: 100%; opacity: 1;" class="bottom"></span>
															<span style="height: 100%; opacity: 1;" class="left"></span>
															<span style="height: 100%; opacity: 1;" class="right"></span>
														</span>
													</div>
													<h3>Social collaboration solution for personal and enterprises. Empower to communicate, collaborate, and build strong relationships with others.</h3>
												</div>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li>
								 <div class="groups">
									<div class="banner" >
										<div class="bnrImg topBnrImg topBnrImg2 parallax" >
											<div class="otrCover">
												<div class="carousel_caption2 outlined">
													<h2>Community</h2>
													<span style="opacity: 1;" class="regular outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>
													<span style="opacity: 1;" class="offset outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>					
												</div>
												<h3>Discover and share, Starts with Communities...</h3>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li>
								 <div class="groups">
									<div class="banner" >
										<div class="bnrImg topBnrImg topBnrImg3 parallax"  >
											<div class="otrCover">
												<div class="carousel_caption2 outlined">
													<h2>Connection</h2>
													<span style="opacity: 1;" class="regular outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>
													<span style="opacity: 1;" class="offset outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>						
												</div>
												<h3>Build a network of useful contacts and follow people that interest you...</h3>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li>
								 <div class="groups">
									<div class="banner" >
										<div class="bnrImg topBnrImg topBnrImg4 parallax"  >
											<div class="otrCover">
												<div class="carousel_caption2 outlined">
													<h2>Collaboration</h2>
													<span style="opacity: 1;" class="regular outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom outline"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>
													<span style="opacity: 1;" class="offset outline">
														<span style="width: 100%; opacity: 1;" class="top"></span>
														<span style="width: 100%; opacity: 1;" class="bottom"></span>
														<span style="height: 100%; opacity: 1;" class="left"></span>
														<span style="height: 100%; opacity: 1;" class="right"></span>
													</span>								
												</div>
												<h3>Blogs, Discussion Forums, Wikis, Event Calendar, File & Media Sharing, Member Profile, Messaging and way more...</h3>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
						<div class="stretchHeight">
							<div class="searchAdjustment coBranded floatLeft">
								<div class="linkBuilderList">
									<div class="linkBuilderItem floatLeft"><a href="${communityEraContext.contextUrl}/community/showCommunities.do" >Communities</a></div>
									<div class="linkBuilderItem floatLeft"><a href="${communityEraContext.contextUrl}/blog/allBlogs.do" >Blogs</a></div>
									<div class="linkBuilderItem floatLeft"><a href="${communityEraContext.contextUrl}/forum/allForums.do" >Forums</a></div>
									<div class="linkBuilderItem floatLeft"><a href="${communityEraContext.contextUrl}/wiki/allWikis.do" >Wikis</a></div>
									<div class="linkBuilderItem floatLeft"><a href="${communityEraContext.contextUrl}/event/allEvents.do" >Events</a></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="container" >
				<div class="wycdCommSection">
					<div class="sliderLowerOuter" >
						<div class="slider" style="" >
							<div id="flexslider2" class="flexslider mainflexs">
								<ul class="slides" style="">
								 	<c:forEach items="${command.latestCommList}" var="row" varStatus="status">
								 		<li>
											 <div class="groups">
											 	<div class="bannerShort" onclick="onCommunityClick(${row.entryId});">
													<div class="bnrStImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?imgType=Banner&communityId=${row.entryId});" >
														<div class="innerGroups" >
															<span class="header" style="line-height: 2.8"><a href="${communityEraContext.contextUrl}/cid/${row.entryId}/home.do" >${row.name}</a></span>
														</div>
													</div>
												</div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="bannerOuter" >
						<div class="bannerContainer" style="width: 100%; height: 100%; display: table; position: relative;" >
							<div class="bannerContent" >
								<div class="bannerTitle"  >
									<strong><i class="fa fa-group" style="margin-right: 10px;"></i>Build and Grow your Communities</strong>
								</div>
								<div class="bannerSubTitle" style="font-size: 16px; line-height: 1.1; " >
									Smarter way for group communication and to explore & share
								</div>
								
								<div class="community">
									<a href="${communityEraContext.contextUrl}/community/showCommunities.do?communityOption=2" class="btnBanner" >
										<strong class="btnBlock"><i class="fa fa-globe" style="margin-right: 8px;"></i>Visit All Communities</strong>
									</a>
									<a href="${communityEraContext.contextUrl}/community/createNewCommunity.do" class="btnBanner" >
										<strong class="btnBlock"><i class="fa fa-users" ></i><i class="fa fa-plus" style="margin-right: 8px; font-size: 10px; margin-left: -1px;"></i>Start a Community</strong>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="middleSection" style="text-align: center;">
					<h2>Here's what you can do</h2>
					<p>Be more social with Jhapak. Blog, Forum, Wiki, Event, Calendar, Assignments and To-Do and many more tools to communicate, collaborate, and build strong relationships.</p>
				</div>
				
				<div class="outerwycdSection">
					<div class="wycdSection" >
						<div class="bannerOuter" >
							<div class="bannerContainer" style="width: 100%; height: 100%; display: table; position: relative;" >
								<div class="bannerContent" >
									<div class="bannerTitle" >
										<strong><i class="fa fa fa-quote-left" style="margin-right: 10px;"></i>Community Blogs</strong>
									</div>
									<div class="bannerSubTitle" style="font-size: 16px; line-height: 1.1; " >
										A place to share ideas, concepts and words
									</div>
									<div class="community">
										<a href="${communityEraContext.contextUrl}/blog/allBlogs.do" class="btnBanner" >
											<strong class="btnBlock"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>View All Posts</strong>
									</a>
								</div>
								</div>
							</div>
						</div>
						<div class="sliderLowerOuter" >
							<div class="slider" style="" >
								<div id="flexslider3" class="flexslider mainflexs" style="height: 240px;">
									<ul class="slides" style="">
									 	<c:forEach items="${communityEraContext.topBlogPosts}" var="row" varStatus="status">
									 		<li>
												 <div class="groups">
												 	<div class="bannerShort" onclick="onBlogClick(&#39;${row.communityId}&#39;, &#39;${row.entryId}&#39;);">
														<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/common/showImage.img?type=BlogEntry&itemId=${row.entryId});" >
															<div class="innerGroups">
																<span class="header"><a href="cid/${row.communityId}/blog/blogEntry.do?id=${row.entryId}" >${row.title}</a></span>
															</div>
														</div>
													</div>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="inboxAds" id="add1">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
				</div> 
					
				<div class="outerwycdSection" >
					<div class="inboxAds" style="float: left;" id="add2">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
					<div class="wycdSection frmwdsec" >
						<div class="sliderLowerOuter" >
							<div class="slider" style="" >
							 	<c:forEach items="${communityEraContext.topForumTopic}" var="row" varStatus="status">
							 		<div class='paginatedList'>
										<div class='leftImg'>
											<c:choose>
												<c:when test="${row.photoPresent}">						 
													<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.authorId}&backlink=ref' title='${row.fullName} - Original Poster'>
								 						<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.authorId}' style='height: 40px; width: 40px;'/>
								 					</a>
												</c:when>
												<c:otherwise>
													<img src='img/user_icon.png' style='height: 40px; width: 40px;' title='${row.fullName} - Original Poster' />
												</c:otherwise>
											</c:choose>
										</div>
											
										<div class='details'>
											<div class='heading' style='word-wrap: normal;'>
												<a href='${communityEraContext.contextUrl}/cid/${row.communityId}/forum/forumThread.do?backlink=ref&amp;id=${row.topicId}' >${row.subject}</a>
											</div>
											<div class='person'>
												${row.responseCount} replies <i class='fa fa-circle' style='margin: 0px 8px; font-size: 6px;'></i> ${row.topicLike} likes
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="bannerOuter" >
							<div class="bannerContainer" style="width: 100%; height: 100%; display: table; position: relative;" >
								<div class="bannerContent" >
									<div class="bannerTitle" >
										<strong><i class="fa fa fa-comments" style="margin-right: 10px;"></i>Discussion Forums</strong>
									</div>
									<div class="bannerSubTitle" style="font-size: 16px; line-height: 1.1; " >
										Participate in discussions of interest, or start a new in your own Community
									</div>
									<div class="community">
										<a href="${communityEraContext.contextUrl}/forum/allForums.do" class="btnBanner" >
											<strong class="btnBlock"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>View All Topics</strong>
									</a>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
					
				<div class="outerwycdSection" >
					<div class="wycdSection" >
						<div class="bannerOuter" >
							<div class="bannerContainer" style="width: 100%; height: 100%; display: table; position: relative;" >
								<div class="bannerContent" >
									<div class="bannerTitle" >
										<strong><i class="fa fa-book" style="margin-right: 10px;"></i>Community Wikies</strong>
									</div>
									<div class="bannerSubTitle" style="font-size: 16px; line-height: 1.1; " >
										Contribute and collaborate your knowledge and help others to build and benefit from your Community 
									</div>
									<div class="community">
										<a href="${communityEraContext.contextUrl}/wiki/allWikis.do" class="btnBanner" >
											<strong class="btnBlock"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>View All Articles</strong>
									</a>
								</div>
								</div>
							</div>
						</div>
						<div class="sliderLowerOuter" >
							<div class="slider" style="" >
								<div id="flexslider4" class="flexslider mainflexs" style="height: 240px;">
									<ul class="slides" style="">
									 	<c:forEach items="${communityEraContext.topWikiEntries}" var="row" varStatus="status">
									 		<li>
												 <div class="groups">
												 	<div class="bannerShort" onclick="onWikiClick(&#39;${row.communityId}&#39;, &#39;${row.entryId}&#39;);">
												 		<c:if test="${row.imageCount > 0}">
												 			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/common/showImage.img?type=WikiEntry&itemId=${row.entryId});" >
																<div class="innerGroups">
																	<span class="header"><a href="cid/${row.communityId}/wiki/wikiDisplay.do?backlink=ref&entryId=${row.entryId}" >${row.title}</a></span>
																</div>
															</div>
						      							</c:if>
						      							<c:if test="${row.imageCount == 0}">
						      								<c:if test="${row.sectionImageCount > 0}">
						      									<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/common/showImage.img?type=WikiEntrySectionAll&itemId=${row.entryId});" >
																	<div class="innerGroups">
																		<span class="header"><a href="cid/${row.communityId}/wiki/wikiDisplay.do?entryId=${row.entryId}" >${row.title}</a></span>
																	</div>
																</div>
							      							</c:if>
						      							</c:if>
													</div>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="inboxAds" id="add3">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
				</div> 
					
				<div class="outerwycdSection" >
					<div class="inboxAds" style="float: left;" id="add4">
						<c:if test="${communityEraContext.production}">
							<!-- CERA_RP_R -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:300px;height:250px"
							     data-ad-client="ca-pub-8702017865901113"
							     data-ad-slot="4553333789"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</c:if>
					</div>
					<div class="wycdSection" >
						<div class="sliderLowerOuter" >
							<div class="slider" style="" >
								<div id="flexslider5" class="flexslider mainflexs" style="height: 240px;">
									<ul class="slides" style="">
									 	<c:forEach items="${communityEraContext.topGlobalEvents}" var="row" varStatus="status">
									 		<li>
												 <div class="groups">
												 	<div class="bannerShort" onclick="onEventClick(&#39;${row.communityID}&#39;, &#39;${row.entryId}&#39;);">
														<div class="bnrEvImg" style="background-image:url(${communityEraContext.contextUrl}/event/eventPoster.img?id=${row.entryId});" >
															<div class="innerGroups">
																<span class="header"><a href="cid/${row.communityID}/event/showEventEntry.do?id=${row.entryId}" >${row.name}</a></span>
															</div>
														</div>
													</div>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<div class="bannerOuter" >
							<div class="bannerContainer" style="width: 100%; height: 100%; display: table; position: relative;" >
								<div class="bannerContent" >
									<div class="bannerTitle" >
										<strong><i class="fa fa-calendar-check-o" style="margin: 0px 8px;"></i>Upcoming Events</strong>
									</div>
									<div class="bannerSubTitle" style="font-size: 16px; line-height: 1.1; " >
										Host, manage and promote successful events for the Community
									</div>
									<div class="community">
										<a href="${communityEraContext.contextUrl}/event/allEvents.do" class="btnBanner" >
											<strong class="btnBlock"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>View Upcoming Events</strong>
									</a>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
/*! Swipebox v1.4.4 | Constantin Saguin csag.co | MIT License | github.com/brutaldesign/swipebox */

;( function ( window, document, $, undefined ) {

	$.swipebox = function( elem, options ) {

		// Default options
		var ui,
			defaults = {
				useCSS : true,
				useSVG : true,
				initialIndexOnArray : 0,
				removeBarsOnMobile : true,
				hideCloseButtonOnMobile : false,
				hideBarsDelay : 3000,
				videoMaxWidth : 1140,
				vimeoColor : 'cccccc',
				beforeOpen: null,
				afterOpen: null,
				afterClose: null,
				afterMedia: null,
				nextSlide: null,
				prevSlide: null,
				loopAtEnd: false,
				autoplayVideos: false,
				shareButtons: [
								{id:'facebook', label:'Share on Facebook', url:'https://www.facebook.com/sharer/sharer.php?u={{url}}'},
								{id:'twitter', label:'Tweet', url:'https://twitter.com/intent/tweet?text={{text}}&url={{url}}'},
								{id:'pinterest', label:'Pin it', url:'http://www.pinterest.com/pin/create/button/'+
																	'?url={{url}}&media={{image_url}}&description={{text}}'},
								{id:'download', label:'Download image', url:'{{raw_image_url}}', download:true}
							],
				queryStringData: {},
				toggleClassOnLoad: ''
			},

			plugin = this,
			elements = [], // slides array [ { href:'...', title:'...' }, ...],
			$elem,
			selector = elem.selector,
			isMobile = navigator.userAgent.match( /(iPad)|(iPhone)|(iPod)|(Android)|(PlayBook)|(BB10)|(BlackBerry)|(Opera Mini)|(IEMobile)|(webOS)|(MeeGo)/i ),
			isTouch = isMobile !== null || document.createTouch !== undefined || ( 'ontouchstart' in window ) || ( 'onmsgesturechange' in window ) || navigator.msMaxTouchPoints,
			supportSVG = !! document.createElementNS && !! document.createElementNS( 'http://www.w3.org/2000/svg', 'svg').createSVGRect,
			winWidth = window.innerWidth ? window.innerWidth : $( window ).width(),
			winHeight = window.innerHeight ? window.innerHeight : $( window ).height(),
			currentX = 0,
			/* jshint multistr: true */
			html = '<div id="swipebox-overlay">\
					<div id="swipebox-container">\
						<div id="swipebox-slider"></div>\
						<div id="swipebox-top-bar">\
							<div id="swipebox-title"></div>\
						</div>\
						<div id="share-modal" class="share-modal--hidden">\
				            <div id="share-tooltip">\
				            </div>\
				        </div>\
						<div id="info-modal" class="info-modal--hidden">\
			            	<div id="info-tooltip">\
			            	</div>\
			            </div>\
						<div id="swipebox-bottom-bar">\
							<div id="swipebox-arrows">\
								<a id="swipebox-prev"></a>\
								<a id="swipebox-next"></a>\
							</div>\
						</div>\
						<a id="swipebox-share"><i class="fa fa-share-alt-square" aria-hidden="true"></i></a>\
						<a id="swipebox-info"><i class="fa fa-info-circle" aria-hidden="true"></i></a>\
						<a id="info-modal-close" class="info-modal-close--hidden"><i class="fa fa-times" aria-hidden="true"></i></a>\
						<a id="swipebox-close"></a>\
					</div>\
			</div>';

		plugin.settings = {};

		$.swipebox.close = function () {
			ui.closeSlide();
		};

		$.swipebox.extend = function () {
			return ui;
		};

		plugin.init = function() {

			plugin.settings = $.extend( {}, defaults, options );

			if ( $.isArray( elem ) ) {

				elements = elem;
				ui.target = $( window );
				ui.init( plugin.settings.initialIndexOnArray );

			} else {

				$( document ).on( 'click', selector, function( event ) {

					// console.log( isTouch );

					if ( event.target.parentNode.className === 'slide current' ) {

						return false;
					}

					if ( ! $.isArray( elem ) ) {
						ui.destroy();
						$elem = $( selector );
						ui.actions();
					}

					elements = [];
					var index, relType, relVal;

					// Allow for HTML5 compliant attribute before legacy use of rel
					if ( ! relVal ) {
						relType = 'data-rel';
						relVal = $( this ).attr( relType );
					}

					if ( ! relVal ) {
						relType = 'rel';
						relVal = $( this ).attr( relType );
					}

					if ( relVal && relVal !== '' && relVal !== 'nofollow' ) {
						$elem = $( selector ).filter( '[' + relType + '="' + relVal + '"]' );
					} else {
						$elem = $( selector );
					}

					$elem.each( function() {

						var title = null,
							mediaId = null,
							shareUrl = null,
							dataType = null,
							downloadUrl = null,
							href = null;

						if ( $( this ).attr( 'title' ) ) {
							title = $( this ).attr( 'title' );
						}


						if ( $( this ).attr( 'href' ) ) {
							href = $( this ).attr( 'href' );
						}
						
						if ( $( this ).attr( 'data-id' ) ) {
							mediaId = $( this ).attr( 'data-id' );
						}
						
						if ( $( this ).attr( 'data-url' ) ) {
							shareUrl = $( this ).attr( 'data-url' );
						}
						
						if ( $( this ).attr( 'data-download-url' ) ) {
							downloadUrl = $( this ).attr( 'data-download-url' );
						}
						
						if ( $( this ).attr( 'data-type' ) ) {
							dataType = $( this ).attr( 'data-type' );
						}
						
						elements.push( {
							href: href,
							title: title,
							mediaId:mediaId,
							dataType:dataType,
							downloadUrl:downloadUrl
						} );
					} );

					index = $elem.index( $( this ) );
					event.preventDefault();
					event.stopPropagation();
					ui.target = $( event.target );
					ui.init( index );
				} );
			}
		};

		ui = {

			/**
			 * Initiate Swipebox
			 */
			init : function( index ) {
				if ( plugin.settings.beforeOpen ) {
					plugin.settings.beforeOpen();
				}
				this.target.trigger( 'swipebox-start' );
				$.swipebox.isOpen = true;
				this.build();
				this.openSlide( index );
				this.openMedia( index );
				this.preloadMedia( index+1 );
				this.preloadMedia( index-1 );
				if ( plugin.settings.afterOpen ) {
					plugin.settings.afterOpen(index);
				}
			},

			/**
			 * Built HTML containers and fire main functions
			 */
			build : function () {
				var $this = this, bg;

				$( 'body' ).append( html );

				if ( supportSVG && plugin.settings.useSVG === true ) {
					bg = $( '#swipebox-close' ).css( 'background-image' );
					bg = bg.replace( 'png', 'svg' );
					$( '#swipebox-prev, #swipebox-next, #swipebox-close' ).css( {
						'background-image' : bg
					} );
				}

				if ( isMobile && plugin.settings.removeBarsOnMobile ) {
					$( '#swipebox-bottom-bar, #swipebox-top-bar' ).remove();
				}

				$.each( elements,  function() {
					$( '#swipebox-slider' ).append( '<div class="slide"></div>' );
				} );

				$this.setDim();
				$this.actions();

				if ( isTouch ) {
					$this.gesture();
				}

				// Devices can have both touch and keyboard input so always allow key events
				$this.keyboard();

				$this.animBars();
				$this.resize();

			},
			
			/**
			 * Set dimensions depending on windows width and height
			 */
			setDim : function () {

				var width, height, sliderCss = {};

				// Reset dimensions on mobile orientation change
				if ( 'onorientationchange' in window ) {

					window.addEventListener( 'orientationchange', function() {
						if ( window.orientation === 0 ) {
							width = winWidth;
							height = winHeight;
						} else if ( window.orientation === 90 || window.orientation === -90 ) {
							width = winHeight;
							height = winWidth;
						}
					}, false );


				} else {

					width = window.innerWidth ? window.innerWidth : $( window ).width();
					height = window.innerHeight ? window.innerHeight : $( window ).height();
				}

				sliderCss = {
					width : width,
					height : height
				};

				$( '#swipebox-overlay' ).css( sliderCss );

			},

			/**
			 * Reset dimensions on window resize envent
			 */
			resize : function () {
				var $this = this;

				$( window ).resize( function() {
					$this.setDim();
				} ).resize();
			},

			/**
			 * Check if device supports CSS transitions
			 */
			supportTransition : function () {

				var prefixes = 'transition WebkitTransition MozTransition OTransition msTransition KhtmlTransition'.split( ' ' ),
					i;

				for ( i = 0; i < prefixes.length; i++ ) {
					if ( document.createElement( 'div' ).style[ prefixes[i] ] !== undefined ) {
						return prefixes[i];
					}
				}
				return false;
			},

			/**
			 * Check if CSS transitions are allowed (options + devicesupport)
			 */
			doCssTrans : function () {
				if ( plugin.settings.useCSS && this.supportTransition() ) {
					return true;
				}
			},

			/**
			 * Touch navigation
			 */
			gesture : function () {

				var $this = this,
					index,
					hDistance,
					vDistance,
					hDistanceLast,
					vDistanceLast,
					hDistancePercent,
					vSwipe = false,
					hSwipe = false,
					hSwipMinDistance = 10,
					vSwipMinDistance = 50,
					startCoords = {},
					endCoords = {},
					bars = $( '#swipebox-top-bar, #swipebox-bottom-bar' ),
					slider = $( '#swipebox-slider' );

				bars.addClass( 'visible-bars' );
				$this.setTimeout();

				$( 'body' ).bind( 'touchstart', function( event ) {

					$( this ).addClass( 'touching' );
					index = $( '#swipebox-slider .slide' ).index( $( '#swipebox-slider .slide.current' ) );
					endCoords = event.originalEvent.targetTouches[0];
					startCoords.pageX = event.originalEvent.targetTouches[0].pageX;
					startCoords.pageY = event.originalEvent.targetTouches[0].pageY;

					$( '#swipebox-slider' ).css( {
						'-webkit-transform' : 'translate3d(' + currentX +'%, 0, 0)',
						'transform' : 'translate3d(' + currentX + '%, 0, 0)'
					} );

					$( '.touching' ).bind( 'touchmove',function( event ) {
						event.preventDefault();
						event.stopPropagation();
						endCoords = event.originalEvent.targetTouches[0];

						if ( ! hSwipe ) {
							vDistanceLast = vDistance;
							vDistance = endCoords.pageY - startCoords.pageY;
							if ( Math.abs( vDistance ) >= vSwipMinDistance || vSwipe ) {
								var opacity = 0.75 - Math.abs(vDistance) / slider.height();

								slider.css( { 'top': vDistance + 'px' } );
								slider.css( { 'opacity': opacity } );

								vSwipe = true;
							}
						}

						hDistanceLast = hDistance;
						hDistance = endCoords.pageX - startCoords.pageX;
						hDistancePercent = hDistance * 100 / winWidth;

						if ( ! hSwipe && ! vSwipe && Math.abs( hDistance ) >= hSwipMinDistance ) {
							$( '#swipebox-slider' ).css( {
								'-webkit-transition' : '',
								'transition' : ''
							} );
							hSwipe = true;
						}

						if ( hSwipe ) {

							// swipe left
							if ( 0 < hDistance ) {

								// first slide
								if ( 0 === index ) {
									// console.log( 'first' );
									$( '#swipebox-overlay' ).addClass( 'leftSpringTouch' );
								} else {
									// Follow gesture
									$( '#swipebox-overlay' ).removeClass( 'leftSpringTouch' ).removeClass( 'rightSpringTouch' );
									$( '#swipebox-slider' ).css( {
										'-webkit-transform' : 'translate3d(' + ( currentX + hDistancePercent ) +'%, 0, 0)',
										'transform' : 'translate3d(' + ( currentX + hDistancePercent ) + '%, 0, 0)'
									} );
								}

							// swipe rught
							} else if ( 0 > hDistance ) {

								// last Slide
								if ( elements.length === index +1 ) {
									// console.log( 'last' );
									$( '#swipebox-overlay' ).addClass( 'rightSpringTouch' );
								} else {
									$( '#swipebox-overlay' ).removeClass( 'leftSpringTouch' ).removeClass( 'rightSpringTouch' );
									$( '#swipebox-slider' ).css( {
										'-webkit-transform' : 'translate3d(' + ( currentX + hDistancePercent ) +'%, 0, 0)',
										'transform' : 'translate3d(' + ( currentX + hDistancePercent ) + '%, 0, 0)'
									} );
								}

							}
						}
					} );

					return false;

				} ).bind( 'touchend',function( event ) {
					event.preventDefault();
					event.stopPropagation();

					$( '#swipebox-slider' ).css( {
						'-webkit-transition' : '-webkit-transform 0.4s ease',
						'transition' : 'transform 0.4s ease'
					} );

					vDistance = endCoords.pageY - startCoords.pageY;
					hDistance = endCoords.pageX - startCoords.pageX;
					hDistancePercent = hDistance*100/winWidth;

					// Swipe to bottom to close
					if ( vSwipe ) {
						vSwipe = false;
						if ( Math.abs( vDistance ) >= 2 * vSwipMinDistance && Math.abs( vDistance ) > Math.abs( vDistanceLast ) ) {
							var vOffset = vDistance > 0 ? slider.height() : - slider.height();
							slider.animate( { top: vOffset + 'px', 'opacity': 0 },
								300,
								function () {
									$this.closeSlide();
								} );
						} else {
							slider.animate( { top: 0, 'opacity': 1 }, 300 );
						}

					} else if ( hSwipe ) {

						hSwipe = false;

						// swipeLeft
						if( hDistance >= hSwipMinDistance && hDistance >= hDistanceLast) {

							$this.getPrev();

						// swipeRight
						} else if ( hDistance <= -hSwipMinDistance && hDistance <= hDistanceLast) {

							$this.getNext();
						}

					} else { // Top and bottom bars have been removed on touchable devices
						// tap
						if ( ! bars.hasClass( 'visible-bars' ) ) {
							$this.showBars();
							$this.setTimeout();
						} else {
							$this.clearTimeout();
							$this.hideBars();
						}
					}

					$( '#swipebox-slider' ).css( {
						'-webkit-transform' : 'translate3d(' + currentX + '%, 0, 0)',
						'transform' : 'translate3d(' + currentX + '%, 0, 0)'
					} );

					$( '#swipebox-overlay' ).removeClass( 'leftSpringTouch' ).removeClass( 'rightSpringTouch' );
					$( '.touching' ).off( 'touchmove' ).removeClass( 'touching' );

				} );
			},

			/**
			 * Set timer to hide the action bars
			 */
			setTimeout: function () {
				if ( plugin.settings.hideBarsDelay > 0 ) {
					var $this = this;
					$this.clearTimeout();
					$this.timeout = window.setTimeout( function() {
							$this.hideBars();
						},

						plugin.settings.hideBarsDelay
					);
				}
			},

			/**
			 * Clear timer
			 */
			clearTimeout: function () {
				window.clearTimeout( this.timeout );
				this.timeout = null;
			},

			/**
			 * Show navigation and title bars
			 */
			showBars : function () {
				var bars = $( '#swipebox-top-bar, #swipebox-bottom-bar' );
				if ( this.doCssTrans() ) {
					bars.addClass( 'visible-bars' );
				} else {
					$( '#swipebox-top-bar' ).animate( { top : 0 }, 500 );
					$( '#swipebox-bottom-bar' ).animate( { bottom : 0 }, 500 );
					setTimeout( function() {
						bars.addClass( 'visible-bars' );
					}, 1000 );
				}
			},

			/**
			 * Hide navigation and title bars
			 */
			hideBars : function () {
				var bars = $( '#swipebox-top-bar, #swipebox-bottom-bar' );
				if ( this.doCssTrans() ) {
					bars.removeClass( 'visible-bars' );
				} else {
					$( '#swipebox-top-bar' ).animate( { top : '-50px' }, 500 );
					$( '#swipebox-bottom-bar' ).animate( { bottom : '-50px' }, 500 );
					setTimeout( function() {
						bars.removeClass( 'visible-bars' );
					}, 1000 );
				}
			},

			/**
			 * Animate navigation and top bars
			 */
			animBars : function () {
				var $this = this,
					bars = $( '#swipebox-top-bar, #swipebox-bottom-bar' );

				bars.addClass( 'visible-bars' );
				$this.setTimeout();

				$( '#swipebox-slider' ).click( function() {
					if ( ! bars.hasClass( 'visible-bars' ) ) {
						$this.showBars();
						$this.setTimeout();
					}
				} );

				$( '#swipebox-bottom-bar' ).hover( function() {
					$this.showBars();
					bars.addClass( 'visible-bars' );
					$this.clearTimeout();

				}, function() {
					if ( plugin.settings.hideBarsDelay > 0 ) {
						bars.removeClass( 'visible-bars' );
						$this.setTimeout();
					}

				} );
			},

			/**
			 * Keyboard navigation
			 */
			keyboard : function () {
				var $this = this;
				$( window ).bind( 'keyup', function( event ) {
					event.preventDefault();
					event.stopPropagation();

					if ( event.keyCode === 37 ) {

						$this.getPrev();

					} else if ( event.keyCode === 39 ) {

						$this.getNext();

					} else if ( event.keyCode === 27 ) {

						$this.closeSlide();
					}
				} );
			},

			/**
			 * Navigation events : go to next slide, go to prevous slide and close
			 */
			actions : function () {
				var $this = this,
					action = 'touchend click'; // Just detect for both event types to allow for multi-input

				if ( elements.length < 2 ) {

					$( '#swipebox-bottom-bar' ).hide();

					if ( undefined === elements[ 1 ] ) {
						$( '#swipebox-top-bar' ).hide();
					}

				} else {
					$( '#swipebox-prev' ).bind( action, function( event ) {
						event.preventDefault();
						event.stopPropagation();
						$this.getPrev();
						$this.setTimeout();
					} );

					$( '#swipebox-next' ).bind( action, function( event ) {
						event.preventDefault();
						event.stopPropagation();
						$this.getNext();
						$this.setTimeout();
					} );
				}

				$( '#swipebox-close' ).bind( action, function() {
					$this.closeSlide();
				} );
				
				$( '#swipebox-info' ).bind( action, function() {
					$this.showInfoColumn();
				} );
				
				$( '#swipebox-share' ).bind( action, function() {
					$this.shareMedia();
				} );
				
				$( '#info-modal-close' ).bind( action, function() {
					$this.closeInfoColumn();
				} );
			},
			
			/**
			 * Set current slide
			 */
			setSlide : function ( index, isFirst ) {

				isFirst = isFirst || false;

				var slider = $( '#swipebox-slider' );

				currentX = -index*100;

				if ( this.doCssTrans() ) {
					slider.css( {
						'-webkit-transform' : 'translate3d(' + (-index*100)+'%, 0, 0)',
						'transform' : 'translate3d(' + (-index*100)+'%, 0, 0)'
					} );
				} else {
					slider.animate( { left : ( -index*100 )+'%' } );
				}

				$( '#swipebox-slider .slide' ).removeClass( 'current' );
				$( '#swipebox-slider .slide' ).eq( index ).addClass( 'current' );
				this.setTitle( index );

				if ( isFirst ) {
					slider.fadeIn();
				}

				$( '#swipebox-prev, #swipebox-next' ).removeClass( 'disabled' );

				if ( index === 0 ) {
					$( '#swipebox-prev' ).addClass( 'disabled' );
				} else if ( index === elements.length - 1 && plugin.settings.loopAtEnd !== true ) {
					$( '#swipebox-next' ).addClass( 'disabled' );
				}
			},
			
			getImageURLForShare: function( index ) {
				return elements[ index ].href || '';
			},
			getPageURLForShare: function( index ) {
				return elements[ index ].shareUrl || '';
			},
			getTextForShare: function( index ) {
				return elements[ index ].title || '';
			},
			getdownloadUrlForShare: function( index ) {
				return elements[ index ].downloadUrl || '';
			},
			
			/**
			 * Open slide
			 */
			openSlide : function ( index ) {
				$( 'html' ).addClass( 'swipebox-html' );
				if ( isTouch ) {
					$( 'html' ).addClass( 'swipebox-touch' );

					if ( plugin.settings.hideCloseButtonOnMobile ) {
						$( 'html' ).addClass( 'swipebox-no-close-button' );
					}
				} else {
					$( 'html' ).addClass( 'swipebox-no-touch' );
				}
				$( window ).trigger( 'resize' ); // fix scroll bar visibility on desktop
				this.setSlide( index, true );
			},

			/**
			 * Set a time out if the media is a video
			 */
			preloadMedia : function ( index ) {
				var $this = this,
					src = null;

				if ( elements[ index ] !== undefined ) {
					src = elements[ index ].href;
				}

				if ( ! $this.isVideo( src ) ) {
					setTimeout( function() {
						$this.openMedia( index );
					}, 1000);
				} else {
					$this.openMedia( index );
				}
			},

			/**
			 * Open
			 */
			openMedia : function ( index ) {
				var $this = this,
					src,
					slide;

				if ( elements[ index ] !== undefined ) {
					src = elements[ index ].href;
				}

				if ( index < 0 || index >= elements.length ) {
					return false;
				}

				slide = $( '#swipebox-slider .slide' ).eq( index );

				if ( ! $this.isVideo( src ) ) {
					slide.addClass( 'slide-loading' );
					$this.loadMedia( src, function() {
						slide.removeClass( 'slide-loading' );
						slide.html( this );

						if ( plugin.settings.afterMedia ) {
							plugin.settings.afterMedia( index );
						}
					} );
				} else {
					slide.html( $this.getVideo( src ) );

					if ( plugin.settings.afterMedia ) {
						plugin.settings.afterMedia( index );
					}
				}

			},

			/**
			 * Set link title attribute as caption
			 */
			setTitle : function ( index ) {
				var title = null;

				$( '#swipebox-title' ).empty();

				if ( elements[ index ] !== undefined ) {
					title = elements[ index ].title;
				}

				if ( title ) {
					$( '#swipebox-top-bar' ).show();
					$( '#swipebox-title' ).append( title );
				} else {
					$( '#swipebox-top-bar' ).hide();
				}
			},

			/**
			 * Check if the URL is a video
			 */
			isVideo : function ( src ) {

				if ( src ) {
					if ( src.match( /(youtube\.com|youtube-nocookie\.com)\/watch\?v=([a-zA-Z0-9\-_]+)/) || src.match( /vimeo\.com\/([0-9]*)/ ) || src.match( /youtu\.be\/([a-zA-Z0-9\-_]+)/ ) ) {
						return true;
					}

					if ( src.toLowerCase().indexOf( 'swipeboxvideo=1' ) >= 0 ) {

						return true;
					}
				}

			},

			/**
			 * Parse URI querystring and:
			 * - overrides value provided via dictionary
			 * - rebuild it again returning a string
			 */
			parseUri : function (uri, customData) {
				var a = document.createElement('a'),
					qs = {};

				// Decode the URI
				a.href = decodeURIComponent( uri );

				// QueryString to Object
				if ( a.search ) {
					qs = JSON.parse( '{"' + a.search.toLowerCase().replace('?','').replace(/&/g,'","').replace(/=/g,'":"') + '"}' );
				}
				
				// Extend with custom data
				if ( $.isPlainObject( customData ) ) {
					qs = $.extend( qs, customData, plugin.settings.queryStringData ); // The dev has always the final word
				}

				// Return querystring as a string
				return $
					.map( qs, function (val, key) {
						if ( val && val > '' ) {
							return encodeURIComponent( key ) + '=' + encodeURIComponent( val );
						}
					})
					.join('&');
			},

			/**
			 * Get video iframe code from URL
			 */
			getVideo : function( url ) {
				var iframe = '',
					youtubeUrl = url.match( /((?:www\.)?youtube\.com|(?:www\.)?youtube-nocookie\.com)\/watch\?v=([a-zA-Z0-9\-_]+)/ ),
					youtubeShortUrl = url.match(/(?:www\.)?youtu\.be\/([a-zA-Z0-9\-_]+)/),
					vimeoUrl = url.match( /(?:www\.)?vimeo\.com\/([0-9]*)/ ),
					qs = '';
				if ( youtubeUrl || youtubeShortUrl) {
					if ( youtubeShortUrl ) {
						youtubeUrl = youtubeShortUrl;
					}
					qs = ui.parseUri( url, {
						'autoplay' : ( plugin.settings.autoplayVideos ? '1' : '0' ),
						'v' : ''
					});
					iframe = '<iframe width="560" height="315" src="//' + youtubeUrl[1] + '/embed/' + youtubeUrl[2] + '?' + qs + '" frameborder="0" allowfullscreen></iframe>';

				} else if ( vimeoUrl ) {
					qs = ui.parseUri( url, {
						'autoplay' : ( plugin.settings.autoplayVideos ? '1' : '0' ),
						'byline' : '0',
						'portrait' : '0',
						'color': plugin.settings.vimeoColor
					});
					iframe = '<iframe width="560" height="315"  src="//player.vimeo.com/video/' + vimeoUrl[1] + '?' + qs + '" frameborder="0" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>';

				} else {
					iframe = '<iframe width="560" height="315" src="' + url + '" frameborder="0" allowfullscreen></iframe>';
				}

				return '<div class="swipebox-video-container" style="max-width:' + plugin.settings.videoMaxWidth + 'px"><div class="swipebox-video">' + iframe + '</div></div>';
			},

			/**
			 * Load image
			 */
			loadMedia : function ( src, callback ) {
                // Inline content
                if ( src.trim().indexOf('#') === 0 ) {
                    callback.call(
                    	$('<div>', {
                    		'class' : 'swipebox-inline-container'
                    	})
                    	.append(
                    		$(src)
	                    	.clone()
	                    	.toggleClass( plugin.settings.toggleClassOnLoad )
	                    )
                    );
                }
                // Everything else
                else {
    				if ( ! this.isVideo( src ) ) {
    					var img = $( '<img>' ).on( 'load', function() {
    						callback.call( img );
    					} );

    					img.attr( 'src', src );
    				}
                }
			},

			/**
			 * Get next slide
			 */
			getNext : function () {
				var $this = this,
					src,
					index = $( '#swipebox-slider .slide' ).index( $( '#swipebox-slider .slide.current' ) );
				if ( index + 1 < elements.length ) {
					src = $( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe' ).attr( 'src' );
					$( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe' ).attr( 'src', src );
					index++;
					$this.setSlide( index );
					$this.preloadMedia( index+1 );
					if ( plugin.settings.nextSlide ) {
						plugin.settings.nextSlide(index);
					}
					$this.showInfo(index);
				} else {

					if ( plugin.settings.loopAtEnd === true ) {
						src = $( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe' ).attr( 'src' );
						$( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe' ).attr( 'src', src );
						index = 0;
						$this.preloadMedia( index );
						$this.setSlide( index );
						$this.setSlide( index );
						$this.preloadMedia( index + 1 );
						if ( plugin.settings.nextSlide ) {
							plugin.settings.nextSlide(index);
						}
						$this.showInfo(index);
					} else {
						$( '#swipebox-overlay' ).addClass( 'rightSpring' );
						setTimeout( function() {
							$( '#swipebox-overlay' ).removeClass( 'rightSpring' );
						}, 500 );
					}
				}
			},

			/**
			 * Get previous slide
			 */
			getPrev : function () {
				var $this = this,
				src,
				index = $( '#swipebox-slider .slide' ).index( $( '#swipebox-slider .slide.current' ) );
				if ( index > 0 ) {
					src = $( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe').attr( 'src' );
					$( '#swipebox-slider .slide' ).eq( index ).contents().find( 'iframe' ).attr( 'src', src );
					index--;
					this.setSlide( index );
					this.preloadMedia( index-1 );
					if ( plugin.settings.prevSlide ) {
						plugin.settings.prevSlide(index);
					}
					$this.showInfo(index);
				} else {
					$( '#swipebox-overlay' ).addClass( 'leftSpring' );
					setTimeout( function() {
						$( '#swipebox-overlay' ).removeClass( 'leftSpring' );
					}, 500 );
				}
			},
			/* jshint unused:false */
			nextSlide : function ( index ) {
				// Callback for next slide
			},

			prevSlide : function ( index ) {
				// Callback for prev slide
			},
			
			/**
			 * Info
			 */
			 showInfoColumn : function () {
				var index = $( '#swipebox-slider .slide' ).index( $( '#swipebox-slider .slide.current' ) ),
				infoModal = $( '#info-modal' ),
				infoModalShow = $( '#swipebox-info' ),
				infoModalClose = $( '#info-modal-close' );
				
				if ( infoModal.hasClass( 'info-modal--hidden' ) ) {
					infoModal.removeClass( 'info-modal--hidden' );
					infoModal.addClass( 'info-modal--show' );
					
					infoModalClose.addClass( 'info-modal-close--show' ); 
					infoModalClose.removeClass( 'info-modal-close--hidden' ); 
					infoModal.addClass( 'swipebox-info--hidden' );
					this.showInfo(index);
				} else {
					infoModal.addClass( 'info-modal--hidden' );
					infoModal.removeClass( 'info-modal--show' );
				}
				
				
			},
			closeInfoColumn : function () {
				var infoModal = $( '#info-modal' ),
				infoModalClose = $( '#info-modal-close' );
				
				infoModal.removeClass( 'info-modal--show' );
				infoModal.addClass( 'info-modal--hidden' );
				
				infoModalClose.removeClass( 'info-modal-close--show' );
				infoModalClose.addClass( 'info-modal-close--hidden' ); 
			},
		
			showInfo : function (index) {
				var $this = this,
				pId,
				pPhoto,
				dataType,
				currentUser = $('#currentUser').val(),
				currentUserPhotoPresent = $('#currentUserPhotoPresent').val(),
				infotooltip = $( '#info-tooltip' );
				
				if ( elements[ index ] !== undefined ) {
					pId = elements[ index ].mediaId;
					dataType = elements[ index ].dataType;
					var urltomove = '';
					if (dataType == 'library') {
						urltomove = 'pers/mediaContent.ajx?mediaId='+pId;
					} else {
						urltomove = 'pers/mediaContent.ajx?imgId='+pId;
					}
					var innInfo = "";
					$.ajax({url:urltomove ,dataType: "json", success:function(result){
    					innInfo += "<div class='swipebox-content'>";
    					if(result.photoPresent == "Y"){
    						innInfo += "<img src='pers/userPhoto.img?id="+result.userId+"'/>";
    					}else{
    						innInfo += "<img src='img/user_icon.png'/>";
    					}
    					innInfo += "<div class='details'><div class='heading'><a href='pers/connectionResult.do?id="+result.userId+"'>"+result.ownerName+"</a></div>";
    					innInfo += "<div class='person'>"+result.created+"</div></div>"; //heading details
    					innInfo += "<div class='rlikeTopEdit' ><div id='divMed-"+pId+"' style='float: right;'>";
    					if (result.photoLikeCount > 0) {
    						innInfo += "<span class='lkCnt' style='margin-right: 40px;'>";
							if (result.photoLikeCount == 1) {
								innInfo += result.photoLikeCount + " like";
							} else {
								innInfo += result.photoLikeCount + " likes";
							}
							if (result.photoLikeAllowed){
								innInfo += " - ";
							}
							innInfo += "</span>";
    					}
						if (result.photoLikeAllowed){
    						if (result.photoLikeId > 0) {
    							innInfo += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+pId+"&#39;, &#39;0&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
    						} else {
    							innInfo += "<a href='javascript:void(0);' onclick='likeMedia(&#39;"+pId+"&#39;, &#39;0&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
    						}
    					} 
    					innInfo += "</div>"; //firstCapTop
    					
    					if ( result.editAllowed ) {
	    					var kk = escape(result.description);
	    					var tt = escape(result.title);
    						innInfo += "<div id='capTopSpan'><span class='capTop' onclick='editMediaContent(&#39;"+pId+"&#39;, &#39;"+tt+"&#39;, &#39;"+kk+"&#39;);'><i class='fa fa-pencil'></i></span></div>"; //capTopSpan
    					}
    					innInfo += "</div>"; //rlikeTop
    					
    					innInfo += "<div style='display:inline-block;' id='iii'>"; 
    					innInfo += "<div id='mediaInfo"+pId+"' class='mediaInfo'><div class='medinf'><p class='header' style='font-weight: bold;color: #2A6496;' id='medTitle"+pId+"'>"+result.title+"</p>"; //mediaInfo
    					
    					innInfo += "<div class='scroll-pane5 horizontal-only'><p style='margin-left: 10px;width: 320px;' id='medDesc'>"+result.description+"</p></div>"; // scroll-pane5
    					innInfo += "</div></div></div>"; // medinf mediaInfo iii
    					if ( currentUser > 0 ) {
    						innInfo += "<div class='addComment'>";
    						if(this['currentUserPhotoPresent'] = true){
    							innInfo += "<img src='pers/userPhoto.img?id="+currentUser+"'/>";
    						} else {
    							innInfo += "<img src='img/user_icon.png'/>";
    						}
    						innInfo += "<textarea id='itmCmt"+pId+"' placeholder='Write a comment...' cols='60' rows='4' class='editItmCmt' ";
    						innInfo += " onkeypress='return runScript(event, &#39;"+pId+"&#39;, &#39;"+result.aData.length+"&#39;,this)' ></textarea></div>"; //addComment
    					} 
    					innInfo += "<div class='cmntSec'  id='theaderComm'>";
    					if (result.aData.length > 0) {
    						innInfo += "<p class='theader'>Comments</p>";
						}
    					innInfo += "</div>";
    					innInfo += "<div id='commentArea' class='commentArea'><div class='scroll-pane4 horizontal-only'><div id='cmntInput'>";
    					var pageNumber = 0;
    					$.each(result.aData, function() {
    						innInfo += "<div class='showComment'>";
    						if(this['photoPresent'] == "Y"){
    							innInfo += "<img src='pers/userPhoto.img?id="+this['posterId']+"'/>";
	    					}else{
	    						innInfo += "<img src='img/user_icon.png'/>";
	    					}
    						innInfo += "<div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
    						innInfo += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a>"+this['datePosted'];
    						
    						innInfo += "</div><div class='incomm'>"+this['comment']+"</div>"; //showComment
    						innInfo += "<div class='heading' style='float:right;' id='divComm-"+this['id']+"'>";
    						if (this['likeCommentCount'] > 0) {
    							innInfo += "<span class='lkCnt' >"+this['likeCommentCount'];
    							if (this['likeCommentCount'] = 1) {
    								innInfo += "like - ";
    							} else {
    								innInfo += "likes - ";
    							}
							} else {
								innInfo += "<span class='lkCnt' id='divComm-"+this['id']+"'>";
							}
    						if (this['userLikeId'] > 0) {
    							innInfo += "</span><a href='javascript:void(0);' onclick='likeMedia(&#39;"+pId+"&#39;, &#39;"+this['id']+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a></div>";
    						} else {
    							innInfo += "</span><a href='javascript:void(0);' onclick='likeMedia(&#39;"+pId+"&#39;, &#39;"+this['id']+"&#39;)'  class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a></div>";
    						}
    						
    						innInfo += " </div>";//showComment
    					});
    					innInfo += "</div></div>"; //.commentArea .scroll-pane4 .swipebox-content
				        //captionLeftEl.children[0].innerHTML = innInfo; // caption (contents of figure)
    					infotooltip.html(innInfo);
    					$('.scroll-pane4').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
    				   }
    				});
					
				}
			},
			
			/**
			 * Close
			 */
			shareMedia : function () {
				var shareButtonOut = '',
				shareButtonData,
				shareURL,
				image_url,
				page_url,
				share_text,
				shareModal = $( '#share-modal' ),
				sharetooltip = $( '#share-tooltip' );
				var index = $( '#swipebox-slider .slide' ).index( $( '#swipebox-slider .slide.current' ) );
				
				if ( shareModal.hasClass( 'share-modal--hidden' ) ) {
					for(var i = 0; i < plugin.settings.shareButtons.length; i++) {
						shareButtonData = plugin.settings.shareButtons[i];
						image_url = this.getImageURLForShare(index);
						page_url = this.getPageURLForShare(index);
						share_text = this.getTextForShare(index);
						dloadUrl = this.getdownloadUrlForShare(index);

						shareURL = shareButtonData.url.replace('{{url}}', encodeURIComponent(image_url) )
											.replace('{{image_url}}', encodeURIComponent(image_url) )
											.replace('{{raw_image_url}}', dloadUrl )
											.replace('{{text}}', encodeURIComponent(share_text) );
						
						shareButtonOut += '<a href="' + shareURL + '" target="_blank" '+
											'class="share--' + shareButtonData.id + '"' +
											(shareButtonData.download ? 'download' : '') + '>' + 
											shareButtonData.label + '</a>';

					}
					shareModal.removeClass( 'share-modal--hidden' );
					sharetooltip.html(shareButtonOut);
					sharetooltip.bind( 'click', function( e ) {
						e = e || window.event;
						var target = e.target || e.srcElement;
						if(!target.href) {
							return false;
						}

						if( target.hasAttribute('download') ) {
							return true;
						}
						
						window.open(target.href, '_blank', 'scrollbars=yes,resizable=yes,toolbar=no,'+
								'location=yes,width=550,height=420,top=100,left=' + 
								(window.screen ? Math.round(screen.width / 2 - 275) : 100)  );
						shareModal.addClass( 'share-modal--hidden' );
						sharetooltip.unbind();
						return false;
					});
				} else {
					shareModal.addClass( 'share-modal--hidden' );
					sharetooltip.unbind();
				}
			},

			/**
			 * Close
			 */
			closeSlide : function () {
				$( 'html' ).removeClass( 'swipebox-html' );
				$( 'html' ).removeClass( 'swipebox-touch' );
				$( window ).trigger( 'resize' );
				this.destroy();
			},
			
			/**
			 * Destroy the whole thing
			 */
			destroy : function () {
				$( window ).unbind( 'keyup' );
				$( 'body' ).unbind( 'touchstart' );
				$( 'body' ).unbind( 'touchmove' );
				$( 'body' ).unbind( 'touchend' );
				$( '#swipebox-slider' ).unbind();
				$( '#swipebox-overlay' ).remove();

				if ( ! $.isArray( elem ) ) {
					elem.removeData( '_swipebox' );
				}

				if ( this.target ) {
					this.target.trigger( 'swipebox-destroy' );
				}

				$.swipebox.isOpen = false;

				if ( plugin.settings.afterClose ) {
					plugin.settings.afterClose();
				}
			}
		};

		plugin.init();
		
		
	};

	$.fn.swipebox = function( options ) {

		if ( ! $.data( this, '_swipebox' ) ) {
			var swipebox = new $.swipebox( this, options );
			this.data( '_swipebox', swipebox );
		}
		return this.data( '_swipebox' );

	};

}( window, document, jQuery ) );

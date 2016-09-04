		function dynamicDropDownQtip(){ //dynaDropDown
			$(".qtip").hide();
			$(document).on('mouseover', '.dynaDropDown', function(event) {
			     $(this).qtip({
		        	overwrite: true,
		            content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: api.elements.target.attr('title'),
		                        type: 'GET', // POST or GET
		                        dataType: 'json',
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
						} 
					},
					style: {
				        classes: 'qtip-light myCustomClass3'
				    },
				    hide: {
		                fixed: true,
		                delay: 300
		            },
		            show: {
		                event: event.type, 
		                ready: true 
		            }
		         }, event);
		     }).each(function(i) {
	            $.attr(this, 'oldtitle', $.attr(this, 'title'));
	        });
        }
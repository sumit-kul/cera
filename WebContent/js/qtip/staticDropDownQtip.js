function staticDropDownQtip(){
    $('.staticDropDown').each(function() {
         $(this).qtip({
        	 overwrite: false,
            content: {
                text: function(event, api) {
                    $.ajax({
                        url: api.elements.target.attr('title'),
                        type: 'GET',
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
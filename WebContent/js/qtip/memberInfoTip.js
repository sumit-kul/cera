function memberInfoQtip(){ //membList
	$(".qtip").hide();
    $('.memberInfo').each(function() {
         $(this).qtip({
        	 overwrite: true,
            content: {
        	 button: true,
                text: function(event, api) {
                    $.ajax({
                        url: api.elements.target.attr('title') // Use href attribute as URL
                    })
                    .then(function(content) {
                        api.set('content.text', content);
                    }, function(xhr, status, error) {
                        api.set('content.text', 'Loading...');
                    });
                    return 'Loading...'; // Set some initial text
                }
            },
            position: {
            	viewport: $(window),
            	my: 'top right',  // Position my top left...
                at: 'bottom right', // at the bottom right of...
                target: 'mouse', // Position it where the click was...
				adjust: { 
					mouse: false 
				} // ...but don't follow the mouse
			},
				style: {
			        classes: 'qtip-bootstrap myCustomClass'
			    },
			    hide: {
			    	target: $('a')
			    }
         });
     });
}
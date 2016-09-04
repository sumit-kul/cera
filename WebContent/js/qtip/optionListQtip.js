function optionListQtip() { //dynaDropDown
    $('.optionList').each(function() {
    	$(".qtip").hide();
        $(this).qtip({
           content: {
               text: function(event, api) {
                   $.ajax({
                       url: api.elements.target.attr('title'), // Use href attribute as URL
                       type: 'GET', // POST or GET
                       dataType: 'json', // Tell it we're retrieving JSON
                       data: {
                       }
                   })
                   .then(function(data) {
                   	var mName = "";
                   	$.each(data.aData, function() {
                   		mName += "<span>"+this['memberName']+"</span><br />";
                   	});
                   	if(data.remaining == 1){
                   		mName += "<span>and "+data.remaining+" other</span>";
                   	}
                   	if(data.remaining > 0){
                   		mName += "<span>and "+data.remaining+" others</span>";
                   	}
                       api.set('content.text', mName);
                   }, function(xhr, status, error) {
                       api.set('content.text', 'Loading...');
                   });
                   return 'Loading...'; // Set some initial text
               }
           },
           position: {
           	viewport: $(window)
			},
			style: {
		        classes: 'qtip-youtube'
		    }
        });
    });
}
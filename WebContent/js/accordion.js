window.addEvent('domready', function(){
			 
	var headings = $$('h3.atStart');
	var list = $$('div.atStart');
	var collapsibles = new Array();
	
	headings.each( function(heading, i) {
		var collapsible = new Fx.Slide(list[i], { 
			duration: 500, 
			transition: Fx.Transitions.linear
		});	
		
		collapsibles[i] = collapsible;
				
        heading.onclick = function(){
			
				if(heading.getStyle('background-image').indexOf('collapse')>0) {
					heading.setStyle('background-image','url(img/icon-link-expand-dark.gif)');
				} else {
					heading.setStyle('background-image','url(img/icon-link-collapse-dark.gif)');
				}
                collapsible.toggle();
                for(var j = 0; j < collapsibles.length; j++){
                        if(j!=i) {
							collapsibles[j].slideOut();
							headings[j].setStyle('background-image','url(img/icon-link-expand-dark.gif)');
						}
                }
                return false;
        }

        collapsible.hide();
		//open first item
        //	collapsibles[0].slideIn();
        //	headings[0].setStyle('background-image','url(img/icon-link-collapse-dark.gif)');
		
	});

}); 
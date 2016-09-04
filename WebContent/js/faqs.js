function showFAQs(max, current) {
	for (i=1; i <= max; i++) {
		faqq = document.getElementById("faq"+i+"on");
		faqa = document.getElementById("faq"+i+"off");
		
		faqq.style.display = "none";
		faqa.style.display = "inline";
	}
	document.getElementById("faq"+current+"on").style.display = "inline";
	document.getElementById("faq"+current+"off").style.display = "none";
}


function isNull(val){return(val==null);}

//-------------------------------------------------------------------
// isBlank(value)
//   Returns true if value only contains spaces
//-------------------------------------------------------------------
function isBlank(val){
	if(val==null){return true;}
	for(var i=0;i<val.length;i++) {
		if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){return false;}
		}
	return true;
	}


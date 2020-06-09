
function addRate(eventId){
		document.getElementById("form:ratingAdded").value="";

	document.getElementById("form:event_Id").value=eventId;
	console.log(eventId);
	}

	function cleanInput(){
	
	  	k=document.getElementById("form:ratingAdded").value;
	  		if(isNaN(k)){
	  			
	  			alert("rating should be a number between 0 and 5!");
	  			return true;
	  		}else{
	  			if(Number(k)>5 || Number(k)<0){
	  			  alert("rating should be a number between 0 and 5!");
	  			  return true;
	  			}
	  		}

	  		return false;
	  		
	}
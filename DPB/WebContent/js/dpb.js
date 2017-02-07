/**
 * 
 */

function dealerProgram(){
	
	
}
function populateNOFdays() {
	
	var endDate = document.getElementById("endDate").value;
	var y=endDate.split("-");
	var startDate = document.getElementById("startDate").value;
	var x=startDate.split("-");
	var stDate = new Date(x[2],x[0],x[1]);
	var edDate = new Date(y[2],y[0],y[1]);
	var calDate= (edDate-stDate)/(1000*60*60*24);
	
	document.getElementById("numberOfDays").value  =calDate+1;
	
	
}
function cancel(val) {
	
	val.form.action = ctxPath + '/dashBoard';
	val.form.submit();
}
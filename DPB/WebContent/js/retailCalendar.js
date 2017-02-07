$(document).ready(function() {
	$('a.center-screen').on('click', function(event) {
      event.preventDefault();
      $.popupWindow('viewRtlCalpopup.action', {
          width: 530,
          height: 510,
          center: 'screen'
      });
    });
	
	$('#yearsubmit').click(function() {
		var yearVal = $.trim($('#enteredYear').val());
		var flag = true;
		var regEx=/^(\d{4})$/;
		if(yearVal == '' || !yearVal.match(regEx)) {
			$('#spanYear').html("Please enter a valid year!");
			flag = false;
		} else if (yearVal < 1999) {
			$('#spanYear').html("The year must be >= 1999.");
			flag = false;
		} 
		if(!flag) {
			$('#tableRtlCal').hide();
			$('#nodata').hide();
		}
		return flag;
	});
	
});

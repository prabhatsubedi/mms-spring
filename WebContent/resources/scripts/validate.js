function validateItem(){
	var validate = true;
	var itemName = $("#itemName");
	if(itemName.val().trim() == ""){
		validate = false;
		$('#error_itemName').text('Please Enter Item Name.');
	}
	return validate;
}

function validateParty(){
	var validate = true;
	var partyName = $("#partyName");
	if(partyName.val().trim() == ""){
		validate = false;
		$('#error_partyName').text('Please Enter Party Name.');
	}
	return validate;
}

function validateCashier(){
	var validate = true;
	var cashierName = $("#cashierName");
	if(cashierName.val().trim() == ""){
		validate = false;
		$('#error_cashierName').text('Please Enter Cashier Name.');
	}
	return validate;
}

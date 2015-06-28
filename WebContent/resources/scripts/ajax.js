function addPurchasedItem(type){
	var purchaseId = $('#purchaseId').val();
	var itemId = $('#itemId').val();
	var amount = $('#amount').val();
	var batchNo = $('#batchNo').val();
	var manufacturedDate = $('#manufacturedDate').val();
	var expiryDate = $('#expiryDate').val();
	var qty = $('#qty').val();
	var rate = $('#rate').val();
	$('#error_amount').text('');
	$('#error_item').text('');
	if(itemId == "" || itemId == 'Select Item'){
		$('#error_item').text('Plesae Select Item.');
		return;
	}
	else if(batchNo == ""){
		$('#error_batchNo').text('Plesae Enter Batch No.');
		return;
	}
	else if(qty == "" || qty == 0){
		$('#error_qty').text('Plesae Enter Qty.');
		return;
	}
	else if(amount == "" || amount == 0){
		$('#error_amount').text('Plesae Enter Amount.');
		return;
	}
	
	$.ajax({
         url: "createPurchasedItems", 
         type: 'POST',
         data: {purchaseId:purchaseId,itemId:itemId,qty:qty,rate:rate,amount:amount,batchNo:batchNo,manufacturedDate:manufacturedDate,expiryDate:expiryDate,type:type},
         success: function(data){ 
        	 if(data == 1){
        		 $('#error_item').text('');
        		 $('#error_amount').text('');
        		 $('#itemId').val('Select Item');
        		 $('#amount').val('');
        		 $('#batchNo').val('');
        		 $('#manufacturedDate').val('');
        		 $('#expiryDate').val('');
        		 notify('Success', "Purchase Item Added Successfully.", 'success');
        	 }
        	 else if(data == 2){
        		 window.location.href = 'purchasedItemsList?purchaseId='+purchaseId; 
        	 }else{
        		 alert("Error...");
        	 }
         }
      });
}

function addSoldItem(type){
	var salesId = $('#salesId').val();
	var itemId = $('#itemId').val();
	var amount = $('#amount').val();
	var batchNo = $('#batchNo').val();
	var manufacturedDate = $('#manufacturedDate').val();
	var expiryDate = $('#expiryDate').val();
	var qty = $('#qty').val();
	var rate = $('#rate').val();
	$('#error_amount').text('');
	$('#error_item').text('');
	$('#error_qty').text('');
	$('#error_rate').text('');
	if(itemId == "" || itemId == 'Select Item'){
		$('#error_item').text('Plesae Select Item.');
		return;
	}
	if(qty == "" || qty == 0){
		$('#error_qty').text('Plesae Enter Qty.');
		return;
	}
	if(rate == "" || rate == 0){
		$('#error_rate').text('Plesae Enter Rate.');
		return;
	}
	if(amount == "" || amount == 0){
		$('#error_amount').text('Plesae Enter Amount.');
		return;
	}
	console.log("Ok.");
	$.ajax({
         url: "createSoldItems", 
         type: 'POST',
         data: {salesId:salesId,itemId:itemId,qty:qty,rate:rate,amount:amount,batchNo:batchNo,manufacturedDate:manufacturedDate,expiryDate:expiryDate,type:type},
         success: function(data){ 
        	 if(data == 1){
        		 $('#error_item').text('');
        		 $('#error_amount').text('');
        		 $('#itemId').val('Select Item');
        		 $('#amount').val('');
        		 $('#batchNo').val('');
        		 $('#manufacturedDate').val('');
        		 $('#expiryDate').val('');
        		 $('#qty').val('');
        		 $('#rate').val('');
        		 notify('Success', "Sales Item Added Successfully.", 'success');
        	 }
        	 else if(data == 2){
        		 window.location.href = 'soldItemsList?salesId='+salesId; 
        	 }else{
        		 alert("Error...");
        	 }
         }
      });
}

function getItemDetail(id){
	var itemName = $('#'+id+' option:selected').text();
	if(itemName == 'Select Item'){
		alert("Please Select Item");
		return
	}
	$.ajax({
		url: "ajaxPurchasedItemDetailByItem",
		type: 'POST',
		data: {itemId:$('#'+id).val()},
		success:function(data){
			console.log("Data: "+data);
			if(data != undefined && data != 'undefined' && data != null && data != ""){
				$('.batch').remove();
				$('.hiddenField').remove();
				$("#soldItemForm").append("<input id='batchNo' class='hiddenField' type='hidden' value="+data.batchNo+">");
				$("#soldItemForm").append("<input id='manufacturedDate' class='hiddenField' type='hidden' value="+data.manufacturedDate+">");
				$("#soldItemForm").append("<input id='expiryDate' class='hiddenField' type='hidden' value="+data.expiryDate+">");
				$('#qty').after("<span class='batch ui-state-error'>Batch: "+data.batchNo+"</span>");
				$('#rate').val(data.rate);
			}else{
				alert("No Stock Available !");
			}
			
		}
	});
}
function calculateAmount(qty, rate, amountId){
	$('#'+amountId).val(qty*rate);
}
function getItemDetails(id){
	var itemName = $('#'+id+' option:selected').text();
	if(itemName == 'Select Item'){
		alert("Please Select Item");
		return
	}
	$.ajax({
        url: "ajaxPurchasedItemsListByItem", 
        type: 'POST',
        data: {itemId:$('#'+id).val()},
        success: function(data){
        	
        	$("#itemDetails").html(data);
        	$("#itemDetails").dialog({
        		modal: true,
        		title: 'Item ('+itemName+') Details',
        		width: '700',
        		height: '400',
        		buttons: {
                    "Apply": function () {
                        $(this).dialog("close");
                        applySelectedItem();
                    },
                    Cancel: function () {
                    	 $(this).dialog("close");
                    }
                }
        	});
        }
     });
}

function applySelectedItem(){
	var pId = "";
	var qty = 0;
	var batch = "";
	var rate = 0;
	var mfd = "";
	var exp = "";
	$('.qtyToSale').each(function(i, obj) {
		    var id = $(this).attr('id').split("_");
		    pId = id[1];
		    console.log("id: "+id);
		    rate = id[4];
		    mfd = id[5];
		    exp = id[6];
		    
		    if($(this).val() != ""){
		    	qty = qty + parseInt($(this).val());
		    	batch = id[2];
		    }
	});
	$('#qty').val(qty);
	$('#rate').val(rate);
	$('.batch').remove();
	console.log("Mfd: "+mfd);
	$("#qty").after("<input id='batchNo' type='hidden' value="+batch+">");
	$("#qty").after("<input id='manufacturedDate' type='hidden' value="+mfd+">");
	$("#qty").after("<input id='expiryDate' type='hidden' value="+exp+">");
	$('#qty').after("<span class='batch ui-state-error'>Batch: "+batch+"</span>");
}
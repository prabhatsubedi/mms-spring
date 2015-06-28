<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class='display-results'>
	<thead>
	<tr>
		<th>Batch No.</th>
		<th>Mfd. Date</th>
		<th>Expiry Date</th>
		<th>Available Qty</th>
		<th>Rate</th>
		<th>Qty to Sale</th>
	</tr></thead>
	<!-- {"item":{"itemId":null,"itemName":"Bruffin","itemPrice":null},
        	 * "purchase":{"date":"2071-5-2","netAmount":null,"discount":null,"invoiceNo":null,"party":null,"purchaseId":null,"purchaseType":null},
        	 * "amount":444,"batchNo":null,"qty":null,"rate":null,"purchasedItemsId":6,"manufacturedDate":null,"expiryDate":null} -->
	<c:forEach var="list" items="${purchasedItemsList}" varStatus="i">
	   <tr class="${i.index%2==0?'even':'odd'}">
	   		<td>${list.batchNo}</td>
	   		<td>${list.manufacturedDate}</td>
	   		<td>${list.expiryDate}</td>
	   		<td>${list.qty}</td>
	   		<td>${list.rate}</td>
	   		<td><input id='pId_${list.stockId}_${list.batchNo}_${list.qty}_${list.rate}_${list.manufacturedDate}_${list.expiryDate}' style="width:25px;" type="text" class="qtyToSale"/></td>
	   </tr>
	</c:forEach>
</table>

<script>
	
</script>
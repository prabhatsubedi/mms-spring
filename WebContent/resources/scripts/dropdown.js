	$(document).ready(function(){
		
		$(".parent ul").css("display","none");
		
		$(".parent").hover(function(){
			$(this).find("ul").css("display","block");
			$(".child-first").find("ul").css("display","none");
			
		},function(){
			
			$(this).find("ul").css("display","none");
					
		},
		
		$(".child-first").hover(function(){
			$(this).find("ul").css("display","block");
			
			
		},function(){
			
			$(this).find("ul").css("display","none");
					
		}));
		
						
		$(".parent li ").hover(function(){
			$(this).parent().addClass("active-border");
			
		},function(){
			
			$(this).parent().removeClass("active-border");
			
		});
		
		
		$(".total").keyup(function(){
			
			var qty = $(".qty").val();
			var rate = $(".rate").val();
			$(".total").val(qty*rate);
		});
		
		
		$(".qty").keyup(function(){
			var qty = $(this).val();
			var rate = $(".rate").val();
			$(".total").val(qty*rate);
		
		});
		
		$(".rate").keyup(function(){
			
			var qty = $(".qty").val();
			var rate = $(this).val();
			$(".total").val(qty*rate);
		});
		
		$(".qty").change(function(){
			var qty = $(this).val();
			var rate = $(".rate").val();
			$(".total").val(qty*rate);
		
		});
		
		$(".rate").change(function(){
			
			var qty = $(".qty").val();
			var rate = $(this).val();
			$(".total").val(qty*rate);
		});
		
		
		$(".total").hover(function(){
			
			var qty = $(".qty").val();
			var rate = $(".rate").val();
			$(".total").val(qty*rate);
		});
		
		//-------------------
		$(".net").keyup(function(){
			var amount = $(".amount").val();
			var discount = $(".discount").val();
			$(".net").val((amount*1)-(discount*1));
		});
		
		$(".amount").keyup(function(){
			var amount = $(this).val();
			var discount = $(".discount").val();
			$(".net").val((amount*1)-(discount*1));
		});
		
		$(".discount").keyup(function(){
			var amount = $(".amount").val();
			var discount = $(this).val();
			$(".net").val((amount*1)-(discount*1));
		});
		
		$(".discount").hover(function(){
			var amount = $(".amount").val();
			var discount = $(".discount").val();
			$(".net").val((amount*1)-(discount*1));
		});
		
		$(".net").hover(function(){
			var amount = $(".amount").val();
			var discount = $(".discount").val();
			$(".net").val((amount*1)-(discount*1));
		});
		
		
				
		$(".print").click(function(){
			
			//var title="";
		    var desc=	$("#shopping_list").html();
		    
		        var popup = window.open('','popup','toolbar=no,menubar=no,width=800,height=550,scrollbars=yes'); 
		        var printtxt ="<style>table td{padding:0px; width:55px"+
		"}table{width:360px;text-align:center; font-family:geneva, arial, helvetica, 'sans-serif';font-size:9px;" +
		"border-collapse:collapse; margin-left:0px;} " +
		"th{border-top:1px solid #ddd;border-bottom:1px solid #ddd;} td.unit-print{white-space: nowrap;}</style>";
		        printtxt +="<h1 style='margin-bottom:0px;\n\
		padding-bottom:0px;'>"+"</h1>"+desc;
		        popup.document.write(printtxt); 
		        popup.document.close();
		        popup.window.print();

		});
		
		
$(".photo").click(function(){
			
			alert("test");

		});
		
		
		
	});
		
    			

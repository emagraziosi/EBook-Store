<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="root" class="container">
	    <div class="card w-30">
	        <div class="card-body">
	            <h4 class="card-title">Enter amount and click initiate PayPal checkout button</h4>
	            <h6 class="card-subtitle text-muted">This will initiate the PayPal Checkout process.</h6>
	            <form action="/orders" method="post">
	                <div class="form-group m-3">
	                    <label for="totalAmount">Amount in USD</label>
	                    <input type="text"
	                           class="form-control" name="totalAmount" id="totalAmount" aria-describedby="helpId"
	                           placeholder="100">
	                    <small id="helpId" class="form-text text-muted">Please enter an amount to initate the paypal
	                        checkout process.</small>
	                </div>
	                <button class="btn btn-warning text-bolder text-blue" type="submit">PayPal Checkout</button>
	            </form>
	        </div>
	    </div>
	
	</div>
</body>
</html>
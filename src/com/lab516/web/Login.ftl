<@Page>
	<style>
   		body {
			padding-top: 40px;
			padding-bottom: 40px;
			background-color: #f5f5f5;
		}
		body, html {
			height: 100%;
		}
		#main { 
			height: 85% 
		} 
		#main form {
			max-width: 300px;
			padding: 19px 29px 29px;
			margin: 0 auto 20px;
			background-color: #fff;
			border: 1px solid #e5e5e5;
			text-align: left;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			border-radius: 5px;
			-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
			-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
			box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
		}
		#main h2 {
			font-size: 36px;
			color: #0663a2;
			text-align: center;
			margin-bottom: 35px;
		}
		#main label {
			font-weight: bold;
			font-size: 16px;
			color: #999;
		}
		#main input {
			font-size: 16px;
			height: auto;
			width
			padding: 7px 9px;
			margin-bottom: 15px;
		}
		#main .add-on { 
			height: 22px !important;
			padding-top: 8px !important; 
		}
		#main .input-prepend input {
			height: 26px;
			width: 255px;
		}
		#btn {
			width: 100px;
			margin-top: 5px;
			margin-left: 100px;
		}
		#error {
		    display: none;
			font-size: 20px;
			font-weight: bold;
		}
	    #footer { 
	    	color: #999;
	    	padding-top: 10px;
	    	font-size: 15px; 
	    	text-align: center;
	    	border-top: 2px solid #0663A2;
	    	width: 100%;
	    }
	</style>
    <div id="main" class="container" >
    	<form method="post" action="${rc.getContextPath()}/loginsubmit.do" >
    		<h2>图书馆管理系统</h2>
    		<div id="error" class="alert alert-error" ></div>
    		<label>账&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
    		<div class="input-prepend">
		    	<div class="add-on"><i class="icon-user"></i></div>
		    	<@TextField id="user_id" value="admin" />
		    </div>
		    <label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
		    <div class="input-prepend">
		    	<div class="add-on" ><i class="icon-envelope"></i></div>
		    	<@TextField id="password" type="password" value="123456" />
		    </div>
	        <input id="btn" value="登  录" class="btn btn-large btn-primary" type="submit" />
    	</form>
    </div>
    <div id="footer" class="container" >
		Copyright © 2015 huyu. All Rights Reserved
	</div>
    <script>
    
    	var link = self.location.href; 
    
    	if(top != window) {
    		top.location.replace(link);
    	} else {
    		jQuery(document).ready(function() {
    			<#if (ex.message)?has_content >
    				jQuery("#error").html("${ex.message}").show();
    			</#if>
    			
    			<#if kickOutMsg?has_content >
    				Dialog.error("${kickOutMsg}");
    			</#if>
    		})
    	}
    	
    </script>
</@Page>

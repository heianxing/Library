<@Page>
    <style>
    	#nav {height:40px;} 
    	#header {position:static;font-weight:bold;font-size:14px;margin-bottom:0px;}
        #header .brand {font-size:26px;padding-top:8px;padding-left:12px;font-weight:bold;color:#026EA1;}
        #header .nav li {margin-bottom:5px;}
        #header .pull-right li a {color:#555 !important}
        #header .pull-right li i {margin-right:5px;}
        #error {display:none}
        #userInfo {margin-top:10px;margin-right:20px;color:grey;}
        #content {padding-left:10px;padding-top:10px;}
        #right {margin-left:205px;}
        #frm {width:100%;}
        #left {float:left;width:200px;}
        #left .accordion-group {display:none;margin-bottom:0px;}
        #left .accordion-inner {padding-bottom:0px;padding-top:5px;}
        #left i {opacity:0.4;}
        #left .active i {opacity:1;}
        #left .nosub-menu .icon-chevron-right {float:right;margin-top:2px;margin-right:-6px;}
        #left .nosub-menu .active .icon-chevron-right {opacity:1;}
      	#modal .modal-header,.modal-footer {text-align:center;}
        #modal .modal-body  {text-align:left;}
        #modal .btn-primary {margin-right:50px;}
        #modal .form-search label {width:170px;}
        #modal .form-search div {margin-top:10px;}
        #footer {color:#999;padding-top:10px;font-size:15px;text-align:center;border-top:2px solid #0663A2;width:100%;}
        #logo {width:28px;height:26px;} 
    </style>

	<!-- 修改用户密码Modal -->
	<div id="modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modalHead" aria-hidden="true" >
		<div class="modal-header" >
	    	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    	<h3>修改密码</h3>
	  	</div>
	  	<div class="modal-body" >
	  		<form class="form-search" >
		  		<div id="error" class="alert alert-error" >
		  		</div>
			  	<div>
			  		<label>新密码:</label>
			  		<input id="newPwd1" type="password" />
				</div>
				<div>
					<label>确认新密码:</label>
					<input id="newPwd2" type="password" />
				</div>
				<div>
					<label>旧密码:</label>
					<input id="oldPwd" type="password" />
				</div>
			</form>
	  	</div>
	  	<div class="modal-footer" >
	  		<input type="button" class="btn btn-primary" onclick="changePwd();" value="确  定" />
	  		<input type="button" class="btn" data-dismiss="modal" aria-hidden="true" value="取  消" />
		</div>
		<script>
			// 重置用户密码成功
			function changePwd() {
				var newPwd1 = jQuery("#newPwd1").val();
				var newPwd2 = jQuery("#newPwd2").val();
				var oldPwd  = jQuery("#oldPwd").val();
				var error = jQuery("#error");

				if(Commons.isEmpty(newPwd1) || Commons.isEmpty(newPwd2) || Commons.isEmpty(oldPwd)) {
					error.html("新密码、确认新密码、旧密码不能为空！").show();
					return;
				}

				if(newPwd1 != newPwd2) {
					error.html("两次输入的新密码不相同！").show();
					return;
				}

				jQuery.post("changepwd.do", { newPwd : newPwd1, oldPwd : oldPwd},
			    	function(result) {
			    		if(result === true) {
			    			jQuery("#newPwd1,#newPwd2,#oldPwd").val('')
			    			jQuery("#modal").modal("hide");
			    			error.hide();
			    			Dialog.success("修改密码成功。");
			    		} else {
			    			error.html("请输入正确的旧密码！").show();
			    		}
			    	});
			}
		</script>
	</div>

    <div id="header" class="navbar" >
        <div id="nav" class="navbar-inner">
            <div class="brand">
            	<img id="logo" src="${rc.contextPath}/resources/images/logo.png" />
            	图书馆管理系统
			</div>
            <ul id="ulMenu1" class="nav" >
				<#list menuList as menu1 >
					<li>
						<a href="#" onclick="showSubMenus('${menu1.menu_id}')">${menu1.menu_name}</a>
					</li>
				</#list>
            </ul>
            <ul class="nav pull-right">
            	<li id="userInfo" >
            		<i class="icon-user" ></i>
            		【${(user.user_name)!""}】
            	</li>
                <li>
                	<a href="#modal" data-toggle="modal" title="修改密码" >
                		<i class="icon-pencil" ></i>
                		修改密码
                	</a> 
                </li>
                <li>
                	<a href="logout.do" title="退出登陆" >
                		<i class="icon-indent-left" ></i>
                		退出登陆
                	</a>
                </li>
            </ul>
        </div>
    </div>

    <div id="content" >
    	<div id="left" >
        	<div id="menuAcc" class="accordion" >
				<#list menuList as menu1 >
					<#if menu1.hasSubMenus() >
						<#list menu1.subMenus as menu2 >
							<div class="accordion-group" parent_menu_id="${menu2.parent_id}">
                    			<#if !menu2.hasSubMenus() >
									<div class="accordion-heading">
										<ul class="nav nav-list nosub-menu">
											<li>
												<a class="accordion-toggle" href="javascript:void(0);" menu_url="${rc.contextPath}${menu2.menu_url}" >
													<i class="${menu2.menu_icon}" ></i>
													${menu2.menu_name}
												</a>
											</li>
										</ul>
									</div>
								<#else>
									<div class="accordion-heading">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#menuAcc" href="#subMenu${menu2_index}">
											<i class="icon-chevron-right"></i>
                            				${menu2.menu_name}
                        				</a>
									</div>
									<div id="subMenu${menu2_index}" class="accordion-body collapse">
                        				<div class="accordion-inner">
                            				<ul class="nav nav-list nosub-menu">
												<#list menu2.subMenus as menu3 >
													<li>
														<a href="javascript:void(0);" menu_url="${rc.contextPath}${menu3.menu_url}">
															<i class="${menu3.menu_icon}" ></i>
															${menu3.menu_name}
														</a>
													</li>
												</#list>
                            				</ul>
                        				</div>
                    				</div>
								</#if>
                			</div>
						</#list>
					</#if>
				</#list>
            </div>
        </div>

        <div id="right" >
        	<iframe id="frm" style="overflow:visible;" scrolling="yes" frameborder="no" ></iframe>
        </div>
    </div>

    <div id="footer" class="container" >
		Copyright © 2015 huyu. All Rights Reserved
    </div>

	<script>
	<!--
		function showSubMenus(menu_id) {
			jQuery("#menuAcc .accordion-group").each(function(index) {
				var self = jQuery(this);
				if(self.attr("parent_menu_id") == menu_id) {
					self.show();
				} else {
					self.hide();
				}
			})

			var accGroup = jQuery("#menuAcc > .accordion-group:visible:first");
			var accBody = accGroup.find(".accordion-body");
			if(accBody.length > 0) {
				accGroup.find(".accordion-heading i.icon-chevron-right").attr("class", "icon-chevron-down");
				if(accBody.attr("class").indexOf("in collapse") == -1){
					accGroup.find(".accordion-toggle").trigger("click");
				}
			}

			accGroup.find("a[menu_url]:first").trigger("click");
		}

		// 火狐不支持<a href target />的写法
		jQuery("#menuAcc a[menu_url]").click(function() {
			var menu_url = this.getAttribute("menu_url");
			jQuery("#frm").attr("src", menu_url);
		})

		// 折叠栏收起后，箭头向右；展开后，箭头向下
		jQuery("#menuAcc .accordion-body").bind({
			"shown"  : function() { jQuery(this).prev().find("i").attr("class", "icon-chevron-down");  },
			"hidden" : function() { jQuery(this).prev().find("i").attr("class", "icon-chevron-right"); }
		});

		jQuery("#menuAcc a[menu_url]").click(function() {
			// 先移除所有li的焦点样式
			jQuery("#menuAcc li").removeClass("active");
			// 只给点击li的增加焦点样式
			jQuery(this).parent().addClass("active");
		})

		jQuery("#ulMenu1 a").click(function() {
			jQuery("#ulMenu1 li").removeClass("active");
			jQuery(this).parent().addClass("active");
		})

		jQuery(document).ready(function() {
			jQuery("#ulMenu1 a:first").trigger("click");
		})

	    var h = jQuery(window).height() - jQuery("#header").height() - 50;
	    jQuery("#content,#frm").height(h);
	    
	    /*
	    var menuLis = jQuery("#ulMenu1 li");
	    if(menuLis.length == 1) {
	    	menuLis.hide();
	    }
	    */
	-->
	</script>
</@Page>

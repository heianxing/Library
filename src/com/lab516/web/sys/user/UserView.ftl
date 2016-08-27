<@Page>
	<style>
		.form-search > div {padding-left:30px;height:35px;padding-top:5px;} 
		.form-search > div > label {width:100px;}
	</style>
	<@form>
		<div>
			<label>账号:</label>
			${user.user_id}
		</div>
		<div>
			<label>姓名:</label>
			${user.user_name}
		</div>
		<div>
			<label>是否启用:</label>
			<#if user.enable >
				<font style="color:green;" >已启用</font>
			<#else>
				<font style="color:red;"   >已禁用</font>
			</#if>
		</div>
		<div>
			<label>用户角色:</label>
			${translateSome(user.roles_ids, roleList, "role_id", "role_name")}
		</div>
		<div>
			<label>邮箱:</label>
			${user.email}
		</div>
	</@form>
</@Page>
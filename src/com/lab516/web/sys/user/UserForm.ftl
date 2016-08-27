<@Page>
	<style>
		#roles_ids label {padding-right:10px;}
	</style>
	<@SmartTab name="用户" />
	
	<@form model=user_ >
		<@Hidden id="create_date" />
		<@Hidden id="email" />
		<div>
			<label>账号:</label>
			<@TextField id="user_id" readonly=isEditPage() />
		</div>
		<div>
			<label>姓名:</label>
			<@TextField id="user_name" />
		</div>
		<#if isEditPage() >
			<@Hidden id="password" />
		<#else>
			<div>
	           <label>用户密码:</label>
	           <@TextField id="password" />
	        </div>
		</#if>
		<div>
			<label>是否启用:</label>
			<@RadioGroup id="enable" model=[[true, "启用"], [false, "禁用"]] />
		</div>
		<div>
			<label>用户角色:</label>
			<@CheckBoxGroup id="roles_ids" model=roleList val="role_id" txt="role_name" />
		</div>
	</@form>

</@Page>
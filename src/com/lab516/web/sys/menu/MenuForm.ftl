<@Page>
	<@SmartTab name="菜单" />

	<@form model=menu >
		<@Hidden id="menu_id" />
		<div>
			<label>菜单名称:</label>
			<@TextField id="menu_name" />
		</div>
		<div>
			<label>菜单路径:</label>
			<@TextField id="menu_url" />
		</div>
		<div>
			<label>上级菜单:</label>
			<#assign parents = menuList + [{"menu_id":'-1', "menu_name":"无上级菜单"}] />
			<@Select id="parent_id" model=parents val="menu_id" txt="menu_name" />
		</div>
		<div>
			<label>图标:</label>
			<@TextField id="menu_icon" />
		</div>
		<div>
			<label>是否启用:</label>
			<@RadioGroup id="enable" model=[[true,"启用"], [false,"禁用"]] />
		</div>
		<div>
			<label>排序号:</label>
			<@TextField id="order_no" />
		</div>
		<div>
			<label>所属角色:</label>
			<@CheckBoxGroup id="roles_id" model=roleList
				val="role_id" txt="role_name" 
				value=getValueFromModel(menu.roles, "role_id") />
		</div>
	</@form>
</@Page>
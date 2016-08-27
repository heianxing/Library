<@Page>
	<@SmartTab name="菜单" />

	<@form>
		<div>
			<label>菜单名称:</label>
			<@TextField id="menu_name" />
			<label>菜单路径:</label>
			<@TextField id="menu_url" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="45px"  >序号</td>
			    <td width="200px" >菜单名称</td>
				<td width="250px" >菜单路径</td>
				<td width="300px" >角色</td>
				<td width="80px"  >排序号</td>
				<td width="125px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record.menu_name}" >
						${record.menu_name}
					</td>
					<td title="${record.menu_url}" >
						${record.menu_url}
					</td>
					<td>
						<@CheckBoxGroup id=record.menu_id name="menu_roles"
							model=roleList value=getValueFromModel(record.roles, "role_id")
							val="role_id" txt="role_name"
							readRequest=false />
					</td>
					<td align="right" >
						${record.order_no}
					</td>
					<td align="center" >
						<@EditLink id=record.menu_id />
						<@DelLink  id=record.menu_id tip=record.menu_name />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
	<script>
		jQuery(":checkbox[name*=menu_roles]").click(function(event){
			event.preventDefault();

			var self = this;
			var menu_id = jQuery(this).closest("table").attr("id");
			var role_id = this.value;
			var isGrantRole = this.checked;

			jQuery.post("changerole.do", { menu_id : menu_id, role_id : role_id, isGrantRole : isGrantRole },
		    	function(result) {
		    		if(result === true) {
		    			self.checked = isGrantRole;
		    		} else {
		    			Dialog.error("修改菜单角色失败！");
		    		}
		    	});
		})
	</script>
</@Page>
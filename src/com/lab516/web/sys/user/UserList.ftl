<@Page>
	<@SmartTab name="用户" />

	<@form>
		<div>
			<label>账号:</label>
			<@TextField id="user_id" />
			<label>姓名:</label>
			<@TextField id="user_name" />
			<label>启用状态:</label>
			<@Select id="enable" model=[["1","已启用"], ["0","已禁用"]] />
			<label>用户角色:</label>
			<@Select id="role_id" model=roleList val="role_id" txt="role_name"  />
			<@SearchBtn />
			<@AddBtn />
		</div>

		<@Grid>
			<tr>
				<td width="50px"  >序号</td>
				<td width="120px" >账号</td>
			    <td width="120px" >姓名</td>
			    <td width="180px" >用户角色</td>
			    <td width="70px"  >状态</td>
				<td width="340px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td align="center" >
						${record_index+1}
					</td>
					<td title="${record.user_id}" >
						${record.user_id}
					</td>
					<td title="${record.user_name}" >
						${record.user_name}
					</td>
					<#assign roles = translateSome(record.roles_ids, roleList, "role_id", "role_name") />
					<td title="${roles}" >
						${roles}
			    	</td>
					<td align="center" >
						<#if record.enable >
							<font style="color:green;" >已启用</font>
						<#else>
							<font style="color:red;"   >已禁用</font>
						</#if>
					</td>
					<td align="center" >
						<a href="javascript:void(0);" onclick="resetInitPwd('${record.user_id}');" >
							<i class="icon-share-alt"></i>
							重置密码
						</a>

						<a href="javascript:void(0);" onclick="changeEnable('${record.user_id}', this)" >
							<#if record.enable >
								<i class="icon-remove"></i>
								<font style="color:red;"   >禁用</font>
							<#else>
								<i class="icon-ok"></i>
								<font style="color:green;" >启用</font>
							</#if>
						</a>

						<@EditLink id=record.user_id />
						<@DelLink  id=record.user_id tip=record.user_name />
						<@ViewLink id=record.user_id title=record.user_name height=350 width=500 />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
	<script>
		// 重置用户密码成功
		function resetInitPwd(user_id) {
			jQuery.post("resetinitpwd.do", { user_id : user_id },
		    	function(result) {
		    		Dialog.success(result);
		    	});
		}

		// 修改用户禁启用状态
		function changeEnable(user_id, self) {
			var aLink = jQuery(self);
			var enable = aLink.html().contains("启用")

			jQuery.post("changeenable.do", { user_id : user_id, enable : enable},
		    	function(result) {
		    		if(result !== true) {
		    			Dialog.error("修改用户状态失败！");
		    			return;
		    		}

		    		var tdEnable = aLink.parent().prev();
		    		if(enable === true) {
		    			tdEnable.html('<font style="color:green;" >已启用</font>');
		    			aLink.html('<i class="icon-remove"></i><font style="color:red;" >&nbsp;禁用</font>');
		    		} else {
		    			tdEnable.html('<font style="color:red;" >已禁用</font>');
		    			aLink.html('<i class="icon-ok"></i><font style="color:green;" >&nbsp;启用</font>');
		    		}
		    	});
		}

	</script>
</@Page>
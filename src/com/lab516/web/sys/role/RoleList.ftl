<@Page>

	<@SmartTab name="角色" />

	<@form>
		<div>
			<label>角色Id:</label>
			<@TextField id="role_id" />
			<label>角色名称:</label>
			<@TextField id="role_name" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="50px"  >序号</td>
			    <td width="240px" >角色Id</td>
			    <td width="240px" >角色名称</td>
				<td width="130px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td>${record.role_id}</td>
					<td>${record.role_name}</td>
					<td align="center" >
						<@EditLink id=record.role_id />
						<@DelLink  id=record.role_id tip=record.role_name />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
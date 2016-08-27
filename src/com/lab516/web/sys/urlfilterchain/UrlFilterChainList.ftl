<@Page>
	<@SmartTab name="安全拦截器" />

	<@form>
		<div>
			<label style="width:50px" >名称:</label>
			<@TextField id="filt_name" />
			<label style="width:50px" >路径:</label>
			<@TextField id="filt_url" />
			<label>拦截器链:</label>
			<@TextField id="filt_chain" />
			<@SearchBtn />
			<@AddBtn />
		</div>

		<@Grid>
			<tr>
				<td width="50px"  >序号</td>
			    <td width="120px" >名称</td>
				<td width="250px" >路径</td>
				<td width="250px" >拦截器链</td>
				<td width="80px"  >状态</td>
				<td width="130px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record.filt_name}" >
						${record.filt_name}
					</td>
					<td title="${record.filt_url}" >
						${record.filt_url}
					</td>
					<td title="${record.filt_chain}" >
						${record.filt_chain}
					</td>
					<td align="center" >
						<#if record.enable >
							<font style="color:green;" >已启用</font>
						<#else>
							<font style="color:red;"   >已禁用</font>
						</#if>
					</td>
					<td align="center" >
						<@EditLink id=record.filt_id />
						<@DelLink  id=record.filt_id tip=record.filt_name />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
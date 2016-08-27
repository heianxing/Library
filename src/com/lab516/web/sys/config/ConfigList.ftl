<@Page>
	<@SmartTab name="配置" />

	<@form>
		<div>
			<label>配置ID:</label>
			<@TextField id="cfg_id" />
			<label>配置名称:</label>
			<@TextField id="cfg_name" />
			<label>配置值:</label>
			<@TextField id="cfg_value" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="45px"  >序号</td>
			    <td width="150px" >配置ID</td>
			    <td width="150px" >配置名称</td>
				<td width="250px" >配置值</td>
				<td width="125px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record.cfg_id}" >
						${record.cfg_id}
					</td>
					<td title="${record.cfg_name}" >
						${record.cfg_name}
					</td>
					<td title="${record.cfg_value}" >
						${record.cfg_value}
					</td>
					<td align="center" >
						<@EditLink id=record.cfg_id />
						<@DelLink  id=record.cfg_id tip=record.cfg_id />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
<@Page>
	<@SmartTab name="字典" />

	<@form>
		<div>
			<label>字典类型:</label>
			<@TextField id="dict_type" />
			<label>字典名称:</label>
			<@TextField id="dict_name" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="45px"  >序号</td>
				<td width="150px" >字典类型</td>
			    <td width="150px" >字典名称</td>
			    <td width="150px" >字典值</td>
			    <td width="80px"  >排序号</td>
				<td width="130px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record.dict_type}" >
						${record.dict_type}
					</td>
					<td title="${record.dict_name}" >
						${record.dict_name}
					</td>
					<td title="${record.dict_value}" >
						${record.dict_value}
					</td>
					<td align="right" >
						${record.order_no}
					</td>
					<td align="center" >
						<@EditLink id=record.dict_id />
						<@DelLink  id=record.dict_id tip=record.dict_name />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
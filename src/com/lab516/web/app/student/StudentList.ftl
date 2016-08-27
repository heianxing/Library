<@Page>
	<@SmartTab name="学生" />
	<@form>
		<div>
			<label>学生编号:</label>
			<@TextField id="stu_id" />
			<label>学生姓名:</label>
			<@TextField id="stu_name" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="45px"  >序号</td>
				<td width="150px" >学生编号</td>
			    <td width="80px"  >学生姓名</td>
				<td width="130px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record.stu_id}" >
						${record.stu_id}
					</td>
					<td title="${record.stu_name}" >
						${record.stu_name}
					</td>
					<td align="center" >
						<@EditLink id=record.stu_id />
						<@DelLink  id=record.stu_id tip=record.stu_name />
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
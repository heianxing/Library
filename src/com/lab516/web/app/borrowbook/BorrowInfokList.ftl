<@Page>
	<@TabTitle name="借书记录" />
	<@form>
		<div>
			<label>图书编号:</label>
			<@TextField id="book_id" />
			<label>图书名称:</label>
			<@TextField id="book_name" />
			<label>学生编号:</label>
			<@TextField id="stu_id" />
			<label>学生姓名:</label>
			<@TextField id="stu_name" />
			<@SearchBtn />
		</div>
		<@Grid>
			<tr>
				<td width="45px"  >序号</td>
				<td width="150px" >图书编号</td>
				<td width="150px" >图书名称</td>
				<td width="150px" >学生编号</td>
			    <td width="80px"  >学号姓名</td>
				<td width="60px"  >状态</td>
				<td width="100px" >借书时间</td>
				<td width="100px" >还书时间</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td>${record_index+1}</td>
					<td title="${record[0]}" >
						${record[0]}
					</td>
					<td title="${record[1]}" >
						${record[1]}
					</td>
					<td title="${record[2]}" >	
						${record[2]}
					</td>
					<td title="${record[3]}" >
						${record[3]}
					</td>
					<td align="center" >
						<#if record[4] == "1" >
							<span class="label label-success">已归还</span>
						<#else>
							<span class="label label-important">未归还</span>
						</#if>
					</td>
					<td align="center" >
						${record[5]?string("yyyy-MM-dd")}
					</td>
					<td align="center" >
						${(record[6]?string("yyyy-MM-dd"))!}
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
</@Page>
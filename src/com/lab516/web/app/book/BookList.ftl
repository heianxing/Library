<@Page>
	<style>
		.index { text-align:left !important;}
		.ctrl { width:15px;height:15px;margin-left:12px;}
		.detail { display:none; }
		.detail table { margin:5px 0px 5px 50px; }
	    .detail:hover { background:white !important; }
	</style>
	<@SmartTab name="图书" />
	<@form>
		<div>
			<label>图书编号:</label>
			<@TextField id="book_id" />
			<label>图书名称:</label>
			<@TextField id="book_name" />
			<label>图书类型:</label>
			<@Select id="book_type" model=bookTypes val="dict_value" txt="dict_name" />
			<@SearchBtn />
			<@AddBtn />
		</div>
		<@Grid>
			<tr>
				<td width="60px"  >序号</td>
				<td width="150px" >图书编号</td>
			    <td width="150px" >图书名称</td>
			    <td width="250px" >图书类型</td>
				<td width="90px"  >作者</td>
				<td width="70px"  >状态</td>
				<td width="130px" >操作</td>
			</td>
			<#list model.recordList as record >
				<tr>
					<td class="index" >
						<img class="ctrl" src="${rc.contextPath}/resources/images/plus.png" />
						${record_index+1}
					</td>
					<td title="${record.book_id}" >
						${record.book_id}
					</td>
					<td title="${record.book_name}" >
						${record.book_name}
					</td>
					<#assign bookType = translateSome(record.book_type,bookTypes,"dict_value","dict_name") />
					<td title="${bookType}">
						${bookType}
					</td>
					<td title="${record.book_author}" >
						${record.book_author}
					</td>
					<td align="center" >
						<#if record.is_borrowed >
							<span class="label label-success">已借出</span>
						<#else>
							<span class="label label-important">未借出</span>
						</#if>
					</td>
					<td align="center" >
						<@EditLink id=record.book_id />
						<@DelLink  id=record.book_id tip=record.book_name />
					</td>
				</tr>
				<tr class="detail" >
					<td colspan="8" >
						<table class="grid" >
							<tr>
							  	<td width="45px"  >序号</td>
								<td width="150px" >学生编号</td>
								<td width="60px"  >状态</td>
								<td width="100px" >借书时间</td>
								<td width="100px" >还书时间</td>
							</tr>
							<#list record.borrowInfoList as borrowInfo >
								<tr>
									<td>${borrowInfo_index+1}</td>
									<td>${borrowInfo.stu_id}</td>	
									<td align="center" >
										<#if borrowInfo.is_returned >
											<span class="label label-success">已归还</span>
										<#else>
											<span class="label label-important">未归还</span>
										</#if>
									</td>
									<td align="center" >
										${borrowInfo.borrow_time?string("yyyy-MM-dd")}
									</td>
									<td align="center" >
										${(borrowInfo.return_time?string("yyyy-MM-dd"))!}
									</td>
								</tr>
							</#list>
						</table>
					</td>
				</tr>
			</#list>
		</@Grid>
	</@form>
	<script>
	
		// 显示或隐藏借书记录
		$(".ctrl").click(function() {
			var $self = $(this);
			var src = $self.attr("src");
			var detail = $self.parents("tr:first").next();
			
			if(src.endWith("plus.png")) {
				src = src.replace("plus.png", "minus.png")
				detail.show();
				
				$(".ctrl[src$='minus.png']").trigger("click");
			} else {
				src = src.replace("minus.png", "plus.png")
				detail.hide();
			}
			
			$self.attr("src", src);
		})
		
	</script>
</@Page>
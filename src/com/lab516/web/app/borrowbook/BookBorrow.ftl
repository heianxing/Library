 <@Page pageType="edit" >
	<style>
		#spMsg {margin-left:16px}
		#spBookTip img {width:20px;height:20px;}
		#spStudentTip img {width:20px;height:20px;}
	</style>
	<@TabTitle name="图书借阅" />
	<@form>
		<#if successMsg?has_content >
			<div>
				 <span id="spMsg" class="label label-info">${successMsg}</span> 
			</div>
			<script>
				// 借书成功后清空表单
				$(function() {
					$(":input[type='text']").val("");
				})
			</script>
		</#if>
		<div>
			<label>图书编号:</label>
			<@TextField id="book_id" />
			<span id="spBookTip" />
		</div>	
		<div>	
			<label>学生编号:</label>
			<@TextField id="stu_id" />
			<span id="spStudentTip" />
		</div>
	</@form>
	<script>
	
		$(function() {
			<#assign imagePath = rc.contextPath + "/resources/images" />
		
			// 检查此图书是否可以被借出
			$("#book_id").keyup(function() {
				var $tip = $("#spBookTip");
				var book_id = this.value;
				if(Commons.isEmpty(book_id)) {
					$tip.empty();
					return;
				}
				
				$tip.html("<img src='${imagePath}/loading.gif' />检查图书编号中......");
			
				$.get("checkBorrowBookById.do", {book_id: book_id}, function(retval) {
					switch(retval) {
						case 1:$tip.html("<img src='${imagePath}/wrong.png' />此图书不存在！");break;
						case 2:$tip.html("<img src='${imagePath}/wrong.png' />此图书已被借走！");break;
						case 3:$tip.html("<img src='${imagePath}/right.png' />此图书可被借走");break;
					}
				})
			}).trigger("keyup");
			
			// 检查此学生编号是否存在
			$("#stu_id").keyup(function() {
				var $tip = $("#spStudentTip");
				var stu_id = this.value;
				if(Commons.isEmpty(stu_id)) {
					$tip.empty();
					return;
				}
				
				$tip.html("<img src='${imagePath}/loading.gif' />检查图书编号中......");
				
				$.get("isStudentExist.do", {stu_id: stu_id}, function(exist) {
					if(exist) {
						$tip.html("<img src='${imagePath}/right.png' />此学生编号正确");
					} else {
						$tip.html("<img src='${imagePath}/wrong.png' />此学生不存在！");
					}
				})
			}).trigger("keyup");
			
		})
	
	</script>
</@Page>
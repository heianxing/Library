<@Page pageType="edit" >
	<style>
		#spMsg {margin-left:16px}
		#spBookTip img {width:20px;height:20px;}
	</style>
	<@TabTitle name="图书归还" />
	<@form>
		<#if successMsg?has_content >
			<div>
				 <span id="spMsg" class="label label-info">${successMsg}</span> 
			</div>
			<script>
				// 还书成功后清空表单
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
	</@form>
	<script>
	
		$(function() {
			
			<#assign imagePath = rc.contextPath + "/resources/images" />
		
			// 检查此图书是否可以被归还
			$("#book_id").keyup(function() {
				var $tip = $("#spBookTip");
				var book_id = this.value;
				if(Commons.isEmpty(book_id)) {
					$tip.empty();
					return;
				}
				
				$tip.html("<img src='${imagePath}/loading.gif' />检查图书编号中......");
			
				$.get("checkReturnBookById.do", {book_id: book_id}, function(retval) {
					switch(retval) {
						case 1:$tip.html("<img src='${imagePath}/wrong.png' />此图书不存在！");break;
						case 2:$tip.html("<img src='${imagePath}/wrong.png' />此图书未被借走！");break;
						case 3:$tip.html("<img src='${imagePath}/right.png' />此图书可被归还");break;
					}
				})
			}).trigger("keyup");
		
		})
	
	</script>
</@Page>
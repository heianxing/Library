<@Page>
	<style>
		#spTip img {width:20px;height:20px;}
	</style>
	<@SmartTab name="图书" />
	<@form isUploadFile=true >
		<div>
			<label>图书编号:</label>
			<@TextField id="book_id" />
			<span id="spTip" />
		</div>
		<div>
			<label>图书名称:</label>
			<@TextField id="book_name" />
		</div>
		<div>
			<label>图书类型:</label>
			<@CheckBoxGroup id="book_type" model=bookTypes val="dict_value" txt="dict_name" />
		</div>
		<div>
			<label>作者:</label>
			<@TextField id="book_author" />
		</div>
		<div>
			<label>封面:</label>
			<@FileUpload id="book_img" />
		</div>
	</@form>
	<script>
		
		<#assign imagePath = rc.contextPath + "/resources/images" />
		
		// ajax验证图书编号是否可用
		$("#book_id").keyup(function() {
			var $tip = $("#spTip");
			var book_id = this.value;
			
			if(Commons.isEmpty(book_id)) {
				$tip.empty();
				return;
			}
			
			$tip.html("<img src='${imagePath}/loading.gif' />检查图书编号中......");
		
			$.get("isBookExist.do", {book_id: book_id}, function(exist) {
				if(exist) {
					$tip.html("<img src='${imagePath}/wrong.png' />此图书编号已被使用！");
				} else {
					$tip.html("<img src='${imagePath}/right.png' />此图书编号可以使用。");
				}
			})
		}).trigger("keyup");
	
	</script>
</@Page>
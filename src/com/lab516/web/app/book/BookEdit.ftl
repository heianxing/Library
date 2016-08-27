<@Page>
	<style>
		#imgBook {position:absolute;left:450px;width:120px !important;height:130px !important;}
	</style>
	<@SmartTab name="图书" />
	<@form model=book isUploadFile=true >
		<@Image  id="imgBook" file=book.book_img />
		<@Hidden id="book_img" /> 
		<@Hidden id="is_borrowed" /> 
		<div>
			<label>图书编号:</label>
			<@TextField id="book_id" readonly=true />
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
</@Page>
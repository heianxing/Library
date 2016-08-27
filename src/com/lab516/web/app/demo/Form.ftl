<@Page>
<@form>
	<style>
		.head {padding-bottom:4px;padding-top:5px;border-top:2px solid #0663A2;width:100%;}
		div {padding-left:40px;margin-top:10px;}
		p {font-weight:bold;}
	</style>

	<div class="head">
		<p>日期和时间框</p>
		<#noparse>
			<@DateField id="_dateField"  	    value=.now /></br>
			<@TimeField id="_timeField"     	value=.now /></br>
			<@DateTimeField id="_dateTimeField" value=.now /></br>
		</#noparse>
	</div>
	<div>
		<@DateField id="_dateField"  	    value=.now /></br>
		<@TimeField id="_timeField"     	value=.now /></br>
		<@DateTimeField id="_dateTimeField" value=.now /></br>
	</div>

	<div class="head">
		<p>下拉框联动, 不用再写ajax了, 已封装好了</p>
		<#noparse>
			<@Select id="menu1" isRefered="true" model=rootMenus           val=0 txt=1 blankLabel="请选择一级菜单..." /></br>
			<@Select id="menu2" referTo="menu1"  ajaxUrl="findsubmenus.do" val=0 txt=1 blankLabel="请选择二级菜单..." /></br>
			<@Select id="menu3" referTo="menu2"  ajaxUrl="findsubmenus.do" val=0 txt=1 blankLabel="请选择三级菜单..." /></br>
		</#noparse>
	</div>
	<div>
		<@Select id="menu1" isRefered="true" model=rootMenus           val=0 txt=1 blankLabel="请选择一级菜单..." />
		<@Select id="menu2" referTo="menu1"  ajaxUrl="findsubmenus.do" val=0 txt=1 blankLabel="请选择二级菜单..." />
		<@Select id="menu3" referTo="menu2"  ajaxUrl="findsubmenus.do" val=0 txt=1 blankLabel="请选择三级菜单..." />
	</div>

	<div class="head">
		<p>单选，可以控制一行显示几列</p>
		<#noparse>
			<@RadioGroup id="radioGroupFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]]  txt=1 val=0 value="apple" cols="2" />
		</#noparse>
	</div>
	<div>
		<@RadioGroup id="radioGroupFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]]  txt=1 val=0 value="apple" cols="2" />
	</div>

	<div class="head">
		<p>多选，可以控制一行显示几列</p>
		<#noparse>
			<@CheckBoxGroup id="checkBoxGroupFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]] txt=1 val=0 value="apple,pear" cols="2" />
		</#noparse>
	</div>
	<div>
		<@CheckBoxGroup id="checkBoxGroupFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]] txt=1 val=0 value="apple,pear" cols="2" />
	</div>

	<div class="head">
		<p>多选</p>
		<#noparse>
			<@Palette id="paletteFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]] txt=1 val=0 value="apple,pear" style="width:500px;" size="5" />
		</#noparse>
	</div>
	<div>
		<@Palette id="paletteFood" model=[["apple","苹果"], ["banana","香蕉"], ["pear","梨"]] txt=1 val=0 value="apple,pear" style="width:500px;" size="8" />
	</div>
	
	<div class="head">
		<p>网格，可以控制一行显示几列</p>
		<#noparse>
			<@Repeater model=['A','B','C','D','E','F','G','H','I','G'] cols=4 cellStyle="width:50px" cellAttr="align='right'"; item,item_index ></br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	${item_index}_${item?string}											</br>
			<@Repeater>																															</br>
		</#noparse>
	</div>
	<div>
		<@Repeater model=['A','B','C','D','E','F','G','H','I','G'] cols=4 cellStyle="width:50px" cellAttr="align='right'"; item,item_index >
			${item_index}_${item?string}
		</@Repeater>
	</div>

	<div class="head">
		<p>富文本编辑器, 提交的时候自动写会表单</p>
		<#noparse>
			<@RichText id="_richText" toolbar="Full" width="800px" height="200px" />
		</#noparse>
	</div>
	<div>
		<@RichText id="_richText" toolbar="Full" width="800px" height="200px" />
	</div>

	<div class="head">
		<p>文件上传</p>
		<#noparse>
			<@FileUpload id="_fileUpload" />
		</#noparse>
	</div>
	<div>
		<@FileUpload id="_fileUpload" />
	</div>
	
	<div class="head">
		<p>文本框</p>
		<#noparse>
			<@TextField id="_textField" />
		</#noparse>
	</div>
	<div>
		<@TextField id="_textField" />
	</div>

	<div class="head">
		<p>文本框</p>
		<#noparse>
			<@TextArea id="_textArea" />
		</#noparse>
	</div>
	<div>
		<@TextArea id="_textArea" />
	</div>

	<div class="head">
		<p>隐藏域</p>
		<#noparse>
			<@Hidden id="_hidden" value=.now />
		</#noparse>
	</div>
	<div>
		<@Hidden id="_hidden" value=.now />
	</div>

	<div class="head">
		<@Button name="提交" type="submit" />
	</div>
</@form>
</@Page>
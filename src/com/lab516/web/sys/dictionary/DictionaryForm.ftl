<@Page>
	<@SmartTab name="字典" />

	<@form model=dictionary >
		<@Hidden id="dict_id" />
		<div>
			<label>字典名称:</label>
			<@TextField id="dict_name" />
		</div>
		<div>
			<label>字典类型:</label>
			<@TextField id="dict_type" />
		</div>
		<div>
			<label>字典值:</label>
			<@TextField id="dict_value" />
		</div>
		<div>
			<label>排序号:</label>
			<@TextField id="order_no" />
		</div>
	</@form>
</@Page>
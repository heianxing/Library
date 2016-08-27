<@Page>	
	<style>
		#cfg_value {width:800px;height:500px;}
	</style>
	<@SmartTab name="配置" />

	<@form model=config >
		<div>
			<label>配置ID:</label>
			<@TextField id="cfg_id" readonly=isEditPage() />
		</div>
		<div>
			<label>配置名称:</label>
			<@TextField id="cfg_name" />
		</div>
		<div>
			<label>配置值:</label>
			<@TextArea id="cfg_value" />
		</div>
	</@form>
</@Page>
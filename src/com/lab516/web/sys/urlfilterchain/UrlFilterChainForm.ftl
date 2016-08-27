<@Page>
	<@SmartTab name="安全拦截器" />

	<@form model=urlFilterChain >
		<@Hidden id="filt_id" />
		<div>
			<label>名称:</label>
			<@TextField id="filt_name" />
		</div>
		<div>
			<label>路径:</label>
			<@TextField id="filt_url" />
		</div>
		<div>
			<label>拦截器链:</label>
			<@TextField id="filt_chain" />
		</div>
		<div>
			<label>是否启用:</label>
			<@RadioGroup id="enable" model=[[true, "启用"], [false, "禁用"]] />
		</div>		
	</@form>
</@Page>
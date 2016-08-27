<@Page>
	<@SmartTab name="角色" />

	<@form model=role >
		<div>
			<label>Id:</label>
			<@TextField id="role_id" readonly=isEditPage() />
		</div>
		<div>
			<label>名称:</label>
			<@TextField id="role_name" />
		</div>
	</@form>
</@Page>
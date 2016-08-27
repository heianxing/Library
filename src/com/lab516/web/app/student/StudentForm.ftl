<@Page>
	<@SmartTab name="学生" />
	<@form model=student >
		<div>
			<label>学生编号:</label>
			<@TextField id="stu_id" readonly=isEditPage() />
		</div>
		<div>
			<label>学生姓名:</label>
			<@TextField id="stu_name" />
		</div>
	</@form>
</@Page>
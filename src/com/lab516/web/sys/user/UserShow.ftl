<@Page pageType="view" >
	<style>
		.alert {font-size:20px;margin-bottom:0px;padding-bottom:0px;}
		.alert i {margin-top:5px;}
		.icon-ok {margin-top:5px;}
		.form-search > div {padding-left:30px;height:35px;padding-top:5px;} 
		.form-search > div > label {width:100px;}
	</style>
	<@Tab model=[["个人信息修改", ""]] />
	
	<@form>
		<div class="alert alert-info">
		 	<i class="icon-ok" ></i>
		 	${message}
		</div>
		<div>
			<label>账号:</label>
			${user.user_id}
		</div>
		<div>
			<label>姓名:</label>
			${user.user_name}
		</div>
		<div>
			<label>邮箱:</label>
			${user.email}
		</div>
		<div>
			<label>联系电话:</label>
			${user.phone}
		</div>
		<div>
			<label>高中:</label>
			${user.school}
		</div>
		<div>
			<label>科类:</label>
			<@DisplayScienceClass class=user.science_class />
		</div>
		<div>
			<label>诊断考试总分:</label>
			${user.diagnosed_exam_score}
		</div>
		<div>
			<label>高考总分:</label>
			${user.college_entra_exam_score}
		</div>
	</@form>
</@Page>
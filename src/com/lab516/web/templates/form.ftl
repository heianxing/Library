<#macro form id="" action="" method="post" model=[] class="form-search" showError="true" isUploadFile="false" showAction=true >
	<#assign formId = getId(id) />
	<form id="${formId}" action="${action}" method="${method}" class="${class}" novalidate="novalidate" <#if isUploadFile >enctype="multipart/form-data"</#if> >
		<#assign formModel = model />
		
		<#if showError >
			<@showBindErrors />
		</#if>

		<script>
			Validator.makeFormValidateable("${formId}")
		  	var domForm = document.forms["${formId}"];
	  		<#list validateRules![] as rule >
	  			domForm.addValidateRule("${rule.fieldName}",
	  									"${rule.fieldTipName}",
	  									"${rule.validateRule}",
	  									"${rule.errorMsg}");
	  		</#list>
		</script>

		<#nested>
		
		<#if ( pageType == "add" || pageType == "edit" ) && showAction >
			<div class="form-actions" >
				<input id="submitBtn" type="submit" value="确 定" class="btn btn-primary" />
				<input id="backBtn" type="button" value="返 回" class="btn" onclick="window.location='list.do';" />
			</div>
		</#if>

	</form>
</#macro>

<#macro showBindErrors >
	<#list entityBindErrors![] as error >
		<div class="alert alert-error" style="font-weight:bold;font-size:25px;" >
			错误：${error}
		</div>
	</#list>
</#macro>

<#macro FieldValidator fieldName getValParam errorMsgPlace getValFunc="getValueById" >
	<#if validateRules?has_content >
		<script>
			FieldValiateManager.validate("${fieldName}",
										 "${formId}",
										 "${getValFunc}",
										 "${getValParam}",
										 "${errorMsgPlace}");
		</script>
	</#if>
</#macro>

<#macro TextField id="" name="" value="" style="" type="text" attr="" disabled="false" readonly="false" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<input id="${id}" name="${name}" value="${value}" type="${type}" style="${style}" ${attr} ${resolveAttr("disabled", disabled)} ${resolveAttr("readonly", readonly)} />

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />
</#macro>

<#macro FileUpload id="" name="" style="" attr="" fileName="" url="">
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<input id="${id}" name="${name}" type="file" style="${style}" ${attr} />

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />

	<#if fileName?has_content >
		<#local hidId = id + "_hid" />

		<input id="${hidId}" type="hidden" name="${fileName}"  value="${url}"/>

		<@FieldValidator fieldName=fileName getValParam=hidId errorMsgPlace=id />

		<script>
			jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
				var fileFullName = jQuery("#${id}").val();
				var fileSimpleName = Template.getFileName(fileFullName);
				jQuery("#${hidId}").val(fileSimpleName);
			});
		</script>
	</#if>
</#macro>

<#macro TextArea id="" name="" value="" style="" attr="" disabled="false" readonly="false" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<textarea id="${id}" name="${name}" style="${style}" ${attr} ${resolveAttr("disabled", disabled)} ${resolveAttr("readonly", readonly)} >${value}</textarea>

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />
</#macro>

<#macro Hidden id="" name="" value="" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<#if value?has_content && value?is_date >
		<#local value = value?string("yyyy-MM-dd hh:mm:ss") />
	</#if>
	<input id="${id}" name="${name}" value="${value}" type="hidden" />
</#macro>

<#macro DateField id="" name="" value="" style="" attr="" disabled="false" readonly="false" readRequest=true>
	<@RawDateTimeField id=id dateFormat="yyyy-MM-dd" name=name value=value style=style attr=attr disabled=disabled readonly=readonly readRequest=readRequest />
</#macro>

<#macro TimeField id="" name="" value="" style="" attr="" disabled="false" readonly="false" readRequest=true >
	<@RawDateTimeField id=id dateFormat="HH:mm:ss" name=name value=value style=style attr=attr disabled=disabled readonly=readonly readRequest=readRequest />
</#macro>

<#macro DateTimeField id="" name="" value="" style="" attr="" disabled="false" readonly="false" readRequest=true >
	<@RawDateTimeField id=id dateFormat="yyyy-MM-dd HH:mm:ss" name=name value=value style=style attr=attr disabled=disabled readonly=readonly readRequest=readRequest />
</#macro>

<#macro RawDateTimeField id dateFormat name value style attr disabled readonly readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<#if value?is_date >
		<#local value = value?string(dateFormat) />
	</#if>

	<input id="${id}" name="${name}" value="${value}" onfocus="WdatePicker({skin:'blue',dateFmt:'${dateFormat}'})" type="text" style="${style}" class="Wdate" ${attr} ${resolveAttr("disabled", disabled)} ${resolveAttr("readonly", readonly)} />

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />
</#macro>

<#macro Select val=0 txt=1 model=[] id=""  name="" value=""  blankLabel="请选择..." blankValue="" attr="" style=""
	    disabled="false" readonly="false" referTo="" ajaxUrl="" isRefered=false textName="" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<select id="${id}" name="${name}" style="${style}" ${attr} >
		<#if blankLabel?has_content || blankValue?has_content >
			<option value="${blankValue}" >${blankLabel}</option>
		</#if>
		<#list model as optItem >
			<option value="${optItem[val]}"
				<#if optItem[val] == value >selected="selected"</#if>
			>
				${optItem[txt]}
			</option>
		</#list>
	</select>

	<#if textName?has_content >
		<#local hidId = id + "_hid" />
		<input id="${hidId}" type="hidden" name="${textName}" />
		<script>
			jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
				var text = Commons.getSelectedOptionText("${id}");
				jQuery("#${hidId}").val(text);
			});
		</script>
	</#if>

	<#if isRefered >
		<script>
			jQuery(document).ready(function(){
				jQuery("#${id}").triggerHandler("change");
			});
		</script>
	</#if> 

	<#if referTo?has_content >
		<script>
			jQuery("#${id}").data(Consts.SELECT_INIT_VALUE, "${value}");
			jQuery("#${referTo}").change(function(){
				Template.selectLinkage("${id}", "${referTo}", "${ajaxUrl}", "${txt}", "${val}", "${blankLabel}", "${blankValue}");
			});
		</script>
	</#if>

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />
</#macro>

<#-- value="${value?string}" -->
<#macro Radio value name id="" checked="false" style="" attr="" readonly=false >	
	<input id="${id}" name="${name}" type="radio" style="${style}" value="${value?string}" ${resolveAttr("checked",checked)} <#if readonly>onclick="return false;"</#if> />
</#macro>

<#macro CheckBox value name id="" checked="false" style="" attr="" readonly=false >
	<input id="${id}" name="${name}" type="checkbox" style="${style}" value="${value}" ${resolveAttr("checked",checked)} <#if readonly>onclick="return false;"</#if> />
</#macro>

<#macro RadioGroup model val=0 txt=1 id="" name="" value="" cols="" cellStyle="" cellAttr="" gridStyle="" gridAttr="" readRequest=true readonly=false >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
  	<#local radioGroupVal = getFieldValue(name, value, readRequest) />
  	<#local rdName = id + "_" + name />
  	<#local hidId = id + "_hid" />
  	
	<@Repeater gridId=id model=model cols=cols
			   cellStyle=cellStyle cellAttr=cellAttr gridStyle=gridStyle gridAttr=gridAttr gridId=id
			   ; item, item_index >
		<#local radioChecked = (radioGroupVal?string == item[val]?string) />
	    <@Radio id=name+item_index name=rdName value=item[val] checked=radioChecked readonly=readonly />
	    <label for="${name+item_index}" >${item[txt]}</label>
	</@Repeater>
	<input id="${hidId}" type="hidden" name="${name}" />
    <script>
		jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
			var value =	Commons.getRadioValue("${rdName}", "${formId}");
			jQuery("#${hidId}").val(value);
		});
	</script>

	<@FieldValidator fieldName=name getValParam=hidId errorMsgPlace=id />
</#macro>

<#macro CheckBoxGroup model val=0 txt=1 id="" name="" value="" cols="" cellStyle="" cellAttr="" gridStyle="" gridAttr="" readRequest=true readonly=false >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local checkBoxGroupVal = getFieldValue(name, value, readRequest) />
	<#local cbName = id + "_" + name />
	<#local hidId = id + "_hid" />

	<@Repeater model=model cols=cols
			   cellStyle=cellStyle cellAttr=cellAttr gridStyle=gridStyle gridAttr=gridAttr gridId=id
		 	   ; item, item_index >
		<#local checkBoxChecked = isConstains(checkBoxGroupVal, item[val]) />
	    <@CheckBox id=name+item_index name=cbName value=item[val] checked=checkBoxChecked readonly=readonly />
	    <label for="${name+item_index}" >${item[txt]}</label>
	</@Repeater>
	<input id="${hidId}" type="hidden" name="${name}" />
	<script>
		jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
			var value =	Commons.getCheckBoxValue("${cbName}", "${formId}");
			jQuery("#${hidId}").val(value);
		});
	</script>

	<@FieldValidator fieldName=name getValParam=hidId errorMsgPlace=id />
</#macro>

<#macro Repeater model cols="" cellStyle="" cellAttr="" gridStyle="" gridAttr="" gridId="" >
	<#if cols == "">
		<#local cols = (model?size) />
	<#else>
		<#local cols = (cols?number) />
	</#if>

	<table id="${gridId}" cellspacing="0" cellpadding="0" class="repeater" style="display:inline-block;${gridStyle}" ${gridAttr} >
		<#list model?chunk(cols, "") as row>
			<tr>
				<#list row as item>
				 	<td style="${cellStyle}" ${cellAttr} >
				 		<#if item?has_content >
				 	 		<#nested item,item_index >
				 	 	</#if>
				 	</td>
				</#list>
			</tr>
		</#list>
	</table>
</#macro>

<#macro Palette model txt val id="" name="" value="" style="" size="10" fromTip="待选择" toTip="已选择" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<#local hidId = id + "_hid" />
	<#local selectFrom = id + "_from" />
	<#local selectTo = id + "_to" />

	<table id="${id}" class="palette" style="${style}" >
		<tr>
			<td width="45%" >
				<label>${fromTip}</label>
				<select id="selectForm" size="${size}" multiple="multiple" ondblclick="Template.moveSelectedOptions('selectForm', 'selectTo');" >
					<#list model as item>
						<#if !isConstains(value, item[val]) >
							<option value="${item[val]}">${item[txt]}</option>
						</#if>
					</#list>
				</select>
			</td>
			<td width="10%" >
				<input type="button" style="background-image:url(${rc.getContextPath()}/resources/images/arrow_right.jpg);"  onclick="Template.moveSelectedOptions('selectForm', 'selectTo');" /></br>
				<input type="button" style="background-image:url(${rc.getContextPath()}/resources/images/arrow_left.jpg);"   onclick="Template.moveSelectedOptions('selectTo', 'selectForm');" /></br>
				<input type="button" style="background-image:url(${rc.getContextPath()}/resources/images/arrow_right2.jpg);" onclick="Template.moveAllOptions('selectForm', 'selectTo');" /></br>
				<input type="button" style="background-image:url(${rc.getContextPath()}/resources/images/arrow_left2.jpg);"  onclick="Template.moveAllOptions('selectTo', 'selectForm');" /></br>
			</td>
			<td width="45%" >
				<label>${toTip}</label>
				<select id="selectTo" size="${size}" multiple="multiple" ondblclick="Template.moveSelectedOptions('selectTo', 'selectForm');" >
					<#list model as item>
						<#if isConstains(value, item[val]) >
							<option value="${item[val]}">${item[txt]}</option>
						</#if>
					</#list>
				</select>
				<input id="${hidId}" name="${name}" value="${value}" type="hidden" />
			</td>
		</tr>
	</table>

	<script>
		jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
			var value = Template.getSelectOptionsValue('selectTo');
			jQuery("#${hidId}").val(value);
		});
	</script>

	<@FieldValidator fieldName=name getValParam=hidId errorMsgPlace=selectTo />
</#macro>

<#--
	// Full Basic Empty 如果要更多配置可以修改ckeditor下的config.js
	// ${id}.getData()
-->
<#macro RichText id="" name="" value="" height="100%" width="100%" readonly="false" toolbar="Basic" readRequest=true >
	<#local id = getId(id) />
	<#local name = getFieldName(id, name) />
	<#local value = getFieldValue(name, value, readRequest) />
	<textarea id="${id}" name="${name}">${value}</textarea>
	<script>
		Commons.loadJsLibOnce("${rc.getContextPath()}/resources/js/ckeditor/ckeditor.js")

		// 在表单提交之前
		jQuery("form").on(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT, function() {
			var richText = CKEDITOR.instances["${id}"];
			if(richText) {
         		richText.updateElement(); // 将富文本编辑器内容写会表单
         	}
		});

		// 绑定document的ready会报错
		jQuery(window).load(function(){
			richtext = CKEDITOR.replace('${id}', { toolbar : "${toolbar}" })

			richtext.config.height = "${height}";
			richtext.config.width = "${width}";

			<#if readonly >
				richtext.on("instanceReady", function (){  this.setReadOnly( ${readonly} ); })
			</#if>
		 })
	</script>

	<@FieldValidator fieldName=name getValParam=id errorMsgPlace=id />
</#macro>

<#-- 目前只能是只读的 -->
<#macro StarRating score id="" number=5 hints=['很差', '较差', '一半', '较好', '非常好'] >
	<#local id = getId(id) />
	<div id="${id}" style="display:inline;"></div>&nbsp;${score}
	<script>
		Commons.loadJsLibOnce("${rc.getContextPath()}/resources/js/raty/jquery.raty.js");
		Commons.loadCssLibOnce("${rc.getContextPath()}/resources/js/raty/jquery.raty.css");

		jQuery(document).ready(function() {
			var hints = [];
			<#list hints as item >
				hints.push("${item}");
			</#list>

			$('#${id}').raty({score    : ${score},
			                  number   : ${number},
							  hints    : hints,
							  halfShow : true,
							  readOnly : true });
		})
	</script>
</#macro>





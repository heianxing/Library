<#macro Grid model=page id="" showPager="true" style="" showBlankRow=false >
	<#local id = getId(id) />
	<#assign model=model />
	<table id="${id}" class="grid" cellspacing="0" cellpadding="0" style="${style}" >
		<#nested>
	</table>
	<#assign model=null />
	<script>
	<!--
		jQuery(document).ready(function(){
			var grid = jQuery("#${id}");
			var cols = grid.find("tr:first > td").length;
			<#if showBlankRow >
				var rows = ${model.pageSize} - ${model.recordList?size};
			<#elseif !model.recordList?has_content >
				var rows = 1;
			<#else>
				var rows = 0;
			</#if>
			
			var blankRowHTML = "";
			for(var i = 0; i < rows; i++) {
				blankRowHTML += "<tr>"
				for(var j = 0; j < cols; j++) {
					blankRowHTML += "<td></td>"
				}
				blankRowHTML += "</tr>"
			}

			grid.append(blankRowHTML);
		});
	-->
	</script>
	<#if showPager >
		<@Pager model=model />
	</#if>
</#macro>

<#macro Pager model id="pager" >
	<div class="pager" id="${id}" >
	    <div class="left" >
			每页${model.pageSize}条,共${model.recordCount}条
		</div>
		<div class="right" >
			<span></span>
			<image src="${rc.getContextPath()}/resources/images/grid/first.gif" onclick="Template.goToPage(1);" />
			<image src="${rc.getContextPath()}/resources/images/grid/prev.gif"  <#if model.hasPrevPage() >onclick="Template.goToPage(${model.prevPage});"</#if> />
			<input id="${id}_page_no" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" value="${model.pageNo}" />/${model.pageCount}
			<span></span>
		    <image src="${rc.getContextPath()}/resources/images/grid/next.gif"  <#if model.hasNextPage() >onclick="Template.goToPage(${model.nextPage});"</#if> />
			<image src="${rc.getContextPath()}/resources/images/grid/last.gif"  onclick="Template.goToPage(${model.pageCount});" />
			<span></span>
			<image src="${rc.getContextPath()}/resources/images/grid/load.png"  onclick="Template.goToPage(jQuery('#${id}_page_no').val());" />
		</div>
	</div>
	<script>
		jQuery(document).ready(function(){
			var w = jQuery(".grid").width();
			jQuery("#${id}").width(w);
		})
	</script>
</#macro>

<#macro Tab model pageIndex=0 srcProp=1 nameProp=0 >
	<ul id="ulTabs" class="nav nav-tabs" style="margin-bottom:5px" >
		<#list model as menuItem >
			<#if menuItem_index == pageIndex >
				<#local class = "active" />
				<#local href = "#" />
		  	<#else>
		  		<#local class = "" />
				<#local href = "${menuItem[srcProp]}" />
			</#if>

			<li class="${class}" >
		    	<a href="${href}">${menuItem[nameProp]}</a>
		  	</li>
		</#list>
	</ul>
</#macro>

<#macro TabTitle name >
	<ul id="ulTabs" class="nav nav-tabs" style="margin-bottom:5px" >
		<li class="active" >
			<a href="#">${name}</a>
		</li>
	</ul>
</#macro>

<#macro SmartTab name pageType=pageType >
	<#local model = [[name + "列表", "list.do"], [name + "新增", "add.do"]] />
	<#if pageType == "list" >
		<#local pageIndex = 0 />
	</#if>
	<#if pageType == "add"  >
		<#local pageIndex = 1 />
	</#if>
	<#if pageType == "edit" >
		<#local model = model + [[name + "修改", "edit.do"]] />
		<#local pageIndex = 2 />
	</#if>
	<@Tab model=model pageIndex=pageIndex />
</#macro>

<#macro Page title="" pageType=pageType >
<#assign pageType = pageType />
<!DOCTYPE html>
<html lang="cn" >
    <head>
        <title>${title}</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <!-- 解决浏览器兼容模式问题    -->
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,Chrome=1">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <link rel="shortcut icon" href="${rc.contextPath}/resources/images/pagelogo.png" type="image/x-icon" />
        <link rel="stylesheet" href="${rc.getContextPath()}/resources/js/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" href="${rc.getContextPath()}/resources/js/bootstrap/css/bootstrap-responsive.css" />
        <link rel="stylesheet" href="${rc.getContextPath()}/resources/css/base.css?ver=${getVersion()}" />
        <!--[if lt IE 9]>
  			<script src="${rc.getContextPath()}/resources/js/html5/html5shiv.min.js"></script>
 			<script src="${rc.getContextPath()}/resources/js/html5/respond.min.js"></script>
		<![endif]--> 
		<script src="${rc.getContextPath()}/resources/js/jQuery/jquery-1.10.2.min.js" ></script>
		<script src="${rc.getContextPath()}/resources/js/jquery.mask.js?ver=${getVersion()}" ></script>
	 	<script src="${rc.getContextPath()}/resources/js/base.js?ver=${getVersion()}" ></script>
		<script src="${rc.getContextPath()}/resources/js/DatePicker/WdatePicker.js" ></script>
	</head>
	<style>
		<#if pageType == "list" >
			.form-search > div { white-space:nowrap;padding-top:5px;padding-bottom:5px;background-color:#f5f5f5; }
			input[type="text"] { width:90px }
			select { width:105px }
			input.btn { margin-left:10px }
		</#if>
		<#if pageType == "add" || pageType == "edit" >
			.form-search > div { white-space:nowrap;padding-top:5px;padding-bottom:5px;padding-left:80px;border-bottom:1px dotted #f1f1f1; }
			input[type="text"] { width:170px }
			input[type="file"] { width:185px }
			select { width:185px }
			textarea { width:300px;height:200px }
			.form-actions .btn { margin-left:67px }
			.form-actions .btn:first-child { margin-left:83px }
		</#if>
		<#if pageType == "view">
			.form-search > div { white-space:nowrap;padding-left:20px;border-bottom:1px dotted #f1f1f1; }
		</#if>
		.form-search > div > label { text-align:right;margin-top:5px;margin-right:5px;width:75px; }
    </style>
<body>
	<#nested>
	<script src="${rc.getContextPath()}/resources/js/bootstrap/js/bootstrap.js" ></script>
	<#if DialogMsg?has_content >
		<script>
			Dialog.${DialogType}("${DialogMsg}");
		</script>
	</#if>
</body>
</html>
</#macro>

<#--
	主要 btn btn-primary	提供额外的视觉感, 可在一系列的按钮中指出主要操作
	信息	btn btn-info	默认样式的替代样式
	成功	btn btn-success	表示成功或积极的动作
	警告	btn btn-warning	提醒应该谨慎采取这个动作
	危险	btn btn-danger	表示这个动作危险或存在危险
	反向	btn btn-inverse	备用的暗灰色按钮，不依赖于语义和用途
	链接	btn btn-link	简化一个按钮, 使它看起来像一个链接，同时保持按钮的行为
-->
<#macro Button name type="button" class="btn-primary" id="" onclick="" goToPage="" style="" >
	<#if goToPage?has_content >
		<#local onclick = "window.location='${goToPage}'" />
	</#if>
	<input id="${id}" type="${type}" value="${name}" class="btn ${class}" style="${style}" onclick="${onclick}" />
</#macro>

<#macro SearchBtn name="查 询" onclick="" style="" id="" >
	<input id="${id}" type="submit" value="${name}" class="btn btn-primary" style="${style}" onclick="${onclick}" />
</#macro>

<#macro SubmitBtn name="确 定" onclick="" style="" id="" >
	<input id="${id}" type="submit" value="${name}" class="btn btn-primary" style="${style}" onclick="${onclick}" />
</#macro>

<#macro BackBtn name="返 回" url="list.do" style="" id="" >
	<input id="${id}" type="button" value="${name}" class="btn" style="${style}" onclick="window.location='${url}'" />
</#macro>

<#macro AddBtn name="新 增" style="" url="add.do" id="" >
	<input id="${id}" type="button" value="${name}" class="btn btn-primary" style="${style}" onclick="window.location='${url}'" />
</#macro>

<#macro EditLink id="" >
	<a href="edit.do?id=${id}" >
		<i class="icon-edit"></i>
		修改
	</a>
</#macro>

<#macro DelLink tip id="" name="删除" url="delete.do" >
	<a href="javascript:void(0);" onclick="Page.del('${url}','${id}','确定要删除【${tip}】吗?');">
		<i class="icon-trash"></i>
		${name}
	</a>
</#macro>

<#macro ViewLink title height=450 width=450 id="" url="view.do" >
	<a href="javascript:void(0);" onclick="Dialog.showPage('${url}?id=${id}','${title}', ${height}, ${width});" >
		<i class="icon-eye-open"></i>
		查看
	</a>
</#macro>

<#macro ViewImage attcId >
	<a id="${attcId}" href="javascript:void(0);" >                     
		<i class="icon-eye-open"></i>                     
		查看图片   
	</a>   
	<script>
		jQuery(document).ready(function() {
			Template.viewImage("#${attcId}", "${attcId}")
		})
	</script>
</#macro>

<#macro Download file >
	<#if file?has_content>
		<a href="${rc.contextPath}/app/upload/download.do?fileName=${file?url}" >                     
	    	<i class="icon-download-alt"></i>                     
	                 下载         
	    </a>     
	<#else>
		<#local id = getSeed() />
		<a id="${id}" href="javascript:void(0)" data-original-title="用户没上传该文件，无法下载" style="color:grey" data-toggle="tooltip" data-placement="top" >                        
        	<i class="icon-remove"></i>
                          下载
        </a>
        <script>
        	jQuery(document).ready(function() {
        		$("#${id}").tooltip();
        	})
        </script>
    </#if>
</#macro>

<#macro Image file id="" >
	<#if file?has_content>
		<image id=${id} class="img-polaroid" style="width:120px;height:120px;" src="${rc.contextPath}/app/upload/display.do?fileName=${file?url}" />
	<#else>
		<image id=${id} class="img-polaroid" style="width:120px;height:120px;" src="${rc.contextPath}/resources/images/noimage.png" />
    </#if>
</#macro>

<#macro Pdf file > 
	<#if file?has_content && file?lower_case?ends_with('pdf') >
		<a href="javascript:void(0);" onclick="viewPdf(this, '${rc.contextPath}/app/upload/display.do?fileName=${file?url}')" >                     
	    	<i class="icon-eye-open"></i>                     
	                 浏览   
	    </a>    
	    <script>
	    	
	    	function viewPdf(linkObj, pdfUrl) {
	    		var jqSelf = jQuery(linkObj);
				if(jqSelf.html().contains("浏览")) {
					// 浏览
					jqSelf.html('<i class="icon-eye-close"></i>&nbsp;取消');
					
					if(!jqSelf.next().is("iframe")) {
						jqSelf.after('<iframe src="' + pdfUrl + '" style="display:block;margin-left:130px;width:700px;height:500px" ></iframe>');
					} else {
						jqSelf.next().show();
					}
				} else {
					// 取消浏览
					jqSelf.html('<i class="icon-eye-open"></i>&nbsp;浏览')
					jqSelf.next().hide()
				} 
	    	}

	    </script>    
	<#else>
		<#local id = getSeed() />
		<#local error = file?has_content?string("不是pdf文件，无法浏览", "用户没上传该文件，无法浏览") />
		<a id="${id}" href="javascript:void(0)" data-original-title="${error}" style="color:grey" data-toggle="tooltip" data-placement="top" >                        
        	<i class="icon-remove"></i>
                          浏览
        </a>
        <script>
        	jQuery(document).ready(function() {
        		$("#${id}").tooltip();
        	})
        </script>
    </#if>
</#macro>
						





String.prototype.contains
	= function(str) {
			return (this.indexOf(str) > -1);
		}
	
String.prototype.substringBetween
	= function(startStr, endStr) {
			var startIndex = this.indexOf(startStr);
			var endIndex = this.lastIndexOf(endStr);

			if(startIndex != -1 && endIndex!= -1)
			    return this.substring(startIndex + 1, endIndex);
			else
			    return null;
		}

String.prototype.substringBefore
	= function(beforeStr) {
			var endIndex = this.indexOf(beforeStr);
			if (endIndex != -1)
				return this.substring(0, endIndex);
			else
				return this;
		}

String.prototype.substringAfter
	= function(afterStr) {
			var startIndex = this.lastIndexOf(afterStr);
			if (startIndex != -1)
				return this.substring(startIndex + afterStr.length);
			else
				return this;
		}

String.prototype.lowerFirstChar
	= function() {
			return this.charAt(0).toLowerCase() + this.substring(1);
		}

String.prototype.removeEnd
	= function(c) {
    		var lastChar = this.charAt(this.length - 1);
    		if (lastChar == c)
    			return this.substring(0, this.length - 1)
    		else
    			this;
		}

String.prototype.endWith = function(s) {
    var reg = new RegExp(s + "$");
    return reg.test(this);
}

String.prototype.replaceAll
	= function(s1, s2) {
	        return this.replace(new RegExp(s1,"gm"), s2);
	    }

String.prototype.trim
	= function() {
        	return this.replaceAll(" ", "");
    	}

// 常量
var Consts = new Object();
	// 表单提交前触发的事件，通知某些复杂的表单宏是将数据写回到隐藏域
	Consts.FORM_PREPARE_FOR_SUBMIT_EVENT = "formPrepareForSubmitEvent";
	// 表单数据校验事件，通知表单中的宏进行数据校验，如果有错误，会弹出浮动提示框
	Consts.FORM_VALIDATE_EVENT = "formValidateEvent";
	// 表单提交前触发事件
	Consts.FORM_BEFORE_SUBMIT_EVENT = "formBeforeSubmitEvent";
	// 用于绑定页面加载后表单宏Select的初始值
	Consts.SELECT_INIT_VALUE = "selectInitValue";
	// 全角逗号
	Consts.SBC_SPLIT = "___";
	// 半角逗号
	Consts.DBC_SPLIT = ","; 
	// 左空格
	Consts.L_BRACE = "(";
	// 右空格
	Consts.R_BRACE = ")";
	// 页号
	Consts.PAGE_NO = "page_no";
	// 项目名称
	Consts.CONTEXT_PATH = "/Library";

var Validator = new Object();

	Validator.validateRules
		= function(fieldVal, rulesStr) {
				var ruleStrArr = rulesStr.split(Consts.SBC_SPLIT);
				for(var i = 0; i < ruleStrArr.length; i++) {
					var ruleStr = ruleStrArr[i];
					if(!this.validateSingleRule(fieldVal, ruleStr))
						return false;
				}
				return true;
			}

	Validator.validateSingleRule
		= function(fieldVal, ruleStr) {
				var ruleName = ruleStr.substringBefore(Consts.L_BRACE);
				var funcName = ruleName.lowerFirstChar();
				var ruleParams = this.getRuleParams(fieldVal, ruleStr)
				
				try{
					return this[funcName].apply(null, ruleParams);
				} catch (e)   {
				    alert(e.name + " " +  e.message + " funcName[" + funcName + "] ruleParams[" + ruleParams.join(",") + "]");
				    return null;
				}
			}

	Validator.getRuleParams
		= function(fieldVal, ruleStr) {
				var allParams = [ fieldVal ];
				var paramsStr = ruleStr.substringBetween(Consts.L_BRACE, Consts.R_BRACE);
				var params = (paramsStr == null)? [] : paramsStr.split(Consts.DBC_SPLIT);

				return allParams.concat(params);
			}
	
	Validator.email
		= function(val) {
			  	var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			    return Commons.isEmpty(val) ? true : regex.test(val);
			}
	
	Validator.max
		= function(val, maxVal) {
				return Commons.isEmpty(val) ? true : ( val * 1 <= maxVal );
			}

	Validator.require
		= function(val) {
				return !Commons.isEmpty(val);
			}

	Validator.numFormat
		= function(val, posLen, negLen) {
				if(Commons.isEmpty(val))
					return true;

				var regex = new RegExp('^[-+]?\\d{1,' + posLen+ '}(\\.\\d{0,' + negLen + '})?$');
				return regex.test(val);
			}

	Validator.strLen
		= function(val, minLen, maxLen) {
				if(Commons.isEmpty(val))
					return true;

				return (minLen <= val.length && val.length <= maxLen);
			}

	Validator.fileTypes
		= function(val, fileTypes) {
				if(Commons.isEmpty(val))
					return true;

				var fileName = val.toLowerCase();
				var fileTypeArr = fileTypes.toLowerCase().split("|");
				for(var i = 0; i < fileTypeArr.length; i++) {
					var fileSuffix = "." + fileTypeArr[i];
					if(fileName.endWith(fileSuffix)) {
						return true;
					}
				}
				return false;
			}

	Validator.fileMaxSize
		= function() {
				return true; // js无法获取文件大小, 所以前台不对文件大小做校验, 对文件大小的校验依赖于后台
			}

	// 和Form相关
	Validator.makeFormValidateable
		= function(formId) {
				var domForm = document.forms[formId];

				// 表单验证错误
				domForm._validateErrors = [];
				domForm.addValidateErrors
					= function(error) {
							this._validateErrors.push(error);
						}
				domForm.cleanValidateErrors
					= function() {
							this._validateErrors.length = 0;
						}
			  	domForm.hasValidateErrors
			  		= function() {
							return ( this._validateErrors.length != 0 );
			  			}

			  	// 表单的验证规则
			  	domForm._validateRules = {};
			  	domForm.addValidateRule
			  		= function(fieldName, fieldTipName, validateRule, errorMsg) {
							this._validateRules[fieldName] = { fieldTipName : fieldTipName,
													     	   validateRule : validateRule,
														       errorMsg     : errorMsg  }
			  			}
			  	domForm.getValidateRuleByName
			  		= function(fieldName) {
			  				return this._validateRules[fieldName];
			  			}

			  	jQuery(domForm).on("submit", function(event, notMask) {

			  		this.cleanValidateErrors();

					var self = jQuery(this);
				    self.trigger(Consts.FORM_PREPARE_FOR_SUBMIT_EVENT);
				    self.trigger(Consts.FORM_VALIDATE_EVENT);

				    // 如果验证出错则阻止表单提交
				    if(this.hasValidateErrors()) {
				    	event.preventDefault();

				    	// 页面滚动到第一个错误提示信息位置
				    	var top = jQuery(".popover-content:first,alert-error:first").first().offset().top - 60;
				    	top = Math.max(0, top);
				    	$(window).scrollTop(top);
				    } else {
				    	self.trigger(Consts.FORM_BEFORE_SUBMIT_EVENT);
				    	if(!notMask) {
				    		jQuery("body").mask("上传数据中，请耐心等待...");
				    	}
				    }
				});

				jQuery(document).ready(function() {
					jQuery(domForm).triggerHandler("submit", [true]);
				})
			}

var FieldValiateManager = new Object();

	FieldValiateManager.validate
		= function(fieldName, formId, getValFunc, getValParam, errorMsgPlace) {
				var domForm = document.forms[formId];

				if(!domForm || !domForm.getValidateRuleByName || !domForm.getValidateRuleByName(fieldName))
					return;

				var rule = domForm.getValidateRuleByName(fieldName);
				var fieldTipName = rule["fieldTipName"];
				var validateRule = rule["validateRule"];
				var errorMsg     = rule["errorMsg"];

				jQuery("form").on(Consts.FORM_VALIDATE_EVENT, function() {

					var fieldValue = Commons[getValFunc].apply(null, [getValParam, formId]);

					// 如果校验出错，则出现浮动框提示错误，并且添加到表单的验证错误信息中
					if(!Validator.validateRules(fieldValue, validateRule)) {
						FieldValiateManager.showErrorMsg(errorMsgPlace, fieldTipName, errorMsg);
						this.addValidateErrors({fieldTipName : fieldTipName, errorMsg : errorMsg});
					}
				});
			}

	FieldValiateManager.removeErrorMsg
		= function(place) {
				if(Commons.isString(place)) {
					place += "#"
				} 
				jQuery(place).popover('destroy');
			}

	FieldValiateManager.showErrorMsg
		= function(place, fieldTipName, msg) {
				if(Commons.isString(place)) {
					var jqObj = jQuery('#' + place);
				} else {
					var jqObj = jQuery(place);
				}

			    var content = "<span style='font-weight:bold;' >"
			    			+ "		<nobr>"
			    			+ "			<font style='color:blue;'>" + fieldTipName + "</font>" + msg
			    			+ "		</nobr>"
			    			+ "</span>";

			    jqObj.popover('destroy');
			    jqObj.popover({ html      : true,
						        placement : "right",
						        trigger   : "manual",
						        content   : content });
			    jqObj.popover('show');

			    var jqNextObj = jqObj.next();
			    jqNextObj.css({"max-width": "1000px", "min-width":"300px" })
			    jqNextObj.find(".popover-title").hide();
			    jqNextObj.click(function() {
			        jQuery(this).prev().popover("hide");
			    });

			    jqObj.click(function() {
			        jQuery(this).popover("hide");
			    });
			}

var Commons = new Object();

	Commons.getValueById
		= function(eleId) {
				return document.getElementById(eleId).value;
			}

	Commons.getRadioValue
		= function(fieldName, formIdOrIndex) {
			    var radioArr = document.forms[formIdOrIndex][fieldName];
			    for(var i = 0; i < radioArr.length; i++) {
			        var radio = radioArr[i];
			        if(radio.checked) {
			            return radio.value;
			        }
			    }
			    return "";
			}
			
	Commons.getCheckBoxValue
		= function(fieldName, formIdOrIndex) {
			    var checkBoxArr = document.forms[formIdOrIndex][fieldName];
			    var result = "";
			    for(var i = 0; i < checkBoxArr.length; i++) {
			        var checkBox = checkBoxArr[i];
			        if(checkBox.checked) {
			            result += checkBox.value + Consts.DBC_SPLIT;
			        }
			    }
			    return result.removeEnd(Consts.DBC_SPLIT);
			}

	Commons.isEmpty
		= function(value) {
				return (value === null
							|| (typeof value == "undefined")
							|| value === "undefined"
							|| value === ""
							|| value === "null"
							|| (Commons.isString(value) && value.trim().length == 0));
			}

	Commons.isString
		= function(obj) {
				return (typeof obj == "string");
			}

	Commons.getSelectedOptionText
		= function(selectId) {
				var selectObj = document.getElementById(selectId);
				var selectedIndex = selectObj.selectedIndex;
				return (selectedIndex >= 0)? selectObj.options[selectedIndex].text : "";
			}

	Commons.defaultString
		= function(value, defaultVal) {
				return this.isEmpty(value) ? defaultVal : value;
			}

	Commons.loadJsLibOnce
		= function(jsLib) {
				if(!document.getElementById(jsLib)) {
					document.write('<script id="' + jsLib + '" src="' + jsLib + '"><\/script>');
				}
			}

	Commons.loadCssLibOnce
		= function(cssLib) {
				if(!document.getElementById(cssLib)) {
					document.write('<link id="' + cssLib + '" rel="stylesheet" href="' + cssLib + '" \/>');
				}
			}

var Page = new Object();

	Page.del
		= function(url, delId, tip) {
				Dialog.confirm(tip, function() {	// TODO id_ put in consts
					jQuery.post(url, { id : delId}, function(result) {
						if(result === true) {
							document.forms[0].submit();
						} else {
							Dialog.error("删除失败！");
						}
					})
				})
			}

var Template = new Object();

	Template.selectLinkage
		= function(orginId, referTo, ajaxUrl, txt, val, blankLabel, blankValue) {
					var referValue = document.getElementById(referTo).value;
					var selecObj = jQuery("#" + orginId);
					selecObj.empty();
					
					jQuery.get( ajaxUrl,
							  { referValue: referValue,
								timeStamp : new Date().getTime() },
							  function(dataList) {
								  if(!Commons.isEmpty(blankLabel)) {
									  selecObj[0].options.add(new Option(blankLabel, Commons.defaultString(blankValue, "")));
								  }

								  for (var i = 0; i < dataList.length; i++) {
									  var optObj = dataList[i];
									  var opt = new Option(optObj[txt], optObj[val]);
									  selecObj[0].options.add(opt);
								  }

								  var initValue = selecObj.data(Consts.SELECT_INIT_VALUE);
								  if(!Commons.isEmpty(initValue)) {
									  selecObj.val(initValue);
									  selecObj.removeData(Consts.SELECT_INIT_VALUE);
								  }
								  selecObj.triggerHandler("change");
							  })
			}

	Template.moveSelectedOptions
		= function(from, to) {
				var fromSelect = document.getElementById(from);
			    var toSelect = document.getElementById(to);

			    var maxOptIndex = fromSelect.options.length - 1;
				for(var i = maxOptIndex; i > -1; i--) {
					var fromOpt = fromSelect.options[i];
					if(fromOpt.selected) {
						toSelect.appendChild(fromOpt);
					}
				}
			}

	Template.moveAllOptions
		= function(from, to) {
				var fromSelect = document.getElementById(from);
			    var toSelect = document.getElementById(to);

				var maxOptIndex = fromSelect.options.length - 1;
				for(var i = maxOptIndex; i > -1; i--) {
					var fromOpt = fromSelect.options[i];
					toSelect.appendChild(fromOpt);
				}
			}

	Template.getSelectOptionsValue
		= function(selectId) {
				var selectObj = document.getElementById(selectId);
				var result = "";
				for(var i = 0; i < selectObj.options.length; i++) {
					var opt = selectObj.options[i];
					result += opt.value + Consts.DBC_SPLIT;
				}

				return result.removeEnd(Consts.DBC_SPLIT);
			}

	Template.goToPage
		= function(page_no) {
				var formObj = document.forms[0];
				var hidObj = document.createElement("INPUT");
				hidObj.name = Consts.PAGE_NO;
				hidObj.value = page_no;
				hidObj.type = "hidden";
				formObj.appendChild(hidObj);
				formObj.submit();
			}

	Template.getFileName
		= function(filePath) {
				var filePath = filePath.substringAfter("\\"); // window
				return filePath.substringAfter("/"); // linux
			}
	
	Template.viewImage
		= function(obj, attcId) {
				var jqObj = jQuery(obj);
				var img = jQuery("<img style='display:none;width:250px;position:absolute;' class='img-polaroid' />")
				jQuery("body").append(img);
				jqObj.hover(
					function () {	
						var offset = jqObj.offset();
						img.attr("src", Consts.CONTEXT_PATH + "/app/attachment/showimg.do?attc_id=" + attcId)
						img.css({"left" : offset.left - 270, 
								 "top"  : offset.top  - 250 })
						img.show();
				  	},
				  	function () {
						img.hide()			  			  
				  	}
				)
			}

var Dialog = new Object();

	Dialog.alert = function(msg) {
		this._tip("alert", msg);
	}

	Dialog.error = function(msg) {
		this._tip("error", msg);
	}

	Dialog.info = function(msg) {
		this._tip("info", msg);
	}

	Dialog.success = function(msg) {
		this._tip("success", msg);
	}

	Dialog._tip = function(type, msg) {
		var id = "_" + type + new Date().getTime();
		var html = '<div id="' + id + '" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >'
		  	 	 + '	<div class="modal-body" style="text-align:center">'
		  		 + '		<button id="_btnClose1" type="button" class="close" >×</button>'
			 	 + '		<image src="' + Consts.CONTEXT_PATH + '/resources/images/dialog/' + type + '.png" style="width:100px;height:100px;margin-left:10px" />'
		  	     + '		<h3>' + msg + '</h3>'
		  	 	 + '	</div>'
		  	     + '	<div class="modal-footer" style="text-align:center" >'
		 	     + '		<input id="_btnClose2" type="button" value=" 关    闭 " class="btn btn-success" />'
		  	     + '	</div>'
		  	     + '</div>';

  	 	jQuery("body").append(html);
  	 	jQuery('#' + id).modal('show')

  	 	jQuery("#_btnClose1,#_btnClose2").click(function(){
  	 		Dialog.destory(id);
  	 	})
	}

	Dialog.destory = function(id) {
		var dialog = jQuery('#' + id);
		dialog.modal('removeBackdrop')
		dialog.remove();
	}

	Dialog.confirm = function(msg, yesCallback, noCallback) {
		var id = "_confirm" + new Date().getTime();
		var html = '<div id="' + id + '" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
		  	 	 + '	<div class="modal-body" style="text-align:center" >'
		  		 + '		<button id="_btnClose" type="button" class="close" >×</button>'
			 	 + '		<image src="' + Consts.CONTEXT_PATH + '/resources/images/dialog/alert.png" style="width:100px;height:100px;margin-left:10px" />'
		  	     + '		<h3>' + msg + '</h3>'
		  	 	 + '	</div>'
		  	     + '	<div class="modal-footer" style="text-align:center" >'
		  		 + '		<input id="_btnYes" type="button" value=" 确    定 " class="btn btn-success" />'
		  		 + '		<input id="_btnNo"  type="button" value=" 取    消 " class="btn" style="margin-left:40px" />'
		  	     + '	</div>'
		  	     + '</div>';

		jQuery("body").append(html);
  	 	jQuery('#' + id).modal('show')

  	 	jQuery("#_btnClose,#_btnYes,#_btnNo").click(function(){
  	 		Dialog.destory(id);
  	 	})

	  	jQuery("#_btnYes").click(function() {
	  		yesCallback(true);
  	 	})

  	 	if(noCallback) {
  	 		jQuery("#_btnNo").click(function() {
  	 			noCallback(false);
  	 		})
  	 	}
	}

	Dialog.prompt = function(msg, callback) {
		var id = "_prompt" + new Date().getTime();
		var valueId = "_val" + id;
		var html = '<div id="' + id + '" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
		 		 + '	<div class="modal-header" style="text-align:left;padding-left:10%" >'
		  		 + '		<button id="_btnClose" type="button" class="close" >×</button>'
		  		 + '		<h4>' + msg + '</h4>'
		  		 + '	</div>'
		  	 	 + '	<div class="modal-body">'
		  	     + '		<input id="' + valueId + '" type="text" style="width:80%" />'
		  	 	 + '	</div>'
		  	     + '	<div class="modal-footer" style="text-align:center" >'
		  		 + '		<input id="_btnYes" type="button" value=" 确    定 " class="btn btn-success" />'
		  		 + '		<input id="_btnNo"  type="button" value=" 取    消 " class="btn" style="margin-left:40px" />'
		  	     + '	</div>'
		  	     + '</div>';

  	 	jQuery("body").append(html);
  	 	jQuery('#' + id).modal('show')

  	 	jQuery("#_btnClose,#_btnYes,#_btnNo").click(function(){
  	 		Dialog.destory(id);
  	 	})

	  	jQuery("#_btnYes").click(function() {
	  		var modalDialg = jQuery(this).closest("[role=dialog]");
	  		var inputObj = modalDialg.find("#" + valueId);
	  		var value = inputObj.val();
	  		callback(value);
  	 	})

  	 	jQuery("#_btnClose,#_btnNo").click(function(){
  	 		callback(null);
  	 	})
	}

	Dialog.showPage = function(url, title, height, width) {
		var id = "_page" + new Date().getTime();
		var html = '<div id="' + id + '" style="width:' + width + 'px;height:' + height + 'px;" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >'
				 + '	<div class="modal-header" style="text-align:left;padding-left:5%;padding-top:3px;padding-bottom:3px;" >'
		  		 + '		<button id="_btnClose1" type="button" class="close" >×</button>'
		  		 + '		<h4>' + title + '</h4>'
		  		 + '	</div>'
		  	 	 + '	<div style="text-align:center;padding:0px;overflow:hidden;width:100%;height:' + (height - 107) + 'px;" >'
		  	 	 + '		<iframe src="' + url + '" style="width:100%;height:' + (height - 107) + 'px;" scrolling="auto" frameborder="no" ></iframe>' // 107 = modal-header.height + modal-footer.height
		  	 	 + '	</div>'
		  	     + '	<div class="modal-footer" style="text-align:center;" >'
		  		 + '		<input id="_btnClose2" type="button" value=" 关    闭 " class="btn btn-success" />'
		  	     + '	</div>'
		  	     + '</div>';
  	 	jQuery("body").append(html);
  	 	jQuery('#' + id).modal('show')

  	 	jQuery("#_btnClose1,#_btnClose2").click(function(){
  	 		Dialog.destory(id);
  	 	})
	}

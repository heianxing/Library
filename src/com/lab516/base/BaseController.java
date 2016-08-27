package com.lab516.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.lab516.entity.sys.User;
import com.lab516.service.sys.UserService;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.EntityValidateRuleResolver;
import com.lab516.support.validate.FieldValidateRule;
import com.lab516.support.validate.annotation.Validate;

/**
 * 页面控制器父类
 */
public abstract class BaseController extends BaseUtils {

	/** 分页的一页数据条数 */
	public static final int PAGE_SIZE = 20;

	/** 前台联动参数 */
	public static final String REFER_VALUE = "referValue";

	public static final String GOTO_LIST = "redirect:list.do";

	public static final String VALIDATE_RULES = "validateRules";

	public static final String ADD = "add";

	public static final String LIST = "list";

	public static final String EDIT = "edit";

	public static final String VIEW = "view";

	public static final String DELETE = "delete";

	public static final String DFT_FTL = "defaultView";

	public static final String FM_FTL = "formView";

	private static final String[] PAGE_TYPES = { ADD, EDIT, LIST, VIEW };

	@ModelAttribute("pageType")
	public String setPageType() {
		String clzName = getClass().getSimpleName().toLowerCase();

		for (String type : PAGE_TYPES) {
			if (clzName.endsWith(type)) {
				return type;
			}
		}

		return "else";
	}

	@ModelAttribute("user")
	public User loadCurrentUser() {
		return UserService.currentUser();
	}

	@ModelAttribute(VALIDATE_RULES)
	public List<FieldValidateRule> loadValidateRule(EhWebRequest request) {
		Validate validate = getClass().getAnnotation(Validate.class);

		if (validate == null) {
			return null;
		}

		List<FieldValidateRule> rules = new ArrayList<>();

		for (Class clazz : validate.value()) {
			rules.addAll(EntityValidateRuleResolver.getRulesByClass(clazz));
		}

		return rules;
	}

}

package com.lab516.support.request;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.ui.ModelMap;

import com.lab516.base.Consts;

public abstract class BindResultRequest extends ServletRequest {

	private static final String BIND_ERRORS = "entityBindErrors";

	private ModelMap modelMap;

	public BindResultRequest(HttpServletRequest request, ModelMap modelMap) {
		super(request);
		this.modelMap = modelMap;
	}

	public void addBindError(String error) {
		List<String> bindErrors = (List<String>) modelMap.get(BIND_ERRORS);

		if (bindErrors == null) {
			bindErrors = new ArrayList<String>();
			modelMap.put(BIND_ERRORS, bindErrors);
		}

		bindErrors.add(error);
	}

	public boolean hasBindErrors() {
		if (getContentLength() > Consts.MAX_UPLOAD_SIZE) {
			addBindError(Consts.MAX_UPLOAD_SIZE_EXCEEDED_ERROR);
		}

		List<String> bindErrors = (List) modelMap.get(BIND_ERRORS);
		return !CollectionUtils.isEmpty(bindErrors);
	}

}

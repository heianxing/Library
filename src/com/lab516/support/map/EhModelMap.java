package com.lab516.support.map;

import org.springframework.ui.ModelMap;

public class EhModelMap {

	private ModelMap modelMap;

	public EhModelMap(ModelMap modelMap) {
		this.modelMap = modelMap;
	}

	public void putAlertMessage(String message) {
		putMessage(message, "alert");
	}

	public void putErrorMessage(String message) {
		putMessage(message, "error");
	}

	public void putInfoMessage(String message) {
		putMessage(message, "info");
	}

	public void putSuccessMessage(String message) {
		putMessage(message, "success");
	}

	private void putMessage(String message, String type) {
		putModel("DialogType", type);
		putModel("DialogMsg", message);
	}

	public void putModel(String key, Object val) {
		modelMap.put(key, val);
	}

	public void putModels(Object... objs) {
		for (Object obj : objs) {
			modelMap.addAttribute(obj);
		}
	}

}

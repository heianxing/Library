package com.lab516.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 安全框架shiro过滤器链
 */
@Entity
@Table(name = "s_url_filter_chain")
public class UrlFilterChain extends BaseEntity {

	@Id
	private String filt_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("名称")
	private String filt_name;

	@Require
	@StrLen(maxLen = 50)
	@FieldTipName("路径")
	private String filt_url;

	@Require
	@StrLen(maxLen = 80)
	@FieldTipName("拦截器链")
	private String filt_chain;
	
	@Require
	@FieldTipName("是否启用")
	private boolean enable;

	public UrlFilterChain() {
	}

	public String getFilt_id() {
		return filt_id;
	}

	public void setFilt_id(String filt_id) {
		this.filt_id = filt_id;
	}

	public String getFilt_name() {
		return filt_name;
	}

	public void setFilt_name(String filt_name) {
		this.filt_name = filt_name;
	}

	public String getFilt_url() {
		return filt_url;
	}

	public void setFilt_url(String filt_url) {
		this.filt_url = filt_url;
	}

	public String getFilt_chain() {
		return filt_chain;
	}

	public void setFilt_chain(String filt_chain) {
		this.filt_chain = filt_chain;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}

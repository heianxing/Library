package com.lab516.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 配置
 */
@Entity
@Table(name = "s_config")
public class Config extends BaseEntity {

	@Id
	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("配置ID")
	private String cfg_id;

	@Require
	@StrLen(maxLen = 60)
	@FieldTipName("配置名称")
	private String cfg_name;

	@StrLen(maxLen = 500)
	@FieldTipName("配置值")
	private String cfg_value;

	public Config() {
	}

	public String getCfg_id() {
		return cfg_id;
	}

	public void setCfg_id(String cfg_id) {
		this.cfg_id = cfg_id;
	}

	public String getCfg_value() {
		return cfg_value;
	}

	public void setCfg_value(String cfg_value) {
		this.cfg_value = cfg_value;
	}

	public String getCfg_name() {
		return cfg_name;
	}

	public void setCfg_name(String cfg_name) {
		this.cfg_name = cfg_name;
	}

}

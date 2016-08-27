package com.lab516.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.NumFormat;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 数据字典
 */
@Entity
@Table(name = "s_dictionary")
public class Dictionary extends BaseEntity {

	@Id
	private String dict_id;

	@Require
	@StrLen(maxLen = 60)
	@FieldTipName("字典名称")
	private String dict_name;

	@Require
	@StrLen(maxLen = 20)
	@FieldTipName("字典类型")
	private String dict_type;

	@Require
	@StrLen(maxLen = 20)
	@FieldTipName("字典值")
	private String dict_value;

	@Require
	@NumFormat(posLen = 5)
	@FieldTipName("排序号")
	private int order_no;

	public Dictionary() {
	}

	public String getDict_id() {
		return dict_id;
	}

	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

	public String getDict_name() {
		return dict_name;
	}

	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}

	public String getDict_type() {
		return dict_type;
	}

	public void setDict_type(String dict_type) {
		this.dict_type = dict_type;
	}

	public String getDict_value() {
		return dict_value;
	}

	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}

	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

}

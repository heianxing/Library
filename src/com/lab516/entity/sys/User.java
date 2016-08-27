package com.lab516.entity.sys;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.Email;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 用户
 */
@Entity
@Table(name = "s_user")
public class User extends BaseEntity {

	@Id
	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("账号")
	private String user_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("姓名")
	private String user_name;

	@Require
	@StrLen(minLen = 5, maxLen = 40)
	@FieldTipName("用户密码")
	private String password;

	@Require
	@FieldTipName("是否启用")
	private boolean enable;

	@Email
	@FieldTipName("邮箱")
	private String email;
	
	@Require
	@FieldTipName("用户角色")
	private String roles_ids;

	private Date create_date;

	public User() {
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getRoles_ids() {
		return roles_ids;
	}

	public void setRoles_ids(String roles_ids) {
		this.roles_ids = roles_ids;
	}

	// end getter and setter ...

	// add manual
	public boolean getEnable() {
		return enable;
	}

	public boolean hasRole(String role_id) {
		return roles_ids.contains(role_id);
	}

	public boolean isStudent() {
		return hasRole(Consts.ROLE_STUDENT);
	}

}

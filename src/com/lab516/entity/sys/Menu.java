package com.lab516.entity.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.NumFormat;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 菜单
 */
@Entity
@Table(name = "s_menu")
public class Menu extends BaseEntity {

	@Id
	private String menu_id;

	@Require
	@StrLen(maxLen = 50)
	@FieldTipName("菜单名称")
	private String menu_name;

	@Require
	@FieldTipName("父菜单ID")
	private String parent_id;

	@Require
	@StrLen(maxLen = 50)
	@FieldTipName("菜单URL")
	private String menu_url;

	@FieldTipName("菜单URL")
	@StrLen(maxLen = 30)
	private String menu_icon;

	@Require
	@FieldTipName("启用标志")
	private boolean enable;

	@Require
	@NumFormat(posLen = 5)
	@FieldTipName("排序号")
	private Integer order_no;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "s_role_menu", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Transient
	private List<Menu> subMenus;

	@Transient
	@Require
	@FieldTipName("所属角色")
	private String roles_id;

	public Menu() {
	}

	public Menu(String menu_id, String menu_name) {
		this.menu_id = menu_id;
		this.menu_name = menu_name;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenu_icon() {
		return menu_icon;
	}

	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getOrder_no() {
		return order_no;
	}

	public void setOrder_no(Integer order_no) {
		this.order_no = order_no;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getRoles_id() {
		return roles_id;
	}

	public void setRoles_id(String roles_id) {
		this.roles_id = roles_id;
	}

	// end get and set

	public boolean isSubMenuOf(Menu other) {
		return this.getParent_id().equals(other.getMenu_id());
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void addSubMenus(Menu menu) {
		if (subMenus == null) {
			subMenus = new ArrayList<Menu>();
		}
		subMenus.add(menu);
	}

	public boolean hasSubMenus() {
		return (subMenus != null && !subMenus.isEmpty());
	}

}

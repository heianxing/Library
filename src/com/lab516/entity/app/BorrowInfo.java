package com.lab516.entity.app;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 借书信息
 */
@Entity
@Table(name = "a_borrowinfo")
public class BorrowInfo extends BaseEntity {

	@Id
	private String brw_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("图书编号")
	private String book_id;

	@Require
	@StrLen(maxLen = 20)
	@FieldTipName("学生编号")
	private String stu_id;

	@Require
	@FieldTipName("状态")
	private boolean is_returned;

	@Require
	@FieldTipName("借书时间")
	private Date borrow_time;

	@FieldTipName("还书时间")
	private Date return_time;

	public BorrowInfo() {
	}

	public String getBrw_id() {
		return brw_id;
	}

	public void setBrw_id(String brw_id) {
		this.brw_id = brw_id;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public boolean isIs_returned() {
		return is_returned;
	}

	public void setIs_returned(boolean is_returned) {
		this.is_returned = is_returned;
	}

	public Date getBorrow_time() {
		return borrow_time;
	}

	public void setBorrow_time(Date borrow_time) {
		this.borrow_time = borrow_time;
	}

	public Date getReturn_time() {
		return return_time;
	}

	public void setReturn_time(Date return_time) {
		this.return_time = return_time;
	}

}

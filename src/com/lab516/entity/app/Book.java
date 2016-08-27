package com.lab516.entity.app;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lab516.base.BaseEntity;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.annotation.rule.Require;
import com.lab516.support.validate.annotation.rule.StrLen;

/**
 * 图书
 */
@Entity
@Table(name = "a_book")
public class Book extends BaseEntity {

	@Id
	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("图书编号")
	private String book_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("图书名称")
	private String book_name;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("作者")
	private String book_author;

	@StrLen(maxLen = 100)
	@FieldTipName("封面")
	private String book_img;

	@Require
	@FieldTipName("图书类型")
	private String book_type;

	private boolean is_borrowed;

	@OneToMany
	@JoinColumn(name = "book_id")
	private List<BorrowInfo> borrowInfoList;

	public Book() {
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public boolean isIs_borrowed() {
		return is_borrowed;
	}

	public void setIs_borrowed(boolean is_borrowed) {
		this.is_borrowed = is_borrowed;
	}

	public String getBook_img() {
		return book_img;
	}

	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}

	public List<BorrowInfo> getBorrowInfoList() {
		return borrowInfoList;
	}

	public void setBorrowInfoList(List<BorrowInfo> borrowInfoList) {
		this.borrowInfoList = borrowInfoList;
	}

	public String getBook_type() {
		return book_type;
	}

	public void setBook_type(String book_type) {
		this.book_type = book_type;
	}

}

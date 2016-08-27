package com.lab516.base;

import java.util.List;

/**
 * 分页数据
 */
public class Page {

	/** 页中数据 */
	private List recordList;

	/** 一共的数据条数 */
	private long recordCount;

	/** 一页数据条数 */
	private int pageSize;

	/** 页号 */
	private int pageNo;

	/** 一共页面数 */
	private int pageCount;

	public Page(List recordList, long recordCount, int pageSize, int pageNo, int pageCount) {
		this.recordList = recordList;
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}

	public Page(List recordList, long recordCount, int pageSize, int pageNo) {
		this.recordList = recordList;
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.pageCount = (recordCount == 0) ? 1 : (int) Math.ceil((double) recordCount / (double) pageSize);

	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	// 是否有前一页
	public boolean hasPrevPage() {
		return pageNo > 1;
	}

	// 获取前一页
	public int getPrevPage() {
		return pageNo - 1;
	}

	// 是否有下一页
	public boolean hasNextPage() {
		return pageNo < pageCount;
	}

	// 获取下一页
	public int getNextPage() {
		return pageNo + 1;
	}

}

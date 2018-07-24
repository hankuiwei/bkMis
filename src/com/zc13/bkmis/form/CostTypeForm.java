package com.zc13.bkmis.form;


/**
 * 费用类型Form
 * 
 * @author 王正伟 Date：Nov 26, 2010 Time：3:18:29 PM
 */
public class CostTypeForm extends BasicForm {
	private Integer id;
	private String costTypeName;// 类型名称
	private String costTypeCode;// 类型代码
	private Integer[] checkbox;// 保存需要删除的记录id
	private Integer[] gaugeIds;// 表具id
	private Integer[] costparatypeIds;// 收费参数id
	private Integer lpId;
	// 查询所需要的参数
	private String typeName;// 类型名称

	/* 分页信息 */
	public String currentpage; // 当前页数
	public String pagesize; // 每页显示的条数

	private int startIndex;

	public int getStartIndex() {
		int c = 1;
		int p = 10;
		try {
			c = Integer.parseInt(currentpage);
		} catch (NumberFormatException e) {
			c = 1;
		}
		try {
			p = Integer.parseInt(pagesize);
		} catch (NumberFormatException e) {
			p = 10;
		}
		startIndex = (c - 1) * p + 1;
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public String getCostTypeCode() {
		return costTypeCode;
	}

	public void setCostTypeCode(String costTypeCode) {
		this.costTypeCode = costTypeCode;
	}

	public Integer[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer[] getGaugeIds() {
		return gaugeIds;
	}

	public void setGaugeIds(Integer[] gaugeIds) {
		this.gaugeIds = gaugeIds;
	}

	public Integer[] getCostparatypeIds() {
		return costparatypeIds;
	}

	public void setCostparatypeIds(Integer[] costparatypeIds) {
		this.costparatypeIds = costparatypeIds;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

}

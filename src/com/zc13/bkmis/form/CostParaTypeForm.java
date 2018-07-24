package com.zc13.bkmis.form;

/**
 * 计费参数类型Form 
 * @author 王正伟
 * Date：Nov 26, 2010
 * Time：3:18:29 PM
 */
public class CostParaTypeForm extends BasicForm{
	private Integer id;
	private String typeName;//类型名称
	private String typeCode;//类型代码
	private String useGauge;//是否使用表具
	private String code;//代码
	private String codeType;//代码类型
	private Integer[] checkbox;//保存需要删除的记录id

	//查询所需要的参数
	private String type_Name;//类型名称
	
	
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
		startIndex = (c-1)*p+1;
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getUseGauge() {
		return useGauge;
	}
	public void setUseGauge(String useGauge) {
		this.useGauge = useGauge;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public Integer[] getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Integer[] checkbox) {
		this.checkbox = checkbox;
	}
	public String getType_Name() {
		return type_Name;
	}
	public void setType_Name(String type_Name) {
		this.type_Name = type_Name;
	}

	
}

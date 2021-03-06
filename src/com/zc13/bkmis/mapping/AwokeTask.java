package com.zc13.bkmis.mapping;

/**
 * AwokeTask generated by MyEclipse Persistence Tools
 */

public class AwokeTask implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String name;
	private Integer amount;
	private String description;
	private String url;
	private Integer sequence;
	private String type;
	private String mapurl;
	private Integer lpId;
	private Integer rootUser;

	// Constructors

	/** default constructor */
	public AwokeTask() {
	}

	public String getMapurl() {
		return mapurl;
	}

	public void setMapurl(String mapurl) {
		this.mapurl = mapurl;
	}
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRootUser() {
		return rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public AwokeTask(Integer id, String code, String name, Integer amount, String description, String url, Integer sequence, String type, String mapurl, Integer lpId, Integer rootUser) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.amount = amount;
		this.description = description;
		this.url = url;
		this.sequence = sequence;
		this.type = type;
		this.mapurl = mapurl;
		this.lpId = lpId;
		this.rootUser = rootUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLpId() {
		return lpId;
	}

	public void setLpId(Integer lpId) {
		this.lpId = lpId;
	}

}
package com.zc13.bkmis.mapping;

/**
 * TSentRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSentRecord implements java.io.Serializable {

	// Fields

	private TSentRecordId id;

	// Constructors

	/** default constructor */
	public TSentRecord() {
	}

	/** full constructor */
	public TSentRecord(TSentRecordId id) {
		this.id = id;
	}

	// Property accessors

	public TSentRecordId getId() {
		return this.id;
	}

	public void setId(TSentRecordId id) {
		this.id = id;
	}

}
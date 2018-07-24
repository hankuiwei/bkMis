package com.zc13.bkmis.mapping;

/**
 * AllMeterType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AllMeterType implements java.io.Serializable {

	// Fields

	private AllMeterTypeId id;

	// Constructors

	/** default constructor */
	public AllMeterType() {
	}

	/** full constructor */
	public AllMeterType(AllMeterTypeId id) {
		this.id = id;
	}

	// Property accessors

	public AllMeterTypeId getId() {
		return this.id;
	}

	public void setId(AllMeterTypeId id) {
		this.id = id;
	}

}
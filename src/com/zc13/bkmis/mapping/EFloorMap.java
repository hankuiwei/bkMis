package com.zc13.bkmis.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * EFloorMap generated by MyEclipse Persistence Tools
 */

public class EFloorMap implements java.io.Serializable {

	// Fields

	private Integer id;
	private ELp ELp;
	private EBuilds EBuilds;
	private String url;
	private String name;
	private String floor;
	private String type;
	private Integer rootUser;
	private Set EFloorMapCoordinates = new HashSet(0);

	// Constructors
	public EFloorMap(Integer id, ELp lp, EBuilds builds, String url,
			String name, String floor, String type, Integer rootUser,
			Set floorMapCoordinates) {
		super();
		this.id = id;
		ELp = lp;
		EBuilds = builds;
		this.url = url;
		this.name = name;
		this.floor = floor;
		this.type = type;
		this.rootUser = rootUser;
		EFloorMapCoordinates = floorMapCoordinates;
	}

	/** default constructor */
	public EFloorMap() {
	}

	/** full constructor */

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EBuilds getEBuilds() {
		return this.EBuilds;
	}

	public void setEBuilds(EBuilds EBuilds) {
		this.EBuilds = EBuilds;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Integer getRootUser() {
		return this.rootUser;
	}

	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}

	public Set getEFloorMapCoordinates() {
		return this.EFloorMapCoordinates;
	}

	public void setEFloorMapCoordinates(Set EFloorMapCoordinates) {
		this.EFloorMapCoordinates = EFloorMapCoordinates;
	}

	public ELp getELp() {
		return ELp;
	}

	public void setELp(ELp lp) {
		ELp = lp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
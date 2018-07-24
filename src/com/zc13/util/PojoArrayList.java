package com.zc13.util;

import java.util.ArrayList;

public class PojoArrayList<E> extends ArrayList<E> {

	public Class getItemclass() {
		return itemclass;
	}

	public PojoArrayList(Class itemclass) {
		this.itemclass = itemclass;
	}

	public E get(int index) {
		try {
			for (; index >= size(); add((E) itemclass.newInstance()))
				;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.get(index);
	}

	private Class itemclass;
}

package com.zc13.bkmis.client;

public class FinalStatic {
	public static final int A = 4 + 4;
	static {
		System.out.println("如果执行了，证明类初始化了……");
	}
}

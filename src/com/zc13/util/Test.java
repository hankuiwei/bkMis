package com.zc13.util;

public class Test {

	/**
	 * @param args
	 * Date:2013-8-13 
	 * Time:下午10:04:53
	 */
	public static void main(String[] args) {
		
		System.out.println(Test.round(0, 2));
	}

	public static double round(double d, int n) {
		//d*10^n
		double t = d * Math.pow(10, n);
		//四舍五入取整
		long l;
		if (t > 0)
			l = (long) (t + 0.5);
		else
			l = (long) (t - 0.5);
		//d/10^n
		t = (double) l / Math.pow(10, n);

		//为有效数字补充0

		return t;
	}
}

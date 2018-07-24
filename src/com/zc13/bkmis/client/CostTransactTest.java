package com.zc13.bkmis.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zc13.bkmis.service.ICostTransactService;
import com.zc13.util.cacu.Cacu;
public class CostTransactTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ICostTransactService costTransactService;
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/zc13/bkmis/client/applicationContext.xml");
		costTransactService = (ICostTransactService)ac.getBean("costTransactService");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tadayDate = dateFormat.format(new Date());
		Map map = new HashMap();
		//map中的参数的获得方法?
		map.put("costId", "12");
		map.put("costType", "elec");
		map.put("lpId", "2");
		map.put("roomId", "222");
		map.put("todayDate", tadayDate);
		map.put("pactId", "56");
		//公式的获得方式?
		String formula = "{计费面积}*{单价}-12+{调节参数}";
		String expression = "";
		try {
			expression = costTransactService.getExpression(map, formula);
			double values = Cacu.cacu(expression);
			System.out.println("处理后的公式："+expression);
			System.out.println("根据公式计算出值："+values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

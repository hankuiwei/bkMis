package com.zc13.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//在hibernate托管给Spring时得到sessionFactory 
/**
 * 
 */
public class HibernateUtil extends HibernateDaoSupport{
	
	private static final SessionFactory sessionFactory; 
	static {
		try {
			Resource resource = new ClassPathResource("../applicationContext.xml");
			BeanFactory factory = new XmlBeanFactory(resource);
			sessionFactory = (SessionFactory)factory.getBean("sessionFactory"); 
		} catch (HibernateException ex) { 
			throw new RuntimeException("Exception building SessionFactory: " + ex.getMessage(), ex); 
		} 
	} 
	public static final ThreadLocal session = new ThreadLocal(); 
	public static Session currentSession() throws HibernateException {
		
		Session s = (Session) session.get(); 
		// Open a new Session, if this Thread has none yet 
		if (s == null) { 
			s = sessionFactory.openSession(); 
			session.set(s); 
		} 
		return s; 
	} 

} 

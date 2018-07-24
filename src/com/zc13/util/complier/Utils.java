package com.zc13.util.complier;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
/**
 * @author 王正伟
 * @Date：Dec 23, 2010
 * @Time：11:32:47 AM
 * @description 
 */
public abstract class Utils {
	public static final String ENCODING = Charset.defaultCharset().name();
	/** 把String转换成URI，如果转换异常不抛出URISyntaxException，而直接抛出RuntimeException。 */
	static URI toURI(String name) {
		try {
			return new URI(name);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}

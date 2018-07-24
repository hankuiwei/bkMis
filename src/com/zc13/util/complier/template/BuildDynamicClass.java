package com.zc13.util.complier.template;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.zc13.util.complier.CharSequenceCompiler;
import com.zc13.util.complier.CharSequenceCompilerException;
import com.zc13.util.complier.Utils;
/**
 * 
 * @author 王正伟
 * Date：Dec 23, 2010
 * Time：3:06:28 PM
 */
public class BuildDynamicClass {
	/** 包名前缀 */
	private static final String PACKAGE_NAME = "com.zc13.runtime";
	/** 类名后缀 */
	private int classNameSuffix = 0;
	/** 随机数生成器，用于生成随机的包名和类名 */
	private static final Random random = new Random();
	/** 字符串形式的Java源文件内容 */
	private String template;
	/**模版文件名*/
	private static final String TEMPLATE_NAME = "template";
	private static final String TARGET_VERSION = "1.6";
	private final CharSequenceCompiler<Function> compiler = new CharSequenceCompiler<Function>(getClass()
			.getClassLoader(), Arrays.asList(new String[] { "-target", TARGET_VERSION, "-encoding",
			Utils.ENCODING }));
	
	public Function newFunction(String expr) {
		StringBuilder errStr = new StringBuilder();
		Function result = null;
		try {
			//生成唯一的包名和类名
			final String packageName = PACKAGE_NAME + digits();
			final String className = "C_" + (classNameSuffix++) + digits();
			final String qName = packageName + '.' + className;
			//生成类的源码
			final String source = fillTemplate(packageName, className, expr);
			System.out.println("*********源码************");
			System.out.println(source);
			System.out.println("*********源码************");
			//编译源码
			Class<Function> compiledFunction = compiler.compile(qName, source,
					new Class<?>[] { Function.class });
			result = compiledFunction.newInstance();
		} catch (CharSequenceCompilerException ex) {
			errStr.append(log(ex.getDiagnostics()));
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			errStr.append(ExceptionUtils.getFullStackTrace(ex)).append("\n");
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			errStr.append(ExceptionUtils.getFullStackTrace(ex)).append("\n");
			ex.printStackTrace();
		} catch (IOException ex) {
			errStr.append(ExceptionUtils.getFullStackTrace(ex)).append("\n");
			ex.printStackTrace();
		}
		if (errStr.toString().trim().length() > 0) {
			System.err.println(errStr.toString());
		}
		return result;
	}
	/** @return 返回以'_'开头的随机16进制字符串 */
	private String digits() {
		return '_' + Long.toHexString(random.nextLong());
	}
	/**
	 * 生成字符串形式的java源文件内容
	 * 
	 * @param packageName
	 *            包名
	 * @param className
	 *            类名
	 * @param expression
	 *            表达式
	 * @return 字符串形式的java源文件内容
	 * @throws IOException
	 */
	private String fillTemplate(String packageName, String className, String expression) throws IOException {
		if (template == null) {
			template = IOUtils.toString(Function.class.getResourceAsStream(TEMPLATE_NAME), Utils.ENCODING);
		}
		System.out.println("*****template*****");
		System.out.println(template);
		System.out.println("*****template*****");
		String source = template.replace("$packageName", packageName)//
				.replace("$className", className)//
				.replace("$expression", expression);
		return source;
	}
	/** 记录{@link DiagnosticCollector}中的错误内容 */
	private CharSequence log(final DiagnosticCollector<JavaFileObject> diagnostics) {
		final StringBuilder msgs = new StringBuilder();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			msgs.append(diagnostic.getMessage(null)).append("\n");
		}
		return msgs;
	}
}

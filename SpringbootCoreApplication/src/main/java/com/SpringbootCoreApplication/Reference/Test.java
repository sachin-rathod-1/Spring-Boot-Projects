package com.SpringbootCoreApplication.Reference;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContext context=new ClassPathXmlApplicationContext("com/SpringbootCoreApplication/Reference/Refconfig.xml");
		
		A a= (A) context.getBean("aref");
		System.out.println(a);
	}

}

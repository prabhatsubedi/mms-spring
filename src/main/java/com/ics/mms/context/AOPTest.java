package com.ics.mms.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest { 

    public static void main(String[] args) {

        ApplicationContext context =  new ClassPathXmlApplicationContext("servlet-context.xml");

                Employee employee = (Employee) context.getBean("employee");

                employee.getName();

                employee.getCompany();

    } 
}

package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        ProfileService profileService = (ProfileService) ctx.getBean("profileService");

        try {
        	profileService.shareProfile("Alice", "Alice", "Bo");
            profileService.readProfile("Bob", "Alice");
            profileService.unshareProfile("Alice", "Bob");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}
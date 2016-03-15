package com.javacodegeeks.testng.spring;

public class LearnJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Foo fooObject = new Foo();
		fooObject.setName("neha");
		fooObject.printString();
		System.out.println(fooObject.getName());
		fooObject.printString("manish");
		fooObject.printString();
		Foo.name = "amar";
		System.out.println(fooObject.getName());
		
		System.out.println(Foo.name);
	}
	

}

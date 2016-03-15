package com.javacodegeeks.testng.spring;

public class Foo {
	public static String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Foo.name = name;
	}	
	
	public void printString(String name)
	{
		Foo.name =name;
		System.out.println(name);
		
	}

	public void printString()
	{
		printString(name);
	}
	
}

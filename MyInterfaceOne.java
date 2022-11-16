package com.demo.javaprogram;

public interface MyInterfaceOne {
	int var11 = 100;
	int var12 = 200;
	
	void myFunction11();
	void myFunction12();
	
	default String myFunction13() {
		return "MyInterfaceOne: myfunction13() is executing...";
	}
	
	static String myFunction14() {
		return "MyInterfaceOne: myFucntion14() is executing...";
	}
}

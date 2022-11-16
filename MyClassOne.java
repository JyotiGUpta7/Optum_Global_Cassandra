package com.demo.javaprogram;

public class MyClassOne implements MyInterfaceTwo {

	@Override
	public void myFunction11() {
		System.out.println("MyInterfaceOne: myFunction11() is executing with var11 = " + var11 + "...");
	}

	@Override
	public void myFunction12() {
		System.out.println("MyInterfaceOne: myFunction12() is executing with var12 = " + var12 + "...");
	}

	@Override
	public void myFunction21() {
		System.out.println("MyInterfaceTwo: myFunction21() is executing with var21 = " + var21 + "...");
	}

	@Override
	public void myFunction22() {
		System.out.println("MyInterfaceTwo: myFunction22() is executing with var22 = " + var22 + "...");
	}
	
	public static void main(String[] args) {
		MyClassOne ob1 = new MyClassOne();
		ob1.myFunction11();
		ob1.myFunction12();
		ob1.myFunction21();
		ob1.myFunction22();
		System.out.println("");
		System.out.println(ob1.myFunction13());
		System.out.println(MyInterfaceOne.myFunction14());
	}
}

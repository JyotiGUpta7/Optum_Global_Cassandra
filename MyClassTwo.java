package com.demo.javaprogram;

public abstract class MyClassTwo implements MyInterfaceTwo {

	@Override
	public void myFunction11() {
		System.out.println("MyInterfaceOne: myFunction11() is executing with var11 = " + var11 + "...");
	}

	@Override
	public void myFunction21() {
		System.out.println("MyInterfaceTwo: myFunction21() is executing with var21 = " + var21 + "...");
	}

	public static void main(String[] args) {
		MyClassTwo ob2 = new MyClassTwo() {
			
			@Override
			public void myFunction12() {
				System.out.println("MyInterfaceOne: myFunction12() is executing with var12 = " + var12 + "...");
			}
			
			@Override
			public void myFunction22() {
				System.out.println("MyInterfaceTwo: myFunction22() is executing with var22 = " + var22 + "...");
			}
		};
		
		ob2.myFunction11();
		ob2.myFunction12();
		ob2.myFunction21();
		ob2.myFunction22();
		System.out.println("This is a break...");
		System.out.println(ob2.myFunction13());
		System.out.println(MyInterfaceOne.myFunction14());
	}
}

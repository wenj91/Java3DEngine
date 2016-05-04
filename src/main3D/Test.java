package main3D;

public class Test {
	public int a = 0;
	
	public void printTest(){
//		a |= 1;
		a |= 2;
		a |= 4;
		System.out.println("a = " + a);
	}

	public static void main(String args[]){
		new Test().printTest();
	}
}

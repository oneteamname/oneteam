package kr.ac.hansung.spring.aop;

public class Cat implements AnimalType {

	private String myName;
	
	//@Override
	public void sound() {
		// TODO Auto-generated method stub
		System.out.println("Cat name=" + myName + ": " + "Meow!");
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

}

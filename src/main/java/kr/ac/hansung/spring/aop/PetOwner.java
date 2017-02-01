package kr.ac.hansung.spring.aop;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PetOwner {

	//@Autowired	// wiring by type
	//@Qualifier(value="qf_cat")
	@Resource(name="cat")	//wiring by name
	private AnimalType animal;
	
	public void play() {
		animal.sound();
	}
}

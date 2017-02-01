package kr.ac.hansung.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logger {
	
	@Pointcut("execution(void kr.ac.hansung.spring.aop.*.sound())")// expression
	private void selectSound(){}	// signature
	
	
	@Before("selectSound()")
	public void aboutToSound() {
		System.out.println("before: about to sound");
	}
}

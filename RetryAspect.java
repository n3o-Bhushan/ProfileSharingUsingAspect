package edu.sjsu.cmpe275.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint; // if needed
import org.aspectj.lang.annotation.Around; // if needed
import org.aspectj.lang.annotation.Aspect; // if needed
import org.springframework.core.annotation.Order;


import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

/**
 * @author bhushan
 *
 */


@Aspect
@Order(1)
public class RetryAspect {


	public static int counter = 2;
	/**
	 * @param joinPoint
	 * @return Returns from the target method
	 * @throws Throwable
	 */
	@Around("execution(* edu.sjsu.cmpe275.aop.ProfileService.*(..))")
	public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Before invoking method " + joinPoint.getSignature().getName());

		boolean flag = true;
		
		Object val = null;

		while (flag) {
			try {
				val = joinPoint.proceed();
				flag = false;
			} catch (NetworkException ex) {

				 
				if (counter > 0) {

					System.out.println("Network Failure - retrying " + counter + " more time");
					counter--;
					
				
				} else {
					
					flag = false;
					counter = 2;
					throw new NetworkException(ex.getMessage());
				}
			} catch (Exception exception) {
				flag = false;
				counter = 2;
			}
		}

		return val;
	}
}
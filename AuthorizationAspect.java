package edu.sjsu.cmpe275.aop.aspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint; // if needed
import org.aspectj.lang.annotation.Aspect; // if needed
import org.aspectj.lang.annotation.Before; // if needed
import org.springframework.core.annotation.Order;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;

/**
 * @author bhushan
 *
 */
@Aspect
@Order(0)
public class AuthorizationAspect {
	public static Map<String, List<String>> profileMap = new HashMap<String, List<String>>();


	@Before("execution(public void edu.sjsu.cmpe275.aop.ProfileService.shareProfile(..))")
	public void beforeSharing(JoinPoint joinPoint) throws Throwable {
		
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		Object[] arguments = joinPoint.getArgs();
		String userId = (String) arguments[0]; 
		String profileUserId = (String) arguments[1];
												
		
	
		if (!userId.equals(profileUserId)) {
			List<String> sharedProfileList = profileMap.get(userId);
			
			
			
			if (sharedProfileList == null || !sharedProfileList.contains(profileUserId)) {
				
				throw new AccessDeniedExeption(userId+" can not access"+profileUserId +"'s profile as"+ profileUserId+"'s profile is not shared with " + userId);
			}
		}

	}

	
	@Before("execution(* edu.sjsu.cmpe275.aop.ProfileService.readProfile(..))")
	public void beforeReading(JoinPoint joinPoint) throws Throwable {
		
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		Object[] arguments = joinPoint.getArgs();
		String userId = (String) arguments[0]; 
		String profileUserId = (String) arguments[1];
												
		
	
		if (!userId.equals(profileUserId)) {
			
			List<String> sharedProfileList = profileMap.get(userId);
			if (sharedProfileList == null || !sharedProfileList.contains(profileUserId)) {
				throw new AccessDeniedExeption(userId+" can not access"+profileUserId +"'s profile as"+ profileUserId+"'s profile is not shared with " + userId);
			}
		}
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.ProfileService.unshareProfile(..))")
	public void beforeUnsharing(JoinPoint joinPoint) throws Throwable {
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		Object[] arguments = joinPoint.getArgs();
		String userId = (String) arguments[0]; 
		String target = (String) arguments[1];
												
		
		
		if (!userId.equals(target)) {
			
			List<String> sharedProfileList = profileMap.get(target);
			if (sharedProfileList == null || !sharedProfileList.contains(userId)) {
				throw new AccessDeniedExeption(userId + "'s profile is not shared with " + target);
			}
		}
	}

	


}

package net.sourceforge.jpotpourri.jpotspect.memo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public aspect MemoAspect {

	private final Map<String, Map<ArgumentWrapper, Object>> data = new HashMap<String, Map<ArgumentWrapper,Object>>();
	
	
	pointcut memoMethod() :
		call(@Memorizable * *.*(**, ..)); // at least one

	
	@SuppressAjWarnings
	Object around() : memoMethod() {
		final String key = makeKey((MethodSignature) thisJoinPoint.getSignature());
//		System.out.println("MemoAspect. key=[" + key + "] params=" + Arrays.toString(thisJoinPoint.getArgs()));
		
		if(this.data.get(key) == null) {
			this.data.put(key, new HashMap<ArgumentWrapper, Object>());
		}
		
		final ArgumentWrapper args = new ArgumentWrapper(thisJoinPoint.getArgs());
		final Map<ArgumentWrapper, Object> memo = this.data.get(key);
		final Object storedResult = memo.get(args);
		
		if(storedResult != null) {
//			System.out.println("MemoAspect. Returning stored result.");
			return storedResult;
		}
		
//		System.out.println("MemoAspect. Calculating for first time.");
		Object newResult = proceed();
		memo.put(args, newResult);
		return newResult;
	}
	
	/**
	 * @param signature of the method
	 * @return a unique string for the given method (even if its overloaded)
	 */
	private static String makeKey(final MethodSignature signature) {
		final StringBuilder params = new StringBuilder();
		for (Class<?> paramType : signature.getParameterTypes()) {
			params.append(",").append(paramType.getName());
		}
		
		final String className = signature.getDeclaringTypeName();
		final String methodName = signature.getName();
		return className + "." + methodName + "(" + params.substring(2) + ")";
	}
	
	/**
	 * Used to get a proper hash value for arrays.
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class ArgumentWrapper {
		
		private final Object[] args;
		
		private final int hashValue;
		
		public ArgumentWrapper(final Object[] args) {
			this.args = args;
			this.hashValue = Arrays.hashCode(args);
		}
		
		@Override
		public boolean equals(final Object other) {
			if((other instanceof ArgumentWrapper) == false) {
				return false;
			}
			final ArgumentWrapper that = (ArgumentWrapper) other;
			return Arrays.equals(this.args, that.args);
		}
		
		@Override
		public int hashCode() {
			return this.hashValue;
		}
	}
	
}

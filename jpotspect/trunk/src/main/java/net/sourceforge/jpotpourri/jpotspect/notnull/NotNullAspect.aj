package myannotation;

import net.sourceforge.jpotpourri.jpotspect.notnull.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public aspect NotNullAspect {

	pointcut notNull():
		// AspectJ does not -yet- support parameter pattern like ".., XX, .."
		call(* *(@net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..)) ||
		call(* *(*, *, *, *, *, *, *, *, *, *, @net.sourceforge.jpotpourri.jpotspect.notnull.NotNull (*), ..));

	@SuppressAjWarnings
	before(): notNull() {
		final MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		final Method method = methodSignature.getMethod();
		final Annotation[][] multiAnnotations = method.getParameterAnnotations();
		
		for (int i = 0; i < multiAnnotations.length; i++) {
			for (Annotation annotation : multiAnnotations[i]) {
				if(annotation.annotationType() != NotNull.class) {
					continue; // skip other annotations than @NotNull
				}
				
				final Object paramValue = thisJoinPoint.getArgs()[i]; 
				if(paramValue == null) {
					final String paramName = methodSignature.getParameterNames()[i];
					final String className = thisJoinPoint.getSignature().getDeclaringTypeName();
					final String methodName = thisJoinPoint.getSignature().getName();
					throw new NullPointerException(className + "." + methodName + "() - Argument [" + paramName + "] is null!");
				}
				break; // found proper annotation for parameter; stop looking for any another annotation
			}
		}
	}
}

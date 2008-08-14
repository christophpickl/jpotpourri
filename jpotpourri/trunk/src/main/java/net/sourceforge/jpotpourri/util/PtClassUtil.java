package net.sourceforge.jpotpourri.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtClassUtil {
	
	private static final Map<Class<?>, Class<?>> COMPLEX_TO_PRIMITIVE_MAP;
	static {
		final Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();

		tmp.put(Boolean.class, boolean.class);
		tmp.put(Integer.class, int.class);
		tmp.put(Long.class,    long.class);
		tmp.put(Float.class,   float.class);
		tmp.put(Double.class,  double.class);
		
		COMPLEX_TO_PRIMITIVE_MAP = Collections.unmodifiableMap(tmp);
	}
	
	private PtClassUtil() {
		// no instantiation
	}
	

	public static Class<?> convertToPrimitiveClass(final Class<?> complexClass) {
		if(COMPLEX_TO_PRIMITIVE_MAP.containsKey(complexClass) == false) {
			throw new IllegalArgumentException("Class [" + complexClass.getName() + "] got no little brother!");
		}
		return COMPLEX_TO_PRIMITIVE_MAP.get(complexClass);
	}
	
	public static boolean areClassesPrimitiveAndComplex(final Class<?> clazz1, final Class<?> clazz2) {
		if((clazz1.isPrimitive() ^ clazz2.isPrimitive()) == false) {
			return false;
		}
		
		final Class<?> primitiveClass = (clazz1.isPrimitive() ? clazz1 : clazz2);
		final Class<?> complexClass =   (clazz1.isPrimitive() ? clazz2 : clazz1);
		
		return COMPLEX_TO_PRIMITIVE_MAP.get(complexClass) == primitiveClass;
	}
}

package net.sourceforge.jpotpourri.jpotface.memory;

import java.lang.reflect.Method;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import net.sourceforge.jpotpourri.util.PtClassUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtMemoryEnabler {

	private static final Log LOG = LogFactory.getLog(PtMemoryEnabler.class);

	// FEATURE enableMemoryOn shortcut for own classes also (not only JTextField/JCheckBox) and 
	//         store their getter/setter-names within a certain datastructure
	
	private PtMemoryEnabler() {
		// no instantiation
	}

	// JTextField
	public static void enableMemoryOn(
		final String key,
		final JTextField target,
		final PtAbstractMemoryStorage memory
		) {
		enableMemoryOn(key, target, memory, "");
	}
	public static void enableMemoryOn(
			final String key,
			final JTextField target,
			final PtAbstractMemoryStorage memory,
			final String defaultValue
			) {
		enableMemoryOn(key, target, memory, "getText", "setText", String.class, false, defaultValue);
	}

	// JCheckBox
	public static void enableMemoryOn(
		final String key,
		final JCheckBox target,
		final PtAbstractMemoryStorage memory
		) {
		enableMemoryOn(key, target, memory, false);
	}
	public static void enableMemoryOn(
			final String key,
			final JCheckBox target,
			final PtAbstractMemoryStorage memory,
			final boolean defaultValue
			) {
		enableMemoryOn(key, target, memory, "isSelected", "setSelected", Boolean.class, true, defaultValue);
	}
	
	public static void enableMemoryOn(
		final String key,
		final Object target,
		final PtAbstractMemoryStorage memory,
		final String getterMethodName,
		final String setterMethodName,
		final Class<?> propertyClass,
		final boolean isPrimitive,
		final Object defaultValue
	) {
		if(memory == null || target == null || getterMethodName == null || 
			setterMethodName == null || propertyClass == null || defaultValue == null) {
			throw new NullPointerException();
		}
		
		if(memory.isValidPropertyClass(propertyClass) == false) {
			final String errMsg = "Invalid property class [" + propertyClass.getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		if(checkClassEquality(defaultValue.getClass(), propertyClass) == false) {
			final String errMsg = "DefaultValue class (" + defaultValue.getClass().getSimpleName() + ") " +
								  "does not match Property class (" + propertyClass.getSimpleName() + ")!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		final Class<?> targetClass = target.getClass();
		final Method getterMethod = fetchMethod(targetClass, getterMethodName, isPrimitive, null);
		if(checkClassEquality(getterMethod.getReturnType(), propertyClass) == false) {
			final String errMsg = "Getter method must return something of class [" + propertyClass.getName() + "] " +
					"but does return [" + getterMethod.getReturnType().getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		final Method setterMethod = fetchMethod(targetClass, setterMethodName, isPrimitive, propertyClass);
		if(setterMethod.getReturnType() != void.class) {
			final String errMsg = "SetterMethod must return void but [" + setterMethod.getReturnType().getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		memory.addStorageItem(new PtStorageItem(
				key, target, propertyClass, getterMethod, setterMethod, isPrimitive, defaultValue));
	}
	
	private static boolean checkClassEquality(final Class<?> clazz1, final Class<?> clazz2) {
		if(clazz1 == clazz2) {
			return true;
		}
		
		if(clazz1.isPrimitive() == false && clazz2.isPrimitive() == false) {
			return false;
		}
		
		return PtClassUtil.areClassesPrimitiveAndComplex(clazz1, clazz2);
	}
	
	private static Method fetchMethod(
			final Class<?> clazz,
			final String methodName,
			final boolean isPrimitive, 
			final Class<?> parameterType
			) {
		try {
			if(parameterType == null) {
				return clazz.getMethod(methodName);
			} else {
				final Class<?> properParameterType;
				if(isPrimitive && parameterType.isPrimitive() == false) {
					properParameterType = PtClassUtil.convertToPrimitiveClass(parameterType);
				} else {
					properParameterType = parameterType;
				}
				return clazz.getMethod(methodName, properParameterType);
			}
			
		} catch (final Exception e) { // SecurityException, NoSuchMethodException
			final String errMsg = "Could not get Method [" + methodName + "] for Class [" + clazz.getName() + "] " +
					"with ParameterTypes " + (parameterType != null ? parameterType.getName() : "null") + "!";
			LOG.warn(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
	}
	
}

package net.sourceforge.jpotpourri.jpotface.memory;

/**
 * 
 * @param <T> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractMemoryStorage<T extends IPtMemoryKey<?>> {

	public abstract void enableMemoryOn(
			final T key,
			final Object target,
			final String getterMethodName,
			final String setterMethodName,
			final Class<?> propertyClass,
			final boolean isPrimitive,
			final Object defaultValue);
			
	/** save method */
	public abstract void retain();
	
	
	abstract void addStorageItem(final PtStorageItem<T> item);
	
	abstract boolean isValidPropertyClass(final Class<?> propertyClass);
}

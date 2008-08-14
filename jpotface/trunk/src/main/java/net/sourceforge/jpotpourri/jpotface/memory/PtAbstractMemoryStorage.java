package net.sourceforge.jpotpourri.jpotface.memory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractMemoryStorage {

	abstract void addStorageItem(final PtStorageItem item);
	
	/** save method */
	public abstract void retain();
	
	abstract boolean isValidPropertyClass(final Class<?> propertyClass);
}

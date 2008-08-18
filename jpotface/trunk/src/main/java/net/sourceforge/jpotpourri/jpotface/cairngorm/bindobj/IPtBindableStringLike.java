package net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj;

/**
 * @param <T> actual object type which should be stored and represented as string
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtBindableStringLike<T> extends IPtBindableObject<T> {

	void setAsString(final String value);
	
	String getAsString();
	
}

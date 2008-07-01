package net.sourceforge.jpotpourri.fprog.predsfuncs;

/**
 * @author christoph_pickl@users.sourceforge.net
 * @param <T> value type
 */
public interface IBinaryPredicate<T> {

	boolean execute(final T t1, final T t2);
	
}

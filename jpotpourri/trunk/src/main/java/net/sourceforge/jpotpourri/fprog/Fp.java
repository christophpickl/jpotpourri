package net.sourceforge.jpotpourri.fprog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.jpotpourri.fprog.predsfuncs.IBinaryFunction;
import net.sourceforge.jpotpourri.fprog.predsfuncs.IUnaryFunction;
import net.sourceforge.jpotpourri.fprog.predsfuncs.IUnaryPredicate;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class Fp {

	private Fp() {
		// no instantiation
	}
	
	public static<R, T> List<R> map(final IUnaryFunction<R, T> function, final List<T> list) {
		final List<R> result = new ArrayList<R>(list.size());
		for (final T element : list) {
			result.add(function.execute(element));
		}
		return Collections.unmodifiableList(result);
	}
	
	public static<T> List<T> filter(final IUnaryPredicate<T> predicate, final List<T> list) {
		final List<T> result = new ArrayList<T>(list.size());
		for (final T element : list) {
			if(predicate.execute(element)) {
				result.add(element);
			}
		}
		return Collections.unmodifiableList(result);
	}
	
	public static<T> T reduce(final IBinaryFunction<T, T> function, final List<T> list, final T initialValue) {
		T result = initialValue;
		for (final T element : list) {
			result = function.execute(result, element);
		}
		return result;
	}
	
}

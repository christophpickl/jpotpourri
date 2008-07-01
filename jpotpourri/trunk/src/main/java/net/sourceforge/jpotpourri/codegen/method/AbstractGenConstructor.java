package net.sourceforge.jpotpourri.codegen.method;

import java.util.List;

import net.sourceforge.jpotpourri.codegen.AbstractGenClass;
import net.sourceforge.jpotpourri.codegen.GenArgument;
import net.sourceforge.jpotpourri.codegen.GenVisibility;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class AbstractGenConstructor extends AbstractGenPseudoMethod {

	public AbstractGenConstructor(final GenVisibility visibility, final String className,
			final List<GenArgument> arguments) {
		super(visibility, className, arguments);
	}
	
	public static AbstractGenConstructor newManConstructor(final AbstractGenClass genClass, final String manClassName) {
		final AbstractGenConstructor gen = genClass.getConstructor();
		
		return new AbstractGenConstructor(gen.getVisibility(), manClassName, gen.getArguments()) {
			@Override
			protected String getBody() {
				final StringBuilder sb = new StringBuilder();
				sb.append("super(");
				boolean first = true;
				for (GenArgument arg : this.getArguments()) {
					if(first) {
						first = false;
					} else {
						sb.append(", ");
					}
					sb.append(arg.getName());
				}
				sb.append(");\n");
				return sb.toString();
			}
		};
	}
}

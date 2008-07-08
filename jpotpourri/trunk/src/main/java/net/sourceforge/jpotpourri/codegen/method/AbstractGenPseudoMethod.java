package net.sourceforge.jpotpourri.codegen.method;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.CodeUtil;
import net.sourceforge.jpotpourri.codegen.GenArgument;
import net.sourceforge.jpotpourri.codegen.GenVisibility;
import net.sourceforge.jpotpourri.codegen.IAnnotationable;
import net.sourceforge.jpotpourri.codegen.IJavaCode;
import net.sourceforge.jpotpourri.codegen.modifier.GenMethodModifier;

/**
 * Used by real method generator AND constructor, as it is a "pseduo" method.
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractGenPseudoMethod implements IJavaCode, IAnnotationable {

	// method&constructor common
	private final boolean isForMethod; // and not for constructor
	private final GenVisibility visibility;
	private final List<GenArgument> arguments;
	private final List<String> annotations = new LinkedList<String>();

	
	// method specific
	private final String methodName;
	private final String returnType;
	private final GenMethodModifier[] modifiers;
	
	
	// constructor specific
	private final String className;
	

	
	
	
	/** Constructor for method class. */
	AbstractGenPseudoMethod(final GenVisibility visibility, final String methodName,
			final String returnType, final List<GenArgument> arguments, final GenMethodModifier... modifiers) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		this.visibility = visibility;
		this.methodName = methodName;
		this.returnType = returnType;
		this.arguments = arguments;
		this.modifiers = modifiers;
		
		// for constructor
		this.isForMethod = true;
		this.className = null;
	}

	/** Constructor for constructor class. */
	AbstractGenPseudoMethod(final GenVisibility visibility, final String className,
			final List<GenArgument> arguments) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		if(className == null) {
			throw new NullPointerException("className");
		}
		this.visibility = visibility;
		this.className = className;
		this.arguments = arguments;
		
		// for method
		this.isForMethod = false;
		this.methodName = null;
		this.returnType = null;
		this.modifiers = null;
	}
	
	/**
	 * @return no need of manual indentation ("\t"); will be done automatically
	 */
	protected abstract String getBody();

	final String getUnsafeReturnType() {
		return this.returnType;
	}
	
	public final String toCode() {
		if(this.isForMethod == false && this instanceof NullGenConstructor) {
			return "";
		}
		
		final StringBuilder sb = new StringBuilder();
		
		for(String annotation : this.annotations) {
			sb.append("\t@").append(annotation).append("\n");
		}
		
		sb.append("\t").append(this.visibility.toCode());
		
		if(this.isForMethod) {
			for (GenMethodModifier modifier : this.modifiers) {
				sb.append(modifier.toCode());
			}
			
			sb.append(this.returnType).append(" ");
		}
		
		if(this.isForMethod) {
			sb.append(this.methodName);
		} else {
			sb.append(this.className);
		}
		
		sb.append("(");
		boolean first = true;
		for (GenArgument arg : this.arguments) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(arg.toCode());
		}
		
		sb.append(")");
		
		if(this instanceof AbstractAbstractGenMethod) {
			
			sb.append(";");
			
		} else {
			
			sb.append(" {"); // TODO add possible exceptions for GenMethod
	
			sb.append("\n");
			
			sb.append(CodeUtil.autoIndentCode(this.getBody(), 2));
			
			sb.append("\t}");
		}
		sb.append("\n");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public final void addAnnotation(final String textAfterAt) {
		this.annotations.add(textAfterAt);
	}
	
	final GenVisibility getVisibility() {
		return this.visibility;
	}
	
	final List<GenArgument> getArguments() {
		return this.arguments;
	}
}

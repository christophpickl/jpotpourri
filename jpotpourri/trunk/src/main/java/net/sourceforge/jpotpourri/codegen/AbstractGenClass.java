package net.sourceforge.jpotpourri.codegen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sourceforge.jpotpourri.codegen.method.AbstractGenConstructor;
import net.sourceforge.jpotpourri.codegen.method.AbstractGenMethod;
import net.sourceforge.jpotpourri.codegen.method.NullGenConstructor;
import net.sourceforge.jpotpourri.codegen.modifier.GenClassModifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class AbstractGenClass implements IJavaCode {

	private static final Log LOG = LogFactory.getLog(AbstractGenClass.class);
	
	private static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static final String CLASSNAME_GEN_SUFFIX = "Gen";
	public static final String CLASSNAME_MAN_SUFFIX = "Man";
	
	private final GenVisibility visibility;
	
	private final String className;
	
	private final String packageName;

	private final String superClass;

	private final GenClassModifier[] classModifiers;

	private final Set<String> imports = new LinkedHashSet<String>();

	private final Set<GenField> fields = new LinkedHashSet<GenField>();
	
	private final Set<AbstractGenMethod> methods = new LinkedHashSet<AbstractGenMethod>();

	private AbstractGenConstructor constructor;

	private String staticInitializer = null;

	private boolean generateManClass = false;

	
	// keywords (static, final, abstract)
	/**
	 * @param superClass can be null
	 */
	public AbstractGenClass(final GenVisibility visibility, final String className,
			final String packageName, final String superClass, final GenClassModifier... modifiers) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		if(className == null) {
			throw new NullPointerException("className");
		}
		if(className.endsWith(CLASSNAME_GEN_SUFFIX) == false &&
		   className.endsWith(CLASSNAME_MAN_SUFFIX) == false) {
			throw new IllegalArgumentException("className=" + className);
		}
		this.visibility = visibility;
		this.className = className;
		this.packageName = packageName;
		this.superClass = superClass;
		this.constructor = new NullGenConstructor();
		this.classModifiers = modifiers;
	}
	
	public static AbstractGenClass newManClass(final AbstractGenClass genClass) {
		assert(genClass.isGenerateManClass() == true);
		assert(genClass.className.endsWith(CLASSNAME_GEN_SUFFIX));
		
		final String genName = genClass.className;
		// convert from "FoobarGen" to "FoobarMan"
		final String manClassName = genName.substring(0, genName.length() - CLASSNAME_GEN_SUFFIX.length()) +
									CLASSNAME_MAN_SUFFIX;
		
		final AbstractGenClass manClass = new AbstractGenClass(GenVisibility.PUBLIC, manClassName,
				genClass.packageName, genClass.className) {
			// nothing to do
		};
		manClass.setConstructor(AbstractGenConstructor.newManConstructor(genClass, manClassName));
		return manClass;
	}
	
	protected final void addImport(final String qualifiedName) {
		this.imports.add(qualifiedName);
	}
	
	protected final void addField(final GenField field) {
		this.fields.add(field);
	}
	
	protected final void addMethod(final AbstractGenMethod method) {
		LOG.debug("adding method to class " + this.className + ": " + method);
		assert(this.methods.contains(method) == false);
		this.methods.add(method);
	}
	
	protected final void setConstructor(final AbstractGenConstructor constructor) {
		LOG.debug("Setting new constructor to: " + constructor);
		if(constructor == null) {
			throw new NullPointerException("constructor");
		}
		this.constructor = constructor;
	}

	public final AbstractGenConstructor getConstructor() {
		return this.constructor;
	}

	public final boolean isGenerateManClass() {
		return this.generateManClass;
	}
	
	public final void setGenerateManClass(final boolean generateManClass) {
		this.generateManClass = generateManClass;
	}
	
	public final String getClassName() {
		return this.className;
	}

	public final String getPackageName() {
		return this.packageName;
	}
	
	public final void setStaticInitialer(final String staticInitializer) {
		this.staticInitializer = staticInitializer;
	}

	public final String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("package ").append(this.packageName).append(";\n");
		sb.append("\n");
		
		for (String importStatement : this.imports) {
			sb.append("import ").append(importStatement).append(";\n");
		}
		sb.append("\n");

		sb.append("/**\n");
		sb.append(" * GENERATED CLASS (" + FULL_DATE_FORMAT.format(new Date()) + ")\n");
		sb.append(" *\n");
		// TODO enable individual author javadoc sb.append(" * @author christoph.pickl@at\n");
		sb.append(" */\n");
		
		sb.append(this.visibility.toCode());
		
		for (GenClassModifier modifier : this.classModifiers) {
			sb.append(modifier.toCode());
		}
		
		sb.append("class ");
		
		sb.append(this.className);
		if(this.superClass != null) {
			sb.append(" extends ").append(this.superClass);
		}
		sb.append(" {\n");

		if(this.fields.isEmpty() == false) {
			sb.append("\n");
		}
		for (GenField field : this.fields) {
			sb.append(field.toCode());
		}

		sb.append("\n");
		

		if(this.staticInitializer != null) {
			sb.append("\tstatic {\n");
			sb.append(CodeUtil.autoIndentCode(this.staticInitializer, 2));
			sb.append("\n");
			sb.append("\t}\n");
		}

		sb.append("\n");
		
		sb.append(this.constructor.toCode());
		
		for (AbstractGenMethod method : this.methods) {
			sb.append(method.toCode());
		}

		sb.append("}");
		sb.append("\n");
		
		return sb.toString();
	}
	
}

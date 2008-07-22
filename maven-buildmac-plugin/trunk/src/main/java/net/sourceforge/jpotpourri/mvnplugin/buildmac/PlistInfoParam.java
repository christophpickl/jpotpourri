package net.sourceforge.jpotpourri.mvnplugin.buildmac;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
enum PlistInfoParam {

	/** mandatory */
	MAIN_CLASS("MainClass"),
	
	/** optional, default: project.name */		
	CF_BUNDLE_GET_INFO_STRING("CFBundleGetInfoString"),
	
	/** optional, default: project.version */
	CF_BUNDLE_SHORT_VERSION_STRING("CFBundleShortVersionString"),
	
	/** optional, default: project.version */
	CF_BUNDLE_VERSION("CFBundleVersion"),
	
	/** optional, default: project.name */
	CF_BUNDLE_NAME("CFBundleName"),

	/** optional, default: 1.5+ */
	JVM_VERSION("JVMVersion");
	

	private final String xmlPomKey;

	private PlistInfoParam(final String xmlPomKey) {
		this.xmlPomKey = xmlPomKey;
	}
	
	String getXmlPomKey() {
		return this.xmlPomKey;
	}
	
}

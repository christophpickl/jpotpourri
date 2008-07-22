package net.sourceforge.jpotpourri.mvnplugin.buildmac;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
enum PlistVersionParam {

	/** optional, default: project.name */
	PROJECT_NAME("ProjectName"),
	
	/** optional, default: project.version */
	CF_BUNDLE_SHORT_VERSION_STRING("CFBundleShortVersionString");
	
	
	
	private final String xmlPomKey;

	private PlistVersionParam(final String xmlPomKey) {
		this.xmlPomKey = xmlPomKey;
	}
	
	String getXmlPomKey() {
		return this.xmlPomKey;
	}
	
}

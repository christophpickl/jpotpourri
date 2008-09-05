package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtAttributeStringDefinition<T> implements IPtNodeDefinition<T> {

	private static final String DUMMY_OBJECT = "";
	
	
	private final String xmlNodeName;
	
	private final String xmlNodeAttribute;
	
	private final MethodFetcher<T> methodFetcher;
	
	
	
	public PtAttributeStringDefinition(
			final Class<? extends T> clazz,
			final String setterMethodName,
			final String xmlNodeName,
			final String xmlNodeAttribute
		) {
		this.xmlNodeName = xmlNodeName;
		this.xmlNodeAttribute = xmlNodeAttribute;
		this.methodFetcher = new MethodFetcher<T>(clazz, setterMethodName, DUMMY_OBJECT);
	}
	
	public final String getXmlNodeName() {
		return this.xmlNodeName;
	}
	
	public final void invoke(final T alert, final Node node) {
		final Node attribute = node.getAttributes().getNamedItem(this.xmlNodeAttribute);
		final String value = attribute.getTextContent().trim();
		this.methodFetcher.invoke(alert, value);
	}
	
}

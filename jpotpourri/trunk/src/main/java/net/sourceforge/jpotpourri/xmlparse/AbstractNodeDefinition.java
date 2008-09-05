package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @param <S> setter parameter type
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractNodeDefinition<T, S> implements IPtNodeDefinition<T> {

	
	private final String xmlNodeName;
	private final MethodFetcher<T> methodFetcher; 
	
	AbstractNodeDefinition(
			final Class<? extends T> clazz,
			final S dummyObject,
			final String setterMethodName,
			final String xmlNodeName
		) {
		this.methodFetcher = new MethodFetcher<T>(clazz, setterMethodName, dummyObject);
		this.xmlNodeName = xmlNodeName;
	}
	
	public final String getXmlNodeName() {
		return this.xmlNodeName;
	}
	
	public final void invoke(final T alert, final Node node) {
		this.methodFetcher.invoke(alert, this.formatValue(node.getTextContent()));
	}
	
	abstract S formatValue(final String value);
}

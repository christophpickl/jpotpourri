package net.sourceforge.jpotpourri.xmlparse;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtCustomNodeDefinition<T> implements IPtNodeDefinition<T> {
	
	private final String xmlNodeName;
	
	public PtCustomNodeDefinition(final String xmlNodeName) {
		this.xmlNodeName = xmlNodeName;
	}
	
	public final String getXmlNodeName() {
		return this.xmlNodeName;
	}
}

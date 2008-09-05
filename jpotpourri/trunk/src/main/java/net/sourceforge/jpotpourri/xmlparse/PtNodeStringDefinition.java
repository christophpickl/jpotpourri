package net.sourceforge.jpotpourri.xmlparse;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtNodeStringDefinition<T> extends AbstractNodeDefinition<T, String> {
	
	private static final String DUMMY_OBJECT = "";
	
	
	public PtNodeStringDefinition(
			final Class<? extends T> clazz,
			final IPtNodeName xmlNodeName,
			final String setterMethodName
		) {
		super(clazz, DUMMY_OBJECT, xmlNodeName, setterMethodName);
	}
	
	@Override
	final String formatValue(final String value) {
		return value;
	}
	
}

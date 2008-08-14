package net.sourceforge.jpotpourri.jpotface.memory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtPreferencesMemoryStorage extends PtAbstractMemoryStorage {

	private static final Log LOG = LogFactory.getLog(PtPreferencesMemoryStorage.class);
	
	private static final Preferences PREF = Preferences.userNodeForPackage(PtPreferencesMemoryStorage.class);
	
	private static final Map<Class<?>, PreferencesMethod> PREF_CLASS_STORE;
	static {
		final Map<Class<?>, PreferencesMethod> tmp = new HashMap<Class<?>, PreferencesMethod>();

		try {
			tmp.put(Boolean.class, new PreferencesMethod(PREF,
					// Preferences.getBoolean(String key, boolean def):boolean
					Preferences.class.getDeclaredMethod("getBoolean", String.class, boolean.class),
					// Preferences.putBoolean(String key, boolean value):void
					Preferences.class.getDeclaredMethod("putBoolean", String.class, boolean.class)));
			
			tmp.put(int.class, new PreferencesMethod(PREF,
					// Preferences.getInt(String key, int def):int
					Preferences.class.getDeclaredMethod("getInt", String.class, int.class),
					// Preferences.putInt(String key, int value):void
					Preferences.class.getDeclaredMethod("putInt", String.class, int.class)));
			
			tmp.put(long.class, new PreferencesMethod(PREF,
					// Preferences.getLong(String key, long def):long
					Preferences.class.getDeclaredMethod("getLong", String.class, long.class),
					// Preferences.putLong(String key, long value):void
					Preferences.class.getDeclaredMethod("putLong", String.class, long.class)));
			
			tmp.put(float.class, new PreferencesMethod(PREF,
					// Preferences.getFloat(String key, float def):float
					Preferences.class.getDeclaredMethod("getFloat", String.class, float.class),
					// Preferences.putFloat(String key, float value):void
					Preferences.class.getDeclaredMethod("putFloat", String.class, float.class)));
			
			tmp.put(double.class, new PreferencesMethod(PREF,
					// Preferences.getDouble(String key, double def):double
					Preferences.class.getDeclaredMethod("getDouble", String.class, double.class),
					// Preferences.putDouble(String key, double value):void
					Preferences.class.getDeclaredMethod("putDouble", String.class, double.class)));
			
			tmp.put(String.class, new PreferencesMethod(PREF,
					// Preferences.get(String key, String def):String
					Preferences.class.getDeclaredMethod("get", String.class, String.class),
					// Preferences.put(String key, String value):void
					Preferences.class.getDeclaredMethod("put", String.class, String.class)));
			
		} catch(final Exception e) {
			final String errMsg = "Internal Error: Could not get Preferences methods!";
			LOG.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
		PREF_CLASS_STORE = Collections.unmodifiableMap(tmp);
	}
	
	private static int currentKeyIndex = 0;
	
	
	private final Set<StorageItemX> items = new HashSet<StorageItemX>();
	
	
	
	@Override
	public void retain() {
		LOG.info("retain() " + this.items.size() + " items");
		
		for (final StorageItemX itemX : this.items) {
			final PreferencesMethod prefMethod = PREF_CLASS_STORE.get(itemX.getItem().getPropertyClass());
			try {
				final PtStorageItem item = itemX.getItem();
				final Method getterMethod = item.getGetterMethod();
				
				LOG.trace("Invoking getter method [" + getterMethod.getName()  + "] " +
						"for target [" + item.getTarget().getClass().getName() + "].");
				final Object value = getterMethod.invoke(item.getTarget());
				LOG.trace("Setting value to [" + value + "] for key [" + itemX.getKey() + "].");
				prefMethod.setValue(itemX.getKey(), value);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not set value for item [" + itemX + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
	}

	@Override
	void addStorageItem(final PtStorageItem item) {
		LOG.info("addStorageItem(item=" + item + ")");
		
		final String prefKey = "KEY_" + currentKeyIndex;
		// set initial value
		try {

			LOG.trace("Getting PreferencesMethod for property class [" + item.getPropertyClass().getName() + "].");
			final PreferencesMethod method = PREF_CLASS_STORE.get(item.getPropertyClass());
			
			final Object value = method.getValue(prefKey, item.getDefaultValue());
			LOG.trace("Preferences value for key [" + prefKey + "] returned value [" + value + "] " +
					"with default [" + item.getDefaultValue() + "].");
			
			
			item.getSetterMethod().invoke(item.getTarget(), value);
			
		} catch (final Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
			final String errMsg = "Could not set default value via setter method for [" + item + "]!";
			LOG.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
		this.items.add(new StorageItemX(item, prefKey));
		currentKeyIndex++;
	}
	
	@Override
	boolean isValidPropertyClass(final Class<?> propertyClass) {
		return PREF_CLASS_STORE.keySet().contains(propertyClass);
	}

	
	
	
	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class PreferencesMethod {
		
		private final Preferences preferences;
		
		private final Method getMethod;
		
		private final Method setMethod;
		
		private PreferencesMethod(final Preferences preferences, final Method getMethod, final Method setMethod) {
			this.preferences = preferences;
			this.getMethod = getMethod;
			this.setMethod = setMethod;
		}
		
		public Object getValue(final String key, final Object defaultValue) {
			try {
				return this.getMethod.invoke(this.preferences, key, defaultValue);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not get value for method [" + this.getMethod.getName() + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
		
		public void setValue(final String key, final Object value) {
			try {
				this.setMethod.invoke(this.preferences, key, value);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not set value for method [" + this.setMethod.getName() + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
	}
	
	
	
	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class StorageItemX {
		
		private final PtStorageItem item;
		
		private final String key;

		private StorageItemX(final PtStorageItem item, final String key) {
			this.item = item;
			this.key = key;
		}
		
		@Override
		public String toString() {
			return "StorageItemX[key=" + this.key + ";item=" + this.item + "]";
		}

		public PtStorageItem getItem() {
			return this.item;
		}

		public String getKey() {
			return this.key;
		}
		
	}
	
}

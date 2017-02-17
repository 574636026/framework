package com.injedu.core.supports.excel.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class StringTypeRegistry {

	private static final Map<String, Class<?>> _registry;
	private static final Map<String, Class<?>> registry;

	private StringTypeRegistry() {

	}

	static {
		_registry = new HashMap<String, Class<?>>();
		_registry.put("java.lang.Intger", Integer.class);
		_registry.put("int", Integer.class);
		_registry.put("java.lang.Long", Long.class);
		_registry.put("long", Long.class);
		_registry.put("java.lang.Byte", Byte.class);
		_registry.put("byte", Byte.class);
		_registry.put("java.lang.Float", Float.class);
		_registry.put("float", Float.class);
		_registry.put("java.lang.Double", Double.class);
		_registry.put("double", Float.class);
		_registry.put("java.lang.Character", Character.class);
		_registry.put("char", Character.class);
		_registry.put("string", String.class);
		_registry.put("java.lang.String", String.class);
		// _registry.put("date", java.util.Date.class);
		registry = Collections.unmodifiableMap(_registry);

	}

	public static Class<?> getClass(String classText) {
		return registry.get(classText);
	}
}

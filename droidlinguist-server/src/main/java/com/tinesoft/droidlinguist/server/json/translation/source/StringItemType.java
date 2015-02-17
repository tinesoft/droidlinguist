package com.tinesoft.droidlinguist.server.json.translation.source;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum listing the possible string items that can be found in the strings'
 * resources xml file.
 * 
 * @author Tine Kondo
 *
 */
public enum StringItemType {
	STRING("string"), //
	STRING_ARRAY("string-array"), //
	PLURALS("plurals");

	private static Map<String, StringItemType> MAP = new HashMap<>();

	static {
		MAP.put("string", STRING);
		MAP.put("string-array", STRING_ARRAY);
		MAP.put("plurals", PLURALS);

	}

	private final String name;

	private StringItemType(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static StringItemType getByName(final String name) {
		return MAP.get(name);
	}

}

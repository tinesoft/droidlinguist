package com.tinesoft.droidlinguist.server.json.translation.target;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum defining the possibles states of a translation file.
 * 
 * @author Tine Kondo
 *
 */
public enum TranslationState {
	REQUEST_TRANSLATION("rqt"), //
	AWAITING_VALIDATION("awv"), //
	REJECTED("rej"), //
	VALIDATED("vld"), //
	COMPLETED("cpd");

	private final String code;

	private static Map<String, TranslationState> MAP = new HashMap<>();

	static {
		MAP.put("rqt", REQUEST_TRANSLATION);
		MAP.put("awv", AWAITING_VALIDATION);
		MAP.put("rej", REJECTED);
		MAP.put("vld", VALIDATED);
		MAP.put("cpd", COMPLETED);
	}

	private TranslationState(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static TranslationState getbyCode(final String code) {
		return MAP.get(code);
	}

}

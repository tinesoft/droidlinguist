package com.tinesoft.droidlinguist.server.translator.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Enum} listing the translators engines supported
 * 
 * @author Tine Kondo
 *
 */
public enum TranslatorType
{
	YANDEX("Yandex Translate API", "yandex", true, "Powered by ", "http://translate.yandex.com/"), //
	MICROSOFT("Microsoft Translator API", "microsoft", true, "Translated by ", "http://aka.ms/MicrosoftTranslatorAttribution"), //
	GOOGLE("Google Translate API", "google", false, "Powered by ", "http://translate.google.com"), //
	NO_TRANSLATOR("Yandex Translate API", "no", true, "Powered by ", ""), //
	FAKE_TRANSLATOR("Yandex Translate API", "fake", false, "Powered by ", "");

	private final String name;
	private final String code;
	private final boolean enabled;
	private final String credits;
	private final String url;

	private static final Map<String, TranslatorType> MAP = new HashMap<>();
	static
	{
		MAP.put("yandex", YANDEX);
		MAP.put("microsoft", MICROSOFT);
		MAP.put("google", GOOGLE);
		MAP.put("no", NO_TRANSLATOR);
		MAP.put("fake", FAKE_TRANSLATOR);
	}

	private TranslatorType(String name, String code, boolean enabled, String credits, String url)
	{
		this.name = name;
		this.code = code;
		this.enabled = enabled;
		this.credits = credits;
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public String getCode()
	{
		return code;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public String getCredits()
	{
		return credits;
	}

	public String getUrl()
	{
		return url;
	}

	public static TranslatorType getByCode(String code)
	{
		return MAP.getOrDefault(code, NO_TRANSLATOR);

	}

}

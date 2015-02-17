package com.tinesoft.droidlinguist.server.translator.api.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * {@link Translator} implementation that does nothing. Returns an empty string
 * or list.
 * 
 * @author Tine Kondo
 *
 */
@Component
public class NoTranslator implements Translator
{

	private static final String EMPTY_STRING = "";

	@Override
	public boolean isLangSupported(String langCode)
	{
		// all languages are supported, since this translator does nothing!
		return true;
	}

	@Override
	public TranslatorType getType()
	{
		return TranslatorType.NO_TRANSLATOR;
	}

	@Override
	public String detectLanguage(String text, String... options)
	{
		return null;
	}

	@Override
	public List<String> getSupportedLanguages(String... options)
	{
		return Collections.emptyList();
	}

	@Override
	public String translate(String text, String sourceLang, String targetLang, String... options)
	{
		return EMPTY_STRING;
	}

	@Override
	public List<String> translate(List<String> texts, String sourceLang, String targetLang, String... options)
	{
		List<String> result = new ArrayList<String>(texts.size());
		for (String t : texts)
			result.add(EMPTY_STRING);
		return result;

	}

}

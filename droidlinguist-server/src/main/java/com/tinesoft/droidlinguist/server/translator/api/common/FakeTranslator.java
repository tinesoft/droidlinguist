package com.tinesoft.droidlinguist.server.translator.api.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Fake {@link Translator} for testing. Returns the source text (prefixed with
 * target language) as result of translation operations.
 * 
 * @author Tine Kondo
 *
 */
@Component
public class FakeTranslator implements Translator
{

	@Override
	public boolean isLangSupported(String langCode)
	{
		// fake translator supports all languages
		return true;
	}

	@Override
	public TranslatorType getType()
	{
		return TranslatorType.FAKE_TRANSLATOR;
	}

	@Override
	public String detectLanguage(String text, String... options)
	{
		return null;
	}

	@Override
	public List<String> getSupportedLanguages(String... options)
	{
		return null;
	}

	@Override
	public String translate(String text, String sourceLang, String targetLang, String... options)
	{
		return "[" + targetLang + "] " + text;
	}

	@Override
	public List<String> translate(List<String> texts, String sourceLang, String targetLang, String... options)
	{
		List<String> result = new ArrayList<String>(texts.size());
		for (String text : texts)
		{
			result.add("[" + targetLang + "] " + text);
		}
		return result;
	}

}

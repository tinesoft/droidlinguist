package com.tinesoft.droidlinguist.server.translator.api.common;

import java.util.List;

import com.tinesoft.droidlinguist.server.translator.api.microsoft.MicrosoftTranslator;
import com.tinesoft.droidlinguist.server.translator.api.yandex.YandexTranslator;

/**
 * Common interface for Translator Engines.
 * 
 * @author Tine Kondo
 * @see {@link MicrosoftTranslator}, {@link YandexTranslator}
 *
 */
public interface Translator
{
	/**
	 * Returns <code>true</code> if the given language is supported by the
	 * translator.
	 * 
	 * @param langCode
	 * @return
	 */
	boolean isLangSupported(String langCode);

	/**
	 * Returns the type of translator. Possible values are defined in enum
	 * {@link TranslatorType}
	 * 
	 * @return
	 */
	public TranslatorType getType();

	/**
	 * Detects the language of the specified text.
	 * 
	 * @param text
	 *            The text to detect the language for.
	 * @param options
	 *            Any additional options (depend on the implementation)
	 * @return
	 */
	String detectLanguage(String text, String... options);

	/**
	 * Returns a list of translation directions supported by the service.
	 * 
	 * @param options
	 *            Any additional options (depend on the implementation)
	 * @return
	 */
	List<String> getSupportedLanguages(String... options);

	/**
	 * Translates the text.
	 * 
	 * @param text
	 *            The text to translate
	 * @param sourceLang
	 *            The source language
	 * @param targetLang
	 *            The target language
	 * @param options
	 *            Any additional options (depend on the implementation)
	 * @return
	 */
	String translate(String text, String sourceLang, String targetLang, String... options);

	/**
	 * Translates the texts.
	 * 
	 * @param text
	 *            The texts to translate
	 * @param sourceLang
	 *            The source language
	 * @param targetLang
	 *            The target language
	 * @param options
	 *            Any additional options (depend on the implementation)
	 * @return
	 */
	List<String> translate(List<String> texts, String sourceLang, String targetLang, String... options);

}

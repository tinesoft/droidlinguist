package com.tinesoft.droidlinguist.server.translator.api.yandex;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.tinesoft.droidlinguist.server.config.TranslatorProperties;
import com.tinesoft.droidlinguist.server.translator.api.common.Translator;
import com.tinesoft.droidlinguist.server.translator.api.common.TranslatorType;

/**
 * {@link Translator} implementation using Yandex Translator API.
 * 
 * @author Tine Kondo
 * @see <a href="https://tech.yandex.com/translate/">https://tech.yandex.com/

 *      translate/</a>
 */
@Component
@EnableConfigurationProperties(TranslatorProperties.class)
public class YandexTranslator implements Translator
{
	private static final Logger LOG = LoggerFactory.getLogger(YandexTranslator.class);

	public static final String GETLANGS_URI = "https://translate.yandex.net/api/v1.5/tr.json/getLangs";
	public static final String DETECT_URI = "https://translate.yandex.net/api/v1.5/tr.json/detect";
	public static final String TRANSLATE_URI = "https://translate.yandex.net/api/v1.5/tr.json/translate";

	// https://tech.yandex.com/translate/doc/dg/concepts/langs-docpage/
	public static final List<String> SUPPORTED_LANGS = Arrays.asList("sq", "en", "ar", "hy", "az", "af", "eu", "be", "bg", "bs", "cy", "vi", "hu", "ht", "gl",
			"nl", "el", "ka", "da", "he", "id", "ga", "it", "is", "es", "kk", "ca", "ky", "zh", "ko", "la", "lv", "lt", "mg", "ms", "mt", "mk", "mn", "de",
			"no", "fa", "pl", "pt", "ro", "ru", "sr", "sk", "sl", "sw", "tg", "th", "tl", "tt", "tr", "uz", "uk", "fi", "fr", "hr", "cs", "sv", "et", "ja");

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TranslatorProperties config;

	@Override
	public boolean isLangSupported(String langCode)
	{
		return SUPPORTED_LANGS.contains(langCode);
	}

	@Override
	public TranslatorType getType()
	{
		return TranslatorType.YANDEX;
	}

	/**
	 * The <code>options</code> parameter when provided, defines the text
	 * format. Possible values are:
	 * <ul>
	 * <li><b>plain</b> - Text without markup (default value).</li>
	 * <li><b>html</b> - Text in HTML format.</li>
	 * </ul>
	 */
	@Override
	public String detectLanguage(String text, String... options)
	{
		LOG.debug("Calling dectectLanguage() with text={}, options={}", text, options);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(3);
		String format = (options.length > 0) ? options[0] : config.getYandex().getOptions().getFormat();

		params.add("key", config.getYandex().getApiKey());
		params.add("text", text);
		params.add("format", format);

		URI uri = new UriTemplate(DETECT_URI).expand();
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)//
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.body(params);

		ResponseEntity<DetectedLang> response = restTemplate.exchange(request, DetectedLang.class);
		DetectedLang detectedLang = response.getBody();
		return detectedLang.getLang();
	}

	/**
	 * The <code>options</code> parameter when provided, defines the ui language
	 * used to display supported languages names. Possible values are:
	 * <ul>
	 * <li>en - in English</li>
	 * <li>ru - in Russian</li>
	 * <li>tr - in Turkish</li>
	 * <li>uk - in Ukrainian</li>
	 * </ul>
	 */

	@Override
	public List<String> getSupportedLanguages(String... options)
	{
		LOG.debug("Calling getSupportedLanguages() with options={}", (Object[]) options);

		String ui = (options.length > 0) ? "&ui={ui}" : config.getYandex().getOptions().getUiLang();

		URI uri = new UriTemplate(GETLANGS_URI + "?key={key}" + ui).expand(config.getYandex().getApiKey(), options);
		RequestEntity<Void> request = RequestEntity.get(uri).build();

		ResponseEntity<SupportedLangs> response = restTemplate.exchange(request, SupportedLangs.class);
		SupportedLangs supportedLangs = response.getBody();
		return new ArrayList<>(supportedLangs.getLangs().keySet());
	}

	/**
	 * The <code>options</code> parameter when provided, defines the text format
	 * (at <code>options[0]</code>) and the language auto-detection (at
	 * <code>options[1]</code>). <br/>
	 * For text format, possible values are:
	 * <ul>
	 * <li><b>plain</b> - Text without markup (default value).</li>
	 * <li><b>html</b> - Text in HTML format.</li>
	 * </ul>
	 * For language auto detection, possible values are : <br/>
	 * <ul>
	 * <li><b>1</b> -</li>
	 * <li><b>0</b></li>
	 * </ul>
	 */
	@Override
	public String translate(String text, String sourceLang, String targetLang, String... options)
	{
		List<String> translatedTexts = translate(Arrays.asList(text), sourceLang, targetLang, options);
		return !translatedTexts.isEmpty() ? translatedTexts.get(0) : null;
	}

	/**
	 * The <code>options</code> parameter when provided, defines the text format
	 * (at <code>options[0]</code>) and the language auto-detection (at
	 * <code>options[1]</code>). <br/>
	 * For text format, possible values are:
	 * <ul>
	 * <li><b>plain</b> - Text without markup (default value).</li>
	 * <li><b>html</b> - Text in HTML format.</li>
	 * </ul>
	 * For language auto detection, possible values are : <br/>
	 * <ul>
	 * <li><b>1</b> -</li>
	 * <li><b>0</b></li>
	 * </ul>
	 */
	@Override
	public List<String> translate(List<String> texts, String sourceLang, String targetLang, String... options)
	{
		LOG.debug("Calling translate() with texts={}, sourceLang={}, targetLang={}, options={}", new Object[] { texts, sourceLang, targetLang, options });

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(4);
		String lang = sourceLang + "-" + targetLang;
		String format = (options.length > 0) ? options[0] : config.getYandex().getOptions().getFormat();
		String autoDetectLang = (options.length > 1) ? options[1] : config.getYandex().getOptions().getAutoDetectLang();

		for (String text : texts)
			params.add("text", text);
		params.add("key", config.getYandex().getApiKey());
		params.add("lang", lang);
		params.add("format", format);
		params.add("options", autoDetectLang);

		URI uri = new UriTemplate(TRANSLATE_URI).expand();
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)//
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.body(params);

		ResponseEntity<TranslationResult> response = restTemplate.exchange(request, TranslationResult.class);
		TranslationResult translationResult = response.getBody();
		return translationResult.getTexts();
	}

}

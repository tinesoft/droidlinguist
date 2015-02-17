package com.tinesoft.droidlinguist.server.translator.api.google;

import java.net.URI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import com.tinesoft.droidlinguist.server.translator.api.google.TranslationResult.Data.DetectedLang;
import com.tinesoft.droidlinguist.server.translator.api.google.TranslationResult.Data.DetectedLanguage;
import com.tinesoft.droidlinguist.server.translator.api.google.TranslationResult.Data.Translation;

/**
 * {@link Translator} implementation using Google Translator API.
 * 
 * @author Tine Kondo
 * @see <a href="https://cloud.google.com/translate/</a>
 */
@Component
@EnableConfigurationProperties(TranslatorProperties.class)
public class GoogleTranslator implements Translator
{
	private static final Logger LOG = LoggerFactory.getLogger(GoogleTranslator.class);

	// Compares {@link DetectedLang} by descending 'confidence' factor.
	private static final Comparator<DetectedLang> LANG_COMPARATOR = Comparator.nullsLast(//
			Comparator.comparing(DetectedLang::getConfidence, Comparator.reverseOrder()));

	public static final String GETLANGS_URI = "https://www.googleapis.com/language/translate/v2/languages";
	public static final String DETECT_URI = "https://www.googleapis.com/language/translate/v2/detect";
	public static final String TRANSLATE_URI = "https://www.googleapis.com/language/translate/v2";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TranslatorProperties config;

	@Override
	public boolean isLangSupported(String langCode)
	{
		// TODO TKO: get list of supported languages
		throw new UnsupportedOperationException("Not yet implemented...");
	}

	@Override
	public TranslatorType getType()
	{
		return TranslatorType.GOOGLE;
	}

	/**
	 * The <code>options</code> parameter when provided, defines the text format
	 * (at <code>options[0]</code>) and the pretty print option (at
	 * <code>options[1]</code>). <br/>
	 * For text format, possible values are:
	 * <ul>
	 * <li><b>plain</b> - Text without markup (default value).</li>
	 * <li><b>html</b> - Text in HTML format.</li>
	 * </ul>
	 * For server results pretty print option, possible values are : <br/>
	 * <ul>
	 * <li><b>true</b> -</li>
	 * <li><b>false</b></li>
	 * </ul>
	 */
	@Override
	public String detectLanguage(String text, String... options)
	{
		LOG.debug("Calling dectectLanguage() with text={}, options={}", text, options);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(3);
		String format = (options.length > 0) ? options[0] : config.getGoogle().getOptions().getFormat();
		String prettyprint = (options.length > 1) ? options[1] : config.getGoogle().getOptions().getPrettyPrint();

		params.add("key", config.getGoogle().getApiKey());
		params.add("q", text);
		params.add("format", format);
		params.add("prettyprint", prettyprint);

		URI uri = new UriTemplate(DETECT_URI).expand();
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)//
				.header("X-HTTP-Method-Override", "GET")// to tell the Translate
														// API to treat the
														// request as GET
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.body(params);

		ResponseEntity<TranslationResult> response = restTemplate.exchange(request, TranslationResult.class);
		TranslationResult translationResult = response.getBody();
		List<DetectedLang> detectedLangs = translationResult.getData().getDetections();
		detectedLangs.sort(LANG_COMPARATOR);// sort by descending 'confidence'

		return detectedLangs.get(0).getLanguage();// to get the more accurate
	}

	/**
	 * The <code>options</code> parameter when provided, defines the target
	 * language used to display supported languages names.
	 */

	@Override
	public List<String> getSupportedLanguages(String... options)
	{
		LOG.debug("Calling getSupportedLanguages() with options={}", (Object[]) options);

		String target = (options.length > 0) ? "&target={target}" : "&target=en";

		URI uri = new UriTemplate(GETLANGS_URI + "?key={key}" + target).expand(config.getGoogle().getApiKey(), options);
		RequestEntity<Void> request = RequestEntity.get(uri).build();

		ResponseEntity<TranslationResult> response = restTemplate.exchange(request, TranslationResult.class);
		TranslationResult translationResult = response.getBody();
		List<DetectedLanguage> supportedLangs = translationResult.getData().getLanguages();

		return supportedLangs.stream().map(DetectedLanguage::getLanguage).collect(Collectors.toList());

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
		String format = (options.length > 0) ? options[0] : config.getGoogle().getOptions().getFormat();
		String prettyprint = (options.length > 1) ? options[1] : config.getGoogle().getOptions().getPrettyPrint();

		for (String text : texts)
			params.add("q", text);
		params.add("key", config.getGoogle().getApiKey());
		params.add("source", sourceLang);
		params.add("target", targetLang);
		params.add("format", format);
		params.add("prettyprint", prettyprint);

		URI uri = new UriTemplate(TRANSLATE_URI).expand();
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)//
				.header("X-HTTP-Method-Override", "GET")// to tell the Translate
														// API to treat the
														// request as GET
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.body(params);

		ResponseEntity<TranslationResult> response = restTemplate.exchange(request, TranslationResult.class);
		TranslationResult translationResult = response.getBody();
		List<Translation> translations = translationResult.getData().getTranslations();

		return translations.stream().map(Translation::getTranslatedText).collect(Collectors.toList());

	}
}

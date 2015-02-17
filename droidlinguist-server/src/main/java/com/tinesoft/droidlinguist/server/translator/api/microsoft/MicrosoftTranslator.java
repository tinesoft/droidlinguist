package com.tinesoft.droidlinguist.server.translator.api.microsoft;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
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
 * {@link Translator} implementation using Microsoft Translator API.
 * 
 * @author Tine Kondo
 * @see <a
 *      href="http://www.bing.com/dev/en-us/translator">http://www.bing.com/dev/en-us/translator</a>
 */
@Component
@EnableConfigurationProperties(TranslatorProperties.class)
public class MicrosoftTranslator implements Translator
{
	private static final Logger LOG = LoggerFactory.getLogger(MicrosoftTranslator.class);

	public static final String TOKEN_URI = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	public static final String DETECT_URI = "http://api.microsofttranslator.com/v2/Http.svc/Detect";
	public static final String TRANSLATE_URI = "http://api.microsofttranslator.com/v2/Http.svc/Translate";

	private static final Pattern TRANSLATION_STRING_RESULT = Pattern.compile("\\<string xmlns=\"[^\"]+\"\\>(.*)\\<\\/string\\>");

	// https://msdn.microsoft.com/en-us/library/hh456380.aspx
	public static final List<String> SUPPORTED_LANGS = Arrays.asList("ar", "bs-Latn", "bg", "ca", "zh-CHS", "zh-CHT", "hr", "cs", "da", "nl", "en", "et", "fi",
			"fr", "de", "el", "ht", "he", "hi", "mww", "hu", "id", "it", "ja", "sw", "tlh", "tlh-Qaak", "ko", "lv", "lt", "ms", "mt", "no", "fa", "pl", "pt",
			"otq", "ro", "ru", "sr-Cyrl", "sr-Latn", "sk", "sl", "es", "sv", "th", "tr", "uk", "ur", "vi", "cy", "yua");

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TranslatorProperties config;

	private AdmAccessToken accessToken;
	private LocalDateTime accessTokenLastUpdate;

	@Override
	public boolean isLangSupported(String langCode)
	{
		return SUPPORTED_LANGS.contains(langCode);
	}

	@Override
	public TranslatorType getType()
	{
		return TranslatorType.MICROSOFT;
	}

	@Override
	public String detectLanguage(String text, String... options)
	{
		if (hasAccessTokenExpired())
			renewAccessToken(new AdmRequestToken(config.getMicrosoft().getClientId(), config.getMicrosoft().getClientSecret()));

		URI uri = new UriTemplate(DETECT_URI + "?text={text}").expand(text);
		RequestEntity<Void> request = RequestEntity.get(uri)//
				.header(HttpHeaders.AUTHORIZATION, getAuthToken())//
				.build();

		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		return extractResultText(response.getBody(), false);
	}

	@Override
	public List<String> getSupportedLanguages(String... options)
	{
		throw new UnsupportedOperationException("Not supported by the REST API (only SOAP supports it right now...)");
	}

	@Override
	public String translate(String text, String sourceLang, String targetLang, String... options)
	{
		if (hasAccessTokenExpired())
			renewAccessToken(new AdmRequestToken(config.getMicrosoft().getClientId(), config.getMicrosoft().getClientSecret()));

		URI uri = new UriTemplate(TRANSLATE_URI + "?text={text}&from={from}&to={to}")//
				.expand(text, sourceLang, targetLang);
		RequestEntity<Void> request = RequestEntity.get(uri)//
				.header(HttpHeaders.AUTHORIZATION, getAuthToken())//
				.build();

		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		return extractResultText(response.getBody(), true);
	}

	@Override
	public List<String> translate(List<String> textes, String sourceLang, String targetLang, String... options)
	{
		// multiple translation is not yet supported by the REST API (only SOAP
		// supports it right now...), so we iterate on each text
		List<String> result = new ArrayList<String>(textes.size());

		for (String text : textes)
			result.add(translate(text, sourceLang, targetLang, options));

		return result;
	}

	public AdmAccessToken getAccessToken(AdmRequestToken requestToken)
	{

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(4);
		params.add(AdmRequestToken.CLIENT_ID, requestToken.getClientId());
		params.add(AdmRequestToken.CLIENT_SECRET, requestToken.getClientSecret());
		params.add(AdmRequestToken.GRANT_TYPE, requestToken.getGrantType());
		params.add(AdmRequestToken.SCOPE, requestToken.getScope());

		URI uri = new UriTemplate(TOKEN_URI).expand();
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)//
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.body(params);

		ResponseEntity<AdmAccessToken> response = restTemplate.exchange(request, AdmAccessToken.class);
		return response.getBody();

	}

	public synchronized void renewAccessToken(AdmRequestToken requestToken)
	{
		accessToken = getAccessToken(requestToken);
		accessTokenLastUpdate = LocalDateTime.now();
		LOG.debug("Access Token has been renewed for '{}'. Now expires in '{}s'", requestToken.getClientId(), accessToken.getExpiresIn());

	}

	public synchronized boolean hasAccessToken()
	{
		return accessToken != null;
	}

	public synchronized boolean hasAccessTokenExpired()
	{
		if (hasAccessToken())
		{
			int expiresIn = accessToken.getExpiresIn();
			return accessTokenLastUpdate.plus(Duration.ofSeconds(expiresIn)).isBefore(LocalDateTime.now());

		}
		return true;
	}

	private String getAuthToken()
	{
		return "Bearer " + accessToken.getAccessToken();

	}

	/**
	 * Extracts the wrapped text returned from Microsoft API call that looks
	 * like this:<br/>
	 * 
	 * <pre>
	 * &lt;string xmlns="http://schemas.microsoft.com/2003/10/Serialization/"&gt;text&lt;/string&gt;
	 * </pre>
	 * 
	 * @param text
	 * @param unescape
	 * @return
	 */

	private static String extractResultText(String text, boolean unescape)
	{
		Matcher m = TRANSLATION_STRING_RESULT.matcher(text);
		String result = text;
		if (m.matches())
			result = m.group(1);

		if (unescape)
			result = result.replace("&lt;", "<").replace("&gt;", ">");
		return result;
	}
}

package com.tinesoft.droidlinguist.server.translator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.tinesoft.droidlinguist.server.DroidLinguistApplication;
import com.tinesoft.droidlinguist.server.config.TranslatorProperties;
import com.tinesoft.droidlinguist.server.translator.api.microsoft.AdmAccessToken;
import com.tinesoft.droidlinguist.server.translator.api.microsoft.AdmRequestToken;
import com.tinesoft.droidlinguist.server.translator.api.microsoft.MicrosoftTranslator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DroidLinguistApplication.class)
@Ignore("deactivated because we don't want to consume our free quota everytime a build is launched")
public class MicrosoftTranslatorTest
{
	@Autowired
	private MicrosoftTranslator translator;
	@Autowired
	TranslatorProperties config;

	@Test
	public void testGetAccessToken()
	{
		AdmRequestToken requestToken = new AdmRequestToken(config.getMicrosoft().getClientId(), config.getMicrosoft().getClientSecret());
		AdmAccessToken token = translator.getAccessToken(requestToken);
		assertNotNull(token);
		assertEquals(600, token.getExpiresIn());// token expires every 10mn
	}

	@Test
	public void testRenewAccessToken()
	{
		AdmRequestToken requestToken = new AdmRequestToken(config.getMicrosoft().getClientId(), config.getMicrosoft().getClientSecret());
		translator.renewAccessToken(requestToken);
		assertTrue(translator.hasAccessToken());
		assertFalse(translator.hasAccessTokenExpired());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testGetAccessTokenFailed()
	{
		AdmRequestToken requestToken = new AdmRequestToken("droidlinguist", "INVALID_TOKEN");
		translator.getAccessToken(requestToken);
	}

	@Test
	public void testDetectLanguage()
	{
		// detect English
		String text = "This sentence is written in English. Will Microsoft Translator API detect it?";
		String detectedLang = translator.detectLanguage(text);
		assertNotNull(detectedLang);
		assertEquals("en", detectedLang);

		// detect French
		text = "Cette phrase est écrite en Français. Est-ce que Microsoft Translator API va le détecter?";
		detectedLang = translator.detectLanguage(text);
		assertNotNull(detectedLang);
		assertEquals("fr", detectedLang);
	}

	@Test
	public void testTranslate()
	{
		// translate from English to French (not so good right now..)
		String text = "This sentence is written in English. Will Microsoft Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en anglais. Microsoft Translator API il détectera ?", translatedText);

		// translate from French to English (pretty accurate :) )
		text = "Cette phrase est écrite en Français. Est-ce que Microsoft Translator API va le détecter?";
		translatedText = translator.translate(text, "fr", "en");
		assertNotNull(translatedText);
		assertEquals("This sentence is written in French. Will Microsoft Translator API detect it?", translatedText);
	}

	@Test
	public void testTranslateWithTags()
	{
		// translate a text containing tags: they should remain untouched in the
		// translated text
		String text = "This sentence is written in <b>English</b>. Will Microsoft Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en <b>anglais</b>. Microsoft Translator API il détectera ?", translatedText);
	}

	@Test(expected = HttpClientErrorException.class)
	public void testTranslateFromUnsupportedLang()
	{
		// translate from unsupported language to French
		String text = "This sentence is written in English. Will Microsoft Translator API detect it?";
		String translatedText = translator.translate(text, "xx", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en anglais. Microsoft Translator API détecter?", translatedText);

	}

	@Test(expected = HttpClientErrorException.class)
	public void testTranslateToUnsupportedLang()
	{
		// translate from French to unsupported language
		String text = "Cette phrase est écrite en Français. Est-ce que Microsoft Translator API va le détecter?";
		String translatedText = translator.translate(text, "fr", "xx");
		assertNotNull(translatedText);
		assertEquals("This sentence is written in French. Is the Microsoft Translator API will detect it?", translatedText);
	}
}

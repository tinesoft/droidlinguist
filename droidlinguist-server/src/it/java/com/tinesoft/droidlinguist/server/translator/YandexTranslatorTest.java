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
import com.tinesoft.droidlinguist.server.translator.api.yandex.YandexTranslator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DroidLinguistApplication.class)
@Ignore("deactivated because we don't want to consume our free quota everytime a build is launched")
public class YandexTranslatorTest
{
	@Autowired
	private YandexTranslator translator;
	@Autowired
	TranslatorProperties config;

	@Test
	public void testDetectLanguage()
	{
		// detect English
		String text = "This sentence is written in English. Will Yandex Translator API detect it?";
		String detectedLang = translator.detectLanguage(text);
		assertNotNull(detectedLang);
		assertEquals("en", detectedLang);

		// detect French
		text = "Cette phrase est écrite en Français. Est-ce que Yandex Translator API va le détecter?";
		detectedLang = translator.detectLanguage(text);
		assertNotNull(detectedLang);
		assertEquals("fr", detectedLang);
	}

	@Test
	public void testTranslate()
	{
		// translate from English to French (not so good right now...)
		String text = "This sentence is written in English. Will Yandex Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en anglais. Va Yandex Translator API détecter?", translatedText);

		// translate from French to English (not so good right now...)
		text = "Cette phrase est écrite en Français. Est-ce que Yandex Translator API va le détecter?";
		translatedText = translator.translate(text, "fr", "en");
		assertNotNull(translatedText);
		assertEquals("This sentence is written in French. Is Yandex Translator API will detect it?", translatedText);
	}

	@Test
	public void testTranslateWithTags()
	{
		// translate a text containing tags: they should remain untouched in the
		// translated text
		String text = "This sentence is written in <b>English</b>. Will Yandex Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en <b>anglais</b>. Va Yandex Translator API détecter?", translatedText);
	}

	@Test(expected = HttpClientErrorException.class)
	public void testTranslateFromUnsupportedLang()
	{
		// translate from unsupported language to French
		String text = "This sentence is written in English. Will Yandex Translator API detect it?";
		String translatedText = translator.translate(text, "xx", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en anglais. Yandex Translator API détecter?", translatedText);

	}

	@Test(expected = HttpClientErrorException.class)
	public void testTranslateToUnsupportedLang()
	{
		// translate from French to unsupported language
		String text = "Cette phrase est écrite en Français. Est-ce que Yandex Translator API va le détecter?";
		String translatedText = translator.translate(text, "fr", "xx");
		assertNotNull(translatedText);
		assertEquals("This sentence is written in French. Is the Yandex Translator API will detect it?", translatedText);
	}
}

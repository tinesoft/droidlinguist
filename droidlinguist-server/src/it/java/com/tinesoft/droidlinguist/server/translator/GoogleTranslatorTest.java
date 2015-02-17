package com.tinesoft.droidlinguist.server.translator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tinesoft.droidlinguist.server.DroidLinguistApplication;
import com.tinesoft.droidlinguist.server.config.TranslatorProperties;
import com.tinesoft.droidlinguist.server.translator.api.google.GoogleTranslator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DroidLinguistApplication.class)
@Ignore("we need a valid Google Translate API key for the tests to pass...")
public class GoogleTranslatorTest
{
	@Autowired
	private GoogleTranslator translator;
	@Autowired
	TranslatorProperties config;

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
		// translate from English to French (not so good right now...)
		String text = "This sentence is written in English. Will Microsoft Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertNotNull(translatedText);
		assertEquals("Cette phrase est écrite en anglais. Microsoft Translator API détecter?", translatedText);

		// translate from French to English (not so good right now...)
		text = "Cette phrase est écrite en Français. Est-ce que Microsoft Translator API va le détecter?";
		translatedText = translator.translate(text, "fr", "en");
		assertNotNull(translatedText);
		assertEquals("This sentence is written in French. Is the Microsoft Translator API will detect it?", translatedText);
	}
}

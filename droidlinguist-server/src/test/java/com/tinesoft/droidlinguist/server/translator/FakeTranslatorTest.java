package com.tinesoft.droidlinguist.server.translator;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tinesoft.droidlinguist.server.DroidLinguistApplication;
import com.tinesoft.droidlinguist.server.translator.api.common.FakeTranslator;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DroidLinguistApplication.class)
public class FakeTranslatorTest
{
	@Autowired
	private FakeTranslator translator;

	@Test
	public void testDetectLang()
	{
		// detect
		String text = "This sentence is written in English. Will Fake Translator API detect it?";
		String detectedLang = translator.detectLanguage(text);
		assertThat(detectedLang).isNull();
	}

	@Test
	public void testTranslate()
	{
		// translate from English to French
		String text = "This sentence is written in English. Will Microsoft Translator API detect it?";
		String translatedText = translator.translate(text, "en", "fr");
		assertThat(translatedText).isEqualTo("[fr] " + text);

		List<String> translatedTexts = translator.translate(Arrays.asList(text), "en", "fr");
		assertThat(translatedTexts).hasSize(1);
		assertThat(translatedTexts).contains("[fr] " + text);
	}
}

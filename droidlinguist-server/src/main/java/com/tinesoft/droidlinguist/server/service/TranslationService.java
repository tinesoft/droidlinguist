package com.tinesoft.droidlinguist.server.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinesoft.droidlinguist.server.json.translation.source.SourceItem;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationFile;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationResource;
import com.tinesoft.droidlinguist.server.tools.SourceStringsParser;
import com.tinesoft.droidlinguist.server.tools.TranslatedStringsBuilder;
import com.tinesoft.droidlinguist.server.tools.TranslationMaker;
import com.tinesoft.droidlinguist.server.translator.api.common.FakeTranslator;
import com.tinesoft.droidlinguist.server.translator.api.common.NoTranslator;
import com.tinesoft.droidlinguist.server.translator.api.common.Translator;
import com.tinesoft.droidlinguist.server.translator.api.common.TranslatorType;
import com.tinesoft.droidlinguist.server.translator.api.google.GoogleTranslator;
import com.tinesoft.droidlinguist.server.translator.api.microsoft.MicrosoftTranslator;
import com.tinesoft.droidlinguist.server.translator.api.yandex.YandexTranslator;

/**
 * Main service of the app, responsible for parsing source string.xml file,
 * building translation files...
 * 
 * @author Tine Kondo
 *
 */
@Service
public class TranslationService
{

	@Autowired
	private YandexTranslator yandexTranslator;
	@Autowired
	private MicrosoftTranslator microsoftTranslator;
	@Autowired
	private GoogleTranslator googleTranslator;
	@Autowired
	private FakeTranslator fakeTranslator;
	@Autowired
	private NoTranslator noTranslator;

	@Autowired
	private SourceStringsParser parser;
	@Autowired
	TranslatedStringsBuilder builder;
	@Autowired
	private TranslationMaker maker;

	private final Map<String, Translator> translators = new HashMap<String, Translator>();

	@PostConstruct
	private void init()
	{
		translators.put(TranslatorType.YANDEX.getCode(), yandexTranslator);
		translators.put(TranslatorType.MICROSOFT.getCode(), microsoftTranslator);
		translators.put(TranslatorType.GOOGLE.getCode(), googleTranslator);
		translators.put(TranslatorType.NO_TRANSLATOR.getCode(), noTranslator);
		translators.put(TranslatorType.FAKE_TRANSLATOR.getCode(), fakeTranslator);
	}

	public List<SourceItem> parseSourceFile(byte[] stringsXml)
	{
		return parser.parse(stringsXml);
	}

	public void buildTranslation(TranslationResource translation, HttpServletResponse response)
	{
		builder.build(translation, response);
	}

	public Map<String, TranslationFile> translate(List<SourceItem> strings, String sourceLang, List<String> targetLangs, String translator)
	{
		Map<String, TranslationFile> map = new LinkedHashMap<>(targetLangs.size());
		Translator translatorAPI = translators.getOrDefault(translator, noTranslator);
		for (String targetLang : targetLangs)
		{
			// FIXME TKO: find a better to select translator to use?
			map.put(targetLang, maker.translate(strings, sourceLang, targetLang, translatorAPI));
		}

		return map;
	}
}

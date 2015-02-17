package com.tinesoft.droidlinguist.server.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tinesoft.droidlinguist.server.json.translation.source.PluralItem;
import com.tinesoft.droidlinguist.server.json.translation.source.SourceItem;
import com.tinesoft.droidlinguist.server.json.translation.source.StringItemType;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationFile;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationState;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationStringItem;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationStringItemValue;
import com.tinesoft.droidlinguist.server.translator.api.common.NoTranslator;
import com.tinesoft.droidlinguist.server.translator.api.common.Translator;

/**
 * Component responsible for creating the translation for each source string.
 * 
 * @author Tine Kondo
 *
 */
@Component
public class TranslationMaker
{
	private static final Logger LOG = LoggerFactory.getLogger(TranslationMaker.class);

	@Autowired
	private NoTranslator noTranslator;

	public TranslationFile translate(Collection<SourceItem> strings, String sourceLang, String targetLang, Translator translator)
	{
		LOG.info("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		LOG.info(" >>> Start translating strings from '{}' to '{}' using '{}'...", new Object[] { sourceLang, targetLang, translator.getType().getName() });

		List<TranslationStringItem> translatedStrings = new ArrayList<>(strings.size());
		boolean allSourceStringsTranslated = true;

		List<String> errors = new ArrayList<String>();

		// check language support by the given translator
		boolean isLangSupported = true;
		if (!translator.isLangSupported(sourceLang))
		{
			isLangSupported = false;
			errors.add("Source Language is not (yet) supported by " + translator.getType().getName());
		}
		if (!translator.isLangSupported(targetLang))
		{
			isLangSupported = false;
			errors.add("Target Language is not (yet) supported by " + translator.getType().getName());
		}

		for (SourceItem si : strings)
		{
			List<String> sourceTexts = si.getValueAsText();
			List<String> targetTexts = si.isTranslatable() ? //
			(isLangSupported ? translator.translate(sourceTexts, sourceLang, targetLang) : //
					noTranslator.translate(sourceTexts, sourceLang, targetLang))
					: new ArrayList<String>(sourceTexts);
			List<TranslationStringItemValue> values = new ArrayList<>();

			TranslationStringItem tsi = new TranslationStringItem();
			tsi.setName(si.getName());
			tsi.setType(si.getType());
			tsi.setTranslatable(si.isTranslatable());
			tsi.setValues(values);

			boolean allItemValuesTranslated = targetTexts.stream().noneMatch(t -> StringUtils.isNotBlank(t));
			allSourceStringsTranslated &= allItemValuesTranslated;

			StringItemType type = StringItemType.getByName(si.getType());

			String state;
			switch (type)
			{
				case STRING:
					state = StringUtils.isNotBlank(targetTexts.get(0)) ? TranslationState.AWAITING_VALIDATION.getCode() : TranslationState.REQUEST_TRANSLATION
							.getCode();
					values.add(new TranslationStringItemValue(targetTexts.get(0), state));
					tsi.setValues(values);
					break;
				case STRING_ARRAY:
					for (String text : targetTexts)
					{
						state = StringUtils.isNotBlank(text) ? TranslationState.AWAITING_VALIDATION.getCode() : TranslationState.REQUEST_TRANSLATION.getCode();
						values.add(new TranslationStringItemValue(text, state));
					}
					break;
				case PLURALS:
					PluralItem pi = (PluralItem) si;
					List<PluralItem.Value> pvalues = pi.getValues();
					for (int i = 0; i < pvalues.size(); i++)
					{
						state = StringUtils.isNotBlank(targetTexts.get(i)) ? TranslationState.AWAITING_VALIDATION.getCode()
								: TranslationState.REQUEST_TRANSLATION.getCode();
						values.add(new TranslationStringItemValue(pvalues.get(i).getQuantity(), targetTexts.get(i), state));
					}
					break;
			}

			translatedStrings.add(tsi);
		}

		TranslationFile tf = new TranslationFile();
		tf.setTargetLang(targetLang);
		tf.setStrings(translatedStrings);
		tf.setErrors(errors);
		tf.setState((allSourceStringsTranslated ? TranslationState.AWAITING_VALIDATION : TranslationState.REQUEST_TRANSLATION).getCode());

		LOG.info(" <<< Done translating strings.");
		return tf;
	}
}

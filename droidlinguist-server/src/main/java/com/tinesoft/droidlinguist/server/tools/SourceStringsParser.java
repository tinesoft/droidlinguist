package com.tinesoft.droidlinguist.server.tools;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.ValidationEvent;
import javax.xml.validation.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tinesoft.droidlinguist.server.exception.SourceStringsParserException;
import com.tinesoft.droidlinguist.server.json.translation.source.PluralItem;
import com.tinesoft.droidlinguist.server.json.translation.source.SourceItem;
import com.tinesoft.droidlinguist.server.json.translation.source.StringArrayItem;
import com.tinesoft.droidlinguist.server.json.translation.source.StringItem;
import com.tinesoft.droidlinguist.server.json.translation.source.StringItemType;
import com.tinesoft.droidlinguist.server.util.EagerValidationEventCollector;
import com.tinesoft.droidlinguist.server.util.JaxbUtil;
import com.tinesoft.droidlinguist.server.xmldom.ItemType;
import com.tinesoft.droidlinguist.server.xmldom.PluralsType;
import com.tinesoft.droidlinguist.server.xmldom.Resources;
import com.tinesoft.droidlinguist.server.xmldom.StringArrayType;
import com.tinesoft.droidlinguist.server.xmldom.StringType;

/**
 * Component that parses the uploaded source strings xml file, into a list of
 * {@link SourceItem} (i.e {@link StringItem}, {@link StringArrayItem} or
 * {@link PluralItem}).
 * 
 * @author Tine Kondo
 *
 */
@Component
public class SourceStringsParser
{
	private static final Logger LOG = LoggerFactory.getLogger(SourceStringsParser.class);

	private static final String STRINGS_RESOURCES_XSD = "xsd/resources.xsd";

	private JAXBContext jaxbContext;
	private Schema validationSchema;

	public List<SourceItem> parse(byte[] stringsXml) throws SourceStringsParserException
	{
		LOG.info("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		LOG.info(" >>> Start parsing new source xml file...");

		jaxbContext = JaxbUtil.createContextIfNull(Resources.class, jaxbContext);
		validationSchema = JaxbUtil.createSchemaIfNull(STRINGS_RESOURCES_XSD, validationSchema);

		EagerValidationEventCollector validationEventHandler = new EagerValidationEventCollector();

		Resources resources = JaxbUtil.convertTo(stringsXml, jaxbContext, validationSchema, validationEventHandler);

		// check if the xml contains (fatal) errors, and throw error
		List<String> errors = checkForValidationErrors(validationEventHandler.getEvents());
		if (!errors.isEmpty())
		{
			LOG.error(" !!! Source xml is not valid. Stop parsing.");
			throw new SourceStringsParserException(errors);
		}

		List<SourceItem> strings = new ArrayList<>(resources.getStringOrStringArrayOrPlurals().size());

		for (Object o : resources.getStringOrStringArrayOrPlurals())
		{
			// filling from the "<string>" element
			if (o instanceof StringType)
			{
				StringType s = (StringType) o;
				StringItem item = new StringItem();
				item.setName(s.getName());
				item.setType(StringItemType.STRING.getName());
				item.setTranslatable(s.getTranslatable() == null ? true : s.getTranslatable());
				item.setValue(JaxbUtil.getXmlMixedContentAsString(s.getContent()));

				strings.add(item);
			}

			// filling from the "<string-array>" element
			if (o instanceof StringArrayType)
			{
				StringArrayType s = (StringArrayType) o;
				StringArrayItem item = new StringArrayItem();
				item.setName(s.getName());
				item.setType(StringItemType.STRING_ARRAY.getName());
				item.setTranslatable(s.getTranslatable() == null ? true : s.getTranslatable());
				item.setValue(new ArrayList<String>(s.getItem().size()));
				for (ItemType i : s.getItem())
				{
					item.getValues().add(JaxbUtil.getXmlMixedContentAsString(i.getContent()));
				}

				strings.add(item);
			}
			// filling from the "<plurals>" element
			if (o instanceof PluralsType)
			{
				PluralsType p = (PluralsType) o;
				PluralItem item = new PluralItem();
				item.setName(p.getName());
				item.setType(StringItemType.PLURALS.getName());
				item.setTranslatable(p.getTranslatable() == null ? true : p.getTranslatable());
				item.setValue(new ArrayList<PluralItem.Value>(p.getItem().size()));
				for (ItemType i : p.getItem())
				{
					PluralItem.Value v = new PluralItem.Value();
					v.setQuantity(i.getQuantity().value());
					v.setText(JaxbUtil.getXmlMixedContentAsString(i.getContent()));

					item.getValues().add(v);
				}

				strings.add(item);
			}
		}

		LOG.info(" <<< Done parsing source xml file.");
		return strings;
	}

	private List<String> checkForValidationErrors(ValidationEvent... events)
	{

		List<String> errors = new ArrayList<>();

		for (ValidationEvent event : events)
		{
			if (event.getSeverity() != ValidationEvent.WARNING)
			{
				String severity = (event.getSeverity() == ValidationEvent.ERROR) ? "[ERROR]: " : "[FATAL_ERROR]: ";
				String location = JaxbUtil.getErrorLocation(event);
				String message = String.format("%s %s Location: %s", severity, event.getMessage(), location);
				errors.add(message);

			}
		}

		return errors;
	}

}

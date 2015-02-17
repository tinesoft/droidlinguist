package com.tinesoft.droidlinguist.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tinesoft.droidlinguist.server.tools.SourceStringsParser;

/**
 * JAXB utility class.
 * 
 * @author Tine Kondo
 *
 */
public final class JaxbUtil
{

	private static final Logger LOG = LoggerFactory.getLogger(JaxbUtil.class);

	private static final String XMLNS_REGEXP = " xmlns:[^=]+=\"[^\"]*\"";

	/**
	 * Utility class. No public constructor
	 */
	private JaxbUtil()
	{
	}

	/**
	 * Creates the {@link JAXBContext} for the given class, if null
	 * 
	 * @param clazz
	 * @param context
	 * @return
	 */
	public static <T> JAXBContext createContextIfNull(Class<T> clazz, JAXBContext context)
	{
		if (context != null)
			return context;

		try
		{
			return JAXBContext.newInstance(clazz.getPackage().getName());
		}
		catch (JAXBException e)
		{
			LOG.error("Failed to create JaxbContext for {}", clazz.getPackage().getName(), e);
			return null;
		}

	}

	/**
	 * Converts the given xml into the corresponding T object.
	 * 
	 * @param xml
	 * @param context
	 * @param validationSchema
	 * @param validationEventHandler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertTo(byte[] xml, JAXBContext context, Schema validationSchema, ValidationEventHandler validationEventHandler)
	{
		try (ByteArrayInputStream bis = new ByteArrayInputStream(xml);)
		{
			Unmarshaller unmarshaller = context.createUnmarshaller();
			if (validationSchema != null)
				unmarshaller.setSchema(validationSchema);
			if (validationEventHandler != null)
				unmarshaller.setEventHandler(validationEventHandler);
			return (T) unmarshaller.unmarshal(bis);
		}
		catch (JAXBException | IOException e)
		{
			LOG.error("Failed to unmarshall ", e);
			return null;
		}

	}

	/**
	 * Converts the given xml object into the corresponding a string
	 * representation.
	 * 
	 * @param xmlObject
	 * @param context
	 * @param validationSchema
	 * @param validationEventHandler
	 * @return
	 */
	public static <T> String convertToString(T xmlObject, JAXBContext context, Schema validationSchema, ValidationEventHandler validationEventHandler)
	{
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1024))
		{
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (validationSchema != null)
				marshaller.setSchema(validationSchema);
			if (validationEventHandler != null)
				marshaller.setEventHandler(validationEventHandler);
			marshaller.marshal(xmlObject, baos);
			return baos.toString().replaceAll(XMLNS_REGEXP, "");// FIXME remove
																// xmlns attr

		}
		catch (JAXBException | IOException e)
		{
			LOG.error("Failed to unmarshall ", e);
			return null;
		}

	}

	/**
	 * Creates the {@link Schema} if null
	 * 
	 * @param xsd
	 * @param schema
	 * @return
	 */
	public static Schema createSchemaIfNull(String xsd, Schema schema)
	{
		if (schema != null)
			return schema;

		try
		{
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			StreamSource xsdSource = new StreamSource(SourceStringsParser.class.getClassLoader().getResourceAsStream(xsd));
			return sf.newSchema(xsdSource);
		}
		catch (SAXException e)
		{
			LOG.error("Failed to create schema at '{}' ", xsd, e);
			return null;
		}
	}

	/**
	 * Calculates a location message for the event.
	 * 
	 * {@link DefaultValidationEventHandler#getLocation(ValidationEvent)}
	 */
	public static String getErrorLocation(ValidationEvent event)
	{
		StringBuilder msg = new StringBuilder();

		ValidationEventLocator locator = event.getLocator();

		if (locator != null)
		{

			URL url = locator.getURL();
			Object obj = locator.getObject();
			Node node = locator.getNode();
			int line = locator.getLineNumber();

			if (url != null || line != -1)
			{
				msg.append("line " + line);
				if (url != null)
					msg.append(" of " + url);
			}
			else if (obj != null)
			{
				msg.append(" obj: " + obj.toString());
			}
			else if (node != null)
			{
				msg.append(" node: " + node.toString());
			}
		}
		else
		{
			msg.append("Cannot determinate the location of the error/warning");
		}

		return msg.toString();
	}

	public static String getXmlMixedContentAsString(List<Object> contents)
	{
		StringBuilder result = new StringBuilder();
		for (Object c : contents)
		{
			if (c instanceof String)
				result.append(c);
			else if (c instanceof Element)
			{
				result.append(convertNodeToString((Element) c));
			}
		}
		return result.toString();
	}

	public static String convertNodeToString(Node node)
	{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;

		try
		{
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(node), new StreamResult(writer));
			return writer.toString().replaceAll(XMLNS_REGEXP, "");// FIXME
																	// remove
																	// xmlns
																	// attr
		}
		catch (TransformerConfigurationException e)
		{
			LOG.error("Error while creating new transformer for Node {}", node, e);

		}
		catch (TransformerException e)
		{
			LOG.error("Error while transforming Node {} to string", node, e);

		}
		return null;

	}

	public static Node convertStringToNode(String xmlStr)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try
		{
			builder = factory.newDocumentBuilder();
			Node node = builder.parse(new InputSource(new StringReader(xmlStr))).getDocumentElement();
			return node;
		}
		catch (ParserConfigurationException e)
		{
			LOG.error("Error while creating the new document builder ", e);
		}
		catch (SAXException e)
		{
			LOG.error("Error while parsing the xml string '{}' ", xmlStr, e);
		}
		catch (IOException e)
		{
			LOG.error("IO Error while parsing the xml string '{}' ", xmlStr, e);
		}

		return null;
	}
}

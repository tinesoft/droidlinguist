package com.tinesoft.droidlinguist.server.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.validation.Schema;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.tinesoft.droidlinguist.server.xmldom.Resources;

public class JaxbUtilTest
{
	private static final String STRINGS_XML = "/strings.xml";
	private static final String XSD_SCHEMA = "xsd/resources.xsd";

	@Test
	public void createContext()
	{
		JAXBContext context = null;
		context = JaxbUtil.createContextIfNull(Resources.class, context);
		assertNotNull(context);
	}

	@Test
	public void createSchema() throws IOException, URISyntaxException
	{

		Schema schema = null;
		schema = JaxbUtil.createSchemaIfNull(XSD_SCHEMA, schema);
		assertNotNull(schema);
	}

	@Test
	public void convertTo() throws IOException, URISyntaxException
	{
		JAXBContext context = JaxbUtil.createContextIfNull(Resources.class, null);
		Schema schema = JaxbUtil.createSchemaIfNull(XSD_SCHEMA, null);
		byte[] xml = Files.readAllBytes(Paths.get(new ClassPathResource(STRINGS_XML).getURI()));
		Resources resources = JaxbUtil.convertTo(xml, context, schema, null);

		assertNotNull(resources);
	}

	@Test
	public void convertToString() throws IOException, URISyntaxException
	{
		JAXBContext context = JaxbUtil.createContextIfNull(Resources.class, null);
		Schema schema = JaxbUtil.createSchemaIfNull(XSD_SCHEMA, null);
		byte[] xml = Files.readAllBytes(Paths.get(new ClassPathResource(STRINGS_XML).getURI()));
		Resources resources = JaxbUtil.convertTo(xml, context, schema, null);

		assertNotNull(resources);

		String xmlStr = JaxbUtil.convertToString(resources, context, schema, null);

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ //

				"<resources>\n"
				+ //
				"    <string name=\"app_name\" translatable=\"false\">Geek &amp; Poke</string>\n"
				+ //
				"    <string name=\"app_description\">Geek &amp; Poke are a serie of comic situations  inspired by IT life, created by Oliver Widder.</string>\n"
				+ //
				"    <string name=\"authorities\" translatable=\"false\">com.tinesoft.geekandpoke</string>\n"
				+ //
				"    <string name=\"picture_search_hint\">Enter picture title...</string>\n"
				+ //
				"    <string name=\"menu_settings\">Settings</string>\n"
				+ //
				"    <string name=\"menu_share\">Share</string>\n"
				+ //
				"    <string name=\"menu_delete\">Delete</string>\n"
				+ //
				"    <string name=\"menu_search\">Search</string>\n"
				+ //
				"    <string name=\"menu_refresh\">Refresh</string>\n"
				+ //
				"    <string name=\"menu_about\">About</string>\n"
				+ //
				"    <string name=\"menu_add_favourite\">Add to favourites</string>\n"
				+ //
				"    <string name=\"menu_remove_favourite\">Remove from favourites</string>\n"
				+ //
				"    <string name=\"menu_clear_cache\">Clear Caches</string>\n"
				+ //
				"    <string name=\"picture_item_title\">How to insult a developer? How to insult a developer? How to insult a developer? </string>\n"
				+ //
				"    <string name=\"picture_item_published_on\">June 14, 2013</string>\n"
				+ //
				"    <string name=\"picture_thumbnail_imageview_description\">Picture Thumbnail</string>\n"
				+ //
				"    <string name=\"picture_imageview_description\">Picture</string>\n"
				+ //
				"    <string name=\"no_pictures\">No pictures</string>\n"
				+ //
				"    <string name=\"no_tags\">No tags</string>\n"
				+ //
				"    <string name=\"default_tag_name\">Uncategorized</string>\n"
				+ //
				"    <string name=\"loading\">Loading...</string>\n"
				+ //
				"    <string name=\"tags\">Tags</string>\n"
				+ //
				"    <string name=\"all\">All</string>\n"
				+ //
				"    <string name=\"favourites\">Favourites</string>\n"
				+ //
				"    <string-array name=\"job_type\">\n"
				+ //
				"        <item>\n"
				+ //
				"            <b>in</b>progress</item>\n"
				+ //
				"        <item>fa<p>i</p>le<b>d</b>\n"
				+ //
				"        </item>\n"
				+ //
				"        <item>\n"
				+ //
				"            <b>d</b>\n"
				+ //
				"            <p>o</p>ne</item>\n"
				+ //
				"    </string-array>\n"
				+ //
				"    <string name=\"toast_clear_cache_complete\">Caches have been cleared.</string>\n"
				+ //
				"    <string name=\"toast_no_network_connection\">No network connection found.</string>\n"
				+ //
				"    <string name=\"toast_network_error_while_fetching_pictures\">An error occured while fetching pictures. Please try again later.</string>\n"
				+ //
				"    <string name=\"toast_picture_starred\">Picture added to your favorites!</string>\n" + //
				"    <string name=\"toast_picture_unstarred\">Picture removed from your favorites.</string>\n" + //
				"    <string name=\"toast_picture_deleted\">Picture deleted.</string>\n" + //
				"    <plurals name=\"toast_pictures_deleted\">\n" + //
				"        <item quantity=\"one\">1 picture has been deleted.</item>\n" + //
				"        <item quantity=\"other\">\n" + //
				"            <p>%1$d</p> pictures have been deleted.</item>\n" + //
				"    </plurals>\n" + //
				"    <plurals name=\"toast_pictures_starred\">\n" + //
				"        <item quantity=\"one\">1 picture added to your favorites!</item>\n" + //
				"        <item quantity=\"other\">\n" + //
				"            <p>%1$d</p> pictures added to your favorites!</item>\n" + //
				"    </plurals>\n" + //
				"    <plurals name=\"toast_pictures_unstarred\">\n" + //
				"        <item quantity=\"one\">1 picture removed from your favorites.</item>\n" + //
				"        <item quantity=\"other\">\n" + //
				"            <p>%1$d</p> pictures <b>removed</b> from your favorites.</item>\n" + //
				"    </plurals>\n" + //
				"    <plurals name=\"actionbar_selected_pictures_title\">\n" + //
				"        <item quantity=\"one\">1 picture selected</item>\n" + //
				"        <item quantity=\"other\">\n" + //
				"            <b>%1$d</b> pictures selected</item>\n" + //
				"    </plurals>\n" + //
				"    <string-array name=\"job_type\">\n" + //
				"        <item>inprogress</item>\n" + //
				"        <item>failed</item>\n" + //
				"        <item>done</item>\n" + //
				"    </string-array>\n" + //
				"    <string name=\"confirm_deletion_dialog_title\">Delete?</string>\n" + //
				"    <plurals name=\"confirm_delete_pictures\">\n" + //
				"        <item quantity=\"one\">One picture will be deleted.</item>\n" + //
				"        <item quantity=\"other\">\n" + //
				"            <xliff:g example=\"15\" id=\"num_pictures\">%1$d</xliff:g> pictures will be deleted.</item>\n" + //
				"    </plurals>\n" + //
				"    <string-array name=\"job_state\">\n" + // ²
				"        <item>inprogress</item>\n" + //
				"        <item>failed</item>\n" + //
				"        <item>done</item>\n" + //
				"    </string-array>\n" + //
				"</resources>\n" + //
				"", xmlStr);
	}

}

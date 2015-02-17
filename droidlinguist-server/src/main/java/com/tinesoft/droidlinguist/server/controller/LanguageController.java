package com.tinesoft.droidlinguist.server.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinesoft.droidlinguist.server.json.Language;

@Controller
@RequestMapping(value = "/api/language")
public class LanguageController
{
	// taken from https://fr.wikipedia.org/wiki/Liste_des_codes_ISO_639-1
	// current total of active language 184/186
	private static final List<Language> LANGUAGES = Arrays.asList(//
			new Language("aa", "Afar", "Afaraf"),//
			new Language("ab", "Abkhazian", "Аҧсуа"),//
			new Language("ae", "Avestan", "Avesta"),//
			new Language("af", "Afrikaans", "Afrikaans"),//
			new Language("ak", "Akan", "Akan"),//
			new Language("am", "Amharic", "አማርኛ"),//
			new Language("an", "Aragonese", "Aragonés"),//
			new Language("ar", "Arabic", "‫العربية"),//
			new Language("as", "Assamese", "অসমীয়া"),//
			new Language("av", "Avaric", "Авар МацӀ "),//
			new Language("ay", "Aymara", "Aymar Aru"),//
			new Language("az", "Azerbaijani", "Azərbaycan Dili"),//
			new Language("ba", "Bashkir", "Башҡорт Теле"),//
			new Language("be", "Belarusian", "Беларуская"),//
			new Language("bg", "Bulgarian", "Български Език"),//
			new Language("bh", "Bihari", "भोजपुरी"),//
			new Language("bi", "Bislama", "Bislama"),//
			new Language("bm", "Bambara", "Bamanankan"),//
			new Language("bn", "Bengali", "বাংলা"),//
			new Language("bo", "Tibetan", "བོད་ཡིག"),//
			new Language("br", "Breton", "Brezhoneg"),//
			new Language("bs", "Bosnian", "Bosanski Jezik"),//
			new Language("ca", "Catalan", "Català"),//
			new Language("ce", "Chechen", "Нохчийн Мотт"),//
			new Language("ch", "Chamorro", "Chamoru"),//
			new Language("co", "Corsican", "Corsu "),//
			new Language("cr", "Cree", "ᓀᐦᐃᔭᐍᐏᐣ"),//
			new Language("cs", "Czech", "Česky "),//
			new Language("cu", "Old Church Slavonic", "Словѣньскъ"),//
			new Language("cv", "Chuvash", "Чӑваш Чӗлхи"),//
			new Language("cy", "Welsh", "Cymraeg"),//
			new Language("da", "Danish", "Dansk"),//
			new Language("de", "German", "Deutsch"),//
			new Language("dv", "Divehi", "‫ދިވެހި"),//
			new Language("dz", "Dzongkha", "རྫོང་ཁ"),//
			new Language("ee", "Ewe", "Ɛʋɛgbɛ"),//
			new Language("el", "Greek", "Ελληνικά"),//
			new Language("en", "English", "English"),//
			new Language("eo", "Esperanto", "Esperanto"),//
			new Language("es", "Spanish", "Español"),//
			new Language("et", "Estonian", "Eesti Keel"),//
			new Language("eu", "Basque", "Euskara"),//
			new Language("fa", "Persian", "‫فارسی"),//
			new Language("ff", "Fulah", "Fulfulde"),//
			new Language("fi", "Finnish", "Suomen Kieli"),//
			new Language("fj", "Fijian", "Vosa Vakaviti"),//
			new Language("fo", "Faroese", "Føroyskt"),//
			new Language("fr", "French", "Français "),//
			new Language("fy", "Western Frisian", "Frysk"),//
			new Language("ga", "Irish", "Gaeilge"),//
			new Language("gd", "Scottish Gaelic", "Gàidhlig"),//
			new Language("gl", "Galician", "Galego"),//
			new Language("gn", "Guarani", "Avañe'ẽ"),//
			new Language("gu", "Gujarati", "ગુજરાતી"),//
			new Language("gv", "Manx", "Ghaelg"),//
			new Language("ha", "Hausa", "‫هَوُسَ"),//
			new Language("he", "Hebrew", "‫עברית"),//
			new Language("hi", "Hindi", "हिन्दी "),//
			new Language("ho", "Hiri Motu", "Hiri Motu"),//
			new Language("hr", "Croatian", "Hrvatski"),//
			new Language("ht", "Haitian", "Kreyòl Ayisyen"),//
			new Language("hu", "Hungarian", "Magyar"),//
			new Language("hy", "Armenian", "Հայերեն"),//
			new Language("hz", "Herero", "Otjiherero"),//
			new Language("ia", "Interlingua", "Interlingua"),//
			new Language("id", "Indonesian", "Bahasa Indonesia"),//
			new Language("ie", "Interlingue", "Interlingue"),//
			new Language("ig", "Igbo", "Igbo"),//
			new Language("ii", "Sichuan Yi", "ꆇꉙ"),//
			new Language("ik", "Inupiaq", "Iñupiaq "),//
			new Language("io", "Ido", "Ido"),//
			new Language("is", "Icelandic", "Íslenska"),//
			new Language("it", "Italian", "Italiano"),//
			new Language("iu", "Inuktitut", "ᐃᓄᒃᑎᑐᑦ"),//
			new Language("ja", "Japanese", "日本語 (にほんご)"),//
			new Language("jv", "Javanese", "Basa Jawa"),//
			new Language("ka", "Georgian", "ქართული"),//
			new Language("kg", "Kongo", "KiKongo"),//
			new Language("ki", "Kikuyu", "Gĩkũyũ"),//
			new Language("kj", "Kwanyama", "Kuanyama"),//
			new Language("kk", "Kazakh", "Қазақ Тілі"),//
			new Language("kl", "Kalaallisut", "Kalaallisut "),//
			new Language("km", "Khmer", "ភាសាខ្មែរ"),//
			new Language("kn", "Kannada", "ಕನ್ನಡ"),//
			new Language("ko", "Korean", "한국어 (韓國語) "),//
			new Language("kr", "Kanuri", "Kanuri"),//
			new Language("ks", "Kashmiri", "कश्मीरी "),//
			new Language("ku", "Kurdish", "Kurdî "),//
			new Language("kv", "Komi", "Коми Кыв"),//
			new Language("kw", "Cornish", "Kernewek"),//
			new Language("ky", "Kirghiz", "Кыргыз Тили"),//
			new Language("la", "Latin", "Latine "),//
			new Language("lb", "Luxembourgish", "Lëtzebuergesch"),//
			new Language("lg", "Ganda", "Luganda"),//
			new Language("li", "Limburgish", "Limburgs"),//
			new Language("ln", "Lingala", "Lingála"),//
			new Language("lo", "Lao", "ພາສາລາວ"),//
			new Language("lt", "Lithuanian", "Lietuvių Kalba"),//
			new Language("lu", "Luba-Katanga", "Kiluba"),//
			new Language("lv", "Latvian", "Latviešu Valoda"),//
			new Language("mg", "Malagasy", "Fiteny Malagasy"),//
			new Language("mh", "Marshallese", "Kajin M̧ajeļ"),//
			new Language("mi", "Māori", "Te Reo Māori"),//
			new Language("mk", "Macedonian", "Македонски Јазик"),//
			new Language("ml", "Malayalam", "മലയാളം"),//
			new Language("mn", "Mongolian", "Монгол"),//
			new Language("mo", "Moldavian", "Лимба Молдовеняскэ"),//
			new Language("mr", "Marathi", "मराठी"),//
			new Language("ms", "Malay", "Bahasa Melayu "),//
			new Language("mt", "Maltese", "Malti"),//
			new Language("my", "Burmese", "ဗမာစာ"),//
			new Language("na", "Nauru", "Ekakairũ Naoero"),//
			new Language("nb", "Norwegian Bokmål", "Norsk Bokmål"),//
			new Language("nd", "North Ndebele", "IsiNdebele"),//
			new Language("ne", "Nepali", "नेपाली"),//
			new Language("ng", "Ndonga", "Owambo"),//
			new Language("nl", "Dutch", "Nederlands"),//
			new Language("nn", "Norwegian Nynorsk", "Norsk Nynorsk"),//
			new Language("no", "Norwegian", "Norsk"),//
			new Language("nr", "South Ndebele", "Ndébélé"),//
			new Language("nv", "Navajo", "Diné Bizaad "),//
			new Language("ny", "Chichewa", "ChiCheŵa "),//
			new Language("oc", "Occitan", "Occitan"),//
			new Language("oj", "Ojibwa", "ᐊᓂᔑᓈᐯᒧᐎᓐ"),//
			new Language("om", "Oromo", "Afaan Oromoo"),//
			new Language("or", "Oriya", "ଓଡ଼ିଆ"),//
			new Language("os", "Ossetian", "Ирон Æвзаг"),//
			new Language("pa", "Panjabi", "ਪੰਜਾਬੀ "),//
			new Language("pi", "Pāli", "पािऴ"),//
			new Language("pl", "Polish", "Polski"),//
			new Language("ps", "Pashto", "‫پښتو"),//
			new Language("pt", "Portuguese", "Português"),//
			new Language("qu", "Quechua", "Runa Simi "),//
			new Language("rm", "Romansh", "Rumantsch Grischun"),//
			new Language("rn", "Kirundi", "KiRundi"),//
			new Language("ro", "Romanian", "Română"),//
			new Language("ru", "Russian", "Русский Язык"),//
			new Language("rw", "Kinyarwanda", "Kinyarwanda"),//
			new Language("sa", "Sanskrit", "संस्कृतम्"),//
			new Language("sc", "Sardinian", "Sardu"),//
			new Language("sd", "Sindhi", "सिन्धी "),//
			new Language("se", "Northern Sami", "Davvisámegiella"),//
			new Language("sg", "Sango", "Yângâ Tî Sängö"),//
			// FIXME tko: no name new Language("sh", "", ""),//
			new Language("si", "Sinhalese", "සිංහල"),//
			new Language("sk", "Slovak", "Slovenčina"),//
			new Language("sl", "Slovene", "Slovenščina"),//
			new Language("sm", "Samoan", "Gagana Fa'a Samoa"),//
			new Language("sn", "Shona", "ChiShona"),//
			new Language("so", "Somali", "Soomaaliga "),//
			new Language("sq", "Albanian", "Shqip"),//
			new Language("sr", "Serbian", "Српски Језик"),//
			new Language("ss", "Swati", "SiSwati"),//
			new Language("st", "Sotho", "SeSotho"),//
			new Language("su", "Sundanese", "Basa Sunda"),//
			new Language("sv", "Swedish", "Svenska"),//
			new Language("sw", "Swahili", "Kiswahili"),//
			new Language("ta", "Tamil", "தமிழ்"),//
			new Language("te", "Telugu", "తెలుగు"),//
			new Language("tg", "Tajik", "Тоҷикӣ "),//
			new Language("th", "Thai", "ไทย"),//
			new Language("ti", "Tigrinya", "ትግርኛ"),//
			new Language("tk", "Turkmen", "Türkmen "),//
			new Language("tl", "Tagalog", "Tagalog"),//
			new Language("tn", "Tswana", "SeTswana"),//
			new Language("to", "Tonga", "Faka Tonga"),//
			new Language("tr", "Turkish", "Türkçe"),//
			new Language("ts", "Tsonga", "XiTsonga"),//
			new Language("tt", "Tatar", "Татарча "),//
			new Language("tw", "Twi", "Twi"),//
			new Language("ty", "Tahitian", "Reo Mā`ohi"),//
			new Language("ug", "Uighur", "Uyƣurqə "),//
			new Language("uk", "Ukrainian", "Українська Мова"),//
			new Language("ur", "Urdu", "‫اردو"),//
			new Language("uz", "Uzbek", "O'zbek "),//
			new Language("ve", "Venda", "TshiVenḓa"),//
			new Language("vi", "Viêt Namese", "Tiếng Việt"),//
			new Language("vo", "Volapük", "Volapük"),//
			new Language("wa", "Walloon", "Walon"),//
			new Language("wo", "Wolof", "Wollof"),//
			new Language("xh", "Xhosa", "IsiXhosa"),//
			// FIXME TKO: encoding new Language("yi", "Yiddish", "‫ייִדיש"),//
			new Language("yo", "Yoruba", "Yorùbá"),//
			new Language("za", "Zhuang", "Saɯ Cueŋƅ "),//
			new Language("zh", "Chinese", "中文"),//
			new Language("zu", "Zulu", "IsiZulu")//
			);

	public static final void main(String[] args)
	{
		LANGUAGES.stream().forEach(l -> {
			System.out.println("new Language(\"" + l.getCode() + "\", \"" + //
					WordUtils.capitalize(l.getNativeName()).split("[,;]")[0] + "\", \"" + //
					WordUtils.capitalize(l.getName()).split("[,;]")[0] + "\"),//");
		});

	}

	private static final Map<String, String> LANGUAGE_COUNTRY_MAP = new HashMap<>();
	static
	{
		LANGUAGE_COUNTRY_MAP.put("ar", "mc");
		LANGUAGE_COUNTRY_MAP.put("bg", "bg");
		LANGUAGE_COUNTRY_MAP.put("cs", "cz");
		LANGUAGE_COUNTRY_MAP.put("da", "dk");
		LANGUAGE_COUNTRY_MAP.put("de", "de");
		LANGUAGE_COUNTRY_MAP.put("el", "gr");
		LANGUAGE_COUNTRY_MAP.put("en", "gb");
		LANGUAGE_COUNTRY_MAP.put("es", "es");
		LANGUAGE_COUNTRY_MAP.put("et", "ee");
		LANGUAGE_COUNTRY_MAP.put("fi", "fi");
		LANGUAGE_COUNTRY_MAP.put("fr", "fr");
		LANGUAGE_COUNTRY_MAP.put("hu", "hu");
		LANGUAGE_COUNTRY_MAP.put("he", "he");
		LANGUAGE_COUNTRY_MAP.put("is", "is");
		LANGUAGE_COUNTRY_MAP.put("it", "it");
		LANGUAGE_COUNTRY_MAP.put("ja", "jp");
		LANGUAGE_COUNTRY_MAP.put("ko", "ko");
		LANGUAGE_COUNTRY_MAP.put("nl", "nl");
		LANGUAGE_COUNTRY_MAP.put("no", "no");
		LANGUAGE_COUNTRY_MAP.put("pl", "pl");
		LANGUAGE_COUNTRY_MAP.put("pt", "pt");
		LANGUAGE_COUNTRY_MAP.put("ro", "ro");
		LANGUAGE_COUNTRY_MAP.put("ru", "ru");
		LANGUAGE_COUNTRY_MAP.put("sk", "sk");
		LANGUAGE_COUNTRY_MAP.put("sl", "si");
		LANGUAGE_COUNTRY_MAP.put("sq", "al");
		LANGUAGE_COUNTRY_MAP.put("sv", "se");
		LANGUAGE_COUNTRY_MAP.put("zh", "cn");
	}

	@RequestMapping(value = "/all", //
	method = RequestMethod.GET, //
	produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Language> getLanguages()
	{
		return LANGUAGES;
	}

	@RequestMapping(value = "/country", //
	method = RequestMethod.GET, //
	produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String getLanguageCountryCode(@RequestParam("langCode") String langCode)
	{
		return LANGUAGE_COUNTRY_MAP.getOrDefault(langCode, "xx");
	}

}

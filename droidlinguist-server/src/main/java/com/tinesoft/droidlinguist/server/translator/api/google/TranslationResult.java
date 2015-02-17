package com.tinesoft.droidlinguist.server.translator.api.google;

import java.util.List;

public class TranslationResult
{
	public static class Data
	{
		public static class Translation
		{
			private String detectedSourceLanguage;
			private String translatedText;

			public String getDetectedSourceLanguage()
			{
				return detectedSourceLanguage;
			}

			public void setDetectedSourceLanguage(String detectedSourceLanguage)
			{
				this.detectedSourceLanguage = detectedSourceLanguage;
			}

			public String getTranslatedText()
			{
				return translatedText;
			}

			public void setTranslatedText(String translatedText)
			{
				this.translatedText = translatedText;
			}

		}

		public static class DetectedLanguage
		{
			private String language;
			private String name;

			public String getLanguage()
			{
				return language;
			}

			public void setLanguage(String language)
			{
				this.language = language;
			}

			public String getName()
			{
				return name;
			}

			public void setName(String name)
			{
				this.name = name;
			}

		}

		public class DetectedLang
		{
			private String language;
			private boolean isReliable;
			private double confidence;

			public String getLanguage()
			{
				return language;
			}

			public void setLanguage(String language)
			{
				this.language = language;
			}

			public boolean isReliable()
			{
				return isReliable;
			}

			public void setReliable(boolean isReliable)
			{
				this.isReliable = isReliable;
			}

			public double getConfidence()
			{
				return confidence;
			}

			public void setConfidence(double confidence)
			{
				this.confidence = confidence;
			}

			@Override
			public String toString()
			{
				return "DetectedLang [language=" + language + ", isReliable=" + isReliable + ", confidence=" + confidence + "]";
			}

		}

		List<DetectedLanguage> languages;
		List<Translation> translations;
		List<DetectedLang> detections;

		public List<DetectedLanguage> getLanguages()
		{
			return languages;
		}

		public void setLanguages(List<DetectedLanguage> languages)
		{
			this.languages = languages;
		}

		public List<Translation> getTranslations()
		{
			return translations;
		}

		public void setTranslations(List<Translation> translations)
		{
			this.translations = translations;
		}

		public List<DetectedLang> getDetections()
		{
			return detections;
		}

		public void setDetections(List<DetectedLang> detections)
		{
			this.detections = detections;
		}

	}

	private Data data;

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

}

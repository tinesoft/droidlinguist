package com.tinesoft.droidlinguist.server.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "translator", locations = "classpath:config/translator.yml", ignoreUnknownFields = false)
public final class TranslatorProperties
{
	public static class Microsoft
	{
		private String clientId;
		private String clientSecret;
		private String grantType;
		private String scope;

		public String getClientId()
		{
			return clientId;
		}

		public void setClientId(String clientId)
		{
			this.clientId = clientId;
		}

		public String getClientSecret()
		{
			return clientSecret;
		}

		public void setClientSecret(String clientSecret)
		{
			this.clientSecret = clientSecret;
		}

		public String getGrantType()
		{
			return grantType;
		}

		public void setGrantType(String grantType)
		{
			this.grantType = grantType;
		}

		public String getScope()
		{
			return scope;
		}

		public void setScope(String scope)
		{
			this.scope = scope;
		}

	}

	public static class Yandex
	{

		public static class Options
		{
			private String format;
			private String uiLang;
			private String autoDetectLang;

			public String getFormat()
			{
				return format;
			}

			public void setFormat(String format)
			{
				this.format = format;
			}

			public String getUiLang()
			{
				return uiLang;
			}

			public void setUiLang(String uiLang)
			{
				this.uiLang = uiLang;
			}

			public String getAutoDetectLang()
			{
				return autoDetectLang;
			}

			public void setAutoDetectLang(String autoDetectLang)
			{
				this.autoDetectLang = autoDetectLang;
			}

		}

		String apiKey;
		Options options;

		public String getApiKey()
		{
			return apiKey;
		}

		public void setApiKey(String apiKey)
		{
			this.apiKey = apiKey;
		}

		public Options getOptions()
		{
			return options;
		}

		public void setOptions(Options options)
		{
			this.options = options;
		}
	}

	public static class Google
	{

		public static class Options
		{
			private String format;
			private String targetLang;
			private String prettyPrint;

			public String getFormat()
			{
				return format;
			}

			public void setFormat(String format)
			{
				this.format = format;
			}

			public String getTargetLang()
			{
				return targetLang;
			}

			public void setTargetLang(String targetLang)
			{
				this.targetLang = targetLang;
			}

			public String getPrettyPrint()
			{
				return prettyPrint;
			}

			public void setPrettyPrint(String prettyPrint)
			{
				this.prettyPrint = prettyPrint;
			}

		}

		String apiKey;
		Options options;

		public String getApiKey()
		{
			return apiKey;
		}

		public void setApiKey(String apiKey)
		{
			this.apiKey = apiKey;
		}

		public Options getOptions()
		{
			return options;
		}

		public void setOptions(Options options)
		{
			this.options = options;
		}

	}

	private int maxTextsPerTranslation;

	@NotNull
	private Microsoft microsoft;
	@NotNull
	private Yandex yandex;
	@NotNull
	private Google google;

	public Microsoft getMicrosoft()
	{
		return microsoft;
	}

	public void setMicrosoft(Microsoft microsoft)
	{
		this.microsoft = microsoft;
	}

	public Yandex getYandex()
	{
		return yandex;
	}

	public void setYandex(Yandex yandex)
	{
		this.yandex = yandex;
	}

	public Google getGoogle()
	{
		return google;
	}

	public void setGoogle(Google google)
	{
		this.google = google;
	}

	public int getMaxTextsPerTranslation()
	{
		return maxTextsPerTranslation;
	}

	public void setMaxTextsPerTranslation(int maxTextsPerTranslation)
	{
		this.maxTextsPerTranslation = maxTextsPerTranslation;
	}

}

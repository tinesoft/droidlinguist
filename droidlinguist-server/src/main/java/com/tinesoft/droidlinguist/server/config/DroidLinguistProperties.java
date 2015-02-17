package com.tinesoft.droidlinguist.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to DroidLinguist.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "droidlinguist", ignoreUnknownFields = false)
public class DroidLinguistProperties
{

	private final Async async = new Async();

	private final Http http = new Http();

	private final CorsConfiguration cors = new CorsConfiguration();

	public Async getAsync()
	{
		return async;
	}

	public Http getHttp()
	{
		return http;
	}

	public CorsConfiguration getCors()
	{
		return cors;
	}

	public static class Async
	{

		private int corePoolSize = 2;

		private int maxPoolSize = 50;

		private int queueCapacity = 10000;

		public int getCorePoolSize()
		{
			return corePoolSize;
		}

		public void setCorePoolSize(int corePoolSize)
		{
			this.corePoolSize = corePoolSize;
		}

		public int getMaxPoolSize()
		{
			return maxPoolSize;
		}

		public void setMaxPoolSize(int maxPoolSize)
		{
			this.maxPoolSize = maxPoolSize;
		}

		public int getQueueCapacity()
		{
			return queueCapacity;
		}

		public void setQueueCapacity(int queueCapacity)
		{
			this.queueCapacity = queueCapacity;
		}
	}

	public static class Http
	{

		private final Cache cache = new Cache();

		public Cache getCache()
		{
			return cache;
		}

		public static class Cache
		{

			private int timeToLiveInDays = 31;

			public int getTimeToLiveInDays()
			{
				return timeToLiveInDays;
			}

			public void setTimeToLiveInDays(int timeToLiveInDays)
			{
				this.timeToLiveInDays = timeToLiveInDays;
			}
		}
	}
}

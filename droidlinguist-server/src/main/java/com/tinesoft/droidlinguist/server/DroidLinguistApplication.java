package com.tinesoft.droidlinguist.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.tinesoft.droidlinguist.server.config.Constants;
import com.tinesoft.droidlinguist.server.config.DroidLinguistProperties;
import com.tinesoft.droidlinguist.server.config.TranslatorProperties;

@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class })
@EnableConfigurationProperties({ DroidLinguistProperties.class })
public class DroidLinguistApplication
{

	private static final Logger LOG = LoggerFactory.getLogger(DroidLinguistApplication.class);

	@Autowired
	private Environment env;

	@Autowired
	private TranslatorProperties translatorConfig;

	/**
	 * Initializes droidlinguist.
	 * <p/>
	 * Spring profiles can be configured with a program arguments
	 * --spring.profiles.active=your-active-profile
	 * <p/>
	 * <p>
	 * </p>
	 */
	@PostConstruct
	public void initApplication() throws IOException
	{
		if (env.getActiveProfiles().length == 0)
		{
			LOG.warn("No Spring profile configured, running with default configuration");
		}
		else
		{
			LOG.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
			if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION))
			{
				LOG.error("You have misconfigured your application! " + "It should not run with both the 'dev' and 'prod' profiles at the same time.");
			}
			if (activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION) && activeProfiles.contains(Constants.SPRING_PROFILE_FAST))
			{
				LOG.error("You have misconfigured your application! " + "It should not run with both the 'prod' and 'fast' profiles at the same time.");
			}
		}

		if (StringUtils.isBlank(translatorConfig.getMicrosoft().getClientId()))
			LOG.warn("Property 'translator.microsoft.clientId' not set. Translations using 'Microsoft Translator' engine  will fail!");

		if (StringUtils.isBlank(translatorConfig.getMicrosoft().getClientSecret()))
			LOG.warn("Property 'translator.microsoft.clientSecret' not set. Translations using 'Microsoft Translator' engine will fail!");

		if (StringUtils.isBlank(translatorConfig.getYandex().getApiKey()))
			LOG.warn("Property 'Dtranslator.yandex.apiKey' not set. Translations using 'Yandex Translate' engine will fail!");

		if (StringUtils.isBlank(translatorConfig.getGoogle().getApiKey()))
			LOG.warn("Property 'translator.google.apiKey' not set. Translations using 'Google Translate' engine will fail!");
	}

	/**
	 * Main method, used to run the application.
	 */
	public static void main(String[] args) throws UnknownHostException
	{
		SpringApplication app = new SpringApplication(DroidLinguistApplication.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		addDefaultProfile(app, source);
		Environment env = app.run(args).getEnvironment();
		LOG.info("Access URLs:\n----------------------------------------------------------\n\t" + "Local: \t\thttp://127.0.0.1:{}\n\t"
				+ "External: \thttp://{}:{}\n----------------------------------------------------------", env.getProperty("server.port"), InetAddress
				.getLocalHost().getHostAddress(), env.getProperty("server.port"));

	}

	/**
	 * If no profile has been configured, set by default the "dev" profile.
	 */
	private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source)
	{
		if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE"))
		{
			app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
		}
	}
}

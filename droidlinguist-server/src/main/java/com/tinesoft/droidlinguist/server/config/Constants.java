package com.tinesoft.droidlinguist.server.config;

/**
 * Application constants.
 */
public final class Constants
{

	// Spring profile for development, production and "fast"
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String SPRING_PROFILE_FAST = "fast";
	// Spring profile used when deploying to OpenShift
	public static final String SPRING_PROFILE_OPENSHIFT = "openshift";

	private Constants()
	{
	}
}

/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin;

import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.AprLifecycleListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.davinta.aeus.framework.rest.client.RestClient;
import com.davinta.aeus.framework.rest.factory.RestClientFactory;
import com.davinta.aeus.util.logging.PlatformLogger;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * SpringBootApplication class.
 * @author Harish Mangala
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableEncryptableProperties
@ComponentScan({ "com.davinta.aeus.util", "com.davinta.aeus.framework", "com.davinta.aeus.integration", "com.davinta.aeus.service" })
public class Application {

	/** The log. */
	PlatformLogger log = PlatformLogger.getLogger(Application.class);

	/** The apr enabled. */
	@Value("${server.tomcat.apr.enabled}")
	private Boolean aprEnabled;

	/** The service name. */
	@Value("${rest.service.admin.name}")
	private String adminServiceName;

	/** The service name. */
	@Value("${rest.service.enrollment.name}")
	private String enrollmentServiceName;

	/** The rest client factory. */
	@Autowired
	private RestClientFactory restClientFactory;

	/** The key store. */
	@Value("${rest.client.ssl.key-store:#{null}}")
	private String keyStore;

	/** The key store password. */
	@Value("${rest.client.ssl.key-store-password:#{null}}")
	private String keyStorePassword;

	/** The trust store. */
	@Value("${rest.client.ssl.trust-store:#{null}}")
	private String trustStore;

	/** The trust store password. */
	@Value("${rest.client.ssl.trust-store-password:#{null}}")
	private String trustStorePassword;

	/** The hostname verifier enabled. */
	@Value("${rest.client.ssl.hostname.verifier.enabled:true}")
	private boolean hostnameVerifierEnabled;

	/**
	 * Sets the java ssl configurations.
	 * @param sslEnabled the new java ssl configurations.
	 */
	@Value("${rest.client.ssl.enabled:false}")
	public void setJavaSslConfigurations(boolean sslEnabled) {
		if (sslEnabled) {
			if (!hostnameVerifierEnabled) {
				javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
			}
			if (keyStore != null) {
				System.setProperty("javax.net.ssl.keyStore", keyStore);
				System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
			}
			if (trustStore != null) {
				System.setProperty("javax.net.ssl.trustStore", trustStore);
				System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
			}
		}
	}

	/**
	 * Create Embedded Servlet Container.
	 * @return servletContainer Bean
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory containterFactory = new TomcatEmbeddedServletContainerFactory();
		if (aprEnabled) {
			LifecycleListener arpLifecycle = new AprLifecycleListener();
			containterFactory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
			containterFactory.addContextLifecycleListeners(arpLifecycle);
		}
		return containterFactory;
	}

	/**
	 * Rest client.
	 *
	 * @return the rest client
	 */
	@Bean
	RestClient restClient() {
		return restClientFactory.instantiate(RestClientFactory.CLIENT_JSON);
	}

	/**
	 * Admin client.
	 *
	 * @return the rest client
	 */
	@Bean
	RestClient adminClient() {
		return restClientFactory.instantiate(RestClientFactory.CLIENT_JSON);
	}

	/**
	 * FamilySurveyClient client.
	 *
	 * @return the rest client
	 */
	@Bean
	RestClient familySurveyClient() {
		return restClientFactory.instantiate(RestClientFactory.CLIENT_JSON);
	}

	/**
	 * Enrollment client.
	 *
	 * @return the rest client
	 */
	@Bean
	RestClient enrollmentClient() {
		return restClientFactory.instantiate(RestClientFactory.CLIENT_JSON);
	}

	/**
	 * bootRun.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}

}

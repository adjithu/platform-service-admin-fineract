package com.davinta.aeus.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.davinta.aeus.framework.rest.client.RestClient;
import com.davinta.aeus.framework.rest.factory.RestClientFactory;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionControllerConstants;
import com.xebialabs.restito.server.StubServer;

/**
 * StepDefs class.
 * @author Rohan Raju
 *
 */
@ContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("integration-test")
public abstract class StepDefs {

	@Value("${AdminService.url}")
	String adminServiceBaseUrl;

	@Autowired
	private RestClientFactory restClientFactory;

	protected StubServer adminServiceCalls;

	RestClient adminClient() {
		return restClientFactory.instantiate(RestClientFactory.CLIENT_JSON);
	}

	protected String getAdminBaseUriForApplicationVersion() {
		StringBuilder b = new StringBuilder();
		b.append(adminServiceBaseUrl);
		b.append(ApplicationVersionControllerConstants.PATH);
		return b.toString();
	}

}


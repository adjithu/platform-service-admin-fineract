/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.davinta.aeus.domain.enums.State;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionControllerConstants;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionEntity;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionResponse;
import com.davinta.aeus.messaging.base.Status;
import com.davinta.aeus.service.admin.applicationversion.repository.ApplicationVersionRepository;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

/**
 * GroupControllerStepDefs class.
 * 
 * @author Rohan Raju
 *
 */
public class ApplicationVersionStepDefs extends StepDefs {

	@Autowired
	ApplicationVersionRepository repository;

	private static Map<Integer, ApplicationVersionEntity> requestDTOS = new HashMap<>();
	private static Map<Integer, ApplicationVersionResponse> responseDTOS = new HashMap<>();
	private static List<String> listsGroupManagementErrorDTOs = new ArrayList<>();

	@Before
	public void before() {
		cleanData();
	}

	@After
	public void after() {
		cleanData();
	}

	private void cleanData() {
		responseDTOS = new HashMap<>();
		requestDTOS = new HashMap<>();
		repository.deleteAll();
	}

	@Given(value = "the following Add Application Version Request data:$")
	public void the_following_Add_Group_Request_data(DataTable dataTable) throws Throwable {
		List<List<String>> dataRows = dataTable.raw().subList(1, dataTable.raw().size());
		for (List<String> dataTableRow : dataRows) {

			Integer number = Integer.parseInt(dataTableRow.get(0));
			String deviceType = (String) dataTableRow.get(1);
			String deviceVersionNumber = (String) dataTableRow.get(2);
			String applicationName = (String) dataTableRow.get(3);
			String applicationVersionNumber = (String) dataTableRow.get(4);
			String applicationDownloadUrl = (String) (dataTableRow.get(5));
			String applicationReleaseDetails = (String) (dataTableRow.get(6));
			boolean isForceUpdate = Boolean.parseBoolean(dataTableRow.get(7));
			String userId = (String) (dataTableRow.get(8));
			ApplicationVersionEntity request = new ApplicationVersionEntity();
			request.setDeviceType(deviceType);
			request.setDeviceVersionNumber(deviceVersionNumber);
			request.setApplicationName(applicationName);
			request.setApplicationVersionNumber(applicationVersionNumber);
			request.setApplicationDownloadUrl(applicationDownloadUrl);
			request.setApplicationReleaseDetails(applicationReleaseDetails);
			request.setForceUpdate(isForceUpdate);
			request.setUserId(userId);
			requestDTOS.put(number, request);
		}
	}

	@When(value = "call the add application version Client")
	public void call_the_create_application_version_Client() throws Throwable {
		List<ApplicationVersionResponse> list = new ArrayList<>();
		for (Map.Entry<Integer, ApplicationVersionEntity> groupRequestEntry : requestDTOS.entrySet()) {
			String uri = getAdminBaseUriForApplicationVersion() + ApplicationVersionControllerConstants.CREATE_APPLICATION_VERSION_REQUEST;
			ApplicationVersionResponse groupResponse = adminClient().post(ApplicationVersionResponse.class, uri,
					groupRequestEntry.getValue());
			responseDTOS.put(groupRequestEntry.getKey(), groupResponse);
		}
	}

	@Then("^the application version data should be created with status:$")
	public void theAllGroupShouldBeCreatedWithStatus(DataTable dataTab) throws Throwable {
		List<List<String>> dataTable = dataTab.raw().subList(1, dataTab.raw().size());
		for (List<String> dataTableRow : dataTable) {
			Integer number = Integer.parseInt(dataTableRow.get(0));
			Integer responseStatusCode = Integer.parseInt(dataTableRow.get(1));
			String errorCode = dataTableRow.get(2);
			String errorMessage = dataTableRow.get(3);
			if (responseStatusCode == Status.SUCCESS) {
				assertTrue("Status should be success", Status.SUCCESS == responseDTOS.get(number).getStatus().getStatusCode());
				assertNull(responseDTOS.get(number).getStatus().getErrorCode());
				Assert.assertNotNull(responseDTOS.get(number).getMessage().getGuid());
			}
			if (responseStatusCode == Status.FAILED) {
				assertTrue(errorCode.equalsIgnoreCase(responseDTOS.get(number).getStatus().getErrorCode()));
			}
		}
	}
}

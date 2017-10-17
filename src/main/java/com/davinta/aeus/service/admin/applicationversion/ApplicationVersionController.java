/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionControllerConstants;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionEntity;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionResponse;
import com.davinta.aeus.service.admin.common.BaseAdminController;
import com.davinta.aeus.util.exception.PlatformApplicationException;

/**
 * ApplicationVersionControllerConstants class.
 * @author Rohan Raju
 *
 */
@RestController
@RequestMapping(value = ApplicationVersionControllerConstants.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationVersionController extends BaseAdminController {

	private final ApplicationVersionService applicationVersionService;

	@Autowired
	public ApplicationVersionController(@Qualifier("applicationVersionService") ApplicationVersionService applicationVersionService) {
		this.applicationVersionService = applicationVersionService;
	}

	@RequestMapping(method = RequestMethod.POST, value = ApplicationVersionControllerConstants.CREATE_APPLICATION_VERSION_REQUEST)
	public ResponseEntity<ApplicationVersionResponse> createApplicationVersion(@RequestBody ApplicationVersionEntity applicationVersionEntity) throws PlatformApplicationException {
		return new ResponseEntity<>(applicationVersionService.createEntity(applicationVersionEntity), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = ApplicationVersionControllerConstants.FIND_BY_GUID_REQUEST)
	public ResponseEntity<ApplicationVersionEntity> getApplicationVersion(@PathVariable("guid") String guid) throws PlatformApplicationException {
		return new ResponseEntity<>(applicationVersionService.findByGuid(guid), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = ApplicationVersionControllerConstants.ALL_APPLICATION_VERSION_REQUEST)
	public ResponseEntity<List<ApplicationVersionResponse>> getAllApplicationVersion() throws PlatformApplicationException {
		return new ResponseEntity<>(applicationVersionService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = ApplicationVersionControllerConstants.FIND_BY_DEVICETYPE_AND_DEVICEVERSION)
	public ResponseEntity<ApplicationVersionResponse> getApplicationVersionBasedOnDeviceTypeAndDeviceVersion(@PathVariable("deviceType") String deviceType,
			@PathVariable("deviceVersionNumber") String deviceVersionNumber) throws PlatformApplicationException {
		return new ResponseEntity<>(applicationVersionService.findByDeviceTypeAndDeviceVersion(deviceType, deviceVersionNumber), HttpStatus.OK);
	}

	@RequestMapping(value = ApplicationVersionControllerConstants.UPDATE_APPLICATION_VERSION_REQUEST
			+ ApplicationVersionControllerConstants.FIND_BY_GUID_REQUEST, method = RequestMethod.PUT)
	public ResponseEntity<ApplicationVersionResponse> updateApplicationVersion(@PathVariable("guid") String guid, @RequestBody ApplicationVersionEntity applicationVersionEntity)
			throws PlatformApplicationException {
		return new ResponseEntity<>(applicationVersionService.updateEntity(guid, applicationVersionEntity), HttpStatus.CREATED);
	}

	public ApplicationVersionService getService() {
		return applicationVersionService;
	}

}

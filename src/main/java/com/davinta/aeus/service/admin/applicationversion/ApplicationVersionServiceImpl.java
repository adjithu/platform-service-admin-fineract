/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davinta.aeus.domain.enums.State;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionConstants;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionEntity;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionFindResponse;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionResponse;
import com.davinta.aeus.messaging.base.Status;
import com.davinta.aeus.messaging.base.entity.EntityMapper;
import com.davinta.aeus.messaging.base.search.EntitySearchRequest;
import com.davinta.aeus.messaging.base.search.Pagination;
import com.davinta.aeus.service.admin.applicationversion.bom.ApplicationVersion;
import com.davinta.aeus.service.admin.applicationversion.exception.ApplicationVersionException;
import com.davinta.aeus.service.admin.applicationversion.mapper.ApplicationVersionMapper;
import com.davinta.aeus.service.admin.applicationversion.mapper.ApplicationVersionResponseMapper;
import com.davinta.aeus.service.admin.applicationversion.repository.ApplicationVersionRepository;
import com.davinta.aeus.service.admin.common.BaseAdminService;
import com.davinta.aeus.service.admin.common.EntityUtil;
import com.davinta.aeus.util.exception.PlatformApplicationException;

/**
 * ApplicationVersionServiceImpl class.
 * @author Rohan Raju
 *
 */
@Service("applicationVersionService")
public class ApplicationVersionServiceImpl extends BaseAdminService implements ApplicationVersionService {

	private final ApplicationVersionMapper applicationVersionMapper;

	private final ApplicationVersionRepository applicationVersionRepository;

	private final ApplicationVersionResponseMapper applicationVersionResponseMapper;

	@Autowired
	public ApplicationVersionServiceImpl(ApplicationVersionMapper applicationVersionMapper, ApplicationVersionRepository applicationVersionRepository,
			ApplicationVersionResponseMapper applicationVersionResponseMapper) {
		this.applicationVersionMapper = applicationVersionMapper;
		this.applicationVersionRepository = applicationVersionRepository;
		this.applicationVersionResponseMapper = applicationVersionResponseMapper;
	}

	@Override
	protected ApplicationVersionRepository getRepository() {
		return applicationVersionRepository;
	}

	
	@Override
	@Transactional(rollbackFor = PlatformApplicationException.class)
	public ApplicationVersionResponse createEntity(ApplicationVersionEntity applicationVersionEntity) throws PlatformApplicationException {
		checkDuplicateApplicationVersionNumber(applicationVersionEntity, null);
		ApplicationVersion applicationVersion = EntityMapper.convertMessagingToBom(applicationVersionEntity, ApplicationVersion.class, applicationVersionMapper);
		EntityUtil.setCommonAttributesForCreate(applicationVersion, applicationVersionEntity);
		ApplicationVersion savedApplicationVersion = applicationVersionRepository.save(applicationVersion);
		return buildApplicationVersionResponse(savedApplicationVersion, Status.SUCCESS, ApplicationVersionConstants.APPLICATION_VERSION_CREATED_SUCCESSFULLY);
	}

	@Override
	public ApplicationVersionEntity findByGuid(String guid) throws PlatformApplicationException {
		ApplicationVersion applicationVersion = applicationVersionRepository.findByGuid(guid);
		validateApplicationVersionResponseFromDb(guid, applicationVersion);
		return getApplicationVersionEntity(applicationVersion);
	}

	@Override
	public ApplicationVersionResponse findByDeviceTypeAndDeviceVersion(String deviceType, String deviceVersionNumber) throws PlatformApplicationException {
//		ApplicationVersion applicationVersion = applicationVersionRepository.findByDeviceTypeIgnoreCaseAndDeviceVersionNumberIgnoreCase(deviceType, deviceVersionNumber,
//				State.ACTIVE.toString());
//		if (applicationVersion == null) {
//			applicationVersion = applicationVersionRepository.findByDeviceTypeLatestVersion(deviceType, State.ACTIVE.toString());
//		}
//		if (applicationVersion == null) {
//			throw new ApplicationVersionException(ApplicationVersionConstants.APPLICATION_VERSION_NOT_FOUND);
//		}
//		return buildApplicationVersionResponse(applicationVersion, Status.SUCCESS, ApplicationVersionConstants.APPLICATION_VERSION_FETECHED_SUCCESSFULLY);
		return null;
	}

	@Override
	public List<ApplicationVersionResponse> findAll() {
		Iterable<ApplicationVersion> all = applicationVersionRepository.findAll();
		if (all == null) {
			return Collections.emptyList();
		}
		return StreamSupport.stream(all.spliterator(), false)
				.map(x -> buildApplicationVersionResponse(x, Status.SUCCESS, ApplicationVersionConstants.APPLICATION_VERSION_FETECHED_SUCCESSFULLY)).collect(Collectors.toList());
	}

	@Override
	public ApplicationVersionResponse updateEntity(String guid, ApplicationVersionEntity applicationVersionEntity) throws PlatformApplicationException {
		ApplicationVersion applicationVersion = findEntityByGuid(guid);
		validateApplicationVersionResponseFromDb(guid, applicationVersion);
		checkDuplicateApplicationVersionNumber(applicationVersionEntity, guid);
		ApplicationVersion updatedApplicationVersion = EntityMapper.convertMessagingToBom(applicationVersionEntity, applicationVersion, applicationVersionMapper);
		EntityUtil.setCommonAttributesForUpdate(applicationVersion, applicationVersionEntity);
		applicationVersionRepository.save(updatedApplicationVersion);
		return buildApplicationVersionResponse(applicationVersion, Status.SUCCESS, ApplicationVersionConstants.APPLICATION_VERSION_UPDATED_SUCCESSFULLY);
	}

	private void checkDuplicateApplicationVersionNumber(ApplicationVersionEntity applicationVersionEntity, String guid) throws ApplicationVersionException {
		ApplicationVersion appVersion = applicationVersionRepository.findByApplicationVersionNumberAndStateNot(applicationVersionEntity.getApplicationVersionNumber(), State.DELETED.code());
		if (appVersion != null && (guid == null || !appVersion.getGuid().equals(guid))) {
			throw new ApplicationVersionException(ApplicationVersionConstants.DUPLICATE_APPLICATIN_VERSION_NUMBER, appVersion.getApplicationVersionNumber());
		}
	}

	@Override
	public ApplicationVersionFindResponse find(EntitySearchRequest entitySearchRequest) throws PlatformApplicationException {
		return null;
	}

	@Override
	public ApplicationVersionFindResponse find(EntitySearchRequest entitySearchRequest, Pagination pagination) throws PlatformApplicationException {
		return null;
	}

	private ApplicationVersionEntity getApplicationVersionEntity(ApplicationVersion applicationVersion) {
		return EntityUtil.convertBomToEntity(applicationVersion, ApplicationVersionEntity.class, applicationVersionResponseMapper);
	}

	protected ApplicationVersion findEntityByGuid(String guid) {
		return applicationVersionRepository.findByGuid(guid);
	}

	private void validateApplicationVersionResponseFromDb(String guid, ApplicationVersion applicationVersion) throws ApplicationVersionException {
		if (applicationVersion == null) {
			throw new ApplicationVersionException(ApplicationVersionConstants.APPLICATION_VERSION_NOT_FOUND, guid);
		}
	}

	private ApplicationVersionResponse buildApplicationVersionResponse(ApplicationVersion savedApplicationVersion, int state, String message) {
		ApplicationVersionResponse applicationVersionResponse = EntityUtil.buildEntityResponse(savedApplicationVersion, ApplicationVersionEntity.class,
				applicationVersionResponseMapper, ApplicationVersionResponse.class);
		applicationVersionResponse.setStatus(buildStatusForTheResponse(state, message));
		return applicationVersionResponse;
	}

	private Status buildStatusForTheResponse(int state, String message) {
		Status status = new Status();
		status.setStatusCode(state);
		status.setMessageDescription(message);
		return status;
	}

	
}

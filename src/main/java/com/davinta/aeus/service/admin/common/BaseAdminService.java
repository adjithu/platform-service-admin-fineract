/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.common;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.davinta.aeus.util.exception.PlatformApplicationException;

public abstract class BaseAdminService {

	protected abstract BaseEntity findEntityByGuid(String guid);
	
	protected abstract PagingAndSortingRepository getRepository();
	
	@Transactional(rollbackFor = PlatformApplicationException.class)
	public void deleteEntity(String guid, String userId) throws PlatformApplicationException {
		BaseEntity baseEntity = findEntityByGuid(guid);
		EntityUtil.setCommonAttributesForDelete(baseEntity, userId);
		getRepository().save(baseEntity);
	}

	@Transactional(rollbackFor = PlatformApplicationException.class)
	public void inactivateEntity(String guid, String userId) throws PlatformApplicationException {
		BaseEntity baseEntity = findEntityByGuid(guid);
		EntityUtil.setCommonAttributesForInactive(baseEntity, userId);
		getRepository().save(baseEntity);
	}

	@Transactional(rollbackFor = PlatformApplicationException.class)
	public void activateEntity(String guid, String userId) throws PlatformApplicationException {
		BaseEntity baseEntity = findEntityByGuid(guid);
		EntityUtil.setCommonAttributesForActivate(baseEntity, userId);
		getRepository().save(baseEntity);
	}
}

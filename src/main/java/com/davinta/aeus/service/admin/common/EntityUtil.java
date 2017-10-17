/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.common;

import java.util.Date;
import java.util.List;

import com.davinta.aeus.bom.domain.Model;
import com.davinta.aeus.messaging.base.EntityResponse;
import com.davinta.aeus.messaging.base.EntityStatus;
import com.davinta.aeus.messaging.base.FindResultModel;
import com.davinta.aeus.messaging.base.MappingHelper;
import com.davinta.aeus.messaging.base.MessageModel;
import com.davinta.aeus.messaging.base.PlatformResponse;
import com.davinta.aeus.messaging.base.RequestMessageModel;
import com.davinta.aeus.messaging.base.ResponseMessageModel;
import com.davinta.aeus.messaging.base.Status;
import com.davinta.aeus.messaging.base.entity.EntityMapper;
import com.davinta.aeus.util.logging.PlatformLogger;

/**
 * EntityUtil class.
 * @author Gayatri Naidu
 *
 */
public final class EntityUtil {

	private static PlatformLogger logger = PlatformLogger.getLogger(EntityUtil.class);

	public static void setCommonAttributesForCreate(BaseEntity entity, RequestMessageModel request) {
		// state will be set based on maker checker configuration
		entity.setState(State.ACTIVE.getCode());
		entity.setCreatedBy(request.getUserId());
		entity.setCreatedDate(new Date());
		entity.setLastModifiedDate(entity.getCreatedDate());
	}

	public static void setCommonAttributesForUpdate(BaseEntity entity, RequestMessageModel request) {
		setCommonAttributesForUpdate(entity, request.getUserId());
	}

	public static void setCommonAttributesForUpdate(BaseEntity entity, String userId) {
		// state will be set based on maker checker configuration
		entity.setState(State.ACTIVE.getCode());
		setModifiedBy(entity, userId);
	}


	public static void setCommonAttributesForDelete(BaseEntity entity, String userId) {
		// state will be set based on maker checker configuration
		entity.setState(State.DELETED.getCode());
		setModifiedBy(entity, userId);
	}

	public static void setCommonAttributesForInactive(BaseEntity entity, String userId) {
		entity.setState(State.ACTIVE.getCode());
		setModifiedBy(entity, userId);
	}

	public static void setCommonAttributesForActivate(BaseEntity entity, String userId) {
		entity.setState(State.ACTIVE.getCode());
		setModifiedBy(entity, userId);
	}

	public static void setCommonAttributesForEntityResponse(RequestMessageModel responseEntity, BaseEntity bom) {
		responseEntity.setGuid(bom.getGuid());
		if (bom.getStateEnum() != null) {
			responseEntity.setEntityStatus(EntityStatus.valueOf(bom.getStateEnum().toString()));
		}

	}

	public static <M extends MessageModel> M convertBomToEntity(BaseEntity entity, Class<M> entityClass, MappingHelper mapper) {
		M entityResponse = EntityMapper.convertBomToMessaging(entity, entityClass, mapper);
		EntityUtil.setCommonAttributesForEntityResponse(entityResponse, entity);
		return entityResponse;
	}

	public static <M extends MessageModel, P extends ResponseMessageModel<M>> P buildEntityResponse(BaseEntity entity, Class<M> entityClass, MappingHelper mapper,
			Class<P> responseClass) {
		M entityResponse = convertBomToEntity(entity, entityClass, mapper);
		return prepareSuccessResponse(responseClass, entityResponse);
	}

	public static <M extends MessageModel> M buildCreateEntityResponseOld(BaseEntity entity, Class<M> entityClass, MappingHelper mapper) {
		M entityResponse = EntityMapper.convertBomToMessaging(entity, entityClass, mapper);
		EntityUtil.setCommonAttributesForEntityResponse(entityResponse, entity);
		return entityResponse;
	}

	public static <M extends EntityResponse> M buildEntityResponse(BaseEntity entity, Class<M> responseClass) {
		M response = null;
		try {
			response = responseClass.newInstance();
			response.setStatus(new Status(Status.SUCCESS));
			response.setGuid(entity.getGuid());
			if (entity.getStateEnum() != null) {
				response.setEntityStatus(EntityStatus.valueOf(entity.getStateEnum().toString()));
			}
		}
		catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error occured in prepareSuccessResponse", e);
			//throw new EntitySystemException(EntitySystemException.ERROR_CONVERTING_ENTITY_TO_RESPONSE);
		}
		return response;
	}

	public static <M extends MessageModel, P extends ResponseMessageModel<M>> P prepareSuccessResponse(Class<P> responseClass, M responseEntity) {
		P response = null;
		try {
			response = responseClass.newInstance();

			response.setStatus(new Status(Status.SUCCESS));
			if (responseEntity != null) {
				response.setMessage(responseEntity);
			}
		}
		catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error occured in prepareSuccessResponse", e);
			//throw new EntitySystemException(EntitySystemException.ERROR_CONVERTING_ENTITY_TO_RESPONSE);
		}
		return response;
	}

	public static <M extends Model, P extends FindResultModel<M>> P createEntityFindResponse(Class<P> findResponseClass, List<M> entities) {
		P response = null;
		try {
			response = findResponseClass.newInstance();
			Status status = new Status(Status.SUCCESS);
			if (entities.isEmpty()) {
				status.setMessageDescription("No results found");
			}
			response.setStatus(status);
			response.setResults(entities);
		}
		catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error occured in createEntityFindResponse", e);
			//throw new EntitySystemException(EntitySystemException.ERROR_CONVERTING_ENTITY_TO_RESPONSE);
		}
		return response;
	}

	public static Status createResponseFailed(String errorCode, String message) {
		Status status = new Status(Status.FAILED, message);
		status.setErrorCode(errorCode);
		return status;
	}

	public static Status createStatusSuccess(String message) {
		return new Status(Status.SUCCESS, message);
	}

	public static PlatformResponse createResponseSuccess(String message) {
		PlatformResponse response = new PlatformResponse();
		Status status = new Status(Status.SUCCESS, message);
		response.setStatus(status);
		return response;
	}

	private static void setModifiedBy(BaseEntity entity, String userId) {
		entity.setLastModifiedBy(userId);
		entity.setLastModifiedDate(new Date());
	}

	private EntityUtil() {

	}

}

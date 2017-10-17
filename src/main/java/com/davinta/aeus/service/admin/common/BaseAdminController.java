/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.davinta.aeus.messaging.base.ActivateEntityRequest;
import com.davinta.aeus.messaging.base.InactivateRequest;
import com.davinta.aeus.messaging.base.PlatformMessageService;
import com.davinta.aeus.messaging.base.PlatformResponse;
import com.davinta.aeus.messaging.base.ServiceConstants;
import com.davinta.aeus.util.exception.PlatformApplicationException;
import com.davinta.aeus.util.messaging.PlatformBaseController;

/**
 * BaseAdminController class.
 * @author gayatri naidu
 *
 */
public abstract class BaseAdminController extends PlatformBaseController {



	@PutMapping(ServiceConstants.ACTIVATE_ENTITY)
	public ResponseEntity<PlatformResponse> activateEntity(@RequestBody ActivateEntityRequest activateEntityRequest) throws PlatformApplicationException {
		getService().activateEntity(activateEntityRequest.getGuid(), activateEntityRequest.getUserId());
		return new ResponseEntity<>(EntityUtil.createResponseSuccess("Entity made active successfully"), HttpStatus.OK);
	}

	@PutMapping(ServiceConstants.INACTIVATE_ENTITY)
	public ResponseEntity<PlatformResponse> inactivateEntity(@RequestBody InactivateRequest inactivateRequest) throws PlatformApplicationException {
		getService().inactivateEntity(inactivateRequest.getGuid(), inactivateRequest.getUserId());
		return new ResponseEntity<>(EntityUtil.createResponseSuccess("Entity made inactive successfully"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{guid}")
	public ResponseEntity<PlatformResponse> deleteEntity(@PathVariable String guid, @RequestParam String userId) throws PlatformApplicationException {
		getService().deleteEntity(guid, userId);
		return new ResponseEntity<>(EntityUtil.createResponseSuccess("Entity made deleted successfully"), HttpStatus.OK);
	}

	protected abstract <S extends PlatformMessageService> S getService();
}

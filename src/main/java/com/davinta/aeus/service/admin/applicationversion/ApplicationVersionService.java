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

import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionEntity;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionFindResponse;
import com.davinta.aeus.messaging.admin.applicationversion.ApplicationVersionResponse;
import com.davinta.aeus.messaging.base.PlatformMessageService;
import com.davinta.aeus.util.exception.PlatformApplicationException;

/**
 * ApplicationVersionService class.
 * @author Rohan Raju
 *
 */
public interface ApplicationVersionService extends PlatformMessageService<ApplicationVersionEntity, ApplicationVersionResponse, ApplicationVersionFindResponse> {
	ApplicationVersionResponse findByDeviceTypeAndDeviceVersion(String deviceType, String deviceVersionNumber) throws PlatformApplicationException;

	List<ApplicationVersionResponse> findAll();

}

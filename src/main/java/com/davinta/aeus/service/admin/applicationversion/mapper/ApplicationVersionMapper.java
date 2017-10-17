/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.davinta.aeus.messaging.base.MappingHelper;

/**
 * ApplicationVersionMapper class.
 * @author Rohan Raju
 *
 */
@Component
public class ApplicationVersionMapper implements MappingHelper {

	@Override
	public Map<String, String> getMapping() {
		Map<String, String> map = new HashMap<>();
		map.put("deviceType", "deviceType");
		map.put("deviceVersionNumber", "deviceVersionNumber");
		map.put("applicationName", "applicationName");
		map.put("applicationVersionNumber", "applicationVersionNumber");
		map.put("applicationDownloadUrl", "applicationDownloadUrl");
		map.put("applicationReleaseDetails", "applicationReleaseDetails");
		map.put("isForceUpdate", "isForceUpdate");
		return map;
	}

}


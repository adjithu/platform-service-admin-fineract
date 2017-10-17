/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * ApplicationVersionResponseMapper class.
 * @author Rohan Raju
 *
 */
@Component
public class ApplicationVersionResponseMapper extends ApplicationVersionMapper {
	@Override
	public Map<String, String> getMapping() {
		Map<String, String> mapping = super.getMapping();
		mapping.put("guid", "guid");
		return mapping;
	}
}

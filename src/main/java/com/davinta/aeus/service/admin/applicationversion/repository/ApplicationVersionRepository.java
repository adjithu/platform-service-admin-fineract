/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.davinta.aeus.service.admin.applicationversion.bom.ApplicationVersion;

/**
 * ApplicationVersionRepository class.
 * @author Rohan Raju
 *
 */
public interface ApplicationVersionRepository extends PagingAndSortingRepository<ApplicationVersion, Long> {

	ApplicationVersion findByGuid(String guid);

	//@Query("MATCH (n:ApplicationVersion) WHERE LOWER(n.deviceType) = LOWER({deviceType}) AND LOWER(n.deviceVersionNumber) = LOWER({deviceVersionNumber}) AND LOWER(n.entityState) = LOWER({entityState}) RETURN n  ORDER BY (n.lastModifiedDate) DESC LIMIT 1 ")
	ApplicationVersion findByDeviceTypeIgnoreCaseAndDeviceVersionNumberIgnoreCase(@Param("deviceType") String deviceType, @Param("deviceVersionNumber") String deviceVersionNumber,
			@Param("entityState") String entityState);

	//@Query("MATCH (n:ApplicationVersion) WHERE LOWER(n.deviceType) = LOWER({deviceType}) AND LOWER(n.entityState) = LOWER({entityState}) RETURN n  ORDER BY n.deviceVersionNumber DESC,n.lastModifiedDate DESC LIMIT 1 ")
	//ApplicationVersion findByDeviceTypeLatestVersion(@Param("deviceType") String deviceType, @Param("entityState") String entityState);

	//@Query("MATCH (n:ApplicationVersion) WHERE n.applicationVersionNumber = {0} AND n.entityState <> 'DELETED' RETURN n")
	ApplicationVersion findByApplicationVersionNumberAndStateNot(String applicationVersionNumber, int state);
}

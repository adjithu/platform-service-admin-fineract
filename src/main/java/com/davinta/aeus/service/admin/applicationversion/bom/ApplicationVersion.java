/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion.bom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.davinta.aeus.service.admin.common.BaseEntity;

/**
 * ApplicationVersion class.
 * @author Rohan Raju
 *
 */
@Entity
@Table(name = "application_version")
public class ApplicationVersion extends BaseEntity {

	private static final long serialVersionUID = -2652874279415473612L;

	@NotBlank
	@Column(nullable = false, name = "device_type")
	private String deviceType;

	@NotBlank
	@Column(nullable = false, name = "device_version_num")
	private String deviceVersionNumber;
	
	@NotNull
	@Column(nullable = false, name = "app_name")
	private String applicationName;
	
	@NotBlank
	@Column(nullable = false, name = "app_version_num")
	private String applicationVersionNumber;

	@URL
	@NotNull
	@Column(nullable = false, name = "app_download_url")
	private String applicationDownloadUrl;

	@Column(nullable = true, name = "app_release_details")
	private String applicationReleaseDetails;

	@Column(nullable = true, name = "is_force_update")
	private boolean isForceUpdate;

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceVersionNumber() {
		return deviceVersionNumber;
	}

	public void setDeviceVersionNumber(String deviceVersionNumber) {
		this.deviceVersionNumber = deviceVersionNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationVersionNumber() {
		return applicationVersionNumber;
	}

	public void setApplicationVersionNumber(String applicationVersionNumber) {
		this.applicationVersionNumber = applicationVersionNumber;
	}

	public String getApplicationDownloadUrl() {
		return applicationDownloadUrl;
	}

	public void setApplicationDownloadUrl(String applicationDownloadUrl) {
		this.applicationDownloadUrl = applicationDownloadUrl;
	}

	public String getApplicationReleaseDetails() {
		return applicationReleaseDetails;
	}

	public void setApplicationReleaseDetails(String applicationReleaseDetails) {
		this.applicationReleaseDetails = applicationReleaseDetails;
	}

	public boolean isForceUpdate() {
		return isForceUpdate;
	}

	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

}
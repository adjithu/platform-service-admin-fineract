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
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.davinta.aeus.bom.domain.Model;

/**
 * This entity have reference of basic fields which are required for Group /
 * GroupMemebr Entity to persist.
 * @author - Manas Paira
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Model {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6946597986556668478L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true, name = "id")
	private Long id;

	@Column(nullable = false, unique = true, name = "guid")
	private String guid = UUID.randomUUID().toString();

	@Column(nullable = true, name = "tenant_id")
	private String tenantId;

	@Column(nullable = false, name = "version")
	@Version
	private Integer version;

	/**
     * A value from {@link State}.
     */
	@Column(nullable = false, name = "state", length = 2)
	private Integer state;

	@Column(nullable = false, name = "created_by")
	private String createdBy;

	@Column(nullable = false, name = "created_date")
	private Date createdDate;

	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@Column(name = "last_modified_date")
	private Date lastModifiedDate;

	public BaseEntity() {
		// Default constructor.
	}

	/**
	 * getId method.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId method.
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getTenantId method.
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * setTenantId method.
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * getVersion method.
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * setVersion method.
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * getGuid method.
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * setGuid method.
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getState() {
		return state;
	}
	
	public State getStateEnum() {
		return State.fromInt(state);
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * getCreatedBy method.
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * setCreatedBy method.
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * getCreatedDate method.
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * setCreatedDate method.
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * getLastModifiedBy method.
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * setLastModifiedBy method.
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * getLastModifiedDate method.
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * setLastModifiedDate method.
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}

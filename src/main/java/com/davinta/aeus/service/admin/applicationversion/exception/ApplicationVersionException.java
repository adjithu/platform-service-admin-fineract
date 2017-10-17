/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.applicationversion.exception;

import java.util.Locale;

import com.davinta.aeus.util.exception.PlatformApplicationException;
import com.davinta.aeus.util.i18n.PlatformResource;

/**
 * ApplicationVersionException class.
 * @author Rohan Raju
 *
 */
public class ApplicationVersionException extends PlatformApplicationException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -399607523782492118L;

	public ApplicationVersionException(String errorCode, Object... errorArgs) {
		this(errorCode, null, errorArgs);
	}

	public ApplicationVersionException(String errorCode, Throwable cause, Object... errorArgs) {
		super(errorCode, cause, errorArgs);
	}

	@Override
	public String getLocalizedMessage(String errorCode, Locale locale, Object... args) {
		return PlatformResource.getTranslatedText(errorCode, args, locale);
	}
}

/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.davinta.aeus.service.admin.common;

/**
 * State enum.
 * @author Murali Kodavati
 *
 */
public enum State {

	/**
	 * State enum values.
	 */
	PENDING(1), ACTIVE(2), INACTIVE(3), DELETED(4);

	
	private int code;

	private State(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public static State fromInt(final Integer statusValue) {

		State result = null;
        switch (statusValue) {
            case 1:
            	result = State.PENDING;
            	break;
            case 2:
            	result = State.ACTIVE;
            	break;
            case 3:
            	 result = State.INACTIVE;
            	 break;
            case 4:
            	result =  State.DELETED;
            	break;
        }
        
        return result;
    }
}

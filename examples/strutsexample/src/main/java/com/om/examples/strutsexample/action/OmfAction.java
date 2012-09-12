/*
 * Copyright 2012 Tom Everett
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.om.examples.strutsexample.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.om.core.api.session.Session;
import org.om.http.filter.ObjectManagerSessionRequestFilter;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author tome
 * 
 */
public abstract class OmfAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the omf session
	 */
	private Session omfSession;

	public Session getOmfSession() {
		return omfSession;
	}

	public void setOmfSession(Session omfSession) {
		this.omfSession = omfSession;
	}

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.omfSession = (Session) httpServletRequest.getAttribute(ObjectManagerSessionRequestFilter.SESSION_OBJECT_NAME);
	}

}

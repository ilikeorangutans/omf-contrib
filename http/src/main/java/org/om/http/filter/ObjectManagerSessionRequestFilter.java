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
package org.om.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.om.core.api.session.Session;
import org.om.dao.util.SessionUtil;

/**
 * 
 * Simple implementation of open session in view for OMF
 * 
 * @author tome
 * 
 */
public class ObjectManagerSessionRequestFilter implements Filter {

	public final static String SESSION_OBJECT_NAME = "ObjectManagerSession";

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try {
			/*
			 * get session
			 */
			final Session session = SessionUtil.getSession();
			if (null != session) {
				/*
				 * set attribute
				 */
				servletRequest.setAttribute(SESSION_OBJECT_NAME, session);
				/*
				 * next
				 */
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				throw new Exception("Unable to create session");
			}
		} catch (final Exception e) {
			throw new ServletException("Exception in doFilter", e);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

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
package org.om.dao.jcr.sessionfactory;

import javax.jcr.Session;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;

/**
 * 
 * A decorator which implements thread-bound sessions. This is similar to
 * Hibernate's thread-bound sessions, and would be used in a similar way.
 * 
 * @author tome
 * 
 */
public class SessionPerThreadJCRSessionFactoryDecorator implements JCRSessionFactory {

	/**
	 * the threadlocal primary session
	 */
	private static ThreadLocal<Session> threadLocalSession = null;
	/**
	 * the session factory to decorate
	 */
	private final JCRSessionFactory sessionFactory;

	/**
	 * ctor
	 */
	public SessionPerThreadJCRSessionFactoryDecorator(JCRSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	public Session getSession() throws JcrException {
		try {
			/*
			 * create if needed
			 */
			if (null == threadLocalSession) {
				/*
				 * get the session for this thread
				 */
				final Session session = sessionFactory.getSession();
				/*
				 * check
				 */
				if (null != session) {
					/*
					 * create the var
					 */
					threadLocalSession = new ThreadLocal<Session>();
					/*
					 * set
					 */
					threadLocalSession.set(session);
				}
			}
			/*
			 * return
			 */
			if (null != threadLocalSession) {
				return threadLocalSession.get();
			} else {
				return null;
			}
		} catch (final Exception e) {
			throw new JcrException("Exception in getSession", e);
		}
	}
}

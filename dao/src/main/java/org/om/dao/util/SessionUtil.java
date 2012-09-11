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
package org.om.dao.util;

import org.om.core.api.session.Session;
import org.om.core.api.session.factory.SessionFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceContext;
import org.om.dao.config.ObjectManagerConfiguration;
import org.om.dao.config.impl.PropertiesFileObjectManagerConfiguration;
import org.om.dao.exception.DAOException;

/**
 * @author tome
 */
public class SessionUtil {
	/**
	 * the OM config
	 */
	private static ObjectManagerConfiguration objectManagerConfiguration = null;

	/**
	 * get a JCR session
	 */
	public static javax.jcr.Session getJCRSession() throws DAOException {
		try {
			/*
			 * init if needed
			 */
			init();
			/*
			 * session, via the JCR context
			 */
			final JcrPersistenceContext jcrPersistenceContext = (JcrPersistenceContext) objectManagerConfiguration.getPersistenceContext();
			if (null != jcrPersistenceContext) {
				return jcrPersistenceContext.getSession();
			} else {
				throw new Exception("Unable to get JcrPersistenceContext");
			}
		} catch (final Exception e) {
			throw new DAOException("Exception in getSession", e);
		}
	}

	/**
	 * get OM session
	 */
	public static Session getSession() throws DAOException {
		try {
			/*
			 * init if needed
			 */
			init();
			/*
			 * the sessionfactory
			 */
			final SessionFactory sessionFactory = objectManagerConfiguration.getSessionFactory();
			/*
			 * session, via the JCR context
			 */
			return sessionFactory.getSession(objectManagerConfiguration.getPersistenceContext());
		} catch (final Exception e) {
			throw new DAOException("Exception in getSession", e);
		}
	}

	/**
	 * init
	 */
	private static void init() throws DAOException {
		try {
			if (null == objectManagerConfiguration) {
				objectManagerConfiguration = PropertiesFileObjectManagerConfiguration.getObjectManagerConfiguration();
			}
		} catch (final Exception e) {
			throw new DAOException("Exception in init", e);
		}
	}
}

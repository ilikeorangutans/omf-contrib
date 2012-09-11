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
package org.om.dao.config.impl;

import java.io.InputStream;
import java.util.Properties;

import org.om.core.api.persistence.PersistenceAdapterFactory;
import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.session.factory.SessionFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceAdapterFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceContext;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;
import org.om.dao.config.ObjectManagerConfiguration;
import org.om.dao.exception.DAOException;
import org.om.dao.sessionfactory.DefaultSessionFactory;

/**
 * @author tome
 */
public class PropertiesFileObjectManagerConfiguration implements ObjectManagerConfiguration {
	/**
	 * props files
	 */
	private static final String PROPERTIES_FILE = "/objectmanagerdao.properties";
	/**
	 * session factory
	 */
	private static final String SESSIONFACTORY = "jcrsessionfactory";

	/**
	 * singleton getter
	 */
	public static ObjectManagerConfiguration getObjectManagerConfiguration() throws DAOException {
		if (null == instance) {
			instance = new PropertiesFileObjectManagerConfiguration();
		}
		return instance;
	}

	/**
	 * OM session factory
	 */
	private SessionFactory sessionFactory;
	/**
	 * JCRSessionFactory
	 */
	private JCRSessionFactory jcrSessionFactory;
	/**
	 * singleton
	 */
	private static PropertiesFileObjectManagerConfiguration instance = null;

	/**
	 * private ctor
	 */
	private PropertiesFileObjectManagerConfiguration() throws DAOException {
		try {
			configure();
		} catch (final Exception e) {
			throw new DAOException("Exception in XMLObjectManagerConfiguration ctor", e);
		}
	}

	/**
	 * configure the OM Configuration
	 */
	private void configure() throws DAOException {
		try {
			final InputStream is = PropertiesFileObjectManagerConfiguration.class.getResourceAsStream(PROPERTIES_FILE);
			if (null != is) {
				/*
				 * props
				 */
				final Properties props = new Properties();
				props.load(is);
				/*
				 * session factory
				 */
				final String sessionFactoryName = props.getProperty(SESSIONFACTORY);
				if (null != sessionFactoryName) {
					jcrSessionFactory = (JCRSessionFactory) Class.forName(sessionFactoryName).newInstance();
					/*
					 * build up the rest
					 */
					final PersistenceAdapterFactory persistenceDelegateFactory = new JcrPersistenceAdapterFactory();
					sessionFactory = new DefaultSessionFactory(persistenceDelegateFactory);
				} else {
					throw new Exception("Unable to find property '" + SESSIONFACTORY + "'");
				}
			} else {
				throw new Exception("Unable to open properties file '" + PROPERTIES_FILE + "'");
			}
		} catch (final Exception e) {
			throw new DAOException("Exception in configure", e);
		}
	}

	public PersistenceContext getPersistenceContext() throws DAOException {
		return new JcrPersistenceContext(jcrSessionFactory.getSession());
	}

	public SessionFactory getSessionFactory() throws DAOException {
		return sessionFactory;
	}
}

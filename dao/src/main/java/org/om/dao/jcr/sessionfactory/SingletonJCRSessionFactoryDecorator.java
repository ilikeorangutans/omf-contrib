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
 * A decorator that makes the session a singleton. Not useful in production, but
 * very useful when using the transient repository in unit tests. This is in the
 * main tree, rather than the test tree so that test writers in other projects,
 * such as the contribs can use it
 * 
 * 
 * @author tome
 * 
 */
public class SingletonJCRSessionFactoryDecorator implements JCRSessionFactory {

	/**
	 * the session
	 */
	private static Session session = null;

	/**
	 * the session factory to decorate
	 */
	private final JCRSessionFactory sessionFactory;

	/**
	 * ctor
	 */
	public SingletonJCRSessionFactoryDecorator(JCRSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	public Session getSession() throws JcrException {
		try {
			/*
			 * create if needed
			 */
			if (null == session) {
				session = sessionFactory.getSession();

			}
			return session;
		} catch (final Exception e) {
			throw new JcrException("Exception in getSession", e);
		}
	}
}

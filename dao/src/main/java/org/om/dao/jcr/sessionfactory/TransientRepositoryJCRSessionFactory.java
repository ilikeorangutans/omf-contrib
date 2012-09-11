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

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.core.TransientRepository;
import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;

/**
 * 
 * A session factory which leverages the Transient Repo. Very useful for running
 * unit tests.
 * 
 * @author tome
 * 
 */
public class TransientRepositoryJCRSessionFactory implements JCRSessionFactory {

	/**
	 * credentials
	 */
	private static final String username = "admin";
	private static final String password = "admin";

	/**
	 * the repo, a singleton
	 */
	private static Repository repository = null;

	public Session getSession() throws JcrException {
		try {
			if (null == repository) {
				repository = new TransientRepository();
			}
			return repository.login(new SimpleCredentials(username, password.toCharArray()));
		} catch (final Exception e) {
			throw new JcrException("Exception in getSession", e);
		}
	}
}

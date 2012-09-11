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
package org.om.dao.config;

import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.session.factory.SessionFactory;
import org.om.dao.exception.DAOException;

/**
 * @author tome
 */
public interface ObjectManagerConfiguration {
	/**
	 * get JCR persistence context
	 */
	public PersistenceContext getPersistenceContext() throws DAOException;

	/**
	 * get a fully configured OM session factory
	 */
	public SessionFactory getSessionFactory() throws DAOException;
}

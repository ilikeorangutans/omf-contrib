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

import java.util.Hashtable;

import javax.jcr.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;

/**
 * 
 * A JCRSessionFactory that gets its bindings from JNDI. Useful in the situation
 * where container-configured JCR settings are being used. For example, if the
 * OM is being hosted in a J2EE container.
 * 
 * @author tome
 * 
 */
public class JNDIJCRSessionFactory implements JCRSessionFactory {

	/**
	 * jndi name
	 */
	private final String jndiName;

	/**
	 * ctor
	 */
	public JNDIJCRSessionFactory(String jndiName) {
		this.jndiName = jndiName;
	}

	public Session getSession() throws JcrException {
		try {
			/*
			 * set up the context
			 */
			@SuppressWarnings("rawtypes")
			final Hashtable env = new Hashtable();
			final Context ctx = new InitialContext(env);
			/*
			 * lookup
			 */
			final Object obj = ctx.lookup(jndiName);
			/*
			 * done
			 */
			return (Session) obj;
		} catch (final Exception e) {
			throw new JcrException("Exception in getSession", e);
		}
	}
}

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
package org.om.dao.sessionfactory;

import org.om.core.api.persistence.PersistenceAdapterFactory;
import org.om.core.impl.mapping.extractor.EntityMappingExtractorImpl;
import org.om.core.impl.mapping.registry.OnDemandMappingRegistry;
import org.om.core.impl.persistence.cglib.CglibProxyFactory;
import org.om.core.impl.persistence.interceptor.factory.PersistenceInterceptorFactoryImpl;
import org.om.core.impl.session.factory.ImmutableSessionFactory;

/**
 * 
 * This class extends ImmutableSessionFactory and passes the "usual defaults" to
 * ImmutableSessionFactory, to allow creating sessions without worrying about
 * needing to pass the ctor arguments.
 * 
 * @author tome
 * 
 * 
 */
public class DefaultSessionFactory extends ImmutableSessionFactory {

	public DefaultSessionFactory(PersistenceAdapterFactory persistenceDelegateFactory) {
		super(persistenceDelegateFactory, new OnDemandMappingRegistry(new EntityMappingExtractorImpl()), new CglibProxyFactory(
				new PersistenceInterceptorFactoryImpl()));
	}
}

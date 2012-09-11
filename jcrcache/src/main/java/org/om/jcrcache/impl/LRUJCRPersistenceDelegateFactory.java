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
package org.om.jcrcache.impl;

import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.persistence.PersistenceAdapter;
import org.om.core.api.persistence.PersistenceAdapterFactory;
import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.session.Session;

/**
 * @author tome
 */
public class LRUJCRPersistenceDelegateFactory implements PersistenceAdapterFactory {
	/**
	 * cache size
	 */
	private final int cacheSize;
	/**
	 * actual delegate factory
	 */
	private final PersistenceAdapterFactory persistenceAdapterFactory;

	/**
	 * ctor
	 */
	public LRUJCRPersistenceDelegateFactory(PersistenceAdapterFactory persistenceAdapterFactory, int cacheSize) {
		this.persistenceAdapterFactory = persistenceAdapterFactory;
		this.cacheSize = cacheSize;
	}

	public PersistenceAdapter create(Session session, Object id, EntityMapping mapping, PersistenceContext persistenceContext, boolean createNode) {
		PersistenceAdapter persistenceAdapter = persistenceAdapterFactory.create(session, id, mapping, persistenceContext, createNode);
		return new LRUJCRCacheImpl(persistenceAdapter, cacheSize);
	}
}

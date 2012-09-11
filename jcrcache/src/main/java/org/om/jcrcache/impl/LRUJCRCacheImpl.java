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

import org.om.core.api.exception.ObjectMapperException;
import org.om.core.api.exception.PersistenceLayerException;
import org.om.core.api.mapping.CollectionMapping;
import org.om.core.api.mapping.field.PropertyMapping;
import org.om.core.api.persistence.PersistenceAdapter;
import org.om.core.api.persistence.request.PersistenceRequest;
import org.om.core.api.persistence.result.CollectionResult;
import org.om.core.api.persistence.result.PersistenceResult;
import org.om.jcrcache.JCRCache;

/**
 * An LRU cache which wraps a JCR persistence delegate. This class is
 * thread-safe
 * 
 * @author tome
 */
public class LRUJCRCacheImpl implements JCRCache {
	/**
	 * the actual PersistenceAdapter
	 */
	private final PersistenceAdapter persistenceAdapter;
	/**
	 * the cache
	 */
	private final LRUCache<String, PersistenceResult> cache;
	/**
	 * cache size
	 */
	private final int cacheSize;

	/**
	 * ctor
	 */
	public LRUJCRCacheImpl(PersistenceAdapter persistenceAdapter, int cacheSize) {
		this.persistenceAdapter = persistenceAdapter;
		this.cacheSize = cacheSize;
		cache = new LRUCache<String, PersistenceResult>(this.cacheSize);
	}

	private String getKey(PropertyMapping mapping) throws ObjectMapperException {
		return mapping.getPropertyName();
	}

	public PersistenceResult getProperty(PropertyMapping propertyMapping) throws ObjectMapperException {
		synchronized (this) {
			/*
			 * search the cache
			 */
			PersistenceResult ret = cache.get(getKey(propertyMapping));
			if (null == ret) {
				/*
				 * fine, use the delegate
				 */
				ret = persistenceAdapter.getProperty(propertyMapping);
			}
			return ret;
		}
	}

	public void setProperty(PropertyMapping propertyMapping, Object object) throws ObjectMapperException {
		synchronized (this) {
			if ((null != propertyMapping) && (null != object)) {
				/*
				 * set via the real delegate
				 */
				persistenceAdapter.setProperty(propertyMapping, object);
				/*
				 * item is cached?
				 */
				if (cache.containsKey(getKey(propertyMapping))) {
					/*
					 * remove
					 */
					cache.remove(getKey(propertyMapping));
				}
				/*
				 * cache
				 */
				cache.put(getKey(propertyMapping), object);
			}
		}
	}

	public int size() {
		return cache.size();
	}

	public void delete() throws ObjectMapperException {
		synchronized (this) {
			/*
			 * remove the cache
			 */
			cache.clear();
			/*
			 * delegate
			 */
			this.persistenceAdapter.delete();
		}
	}

	public CollectionResult getCollection(CollectionMapping collectionMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceResult getProperty(PersistenceRequest request) throws PersistenceLayerException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object resolve(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}

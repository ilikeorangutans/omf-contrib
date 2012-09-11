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

import org.om.core.api.persistence.result.PersistenceResult;

/**
 * 
 * @author tome
 * 
 */
public class CachedPersistenceResult implements PersistenceResult {

	private final Object value;
	private final boolean result;

	public CachedPersistenceResult(PersistenceResult persistenceResult) {
		this.value = persistenceResult.getValue();
		this.result = persistenceResult.hasResult();
	}

	public Object getValue() {
		return value;
	}

	public boolean hasResult() {
		return result;
	}

}

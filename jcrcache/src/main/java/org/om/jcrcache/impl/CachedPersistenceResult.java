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

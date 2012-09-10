package org.om.jcr2pojo.entitymappingbuilder.namingstrategy;

import org.om.core.api.annotation.Property;
import org.om.core.impl.persistence.jcr.exception.JcrException;

/**
 * 
 * @author tome
 * 
 */
public interface PropertyNamingStrategy {
	String generateName(Property property) throws JcrException;
}

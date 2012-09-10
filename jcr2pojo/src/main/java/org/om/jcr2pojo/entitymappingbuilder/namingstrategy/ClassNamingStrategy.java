package org.om.jcr2pojo.entitymappingbuilder.namingstrategy;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.w3c.dom.Node;

/**
 * 
 * @author tome
 * 
 */
public interface ClassNamingStrategy {

	String generateName(Node node) throws JcrException;
}

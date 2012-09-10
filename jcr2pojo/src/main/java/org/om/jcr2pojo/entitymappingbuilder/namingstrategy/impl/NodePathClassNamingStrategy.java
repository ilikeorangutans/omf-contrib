package org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.w3c.dom.Node;

/**
 * 
 * @author tome
 * 
 */
public class NodePathClassNamingStrategy implements ClassNamingStrategy {

	public String generateName(Node node) throws JcrException {
		try {
			return node.getPath();
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}
}

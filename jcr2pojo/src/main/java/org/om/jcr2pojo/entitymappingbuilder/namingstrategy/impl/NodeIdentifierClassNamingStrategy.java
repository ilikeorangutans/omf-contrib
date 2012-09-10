package org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.w3c.dom.Node;

/**
 * 
 * @author tome
 *         <p>
 *         Name the generated java classes based on the Node ID
 *         </p>
 * 
 */
public class NodeIdentifierClassNamingStrategy implements ClassNamingStrategy {

	public String generateName(Node node) throws JcrException {
		try {
			return new String("Id_") + node.getIdentifier().replaceAll("-", "_");
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}
}

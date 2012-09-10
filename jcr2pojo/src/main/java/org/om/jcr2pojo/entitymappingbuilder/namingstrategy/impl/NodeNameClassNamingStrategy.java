package org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.w3c.dom.Node;

/**
 * 
 * @author tome
 * 
 */
public class NodeNameClassNamingStrategy implements ClassNamingStrategy {
	/**
	 * make a valid java class name from a jcr node name
	 */
	private String fixName(String name) {
		final int i = name.indexOf(":");
		if (-1 != i) {
			final char[] b = name.toCharArray();
			b[i + 1] = Character.toUpperCase(b[i + 1]);
			String ret = new String(b);
			ret = ret.replaceAll(":", "");
			return ret;
		} else {
			return name;
		}
	}

	public String generateName(Node node) throws JcrException {
		try {
			return fixName(node.getName());
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}
}

package org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl;

import org.om.core.api.annotation.Property;
import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.PropertyNamingStrategy;

/**
 * 
 * @author tome
 * 
 */
public class DefaultPropertyNamingStrategy implements PropertyNamingStrategy {

	/**
	 * make a valid java field name from a jcr property name
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

	public String generateName(Property property) throws JcrException {
		try {
			return fixName(property.getName());
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}

}

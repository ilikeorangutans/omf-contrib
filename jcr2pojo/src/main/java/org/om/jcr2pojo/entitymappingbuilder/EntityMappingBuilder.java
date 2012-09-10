package org.om.jcr2pojo.entitymappingbuilder;

import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.session.Session;
import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.w3c.dom.Node;

public interface EntityMappingBuilder {

	/**
	 * build entity mapping
	 */
	public abstract EntityMapping build(Node node) throws JcrException;

	/**
	 * build entity mapping
	 */
	public abstract EntityMapping build(String jcrPath, Session session) throws JcrException;

}
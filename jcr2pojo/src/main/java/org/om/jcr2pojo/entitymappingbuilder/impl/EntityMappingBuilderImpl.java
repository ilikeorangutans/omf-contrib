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
package org.om.jcr2pojo.entitymappingbuilder.impl;

import java.util.HashSet;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.mapping.field.Mapping;
import org.om.core.api.session.Session;
import org.om.core.impl.mapping.EntityMappingImpl;
import org.om.core.impl.mapping.field.ImmutablePropertyMapping;
import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.core.impl.persistence.jcr.util.PropertyTypeToClass;
import org.om.jcr2pojo.entitymappingbuilder.EntityMappingBuilder;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.PropertyNamingStrategy;

/**
 * @author tome
 */
public class EntityMappingBuilderImpl implements EntityMappingBuilder {
	/**
	 * class naming strategy
	 */
	private final ClassNamingStrategy classNamingStrategy;
	/**
	 * property naming strategy
	 */
	private final PropertyNamingStrategy propertyNamingStrategy;
	/**
	 * namespaces we want to not map
	 */
	private static final String[] ignoreNamespaces = { "jcr" };

	/**
	 * ctor
	 */
	public EntityMappingBuilderImpl(ClassNamingStrategy classNamingStrategy, PropertyNamingStrategy propertyNamingStrategy) {
		this.classNamingStrategy = classNamingStrategy;
		this.propertyNamingStrategy = propertyNamingStrategy;
	}

	public EntityMapping build(Node node) throws JcrException {
		try {
			/*
			 * node type
			 */
			// NodeType nodeType = node.getPrimaryNodeType();
			// System.out.println(nodeType.getName());
			/*
			 * mapping
			 */
			Set<Mapping> mappings = new HashSet<Mapping>();
			/*
			 * get properties
			 */
			final PropertyIterator iter = node.getProperties();
			while (iter.hasNext()) {
				/*
				 * property
				 */
				final Property property = iter.nextProperty();
				/*
				 * map it?
				 */
				if (isMappableProperty(property.getName())) {
					/*
					 * field name
					 */
					final String fieldName = propertyNamingStrategy.generateName(property);
					/*
					 * jcr type
					 */
					final int jcrType = property.getType();
					/*
					 * java type
					 */
					final Class<?> type = PropertyTypeToClass.getClassForType(jcrType);
					/*
					 * mapping
					 */
					final ImmutablePropertyMapping propertyMapping = new ImmutablePropertyMapping(fieldName, false, property.getName(), type, null, null, null);
					mappings.add(propertyMapping);
				}
			}
			/*
			 * done
			 */
			final EntityMappingImpl entityMappingImpl = new EntityMappingImpl(classNamingStrategy.generateName(node));
			entityMappingImpl.setPropertyMap(new ImmutablePropertyMap(mappings));
			return entityMappingImpl;
		} catch (final Exception e) {
			throw new JcrException("Exception in build", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.om.core.impl.persistence.jcr.impl.mappingbuilder.EntityMappingBuilder
	 * #build(java.lang.String, javax.jcr.Session)
	 */
	public EntityMapping build(String jcrPath, Session session) throws JcrException {
		try {
			/*
			 * get the node
			 */
			final Node node = session.getRootNode().getNode(jcrPath);
			if (null != node) {
				return build(node);
			} else {
				throw new Exception("Unable to find node '" + jcrPath + "'");
			}
		} catch (final Exception e) {
			throw new JcrException("Exception in build for path '" + jcrPath + "'", e);
		}
	}

	/**
	 * check if we want to map this, or ignore it
	 */
	private boolean isMappableProperty(String propertyName) {
		final int i = propertyName.indexOf(":");
		if (-1 != i) {
			final String namespace = propertyName.substring(0, i);
			for (int j = 0; j < ignoreNamespaces.length; j++) {
				if (ignoreNamespaces[j].compareTo(namespace) == 0) {
					return false;
				}
			}
			return true;
		} else {
			/*
			 * if there is no namespace, map it
			 */
			return true;
		}
	}
}

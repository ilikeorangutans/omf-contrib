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
package org.om.jcr2pojo.classmappingbuilder.impl;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Session;

import org.om.core.impl.persistence.jcr.util.PropertyTypeToClass;
import org.om.jcr2pojo.classmapping.ClassMapping;
import org.om.jcr2pojo.classmapping.FieldMapping;
import org.om.jcr2pojo.classmappingbuilder.ClassMappingBuilder;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.PropertyNamingStrategy;
import org.om.jcr2pojo.exception.JCR2POJOException;

/**
 * @author tome
 */
public class ClassMappingBuilderImpl implements ClassMappingBuilder {
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
	public ClassMappingBuilderImpl(ClassNamingStrategy classNamingStrategy, PropertyNamingStrategy propertyNamingStrategy) {
		this.classNamingStrategy = classNamingStrategy;
		this.propertyNamingStrategy = propertyNamingStrategy;
	}

	/**
	 * build class mappings from a node
	 */
	public ClassMapping build(Node node) throws JCR2POJOException {
		try {
			final ClassMapping classMapping = new ClassMapping(classNamingStrategy.generateName(node));
			/*
			 * node type
			 */
			// NodeType nodeType = node.getPrimaryNodeType();
			// System.out.println(nodeType.getName());

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
					final FieldMapping fieldMapping = new FieldMapping(fieldName, type);
					classMapping.addField(fieldMapping);
				}
			}
			/*
			 * done
			 */
			return classMapping;
		} catch (final Exception e) {
			throw new JCR2POJOException("Exception in build", e);
		}
	}

	/**
	 * build class mappings from JCR path & Session
	 */
	public ClassMapping build(String jcrPath, Session session) throws JCR2POJOException {
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
			throw new JCR2POJOException("Exception in build for path '" + jcrPath + "'", e);
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

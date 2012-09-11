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
package org.om.jcr2pojo.reveng;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.om.jcr2pojo.classgenerator.DAOGenerator;
import org.om.jcr2pojo.classgenerator.POJOGenerator;
import org.om.jcr2pojo.classmapping.ClassMapping;
import org.om.jcr2pojo.classmappingbuilder.ClassMappingBuilder;
import org.om.jcr2pojo.classmappingbuilder.impl.ClassMappingBuilderImpl;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.PropertyNamingStrategy;
import org.om.jcr2pojo.exception.JCR2POJOException;

/**
 * @author tome
 */
public class JCRJavaPersistenceGenerator {
	/**
	 * class naming strategy
	 */
	private final ClassNamingStrategy classNamingStrategy;
	/**
	 * property naming strategy
	 */
	private final PropertyNamingStrategy propertyNamingStrategy;
	/**
	 * namespace to place classes in
	 */
	private final String namespace;
	/**
	 * root node to look at
	 */
	private final Node node;
	/**
	 * output path
	 */
	private final String outputPath;

	/**
	 * ctor
	 */
	public JCRJavaPersistenceGenerator(Node node, String namespace, String outputPath, ClassNamingStrategy classNamingStrategy,
			PropertyNamingStrategy propertyNamingStrategy) {
		this.node = node;
		this.namespace = namespace;
		this.classNamingStrategy = classNamingStrategy;
		this.propertyNamingStrategy = propertyNamingStrategy;
		this.outputPath = outputPath;
		/*
		 * make the output dir
		 */
		new File(outputPath + "/").mkdirs();
	}

	/**
	 * map nodes and produce java to the console
	 */
	public void execute() throws JCR2POJOException {
		try {
			/*
			 * get the mappings
			 */
			final HashMap<String, ClassMapping> mappings = mapNode(node);
			if ((null != mappings) && (mappings.size() > 0)) {
				/*
				 * walk
				 */
				final Iterator<String> iter = mappings.keySet().iterator();
				while (iter.hasNext()) {
					final String key = iter.next();
					final ClassMapping classMapping = mappings.get(key);
					if (classMapping.getFields().length > 0) {
						generatePOJO(classMapping);
						generateDAO(classMapping);
					}
				}
			}
		} catch (final Exception e) {
			throw new JCR2POJOException("Exception in reverseEngineer", e);
		}
	}

	/**
	 * generate DAO
	 */
	private void generateDAO(ClassMapping classMapping) throws JCR2POJOException {
		try {
			final FileOutputStream fos = new FileOutputStream(outputPath + "/" + classMapping.getName() + "DAO" + ".java");
			final DAOGenerator daoGenerator = new DAOGenerator();
			daoGenerator.generateDAO(classMapping.getName() + "DAO", namespace, fos);
		} catch (final Exception e) {
			throw new JCR2POJOException("Exception in generateJava", e);
		}
	}

	/**
	 * generate POJO
	 */
	private void generatePOJO(ClassMapping classMapping) throws JCR2POJOException {
		try {
			final FileOutputStream fos = new FileOutputStream(outputPath + "/" + classMapping.getName() + ".java");
			final POJOGenerator pojoGenerator = new POJOGenerator();
			pojoGenerator.generatePOJO(namespace, classMapping, fos);
		} catch (final Exception e) {
			throw new JCR2POJOException("Exception in generateJava", e);
		}
	}

	/**
	 * map all nodes that can be found
	 */
	private HashMap<String, ClassMapping> mapNode(Node node) throws JCR2POJOException {
		try {
			final HashMap<String, ClassMapping> ret = new HashMap<String, ClassMapping>();
			final ClassMappingBuilder entityMapper = new ClassMappingBuilderImpl(classNamingStrategy, propertyNamingStrategy);
			final NodeIterator iter = node.getNodes();
			while (iter.hasNext()) {
				final Node n = iter.nextNode();
				/*
				 * map
				 */
				final ClassMapping mapping = entityMapper.build(n);
				ret.put(mapping.getName(), mapping);
				/*
				 * node has nodes?
				 */
				if (node.hasNodes()) {
					final HashMap<String, ClassMapping> subCollection = mapNode(n);
					ret.putAll(subCollection);
				}
			}
			return ret;
		} catch (final Exception e) {
			throw new JCR2POJOException("Exception in mapNode", e);
		}
	}
}

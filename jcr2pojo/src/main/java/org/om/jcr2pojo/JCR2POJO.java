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
package org.om.jcr2pojo;

import java.io.ByteArrayOutputStream;

import javax.jcr.Session;

import org.om.core.impl.persistence.jcr.sessionfactory.impl.PropertiesConfiguredJCRSessionFactory;
import org.om.jcr2pojo.classgenerator.POJOGenerator;
import org.om.jcr2pojo.classmapping.ClassMapping;
import org.om.jcr2pojo.classmappingbuilder.ClassMappingBuilder;
import org.om.jcr2pojo.classmappingbuilder.impl.ClassMappingBuilderImpl;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl.DefaultPropertyNamingStrategy;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl.NodeIdentifierClassNamingStrategy;

/**
 * 
 * @author tome
 * 
 */
public class JCR2POJO {
	/**
	 * stuff we need
	 */
	private static ClassMappingBuilder classMappingBuilder = new ClassMappingBuilderImpl(new NodeIdentifierClassNamingStrategy(),
			new DefaultPropertyNamingStrategy());
	private static POJOGenerator pojoGenerator = new POJOGenerator();

	/**
	 * Ah "C", I knew you well!
	 */
	public static void main(String args[]) throws java.io.IOException {
		/*
		 * path
		 */
		final String jcrPath = args[0];
		/*
		 * package name
		 */
		final String packageName = args[2];
		/*
		 * get a session
		 */
		final Session session = new PropertiesConfiguredJCRSessionFactory().getSession();
		if (null != session) {
			/*
			 * build the mappings
			 */
			final ClassMapping classMapping = classMappingBuilder.build(jcrPath, session);
			if (null != classMapping) {
				/*
				 * build the class
				 */
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pojoGenerator.generatePOJO(packageName, classMapping, baos);
				System.out.println(baos.toString());
			}
			/*
			 * done
			 */
			session.logout();
		}
	}
}

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
package org.om.jcr2pojo.classgenerator;

import java.io.ByteArrayOutputStream;

import javax.jcr.Node;
import javax.jcr.Session;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.om.dao.jcr.sessionfactory.TransientRepositoryJCRSessionFactory;
import org.om.dao.util.RecursiveDelete;
import org.om.jcr2pojo.classmapping.ClassMapping;
import org.om.jcr2pojo.classmappingbuilder.ClassMappingBuilder;
import org.om.jcr2pojo.classmappingbuilder.impl.ClassMappingBuilderImpl;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl.DefaultPropertyNamingStrategy;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl.NodeIdentifierClassNamingStrategy;

/**
 * @author tome
 */
public class TestPOJOGenerator2 {
	@Before
	public void setUp() {
		try {
			final Session session = new TransientRepositoryJCRSessionFactory().getSession();
			Assert.assertNotNull(session);
			/*
			 * get the root node
			 */
			final Node rootNode = session.getRootNode();
			Assert.assertNotNull(rootNode);
			/*
			 * delete existing nodes
			 */
			RecursiveDelete.recursiveDelete(rootNode);
			/*
			 * add two nodes, one of which has properties
			 */
			final Node foo = rootNode.addNode("foo");
			final Node bar = foo.addNode("bar");
			bar.setProperty("foobar", "Horray!!");
			bar.setProperty("mycoolfield", "1000000");
			bar.setProperty("price", 1234);
			bar.setProperty("isAmortized", false);
			bar.setProperty("rate", 1.2);
			/*
			 * save to the jcr
			 */
			session.save();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@After
	public void tearDown() {
		try {
			final Session session = new TransientRepositoryJCRSessionFactory().getSession();
			Assert.assertNotNull(session);
			/*
			 * get the root node
			 */
			final Node rootNode = session.getRootNode();
			Assert.assertNotNull(rootNode);
			/*
			 * remove foo
			 */
			rootNode.getNode("foo/bar").remove();
			rootNode.getNode("foo").remove();
			/*
			 * save to the jcr
			 */
			session.save();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void test1() {
		try {
			/*
			 * session
			 */
			final Session session = new TransientRepositoryJCRSessionFactory().getSession();
			Assert.assertNotNull(session);
			/*
			 * mappings
			 */
			final ClassMappingBuilder classMappingBuilder = new ClassMappingBuilderImpl(new NodeIdentifierClassNamingStrategy(),
					new DefaultPropertyNamingStrategy());
			final ClassMapping classMapping = classMappingBuilder.build("foo/bar", session);
			Assert.assertNotNull(classMapping);
			Assert.assertNotNull(classMapping.getFields());
			Assert.assertTrue(classMapping.getFields().length == 5);
			/*
			 * class
			 */
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final POJOGenerator pojoGenerator = new POJOGenerator();
			pojoGenerator.generatePOJO("com.khubla", classMapping, baos);
			System.out.println(baos.toString());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

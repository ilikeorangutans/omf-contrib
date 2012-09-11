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

import junit.framework.Assert;

import org.junit.Test;
import org.om.jcr2pojo.classmapping.ClassMapping;
import org.om.jcr2pojo.classmapping.FieldMapping;

/**
 * @author tome
 */
public class TestPOJOGenerator {
	@Test
	public void test1() {
		try {
			/*
			 * build some mappings
			 */
			final ClassMapping classMapping = new ClassMapping("TestClass");

			/*
			 * add some fields
			 */
			classMapping.addField(new FieldMapping("a", String.class));
			classMapping.addField(new FieldMapping("b", Integer.class));
			classMapping.addField(new FieldMapping("c", Float.class));
			/*
			 * generate some java
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

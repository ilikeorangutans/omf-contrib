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
package org.om.dao.config;

import junit.framework.Assert;

import org.junit.Test;
import org.om.dao.config.impl.PropertiesFileObjectManagerConfiguration;

/**
 * @author tome
 */
public class TestXMLObjectManagerConfiguration {
	@Test
	public void testInstantiation() {
		try {
			final ObjectManagerConfiguration objectManagerConfiguration = PropertiesFileObjectManagerConfiguration.getObjectManagerConfiguration();
			Assert.assertNotNull(objectManagerConfiguration);
			Assert.assertNotNull(objectManagerConfiguration.getPersistenceContext());
			Assert.assertNotNull(objectManagerConfiguration.getSessionFactory());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

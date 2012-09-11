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

package org.om.dao.genericdao;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author tome
 *         <p>
 *         This test needs JackRabbit 2.4.0 running in standalone mode on
 *         localhost
 *         </p>
 */
public class TestGenericDAO {
	@Test
	@Ignore
	public void testWriteRead() {
		try {
			/*
			 * DAO
			 */
			final TestEntityDAO testEntityDAO = new TestEntityDAO();
			/*
			 * entity
			 */
			final TestEntity testEntity1 = new TestEntity();
			testEntity1.setFoobar("Horray!!");
			testEntity1.setBlargh(1000000);
			testEntity1.setId("tge");
			/*
			 * save it
			 */
			testEntityDAO.save(testEntity1);
			/*
			 * get it
			 */
			final TestEntity testEntity2 = testEntityDAO.get("tge");
			/*
			 * check
			 */
			Assert.assertTrue(testEntity1.getId().compareTo(testEntity2.getId()) == 0);
			Assert.assertTrue(testEntity1.getFoobar().compareTo(testEntity2.getFoobar()) == 0);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

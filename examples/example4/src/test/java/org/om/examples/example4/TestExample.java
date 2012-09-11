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
package org.om.examples.example4;

import junit.framework.Assert;

import org.junit.Test;
import org.om.dao.annotation.transactional.filter.JCRTransactionalModule;
import org.om.dao.genericdao.GenericDAO;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.om.examples.example4.Example4;
import com.om.examples.example4.pojo.MyPojo;
import com.om.examples.example4.service.MyService;

/**
 * @author tome
 */
public class TestExample {
	@Test
	public void test() {
		try {
			Example4.main(null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * test that we can get the service and that it has the DAO
	 */
	@Test
	public void test2() {
		try {
			Injector injector = Guice.createInjector(new JCRTransactionalModule());
			MyService svc = injector.getInstance(MyService.class);
			Assert.assertNotNull(svc);
			GenericDAO<MyPojo> dao = svc.getMypojodao();
			Assert.assertNotNull(dao);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

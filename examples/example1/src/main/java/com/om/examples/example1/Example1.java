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
package com.om.examples.example1;

import org.om.core.api.session.Session;
import org.om.dao.util.SessionUtil;

import com.om.examples.example1.dao.MyPojoDAO;
import com.om.examples.example1.pojo.MyPojo;

/**
 * @author tome
 */
public class Example1 {
	/**
	 * void main
	 */
	public static void main(String args[]) throws java.io.IOException, java.io.FileNotFoundException {
		/*
		 * verify that we can get a session.
		 */
		final Session session = SessionUtil.getSession();
		if (null != session) {
			MyPojoDAO dao = new MyPojoDAO();
			/*
			 * make an object
			 */
			final MyPojo myPojo = new MyPojo();
			myPojo.setCount(12);
			myPojo.setId("tge");
			myPojo.setRate(13.3);
			/*
			 * save the object
			 */
			// dao.save(myPojo);
			/*
			 * get the object back
			 */
			// MyPojo retrievedPojo = dao.get(myPojo.getId());
			// System.out.println(retrievedPojo.getId());
		} else {
			System.out.println("Unable to get session, check your settings");
		}
	}
}

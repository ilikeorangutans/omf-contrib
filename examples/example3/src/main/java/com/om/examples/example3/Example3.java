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
package com.om.examples.example3;

import org.om.core.api.session.Session;
import org.om.dao.util.SessionUtil;

import com.om.examples.example3.dao.MyPojoDAO;

/**
 * 
 * @author tome
 * 
 */
public class Example3 {
	/**
	 * void main
	 */
	public static void main(String args[]) throws java.io.IOException, java.io.FileNotFoundException {
		/*
		 * verify that we can get a session. This will also verify that XML
		 * autobeans are correctly configured and that the JCR can be connected
		 * to and logged in to
		 */
		Session session = SessionUtil.getSession();
		/*
		 * declare the DAO
		 */
		final MyPojoDAO dao = new MyPojoDAO();
		// dao.get(new Path("/jcr/mystuff/myPojo"));
	}
}

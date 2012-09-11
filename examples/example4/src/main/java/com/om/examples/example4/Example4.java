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
package com.om.examples.example4;

import org.om.dao.annotation.transactional.filter.JCRTransactionalModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.om.examples.example4.service.MyService;

/**
 * @author tome
 */
public class Example4 {
	/**
	 * service
	 */
	private static Injector injector = Guice.createInjector(new JCRTransactionalModule());

	/**
	 * void main
	 */
	public static void main(String args[]) throws java.io.IOException, java.io.FileNotFoundException {
		MyService svc = injector.getInstance(MyService.class);
		svc.doStuff();
	}
}

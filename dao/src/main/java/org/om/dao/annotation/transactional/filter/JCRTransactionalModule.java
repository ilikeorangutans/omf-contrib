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
package org.om.dao.annotation.transactional.filter;

import org.om.dao.annotation.transactional.Transactional;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author tome
 */
public class JCRTransactionalModule extends AbstractModule {
	@Override
	protected void configure() {
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), new JCRTransactionalMethodIterceptor());
	}
}
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
package com.om.examples.example4.service;

import com.google.inject.ImplementedBy;
import com.om.examples.example4.dao.MyPojoDAO;

/**
 * @author tome
 */
@ImplementedBy(MyServiceImpl.class)
public interface MyService {
	void doStuff();

	MyPojoDAO getMypojodao();
}

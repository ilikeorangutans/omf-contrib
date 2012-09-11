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
package org.om.examples.example1;

import junit.framework.Assert;

import org.junit.Test;

import com.om.examples.example1.Example1;

/**
 * @author tome
 */
public class TestExample {
	@Test
	public void test() {
		try {
			Example1.main(null);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

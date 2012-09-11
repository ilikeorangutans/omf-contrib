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
package org.om.jcr2pojo.classmapping;

import java.util.Hashtable;

/**
 * 
 * @author tome
 * 
 */
public class ClassMapping {

	/**
	 * class name
	 */
	private final String name;

	/**
	 * fields
	 */
	private final Hashtable<String, FieldMapping> fieldMappings = new Hashtable<String, FieldMapping>();

	/**
	 * ctor
	 */
	public ClassMapping(String name) {
		this.name = name;
	}

	public void addField(FieldMapping fieldMapping) {
		fieldMappings.put(fieldMapping.getName(), fieldMapping);
	}

	public FieldMapping[] getFields() {
		final FieldMapping[] ret = new FieldMapping[fieldMappings.size()];
		fieldMappings.values().toArray(ret);
		return ret;
	}

	public String getName() {
		return name;
	}
}

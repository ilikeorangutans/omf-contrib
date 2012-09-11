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
package org.om.jcr2pojo.entitymappingbuilder.namingstrategy.impl;

import javax.jcr.Property;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.PropertyNamingStrategy;

/**
 * 
 * @author tome
 * 
 */
public class DefaultPropertyNamingStrategy implements PropertyNamingStrategy {

	/**
	 * make a valid java field name from a jcr property name
	 */
	private String fixName(String name) {
		final int i = name.indexOf(":");
		if (-1 != i) {
			final char[] b = name.toCharArray();
			b[i + 1] = Character.toUpperCase(b[i + 1]);
			String ret = new String(b);
			ret = ret.replaceAll(":", "");
			return ret;
		} else {
			return name;
		}
	}

	public String generateName(Property property) throws JcrException {
		try {
			return fixName(property.getName());
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}

}

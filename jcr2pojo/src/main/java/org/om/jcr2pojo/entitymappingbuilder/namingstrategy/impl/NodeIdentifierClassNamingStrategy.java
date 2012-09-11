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

import javax.jcr.Node;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.jcr2pojo.entitymappingbuilder.namingstrategy.ClassNamingStrategy;

/**
 * 
 * @author tome
 *         <p>
 *         Name the generated java classes based on the Node ID
 *         </p>
 * 
 */
public class NodeIdentifierClassNamingStrategy implements ClassNamingStrategy {

	public String generateName(Node node) throws JcrException {
		try {
			return new String("Id_") + node.getIdentifier().replaceAll("-", "_");
		} catch (final Exception e) {
			throw new JcrException("Exception in generateName", e);
		}
	}
}

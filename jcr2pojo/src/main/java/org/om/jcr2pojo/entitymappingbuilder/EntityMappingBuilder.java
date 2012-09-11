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
package org.om.jcr2pojo.entitymappingbuilder;

import javax.jcr.Node;

import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.session.Session;
import org.om.core.impl.persistence.jcr.exception.JcrException;

public interface EntityMappingBuilder {

	/**
	 * build entity mapping
	 */
	public abstract EntityMapping build(Node node) throws JcrException;

	/**
	 * build entity mapping
	 */
	public abstract EntityMapping build(String jcrPath, Session session) throws JcrException;
}
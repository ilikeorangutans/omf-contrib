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
package org.om.jcr2pojo.reveng;

import java.io.InputStream;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.Session;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.dao.jcr.sessionfactory.TransientRepositoryJCRSessionFactory;
import org.om.dao.util.RecursiveDelete;

/**
 * @author tome
 *         <p>
 *         A little helper for importing some data for testing purposes
 *         </p>
 */
public class ImportUtil {
	public static void importXML(String nodeName, InputStream xml) throws JcrException {
		try {
			final Session session = new TransientRepositoryJCRSessionFactory().getSession();
			RecursiveDelete.recursiveDelete(session.getRootNode());
			final Node node = session.getRootNode().addNode(nodeName, "nt:unstructured");
			session.importXML(node.getPath(), xml, ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING);
			session.save();
			session.logout();
		} catch (final Exception e) {
			throw new JcrException("Exception in importXML", e);
		}
	}
}

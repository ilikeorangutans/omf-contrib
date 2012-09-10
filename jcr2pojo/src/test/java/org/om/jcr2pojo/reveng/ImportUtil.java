package org.om.jcr2pojo.reveng;

import java.io.InputStream;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.Session;

import org.om.core.impl.persistence.jcr.exception.JcrException;
import org.om.core.impl.persistence.jcr.sessionfactory.impl.PropertiesConfiguredJCRSessionFactory;
import org.om.core.impl.persistence.jcr.util.RecursiveDelete;

/**
 * @author tome
 *         <p>
 *         A little helper for importing some data for testing purposes
 *         </p>
 */
public class ImportUtil {
	public static void importXML(String nodeName, InputStream xml) throws JcrException {
		try {
			final Session session = new PropertiesConfiguredJCRSessionFactory().getSession();
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

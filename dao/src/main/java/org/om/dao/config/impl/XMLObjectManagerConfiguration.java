package org.om.dao.config.impl;

import java.io.InputStream;
import java.util.Properties;

import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.session.factory.SessionFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceContext;
import org.om.core.impl.persistence.jcr.JcrPersistenceDelegateFactory;
import org.om.core.impl.persistence.jcr.exception.JCRException;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;
import org.om.core.impl.session.factory.DefaultSessionFactory;
import org.om.dao.config.ObjectManagerConfiguration;

/**
 * @author tome
 */
public class XMLObjectManagerConfiguration implements ObjectManagerConfiguration {
   /**
    * props files
    */
   private static final String PROPERTIES_FILE = "/objectmanagerdao.properties";
   /**
    * session factory
    */
   private static final String SESSIONFACTORY = "jcrsessionfactory";

   /**
    * singleton getter
    */
   public static ObjectManagerConfiguration getObjectManagerConfiguration() throws JCRException {
      if (null == instance) {
         instance = new XMLObjectManagerConfiguration();
      }
      return instance;
   }

   /**
    * OM session factory
    */
   private SessionFactory sessionFactory;
   /**
    * persistence context
    */
   private PersistenceContext persistenceContext;
   /**
    * singleton
    */
   private static XMLObjectManagerConfiguration instance = null;

   /**
    * private ctor
    */
   private XMLObjectManagerConfiguration() throws JCRException {
      try {
         configure();
      } catch (final Exception e) {
         throw new JCRException("Exception in XMLObjectManagerConfiguration ctor", e);
      }
   }

   /**
    * configure the OM Configuration
    */
   private void configure() throws JCRException {
      try {
         final InputStream is = XMLObjectManagerConfiguration.class.getResourceAsStream(PROPERTIES_FILE);
         if (null != is) {
            /*
             * props
             */
            Properties props = new Properties();
            props.load(is);
            /*
             * session factory
             */
            String sessionFactoryName = props.getProperty(SESSIONFACTORY);
            if (null != sessionFactoryName) {
               final JCRSessionFactory jcrSessionFactory = (JCRSessionFactory) Class.forName(sessionFactoryName).newInstance();
               /*
                * build up the rest
                */
               persistenceContext = new JcrPersistenceContext(jcrSessionFactory);
               final JcrPersistenceDelegateFactory jcrPersistenceDelegateFactory = new JcrPersistenceDelegateFactory();
               sessionFactory = new DefaultSessionFactory(jcrPersistenceDelegateFactory);
            } else {
               throw new Exception("Unable to find property '" + SESSIONFACTORY + "'");
            }
         } else {
            throw new Exception("Unable to open properties file '" + PROPERTIES_FILE + "'");
         }
      } catch (final Exception e) {
         throw new JCRException("Exception in parseConfiguration ctor", e);
      }
   }

   public PersistenceContext getPersistenceContext() throws Exception {
      return persistenceContext;
   }

   public SessionFactory getSessionFactory() throws Exception {
      return sessionFactory;
   }
}

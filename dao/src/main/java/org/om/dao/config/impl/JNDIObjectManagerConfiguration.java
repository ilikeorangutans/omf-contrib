package org.om.dao.config.impl;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.om.core.api.persistence.PersistenceAdapterFactory;
import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.session.factory.SessionFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceAdapterFactory;
import org.om.core.impl.persistence.jcr.JcrPersistenceContext;
import org.om.core.impl.persistence.jcr.sessionfactory.JCRSessionFactory;
import org.om.core.impl.session.factory.DefaultSessionFactory;
import org.om.dao.config.ObjectManagerConfiguration;
import org.om.dao.exception.DAOException;

/**
 * @author tome
 */
public class JNDIObjectManagerConfiguration implements ObjectManagerConfiguration {
   /**
    * JNDI name
    */
   private static final String JNDI_NAME = "java:comp/env/om/sessionfactory";
   /**
    * OM session factory
    */
   private SessionFactory sessionFactory;
   /**
    * JCRSessionFactory
    */
   private JCRSessionFactory jcrSessionFactory;
   /**
    * singleton
    */
   private static JNDIObjectManagerConfiguration instance = null;

   /**
    * singleton getter
    */
   public static ObjectManagerConfiguration getObjectManagerConfiguration() throws DAOException {
      if (null == instance) {
         instance = new JNDIObjectManagerConfiguration();
      }
      return instance;
   }

   /**
    * private ctor
    */
   private JNDIObjectManagerConfiguration() throws DAOException {
      try {
         configure();
      } catch (final Exception e) {
         throw new DAOException("Exception in XMLObjectManagerConfiguration ctor", e);
      }
   }

   /**
    * configure the OM Configuration
    */
   private void configure() throws DAOException {
      try {
         final Context initialContext = new InitialContext();
         jcrSessionFactory = (JCRSessionFactory) initialContext.lookup(JNDI_NAME);
         final PersistenceAdapterFactory persistenceDelegateFactory = new JcrPersistenceAdapterFactory();
         sessionFactory = new DefaultSessionFactory(persistenceDelegateFactory);
      } catch (final Exception e) {
         throw new DAOException("Exception in configure", e);
      }
   }

   public PersistenceContext getPersistenceContext() throws DAOException {
      return new JcrPersistenceContext(jcrSessionFactory.getSession());
   }

   public SessionFactory getSessionFactory() throws DAOException {
      return sessionFactory;
   }
}

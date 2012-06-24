package org.om.dao.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author tome
 */
public class TestSessionUtil {
   @Test
   public void testInstantiation() {
      try {
         Assert.assertNotNull(SessionUtil.getSession());
         Assert.assertNotNull(SessionUtil.getPersistenceContext());
      } catch (final Exception e) {
         e.printStackTrace();
         Assert.fail();
      }
   }
}

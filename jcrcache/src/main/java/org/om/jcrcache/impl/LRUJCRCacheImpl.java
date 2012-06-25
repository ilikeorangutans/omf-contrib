package org.om.jcrcache.impl;

import java.util.Collection;

import org.om.core.api.exception.ObjectMapperException;
import org.om.core.api.mapping.CollectionMapping;
import org.om.core.api.mapping.Mapping;
import org.om.core.api.mapping.PropertyMapping;
import org.om.core.api.persistence.PersistenceAdapter;
import org.om.jcrcache.JCRCache;

/**
 * An LRU cache which wraps a JCR persistence delegate. This class is thread-safe
 * 
 * @author tome
 */
public class LRUJCRCacheImpl implements JCRCache {
   /**
    * the actual PersistenceAdapter
    */
   private final PersistenceAdapter persistenceAdapter;
   /**
    * the cache
    */
   private final LRUCache<String, Object> cache;
   /**
    * cache size
    */
   private final int cacheSize;

   /**
    * ctor
    */
   public LRUJCRCacheImpl(PersistenceAdapter persistenceAdapter, int cacheSize) {
      this.persistenceAdapter = persistenceAdapter;
      this.cacheSize = cacheSize;
      cache = new LRUCache<String, Object>(this.cacheSize);
   }

   private String getKey(PropertyMapping mapping) throws ObjectMapperException {
      return mapping.getFieldname();
   }

   public Object getProperty(PropertyMapping propertyMapping) throws ObjectMapperException {
      synchronized (this) {
         /*
          * search the cache
          */
         Object ret = cache.get(getKey(propertyMapping));
         if (null == ret) {
            /*
             * fine, use the delegate
             */
            ret = persistenceAdapter.getProperty(propertyMapping);
         }
         return ret;
      }
   }

   public void setProperty(PropertyMapping propertyMapping, Object object) throws ObjectMapperException {
      synchronized (this) {
         if ((null != propertyMapping) && (null != object)) {
            /*
             * set via the real delegate
             */
            persistenceAdapter.setProperty(propertyMapping, object);
            /*
             * item is cached?
             */
            if (cache.containsKey(getKey(propertyMapping))) {
               /*
                * remove
                */
               cache.remove(getKey(propertyMapping));
            }
            /*
             * cache
             */
            cache.put(getKey(propertyMapping), object);
         }
      }
   }

   public int size() {
      return cache.size();
   }

   public void delete() throws ObjectMapperException {
      synchronized (this) {
         /*
          * remove the cache
          */
         cache.clear();
         /*
          * delegate
          */
         this.persistenceAdapter.delete();
      }
   }

   public Collection<?> getCollection(CollectionMapping collectionMapping) {
      synchronized (this) {
         /*
          * delegate
          */
         return persistenceAdapter.getCollection(collectionMapping);
      }
   }

   public boolean canProvide(Mapping mapping) throws ObjectMapperException {
      synchronized (this) {
         /*
          * delegate
          */
         return persistenceAdapter.canProvide(mapping);
      }
   }
}

package org.om.jcr2pojo.classgenerator;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.om.core.api.mapping.field.Mapping;
import org.om.core.impl.mapping.EntityMappingImpl;
import org.om.core.impl.mapping.field.ImmutablePropertyMapping;

/**
 * @author tome
 */
public class TestPOJOGenerator {
	@Test
	public void test1() {
		try {
			/*
			 * build some mappings
			 */
			final EntityMappingImpl entityMappingImpl = new EntityMappingImpl("TestClass");
			final Set<Mapping> mappings = new HashSet<Mapping>();
			entityMappingImpl.setPropertyMap(new ImmutablePropertyMap(mappings));
			/*
			 * add some fields
			 */
			mappings.add(new ImmutablePropertyMapping("a", false, null, String.class, null, null, null));
			mappings.add(new ImmutablePropertyMapping("b", false, null, Integer.class, null, null, null));
			mappings.add(new ImmutablePropertyMapping("c", false, null, Float.class, null, null, null));
			/*
			 * generate some java
			 */
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final POJOGenerator pojoGenerator = new POJOGenerator();
			pojoGenerator.generatePOJO("com.khubla", entityMappingImpl, baos);
			System.out.println(baos.toString());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package serviciopersonas.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import serviciopersonas.exception.NoSuchPersonaException;

import serviciopersonas.model.Persona;

import serviciopersonas.service.persistence.PersonaPersistence;
import serviciopersonas.service.persistence.PersonaUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class PersonaPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PersonaUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Persona> iterator = _personas.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		String pk = RandomTestUtil.randomString();

		Persona persona = _persistence.create(pk);

		Assert.assertNotNull(persona);

		Assert.assertEquals(persona.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Persona newPersona = addPersona();

		_persistence.remove(newPersona);

		Persona existingPersona = _persistence.fetchByPrimaryKey(newPersona.getPrimaryKey());

		Assert.assertNull(existingPersona);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPersona();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		String pk = RandomTestUtil.randomString();

		Persona newPersona = _persistence.create(pk);

		newPersona.setNombre(RandomTestUtil.randomString());

		newPersona.setEdad(RandomTestUtil.nextInt());

		_personas.add(_persistence.update(newPersona));

		Persona existingPersona = _persistence.findByPrimaryKey(newPersona.getPrimaryKey());

		Assert.assertEquals(existingPersona.getDni(), newPersona.getDni());
		Assert.assertEquals(existingPersona.getNombre(), newPersona.getNombre());
		Assert.assertEquals(existingPersona.getEdad(), newPersona.getEdad());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Persona newPersona = addPersona();

		Persona existingPersona = _persistence.findByPrimaryKey(newPersona.getPrimaryKey());

		Assert.assertEquals(existingPersona, newPersona);
	}

	@Test(expected = NoSuchPersonaException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Persona> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("FOO_Persona", "dni", true,
			"nombre", true, "edad", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Persona newPersona = addPersona();

		Persona existingPersona = _persistence.fetchByPrimaryKey(newPersona.getPrimaryKey());

		Assert.assertEquals(existingPersona, newPersona);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		Persona missingPersona = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPersona);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Persona newPersona1 = addPersona();
		Persona newPersona2 = addPersona();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPersona1.getPrimaryKey());
		primaryKeys.add(newPersona2.getPrimaryKey());

		Map<Serializable, Persona> personas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, personas.size());
		Assert.assertEquals(newPersona1,
			personas.get(newPersona1.getPrimaryKey()));
		Assert.assertEquals(newPersona2,
			personas.get(newPersona2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		String pk1 = RandomTestUtil.randomString();

		String pk2 = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Persona> personas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(personas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Persona newPersona = addPersona();

		String pk = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPersona.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Persona> personas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, personas.size());
		Assert.assertEquals(newPersona, personas.get(newPersona.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Persona> personas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(personas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Persona newPersona = addPersona();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPersona.getPrimaryKey());

		Map<Serializable, Persona> personas = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, personas.size());
		Assert.assertEquals(newPersona, personas.get(newPersona.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Persona newPersona = addPersona();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Persona.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("dni", newPersona.getDni()));

		List<Persona> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Persona existingPersona = result.get(0);

		Assert.assertEquals(existingPersona, newPersona);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Persona.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("dni",
				RandomTestUtil.randomString()));

		List<Persona> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Persona newPersona = addPersona();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Persona.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("dni"));

		Object newDni = newPersona.getDni();

		dynamicQuery.add(RestrictionsFactoryUtil.in("dni",
				new Object[] { newDni }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDni = result.get(0);

		Assert.assertEquals(existingDni, newDni);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Persona.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("dni"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("dni",
				new Object[] { RandomTestUtil.randomString() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Persona addPersona() throws Exception {
		String pk = RandomTestUtil.randomString();

		Persona persona = _persistence.create(pk);

		persona.setNombre(RandomTestUtil.randomString());

		persona.setEdad(RandomTestUtil.nextInt());

		_personas.add(_persistence.update(persona));

		return persona;
	}

	private List<Persona> _personas = new ArrayList<Persona>();
	private PersonaPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}
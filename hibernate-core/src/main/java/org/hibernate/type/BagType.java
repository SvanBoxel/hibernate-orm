/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;

/**
 * A {@link CollectionType} for "bag" collections (non-unique, unordered), backed by a
 * {@link PersistentBag}.
 * <p>
 * A bag is a collection that allows duplicate elements and has no inherent ordering.
 * This is essentially a {@link java.util.Collection} that maps to a database table
 * without unique constraints on the collection elements.
 * <p>
 * The persistent collection wrapper {@link PersistentBag} provides lazy loading
 * and dirty checking functionality while allowing duplicate elements.
 *
 * @see CollectionType
 * @see PersistentBag
 *
 * @author Hibernate Team
 */
public class BagType extends CollectionType {

	public BagType(String role, String propertyRef) {
		super(role, propertyRef );
	}

	@Override
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.BAG;
	}

	@Override
	public Class<?> getReturnedClass() {
		return Collection.class;
	}

	@Override
	public PersistentCollection<?> instantiate(SharedSessionContractImplementor session, CollectionPersister persister, Object key)
	throws HibernateException {
		return new PersistentBag<>( session );
	}

	@Override
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return new PersistentBag<>( session, (Collection<?>) collection );
	}

	@Override
	public Object instantiate(int anticipatedSize) {
		return anticipatedSize <= 0 ? new ArrayList<>() : new ArrayList<>( anticipatedSize + 1 );
	}

}

/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentIdentifierBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;

/**
 * A {@link CollectionType} for identifier bags - collections with duplicate elements
 * where each element has a surrogate identifier, backed by a {@link PersistentIdentifierBag}.
 * <p>
 * An identifier bag is similar to a regular bag but each element in the collection
 * has an associated surrogate identifier (typically a primary key in the collection table).
 * This allows for more efficient updates and deletes of individual collection elements.
 * <p>
 * The persistent collection wrapper {@link PersistentIdentifierBag} provides lazy loading
 * and dirty checking functionality while maintaining element identifiers.
 *
 * @see CollectionType
 * @see PersistentIdentifierBag
 * @see BagType
 *
 * @author Hibernate Team
 */
public class IdentifierBagType extends CollectionType {

	public IdentifierBagType(String role, String propertyRef) {
		super(role, propertyRef );
	}

	@Override
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.ID_BAG;
	}

	@Override
	public PersistentCollection<?> instantiate(
			SharedSessionContractImplementor session,
			CollectionPersister persister, Object key)
		throws HibernateException {

		return new PersistentIdentifierBag<>( session );
	}

	@Override
	public Object instantiate(int anticipatedSize) {
		return anticipatedSize <= 0 ? new ArrayList<>() : new ArrayList<>( anticipatedSize + 1 );
	}

	@Override
	public Class<?> getReturnedClass() {
		return java.util.Collection.class;
	}

	@Override
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return new PersistentIdentifierBag<>( session, (java.util.Collection<?>) collection );
	}

}

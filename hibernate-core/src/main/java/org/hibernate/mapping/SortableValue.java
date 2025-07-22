/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

/**
 * Interface for mapping elements that represent sortable collections or values.
 * <p>
 * This interface is implemented by mapping elements that can be configured
 * to maintain a sorted order, such as sorted sets and maps. It provides
 * methods to determine if the value is sorted and to retrieve the properties
 * used for sorting.
 * <p>
 * When a value is sortable, the sort order can be defined either through
 * natural ordering (Comparable) or through explicitly specified property
 * orderings.
 *
 * @see Collection
 * @see SortedSet
 * @see SortedMap
 *
 * @author Hibernate Team
 */
public interface SortableValue {

	boolean isSorted();

	int[] sortProperties();
}

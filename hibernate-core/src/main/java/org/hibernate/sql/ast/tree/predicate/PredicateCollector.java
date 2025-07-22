/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import java.util.function.Consumer;

/**
 * A utility class for collecting and combining {@link Predicate} instances
 * into a single composite predicate.
 * <p>
 * This collector implements {@link Consumer} to allow for easy accumulation
 * of predicates using functional programming patterns. Each predicate added
 * is combined with the existing predicate using logical AND operations.
 * <p>
 * The collector is commonly used in query building scenarios where multiple
 * conditions need to be gathered and combined into a single WHERE clause
 * predicate.
 *
 * @see Predicate
 * @see Consumer
 *
 * @author Hibernate Team
 */
public class PredicateCollector implements Consumer<Predicate> {
	private Predicate predicate;

	public PredicateCollector() {
	}

	public PredicateCollector(Predicate predicate) {
		this.predicate = predicate;
	}

	public void applyPredicate(Predicate incomingPredicate) {
		this.predicate = Predicate.combinePredicates( this.predicate, incomingPredicate );
	}

	@Override
	public void accept(Predicate predicate) {
		applyPredicate( predicate );
	}

	public Predicate getPredicate() {
		return predicate;
	}
}

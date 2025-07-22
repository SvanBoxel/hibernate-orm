/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.type.descriptor.java.JavaType;

/**
 * A {@link DomainResultAssembler} implementation for basic parts that are not fetched.
 * <p>
 * This assembler is used when a basic attribute or component part is referenced
 * in a query but not actually selected/fetched. It serves as a placeholder
 * in the result processing pipeline, always returning {@code null} since
 * the value was not fetched from the database.
 * <p>
 * This is commonly used in scenarios where only certain parts of an entity
 * or component are loaded, but the complete structure needs to be maintained
 * for proper result processing.
 *
 * @param <J> The Java type of the unfetched value
 *
 * @see DomainResultAssembler
 * @see BasicResult
 *
 * @author Hibernate Team
 */
public class UnfetchedBasicPartResultAssembler<J>  implements DomainResultAssembler<J> {

	private final JavaType<J> javaType;

	public UnfetchedBasicPartResultAssembler(JavaType<J> javaType) {
		this.javaType = javaType;
	}

	@Override
	public J assemble(RowProcessingState rowProcessingState) {
		return null;
	}

	@Override
	public JavaType<J> getAssembledJavaType() {
		return javaType;
	}

}

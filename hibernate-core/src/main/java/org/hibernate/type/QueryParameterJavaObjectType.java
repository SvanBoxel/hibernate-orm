/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

/**
 * A specialized {@link JavaObjectType} implementation for handling query parameter values.
 * <p>
 * This type extends the generic {@link JavaObjectType} to provide specific handling
 * for query parameter binding scenarios where the Java type is known but the
 * corresponding Hibernate type needs to be resolved dynamically.
 * <p>
 * The type is typically used internally by the query parameter binding mechanism
 * to defer type resolution until the actual SQL type mapping is required,
 * allowing for flexible parameter binding in queries.
 *
 * @see JavaObjectType
 * @see BasicType
 *
 * @author Hibernate Team
 */
public class QueryParameterJavaObjectType extends JavaObjectType {

	public static final QueryParameterJavaObjectType INSTANCE = new QueryParameterJavaObjectType();

	public QueryParameterJavaObjectType() {
		super();
	}

	@Override
	public String getName() {
		return "QUERY_PARAMETER_JAVA_OBJECT";
	}
}

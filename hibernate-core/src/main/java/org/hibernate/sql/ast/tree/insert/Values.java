/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.insert;

import org.hibernate.sql.ast.tree.expression.Expression;

import java.util.List;

/**
 * Represents a VALUES clause in an SQL INSERT statement within the SQL AST.
 * <p>
 * This class encapsulates a list of expressions that represent the values
 * to be inserted into the target table. Each {@link Expression} in the list
 * corresponds to a column value in the INSERT statement.
 * <p>
 * The VALUES clause is a fundamental part of INSERT statements, specifying
 * the actual data to be inserted into the database table columns.
 *
 * @see Expression
 * @see InsertSelectStatement
 *
 * @author Hibernate Team
 */
public class Values {
	private final List<Expression> expressions;

	public Values(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}
}

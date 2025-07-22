/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function;

import jakarta.persistence.TemporalType;
import org.hibernate.dialect.Dialect;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.common.TemporalUnit;
import org.hibernate.query.sqm.produce.function.internal.PatternRenderer;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.ExtractUnit;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

import static org.hibernate.query.common.TemporalUnit.EPOCH;
import static org.hibernate.type.spi.TypeConfiguration.getSqlTemporalType;

/**
 * Oracle-specific implementation of the EXTRACT function.
 * <p>
 * This class extends {@link ExtractFunction} to provide Oracle-specific
 * handling of temporal unit extraction from date/time values. Oracle has
 * unique behaviors and syntax requirements for certain extract operations.
 * <p>
 * Oracle-specific features:
 * <ul>
 *   <li>Special handling for EPOCH extraction</li>
 *   <li>Oracle-specific temporal type handling</li>
 *   <li>Proper casting and conversion for Oracle date/time types</li>
 *   <li>Support for Oracle's unique temporal functions</li>
 * </ul>
 * <p>
 * The implementation ensures that extract operations work correctly
 * with Oracle's date and timestamp types while maintaining compatibility
 * with the standard EXTRACT function interface.
 *
 * @see ExtractFunction
 * @see OracleDialect
 * @see TemporalUnit
 *
 * @author Hibernate Team
 */
public class OracleExtractFunction extends ExtractFunction {
	public OracleExtractFunction(Dialect dialect, TypeConfiguration typeConfiguration) {
		super( dialect, typeConfiguration );
	}

	@Override
	public void render(
			SqlAppender sqlAppender,
			List<? extends SqlAstNode> sqlAstArguments,
			ReturnableType<?> returnType,
			SqlAstTranslator<?> walker) {
		new PatternRenderer( extractPattern( sqlAstArguments ) ).render( sqlAppender, sqlAstArguments, walker );
	}

	@SuppressWarnings("deprecation")
	private String extractPattern(List<? extends SqlAstNode> sqlAstArguments) {
		final ExtractUnit field = (ExtractUnit) sqlAstArguments.get( 0 );
		final TemporalUnit unit = field.getUnit();
		if ( unit == EPOCH ) {
			final Expression expression = (Expression) sqlAstArguments.get( 1 );
			final JdbcMappingContainer type = expression.getExpressionType();
			final TemporalType temporalType = type != null ? getSqlTemporalType( type ) : null;
			if ( temporalType == TemporalType.DATE ) {
				return "trunc((cast(from_tz(cast(?2 as timestamp),'UTC') as date) - date '1970-1-1')*86400)";
			}
		}
		return dialect.extractPattern( unit );
	}
}

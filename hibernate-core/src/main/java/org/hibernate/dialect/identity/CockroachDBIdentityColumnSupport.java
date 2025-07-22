/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;

import java.sql.Types;

import org.hibernate.MappingException;

/**
 * Identity column support implementation for CockroachDB.
 * <p>
 * This class provides CockroachDB-specific support for identity/auto-increment
 * columns. It extends {@link IdentityColumnSupportImpl} to handle CockroachDB's
 * unique serial column implementation.
 * <p>
 * CockroachDB identity column characteristics:
 * <ul>
 *   <li>Supports SERIAL2, SERIAL4, and SERIAL8 column types</li>
 *   <li>Uses unique_rowid() function for value generation by default</li>
 *   <li>Full support requires sql.defaults.serial_normalization=sql_sequence setting</li>
 *   <li>SERIAL4 support is not enabled by default in all versions</li>
 * </ul>
 * <p>
 * <strong>Note:</strong> This implementation currently returns {@code false}
 * for {@link #supportsIdentityColumns()} due to configuration requirements
 * and version-dependent limitations.
 *
 * @see IdentityColumnSupport
 * @see IdentityColumnSupportImpl
 * @see CockroachDBDialect
 *
 * @author Hibernate Team
 */
public class CockroachDBIdentityColumnSupport extends IdentityColumnSupportImpl {

	public static final CockroachDBIdentityColumnSupport INSTANCE = new CockroachDBIdentityColumnSupport();

	@Override
	public boolean supportsIdentityColumns() {
		// Full support requires setting the sql.defaults.serial_normalization=sql_sequence in CockroachDB.
		// Also, support for serial4 is not enabled by default: https://github.com/cockroachdb/cockroach/issues/26925#issuecomment-1255293916
		return false;
	}

	@Override
	// CockroachDB does not create a sequence for id columns
	public String getIdentitySelectString(String table, String column, int type) {
		return "select 1";
	}

	@Override
	public String getIdentityColumnString(int type) {
		// Note that the unique_rowid() function used to generated values with serial_normalization=rowid (default)
		// will always produce INT8 (Types.BIGINT) values which might not fit other data types.
		// See https://www.cockroachlabs.com/docs/stable/serial.html
		switch ( type ) {
			case Types.TINYINT:
			case Types.SMALLINT:
				return "serial2 not null";
			case Types.INTEGER:
				return "serial4 not null";
			case Types.BIGINT:
				return "serial8 not null";
			default:
				throw new MappingException( "illegal identity column type");
		}
	}

	@Override
	public boolean hasDataTypeInIdentityColumn() {
		return false;
	}
}

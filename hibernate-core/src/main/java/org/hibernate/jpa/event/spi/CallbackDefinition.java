/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.event.spi;

import java.io.Serializable;

import org.hibernate.resource.beans.spi.ManagedBeanRegistry;

/**
 * Definition of a JPA callback method for entity lifecycle events.
 * <p>
 * This interface represents a callback definition that can be used to create
 * {@link Callback} instances for entity lifecycle events such as
 * {@literal @PrePersist}, {@literal @PostPersist}, {@literal @PreUpdate},
 * {@literal @PostUpdate}, {@literal @PreRemove}, {@literal @PostRemove},
 * {@literal @PostLoad}.
 * <p>
 * Callback definitions provide a factory mechanism for creating actual
 * callback instances, allowing for deferred instantiation and dependency
 * injection through the {@link ManagedBeanRegistry}.
 * <p>
 * The definition is serializable to support proper state management
 * across different contexts and sessions.
 *
 * @see Callback
 * @see CallbackRegistry
 * @see ManagedBeanRegistry
 * @see jakarta.persistence.PrePersist
 * @see jakarta.persistence.PostPersist
 *
 * @author Hibernate Team
 */
public interface CallbackDefinition extends Serializable {

	Callback createCallback(ManagedBeanRegistry beanRegistry);

}

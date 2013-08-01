/*
 * Chat Common
 * Copyright (C) 2010 Surevine Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */
package com.surevine.chat.common.xmpp.security.label;

/**
 * This class represents a protective marking that forms part of a security label.
 */
public class ProtectiveMarking implements ISecurityLabelPart {

    /**
     * The name of the protective marking.
     */
    private String name;

    /**
     * Default constructor for GWT serialisation.
     */
    @SuppressWarnings("unused")
    private ProtectiveMarking() {
        // NO-OP
    }

    /**
     * Constructs a <code>ProtectiveMarking</code> with a name.
     *
     * @param name
     *            The name of the protective marking.
     */
    public ProtectiveMarking(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of the protective marking.
     *
     * @return Returns the name of the protective marking.
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        int hash = 1;
        final int prime = 31;
        hash = hash * prime + (name == null ? 0 : name.hashCode());
        return hash;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        ProtectiveMarking that = (ProtectiveMarking) other;
        return this.name == null ? that.name == null : this.name.equals(that.name);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("ProtectiveMarking=[");
        buffer.append("name=");
        buffer.append(name);
        buffer.append("]");
        return buffer.toString();
    }

}

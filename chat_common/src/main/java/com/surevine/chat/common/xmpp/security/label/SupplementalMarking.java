/*
 * Copyright (C) 2010 Surevine Limited
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
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
 * This abstract base class represents any type of supplemental marking contained within a security
 * label.
 */
public abstract class SupplementalMarking implements ISecurityLabelPart {

    /**
     * The name of the supplemental marking.
     */
    private String name;

    /**
     * The type of the supplemental marking.
     */
    private SupplementalMarkingType type;

    /**
     * Default constructor for GWT serialisation.
     */
    protected SupplementalMarking() {
        // NO-OP
    }

    /**
     * Constructs a <code>SupplementalMarking</code> with a name.
     *
     * @param name
     *            The name of the marking.
     * @param type
     *            The type of the marking.
     */
    protected SupplementalMarking(final String name, final SupplementalMarkingType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the type attribute for the supplemental marking.
     *
     * @return The type attribute for the supplemental marking.
     */
    public SupplementalMarkingType getType() {
        return type;
    }

    /**
     * Gets the name of the supplemental marking.
     *
     * @return The name of the supplemental marking.
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
        hash = hash * prime + (type == null ? 0 : type.hashCode());
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
        if (!(other instanceof SupplementalMarking)) {
            return false;
        }
        SupplementalMarking that = (SupplementalMarking) other;
        boolean isEqual = true;
        isEqual &= (this.name == null ? that.name == null : this.name.equals(that.name));
        isEqual &= (this.type == null ? that.type == null : this.type.equals(that.type));
        return isEqual;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("SupplementalMarking=[");
        buffer.append("name=");
        buffer.append(name);
        buffer.append(", type=");
        buffer.append(type);
        buffer.append("]");
        return buffer.toString();
    }

}

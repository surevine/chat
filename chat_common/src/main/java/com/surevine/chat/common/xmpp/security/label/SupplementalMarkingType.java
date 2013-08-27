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
 * Type safe enumeration of supplemental marking types.
 */
public enum SupplementalMarkingType {

    /**
     * Open group supplemental marking.
     */
    OPEN_GROUP("openGroup");

    /**
     * The name of the supplemental marking type.
     */
    private final String name;

    /**
     * Constructs a <code>SupplementalMarkingType</code> with a name.
     *
     * @param name
     *            The name of the type.
     */
    SupplementalMarkingType(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        return this.name;
    }

}

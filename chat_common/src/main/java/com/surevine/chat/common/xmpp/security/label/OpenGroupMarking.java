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
 * This class represents an "open group" (OR) supplemental marking.
 */
public class OpenGroupMarking extends SupplementalMarking {

    /**
     * Default constructor for GWT serialisation.
     */
    @SuppressWarnings("unused")
    private OpenGroupMarking() {
        // NO-OP
    }

    /**
     * Constructs a new <code>OpenGroupMarking</code> with a name.
     *
     * @param name
     *            The open group marking name.
     */
    public OpenGroupMarking(final String name) {
        super(name, SupplementalMarkingType.OPEN_GROUP);
    }

}

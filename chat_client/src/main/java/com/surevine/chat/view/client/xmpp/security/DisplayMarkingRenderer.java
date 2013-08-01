/*
 * Chat Client
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
package com.surevine.chat.view.client.xmpp.security;

import java.util.HashMap;
import java.util.Map;

import com.surevine.chat.common.xmpp.security.IDisplayMarkingRenderer;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;

/**
 * Concrete implementation of a {@link IDisplayMarkingRenderer} that is backed
 * by GWT.
 */
public class DisplayMarkingRenderer implements IDisplayMarkingRenderer {

    /**
     * A map containing security label to colour mappings.
     */
    private Map<String, DisplayMarkingColour> colours = null;

    /**
     * Constructs a <code>DisplayMarkingRenderer</code> with a map of security
     * labels and display marking colours.
     * 
     * @param colours
     *            The map of security label to display marking colour mappings.
     */
    public DisplayMarkingRenderer(
            final Map<String, DisplayMarkingColour> colours) {
        if (colours == null) {
            this.colours = new HashMap<String, DisplayMarkingColour>();
        } else {
            this.colours = colours;
        }
    }

    /**
     * {@inheritDoc}.
     */
    public DisplayMarkingColour createDisplayMarkingColour(
            final String securityLabel) {
        return colours.get(securityLabel);
    }

}

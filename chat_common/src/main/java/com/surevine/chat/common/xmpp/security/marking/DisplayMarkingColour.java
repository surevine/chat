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
package com.surevine.chat.common.xmpp.security.marking;

/**
 * This class represents the background and foreground colour markings rendered for a security
 * label. The colours are represented by Hex characters and should be web accessible.
 */
public class DisplayMarkingColour {

    /**
     * Black.
     */
    private static final String BLACK = "#FFFFFF";

    /**
     * White.
     */
    private static final String WHITE = "#000000";

    /**
     * The background colour.
     */
    private String backgroundColour;

    /**
     * The foreground colour.
     */
    private String foregroundColour;

    /**
     * Default constructor that sets the background and foregound colours to white and black
     * respectively.
     */
    public DisplayMarkingColour() {
        this(WHITE, BLACK);
    }

    /**
     * Constructs a <code>DisplayMarkingColour</code> with background and foreground colours.
     *
     * @param backgroundColour
     *            The background colour.
     * @param foregroundColour
     *            The foreground colour.
     */
    public DisplayMarkingColour(final String backgroundColour, final String foregroundColour) {
        this.backgroundColour = backgroundColour;
        this.foregroundColour = foregroundColour;
    }

    /**
     * Gets the background colour.
     *
     * @return The background colour.
     */
    public String getBackgroundColour() {
        return backgroundColour;
    }

    /**
     * Gets the foreground colour.
     *
     * @return The foreground colour.
     */
    public String getForegroundColour() {
        return foregroundColour;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("DisplayMarkingColour=[foregroundColour=");
        buffer.append(foregroundColour);
        buffer.append(", backgroundColour=");
        buffer.append(backgroundColour);
        buffer.append("]");
        return super.toString();
    }

}

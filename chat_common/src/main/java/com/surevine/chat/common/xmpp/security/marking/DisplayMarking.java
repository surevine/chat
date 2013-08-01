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

import com.surevine.chat.common.xmpp.security.IDisplayMarking;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Represents a display marking for a security label. This is used to provide a simple textual
 * representation of a security label.
 */
public class DisplayMarking implements IDisplayMarking {

    /**
     * The display marking value.
     */
    private String marking;

    /**
     * The display marking colouring.
     */
    private DisplayMarkingColour colour;

    /**
     * Default constructor for GWT serialisation.
     */
    @SuppressWarnings("unused")
    private DisplayMarking() {
        // NO-OP
    }

    /**
     * Constructs a new <code>DisplayMarking</code> with a text label, and colours.
     *
     * @param marking
     *            The display marking text.
     * @param colour
     *            The display marking colour.
     */
    public DisplayMarking(final String marking, final DisplayMarkingColour colour) {
        this.marking = marking;
        if (colour == null) {
            this.colour = new DisplayMarkingColour();
        } else {
            this.colour = colour;
        }
    }

    /**
     * {@inheritDoc}.
     */
    public String getMarking() {
        return marking;
    }

    /**
     * {@inheritDoc}.
     */
    public String getBgColour() {
        return colour.getBackgroundColour();
    }

    /**
     * {@inheritDoc}.
     */
    public String getFgColour() {
        return colour.getForegroundColour();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + (marking == null ? 0 : marking.hashCode());
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
        DisplayMarking that = (DisplayMarking) other;
        return (this.marking == null ? that.marking == null : this.marking.equals(that.marking));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("DisplayMarking=[marking=");
        buffer.append(marking);
        buffer.append(", colour=");
        buffer.append(colour.toString());
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * {@inheritDoc}.
     */
    public void addToXmlElement(final IXmlElement parentElement) {
        IXmlElement element = parentElement
                .addChild(IXmppSecurityLabelExtension.XEP_0258_DISPLAY_MARKING);
        if (colour.getForegroundColour() != null) {
            element.setAttribute(IDisplayMarking.FG_COLOUR, colour.getForegroundColour());
        }
        if (colour.getBackgroundColour() != null) {
            element.setAttribute(IDisplayMarking.BG_COLOUR, colour.getBackgroundColour());
        }
        element.setText(marking);
    }

}

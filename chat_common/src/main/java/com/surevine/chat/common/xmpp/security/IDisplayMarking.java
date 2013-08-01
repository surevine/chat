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
package com.surevine.chat.common.xmpp.security;

import com.surevine.chat.common.xmpp.xml.IXmlable;

/**
 * This interface defines the Display Marking portion of the XEP-0258 security labels specification.
 * Classes representing Display Markings as defined by the XEP-0258 specification should implemement
 * this interface.
 */
public interface IDisplayMarking extends IXmlable {

    /**
     * The XML attribute for a foreground colour.
     */
    String FG_COLOUR = "fgcolor";

    /**
     * The XML attribute for a background colour.
     */
    String BG_COLOUR = "bgcolor";

    /**
     * Gets the background colour for the display marking.
     *
     * @return The background colour for the display marking.
     */
    String getBgColour();

    /**
     * Gets the display text for the display marking.
     *
     * @return The display text for the display marking.
     */
    String getMarking();

    /**
     * Gets the foreground colour for the display marking.
     *
     * @return The foreground colour for the display marking.
     */
    String getFgColour();

}

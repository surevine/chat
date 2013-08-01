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

/**
 * Used for any UI value which represents a colour.
 */
public interface HasColour {
    /**
     * Returns the colour value.
     * 
     * @return the css colour string
     */
    String getColour();

    /**
     * Sets the colour value.
     * 
     * @param colour
     *            the css colour string
     */
    void setColour(String colour);
}

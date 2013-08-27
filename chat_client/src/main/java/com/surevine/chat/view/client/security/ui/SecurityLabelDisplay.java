/*
 * Chat Client
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

package com.surevine.chat.view.client.security.ui;

import com.google.gwt.user.client.ui.HasText;
import com.surevine.chat.view.client.xmpp.security.HasColour;

/**
 * The Security Label Display interface.
 */
public interface SecurityLabelDisplay {

    /**
     * Element which displays a security label.
     * 
     * @return The display text.
     */
    HasText getDisplayText();

    /**
     * Get the foreground colour.
     * 
     * @return The foreground colour.
     */
    HasColour getForegroundColour();

    /**
     * Get the background colour.
     * 
     * @return The background colour.
     */
    HasColour getBackgroundColour();
}

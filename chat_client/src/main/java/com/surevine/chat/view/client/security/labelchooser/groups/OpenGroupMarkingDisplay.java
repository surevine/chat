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

package com.surevine.chat.view.client.security.labelchooser.groups;

import com.calclab.hablar.core.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Display of a security label group item.
 */
public interface OpenGroupMarkingDisplay extends Display {

    /**
     * Get the name.
     * 
     * @return The group marking name.
     */
    HasText getName();

    /**
     * Get whether the checkbox is checked.
     * 
     * @return <code>true</code> if selected, <code>false</code> if not.
     */
    HasValue<Boolean> getSelected();

    /**
     * Gets the change handler for the checkbox
     */
    HasClickHandlers getClickHandler();

    /**
     * Set the checkbox enabled/disabled.
     * 
     * @param enabled
     *            <code>true</code> to enable, <code>false</code> to disable.
     */
    void setSelectEnabled(boolean enabled);
}

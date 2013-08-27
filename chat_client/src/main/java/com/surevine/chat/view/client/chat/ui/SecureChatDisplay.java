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

package com.surevine.chat.view.client.chat.ui;

import com.calclab.hablar.chat.client.ui.ChatDisplay;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.surevine.chat.view.client.security.ui.SecurityLabelDisplay;

/**
 * The interface for secure chat display.
 */
public interface SecureChatDisplay extends ChatDisplay {

    /**
     * Get the security label.
     * 
     * @return The <code>SecurityLabelDisplay</code>.
     */
    SecurityLabelDisplay getSecurityLabel();

    /**
     * Set the label visible.
     * 
     * @param visible
     *            <code>true</code> to show, otherwise <code>false</code>
     */
    void setLabelVisible(boolean visible);

    /**
     * The change classification button.
     * 
     * @return The <code>HasClickHandlers</code>.
     */
    HasClickHandlers changeClassificationButton();

    /**
     * Set the change classification button visible.
     * 
     * @param visible
     *            <code>true</code> to show, otherwise <code>false</code>
     */
    void setChangeClassificationVisible(boolean visible);
}

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

package com.surevine.chat.view.client.chat.ui.open;

import com.calclab.hablar.core.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;

/**
 * Display interface to open a new one-to-one chat
 */
public interface OpenSecureChatDisplay extends Display {

    /**
     * Gets the title element
     */
    HasText getTitleLabel();

    /**
     * Returns the security label chooser
     * 
     * @return the security label chooser.
     */
    SecurityLabelChooserDisplay getSecurityLabelChooser();

    /**
     * The accept button
     * 
     * @return
     */
    HasClickHandlers getAccept();

    /**
     * The cancel button
     * 
     * @return
     */
    HasClickHandlers getCancel();

}

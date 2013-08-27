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

package com.surevine.chat.view.client.security.labelchooser;

import java.util.List;

import com.calclab.hablar.core.client.mvp.Display;
import com.google.gwt.user.client.ui.HasValue;
import com.surevine.chat.common.xmpp.security.label.Caveat;
import com.surevine.chat.common.xmpp.security.label.Prefix;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.label.SupplementalMarking;
import com.surevine.chat.view.client.security.labelchooser.groups.OpenGroupMarkingDisplay;
import com.surevine.chat.view.client.security.ui.SecurityLabelDisplay;
import com.surevine.chat.view.client.ui.ISecurityLabelPartList;

/**
 * The security label chooser display interface.
 */
public interface SecurityLabelChooserDisplay extends Display {

    /**
     * Get the selected ProtectiveMarking.
     * 
     * @return A HasValue with a ProtectiveMarking
     */
    HasValue<Prefix> getPrefix();

    /**
     * Get the selected Prefix.
     * 
     * @return A HasList with a Prefix.
     */
    ISecurityLabelPartList<Prefix> getPrefixList();

    /**
     * Get the selected ProtectiveMarking.
     * 
     * @return A HasValue with a ProtectiveMarking
     */
    HasValue<ProtectiveMarking> getProtectiveMarking();

    /**
     * Get the selected ProtectiveMarking.
     * 
     * @return A HasList with a ProtectiveMarking.
     */
    ISecurityLabelPartList<ProtectiveMarking> getProtectiveMarkingList();

    /**
     * Get the selected Caveat.
     * 
     * @return A HasValue with a Caveat.
     */
    HasValue<Caveat> getCaveat();

    /**
     * Get the selected Caveat.
     * 
     * @return A HasList with a Caveat.
     */
    ISecurityLabelPartList<Caveat> getCaveatList();

    /**
     * Adds a new {@link OpenGroupMarkingDisplay}.
     * 
     * @param openGroupMarkingDisplay
     *            add the {@link OpenGroupMarkingDisplay} to the list
     */
    void addOpenGroupMarking(OpenGroupMarkingDisplay openGroupMarkingDisplay);

    /**
     * Remove all the open groups.
     */
    void clearGroupsList();

    /**
     * Create a new {@link OpenGroupMarkingDisplay}.
     * 
     * @return the new {@link OpenGroupMarkingDisplay}
     */
    OpenGroupMarkingDisplay createOpenGroupMarking();

    /**
     * Sets whether the caveats are visible.
     * 
     * @param visible
     *            <code>true</code> to display the caveats.
     */
    void setCaveatsVisible(boolean visible);

    /**
     * Sets whether the open groups are visible.
     * 
     * @param visible
     *            <code>true</code> to display the open groups.
     */
    void setOpenGroupsVisible(boolean visible);

    /**
     * Gets the view for the security label preview.
     * 
     * @return the {@link SecurityLabelDisplay}
     */
    SecurityLabelDisplay getSecurityLabelDisplay();

    /**
     * Reset the view back to a known starting state.<br />
     * <ul>
     * <li>First {@link ProtectiveMarking} selected</li>
     * <li>First {@link Caveat} selected</li>
     * <li>No {@link SupplementalMarking} selected</li>
     * </ul>
     */
    void reset();

    /**
     * Returns a list of all the open group marking displays.
     * 
     * @return the open group marking display list.
     */
    List<OpenGroupMarkingDisplay> getOpenGroupMarkings();
}

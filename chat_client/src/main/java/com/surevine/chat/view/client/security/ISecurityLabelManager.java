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

package com.surevine.chat.view.client.security;

import java.util.List;
import java.util.Map;

import com.surevine.chat.common.xmpp.security.label.Caveat;
import com.surevine.chat.common.xmpp.security.label.OpenGroupMarking;
import com.surevine.chat.common.xmpp.security.label.Prefix;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;

/**
 * The <code>SecurityLabelManager</code> class manages the application security
 * label structure.
 */
public interface ISecurityLabelManager {

    /**
     * Gets the protective markings to render on the UI.
     * 
     * @return A list of protective markings for the UI.
     */
    public List<Prefix> getPrefixes();

    /**
     * Gets the protective markings to render on the UI.
     * 
     * @return A list of protective markings for the UI.
     */
    public List<ProtectiveMarking> getProtectiveMarkings();

    /**
     * Gets the caveats to render on the UI.
     * 
     * @return A list of caveats for the UI.
     */
    public List<Caveat> getCaveats();

    /**
     * Gets the open group markings to render on the UI.
     * 
     * @return A list of open group markings for the UI.
     */
    public List<OpenGroupMarking> getOpenGroupMarkings();

    /**
     * Gets the open group marking suffix for the UI.
     * 
     * @return The open group marking suffix for the UI.
     */
    public String getOpenGroupsSuffix();

    /**
     * Creates a map of security labels and their display marking colour
     * mappings for use in the UI.
     * 
     * @return A map of security label to display marking colour mappings.
     */
    public Map<String, DisplayMarkingColour> getDisplayLabelMappings();

    /**
     * Determines whether the protective marking is sufficient enough to display
     * caveats for the UI.
     * 
     * @param protectiveMarking
     *            The protective marking to check.
     * @return <code>true</code> if the caveats should be shown, otherwise
     *         <code>false</code>.
     */
    public boolean showCaveats(final ProtectiveMarking protectiveMarking);

    /**
     * Determines whether the protective marking is sufficient enough to display
     * open groups for the UI.
     * 
     * @param protectiveMarking
     *            The protective marking to check.
     * @return <code>true</code> if the open groups should be shown, otherwise
     *         <code>false</code>.
     */
    public boolean showOpenGroups(final ProtectiveMarking protectiveMarking);

    /**
     * Determines whether the open group marking should be mandatorily selected.
     * 
     * @param openGroupMarking
     *            The open group marking.
     * @return <code>true</code> if the open group should be selected, otherwise
     *         <code>false</code>.
     */
    public boolean isDefaultOpenGroup(final OpenGroupMarking openGroupMarking);

    /**
     * Determines whether no open groups will be allowed. This will mean that no
     * open groups can be selected, but if any are selected then all the default
     * open groups have to be selected.
     * 
     * @return <code>true</code> if it's valid to have no open groups, otherwise
     *         <code>false</code>
     */
    public boolean allowNoOpenGroup();

    /**
     * Gets the text which will be displayed for the default no caveat option.
     * Return null to force a caveat.
     * 
     * @return the text to display.
     */
    public String getNoCaveatText();

    /**
     * Determines whether the protective marking is low enough to allow no
     * prefix.
     * 
     * @param protectiveMarking
     *            The protective marking to check.
     * @return <code>true</code> if no prefix should be allowed, otherwise
     *         <code>false</code>.
     */
    public boolean allowNoPrefix(final ProtectiveMarking protectiveMarking);

    /**
     * Gets the list of caveats which are available for a certain protective
     * marking
     * 
     * @param protectiveMarking
     *            the protective marking.
     * @return the list of available caveats
     */
    List<Caveat> getCaveatsForProtectiveMarking(
            final ProtectiveMarking protectiveMarking);

    /**
     * Gets the text which will be displayed for the default no prefix option.
     * Return null to force a prefix.
     * 
     * @return the text to display.
     */
    String getNoPrefixText();
}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.surevine.chat.common.xmpp.security.label.Caveat;
import com.surevine.chat.common.xmpp.security.label.OpenGroupMarking;
import com.surevine.chat.common.xmpp.security.label.Prefix;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;

/**
 * The <code>SecurityLabelManager</code> class manages the application security
 * label structure.
 */
public class SecurityLabelManager implements ISecurityLabelManager {

    /**
     * The security label constants.
     */
    private SecurityConstants securityConstants;

    /**
     * Constructs a default <code>SecurityLabelManager</code>.
     */
    public SecurityLabelManager() {
        // Set the default GWT created constants
        this(null);
    }

    /**
     * Constructs a <code>SecurityLabelManager</code> with a
     * {@link SecurityConstants}.
     * 
     * @param securityConstants
     *            The constants containing the security label values.
     */
    public SecurityLabelManager(final SecurityConstants securityConstants) {
        if (securityConstants != null) {
            this.securityConstants = securityConstants;
        } else {
            this.securityConstants = (SecurityConstants) GWT
                    .create(SecurityConstants.class);
        }
    }

    /**
     * Gets the prefixes to render on the UI.
     * 
     * @return A list of prefixes for the UI.
     */
    public List<Prefix> getPrefixes() {
        final List<Prefix> prefixes = new ArrayList<Prefix>();
        final String[] allPrefixes = securityConstants.getPrefixes();
        for (final String prefix : allPrefixes) {
            prefixes.add(new Prefix(prefix));
        }
        return prefixes;
    }

    /**
     * Gets the protective markings to render on the UI.
     * 
     * @return A list of protective markings for the UI.
     */
    public List<ProtectiveMarking> getProtectiveMarkings() {
        final List<ProtectiveMarking> protectiveMarkings = new ArrayList<ProtectiveMarking>();
        final String[] allMarkings = securityConstants.getProtectiveMarkings();
        for (final String marking : allMarkings) {
            protectiveMarkings.add(new ProtectiveMarking(marking));
        }
        return protectiveMarkings;
    }

    /**
     * Gets the caveats to render on the UI.
     * 
     * @return A list of caveats for the UI.
     */
    public List<Caveat> getCaveats() {
        final List<Caveat> caveats = new ArrayList<Caveat>();
        final String[] allCaveats = securityConstants.getCaveats();
        for (final String caveat : allCaveats) {
            caveats.add(new Caveat(caveat));
        }
        return caveats;
    }

    /**
     * Gets the open group markings to render on the UI.
     * 
     * @return A list of open group markings for the UI.
     */
    public List<OpenGroupMarking> getOpenGroupMarkings() {
        final List<OpenGroupMarking> openGroupMarkings = new ArrayList<OpenGroupMarking>();
        final String[] allGroups = securityConstants.getOpenGroups();
        for (final String group : allGroups) {
            openGroupMarkings.add(new OpenGroupMarking(group));
        }
        return openGroupMarkings;
    }

    /**
     * Gets the open group marking suffix for the UI.
     * 
     * @return The open group marking suffix for the UI.
     */
    public String getOpenGroupsSuffix() {
        return securityConstants.getOpenGroupsSuffix();
    }

    /**
     * Creates a map of security labels and their display marking colour
     * mappings for use in the UI.
     * 
     * @return A map of security label to display marking colour mappings.
     */
    public Map<String, DisplayMarkingColour> getDisplayLabelMappings() {
        final Map<String, DisplayMarkingColour> displayMarkingColourMap = new HashMap<String, DisplayMarkingColour>();
        final String[] fgColours = securityConstants
                .getDisplayMarkingFGColours();
        final String[] bgColours = securityConstants
                .getDisplayMarkingBGColours();
        final String[] protectiveMarkings = securityConstants
                .getProtectiveMarkings();
        for (int count = 0; count < protectiveMarkings.length; count++) {
            displayMarkingColourMap
                    .put(protectiveMarkings[count], new DisplayMarkingColour(
                            bgColours[count], fgColours[count]));
        }
        return displayMarkingColourMap;
    }

    /**
     * Determines whether the protective marking is sufficient enough to display
     * caveats for the UI.
     * 
     * @param protectiveMarking
     *            The protective marking to check.
     * @return <code>true</code> if the caveats should be shown, otherwise
     *         <code>false</code>.
     */
    public boolean showCaveats(final ProtectiveMarking protectiveMarking) {
        final String[] protectiveMarkings = securityConstants
                .getProtectiveMarkings();
        final int index3 = 3;
        final int index4 = 4;
        final int index5 = 5;
        return ((protectiveMarkings[index3].equals(protectiveMarking.getName()))
                || (protectiveMarkings[index4].equals(protectiveMarking
                        .getName())) || (protectiveMarkings[index5]
                .equals(protectiveMarking.getName())));
    }

    /**
     * {@inheritDoc}
     */
    public boolean showOpenGroups(final ProtectiveMarking protectiveMarking) {
        final String[] protectiveMarkings = securityConstants
                .getProtectiveMarkings();
        final int index0 = 0;
        // Return true if we're anything other than index 0
        return (!protectiveMarkings[index0].equals(protectiveMarking.getName()));
    }

    /**
     * Returns the list of caveats available for a certain protective marking
     * 
     * @param protectiveMarking
     *            The protective marking to check.
     * @return the array of Caveats.
     */
    public List<Caveat> getCaveatsForProtectiveMarking(
            final ProtectiveMarking protectiveMarking) {
        final String[] protectiveMarkings = securityConstants
                .getProtectiveMarkings();
        final int index3 = 3;
        final int index4 = 4;
        final int index5 = 5;

        if (protectiveMarkings[index3].equals(protectiveMarking.getName())) {
            final String[] caveatArray = securityConstants.getCaveats();

            final List<Caveat> caveats = new ArrayList<Caveat>();

            // We will only allow the first caveat
            caveats.add(new Caveat(caveatArray[0]));

            return caveats;
        }

        if ((protectiveMarkings[index4].equals(protectiveMarking.getName()))
                || (protectiveMarkings[index5].equals(protectiveMarking
                        .getName()))) {
            return getCaveats();
        }

        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    public boolean allowNoPrefix(final ProtectiveMarking protectiveMarking) {
        final String[] protectiveMarkings = securityConstants
                .getProtectiveMarkings();
        final int index0 = 0;
        return (protectiveMarkings[index0].equals(protectiveMarking.getName()));
    }

    /**
     * Determines whether the open group marking should be mandatorily selected.
     * 
     * @param openGroupMarking
     *            The open group marking.
     * @return <code>true</code> if the open group should be selected, otherwise
     *         <code>false</code>.
     */
    public boolean isDefaultOpenGroup(final OpenGroupMarking openGroupMarking) {
        final String[] openGroups = securityConstants.getOpenGroups();
        final int index0 = 0;
        return (openGroups[index0].equals(openGroupMarking.getName()));
    }

    /**
     * Determines whether no open groups will be allowed. This will mean that no
     * open groups can be selected, but if any are selected then all the default
     * open groups have to be selected.
     * 
     * @return <code>true</code> if it's valid to have no open groups, otherwise
     *         <code>false</code>
     */
    public boolean allowNoOpenGroup() {
        return true;
    }

    /**
     * Gets the text which will be displayed for the default no caveat option.
     * Return null to force a caveat.
     * 
     * @return the text to display.
     */
    public String getNoCaveatText() {
        final String text = securityConstants.getNoCaveatText();

        if (text.length() == 0) {
            return null;
        }

        return text;
    }

    /**
     * Gets the text which will be displayed for the default no prefix option.
     * Return null to force a prefix.
     * 
     * @return the text to display.
     */
    public String getNoPrefixText() {
        final String text = securityConstants.getNoPrefixText();

        if (text.length() == 0) {
            return null;
        }

        return text;
    }
}

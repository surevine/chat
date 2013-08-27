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

import com.google.gwt.i18n.client.Constants;

/**
 * This class backs a properties file containing the mappings of security labels
 * to their display marking colours.
 */
public interface SecurityConstants extends Constants {

    /**
     * Gets the available prefixes.
     * 
     * @return The available prefixes.
     */
    @Key("sec.label.prefixes")
    String[] getPrefixes();

    /**
     * Gets the available protective markings.
     * 
     * @return The available protective markings.
     */
    @Key("sec.label.protective.markings")
    String[] getProtectiveMarkings();

    /**
     * Gets the available caveats.
     * 
     * @return The available caveats.
     */
    @Key("sec.label.caveats")
    String[] getCaveats();

    /**
     * Gets the available open groups.
     * 
     * @return The available open groups.
     */
    @Key("sec.label.open.groups")
    String[] getOpenGroups();

    /**
     * Gets the open group suffix.
     * 
     * @return The open group suffix.
     */
    @Key("sec.label.open.groups.suffix")
    String getOpenGroupsSuffix();

    /**
     * Gets the display marking foreground colours.
     * 
     * @return The display marking foreground colours.
     */
    @Key("sec.label.protective.markings.fgcolours")
    String[] getDisplayMarkingFGColours();

    /**
     * Gets the display marking background colours.
     * 
     * @return The display marking background colours.
     */
    @Key("sec.label.protective.markings.bgcolours")
    String[] getDisplayMarkingBGColours();

    /**
     * Gets the "No Prefix" text.
     * 
     * @return The "No Prefix" text.
     */
    @Key("sec.label.prefixes.noprefixtext")
    String getNoPrefixText();

    /**
     * Gets the "No Caveat" text.
     * 
     * @return The "No Caveat" text.
     */
    @Key("sec.label.caveats.nocaveattext")
    String getNoCaveatText();
}

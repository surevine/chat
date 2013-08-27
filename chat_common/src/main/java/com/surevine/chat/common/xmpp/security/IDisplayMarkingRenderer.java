/*
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

package com.surevine.chat.common.xmpp.security;

import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;

/**
 * This interface defines the contract for classes implementing security label rendering services.
 * In particular this interface is for rendering the display marking components of a security label
 * so that their respective foreground and background colours are correctly set for the underlying
 * security label.
 */
public interface IDisplayMarkingRenderer {

    /**
     * Creates a {@link DisplayMarkingColour} that corresponds to the specified security label.
     *
     * @param securityLabel
     *            The security label.
     * @return A <code>DisplayMarkingColour</code> for the specified security label if a mapping is
     *         found, otherwise <code>null</code>.
     */
    DisplayMarkingColour createDisplayMarkingColour(String securityLabel);

}

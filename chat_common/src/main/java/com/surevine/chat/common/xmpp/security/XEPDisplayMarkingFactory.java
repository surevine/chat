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

import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.label.SecurityLabel;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarking;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This factory class is responsible for creating {@link DisplayMarking} objects.
 */
public class XEPDisplayMarkingFactory {

    /**
     * Creates a {@link DisplayMarking} object for the corresponding security label.
     *
     * @param securityLabel
     *            The security label.
     * @param renderer
     *            The display marking renderer.
     * @return The display marking for the corresponding security label.
     */
    public IDisplayMarking createDisplayMarking(final ISecurityLabel securityLabel,
            final IDisplayMarkingRenderer renderer) {
        if (securityLabel == null) {
            throw new IllegalArgumentException("Unexpected null security label");
        }
        if (renderer == null) {
            throw new IllegalArgumentException("Unexpected null renderer");
        }
        
        DisplayMarkingColour colour = null;
        
        if(securityLabel instanceof SecurityLabel) {
        	SecurityLabel label = (SecurityLabel) securityLabel;
        	ProtectiveMarking protectiveMarking = label.getProtectiveMarking();
        	
        	if(protectiveMarking != null) {
        		colour = renderer.createDisplayMarkingColour(protectiveMarking.getName());
        	}
        }
        
        // If we don't know what the security label is then create a default display marking
        if (colour == null) {
            return createDefaultDisplayMarking(securityLabel);
        }

        return new DisplayMarking(securityLabel.getLabel(), colour);
    }

    /**
     * Creates a default display marking for an unknown security label.
     *
     * @param securityLabel
     *            The security label.
     * @return The default display marking.
     */
    private IDisplayMarking createDefaultDisplayMarking(final ISecurityLabel securityLabel) {
        return new DisplayMarking(securityLabel.getLabel(), null);
    }

    /**
     * Creates a new {@link DisplayMarking} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The display marking for the XML element.
     */
    public IDisplayMarking createDisplayMarking(final IXmlElement xmlElement) {
        String fgColour = xmlElement.getAttribute(IDisplayMarking.FG_COLOUR);
        String bgColour = xmlElement.getAttribute(IDisplayMarking.BG_COLOUR);
        String displayText = xmlElement.getText();
        return new DisplayMarking(displayText, new DisplayMarkingColour(bgColour, fgColour));
    }

}

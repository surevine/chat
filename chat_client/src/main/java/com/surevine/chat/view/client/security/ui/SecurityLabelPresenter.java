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

import com.surevine.chat.common.xmpp.security.IDisplayMarking;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.label.OpenGroupMarking;
import com.surevine.chat.common.xmpp.security.label.SecurityLabel;
import com.surevine.chat.common.xmpp.security.label.SupplementalMarking;
import com.surevine.chat.view.client.security.SecurityLabelManager;

/**
 * Delivers security labels to the presentation layer.
 */
public class SecurityLabelPresenter {

    /**
     * The security display marking.
     */
    private final SecurityLabelDisplay display;

    /**
     * Constructs a <code>SecurityLabelPresenter</code> with a display label and
     * a security label.
     * 
     * @param display
     *            The <code>SecurityLabelDisplay</code>.
     * @param xmppSecurityLabel
     *            The <code>IXmppSecurityLabelExtension</code>.
     */
    public SecurityLabelPresenter(final SecurityLabelDisplay display,
            final IXmppSecurityLabelExtension xmppSecurityLabel) {
        this.display = display;
        setXmppSecurityLabel(xmppSecurityLabel);
    }

    /**
     * Sets the XMPP security label.
     * 
     * @param xmppSecurityLabel
     *            The security label to set.
     */
    public void setXmppSecurityLabel(
            final IXmppSecurityLabelExtension xmppSecurityLabel) {
        if (xmppSecurityLabel != null) {
            final IDisplayMarking displayMarking = xmppSecurityLabel
                    .getDisplayMarking();
            final SecurityLabelManager securityLabelManager = new SecurityLabelManager();

            String displayMarkingText = displayMarking.getMarking();

            if (xmppSecurityLabel.getLabel() instanceof SecurityLabel) {
                final SecurityLabel label = (SecurityLabel) xmppSecurityLabel
                        .getLabel();

                for (final SupplementalMarking marking : label
                        .getSupplementalMarkings()) {
                    // If we have an open group set then we will display the
                    // suffix
                    if (marking instanceof OpenGroupMarking) {
                        displayMarkingText += " "
                                + securityLabelManager.getOpenGroupsSuffix();
                        break;
                    }

                }
            }

            display.getDisplayText().setText(displayMarkingText);
            display.getBackgroundColour().setColour(
                    displayMarking.getBgColour());
            display.getForegroundColour().setColour(
                    displayMarking.getFgColour());
        } else {
            display.getDisplayText().setText("Invalid Marking");
            display.getBackgroundColour().setColour(null);
            display.getForegroundColour().setColour(null);
        }
    }
}

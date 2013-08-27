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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;

/**
 * A widget to open a new secure chat
 */
public class OpenSecureChatWidget extends Composite implements
        OpenSecureChatDisplay {
    /**
     * 
     */
    interface OpenSecureChatWidgetUiBinder extends
            UiBinder<Widget, OpenSecureChatWidget> {
    }

    /**
     * 
     */
    private static OpenSecureChatWidgetUiBinder uiBinder = GWT
            .create(OpenSecureChatWidgetUiBinder.class);

    @UiField
    Label title;

    @UiField
    SecurityLabelChooserDisplay securityLabelChooserWidget;

    @UiField
    Button accept;

    @UiField
    Button cancel;

    /**
     * Creates a new widget
     */
    public OpenSecureChatWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * {@inheritDoc}
     */
    public HasClickHandlers getAccept() {
        return accept;
    }

    /**
     * {@inheritDoc}
     */
    public HasClickHandlers getCancel() {
        return cancel;
    }

    /**
     * {@inheritDoc}
     */
    public SecurityLabelChooserDisplay getSecurityLabelChooser() {
        return securityLabelChooserWidget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public HasText getTitleLabel() {
        return title;
    }

}

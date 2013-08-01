/*
 * Chat Client
 * Copyright (C) 2010 Surevine Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */
package com.surevine.chat.view.client.security.labelchooser.groups;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The View of the MVP (Model-View-Presenter) pattern of the security label
 * group.
 */
public class OpenGroupMarkingWidget extends Composite implements
        OpenGroupMarkingDisplay {

    /**
     * The interface for the <code>OpenGroupMarkingWidgetUiBinder</code>.
     */
    interface OpenGroupMarkingWidgetUiBinder extends
            UiBinder<Widget, OpenGroupMarkingWidget> {
    }

    /**
     * The <code>OpenGroupMarkingWidgetUiBinder</code>.
     */
    private static OpenGroupMarkingWidgetUiBinder uiBinder = GWT
            .create(OpenGroupMarkingWidgetUiBinder.class);

    /**
     *
     */
    @UiField
    Label name;

    /**
     *
     */
    @UiField
    CheckBox select;

    /**
     *
     */
    public OpenGroupMarkingWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * {@inheritDoc}.
     */
    public HasText getName() {
        return name;
    }

    /**
     * {@inheritDoc}.
     */
    public HasValue<Boolean> getSelected() {
        return select;
    }

    /**
     * {@inheritDoc}.
     */
    public void setSelectEnabled(final boolean enabled) {
        select.setEnabled(enabled);

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Widget asWidget() {
        return this;
    }

    /**
     * {@inheritDoc}.
     */
    public HasClickHandlers getClickHandler() {
        return select;
    }
}

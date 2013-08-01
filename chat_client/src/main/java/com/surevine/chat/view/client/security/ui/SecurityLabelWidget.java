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
package com.surevine.chat.view.client.security.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.view.client.xmpp.security.HasColour;

/**
 *
 */
public class SecurityLabelWidget extends Composite implements
        SecurityLabelDisplay {

    /**
     * The SecurityLabelWidgetUiBinder interface.
     */
    interface SecurityLabelWidgetUiBinder extends
            UiBinder<Widget, SecurityLabelWidget> {
    }

    /**
     * The uiBinder.
     */
    private static SecurityLabelWidgetUiBinder uiBinder = GWT
            .create(SecurityLabelWidgetUiBinder.class);

    /**
     * The label UiField.
     */
    @UiField
    DivElement label;

    /**
     * The foreground colour of the display marking.
     */
    private final HasColour fgColour = new HasColour() {
        public String getColour() {
            return label.getStyle().getColor();
        }

        /**
         * {@inheritDoc}.
         */
        public void setColour(final String colour) {
            label.getStyle().setColor(colour);
        }
    };

    /**
     * The background colour of the display marking.
     */
    private final HasColour bgColour = new HasColour() {
        public String getColour() {
            return label.getStyle().getBackgroundColor();
        }

        /**
         * {@inheritDoc}.
         */
        public void setColour(final String colour) {
            label.getStyle().setBackgroundColor(colour);
        }
    };

    /**
     * Construct a new security label widget with a certain text, background and
     * foreground colours.
     */
    public SecurityLabelWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * Gets the background colour display element.
     * 
     * * @return The Background Colour.
     */
    public HasColour getBackgroundColour() {
        return bgColour;
    }

    /**
     * Gets the display text element.
     * 
     * @return The display text.
     */
    public HasText getDisplayText() {
        return new HasText() {

            public String getText() {
                return label.getInnerText();
            }

            public void setText(final String text) {
                label.setInnerText(text);
            }

        };
    }

    /**
     * Gets the foreground colour display element.
     * 
     * @return The Foreground Colour.
     */
    public HasColour getForegroundColour() {
        return fgColour;
    }
}

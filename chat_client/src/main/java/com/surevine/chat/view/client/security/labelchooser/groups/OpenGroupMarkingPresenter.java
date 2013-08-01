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

import com.surevine.chat.common.xmpp.security.label.OpenGroupMarking;

/**
 * The presenter for a security label group marking.
 */
public class OpenGroupMarkingPresenter {

    /**
     * The <code>OpenGroupMarking</code>.
     */
    private final OpenGroupMarking openGroup;

    /**
     * The <code>OpenGroupMarkingDisplay</code>.
     */
    private final OpenGroupMarkingDisplay display;

    /**
     * Constructor.
     * 
     * @param openGroup
     *            The <code>OpenGroupMarking</code>.
     * @param display
     *            The <code>OpenGroupMarkingDisplay</code>.
     * @param selectable
     *            Make the checkbox selectable, <code>true</code> to enable,
     *            <code>false</code> to disable.
     */
    public OpenGroupMarkingPresenter(final OpenGroupMarking openGroup,
            final OpenGroupMarkingDisplay display, final boolean selectable) {

        this.openGroup = openGroup;
        this.display = display;

        display.getName().setText(openGroup.getName());
        display.getSelected().setValue(!selectable);
        display.setSelectEnabled(selectable);
    }

    /**
     * 
     * @return
     */
    public OpenGroupMarking getItem() {
        return openGroup;
    }

    /**
     * 
     * @return
     */
    public boolean isSelected() {
        return display.getSelected().getValue();
    }

    /**
     * 
     * @param enabled
     */
    public void setEnabled(final boolean enabled) {
        display.setSelectEnabled(enabled);
    }

    /**
     * 
     * @param selected
     */
    public void setSelected(final boolean selected) {
        display.getSelected().setValue(selected);
    }

}

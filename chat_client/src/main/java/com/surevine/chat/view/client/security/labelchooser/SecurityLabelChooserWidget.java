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

package com.surevine.chat.view.client.security.labelchooser;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.common.xmpp.security.label.Caveat;
import com.surevine.chat.common.xmpp.security.label.Prefix;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.view.client.security.labelchooser.groups.OpenGroupMarkingDisplay;
import com.surevine.chat.view.client.security.labelchooser.groups.OpenGroupMarkingWidget;
import com.surevine.chat.view.client.security.ui.SecurityLabelDisplay;
import com.surevine.chat.view.client.security.ui.SecurityLabelWidget;
import com.surevine.chat.view.client.ui.ISecurityLabelPartList;

/**
 * The View of the MVP (Model-View-Presenter) pattern of the security label
 * chooser.
 */
public class SecurityLabelChooserWidget extends Composite implements
        SecurityLabelChooserDisplay {

    /**
     * An interface for the widget uiBinder.
     */
    interface SecurityLabelChooserWidgetUiBinder extends
            UiBinder<Widget, SecurityLabelChooserWidget> {
    }

    /**
     * The uiBinder.
     */
    private static SecurityLabelChooserWidgetUiBinder uiBinder = GWT
            .create(SecurityLabelChooserWidgetUiBinder.class);

    /**
     * The <code>PrefixListBox</code> UiField.
     */
    @UiField(provided = true)
    SecurityLabelPartListBox<Prefix> prefixListBox = new SecurityLabelPartListBox<Prefix>();

    /**
     * The <code>ProtectiveMarkingListBox</code> UiField.
     */
    @UiField(provided = true)
    SecurityLabelPartListBox<ProtectiveMarking> protectiveMarkingListBox = new SecurityLabelPartListBox<ProtectiveMarking>();;

    /**
     * The <code>ProtectiveMarkingListBox</code> UiField.
     */
    @UiField(provided = true)
    SecurityLabelPartListBox<Caveat> caveatListBox = new SecurityLabelPartListBox<Caveat>();;

    /**
     * The groups list <code>FlowPanel</code>.
     */
    @UiField
    FlowPanel groupsList;

    /**
     * The security label widget to display the preview.
     */
    @UiField
    SecurityLabelWidget securityLabelWidget;

    /**
     * The paragraph element which contains the caveats.
     */
    @UiField
    ParagraphElement caveatsContainer;

    /**
     * The paragraph element which contains the open groups.
     */
    @UiField
    ParagraphElement openGroupsContainer;

    /**
     * Constructor.
     */
    public SecurityLabelChooserWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        prefixListBox.ensureDebugId("SecurityLabelChooserWidget-prefixListBox");
        protectiveMarkingListBox
                .ensureDebugId("SecurityLabelChooserWidget-protectiveMarkingListBox");
        caveatListBox.ensureDebugId("SecurityLabelChooserWidget-caveatListBox");
        groupsList.ensureDebugId("SecurityLabelChooserWidget-groupsList");
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
    public void addOpenGroupMarking(
            final OpenGroupMarkingDisplay openGroupMarkingDisplay) {
        groupsList.add(openGroupMarkingDisplay.asWidget());

    }

    /**
     * {@inheritDoc}.
     */
    public void clearGroupsList() {
        groupsList.clear();

    }

    /**
     * {@inheritDoc}.
     */
    public OpenGroupMarkingDisplay createOpenGroupMarking() {
        return new OpenGroupMarkingWidget();
    }

    /**
     * {@inheritDoc}.
     */
    public void setCaveatsVisible(final boolean visible) {
        if (visible) {
            caveatsContainer.getStyle().setDisplay(Display.BLOCK);
        } else {
            caveatsContainer.getStyle().setDisplay(Display.NONE);
        }
    }

    /**
     * {@inheritDoc}.
     */
    public SecurityLabelDisplay getSecurityLabelDisplay() {
        return securityLabelWidget;
    }

    /**
     * {@inheritDoc}.
     */
    public HasValue<Caveat> getCaveat() {
        return caveatListBox;
    }

    /**
     * {@inheritDoc}.
     */
    public HasValue<ProtectiveMarking> getProtectiveMarking() {
        return protectiveMarkingListBox;
    }

    /**
     * {@inheritDoc}.
     */
    public void reset() {
        protectiveMarkingListBox.setSelectedIndex(0);
        caveatListBox.setSelectedIndex(0);
    }

    /**
     * {@inheritDoc}.
     */
    public List<OpenGroupMarkingDisplay> getOpenGroupMarkings() {
        final ArrayList<OpenGroupMarkingDisplay> result = new ArrayList<OpenGroupMarkingDisplay>();

        final int widgetCount = groupsList.getWidgetCount();

        Widget widget;

        for (int i = 0; i < widgetCount; ++i) {
            widget = groupsList.getWidget(i);

            if (widget instanceof OpenGroupMarkingDisplay) {
                result.add((OpenGroupMarkingDisplay) widget);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public HasValue<Prefix> getPrefix() {
        return prefixListBox;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setOpenGroupsVisible(final boolean visible) {
        if (visible) {
            openGroupsContainer.getStyle().setDisplay(Display.BLOCK);
        } else {
            openGroupsContainer.getStyle().setDisplay(Display.NONE);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ISecurityLabelPartList<Caveat> getCaveatList() {
        return caveatListBox;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ISecurityLabelPartList<Prefix> getPrefixList() {
        return prefixListBox;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ISecurityLabelPartList<ProtectiveMarking> getProtectiveMarkingList() {
        return protectiveMarkingListBox;
    }

}

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

package com.surevine.chat.view.client.rooms.ui.open;

import java.util.Collection;
import java.util.List;

import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.hablar.core.client.ui.selectionlist.DoubleList;
import com.calclab.hablar.core.client.ui.selectionlist.Selectable;
import com.calclab.hablar.core.client.validators.HasState;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.rooms.client.open.SelectRosterItemDisplay;
import com.calclab.hablar.rooms.client.open.SelectRosterItemWidget;
import com.calclab.hablar.roster.client.selection.DoubleListRosterItemSelector;
import com.calclab.hablar.roster.client.selection.RosterItemSelector;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserWidget;

/**
 * 
 */
public class EditSecureRoomWidget extends Composite implements
        EditSecureRoomDisplay {

    /**
     * 
     */
    interface InviteToSecureRoomWidgetUiBinder extends
            UiBinder<Widget, EditSecureRoomWidget> {
    }

    /**
     * 
     */
    private static InviteToSecureRoomWidgetUiBinder uiBinder = GWT
            .create(InviteToSecureRoomWidgetUiBinder.class);

    /**
     * The security label chooser
     */
    @UiField
    SecurityLabelChooserWidget securityLabelChooserWidget;

    /**
     * The element which contains the security label chooser.
     */
    @UiField
    DivElement securityLabelChooserContainer;

    @UiField
    Button accept, cancel;
    @UiField
    DoubleList selectionList;
    @UiField
    TextBox message, roomName;
    @UiField
    Label roomNameError;

    @UiField
    SpanElement title;

    private final RosterItemSelector selector;

    /**
     * 
     */
    public EditSecureRoomWidget(final AvatarProviderRegistry registry) {
        initWidget(uiBinder.createAndBindUi(this));
        roomName.ensureDebugId("InviteToRoomWidget-roomName");
        message.ensureDebugId("InviteToRoomWidget-message");
        accept.ensureDebugId("InviteToRoomWidget-invite");
        cancel.ensureDebugId("InviteToRoomWidget-cancel");
        selectionList.ensureDebugId("InviteToRoomWidget-list");
        selector = new DoubleListRosterItemSelector(selectionList, registry);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void clearList() {
        clearSelectionList();
    }

    @Override
    public SelectRosterItemDisplay createItem() {
        return new SelectRosterItemWidget();
    }

    @Override
    public HasState<Boolean> getAcceptEnabled() {
        return new HasState<Boolean>() {
            @Override
            public void setState(final Boolean state) {
                accept.setEnabled(state);
            }
        };
    }

    @Override
    public HasClickHandlers getCancel() {
        return cancel;
    }

    @Override
    public HasClickHandlers getInvite() {
        return accept;
    }

    @Override
    public HasText getMessage() {
        return message;
    }

    @Override
    public HasValue<String> getRoomName() {
        return roomName;
    }

    @Override
    public HasValue<List<Selectable>> getSelectionList() {
        return selectionList;
    }

    @Override
    public HasText getRoomNameError() {
        return roomNameError;
    }

    @Override
    public HasKeyDownHandlers getRoomNameKeys() {
        return roomName;
    }

    @Override
    public void setAcceptText(final String text) {
        accept.setText(text);
    }

    @Override
    public void setPageTitle(final String text) {
        title.setInnerText(text);
    }

    @Override
    public void setRoomNameEnabled(final boolean enabled) {
        roomName.setEnabled(enabled);
    }

    @Override
    public void addRosterItem(final RosterItem rosterItem) {
        selector.addRosterItem(rosterItem);
    }

    @Override
    public void addSelectedRosterItem(final RosterItem rosterItem) {
        selector.addSelectedRosterItem(rosterItem);
    }

    @Override
    public void clearSelectionList() {
        selector.clearSelectionList();
    }

    @Override
    public Collection<RosterItem> getSelectedItems() {
        return selector.getSelectedItems();
    }

    /**
     * 
     * @param visible
     */
    public void setSecurityLabelChooserVisible(final boolean visible) {
        if (visible) {
            securityLabelChooserContainer.getStyle().setDisplay(Display.BLOCK);
        } else {
            securityLabelChooserContainer.getStyle().setDisplay(Display.NONE);
        }
    }

    /**
     * {@inheritDoc}.
     */
    public SecurityLabelChooserDisplay getSecurityLabelChooser() {
        return securityLabelChooserWidget;
    }
}

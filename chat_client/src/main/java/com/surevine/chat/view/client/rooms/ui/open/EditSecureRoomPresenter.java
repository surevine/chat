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
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.PagePresenter;
import com.calclab.hablar.core.client.ui.selectionlist.Selectable;
import com.calclab.hablar.core.client.validators.CompositeValidatorChecker;
import com.calclab.hablar.core.client.validators.ListNotEmptyValidator;
import com.calclab.hablar.core.client.validators.Validators;
import com.calclab.hablar.rooms.client.RoomMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserPresenter;

/**
 * Presenter for the widget used to edit an existing group chat/room.
 */
public abstract class EditSecureRoomPresenter extends
        PagePresenter<EditSecureRoomDisplay> {

    /**
     * The validator used for the room name
     */
    protected CompositeValidatorChecker roomNameValidator;

    /**
     * The presenter logic to attach to the security label chooser
     */
    protected SecurityLabelChooserPresenter securityLabelChooserPresenter;

    /**
     * Creates a new presenter object attached to a given view.
     * 
     * @param pageType
     *            the hablar page type.
     * @param eventBus
     *            the event bus to use.
     * @param roster
     * @param display
     *            the display to attach to this presenter.
     */
    public EditSecureRoomPresenter(
            final String pageType,
            final HablarEventBus eventBus,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final EditSecureRoomDisplay display) {
        super(pageType, eventBus, display);

        securityLabelChooserPresenter = new SecurityLabelChooserPresenter(
                securityLabelManager, securityLabelExtensionFactory,
                display.getSecurityLabelChooser());

        display.getCancel().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                requestVisibility(Visibility.hidden);
            }
        });

        display.getInvite().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onAccept();
                requestVisibility(Visibility.notFocused);
            }
        });

        roomNameValidator = new CompositeValidatorChecker(
                display.getRoomNameError(), display.getAcceptEnabled());
        roomNameValidator.add(display.getRoomName(),
                Validators.notEmpty(RoomMessages.msg.emptyGroupChatName()));
        roomNameValidator.add(display.getRoomName(),
                Validators.isValidRoomName(RoomMessages.msg.invalidGroupChatName()));
        roomNameValidator.add(display.getSelectionList(),
                new ListNotEmptyValidator<Selectable>(RoomMessages.msg
                        .selectionEmptyErrorMessage()));
        display.getRoomNameKeys().addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(final KeyDownEvent event) {
                Scheduler scheduler = Scheduler.get();
                scheduler.scheduleDeferred(roomNameValidator
                        .getScheduledCommand());
            }
        });
        display.getSelectionList().addValueChangeHandler(
                new ValueChangeHandler<List<Selectable>>() {

                    @Override
                    public void onValueChange(
                            final ValueChangeEvent<List<Selectable>> event) {
                        Scheduler scheduler = Scheduler.get();
                        scheduler.scheduleDeferred(roomNameValidator
                                .getScheduledCommand());
                    }
                });
    }

    /**
     * Gets the {@link RosterItem} list of buddies who can be included in this
     * chat.
     * 
     * @return the list of buddies.
     */
    public Collection<RosterItem> getItems() {
        return display.getSelectedItems();
    }

    /**
     * Sets the {@link RosterItem} list of buddies who can be included in this
     * chat.
     * 
     * @param items
     *            the list of buddies.
     * @param selected
     *            <code>true</code> to add it to the "selected" list,
     *            <code>false</code> to add it to the "unselected" list.
     */
    public void setItems(final Collection<RosterItem> items,
            final boolean selected) {
        display.clearList();
        for (final RosterItem item : items) {
            createItem(item, selected);
        }
    }

    /**
     * Adds a buddy (in the form of a {@link RosterItem}) to the widget.
     * 
     * @param item
     *            the buddy to add.
     * @param selected
     *            <code>true</code> to add it to the "selected" list,
     *            <code>false</code> to add it to the "unselected" list.
     */
    protected void createItem(final RosterItem item, final boolean selected) {
        if (selected) {
            display.addSelectedRosterItem(item);
        } else {
            display.addRosterItem(item);
        }
    }

    /**
     * Overridde to provide the action when the accept button is clicked.
     */
    protected abstract void onAccept();

    @Override
    protected void onBeforeFocus() {
        onPageOpen();
    }

    /**
     * Overridde to initialise the widget when the page is opened.
     */
    protected abstract void onPageOpen();

    /**
     * Sends invitations to all the selected buddies.
     * 
     * @param room
     *            the room to which to invite the users.
     */
    protected void sendInvitations(final Room room) {
        final String reasonText = display.getMessage().getText();
        for (final RosterItem item : getItems()) {
            GWT.log("INVITING: " + item.getJID());
            room.sendInvitationTo(item.getJID(), reasonText);
        }
    }
}

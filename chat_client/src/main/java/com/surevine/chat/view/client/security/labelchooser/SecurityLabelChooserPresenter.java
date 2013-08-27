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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.common.xmpp.security.label.Caveat;
import com.surevine.chat.common.xmpp.security.label.OpenGroupMarking;
import com.surevine.chat.common.xmpp.security.label.Prefix;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.label.SecurityLabel;
import com.surevine.chat.common.xmpp.security.label.SupplementalMarking;
import com.surevine.chat.view.client.ChatClientEntryPoint;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.labelchooser.groups.OpenGroupMarkingDisplay;
import com.surevine.chat.view.client.security.labelchooser.groups.OpenGroupMarkingPresenter;
import com.surevine.chat.view.client.security.ui.SecurityLabelPresenter;
import com.surevine.chat.view.client.xmpp.security.DisplayMarkingRenderer;

/**
 * The Presenter of the MVP (Model-View-Presenter) pattern for security label
 * chooser.
 */
public class SecurityLabelChooserPresenter {

    /**
     * The page type.
     */
    public static final String TYPE = "SecurityLabelChooser";

    /**
     * A <code>HashMap</code> of the <code>OpenGroupMarkingPresenter</code>s.
     */
    private final Map<String, OpenGroupMarkingPresenter> openGroupMarkingPresenters;

    /**
     * The presenter for the security label widget
     */
    private final SecurityLabelPresenter securityLabelPresenter;

    /**
     * The display for this widget
     */
    private final SecurityLabelChooserDisplay display;

    /**
     * The SecurityLabelManager which gives us the logic
     */
    private final ISecurityLabelManager securityLabelManager;

    /**
     * The factory to create XmppSecurityLabel extensions
     */
    private final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory;

    /**
     * Internal list of default open group markings
     */
    private final ArrayList<OpenGroupMarkingPresenter> defaultOpenGroupMarkings;

    /**
     * Internal list of non-default open group markings
     */
    private final ArrayList<OpenGroupMarkingPresenter> otherOpenGroupMarkings;

    /**
     * Flag to denote if the caveats are currently visible
     */
    private boolean caveatsVisible;

    /**
     * Flag to denote if the open groups are currently visible
     */
    private boolean openGroupsVisible;

    /**
     * Constructor.
     * 
     * @param eventBus
     * @param chat
     * @param display
     * @param boolean <code>true</code> if a room chat, otherwise
     *        <code>false</code> (a pair chat).
     */
    public SecurityLabelChooserPresenter(
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,
            final SecurityLabelChooserDisplay display) {
        this.display = display;

        this.securityLabelManager = securityLabelManager;
        this.xmppSecurityLabelExtensionFactory = xmppSecurityLabelExtensionFactory;

        openGroupMarkingPresenters = new HashMap<String, OpenGroupMarkingPresenter>();

        defaultOpenGroupMarkings = new ArrayList<OpenGroupMarkingPresenter>();
        otherOpenGroupMarkings = new ArrayList<OpenGroupMarkingPresenter>();

        setOpenGroupMarkings();

        securityLabelPresenter = new SecurityLabelPresenter(
                display.getSecurityLabelDisplay(), null);

        // Set the data for the various lists
        display.getPrefixList().setList(securityLabelManager.getPrefixes());
        display.getPrefixList().setNoItemText(
                securityLabelManager.getNoPrefixText());

        display.getProtectiveMarkingList().setList(
                securityLabelManager.getProtectiveMarkings());
        display.getProtectiveMarkingList().setNoItemAllowed(false);

        display.getCaveatList().setList(securityLabelManager.getCaveats());
        display.getCaveatList().setNoItemText(
                securityLabelManager.getNoCaveatText());

        reset();

        display.getPrefix().addValueChangeHandler(
                new ValueChangeHandler<Prefix>() {
                    public void onValueChange(
                            final ValueChangeEvent<Prefix> event) {
                        updateSecurityLabel();
                    }
                });

        display.getProtectiveMarking().addValueChangeHandler(
                new ValueChangeHandler<ProtectiveMarking>() {
                    public void onValueChange(
                            final ValueChangeEvent<ProtectiveMarking> event) {
                        updateCaveatsVisibility();
                        updateCaveatsList();
                        updateOpenGroupsVisibility();
                        updatePrefixList();
                        updateSecurityLabel();
                    }
                });

        display.getCaveat().addValueChangeHandler(
                new ValueChangeHandler<Caveat>() {
                    public void onValueChange(
                            final ValueChangeEvent<Caveat> event) {
                        updateSecurityLabel();
                    }
                });

    }

    /**
     * Updates the security label displayed
     */
    private void updateSecurityLabel() {
        securityLabelPresenter
                .setXmppSecurityLabel(createSecurityLabel(display));
    }

    /**
     * Updates whether the caveats are visible or not
     */
    private void updateCaveatsList() {
        final List<Caveat> caveats = securityLabelManager
                .getCaveatsForProtectiveMarking(display.getProtectiveMarking()
                        .getValue());

        display.getCaveatList().setList(caveats);

        display.setCaveatsVisible(caveatsVisible);
    }

    /**
     * Updates whether the caveats are visible or not
     */
    private void updateCaveatsVisibility() {
        caveatsVisible = securityLabelManager.showCaveats(display
                .getProtectiveMarking().getValue());

        display.setCaveatsVisible(caveatsVisible);
    }

    /**
     * Updates whether the open groups are visible or not
     */
    private void updateOpenGroupsVisibility() {
        openGroupsVisible = securityLabelManager.showOpenGroups(display
                .getProtectiveMarking().getValue());

        display.setOpenGroupsVisible(openGroupsVisible);
    }

    /**
     * Sets the current security label
     */
    public void setSecurityLabel(final IXmppSecurityLabelExtension securityLabel) {
        if (securityLabel == null) {
            return;
        }

        final ISecurityLabel iLabel = securityLabel.getLabel();

        if ((iLabel == null) || !(iLabel instanceof SecurityLabel)) {
            return;
        }

        final SecurityLabel label = (SecurityLabel) iLabel;

        // First update the protective marking
        display.getProtectiveMarking().setValue(label.getProtectiveMarking(),
                true);

        // Then update all the lists and stuff to reflect the selected
        // protective marking
        updatePrefixList();
        updateCaveatsVisibility();
        updateCaveatsList();
        updateOpenGroupsVisibility();

        // Then update everything else
        display.getPrefix().setValue(label.getPrefix(), true);

        // Do something weird here as the label can have multiple caveats, but
        // we only display one. We will just display the first one that matches
        for (final Caveat caveat : label.getCaveats()) {
            try {
                display.getCaveat().setValue(caveat);
            } catch (final IndexOutOfBoundsException eIOOB) {
                continue;
            }

            // No exception caught so we must have found a matching one
            break;
        }

        // Set the open groups - deselect them first then select all the
        // provided ones
        for (final OpenGroupMarkingPresenter presenter : openGroupMarkingPresenters
                .values()) {
            presenter.setSelected(false);
        }

        for (final SupplementalMarking supplementalMarking : label
                .getSupplementalMarkings()) {
            if (openGroupMarkingPresenters.containsKey(supplementalMarking
                    .getName())) {
                openGroupMarkingPresenters.get(supplementalMarking.getName())
                        .setSelected(true);
            }
        }

        updateOpenGroupMarkings(true);

        updateSecurityLabel();
    }

    /**
     * Returns the current security label
     * 
     * @return
     */
    public IXmppSecurityLabelExtension getSecurityLabel() {
        return createSecurityLabel(display);
    }

    /**
     * Create a security label from the chosen components on the page.
     * 
     * @param display
     *            The <code>SecurityLabelChooserDisplay</code>.
     * @return The <code>IXmppSecurityLabelExtension</code> security label, or
     *         null if it couldn't be created.
     */
    protected IXmppSecurityLabelExtension createSecurityLabel(
            final SecurityLabelChooserDisplay display) {

        Prefix prefix = null;
        ProtectiveMarking protectiveMarking = null;
        final List<SupplementalMarking> suppMarkings = new ArrayList<SupplementalMarking>();
        final List<Caveat> caveats = new ArrayList<Caveat>();

        // Get the selected ProtectiveMarking.
        prefix = display.getPrefix().getValue();

        // Get the selected ProtectiveMarking.
        protectiveMarking = display.getProtectiveMarking().getValue();

        // Get the selected caveat.
        if (caveatsVisible) {
            final Caveat caveat = display.getCaveat().getValue();

            if (caveat != null) {
                caveats.add(caveat);
            }
        }

        if (openGroupsVisible) {
            // Get the selected OpenGroupMarkings.
            for (final OpenGroupMarkingPresenter openGroupMarkingPresenter : getOpenGroupMarkings()) {
                if (openGroupMarkingPresenter.isSelected()) {
                    suppMarkings.add(openGroupMarkingPresenter.getItem());
                }
            }
        }

        // Set up the display marking renderer
        final DisplayMarkingRenderer renderer = new DisplayMarkingRenderer(
                ChatClientEntryPoint.getSecurityLabelManager()
                        .getDisplayLabelMappings());
        try {
            return xmppSecurityLabelExtensionFactory
                    .createXMPPSecurityLabelExtension(new SecurityLabel(prefix,
                            protectiveMarking, caveats, suppMarkings), renderer);
        } catch (final IllegalArgumentException e) {
            // There was something wrong with the security label
            return null;
        }
    }

    /**
     * Get the <code>OpenGroupMarkingPresenter</code>s.
     * 
     * @return The <code>Collection</code> of
     *         <code>OpenGroupMarkingPresenter</code>s.
     */
    public Collection<OpenGroupMarkingPresenter> getOpenGroupMarkings() {
        return openGroupMarkingPresenters.values();
    }

    /**
     * 
     * @param openGroupMarking
     * @param selectable
     * @param selected
     */
    protected OpenGroupMarkingPresenter createOpenGroupMarkingDisplay(
            final OpenGroupMarking openGroupMarking, final boolean isDefault) {
        boolean isEnabled = true;

        if (isDefault && !securityLabelManager.allowNoOpenGroup()) {
            isEnabled = false;
        }

        final OpenGroupMarkingDisplay groupDisplay = display
                .createOpenGroupMarking();
        final OpenGroupMarkingPresenter selectGroup = new OpenGroupMarkingPresenter(
                openGroupMarking, groupDisplay, isEnabled);

        selectGroup.setSelected(isDefault);

        display.addOpenGroupMarking(groupDisplay);
        openGroupMarkingPresenters.put(openGroupMarking.getName(), selectGroup);

        groupDisplay.getClickHandler().addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                if (isDefault) {
                    updateOpenGroupMarkings(!selectGroup.isSelected());
                } else {
                    updateOpenGroupMarkings(false);
                }

                updateSecurityLabel();
            }
        });

        return selectGroup;
    }

    /**
     * Create and initialise <code>OpenGroupMarking</code>s.
     */
    protected void setOpenGroupMarkings() {
        final List<OpenGroupMarking> openGroupMarkings = securityLabelManager
                .getOpenGroupMarkings();

        display.clearGroupsList();

        for (final OpenGroupMarking openGroupMarking : openGroupMarkings) {
            if (securityLabelManager.isDefaultOpenGroup(openGroupMarking)) {
                defaultOpenGroupMarkings.add(createOpenGroupMarkingDisplay(
                        openGroupMarking, true));
            } else {
                otherOpenGroupMarkings.add(createOpenGroupMarkingDisplay(
                        openGroupMarking, false));
            }
        }
    }

    /**
     * Updates the selectability of the open group markings
     * 
     * @param deselectDefaults
     *            If <code>true</code> then all default markings will be
     *            deselected if any of them are deslected, otherwise they will
     *            all be reselected if any of them are selected
     */
    protected void updateOpenGroupMarkings(final boolean deselectDefaults) {
        if (securityLabelManager.allowNoOpenGroup()) {
            // If we're allowing no open groups then there is a vast amount of
            // logic around this...
            boolean allDefaultsSelected = true;
            boolean anyDefaultsSelected = false;
            boolean anyOthersSelected = false;

            // Work out if any or all default groups are selected
            for (final OpenGroupMarkingPresenter ogmPresenter : defaultOpenGroupMarkings) {
                if (ogmPresenter.isSelected()) {
                    anyDefaultsSelected = true;
                } else {
                    allDefaultsSelected = false;
                }
            }

            // Work out if any other groups are selected
            for (final OpenGroupMarkingPresenter ogmPresenter : otherOpenGroupMarkings) {
                if (ogmPresenter.isSelected()) {
                    anyOthersSelected = true;
                    break;
                }
            }

            // Now to do the logic
            if (allDefaultsSelected) {
                // If all the default groups are selected
                for (final OpenGroupMarkingPresenter ogmPresenter : defaultOpenGroupMarkings) {
                    if (anyOthersSelected) {
                        ogmPresenter.setEnabled(false);
                    } else {
                        ogmPresenter.setEnabled(true);
                    }
                }
            } else {
                if (anyOthersSelected) {
                    for (final OpenGroupMarkingPresenter ogmPresenter : defaultOpenGroupMarkings) {
                        ogmPresenter.setSelected(true);
                        ogmPresenter.setEnabled(false);
                    }
                }

                if (anyDefaultsSelected) {
                    for (final OpenGroupMarkingPresenter ogmPresenter : defaultOpenGroupMarkings) {
                        if (deselectDefaults) {
                            ogmPresenter.setSelected(false);
                        } else {
                            ogmPresenter.setSelected(true);
                        }
                    }
                }
            }
        } else {
            for (final OpenGroupMarkingPresenter ogmPresenter : defaultOpenGroupMarkings) {
                ogmPresenter.setSelected(true);
                ogmPresenter.setEnabled(false);
            }
        }
    }

    /**
     * {@inheritDoc}.
     */
    protected void reset() {
        display.reset();
        updateAllFields();
    }

    /**
     * Updates the visibility, contents, etc of all the fields to reflect the
     * current security label.
     */
    private void updateAllFields() {
        updatePrefixList();
        updateCaveatsVisibility();
        updateCaveatsList();
        updateOpenGroupsVisibility();
        updateSecurityLabel();
    }

    /**
     * Updates the prefix dropdown as required
     */
    protected void updatePrefixList() {
        display.getPrefixList().setNoItemAllowed(
                securityLabelManager.allowNoPrefix(display
                        .getProtectiveMarking().getValue()));
    }

}

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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.surevine.chat.common.xmpp.security.label.ISecurityLabelPart;
import com.surevine.chat.view.client.ui.ISecurityLabelPartList;

/**
 * A List box containing the available security label parts.
 * 
 * @param <T>
 *            the type of the {@link ISecurityLabelPart} which will be displayed
 *            in the list box
 */
public class SecurityLabelPartListBox<T extends ISecurityLabelPart> extends
        ListBox implements HasValue<T>, ISecurityLabelPartList<T> {

    /**
     * A list of available protective markings.
     */
    private List<T> items;

    /**
     * This is the text that will be displayed for the none selected option.
     */
    private String noItemText;

    /**
     * This determines whether the no item option will be displayed.
     */
    private boolean noItemAllowed;

    /**
     * Default constructor.
     */
    public SecurityLabelPartListBox() {
        this("", new ArrayList<T>());
    }

    /**
     * Constructor.
     * 
     * @param noItemText
     *            the text which will be shown on the "no item" option
     * @param items
     *            the items to show in the list box
     */
    public SecurityLabelPartListBox(final String noItemText, final List<T> items) {
        this.noItemText = noItemText;
        this.items = items;
        noItemAllowed = (noItemText != null);

        populateListBox();

        // Add a handler to map through the list box change event to the value
        // change event
        addChangeHandler(new ChangeHandler() {
            public void onChange(final ChangeEvent event) {
                ValueChangeEvent
                        .fire(SecurityLabelPartListBox.this, getValue());
            }
        });
    }

    /**
     * Populate the <code>ListBox</code>.
     */
    private void populateListBox() {
        final T currentItem = getValue();

        clear();

        if (noItemAllowed) {
            this.addItem(noItemText, "-1");
        }

        for (int i = 0; i < items.size(); ++i) {
            this.addItem(items.get(i).getName(), String.valueOf(i));
        }

        // We will attempt to set the value to the same as it was before (if it
        // still exists)
        try {
            setValue(currentItem, true);
        } catch (final IndexOutOfBoundsException e) {
            // Ignore
        } catch (final NullPointerException e) {
            // Ignore
        }
    }

    /**
     * Get the selected caveat from the <code>ListBox</code>.
     * 
     * @return The selected <code>Caveat</code>.
     */
    public T getSelectedItem() {
        try {
            final int selectedItemId = Integer
                    .parseInt(getValue(getSelectedIndex()));

            if (selectedItemId == -1) {
                return null;
            }

            return items.get(selectedItemId);
        } catch (final IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        } catch (final NumberFormatException eNF) {
            return null;
        }
    }

    /**
     * Sets the list of items to display.
     * 
     * @param items
     *            the list of items
     */
    public void setItems(final List<T> items) {
        if (items == null) {
            throw new NullPointerException("items cannot be null");
        }

        this.items = items;
        populateListBox();
    }

    /**
     * {@inheritDoc}.
     */
    public T getValue() {
        return getSelectedItem();
    }

    /**
     * {@inheritDoc}.
     */
    public void setValue(final T value) {
        setValue(value, false);
    }

    /**
     * {@inheritDoc}.
     */
    public void setValue(final T value, final boolean fireEvents) {
        final int index = getIndexForItem(value);

        if (index > -1) {
            setSelectedIndex(index);
        } else {
            throw new IndexOutOfBoundsException("Caveat " + value
                    + " not found in list");
        }
    }

    /**
     * Sets whether to allow the user to select the no prefix option.
     * 
     * @param value
     *            <code>true</code> to allow the user to select no prefix
     */
    public void setNoItemAllowed(final boolean value) {
        if (noItemAllowed != value) {
            noItemAllowed = value;
            populateListBox();
        }
    }

    /**
     * Return the list box option index for the Caveat.
     * 
     * @param value
     *            the item for which to get the index
     * @return the index, or -1 if not found
     */
    private int getIndexForItem(final T value) {
        int index;

        // If the value is null and we have a no caveat option
        if ((value == null) && noItemAllowed) {
            index = 0;
        } else {
            index = items.indexOf(value);

            // If we've found the caveat and have a no caveat option, we need to
            // add to the index
            if ((index > -1) && noItemAllowed) {
                ++index;
            }
        }

        return index;
    }

    /**
     * {@inheritDoc}.
     */
    public HandlerRegistration addValueChangeHandler(
            final ValueChangeHandler<T> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<T> getList() {
        return items;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setList(final List<T> newList) {
        items = newList;
        populateListBox();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setNoItemText(final String text) {
        noItemText = text;
        populateListBox();
    }
}

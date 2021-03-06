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

package com.surevine.chat.view.client.ui;

import java.util.List;

/**
 * Interface for classes which have a list of objects
 * 
 * @param <T>
 *            the type of object which is stored in the list
 */
public interface ISecurityLabelPartList<T> {

    /**
     * Sets this list for this object
     * 
     * @param newList
     *            the new list.
     */
    void setList(List<T> newList);

    /**
     * Gets the list for this object
     * 
     * @return
     */
    List<T> getList();

    /**
     * Sets the text which will be displayed on the "no item" option
     * 
     * @param text
     *            the text to display.
     */
    void setNoItemText(String text);

    /**
     * Sets whether to show the "no item" option
     * 
     * @param allow
     *            <code>true</code> to display the option
     */
    void setNoItemAllowed(boolean allow);
}

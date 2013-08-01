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
package com.surevine.chat.view.client.chat.ui;

import com.calclab.hablar.chat.client.ui.ChatMessage;
import com.calclab.hablar.chat.client.ui.ChatWidget;
import com.calclab.hablar.core.client.avatars.AvatarDisplay;
import com.calclab.hablar.core.client.ui.menu.Action;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.view.client.security.ui.SecurityLabelDisplay;
import com.surevine.chat.view.client.security.ui.SecurityLabelWidget;

/**
 * The View of the MVP (Model-View-Presenter) pattern.
 */
public class SecureChatWidget extends Composite implements SecureChatDisplay {

    /**
     *
     */
    public static final double LABEL_HEIGHT = 1.5;
    /**
     *
     */
    public static final double FOOTER_HEIGHT = 2;

    /**
     * The SecureChatWidgetUiBinder interface.
     */
    interface SecureChatWidgetUiBinder extends
            UiBinder<Widget, SecureChatWidget> {
    }

    /**
     * The uiBinder.
     */
    private static SecureChatWidgetUiBinder uiBinder = GWT
            .create(SecureChatWidgetUiBinder.class);

    /**
     * The <code>ChatWidget</code> UiField.
     */
    @UiField(provided = true)
    protected ChatWidget chatWidget;

    /**
     * The <code>SecurityLabelWidget</code> UiField.
     */
    @UiField
    SecurityLabelWidget securityLabelWidget;

    /**
     *
     */
    @UiField
    LayoutPanel mainPanel;

    /**
     *
     */
    private double labelHeight = 0;

    /**
     * The change classification <code>Button</code> UiField.
     */
    Button changeClassificationButton;

    /**
     * Constructor.
     * 
     * @param chatWidget
     *            The <code>ChatWidget</code>.
     */
    public SecureChatWidget(final ChatWidget chatWidget) {
        this.chatWidget = chatWidget;
        initWidget(uiBinder.createAndBindUi(this));

        changeClassificationButton = new Button("Change Classification");
        changeClassificationButton.getElement().addClassName(
                "hablar-ChangeClassificationButton");
        
        final FlowPanel container = new FlowPanel();
        container.add(changeClassificationButton);
        
        addToActions(container);
    }

    /**
     * {@inheritDoc}.
     */
    public void clearAndFocus() {
        chatWidget.clearAndFocus();
    }

    /**
     * {@inheritDoc}.
     */
    public HasClickHandlers getAction() {
        return chatWidget.getAction();
    }

    /**
     * {@inheritDoc}.
     */
    public HasText getBody() {
        return chatWidget.getBody();
    }

    /**
     * {@inheritDoc}.
     */
    public HasKeyDownHandlers getTextBox() {
        return chatWidget.getTextBox();
    }

    /**
     * {@inheritDoc}.
     */
    public void setControlsVisible(final boolean visible) {
        chatWidget.setControlsVisible(visible);
    }

    /**
     * {@inheritDoc}.
     */
    public void setId(final String id) {
        chatWidget.setId(id);
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
    public SecurityLabelDisplay getSecurityLabel() {
        return securityLabelWidget;
    }

    /**
     * {@inheritDoc}.
     */
    public HasClickHandlers changeClassificationButton() {
        return changeClassificationButton;
    }

    /**
     * {@inheritDoc}.
     */
    public HasClickHandlers createAction(final Action<?> action) {
        return chatWidget.createAction(action);
    }

    /**
     * {@inheritDoc}.
     */
    public HasText getState() {
        return chatWidget.getState();
    }

    /**
     * {@inheritDoc}.
     */
    public void setStatusVisible(final boolean visible) {
        chatWidget.setStatusVisible(visible);
    }

    /**
     * Set the security label visible or not.
     * 
     * @param visible
     *            <code>true</code> for visible otherwise <code>false</code>.
     */
    public void setLabelVisible(final boolean visible) {
        layoutLabel();
        // FIXME: Animation
        // page.forceLayout();
        labelHeight = visible ? LABEL_HEIGHT : 0;
        layoutLabel();
        mainPanel.animate(500);
    }

    /**
     * Set the new security label button visible or not.
     * 
     * @param visible
     *            <code>true</code> for visible otherwise <code>false</code>.
     */
    public void setChangeClassificationVisible(final boolean visible) {
        changeClassificationButton.setVisible(visible);
    }

    /**
     *
     */
    private void layoutLabel() {
        mainPanel.setWidgetTopHeight(securityLabelWidget, 0, Unit.EM,
                labelHeight, Unit.EM);
        mainPanel.setWidgetTopBottom(chatWidget, labelHeight, Unit.EM, 0,
                Unit.EM);
    }

    /**
     * {@inheritDoc}.
     */
    public void addToActions(final Widget widget) {
        chatWidget.addToActions(widget);
    }

    @Override
    public HasFocusHandlers getTextBoxFocus() {
        // TODO Auto-generated method stub
        return chatWidget.getTextBoxFocus();
    }

    @Override
    public void setTextBoxFocus(final boolean hasFocus) {
        chatWidget.setTextBoxFocus(hasFocus);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addMessage(ChatMessage message, String messageClass) {
       chatWidget.addMessage(message, messageClass);
    }

    @Override
    public AvatarDisplay addAvatar(final String title) {
        return chatWidget.addAvatar(title);
    }
    
    @Override
    public void removeAvatar(final AvatarDisplay avatar) {
        chatWidget.removeAvatar(avatar);
    }

    @Override
    public String getAvatarSize() {
        return chatWidget.getAvatarSize();
    }
}

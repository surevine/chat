package com.surevine.chat.view.client.chat.ui.open;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;

/**
 * A widget to open a new secure chat
 */
public class OpenSecureChatWidget extends Composite implements
        OpenSecureChatDisplay {
    /**
     * 
     */
    interface OpenSecureChatWidgetUiBinder extends
            UiBinder<Widget, OpenSecureChatWidget> {
    }

    /**
     * 
     */
    private static OpenSecureChatWidgetUiBinder uiBinder = GWT
            .create(OpenSecureChatWidgetUiBinder.class);

    @UiField
    Label title;

    @UiField
    SecurityLabelChooserDisplay securityLabelChooserWidget;

    @UiField
    Button accept;

    @UiField
    Button cancel;

    /**
     * Creates a new widget
     */
    public OpenSecureChatWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * {@inheritDoc}
     */
    public HasClickHandlers getAccept() {
        return accept;
    }

    /**
     * {@inheritDoc}
     */
    public HasClickHandlers getCancel() {
        return cancel;
    }

    /**
     * {@inheritDoc}
     */
    public SecurityLabelChooserDisplay getSecurityLabelChooser() {
        return securityLabelChooserWidget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public HasText getTitleLabel() {
        return title;
    }

}

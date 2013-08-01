package com.surevine.chat.view.client.chat.ui.open;

import com.calclab.hablar.core.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;

/**
 * Display interface to open a new one-to-one chat
 */
public interface OpenSecureChatDisplay extends Display {

    /**
     * Gets the title element
     */
    HasText getTitleLabel();

    /**
     * Returns the security label chooser
     * 
     * @return the security label chooser.
     */
    SecurityLabelChooserDisplay getSecurityLabelChooser();

    /**
     * The accept button
     * 
     * @return
     */
    HasClickHandlers getAccept();

    /**
     * The cancel button
     * 
     * @return
     */
    HasClickHandlers getCancel();

}

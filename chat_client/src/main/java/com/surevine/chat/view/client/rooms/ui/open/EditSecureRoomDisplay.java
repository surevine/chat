package com.surevine.chat.view.client.rooms.ui.open;

import com.calclab.hablar.rooms.client.open.EditRoomDisplay;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserDisplay;

public interface EditSecureRoomDisplay extends EditRoomDisplay {

    /**
     * Returns the security label chooser for this room
     * 
     * @return the security label chooser.
     */
    SecurityLabelChooserDisplay getSecurityLabelChooser();

    /**
     * Sets whether the security label chooser is visible
     */
    void setSecurityLabelChooserVisible(boolean visible);
}

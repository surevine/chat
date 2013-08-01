package com.surevine.chat.view.client.rooms.ui.classification;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

public class ChangeRoomClassificationPresenter extends OpenSecureChatPresenter {

    private final SecureRoomFactory secureRoomFactory;
    
    private final XmppSession session;
    
    private final RoomManager roomManager;

    public ChangeRoomClassificationPresenter(final HablarEventBus eventBus,
            final XmppSession session, final RoomManager roomManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,            final OpenSecureChatWidget openSecureChatWidget, final Room room,
            final SecureRoomFactory secureRoomFactory) {

        super(eventBus, roomManager, securityLabelManager, xmppSecurityLabelExtensionFactory, openSecureChatWidget, room);
        this.session = session;
        this.secureRoomFactory = secureRoomFactory;
        this.roomManager = roomManager;
    }

    @Override
    protected void openChat(final XmppURI toUri, final Chat relatedChat) {
        assert relatedChat instanceof Room : relatedChat.getClass().toString();

        final Room relatedRoom = (Room) relatedChat;

        secureRoomFactory.changeRoomSecurityLabel(session.getCurrentUserURI(),
                roomManager, relatedRoom, securityLabelChooserPresenter
                        .getSecurityLabel());
        
        requestVisibility(Visibility.hidden);
    }
}

package com.surevine.chat.view.client.roster;

import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.roster.client.RosterBasicActions;
import com.calclab.hablar.roster.client.RosterMessages;
import com.calclab.hablar.roster.client.groups.RosterItemPresenter;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * Overrides the default actions on the roster item menu to start a secure chat
 * when the user requests to start a chat.
 */
public class RosterMenuActions extends RosterBasicActions {

    private final Hablar hablar;
    
    private final ChatManager chatManager;
    
    final ISecurityLabelManager securityLabelManager;
    
    final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory;

    /**
     * Construct a new instance with the given event bus.
     * 
     * @param eventBus
     *            the event bus.
     */
    @Inject
    public RosterMenuActions(final Hablar hablar, final XmppRoster roster, final ChatManager chatManager, final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory) {
        super(roster, chatManager, hablar.getEventBus());

        this.hablar = hablar;
        this.chatManager = chatManager;
        this.securityLabelManager = securityLabelManager;
        this.xmppSecurityLabelExtensionFactory = xmppSecurityLabelExtensionFactory;
    }

    @Override
    public void addHighPriorityActions(final RosterPage rosterPage) {
        rosterPage.getItemMenu().addAction(
                new SimpleAction<RosterItemPresenter>(RosterMessages.msg.startChatAction(),
                        ACTION_ID_START_CHAT) {

                    @Override
                    public void execute(final RosterItemPresenter target) {
                        // Setup the Security Label Chooser page
                        final OpenSecureChatPresenter openChat = new OpenSecureChatPresenter(
                                hablar.getEventBus(),
                                chatManager, securityLabelManager, xmppSecurityLabelExtensionFactory, new OpenSecureChatWidget(), target.getItem()
                                        .getJID());
                        hablar.addPage(openChat, OverlayContainer.ROL);

                        openChat.requestVisibility(Visibility.focused);
                    }
                });
    }
}

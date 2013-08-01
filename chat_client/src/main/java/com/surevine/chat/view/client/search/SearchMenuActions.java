package com.surevine.chat.view.client.search;

import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.search.client.SearchResultItem;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.Menu;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.search.client.SearchBasicActions;
import com.calclab.hablar.search.client.SearchMessages;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

public class SearchMenuActions extends SearchBasicActions {

    private final Hablar hablar;

    private final ChatManager chatManager;
    
    final ISecurityLabelManager securityLabelManager;
    
    final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory;
    
    public SearchMenuActions(final Hablar hablar, final XmppRoster roster, final ChatManager chatManager,final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory) {
        super(roster, chatManager);
        this.hablar = hablar;
        this.chatManager = chatManager;
        this.securityLabelManager = securityLabelManager;
        this.xmppSecurityLabelExtensionFactory = xmppSecurityLabelExtensionFactory;
    }

    @Override
    protected void addStartChatAction(final Menu<SearchResultItem> menu) {
        menu.addAction(new SimpleAction<SearchResultItem>(SearchMessages.msg.chat(),
                CHAT_DEB_ID) {
            @Override
            public void execute(final SearchResultItem item) {

                // Setup the Security Label Chooser page
                final OpenSecureChatPresenter openChat = new OpenSecureChatPresenter(
                        hablar.getEventBus(), chatManager, securityLabelManager, xmppSecurityLabelExtensionFactory, new OpenSecureChatWidget(), item
                                .getJid());
                hablar.addPage(openChat, OverlayContainer.ROL);

                openChat.requestVisibility(Visibility.focused);
            }
        });
    }

}

package com.surevine.chat.view.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.SecurityLabelManager;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;
import com.surevine.chat.view.client.xmpp.security.SecurityLabelService;

public class ChatClientGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ISecurityLabelService.class).to(SecurityLabelService.class).in(Singleton.class);
        bind(ISecurityLabelManager.class).to(SecurityLabelManager.class).in(Singleton.class);
    }
/*    
    @Provides
    @Named("Pair")
    @Inject
    protected ChatSelectionStrategy providePairChatSelectionStrategy(final ISecurityLabelService securityLabelService) {
        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_CHAT);
        
        return strategy;
    }
    
    @Provides
    @Named("Room")
    @Inject
    protected ChatSelectionStrategy provideGroupChatSelectionStrategy(final ISecurityLabelService securityLabelService) {
        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_GROUPCHAT);
        
        return strategy;
    }
*/    
}

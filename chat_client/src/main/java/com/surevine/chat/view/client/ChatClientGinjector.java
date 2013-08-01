package com.surevine.chat.view.client;

import com.calclab.emite.browser.client.AutoConfig;
import com.calclab.hablar.client.HablarGinjector;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.google.gwt.inject.client.GinModules;
import com.surevine.chat.common.xmpp.ChatCommonGinModule;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;

@GinModules({ChatClientGinModule.class,ChatCommonGinModule.class})
public interface ChatClientGinjector extends HablarGinjector {

    ISecurityLabelManager getSecurityLabelManager();

    XmppSecurityLabelExtensionFactory getXmppSecurityLabelExtensionFactory();

    ISecurityLabelService getSecurityLabelService();
    
    AvatarProviderRegistry getAvatarProviderRegistry();
    
    AutoConfig getAutoConfig();
}

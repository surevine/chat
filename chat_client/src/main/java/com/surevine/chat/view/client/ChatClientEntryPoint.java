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

package com.surevine.chat.view.client;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURIFactory;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.hablar.client.HablarConfig;
import com.calclab.hablar.console.client.HablarConsole;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarWidget;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.html.client.HtmlConfig;
import com.calclab.hablar.login.client.HablarLogin;
import com.calclab.hablar.login.client.LoginConfig;
import com.calclab.hablar.roster.client.RosterMessages;
import com.calclab.hablar.search.client.query.NicknameContainsSearchQueryFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.roster.RosterMenuActions;
import com.surevine.chat.view.client.search.SearchMenuActions;
import com.surevine.chat.view.client.security.SecurityLabelManager;
/**
 * The GWT module entry point for the Chat Client.
 */
public class ChatClientEntryPoint implements EntryPoint {

    /**
     * The security label manager.
     */
    private static final SecurityLabelManager SECURITY_LABEL_MANAGER = new SecurityLabelManager();

    /**
     * Gets the security label manager.
     * 
     * @return The security label manager.
     */
    public static SecurityLabelManager getSecurityLabelManager() {
        return SECURITY_LABEL_MANAGER;
    }

    /**
     * {@inheritDoc}.
     */
    public void onModuleLoad() {
        // Sets the default icon set
//        DefaultIcons.load();

        final ChatClientGinjector ginjector = GWT
                .create(ChatClientGinjector.class);

        // Ensure that AutoConfig (not AutoConfigBoot) has definitely been run before we try and log in
        ginjector.getAutoConfig();
        
        final XmppSession session = ginjector.getXmppSession();
        
        final HablarConfig config = HablarConfig.getFromMeta();
        final HablarWidget widget = new HablarWidget(config.layout, config.tabHeaderSize);
        final Hablar hablar = widget.getHablar();

        new HablarLogin(hablar, LoginConfig.getFromMeta(), session);

        // Configuration
        config.searchConfig.queryFactory = new NicknameContainsSearchQueryFactory();

        final XmppRoster roster = ginjector.getXmppRoster();
        final ChatManager chatManager = ginjector.getChatManager();
        
        final SearchMenuActions searchMenu = new SearchMenuActions(hablar,
                roster, chatManager, ginjector.getSecurityLabelManager(),
                ginjector.getXmppSecurityLabelExtensionFactory());
        config.searchConfig.searchActions = searchMenu;

        config.rosterConfig.rosterItemClickAction = new SimpleAction<RosterItem>(
                RosterMessages.msg.clickToChatWith(), "rosterItemClickAction") {
            @Override
            public void execute(final RosterItem item) {

                // Setup the Security Label Chooser page
                final OpenSecureChatPresenter openChat = new OpenSecureChatPresenter(
                        hablar.getEventBus(), chatManager,
                        ginjector.getSecurityLabelManager(),
                        ginjector.getXmppSecurityLabelExtensionFactory(),
                        new OpenSecureChatWidget(), item.getJID());
                hablar.addPage(openChat, OverlayContainer.ROL);

                openChat.requestVisibility(Visibility.focused);
            }
        };

        config.rosterConfig.rosterMenuActions = new RosterMenuActions(hablar,
                roster, chatManager, ginjector.getSecurityLabelManager(),
                ginjector.getXmppSecurityLabelExtensionFactory());
        
        widget.setWidth("100%");
        widget.setHeight("100%");
        // Apply the configuration
        ChatClientHablar.install(widget.getHablar(), config, ginjector);

        // Add the console functionality if console=true is given as a request
        // param
        if ((Location.getParameter("console") != null)
                && (Location.getParameter("console").equals("true"))) {
            new HablarConsole(widget.getHablar(),
                    ginjector.getXmppConnection(), ginjector.getXmppSession());
        }

        final RootPanel rootPanel = RootPanel.get("chatclient_container");
        rootPanel.add(widget.asWidget());
        
        this.exposeRosterAction();
        GWT.log("Exposed the roster action");
        
        // Use a deferred scheduled command to ensure that the host page is notified that the application is ready.
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                notifyHostPage();
            }
            
        });
    }
    
    private native void notifyHostPage() /*-{
        // Only make the call to the onGwtReady method if it has been setup.
        if (typeof $wnd.onGwtReady != 'undefined') {
            $wnd.onGwtReady();
        }
    }-*/;
    
    public native void exposeRosterAction() /*-{
        var that = this;
        $wnd.rosterAction = $entry(function(from) {
          that.@com.surevine.chat.view.client.ChatClientEntryPoint::executeRosterAction(Ljava/lang/String;)(from)
        });
    }-*/;
    
    public void executeRosterAction(final String uri) {
        if(uri == null) {
            return;
        }
        
        GWT.log("in the instance method with arg - " + uri);
        
        XmppURIFactory xmppURIFactory =  new XmppURIFactory();
        
        XmppURI xmppURI = xmppURIFactory.parse(uri);
        
        RosterItem target = new RosterItem(xmppURI, null, null, null);
        
        config.rosterConfig.rosterItemClickAction.execute(target);
    }
    
    private HablarConfig config;
    
}

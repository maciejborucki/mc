/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class WelcomeView extends VerticalLayout implements View {

    final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final HorizontalLayout menuBar = new MenuView();
    final HorizontalLayout horizontalMainLayout = new HorizontalLayout();
    final VerticalLayout verticalMenuLayout = new VerticalLayout();
    final VerticalLayout verticalMainLayout = new VerticalLayout();
    final Navigator navigator = ViewUtils.getNavigator();
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewUtils.checkAuthorization(getUI());
    }

    public WelcomeView() {
        navigator.addView(Const.LOGOUT_VIEW, new LogoutView());
        navigator.addView(Const.PERSONAL_DATA_VIEW, new PersonalDataView());
        initComponents();
    }

    private void initComponents() {
        ViewUtils.generateHeaderFooter(header, footer);                   
        layout.addComponent(menuBar);
        layout.addComponent(new VerticalLayout(new Label("dsakjdskj")));
        this.setSizeFull();
        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);       
        ViewUtils.attachHeader(this, header);        
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        this.addComponent(layout);
        this.setExpandRatio(layout, 80);       
        ViewUtils.attachFooter(this, footer);   
    }
}
